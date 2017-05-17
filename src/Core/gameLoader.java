package Core;

import Core.GUI.guiCoreV4;

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

        System.out.println("Getting XML data...");
        xmlLoader.getExpansionInfo(); //get the information for the different files
        //creates a new UI with which to run off of
        System.out.println("Loading UI core...");
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                guiCoreV4 UI = new guiCoreV4(11);
                UI.loadLauncherScreen();
            }
        } );

    }

    //loads map related elements
    public static void mapLoader(int xScale, int yScale, int starDensity){

        System.out.println("Loading new map...");

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
        techCore.techPreloader();
        starCore.starPreloader();
        planetCore.planetPreloader();


    }

}
