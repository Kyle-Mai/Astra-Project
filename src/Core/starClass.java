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

    private ArrayList<planetClass> planetList = new ArrayList<>(); //Stores a list of the different starType blueprints.

    private int starRadius; //radius of the star
    private int starMagnitude;
    private String starSpectral; //type of star
    private boolean starIsHabitable; //whether or not the star can theoretically support life
    private boolean isBinarySystem; //whether or not the system will generate two stars
    private int numOfPlanets; //number of planets orbiting this star
    private int surfaceTemp; //in kelvin
    private int starIndex;
    private Double absLum;
    private int habitableZoneMax;
    private int habitableZoneMin;
    private int mapLocationX;
    private int mapLocationY;

    public starClass(int mapLocationX, int mapLocationY){
        this.starIndex = chooseStarType(); //The ID of the star type.
        this.surfaceTemp = determineSurfaceTemperature(this.starIndex); //The surface temperature of the star
        this.starIsHabitable = determineHabitability(this.starIndex); //Whether or not the star is habitable
        this.starSpectral = determineSpectralClass(this.surfaceTemp); //The spectral class of the star
        this.starRadius = determineRadius(this.starIndex); //The star's radius
        this.isBinarySystem = determineBinary(); //Whether or not the star is in a binary system
        this.numOfPlanets = determineNumOfPlanets(this.starIndex); //number of planets orbiting the star
        this.absLum = determineLuminosity(this.starSpectral, this.starMagnitude); //the absolute luminosity of the star
        this.habitableZoneMax = habitableZoneMax(this.absLum); //the upper end of the star's habitable zone
        this.habitableZoneMin = habitableZoneMin(this.absLum); //the lower end of the star's habitable zone
        this.mapLocationX = mapLocationX; //the X position of the star on the map grid
        this.mapLocationY = mapLocationY; //the Y position of the star on the map grid


    }

    //generates the planets surrounding this star
    private void constructPlanets() {
        for (int i = 0; i <= numOfPlanets; i++) {
            planetList.add(new planetClass(this));
        }

    }

    /** Accessor Methods **/
    //For calling the variables of objects made from the starClass.

    public int getHabitableZoneMax() { return this.habitableZoneMax; }
    public int getHabitableZoneMin() { return this.habitableZoneMin; }
    public int getStarRadius() { return this.starRadius; }


}
