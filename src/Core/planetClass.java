package Core;

/**
    KM
    April 19 2017
    Handles the construction and manipulation of the planet class.
 */

//TODO: Rebuild in the same format as the starClass/starCore.

public class planetClass extends planetCore {

    /** planet construction methods **/

    //planet variables
    private int distanceFromStar; //distance from the star, measured in million km
    private int planetRadius; //radius, in km
    private boolean isTidallyLocked; //checks to see if planet is tidally locked to sun >> 100% uninhabitable
    private boolean hasAtmosphere; //whether or not the planet has an atmosphere
    private int atmosphereType; //the type of atmosphere the planet has
    private int planetClass; //class of the planet
    private int planetType; //type of planet, defined using the class in a class.type style

    //variables pulled from parent star
    private int parentStar;
    private int starRadius; //radius of star, in km
    private int starMagnitude;
    private int starLuminosity;
    private String starSpectral; //Spectral class of the star

    public planetClass(int parentStar) {
        this.parentStar = parentStar;
        this.planetRadius = calculateSize();
        this.isTidallyLocked = checkTidalLock(); //runs a check to determine tidal lock

        this.hasAtmosphere = checkAtmosphere();

    }

    public planetClass(int parentStar, String planetName) {
        this.parentStar = parentStar;

    }

    private int determinePlanetClass(){ //sets the planetType of the planet
        int randomPlanet = randomNumber();

        //if the planet is tidally locked, we want to give it a tidal climate, period.
        if (this.isTidallyLocked) {
            return 6;
        }

        //planet has been spawned within the habitable zone of the star
        if (isInHabitableZone()) {

            if (randomPlanet <= 300) {
                return 1; //temperate climate
            } else if (randomPlanet > 300 && randomPlanet <= 500) {
                return 2; //cold climate
            } else if (randomPlanet > 500 && randomPlanet <= 700) {
                return 3; //hot climate
            } else if (randomPlanet > 700 && randomPlanet <= 900) {
                return 4; //extreme climate
            } else {
                return 5; //other climate
            }

        } else {
            //planet is not within the star's habitable zone



        }

        return 0; //redundancy, declares a failure in the generation process
    }

}
