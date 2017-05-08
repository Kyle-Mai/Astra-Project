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
    private int planetType; //type of planet
    private boolean isInHabitableZone;
    private starClass parentStar;
    private boolean isPlanetHabitable;
    private boolean isHabited;

    public planetClass(starClass parentStar) {
        this.parentStar = parentStar;
        this.planetRadius = calculateSize(parentStar.getStarRadius(), this.distanceFromStar);
        this.isTidallyLocked = checkTidalLock(this.planetRadius, parentStar.getStarRadius()); //runs a check to determine tidal lock
        this.isInHabitableZone = isInHabitableZone(this.distanceFromStar, parentStar.getHabitableZoneMax(), parentStar.getHabitableZoneMin());
        this.planetType = determinePlanetClass(this.isTidallyLocked, this.isInHabitableZone); //whether or not the planet is within the habitable zone of the star
        this.isPlanetHabitable = determineHabitability(this.planetType); //whether or not the planet can be colonized
        this.isHabited = false; //by default, the planet is not currently colonized

    }

    /** Accessor Methods **/

    public boolean getHabited() { return this.isHabited; }
    public boolean getHabitability() { return this.isPlanetHabitable; }

}
