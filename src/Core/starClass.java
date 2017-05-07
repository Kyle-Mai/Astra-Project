package Core;

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
    private boolean numOfPlanets; //number of planets orbiting this star
    private int surfaceTemp; //in kelvin
    private int starIndex;

    public starClass(){
        this.starIndex = chooseStarType(); //The ID of the star type.
        this.surfaceTemp = determineSurfaceTemperature(starIndex);
        this.starIsHabitable = determineHabitability(starIndex);
        this.starSpectral = determineSpectralClass(surfaceTemp);
        this.isBinarySystem = determineBinary();

    }


}
