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

        //Preloads planets and stars.
        starCore.starPreloader();
        planetCore.planetPreloader();

        //loads XML based content
        xmlLoader.loadContent();

        //loads the map data
        mapGenerator map = new mapGenerator(xScale, yScale, starDensity);

        //testing pre-defined star system
        starClass solSystem = new starClass(10, 10, "Sol", 1002, true, "A", 4.83, 695700, false, 8, true);

        //generates the map
        map.generateTiles();

    }
}
