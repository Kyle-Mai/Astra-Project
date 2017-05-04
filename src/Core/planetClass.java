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

    //Determines the habitability of the planet.
    private boolean isInHabitableZone(){
        //I don't like data type values, so I'm converting in and out of them.
        Integer starMag = starMagnitude;
        Double absLum; //absolute luminosity of the star
        Double absMagnitude = starMag.doubleValue(); //absolute magnitude of the star, as a double
        Double bolMagnitude; //bolometric magnitude
        Double BC = new Double(0); //bolometric correction constant

        Double upperBound, lowerBound; //closest and furthest range of the habitable zone (approximation)

        //this calculates the bolometric correction for the star based on the spectral class
        if (starSpectral == "B" || starSpectral == "M") {
            BC = -2.0;
        } else if (starSpectral == "A") {
            BC = -0.3;
        } else if (starSpectral == "F") {
            BC = -0.15;
        } else if (starSpectral == "G") {
            BC = -0.4;
        } else if (starSpectral == "K") {
            BC = -0.8;
        } else if (starSpectral == "O") {
            BC = -4.0;
        }

        //determines the bolometric magnitude of the star
        bolMagnitude = absMagnitude + BC;

        //determines the absolute luminosity of the star based on the bolometric magnitude and the constant for the Earth (4.72)
        absLum = (Math.pow((10),(bolMagnitude - bolometricEarthConst) / -2.5));

        //this gives us the upper and lower bounds of the habitable zone, measured in AU.
        lowerBound = Math.sqrt(absLum / 1.1);
        upperBound = Math.sqrt(absLum / 0.53);

        //if the distance from the star is within the upper and lower bounds of the habitable zone, return true
        //other things may influence habitability, however, this is just the zone
        if ((lowerBound * AUtoKM) < distanceFromStar && (upperBound * AUtoKM) > distanceFromStar) {
            return true; //within habitable zone, return true
        } else {
            return false; //outside of habitable zone, return false
        }
    }

    //determines the habitability of the planet based on the conditions and returns a number between 1-1000 (percent habitability)
    //higher percent > more habitable, by default anything less than 30% is a no-go
    private int determineHabitability() {
        return 100;

    }



}
