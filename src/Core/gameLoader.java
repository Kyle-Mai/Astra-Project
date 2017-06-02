package Core;

import Core.GUI.gfxRepository;
import Core.GUI.guiCoreV4;
import Core.Player.playerData;

/**
    KM
    May 09 2017
    Handles the loading of core content when the game is launched.
 */

public class gameLoader {

    public static void main(String args[]){

        /** Loads the UI core and the launcher **/

        UILoader();

        /** Preloads content before the game loads **/

        preloadCoreContent();

        /** Loads after the launcher "play" button is pressed **/

        //loadXMLData();
        //cleanContent();

        /** Loads when a new game is played **/

        //mapLoader(20, 20, 6);

    }

    //------------------------------------------------------------------------------------------------------------------

    /** Loader methods **/
    //Load different sections of the program based on what is needed at the time

    //loads UI related elements
    public static void UILoader() {

        gfxRepository.gfxPreloader(); //preload GFX content

        System.out.println("Getting XML data...");
        xmlLoader.getExpansionInfo(); //get the information for the different files
        xmlLoader.getModInfo();
        //creates a new UI with which to run off of
        System.out.println("Loading UI core...");
        javax.swing.SwingUtilities.invokeLater(new Runnable() { //open up a new thread for the UI
            public void run() {
                gameSettings.ui = new guiCoreV4(11);
                gameSettings.ui.loadLauncherScreen();
            }
        } );

    }

    //loads map related elements
    public static void mapLoader(mapGenerator map, playerData player){

        System.out.println("Loading new map...");

        //testing pre-defined star system
        starClass solSystem = new starClass(map.getxScale() / 2, map.getyScale() / 2, "Sol", 1002, true, "A", 4.83, 695700, false, 8, false);

        solSystem.planetList.add(new planetClass(solSystem, "Mercury", 2014, 1, 57909050L, 2440, false, false, false, false, 0));
        solSystem.planetList.add(new planetClass(solSystem, "Venus", 2009, 2, 108208000L, 6051, true, false, false, false, 0));
        solSystem.planetList.add(new planetClass(solSystem, "Earth", 2000, 3, 149598023L, 6371, true, true, true, false, 1));
        solSystem.planetList.add(new planetClass(solSystem, "Mars", 2014, 4, 227939134L, 3390, true, true, true, false, 2));
        solSystem.planetList.add(new planetClass(solSystem, "Jupiter", 2013, 5, 778297882L, 69911, false, false, false, false, 67));
        solSystem.planetList.add(new planetClass(solSystem, "Saturn", 2013, 6, 1429392695L, 58232, false, false, false, false, 62));
        solSystem.planetList.add(new planetClass(solSystem, "Uranus", 2021, 7, 2875031718L, 25362, false, false, false, false, 27));
        solSystem.planetList.add(new planetClass(solSystem, "Neptune", 2013, 8, 4504449781L, 24622, false, false, false, false, 14));

        colonyCore earthColony = new colonyCore(solSystem.planetList.get(2), 89, 2608, 4, 7, 2.13, 11.15, 18.87, 3.67);
        colonyCore marsColony = new colonyCore(solSystem.planetList.get(3), 55, 255, 11, 3, 26.16, 21.12, 4.55, 4.26);

        solSystem.setHomeSystem(true);
        earthColony.setHomePlanet(true);
        solSystem.planetList.get(2).setPlanetColony(earthColony);
        solSystem.planetList.get(3).setPlanetColony(marsColony);

        player.addPlanetColony(earthColony);
        player.addPlanetColony(marsColony);

        //generates the map
        map.generateTiles();

        //gameSettings.map.mapTiles.get(solSystem.getMapLocationX()).get(solSystem.getMapLocationY()).setVisiblity(true);

    }

    //loads the XML data into the memory
    public static void loadXMLData(){

        System.out.println("Loading XML-based content...");

        xmlLoader.loadContent();

    }

    //goes through and makes the data more efficient
    public static void cleanContent() {

        //sorts the data in the arraylists by ID for slightly faster indexing (in theory)
        techCore.sortTechs();

    }

    //preloads all core content into the active memory
    public static void preloadCoreContent(){

        //preloads default planet and star types
        starCore.starPreloader();
        techCore.techPreloader();
        planetCore.planetPreloader();


    }

}
