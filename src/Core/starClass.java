package Core;

import java.util.ArrayList;

/**
 *
 KM
 May 02
 Handles the methods and variables concerned with individual stars, and overall star management after initial creation.

 */

//TODO: Add methods, begin filling out.

public class starClass extends starCore {

    protected ArrayList<planetClass> planetList = new ArrayList<>(); //Stores a list of the different starType blueprints.

    private int starRadius;
    private int starMagnitude;
    private String starSpectral;
    private boolean starIsHabitable;
    private boolean isBinarySystem;
    private int numOfPlanets;
    private int surfaceTemp;
    private int starIndex;
    private Double absLum;
    private int habitableZoneMax;
    private int habitableZoneMin;
    private int mapLocationX;
    private int mapLocationY;
    private int arrayLoc;

    public starClass(int mapLocationX, int mapLocationY){
        this.starIndex = chooseStarType(); //The ID of the star type.
        this.arrayLoc = getStarFromID(this.starIndex); //Finds the position in the array of the star.
        this.surfaceTemp = determineSurfaceTemperature(arrayLoc); //The surface temperature of the star (in kelvin)
        this.starIsHabitable = determineHabitability(arrayLoc); //Whether or not the star can support habitable planets
        this.starSpectral = determineSpectralClass(this.surfaceTemp); //The spectral class of the star
        this.starRadius = determineRadius(arrayLoc); //The star's radius
        this.isBinarySystem = determineBinary(); //Whether or not the star is in a binary system
        this.numOfPlanets = determineNumOfPlanets(arrayLoc); //number of planets orbiting the star
        this.absLum = determineLuminosity(this.starSpectral, this.starMagnitude); //the absolute luminosity of the star
        this.habitableZoneMax = habitableZoneMax(this.absLum); //the upper end of the star's habitable zone
        this.habitableZoneMin = habitableZoneMin(this.absLum); //the lower end of the star's habitable zone
        this.mapLocationX = mapLocationX; //the X position of the star on the map grid
        this.mapLocationY = mapLocationY; //the Y position of the star on the map grid
        System.out.println("Star successfully generated at the coordinates " + mapLocationX + "|" + mapLocationY);
        constructPlanets();


    }

    //generates the planets surrounding this star
    private void constructPlanets() {
        for (int i = 0; i <= this.numOfPlanets; i++) {
            planetList.add(new planetClass(this, i));
        }

    }

    /** Accessor Methods **/
    //For calling the variables of objects made from the starClass.

    public int getHabitableZoneMax() { return this.habitableZoneMax; }
    public int getHabitableZoneMin() { return this.habitableZoneMin; }
    public int getStarRadius() { return this.starRadius; }
    public int getNumOfPlanets() { return this.numOfPlanets; }


}
