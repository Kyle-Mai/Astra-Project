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
    private int distanceFromStar;
    private int planetRadius;
    private boolean isTidallyLocked;
    private int planetType;
    private boolean isInHabitableZone;
    private starClass parentStar;
    private boolean isPlanetHabitable;
    private boolean isHabited;
    private int planetNumber;
    private int arrayLoc;

    public planetClass(starClass parentStar, int planetNumber) {
        this.parentStar = parentStar; //the parent star of the planet
        this.planetNumber = planetNumber; //the current planet being generated, with a higher number being further from the parent star
        this.distanceFromStar = determineDistanceFromStar(this.parentStar, this.planetNumber);
        this.planetRadius = calculateSize(parentStar.getStarRadius(), this.distanceFromStar); //determines the radius of the planet in km
        this.isTidallyLocked = checkTidalLock(this.planetRadius, parentStar.getStarRadius()); //runs a check to determine tidal lock
        this.isInHabitableZone = isInHabitableZone(this.distanceFromStar, parentStar.getHabitableZoneMax(), parentStar.getHabitableZoneMin());
        this.planetType = determinePlanetClass(this.distanceFromStar, this.planetRadius, this.isTidallyLocked, this.isInHabitableZone, this.parentStar); //determines the planet's class
        this.arrayLoc = getPlanetFromID(planetType); //Gets the planet's array location from the ID.
        this.isPlanetHabitable = determineHabitability(arrayLoc); //whether or not the planet can be colonized
        this.isHabited = false; //by default, the planet is not currently colonized

    }

    /** Accessor Methods **/

    public boolean getHabited() { return this.isHabited; }
    public boolean getHabitability() { return this.isPlanetHabitable; }
    public int getDistanceFromStar() { return this.distanceFromStar; }
    public int getPlanetRadius() { return this.planetRadius; }

}
