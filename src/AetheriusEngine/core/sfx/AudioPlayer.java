package AetheriusEngine.core.sfx;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
Lolita's Revenge
June 29 2017

Plays and manages audio files.
 */

public class AudioPlayer extends LinkedList<Media> implements Runnable {

    /*------------------------------------------------------------------------------------------------------------------
     Variables.
     Define the function of the SFX player.
     */

    private long sleepDuration = 600; //How long the thread's refresh rate is while audio is playing

    private double delay = 0;
    private boolean loop = false;
    private boolean playing = false;
    private boolean paused = false;
    private boolean shuffle = false;
    private boolean closeOnComplete = true;
    private boolean optimizingMemory = true; //whether or not the audio player will clear unnecessary data after using it to try and preserve memory (in theory), can be disabled if the audio will be reused
    private boolean retainingFiles = false;
    private boolean avoidRepeat = false; //whether or not the audio player will avoid repeating the same audio file when shuffling
    private MediaPlayer mediaPlayer;
    private double volume = 100;
    private LinkedList<URL> audioFile = new LinkedList<>();
    private ExecutorService threadPool = Executors.newCachedThreadPool();
    private JFXPanel fxPanel; //the FX panel, probably shouldn't be touched but w/e
    private int lastPlayed;

    private File audioFolder;

    private Runnable onComplete;
    private Runnable onAudioEnd;

    /*------------------------------------------------------------------------------------------------------------------
     Constructors.
     Used to construct instances of the SFX player.
     */

    public AudioPlayer() {}
    public AudioPlayer(File directory) { audioFolder = directory; }
    public AudioPlayer(URL a) { audioFile.add(a); }

    public AudioPlayer(URL a, double v) {
        audioFile.add(a);
        setVolume(v);
    }

    public AudioPlayer(URL a, double v, double d) {
        audioFile.add(a);
        setVolume(v);
        delay = d;
    }

    /*------------------------------------------------------------------------------------------------------------------
     Accessible methods.
     Can be accessed outside of the SFX player to edit values.
     */

    public void setAudioFolder(File folder) { audioFolder = folder; }
    public File getAudioFolder() { return audioFolder; }

    public void addAudioFromFolder(String audio) {
        if (audioFolder == null) throw new NullPointerException("Audio folder directory was not initialized.");
        try {
            File temp = new File(audioFolder + "/" + audio);
            audioFile.add(temp.toURI().toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public URL getURLFromFile(File req) { //gets the URL of a file
        try {
            return req.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addFile(URL f) { audioFile.add(f); } //adds a new audio file
    public void addFile(URL f, int i) { audioFile.add(i, f); } //adds the file to the set index
    public void addFile(URL... f) { audioFile.addAll(Arrays.asList(f)); } //adds multiple audio files
    public void addFileToFront(URL f) { audioFile.addFirst(f); }
    public void replaceFile(URL f, int i) { audioFile.set(i, f); } //replaces the file at the index
    public void removeFile(URL f) { audioFile.remove(f); } //removes a specific file
    public void removeFile(int i) { audioFile.remove(i); } //removes a file from the selected index
    public void dumpFiles() { audioFile.clear(); } //clears all loaded files

    public void trimFiles(int i1, int i2) { //trims the audio file array to between the specified indexes
        if (i1 > i2 || i1 < 0 || i2 < 0 || i1 > audioFile.size() || i2 > audioFile.size()) throw new IllegalArgumentException("Invalid arguments. Please specify a valid range defined by i1 and i2.");
        ArrayList<URL> temp = new ArrayList<>();
        temp.addAll(audioFile.subList(i1, i2));
        audioFile.clear();
        audioFile.addAll(temp);
        temp.clear();
    }

    public boolean isFileLoaded(URL f) { return audioFile.contains(f); }
    public boolean filesExist() { return !audioFile.isEmpty(); }
    public int getFileCount() { return audioFile.size(); }
    public URL getFile(URL f) { return audioFile.get(audioFile.indexOf(f)); } //gets a file
    public URL getFile(int i) { return audioFile.get(i); } //gets a file from an index
    public int getFileIndex(URL f) { return audioFile.indexOf(f); } //gets the index of a file

    public void setShuffle(boolean b) { shuffle = b; } //shuffles the audio

    public void setVolume(double vol) { //sets the volume of the audio player
        if (vol < 0) {
            volume = 0;
        } else if (vol > 100) {
            volume = 100;
        } else {
            volume = vol;
        }

        if (mediaPlayer != null) { //only attempt to alter the media player if one is initialized
            mediaPlayer.setVolume(0.01 * volume);
        }
    }

    public void setDelay(double d) { delay = d; } //sets the delay between initialization and playing

    public double getVolume() { return volume; } //gets the volume of the audio player
    public double getDuration() { return mediaPlayer.getTotalDuration().toMillis(); } //gets the duration of the current audio
    public double getDelay() { return delay; } //gets the currently selected delay

    public void seek(Duration d) { mediaPlayer.seek(d); }

    public void pause() { //pauses the currently playing audio
        if (playing && mediaPlayer != null) {
            mediaPlayer.pause();
            paused = true;
            playing = false;
        }
    }

    public void resume() { //resumes the currently playing audio
        if (paused && mediaPlayer != null) {
            mediaPlayer.play();
            playing = true;
            paused = false;
        }
    }

    public void stop() { //stops the audio
        mediaPlayer.stop();
        playing = false;
        paused = false;
    }

    public void loop() { //loops the audio
        loop = true;
        if (mediaPlayer != null) { audioLoop(); }
    }

    public void toggleMute() { mediaPlayer.setMute(!mediaPlayer.isMute()); }

    public boolean isPlaying() { return playing; } //whether or not there's any audio playing
    public boolean isLooping() { return loop; } //whether or not the audio is looping
    public boolean isPaused() { return paused; } //whether or not the audio is paused
    public boolean isShuffling() { return shuffle; } //whether or not the audio is being shuffled
    public boolean isMuted() { return mediaPlayer.isMute(); }

    public void setCloseOnComplete(boolean b) { closeOnComplete = b; } //whether or not the audio thread will clean itself up on closing
    public boolean isCloseOnComplete() { return closeOnComplete; }

    public void setOnComplete(Runnable r) { onComplete = r; } //runnable that can be called at the end after all of the audio is finished playing
    public Runnable getOnComplete() { return onComplete; }

    public void setOnAudioEnd(Runnable r) { onAudioEnd = r; } //runnable that can be called after each individual audio file finishes
    public Runnable getOnAudioEnd() { return onAudioEnd; }

    public void setFxPanel(JFXPanel p) { fxPanel = p; }
    public JFXPanel getFxPanel() { return fxPanel; }

    public MediaPlayer getMediaPlayer() { return mediaPlayer; }

    public boolean isOptimizingMemory() { return optimizingMemory; }
    public void setOptimizingMemory(boolean b) { optimizingMemory = b; }

    public void setSleepDuration(long d) { sleepDuration = d; }
    public long getSleepDuration() { return sleepDuration; }

    public Duration getCurrentTime() { return mediaPlayer.getCurrentTime(); }

    public boolean isRetainingFiles() { return retainingFiles; }
    public void setRetainingFiles(boolean b) { retainingFiles = b; }

    public double getPlaybackRate() { return mediaPlayer.getRate(); }
    public void setPlaybackRate(double d) { mediaPlayer.setRate(d); }

    public double getBalance() { return mediaPlayer.getBalance(); }
    public void setBalance(double d) { mediaPlayer.setBalance(d); }

    public boolean isAvoidRepeat() { return avoidRepeat; }
    public void setAvoidRepeat(boolean b) { avoidRepeat = b; }

    public void execute() {
        threadPool.submit(this);
    }

    public void dispose() { //attempts to clear and shut down the audio player.
        dump();
        if (mediaPlayer != null) {
            mediaPlayer.dispose();
        }
        fxPanel = null;
        threadPool.shutdown();
    }

    public void dump() {
        loop = false;
        shuffle = false;
        audioFile.clear();
        clear();
        if (mediaPlayer != null) {
            stop();
        }
    }

    @Override
    public void run() { //plays the audio
        while (!Thread.interrupted()) {
            try {
                if (playing || paused)
                    throw new RuntimeException("Invalid operation - Audio thread was accessed during execution."); //Safety net.

                loadFXPanel();

                initializeData();

                if (size() == 1) {
                    loadAudio(getFirst());
                } else if (size() > 1 && shuffle) {
                    shuffleAudio();
                } else if (size() > 1 && !shuffle && !loop) {
                    playList();
                } else if (size() < 1) {
                    throw new NullPointerException("Unexpected Error - No audio was initialized.");
                } else {
                    throw new IllegalArgumentException("Unexpected Error - Audio initialization was incorrect.");
                }

                if (onComplete != null) {
                    onComplete.run();
                } //if a runnable was set to trigger on the completion of the audio player's queue, run it

                if (closeOnComplete) {
                    clear();
                    if (!retainingFiles) {
                        audioFile.clear();
                    }
                    Thread.currentThread().interrupt(); //closes the thread down
                }
            } catch (InterruptedException e) {
                break; //exit the loop to close the thread
            }
        }
        stop();
        //System.out.println("Audio thread completed operation.");
    }

    /*------------------------------------------------------------------------------------------------------------------
     Core methods.
     Methods used by the run function when the audio is set to play. These should NOT be touched or accessed.
     */

    private void loadFXPanel() { fxPanel = new JFXPanel(); }

    private void mediaPlayerSetup() {
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                //System.out.println("Audio finished playing.");
                playing = false;
                paused = false;

                if (onAudioEnd != null) {
                    onAudioEnd.run();
                }
            }
        });
    }

    private void audioLoop() { mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); }

    private void initializeData() { //initializes the audio files from the data
        clear();

        if (audioFile.size() == 0) {
            throw new NullPointerException("Unexpected Error - No audio loaded.");
        } else {
            for (URL file : audioFile) {
                if (file != null) {
                    add(new Media(file.toString()));
                }
            }
            //for (int i = 0; i < audioFile.size(); i++) {
            //    add(new Media(audioFile.get(i).toString()));
            //}
        }

        if (optimizingMemory || !retainingFiles) {
            audioFile.clear(); //all audio files initialized, might as well dump the now useless data
        }
    }

    private void loadAudio(Media media) throws InterruptedException { //loads the audio into the media player and plays it after the delay (if one was set)
        mediaPlayer = new MediaPlayer(media);
        mediaPlayerSetup();

        if (delay > 0) { //delay inputted, pause thread before playing audio
            Thread.sleep((long)delay);
        } else if (delay < 0) { //negative delay inputted -- is this a mistake?
            throw new IllegalArgumentException("Delay cannot be a negative number.");
        }

        if (loop && !shuffle && size() <= 1) { audioLoop(); }

        mediaPlayer.setVolume(0.01 * volume);
        mediaPlayer.play();
        playing = true;

        while(playing || paused) { //Pause until the audio finishes playing
            Thread.sleep(sleepDuration);
        }

        if (optimizingMemory) {
            mediaPlayer.dispose();
        }
    }

    private void shuffleAudio() throws InterruptedException { //selects a random audio from the list and plays it, looping when applicable
        int newAudio;
        int counter = 0;
        Random shuffler = new Random();
        newAudio = shuffler.nextInt(size());
        if (avoidRepeat) {
            while (true) {
                if (newAudio != lastPlayed || counter > 6) { //retry 6 times for a different audio, otherwise abort the process and just repeat anyways
                    break;
                } else {
                    newAudio = shuffler.nextInt(size());
                }
                counter++;
            }
        }
        lastPlayed = newAudio;
        loadAudio(get(newAudio));
        if (loop) { //if the program is set to loop, choose another audio file after the current one finishes playing
            shuffleAudio();
        }
    }

    private void playList() throws InterruptedException { //plays the audio in order
        for (int i = 0; i < size(); i++) {
            loadAudio(get(i));
        }
    }

    //------------------------------------------------------------------------------------------------------------------

}
