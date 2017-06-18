package Core;

import java.awt.image.BufferedImage;
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

    /** planet construction methods **/

    //planet variables
    private int planetRadius;
    private boolean isTidallyLocked;
    private int planetType;
    private starClass parentStar;
    private boolean isPlanetHabitable;
    private boolean isHabited;
    private int planetNumber;
    private transient int arrayLoc;
    private String planetName;
    private colonyCore planetColony;
    private double resources;
    private int colonyID;
    private boolean homePlanet;
    private int systemPosX, systemPosY;

    //generates a random planet
    public planetClass(starClass parentStar, int planetNumber) {
        this.parentStar = parentStar; //the parent star of the planet
        this.planetNumber = planetNumber; //the current planet being generated, with a higher number being further from the parent star
        //this.distanceFromStar = determineDistanceFromStar(this.parentStar, this.planetNumber);
        this.planetRadius = calculateSize(); //determines the radius of the planet in km
        this.isTidallyLocked = checkTidalLock(); //runs a check to determine tidal lock
        this.planetType = determinePlanetClass(0, this.planetRadius, this.isTidallyLocked, this.parentStar.isStarHabitable(), this.parentStar); //determines the planet's class
        this.arrayLoc = getPlanetFromID(planetType); //Gets the planet's array location from the ID.
        this.isPlanetHabitable = determineHabitability(arrayLoc); //whether or not the planet can be colonized
        this.isHabited = false; //by default, the planet is not currently colonized
        this.planetName = this.parentStar.getStarName() + " " + this.planetNumber;
        this.resources = calculateResources(arrayLoc) * (gameSettings.currentResources / 100);
        //System.out.println("Planet '" + listOfPlanets.get(arrayLoc).getClassName() + "' (ID" + planetType + ") successfully generated.");

    }

    //generates a pre-defined planet
    public planetClass(starClass parentStar, String planetName, int planetType, int planetNumber, int planetRadius, boolean isHabited, boolean isPlanetHabitable, boolean isTidallyLocked, boolean homePlanet, double resources) {
        this.parentStar = parentStar;
        this.planetName = planetName;
        this.planetType = planetType;
        this.planetNumber = planetNumber;
        this.planetRadius = planetRadius;
        this.arrayLoc = getPlanetFromID(this.planetType);
        this.isHabited = isHabited;
        this.isPlanetHabitable = isPlanetHabitable;
        this.isTidallyLocked = isTidallyLocked;
        this.homePlanet = homePlanet;
        this.resources = resources;
        //System.out.println("Predefined planet '" + listOfPlanets.get(arrayLoc).getClassName() + " [" + this.planetName + "] (ID" + planetType + ") successfully generated in the " + parentStar.getStarName() + " system.");

    }

    /** Accessor Methods **/

    public boolean getHabited() { return this.isHabited; }
    public boolean getHabitability() { return this.isPlanetHabitable; }
    public int getPlanetRadius() { return this.planetRadius; }
    public String getPlanetName() { return this.planetName; }
    public boolean getTidalLock() { return this.isTidallyLocked; }
    public starClass getParentStar() { return this.parentStar; }
    public int getPlanetNumber() { return this.planetNumber; }
    public int getPlanetType() { return this.planetType; }
    public colonyCore getPlanetColony() { return this.planetColony; }
    public double getResources() { return this.resources; }
    public int getArrayLoc() { return this.arrayLoc; }
    public String getPlanetClassName() { return planetClass.listOfPlanets.get(arrayLoc).getClassName(); }
    public String getPlanetClassDesc() { return planetClass.listOfPlanets.get(arrayLoc).getClassDesc(); }
    public boolean isHomePlanet() { return this.homePlanet; }
    public int getSystemPosX() { return this.systemPosX; }
    public int getSystemPosY() { return this.systemPosY; }
    public BufferedImage getPlanetPortrait() { return planetClass.listOfPlanets.get(arrayLoc).getGfxImage(); }

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
        if (this.resources < 0) { //should go below zeor anyways, but redundancies
            this.resources = 0;
        }
    }

    public void setSystemPosX(int x) { this.systemPosX = x; }
    public void setSystemPosY(int y) { this.systemPosY = y; }

}
