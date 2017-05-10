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
        xmlLoader.loadXML(expansions);
        //Loads mod content.
        xmlLoader.loadXML(mods);

        //Tests star
        starClass testStar = new starClass(1, 2);

    }
}
