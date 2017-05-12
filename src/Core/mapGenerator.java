package Core;

import java.util.ArrayList;

/**
    KM
    May 10 2017
    Class that handles the generation of the star map, including generating the stars and planets.
 */

public class mapGenerator {

    /** ArrayLists **/

    private ArrayList<ArrayList<mapTile>> mapTiles = new ArrayList<ArrayList<mapTile>>();
    public static ArrayList<starClass> predefinedStars = new ArrayList<>();

    /** Constants **/

    /** Variables **/

    /** Map Constructor **/
    //Necessary in order to store all of the map data together.

    // sets up map tile object, which is assigned to every tile on the map
    private class mapTile {
        //variables assigned to each tile of the map
        boolean isVisible;
        boolean hasStar;
        starClass star;
        int xPos;
        int yPos;

        //generates a random tile
        private mapTile(boolean generateStar, int xPos, int yPos) {
            this.xPos = xPos;
            this.yPos = yPos;
            this.hasStar = generateStar;
            this.isVisible = false;
            if (generateStar) { //if the tile is set to generate a star, generate a random star
                newStar();
                this.hasStar = true;
            } else {
                this.hasStar = false;
            }
        }

        //generates a tile with a pre-defined star
        private mapTile(starClass star, int xPos, int yPos) {
            this.xPos = xPos;
            this.yPos = yPos;
            this.hasStar = true;
            this.isVisible = false;
            this.star = star;

        }


        //Base methods

        private void newStar() { //creates a new star at this tile
            this.star = new starClass(this.xPos, this.yPos);

        }

        //Accessor methods

        void setVisiblity(boolean tileVisible) { //sets the visibility of this tile
            this.isVisible = tileVisible;

        }
    }

    //variables for the map as a whole
    int xScale;
    int yScale;
    int starDensity;
    int mapArea;

    public mapGenerator(int xScale, int yScale, int starDensity) {
        System.out.println("Generating new map...");

        this.xScale = xScale; //x scale of the map
        this.yScale = yScale; //y scale of the map
        this.starDensity = starDensity; //how many stars the map generator will try to generate, as a percentage of the total area
        this.mapArea = this.xScale * this.yScale; //gets the total number of tiles generated

    }

    /** Simple Methods **/
    //Simple methods used many times over.

    //generates a random number between 1-1000, generally used for percentages out of 100.0%
    protected int randomNumber(){
        return (1 + (int)(Math.random() * 1000));
    }

    //generates a random number between two defined values
    protected int randomNumber(int low, int high){
        return (low + (int)(Math.random() * high));
    }

    /** Standard Methods **/

    //decides whether or not the tile will have a star generated on it as a ratio of star density to the map area.
    private boolean willGenerateStar() {
        int ran = randomNumber(1, this.mapArea);
        if (this.mapArea / this.starDensity > ran) {
            return true;
        } else {
            return false;
        }
    }

    //generates tiles on the map
    public void generateTiles() {

        //refresh the index
        int indexX = 1, indexY = 1;

        boolean genStar;
        boolean preDefined = false;

        this.mapTiles.clear(); //safety net, clears all existing content

        System.out.println("Generating map tiles...");

        //fills out the 2D array so that it supports a list of lists
        for (int i = 0; i < this.yScale; i++) {
            mapTiles.add(new ArrayList<mapTile>());
        }

        //goes through and adds tiles to the 2D arraylist
        for (int i = 0; i < this.mapArea; i++) {

            for (int j = 0; j < predefinedStars.size(); j++) {
                if (indexX == predefinedStars.get(j).getMapLocationX() && indexY == predefinedStars.get(j).getMapLocationY()) {
                    this.mapTiles.get(indexY - 1).add(new mapTile(predefinedStars.get(j), indexX, indexY));
                    System.out.println("Loading predefined system " + predefinedStars.get(j).getStarName() + " (ID" + predefinedStars.get(j).getStarIndex() + ") at the location " + indexX + "|" + indexY);
                    preDefined = true;
                }
            }

            if (!preDefined) { //if a predefined star wasn't already generated, generate a random tile
                genStar = willGenerateStar();
                this.mapTiles.get(indexY - 1).add(new mapTile(genStar, indexX, indexY));
            }

            if (indexX < this.xScale) { //moves the index as it generates tiles
                indexX++;
            } else {
                indexX = 1;
                indexY++;
            }

            preDefined = false;

        }
        System.out.println("Map generation complete.");
    }

}
