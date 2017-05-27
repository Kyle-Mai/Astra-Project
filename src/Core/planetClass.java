package Core;

import java.io.Serializable;

/**
    KM
    April 19 2017
    Handles the construction and manipulation of the planet class.

    SOURCES:
    Mkyong - See map generator class for Serializable sourcing.
    Self - Everything else.
 */

//TODO: Rebuild in the same format as the starClass/starCore.

public class planetClass extends planetCore implements Serializable {

    private static final long serialVersionUID = 2L;

    @Override
    public String toString() { //used when writing to a save file

        StringBuffer data = new StringBuffer();

        data.append("/");
        data.append(this.getParentStar().getMapLocationX());
        data.append("-");
        data.append(this.getParentStar().getMapLocationY());
        data.append("-");
        data.append(this.getPlanetNumber());
        data.append("-");
        data.append(this.getPlanetType());
        data.append("-");
        data.append(this.getPlanetRadius());
        data.append("-");
        data.append(this.getDistanceFromStar());
        data.append("-");
        data.append(this.getResources());
        data.append("-");
        data.append(this.writeTidalLock());
        data.append("-");
        data.append(this.getNumOfMoons());

        return data.toString();
    }

    private String writeTidalLock() {
        if (this.getHabitability()) {
            return "t";
        } else {
            return "f";
        }
    }

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
    private colonyCore planetColony;
    private double resources;
    private int colonyID;

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
    public String getPlanetName() { return this.planetName; }
    public boolean getTidalLock() { return this.isTidallyLocked; }
    public starClass getParentStar() { return this.parentStar; }
    public int getPlanetNumber() { return this.planetNumber; }
    public int getPlanetType() { return this.planetType; }
    public int getNumOfMoons() { return this.numOfMoons; }
    public colonyCore getPlanetColony() { return this.planetColony; }
    public double getResources() { return this.resources; }

    /** Setter methods **/

    public void setColonyID(int idnum) { this.colonyID = idnum; }

    public void setPlanetColony(colonyCore colony) {
        this.planetColony = colony;
    }

    public void setHabited(boolean isHabited) {
        this.isHabited = isHabited;
    }

    public void setResources(double used) {
        this.resources = this.resources - used;
    }

}
