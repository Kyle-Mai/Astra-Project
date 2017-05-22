package Core;

/**
 * KM
 * May 21 2017
 * Stores all of the different game settings.
 */


public class gameSettings {

    //variables for the resource abundance

    public static final int resourceAbundanceMin = 25;
    public static final int resourceAbundanceAvg = 100;
    public static final int resourceAbundanceHigh = 200;

    public static int currentResources = 100;

    //how often star systems will spawn

    public static final int starFreqMin = 10;
    public static final int starFreqAvg = 30;
    public static final int starFreqHigh = 80;

    public static int starFrequency = 25;

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

}
