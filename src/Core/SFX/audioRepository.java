package Core.SFX;

import java.net.URL;
import java.util.Random;

/**
 * KM
 * May 18 2017
 * Repository for storing audio file declarations to ease the stress off of the program later on.
 */
public class audioRepository {


    public static int uiVolume = 70;
    public static int musicVolume = 60;
    public static int ambianceVolume = 20;
    public static int voiceVolume = 70;
    public static audioCore music;

    //BROKEN
    /* public static void musicPlay(String toPlay, boolean isShuffle, boolean loop) {


        //TODO: Improve efficiency whenever.

        if (!toPlay.equals("")) {
            music = new audioCore(toPlay, musicVolume, isShuffle, loop); //loads new audio
            music.start();
        } else {

            Random shuffle = new Random();
            int musicPlay = 1 + shuffle.nextInt(2);

            switch (musicPlay) { //BEWARE THE DREADED CHEESE CODING
                case 1:
                    music.loadMusic("imperial_fleet.mp3", musicVolume, true, false);
                    break;
                case 2:
                    music.loadMusic("new_dawn.mp3", musicVolume, true, false);
                    break;
                case 3:
                    music.loadMusic("in_search_of_life.mp3", musicVolume, true, false);
                    break;
                case 4:
                    music.loadMusic("mercedes_romero.mp3", musicVolume, true, false);
                    break;
                case 5:
                    music.loadMusic("spatial_lullaby.mp3", musicVolume, true, false);
                    break;
                case 6:
                    music.loadMusic("towards_utopia.mp3", musicVolume, true, false);
                    break;
                case 7:
                    music.loadMusic("to_the_ends_of_the_galaxy.mp3", musicVolume, true, false);
                    break;
                case 8:
                    music.loadMusic("gravitational_constant.mp3", musicVolume, true, false);
                    break;
                case 9:
                    music.loadMusic("assembling_the_fleet.mp3", musicVolume, true, false);
                    break;
                default: //fallback in case something bugs out
                    music.loadMusic("new_dawn.mp3", musicVolume, true, false);
                    break;
            }



        }

    }
    */

    public static void buttonClick() {
        audioCore buttonPress = new audioCore("menu_press.wav", uiVolume, 0, 500);
        buttonPress.start();
    }

    public static void buttonSelect() {
        audioCore buttonPress = new audioCore("menu_select.wav", uiVolume, 0, 500);
        buttonPress.start();
    }

    public static void buttonDisable() {
        audioCore buttonPress = new audioCore("menu_close.wav", uiVolume, 0, 500);
        buttonPress.start();
    }

    public static void menuTab() {
        audioCore buttonPress = new audioCore("menu_tab.wav", uiVolume, 0, 500);
        buttonPress.start();
    }

    public static void buttonConfirm() {
        audioCore buttonPress = new audioCore("menu_confirm.wav", uiVolume, 0, 500);
        buttonPress.start();
    }


}
