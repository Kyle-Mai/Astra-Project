package Core;

/**
    KM
    May 09 2017
    Handles the loading of core content when the game is launched.
 */

public class gameLoader {

    public static void main(String args[]){

        final int xScale = 20; //x scale of the map
        final int yScale = 20; //y scale of the map
        final int starDensity = 6; //one in every x tiles has a star

        /** Preloads content before the game loads **/

        //Preloads planets and stars.
        starCore.starPreloader();
        planetCore.planetPreloader();

        /** Loads the UI core and the launcher **/

        System.out.println("Loading UI core...");
        coreUI UI = new coreUI("Black"); //loads the UI core
        UI.setVisible(true);
        launcherUI launcher = new launcherUI(UI);
        launcher.loadUI("Astra Project Launcher", 9);
        launcher.addContent();

        /** Loads after the launcher "play" button is pressed **/

        //loads XML based content
        xmlLoader.loadContent();

        /** Loads when a new game is played **/

        //loads the map data
        mapGenerator map = new mapGenerator(xScale, yScale, starDensity);

        //testing pre-defined star system
        starClass solSystem = new starClass(xScale / 2, yScale / 2, "Sol", 1002, true, "A", 4.83, 695700, false, 8, false);

        solSystem.planetList.add(new planetClass(solSystem, "Mercury", 2014, 1, 57909050L, 2440, false, false, false, false, 0));
        solSystem.planetList.add(new planetClass(solSystem, "Venus", 2009, 2, 108208000L, 6051, true, false, false, false, 0));
        solSystem.planetList.add(new planetClass(solSystem, "Earth", 2000, 3, 149598023L, 6371, true, true, true, false, 1));
        solSystem.planetList.add(new planetClass(solSystem, "Mars", 2014, 4, 227939134L, 3390, true, false, false, false, 0));
        solSystem.planetList.add(new planetClass(solSystem, "Jupiter", 2013, 5, 778297882L, 69911, false, false, false, false, 67));
        solSystem.planetList.add(new planetClass(solSystem, "Saturn", 2013, 6, 1429392695L, 58232, false, false, false, false, 62));
        solSystem.planetList.add(new planetClass(solSystem, "Uranus", 2021, 7, 2875031718L, 25362, false, false, false, false, 27));
        solSystem.planetList.add(new planetClass(solSystem, "Neptune", 2013, 8, 4504449781L, 24622, false, false, false, false, 14));

        //generates the map
        map.generateTiles();

    }
}
