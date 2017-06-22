package Core.SFX;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * KM
 * June 22 2017
 * Plays and manages audio files.
 */

public class sfxPlayer extends Thread {

    //------------------------------------------------------------------------------------------------------------------
    /**
     Variables.
     Define the function of the SFX player.
     */

    private double delay = 0;
    private boolean loop = false;
    private boolean shuffle = false;
    private MediaPlayer mediaPlayer;
    private ArrayList<File> audioFile = new ArrayList<>();
    private ArrayList<Media> audio = new ArrayList<>();
    static { JFXPanel fxPanel = new JFXPanel(); } //needed to play audio, apparently

    //------------------------------------------------------------------------------------------------------------------
    /**
     Constructors.
     Used to construct instances of the SFX player.
     */

    public sfxPlayer() {}

    public sfxPlayer(File audio) {audioFile.add(audio); }

    public sfxPlayer(File audio, double volume) {
        audioFile.add(audio);
        setVolume(volume);
    }

    public sfxPlayer(File audio, double volume, double delay) {
        audioFile.add(audio);
        setVolume(volume);
        this.delay = delay;
    }

    //------------------------------------------------------------------------------------------------------------------
    /**
     Accessible methods.
     Can be accessed outside of the SFX player to edit values.
     */

    public void addFile(File... file) { audioFile.addAll(Arrays.asList(file)); }
    public void removeFile(File f) { audioFile.remove(f); }
    public void removeFile(int i) { audioFile.remove(i); }

    public File getFile(File f) { return audioFile.get(audioFile.indexOf(f)); }
    public File getFile(int i) { return audioFile.get(i); }
    public int getFileIndex(File f) { return audioFile.indexOf(f); }

    public void setVolume(double volume) {mediaPlayer.setVolume(0.01 * volume); }
    public void setDelay(double delay) { this.delay = delay; }

    public double getVolume() { return mediaPlayer.getVolume() * 100; }
    public double getDuration() { return mediaPlayer.getTotalDuration().toMillis(); }
    public double getDelay() { return delay; }

    public void loop() { mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); }
    public boolean isLooping() { return loop; }
    public void shuffle() { this.shuffle = true; }
    public boolean isShuffling() { return shuffle; }

    public void stopAudio() { mediaPlayer.stop(); }

    //------------------------------------------------------------------------------------------------------------------

    /**
     Run methods.
     Methods used by the run function when the audio is set to play.
     */

    @Override
    public void run() {
        initializeData();

        if (loop && !shuffle && audio.size() <= 1) { loop(); }

        if (audio.size() == 1) {
            loadAudio(audio.get(0));
        } else if (audio.size() > 1 && shuffle) {
            shuffleAudio();
        } else if (audio.size() > 1 && loop) {
            playList();
        } else {
            System.out.println("[SFX](run) Unexpected Error - Audio initialization was incorrect.");
        }

        currentThread().interrupt(); //closes the thread down
    }

    private void initializeData() {
        audio.clear();

        if (audioFile.size() == 0) {
            System.out.println("[SFX](initData) Unexpected Error - No audio loaded. Discarding audio player.");
            currentThread().interrupt();
        } else {
            for (int i = 0; i < audioFile.size(); i++) {
                audio.add(new Media(audioFile.get(i).toString()));
            }
        }
    }

    private void loadAudio(Media media) {
        mediaPlayer = new MediaPlayer(media);
        try {
            sleep((long)delay);
        } catch (InterruptedException e) {
            currentThread().interrupt();
        }
        mediaPlayer.play();
    }

    private void shuffleAudio() {
        Random shuffler = new Random();
        loadAudio(audio.get(shuffler.nextInt(audio.size() - 1)));
        if (loop) {
            shuffleAudio();
        }
    }

    private void playList() {
        for (int i = 0; i < audio.size(); i++) {
            loadAudio(audio.get(i));
        }
    }

    //------------------------------------------------------------------------------------------------------------------

}
