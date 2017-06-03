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
    private static audioCore ambiance_other = null;

    /** UI actions **/

    public static void buttonClick() {
        audioCore buttonPress = new audioCore("/ui/menu_press.wav", uiVolume, 0, 400);
        buttonPress.start();
    }

    public static void buttonSelect() {
        audioCore buttonPress = new audioCore("/ui/menu_select.wav", uiVolume, 0, 400);
        buttonPress.start();
    }

    public static void buttonDisable() {
        audioCore buttonPress = new audioCore("/ui/menu_close.wav", uiVolume, 0, 500);
        buttonPress.start();
    }

    public static void menuTab() {
        audioCore buttonPress = new audioCore("/ui/menu_tab.wav", uiVolume, 0, 120);
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

    public static void buttonHighlight() {
        audioCore buttonPress = new audioCore("/ui/menu_highlight.wav", uiVolume, 0, 200);
        buttonPress.start();
    }

    public static void gamePaused() {
        audioCore buttonPress = new audioCore("/ui/pause.wav", uiVolume, 0, 350);
        buttonPress.start();
    }

    public static void gameSlower() {
        audioCore buttonPress = new audioCore("/ui/time_slow.wav", uiVolume, 0, 300);
        buttonPress.start();
    }

    public static void gameFaster() {
        audioCore buttonPress = new audioCore("/ui/time_speed.wav", uiVolume, 0, 300);
        buttonPress.start();
    }

    public static void gameInvalid() {
        audioCore buttonPress = new audioCore("/ui/invalid_action.wav", uiVolume, 0, 300);
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
        music = new audioCore("/music/towards_utopia.mp3", musicVolume, true);
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

    public static void planetAmbiance(int planetID) {

        int toplay = planetID - 1999;

        if (ambiance_other != null) {
            ambiance_other.stopAudio();
            ambiance_other.interrupt();
        }

        switch (toplay) {
            case 1:
                ambiance_other = new audioCore("/ambiance/continental_planet.wav", audioRepository.ambianceVolume, 0, 2600);
                break;
            case 2:
                ambiance_other = new audioCore("/ambiance/ocean_planet.wav", audioRepository.ambianceVolume, 0, 2600);
                break;
            case 3:
                ambiance_other = new audioCore("/ambiance/ocean_planet.wav", audioRepository.ambianceVolume, 0, 2600);
                break;
            case 4:
                ambiance_other = new audioCore("/ambiance/alpine_planet.wav", audioRepository.ambianceVolume, 0, 6600);
                break;
            case 5:
                ambiance_other = new audioCore("/ambiance/tundra_planet.wav", audioRepository.ambianceVolume, 0, 2600);
                break;
            case 6:
                ambiance_other = new audioCore("/ambiance/barren_cold.wav", audioRepository.ambianceVolume, 0, 2600);
                break;
            case 7:
                ambiance_other = new audioCore("/ambiance/desert_planet.wav", audioRepository.ambianceVolume, 0, 2600);
                break;
            case 8:
                ambiance_other = new audioCore("/ambiance/tropical_planet.wav", audioRepository.ambianceVolume, 0, 2400);
                break;
            case 9:
                ambiance_other = new audioCore("/ambiance/tropical_planet.wav", audioRepository.ambianceVolume, 0, 2400);
                break;
            case 10:
                ambiance_other = new audioCore("/ambiance/toxic_planet.wav", audioRepository.ambianceVolume, 0, 2700);
                break;
            case 11:
                ambiance_other = new audioCore("/ambiance/frozen_planet.wav", audioRepository.ambianceVolume, 0, 2600);
                break;
            case 12:
                ambiance_other = new audioCore("/ambiance/molten_planet.wav", audioRepository.ambianceVolume, 0, 2600);
                break;
            case 13:
                ambiance_other = new audioCore("/ambiance/storm_planet.wav", audioRepository.ambianceVolume, 0, 2600);
                break;
            case 14:
                ambiance_other = new audioCore("/ambiance/gas_giant.wav", audioRepository.ambianceVolume, 0, 3600);
                break;
            case 16:
                ambiance_other = new audioCore("/ambiance/nuked_planet.wav", audioRepository.ambianceVolume, 0, 2600);
                break;
            case 17:
                ambiance_other = new audioCore("/ambiance/desert_planet.wav", audioRepository.ambianceVolume, 0, 2600);
                break;
            case 18:
                ambiance_other = new audioCore("/ambiance/radiation_planet.wav", audioRepository.ambianceVolume, 0, 2600);
                break;
            case 21:
                ambiance_other = new audioCore("/ambiance/gaia_planet.wav", audioRepository.ambianceVolume, 0, 2600);
                break;
            case 22:
                ambiance_other = new audioCore("/ambiance/gas_giant.wav", audioRepository.ambianceVolume, 0, 2600);
                break;
            default:
                ambiance_other = new audioCore("/ambiance/barren_planet.wav", audioRepository.ambianceVolume, 0, 2600);
                break;
        }

        ambiance_other.start();
    }

    public static void starSound() {
        ambiance_other = new audioCore("/ambiance/solar_fusion_01.wav", audioRepository.ambianceVolume, 0, 5500);
        ambiance_other.start();

    }

    public static void planetHot() {
        if (ambiance_other != null) {
            ambiance_other.stopAudio();
            ambiance_other.interrupt();
        }
        ambiance_other = new audioCore("/ambiance/molten_planet.wav", audioRepository.ambianceVolume, 0, 2600);
        ambiance_other.start();

    }

    public static void planetGas() {
        if (ambiance_other != null) {
            ambiance_other.stopAudio();
            ambiance_other.interrupt();
        }
        ambiance_other = new audioCore("/ambiance/gas_giant.wav", audioRepository.ambianceVolume, 0, 2600);
        ambiance_other.start();

    }

    public static void planetCold() {
        if (ambiance_other != null) {
            ambiance_other.stopAudio();
            ambiance_other.interrupt();
        }
        ambiance_other = new audioCore("/ambiance/frozen_planet.wav", audioRepository.ambianceVolume, 0, 2600);
        ambiance_other.start();

    }

    public static void planetStorm() {
        if (ambiance_other != null) {
            ambiance_other.stopAudio();
            ambiance_other.interrupt();
        }
        ambiance_other = new audioCore("/ambiance/storm_planet.wav", audioRepository.ambianceVolume, 0, 2600);
        ambiance_other.start();

    }

    public static void planetHabitable() {
        if (ambiance_other != null) {
            ambiance_other.stopAudio();
            ambiance_other.interrupt();
        }
        ambiance_other = new audioCore("/ambiance/continental_planet.wav", audioRepository.ambianceVolume, 0, 2600);
        ambiance_other.start();

    }

    public static void planetBarren() {
        if (ambiance_other != null) {
            ambiance_other.stopAudio();
            ambiance_other.interrupt();
        }
        ambiance_other = new audioCore("/ambiance/barren_planet.wav", audioRepository.ambianceVolume, 0, 2600);
        ambiance_other.start();

    }


}
