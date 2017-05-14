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
    private long distanceFromStar;
    private int planetRadius;
    private boolean isTidallyLocked;
    private int planetType;
    private boolean isInHabitableZone;
    private starClass parentStar;
    private boolean isPlanetHabitable;
    private boolean isHabited;
    private int planetNumber;
    private int arrayLoc;
    private String planetName;
    private String planetDesc;
    private int numOfMoons;

    //generates a random planet
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
        System.out.println("Planet '" + listOfPlanets.get(arrayLoc).getClassName() + "' (ID" + planetType + ") successfully generated.");

    }

    //generates a pre-defined planet
    public planetClass(starClass parentStar, String planetName, int planetType, int planetNumber, long distanceFromStar, int planetRadius, boolean isInHabitableZone, boolean isHabited, boolean isPlanetHabitable, boolean isTidallyLocked, int numOfMoons) {
        this.parentStar = parentStar;
        this.planetName = planetName;
        this.planetType = planetType;
        this.planetNumber = planetNumber;
        this.distanceFromStar = distanceFromStar;
        this.planetRadius = planetRadius;
        this.isInHabitableZone = isInHabitableZone;
        this.arrayLoc = getPlanetFromID(this.planetType);
        this.isHabited = isHabited;
        this.isPlanetHabitable = isPlanetHabitable;
        this.isTidallyLocked = isTidallyLocked;
        this.numOfMoons = numOfMoons;
        System.out.println("Predefined planet '" + listOfPlanets.get(arrayLoc).getClassName() + " [" + this.planetName + "] (ID" + planetType + ") successfully generated in the " + parentStar.getStarName() + " system.");

    }

    /** Accessor Methods **/

    public boolean getHabited() { return this.isHabited; }
    public boolean getHabitability() { return this.isPlanetHabitable; }
    public long getDistanceFromStar() { return this.distanceFromStar; }
    public int getPlanetRadius() { return this.planetRadius; }
    public String getPlanetNmae() { return this.planetName; }

}
