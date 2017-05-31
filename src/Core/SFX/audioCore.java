package Core.SFX;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.Random;

/**
 * KM
 * May 18 2017
 * Handles the loading and playing of audio files.
 *
 * SOURCES:
 *  Stack Overflow - Handling of playing/initializing audio.
 */

public class audioCore extends Thread {

    private String sound;
    private double volume;
    private double delay;
    private double duration;
    private boolean loop = false;
    private boolean shuffleType = false;
    private MediaPlayer mediaPlayer;

    static { JFXPanel fxPanel = new JFXPanel(); } //needed to play audio, apparently

    @Override
    public void run() {

        if (shuffleType) { //shuffling music
            shuffleMusic();

        } else { //not shuffling music
            initializeAudioData(); //sets up the audio file

            if (loop) {
                loopAudio(); //if loop is enabled, loop the audio
            } else {
                playAudio(); //otherwise, just play the audio file
            }
        }

        Thread.currentThread().interrupt(); //closes the thread down
    }

    private void initializeAudioData() { //initializes the audio file's data

        URL file = this.getClass().getResource("Resources/" + this.sound);
        //System.out.println("Playing audio file : " + file);

        Media media = new Media(file.toString()); //gets the media from file
        mediaPlayer = new MediaPlayer(media); //loads the media into the media player

        try { //pause and wait for the mediaplayer to initialize
            Thread.sleep((long)delay);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        setVolume((int)volume);

        if (this.duration == 0) {
            duration = mediaPlayer.getTotalDuration().toMillis();
        }

    }

    private void playAudio() { //plays the audio

        mediaPlayer.play(); //plays the audio

        try {
            //System.out.println("Closing audio thread when music ends in " + ((long)duration / 1000) + " s");
            Thread.sleep((long)duration);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mediaPlayer.stop();

    }

    private void loopAudio() { //loops the audio

        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();

    }

    public void setVolume(int volume) { //sets the volume of the audio
        this.volume = volume;
        mediaPlayer.setVolume(0.01 * this.volume);

    }

    private void shuffleMusic() { //shuffles the music from an array of audio
        Random newMusic = new Random();
        String[] musicList = {"music/imperial_fleet.mp3", "music/new_dawn.mp3", "music/in_search_of_life.mp3", "music/mercedes_romero.mp3", "music/spatial_lullaby.mp3", "music/towards_utopia.mp3", "music/to_the_ends_of_the_galaxy.mp3", "music/gravitational_constant.mp3", "music/assembling_the_fleet.mp3", "music/cradle_of_the_galaxy.mp3"};

        int musicToPlay = newMusic.nextInt(musicList.length - 1);

        this.sound = musicList[musicToPlay];

        initializeAudioData();
        playAudio();

        mediaPlayer = null; //attempt to reset media player

        shuffleMusic();
    }

    public void stopAudio() { //stops the audio
        mediaPlayer.stop();
    }

    //constructors

    public audioCore(String toPlay, double volumeLevel) {
        this.sound = toPlay;
        this.volume = volumeLevel;
        this.delay = 1000;
        this.duration = 0;

    }

    public audioCore(String toPlay, double volumeLevel, double delay) {
        this.sound = toPlay;
        this.volume = volumeLevel;
        this.delay = delay;
        this.duration = 0;

    }

    public audioCore(String toPlay, double volumeLevel, double delay, double duration) {
        this.sound = toPlay;
        this.volume = volumeLevel;
        this.delay = delay;
        this.duration = duration;

    }

    public audioCore(String toPlay, double volumeLevel, boolean loop) {
        this.sound = toPlay;
        this.volume = volumeLevel;
        this.delay = 1000;
        this.duration = 0;
        this.loop = loop;

    }

    public audioCore(double volumeLevel, boolean shuffling) {
        this.volume = volumeLevel;
        this.delay = 1000;
        this.duration = 0;
        this.shuffleType = shuffling;

    }


}