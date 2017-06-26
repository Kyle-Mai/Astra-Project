package Core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
    KM
    May 10 2017
    Class that handles the generation of the star map, including generating the stars and planets.

    SOURCES:

    Mkyong - Serialization and storing of object data via https://www.mkyong.com/java/how-to-write-an-object-to-file-in-java/
    Java API - Used Java API to help me understand how to use 2D ArrayLists.
    Self - Everything else.
 */

public class mapGenerator implements Serializable {

    private static final long serialVersionUID = 3L;

    //decides whether or not the tile will have a star generated on it as a ratio of star density to the map area.
    private boolean willGenerateStar() {
        int ran = randomNumber(1, this.mapArea);
        if (this.mapArea / this.starDensity > ran) {
            return true;
        } else {
            return false;
        }
    }

    /** ArrayLists **/

    public ArrayList<ArrayList<mapTile>> mapTiles = new ArrayList<>();
    public static transient ArrayList<starClass> predefinedStars = new ArrayList<>();
    public transient ArrayList<starClass> generatedStars = new ArrayList<>();

    /** Constants **/

    /** Variables **/

    //variables for the map as a whole
    private int xScale;
    private int yScale;
    private int starDensity;
    private int mapArea;

    /** Map Constructor **/
    //Necessary in order to store all of the map data together.

    // sets up map tile object, which is assigned to every tile on the map
    public class mapTile implements Serializable {

        private static final long serialVersionUID = 4L;

        //variables assigned to each tile of the map
        private boolean isVisible;
        private boolean hasStar;
        private starClass star;
        private int xPos;
        private int yPos;

        //generates a random tile
        private mapTile(boolean generateStar, int xPos, int yPos) {
            this.xPos = xPos;
            this.yPos = yPos;
            this.hasStar = generateStar;
            this.isVisible = false;
            if (generateStar) { //if the tile is set to generate a star, generate a random star
                newStar();
                this.hasStar = true;
                generatedStars.add(this.star);
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
            this.star.setMapLocationX(xPos);
            this.star.setMapLocationY(yPos);
            generatedStars.add(this.star);

        }

        public boolean getVisibility() { return this.isVisible; }
        public boolean getStar() { return this.hasStar; }
        public starClass getStarData() { return this.star; }

        //Base methods

        private void newStar() { //creates a new star at this tile
            this.star = new starClass(this.xPos, this.yPos);

        }

        public void setStarData(starClass set) {
            this.star = set;
        }

        //Accessor methods

        void setVisiblity(boolean tileVisible) { //sets the visibility of this tile
            this.isVisible = tileVisible;

        }
    }

    public mapGenerator(int xScale, int yScale, int starDensity) {
        System.out.println("Creating new map...");

        this.xScale = xScale; //x scale of the map
        this.yScale = yScale; //y scale of the map
        this.starDensity = starDensity; //how many stars the map generator will try to generate, as a percentage of the total area
        this.mapArea = this.xScale * this.yScale; //gets the total number of tiles generated

    }

    public int getxScale() { return this.xScale; }
    public int getyScale() { return this.yScale; }
    public int getStarDensity() { return this.starDensity; }

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
                        this.mapTiles.get(indexY - 1).add(new mapTile(predefinedStars.get(j), indexX - 1, indexY - 1));
                        if (predefinedStars.get(j).isHomeSystem()) {
                            this.mapTiles.get(indexY - 1).get(indexX - 1).setVisiblity(true); //home system is visible!
                        }
                        System.out.println("Loading predefined system " + predefinedStars.get(j).getStarName() + " (ID" + predefinedStars.get(j).getStarIndex() + ") at the location " + indexX + "|" + indexY);
                        preDefined = true;
                    }
                }

                if (!preDefined) { //if a predefined star wasn't already generated, generate a random tile
                    genStar = willGenerateStar();

                    //checks to see if the star being generated is too closer to another star
                    if (indexY > 1 && indexX > 1) {
                        if (this.mapTiles.get(indexY - 1).get(indexX - 2).getStar() || this.mapTiles.get(indexY - 2).get(indexX - 1).getStar()) {
                            genStar = false;
                            //System.out.println("Map generation refused - too close proximity to another star.");
                        }
                    } else if (indexX > 1 && indexY == 1) {
                        if (this.mapTiles.get(indexY - 1).get(indexX - 2).getStar()) {
                            genStar = false;
                            //System.out.println("Map generation refused - too close proximity to another star.");
                        }
                    } else if (indexX == 1 && indexY > 1) {
                        if (this.mapTiles.get(indexY - 2).get(indexX - 1).getStar()) {
                            genStar = false;
                            //System.out.println("Map generation refused - too close proximity to another star.");
                        }
                    }

                    this.mapTiles.get(indexY - 1).add(new mapTile(genStar, indexX - 1, indexY - 1));
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
