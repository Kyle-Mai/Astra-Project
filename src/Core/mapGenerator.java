package Core;

import java.util.ArrayList;

/**
    KM
    May 10 2017
    Class that handles the generation of the star map, including generating the stars and planets.
 */

public class mapGenerator {

    /** ArrayLists **/

    private ArrayList<mapTile> mapTiles = new ArrayList<>();

    /** Constants **/

    /** Variables **/

    /** Map Constructor **/
    //Necessary in order to store all of the map data together.

    private class mapTile {
        boolean isVisible;
        boolean hasStar;
        starClass star;
        int xPos;
        int yPos;

        private mapTile(boolean generateStar, int xPos, int yPos) {
            this.xPos = xPos;
            this.yPos = yPos;
            this.hasStar = generateStar;
            this.isVisible = false;
            if (generateStar) {
                newStar();
                this.hasStar = true;
            } else {
                this.hasStar = false;
            }
        }


        //Base methods

        private void newStar() {
            this.star = new starClass(this.xPos, this.yPos);

        }

        //Accessor methods

        void setVisiblity(boolean tileVisible) {
            this.isVisible = tileVisible;

        }
    }

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

    private boolean willGenerateStar() {
        int ran = randomNumber(1, this.mapArea);
        if (this.mapArea / this.starDensity > ran) {
            return true;
        } else {
            return false;
        }
    }

    public void generateTiles() {
        int indexX = 1, indexY = 1;
        boolean genStar;
        this.mapTiles.clear(); //safety net, just to be safe (obviously)

        System.out.println("Generating map tiles...");

        for (int i = 0; i < this.mapArea; i++) {
            genStar = willGenerateStar();
            this.mapTiles.add(new mapTile(genStar, indexX, indexY));
            if (indexX < this.xScale) { //moves the index as it generates tiles
                indexX++;
            } else {
                indexX = 1;
                indexY++;
            }

        }
        System.out.println("Map generation complete.");
    }

}
