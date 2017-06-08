package Core;

import Core.GUI.guiCoreV4;
import Core.Player.playerData;
import Core.events.eventCoreV2;

/**
 * KM
 * May 21 2017
 * Stores all of the different game settings.
 */


public class gameSettings {

    //variables to store the core classes of each type to allow them to be passed around to whatever thread requests them

    public volatile static mapGenerator map;
    public volatile static playerData player;
    public volatile static guiCoreV4 ui;
    public volatile static turnTicker turn;
    public volatile static eventCoreV2 eventhandler;

    //TODO: Experiment with volatile...

    //variables for the resource abundance

    public static final int resourceAbundanceMin = 25;
    public static final int resourceAbundanceAvg = 100;
    public static final int resourceAbundanceHigh = 200;

    public static int currentResources = 100;

    //how often star systems will spawn

    public static final int starFreqMin = 35;
    public static final int starFreqAvg = 65;
    public static final int starFreqHigh = 125;

    public static int starFrequency = 65;

    //variables for the map scale

    public static final int mapScaleMin = 20;
    public static final int mapScaleAvg = 80;
    public static final int mapScaleHigh = 200;

    public static int currMapScaleX = 80;
    public static int currMapScaleY = 80;

    //variables for overall difficulty (tech progression speed, taxation, etc)

    public static final int overallDifficultyMin = 50;
    public static final int overallDifficultyAvg = 100;
    public static final int overallDifficultyHigh = 150;

    public static int currDifficulty = 100;

    //variables for time scale

    public static final int[] timeScale = {250, 1000, 1500, 2000, 2750}; //essentially how fast each turn ticks
    public static final String[] timeLocale = {"Fastest", "Fast", "Normal", "Slow", "Slowest"};
    public static int currentTime = 2; //current time scale
    public static volatile int currentDate = 0;

    public static boolean gameIsPaused = true; //whether or not the game is paused

    //variables for star/planet IDing

    public static int objectIDValue = 6000;

    //game progression

    public static boolean FTLenabled = false; //whether or not the player can send ships through FTL
    public static boolean tutorialEnabled = true; //whether or not tutorial events will trigger

}
