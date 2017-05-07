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
        this.surfaceTemp = determineSurfaceTemperature(starIndex); //The surface temperature of the star
        this.starIsHabitable = determineHabitability(starIndex); //Whether or not the star is habitable
        this.starSpectral = determineSpectralClass(surfaceTemp); //The spectral class of the star
        this.starRadius = determineRadius(starIndex); //The star's radius
        this.isBinarySystem = determineBinary(); //Whether or not the star is in a binary system
        this.numOfPlanets = determineNumOfPlanets(starIndex); //number of planets orbiting the star
        this.absLum = determineLuminosity(starSpectral, starMagnitude); //the absolute luminosity of the star
        this.habitableZoneMax = habitableZoneMax(absLum); //the upper end of the star's habitable zone
        this.habitableZoneMin = habitableZoneMin(absLum); //the lower end of the star's habitable zone
        this.mapLocationX = mapLocationX; //the X position of the star on the map grid
        this.mapLocationY = mapLocationY; //the Y position of the star on the map grid


    }


}
