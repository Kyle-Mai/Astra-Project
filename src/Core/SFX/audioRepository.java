package Core.SFX;

import AetheriusEngine.core.sfx.AudioPlayer;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * KM
 * May 18 2017
 * Repository for storing audio file declarations to ease the stress off of the program later on.
 */

public class audioRepository {

    private static ExecutorService audioExecutor = Executors.newCachedThreadPool();

    public static int uiVolume = 60;
    public static int musicVolume = 50;
    public static int mute = 0;
    public static int ambianceVolume = 20;
    public static int voiceVolume = 70;
    public static int effectsVolume = 50;

    private static AudioPlayer ambianceOther = null;

    private static audioCore music;
    private static audioCore ambiance;
    private static audioCore tutorial = null;

    /** UI actions **/

    public static void buttonClick() {
        AudioPlayer effect = new AudioPlayer();
        effect.setAudioFolder(new File(System.getProperty("user.dir") + "/src/Core/SFX/Resources/ui/"));
        effect.addAudioFromFolder("menu_press.wav");
        effect.setVolume(uiVolume);
        audioExecutor.submit(effect);
    }

    public static void buttonSelect() {
        AudioPlayer effect = new AudioPlayer();
        effect.setAudioFolder(new File(System.getProperty("user.dir") + "/src/Core/SFX/Resources/ui/"));
        effect.addAudioFromFolder("menu_select.wav");
        effect.setVolume(uiVolume);
        audioExecutor.submit(effect);
    }

    public static void buttonDisable() {
        AudioPlayer effect = new AudioPlayer();
        effect.setAudioFolder(new File(System.getProperty("user.dir") + "/src/Core/SFX/Resources/ui/"));
        effect.addAudioFromFolder("menu_close.wav");
        effect.setVolume(uiVolume);
        audioExecutor.submit(effect);
    }

    public static void menuTab() {
        AudioPlayer effect = new AudioPlayer();
        effect.setAudioFolder(new File(System.getProperty("user.dir") + "/src/Core/SFX/Resources/ui/"));
        effect.addAudioFromFolder("menu_tab.wav");
        effect.setVolume(uiVolume);
        audioExecutor.submit(effect);
    }

    public static void menuTab2() {
        AudioPlayer effect = new AudioPlayer();
        effect.setAudioFolder(new File(System.getProperty("user.dir") + "/src/Core/SFX/Resources/ui/"));
        effect.addAudioFromFolder("menu_tab2.wav");
        effect.setVolume(uiVolume);
        audioExecutor.submit(effect);
    }

    public static void buttonConfirm() {
        AudioPlayer effect = new AudioPlayer();
        effect.setAudioFolder(new File(System.getProperty("user.dir") + "/src/Core/SFX/Resources/ui/"));
        effect.addAudioFromFolder("menu_confirm.wav");
        effect.setVolume(uiVolume);
        audioExecutor.submit(effect);
    }

    public static void buttonHighlight() {
        AudioPlayer effect = new AudioPlayer();
        effect.setAudioFolder(new File(System.getProperty("user.dir") + "/src/Core/SFX/Resources/ui/"));
        effect.addAudioFromFolder("menu_highlight.wav");
        effect.setVolume(uiVolume);
        audioExecutor.submit(effect);
    }

    public static void gamePaused() {
        AudioPlayer effect = new AudioPlayer();
        effect.setAudioFolder(new File(System.getProperty("user.dir") + "/src/Core/SFX/Resources/ui/"));
        effect.addAudioFromFolder("pause.wav");
        effect.setVolume(uiVolume);
        audioExecutor.submit(effect);
    }

    public static void gameSlower() {
        AudioPlayer effect = new AudioPlayer();
        effect.setAudioFolder(new File(System.getProperty("user.dir") + "/src/Core/SFX/Resources/ui/"));
        effect.addAudioFromFolder("time_slow.wav");
        effect.setVolume(uiVolume);
        audioExecutor.submit(effect);
    }

    public static void gameFaster() {
        AudioPlayer effect = new AudioPlayer();
        effect.setAudioFolder(new File(System.getProperty("user.dir") + "/src/Core/SFX/Resources/ui/"));
        effect.addAudioFromFolder("time_speed.wav");
        effect.setVolume(uiVolume);
        audioExecutor.submit(effect);
    }

    public static void gameInvalid() {
        AudioPlayer effect = new AudioPlayer();
        effect.setAudioFolder(new File(System.getProperty("user.dir") + "/src/Core/SFX/Resources/ui/"));
        effect.addAudioFromFolder("invalid_action.wav");
        effect.setVolume(uiVolume);
        audioExecutor.submit(effect);
    }

    public static void repairNotification() {
        AudioPlayer effect = new AudioPlayer();
        effect.setAudioFolder(new File(System.getProperty("user.dir") + "/src/Core/SFX/Resources/ui/"));
        effect.addAudioFromFolder("notification_repair.wav");
        effect.setVolume(uiVolume);
        audioExecutor.submit(effect);
    }

    public static void startResearch() {
        AudioPlayer effect = new AudioPlayer();
        effect.setAudioFolder(new File(System.getProperty("user.dir") + "/src/Core/SFX/Resources/ui/"));
        effect.addAudioFromFolder("start_research_01.wav");
        effect.setVolume(uiVolume);
        audioExecutor.submit(effect);
    }

    public static void constructShip() {
        AudioPlayer effect = new AudioPlayer();
        effect.setAudioFolder(new File(System.getProperty("user.dir") + "/src/Core/SFX/Resources/ui/"));
        effect.addAudioFromFolder("queuing_ship_01.wav");
        effect.setVolume(uiVolume);
        audioExecutor.submit(effect);
    }

    public static void constructionComplete() {
        AudioPlayer effect = new AudioPlayer();
        effect.setAudioFolder(new File(System.getProperty("user.dir") + "/src/Core/SFX/Resources/ui/"));
        effect.addAudioFromFolder("construction_complete.wav");
        effect.setVolume(uiVolume);
        audioExecutor.submit(effect);
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
        ambiance = new audioCore("/ambiance/space_ambient01.wav", ambianceVolume, true);
        ambiance.start();
    }

    public static void planetAmbiance(int planetID) {

        int toplay = planetID - 1999;

        if (ambianceOther != null) {
            ambianceOther.dump();
        } else {
            ambianceOther = new AudioPlayer();
        }

        ambianceOther.setAudioFolder(new File(System.getProperty("user.dir") + "/src/Core/SFX/Resources/ambiance/"));

        switch (toplay) {
            case 1:
                ambianceOther.addAudioFromFolder("continental_planet.wav");
                break;
            case 2:
                ambianceOther.addAudioFromFolder("ocean_planet.wav");
                break;
            case 3:
                ambianceOther.addAudioFromFolder("ocean_planet.wav");
                break;
            case 4:
                ambianceOther.addAudioFromFolder("alpine_planet.wav");
                break;
            case 5:
                ambianceOther.addAudioFromFolder("tundra_planet.wav");
                break;
            case 6:
                ambianceOther.addAudioFromFolder("barren_cold.wav");
                break;
            case 7:
                ambianceOther.addAudioFromFolder("desert_planet.wav");
                break;
            case 8:
                ambianceOther.addAudioFromFolder("tropical_planet.wav");
                break;
            case 9:
                ambianceOther.addAudioFromFolder("tropical_planet.wav");
                break;
            case 10:
                ambianceOther.addAudioFromFolder("toxic_planet.wav");
                break;
            case 11:
                ambianceOther.addAudioFromFolder("frozen_planet.wav");
                break;
            case 12:
                ambianceOther.addAudioFromFolder("molten_planet.wav");
                break;
            case 13:
                ambianceOther.addAudioFromFolder("storm_planet.wav");
                break;
            case 14:
                ambianceOther.addAudioFromFolder("gas_giant.wav");
                break;
            case 16:
                ambianceOther.addAudioFromFolder("nuked_planet.wav");
                break;
            case 17:
                ambianceOther.addAudioFromFolder("desert_planet.wav");
                break;
            case 18:
                ambianceOther.addAudioFromFolder("radiation_planet.wav");
                break;
            case 21:
                ambianceOther.addAudioFromFolder("gaia_planet.wav");
                break;
            case 22:
                ambianceOther.addAudioFromFolder("gas_giant.wav");
                break;
            default:
                ambianceOther.addAudioFromFolder("barren_planet.wav");
                break;
        }
        ambianceOther.setVolume(ambianceVolume);
        audioExecutor.submit(ambianceOther);
    }

    public static void starSound() {
        if (ambianceOther != null) {
            ambianceOther.dump();
        } else {
            ambianceOther = new AudioPlayer();
        }

        ambianceOther.setAudioFolder(new File(System.getProperty("user.dir") + "/src/Core/SFX/Resources/ambiance/"));

        ambianceOther.addAudioFromFolder("solar_fusion_01.wav");
        ambianceOther.setVolume(ambianceVolume);
        audioExecutor.submit(ambianceOther);
    }

    /** ship audio **/

    public static void shipScienceSelect() {
        audioCore buttonPress = new audioCore("/ship/select_science.wav", uiVolume, 0, 1500);
        buttonPress.start();
    }

    public static void shipScienceMove() {
        audioCore buttonPress = new audioCore("/ship/move_science.wav", uiVolume, 0, 1500);
        buttonPress.start();
    }

    /** Advisor audio **/

    public static void tutorial_01() {
        if (tutorial != null) { //stop tutorial audio thread if it's playing already
            tutorial.interrupt();
        }
        tutorial = new audioCore("/advisor/tutorial_greeting.wav", voiceVolume);
        tutorial.start();
    }

    public static void tutorial_02() {
        if (tutorial != null) { //stop tutorial audio thread if it's playing already
            tutorial.interrupt();
        }
        tutorial = new audioCore("/advisor/tut_survey_intro_01.wav", voiceVolume);
        tutorial.start();
    }

    public static void tutorial_03() {
        if (tutorial != null) { //stop tutorial audio thread if it's playing already
            tutorial.interrupt();
        }
        tutorial = new audioCore("/advisor/tut_technologies_intro_01.wav", voiceVolume);
        tutorial.start();
    }

    public static void tutorial_04() {
        if (tutorial != null) { //stop tutorial audio thread if it's playing already
            tutorial.interrupt();
        }
        tutorial = new audioCore("/advisor/tut_technologies_success_01.wav", voiceVolume);
        tutorial.start();
    }

    public static void tutorial_off() {
        Random r = new Random();
        int opt = 1 + r.nextInt(6); //pick the audio to play

        if (tutorial != null) { //stop tutorial audio thread if it's playing already
            tutorial.interrupt();
        }

        switch (opt) {
            case 1:
                tutorial = new audioCore("/advisor/no_tut_advior_01.wav", voiceVolume, 0, 2200);
                break;
            case 2:
                tutorial = new audioCore("/advisor/no_tut_advior_02.wav", voiceVolume, 0, 2200);
                break;
            case 3:
                tutorial = new audioCore("/advisor/no_tut_advior_03.wav", voiceVolume, 0, 2200);
                break;
            case 4:
                tutorial = new audioCore("/advisor/no_tut_advior_04.wav", voiceVolume, 0, 2200);
                break;
            case 5:
                tutorial = new audioCore("/advisor/no_tut_advior_05.wav", voiceVolume, 0, 2200);
                break;
            case 6:
                tutorial = new audioCore("/advisor/no_tut_advior_06.wav", voiceVolume, 0, 2200);
                break;
            default:
                tutorial = new audioCore("/advisor/no_tut_advior_07.wav", voiceVolume, 0, 2200);
                break;
        }

        tutorial.start();
    }

    public static void announce_researchComplete() {
        audioCore announcer = new audioCore("/advisor/research_complete.wav", voiceVolume, 0, 2000);
        announcer.start();
    }

    public static void event_conversation() {
        audioCore event = new audioCore("/event/event_conversation.wav", uiVolume, 0, 5000);
        event.start();
    }


}
