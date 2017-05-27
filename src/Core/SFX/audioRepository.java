package Core.SFX;

/**
 * KM
 * May 18 2017
 * Repository for storing audio file declarations to ease the stress off of the program later on.
 */

public class audioRepository {


    public static int uiVolume = 60;
    public static int musicVolume = 50;
    public static int mute = 0;
    public static int ambianceVolume = 20;
    public static int voiceVolume = 70;
    public static int effectsVolume = 50;

    private static audioCore music;
    private static audioCore ambiance;

    /** UI actions **/

    public static void buttonClick() {
        audioCore buttonPress = new audioCore("/ui/menu_press.wav", uiVolume, 0, 500);
        buttonPress.start();
    }

    public static void buttonSelect() {
        audioCore buttonPress = new audioCore("/ui/menu_select.wav", uiVolume, 0, 500);
        buttonPress.start();
    }

    public static void buttonDisable() {
        audioCore buttonPress = new audioCore("/ui/menu_close.wav", uiVolume, 0, 500);
        buttonPress.start();
    }

    public static void menuTab() {
        audioCore buttonPress = new audioCore("/ui/menu_tab.wav", uiVolume, 0, 100);
        buttonPress.start();
    }

    public static void menuTab2() {
        audioCore buttonPress = new audioCore("/ui/menu_tab2.wav", uiVolume, 0, 100);
        buttonPress.start();
    }

    public static void buttonConfirm() {
        audioCore buttonPress = new audioCore("/ui/menu_confirm.wav", uiVolume, 0, 500);
        buttonPress.start();
    }

    /** Music **/

    public static void setMusicVolume() { //changes the volume of the music
        music.setVolume(musicVolume);
    }

    public static void muteMusic() { music.setVolume(mute); }

    public static void musicTitleScreen() { //plays the title screen music
        music.stopAudio();
        music.interrupt();
        music = new audioCore("/music/creation_and_beyond.mp3", musicVolume, true);
        music.start();
    }

    public static void musicLauncherScreen() { //plays the launcher screen music
        music = new audioCore("/music/new_dawn.mp3", musicVolume, true);
        music.start();
    }

    public static void musicShuffle() {
        music.stopAudio();
        music.interrupt();
        music = new audioCore(musicVolume, true);
        music.start();
    }

    /** Ambiance **/

    public static void setAmbianceVolume() {
        ambiance.setVolume(ambianceVolume);
    }

    public static void ambianceMainGame() {
        ambiance = new audioCore("/ambiance/space_ambient01.wav", audioRepository.ambianceVolume, true);
        ambiance.start();
    }


}
