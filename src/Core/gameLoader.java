package Core;

import java.io.File;

/**
    KM
    May 09 2017
    Handles the loading of core content when the game is launched.
 */

public class gameLoader {

    public static void main(String args[]){

        final File expansions = new File(System.getProperty("user.dir") + "/src/Expansions");
        final File mods = new File(System.getProperty("user.dir") + "/src/Mods");

        //Preloads planets and stars.
        starCore.starPreloader();
        planetCore.planetPreloader();
        //Loads expansion content.
        System.out.println("Attempting to load expansion files...");
        xmlLoader.loadXML(expansions);
        //Loads mod content.
        System.out.println("Attempting to load mods...");
        xmlLoader.loadXML(mods);

        //Tests star
        starClass testStar1 = new starClass(1, 1);
        starClass testStar2 = new starClass(1, 2);
        starClass testStar3 = new starClass(1, 3);
        starClass testStar4 = new starClass(1, 4);
        starClass testStar5 = new starClass(1, 5);

    }
}
