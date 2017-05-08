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
    private boolean isInHabitableZone;

    //variables pulled from parent star
    private starClass parentStar;
    private int starRadius; //radius of star, in km
    private int starMagnitude;
    private int starLuminosity;
    private String starSpectral; //Spectral class of the star

    public planetClass(starClass parentStar) {
        this.parentStar = parentStar;
        this.planetRadius = calculateSize(parentStar.getStarRadius(), this.distanceFromStar);
        this.isTidallyLocked = checkTidalLock(this.planetRadius, parentStar.getStarRadius()); //runs a check to determine tidal lock
        this.isInHabitableZone = isInHabitableZone(this.distanceFromStar, parentStar.getHabitableZoneMax(), parentStar.getHabitableZoneMin());
        this.planetType = determinePlanetClass(this.isTidallyLocked, this.isInHabitableZone);
        this.hasAtmosphere = checkAtmosphere();

    }

}
