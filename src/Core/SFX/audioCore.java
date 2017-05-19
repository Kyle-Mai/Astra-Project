package Core.SFX;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

/**
 * KM
 * May 18 2017
 * Handles the loading and playing of audio files.
 */

public class audioCore extends Thread {

    String sound;
    double volume;
    double delay;
    double duration;

    static { JFXPanel fxPanel = new JFXPanel(); }

    @Override
    public void run() {

        URL file = this.getClass().getResource("Resources/" + this.sound);

        System.out.println("Playing audio file : " + file);

        final Media media = new Media(file.toString());
        final MediaPlayer mediaPlayer = new MediaPlayer(media);

        try { //pause and wait for the mediaplayer to initialize
            Thread.sleep((long)delay);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        mediaPlayer.setVolume(0.01 * this.volume);

        if (this.duration == 0) {
            duration = mediaPlayer.getTotalDuration().toMillis();
        }
        long longDuration = (new Double(duration)).longValue();

        mediaPlayer.play(); //plays the audio

        try {
            System.out.println("Closing audio thread when music ends in " + (longDuration / 1000) + " s");
            Thread.sleep(longDuration);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mediaPlayer.stop();
        System.out.println("Audio thread closed.");

        Thread.currentThread().interrupt(); //closes the thread down
    }

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



}