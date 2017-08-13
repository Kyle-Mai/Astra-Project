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
    private static AudioPlayer ambianceMain;
    private static AudioPlayer voiceTutorial = null;
    private static AudioPlayer musicPlayer;

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
        musicPlayer.setVolume(musicVolume);
    }

    public static void muteMusic() { musicPlayer.setVolume(mute); }

    public static void musicTitleScreen() { //plays the title screen music
        musicPlayer.dump();
        musicPlayer.setAudioFolder(new File(System.getProperty("user.dir") + "/src/Core/SFX/Resources/music/"));
        musicPlayer.addAudioFromFolder("towards_utopia.mp3");
        musicPlayer.setVolume(musicVolume);
        musicPlayer.loop();
        audioExecutor.submit(musicPlayer);
    }

    public static void musicLauncherScreen() { //plays the launcher screen music
        musicPlayer = new AudioPlayer();
        musicPlayer.setAudioFolder(new File(System.getProperty("user.dir") + "/src/Core/SFX/Resources/music/"));
        musicPlayer.addAudioFromFolder("new_dawn.mp3");
        musicPlayer.setVolume(musicVolume);
        musicPlayer.loop();
        audioExecutor.submit(musicPlayer);
    }

    public static void musicShuffle() {
        musicPlayer.dump();
        musicPlayer.setAudioFolder(new File(System.getProperty("user.dir") + "/src/Core/SFX/Resources/music/"));
        musicPlayer.addAudioFromFolder("imperial_fleet.mp3");
        musicPlayer.addAudioFromFolder("new_dawn.mp3");
        musicPlayer.addAudioFromFolder("in_search_of_life.mp3");
        musicPlayer.addAudioFromFolder("mercedes_romero.mp3");
        musicPlayer.addAudioFromFolder("spatial_lullaby.mp3");
        musicPlayer.addAudioFromFolder("towards_utopia.mp3");
        musicPlayer.addAudioFromFolder("to_the_ends_of_the_galaxy.mp3");
        musicPlayer.addAudioFromFolder("gravitational_constant.mp3");
        musicPlayer.addAudioFromFolder("assembling_the_fleet.mp3");
        musicPlayer.addAudioFromFolder("cradle_of_the_galaxy.mp3");
        musicPlayer.setVolume(musicVolume);
        musicPlayer.setShuffle(true);
        musicPlayer.loop();
        audioExecutor.submit(musicPlayer);
    }

    /** Ambiance **/

    public static void setAmbianceVolume() {
        ambianceMain.setVolume(ambianceVolume);
    }

    public static void ambianceMainGame() {
        ambianceMain = new AudioPlayer();
        ambianceMain.setAudioFolder(new File(System.getProperty("user.dir") + "/src/Core/SFX/Resources/ambiance/"));
        ambianceMain.addAudioFromFolder("space_ambient01.wav");
        ambianceMain.setVolume(ambianceVolume);
        ambianceMain.loop();
        audioExecutor.submit(ambianceMain);
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
        AudioPlayer effect = new AudioPlayer();
        effect.setAudioFolder(new File(System.getProperty("user.dir") + "/src/Core/SFX/Resources/ship/"));
        effect.addAudioFromFolder("select_science.wav");
        effect.setVolume(uiVolume);
        audioExecutor.submit(effect);
    }

    public static void shipScienceMove() {
        AudioPlayer effect = new AudioPlayer();
        effect.setAudioFolder(new File(System.getProperty("user.dir") + "/src/Core/SFX/Resources/ship/"));
        effect.addAudioFromFolder("move_science.wav");
        effect.setVolume(uiVolume);
        audioExecutor.submit(effect);
    }

    /** Advisor audio **/

    public static void tutorial_01() {
        if (voiceTutorial != null) { //stop tutorial audio thread if it's playing already
            voiceTutorial.dump();
        } else {
            voiceTutorial = new AudioPlayer();
        }
        voiceTutorial.setAudioFolder(new File(System.getProperty("user.dir") + "/src/Core/SFX/Resources/advisor/"));
        voiceTutorial.setVolume(voiceVolume);
        voiceTutorial.addAudioFromFolder("tutorial_greeting.wav");
        audioExecutor.submit(voiceTutorial);
    }

    public static void tutorial_02() {
        if (voiceTutorial != null) { //stop tutorial audio thread if it's playing already
            voiceTutorial.dump();
        } else {
            voiceTutorial = new AudioPlayer();
        }
        voiceTutorial.setAudioFolder(new File(System.getProperty("user.dir") + "/src/Core/SFX/Resources/advisor/"));
        voiceTutorial.setVolume(voiceVolume);
        voiceTutorial.addAudioFromFolder("tut_survey_intro_01.wav");
        audioExecutor.submit(voiceTutorial);
    }

    public static void tutorial_03() {
        if (voiceTutorial != null) { //stop tutorial audio thread if it's playing already
            voiceTutorial.dump();
        } else {
            voiceTutorial = new AudioPlayer();
        }
        voiceTutorial.setAudioFolder(new File(System.getProperty("user.dir") + "/src/Core/SFX/Resources/advisor/"));
        voiceTutorial.setVolume(voiceVolume);
        voiceTutorial.addAudioFromFolder("tut_technologies_intro_01.wav");
        audioExecutor.submit(voiceTutorial);
    }

    public static void tutorial_04() {
        if (voiceTutorial != null) { //stop tutorial audio thread if it's playing already
            voiceTutorial.dump();
        } else {
            voiceTutorial = new AudioPlayer();
        }
        voiceTutorial.setAudioFolder(new File(System.getProperty("user.dir") + "/src/Core/SFX/Resources/advisor/"));
        voiceTutorial.setVolume(voiceVolume);
        voiceTutorial.addAudioFromFolder("tut_technologies_success_01.wav");
        audioExecutor.submit(voiceTutorial);
    }

    public static void tutorial_off() {
        Random r = new Random();
        int opt = 1 + r.nextInt(6); //pick the audio to play

        if (voiceTutorial != null) { //stop tutorial audio thread if it's playing already
            voiceTutorial.dump();
        } else {
            voiceTutorial = new AudioPlayer();
        }

        voiceTutorial.setAudioFolder(new File(System.getProperty("user.dir") + "/src/Core/SFX/Resources/advisor/"));

        switch (opt) {
            case 1:
                voiceTutorial.addAudioFromFolder("no_tut_advior_01.wav");
                break;
            case 2:
                voiceTutorial.addAudioFromFolder("no_tut_advior_02.wav");
                break;
            case 3:
                voiceTutorial.addAudioFromFolder("no_tut_advior_03.wav");
                break;
            case 4:
                voiceTutorial.addAudioFromFolder("no_tut_advior_04.wav");
                break;
            case 5:
                voiceTutorial.addAudioFromFolder("no_tut_advior_05.wav");
                break;
            case 6:
                voiceTutorial.addAudioFromFolder("no_tut_advior_06.wav");
                break;
            default:
                voiceTutorial.addAudioFromFolder("no_tut_advior_07.wav");
                break;
        }
        voiceTutorial.setVolume(voiceVolume);
        audioExecutor.submit(voiceTutorial);
    }

    public static void announce_researchComplete() {
        AudioPlayer effect = new AudioPlayer();
        effect.setAudioFolder(new File(System.getProperty("user.dir") + "/src/Core/SFX/Resources/advisor/"));
        effect.addAudioFromFolder("research_complete.wav");
        effect.setVolume(voiceVolume);
        audioExecutor.submit(effect);
    }

    public static void event_conversation() {
        AudioPlayer effect = new AudioPlayer();
        effect.setAudioFolder(new File(System.getProperty("user.dir") + "/src/Core/SFX/Resources/event/"));
        effect.addAudioFromFolder("event_conversation.wav");
        effect.setVolume(uiVolume);
        audioExecutor.submit(effect);
    }


}
