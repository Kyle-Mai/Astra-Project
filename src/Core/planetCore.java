package Core;

import java.util.ArrayList;

/**

 KM
 May 04 2017
 Handles the core work of the planet class.
Essentially works the same as the starCore, but for planets.

 **/

//TODO: Finish converting the planetClass to the Core/Class format.

public class planetCore {

    /** ArrayLists **/

    protected ArrayList<planetType> listOfPlanets = new ArrayList<>();
    protected ArrayList<planetSizes> listOfScales = new ArrayList<>();
    protected ArrayList<Integer> spawnWeights = new ArrayList<>();


    /** Constants **/
    //Variables used in place of constant numbers either for simplicity or reusability. Also stores it in one easy-to-access place at the top of the class.

    //Conversion constants.
    protected final double AUtoKM = 14959.7870700; //converts au to million KM

    private final int tidalLockChance = 13; //chance that the planet will be tidally locked


    /** Atmosphere generation **/
    //parameters are passed to the checkAtmosphere function which determines whether or not the planet has an atmosphere based on the radius

    private final double atmosphereChanceMultiplierClose = 0; //unused right now


    /** Planet size characteristics **/
    //An ArrayList dedicated to storing variable related to planet size (listOfScales), atmosphere chance, etc, so that it can remain centralized.

    //Pre-defined blueprints for different planet scales, and the modifiers that go with it.
    private void createPlanetSizes(){
        listOfScales.add(new planetSizes("Dwarf", 700, 15));
        listOfScales.add(new planetSizes("Small", 1500, 180));
        listOfScales.add(new planetSizes("Average", 2500, 360));
        listOfScales.add(new planetSizes("Large", 4000, 950));
        listOfScales.add(new planetSizes("Huge", 40000, 1000));

    }

    private static class planetSizes {
        int atmosphereChance;
        int planetScale;
        String sizeName;

        //Constructor for building the ArrayList.
        private planetSizes(String sizeName, int planetScaleKM, int atmosphereChance) {

            this.sizeName = sizeName; //Name of the scale, redundant, just used for identification.
            this.planetScale = planetScaleKM; //Radius of the planet, listed in KM.
            this.atmosphereChance = atmosphereChance; //Chance of a planet within this scale having an atmosphere.
        }

        //Accessor methods for calling variables related to planet size.
        private int getPlanetScale() { return this.planetScale; }
        private int getAtmosphereChance() { return this.atmosphereChance; }

    }


    /** Planet creation **/
    //Used to create the blueprints for different planet types from a blueprint class (planetTypes).

    //Sets all of the predefined blueprints into a variable ArrayList (listOfPlanets) to allow for dynamic addition/removal of planet types.
    private void createPlanetTypes(){
        listOfPlanets.add(new planetType("Continental World", 2000, 2100, 86, "The landscape is dotted with numerous large continents and a temperate climate.", true, 3500, 2300));
        listOfPlanets.add(new planetType("Oceanic World", 2001, 2100, 107, "Small islands poke out of the massive oceans encompassing this world. Very little of the planet is actually above water.", true, 3600, 2100));
        listOfPlanets.add(new planetType("Wetlands World", 2002, 2100, 150, "Thousands of rivers flow through the landscape of this planet. Much of the planet is covered with perpetual monsoon weather.", true, 2700, 1300));
        listOfPlanets.add(new planetType("Alpine World", 2003, 2101, 133, "Large mountain ranges, young and old, spread across the landscape of this planet.", false, 2500, 1300));
        listOfPlanets.add(new planetType("Tundra World", 2004, 2101, 155, "The surface of this planet is fairly barren, with large swaths of frozen ground. Some liquid water can be found around the equatorial region, but it is otherwise frozen.", true, 2300, 800));
        listOfPlanets.add(new planetType("Ice World", 2005, 2101, 207,  "This planet is covered in a surface almost completely composed of frozen water. In some places, the ice extends for kilometers under the surface before thawing.", true, 2800, 1400));
        listOfPlanets.add(new planetType("Desert World", 2006, 2102, 222, "This planet is covered in a surface almost completely composed of frozen water. In some places, the ice extends for kilometers under the surface before thawing.", true, 3100, 2000));
        listOfPlanets.add(new planetType("Swamp World", 2007, 2102, 201, "", true, 2400, 600));
        listOfPlanets.add(new planetType("Tropical World", 2008, 2102, 56, "", true, 2500, 900));
        listOfPlanets.add(new planetType("Greenhouse World", 2009, 2103, 106, "The atmosphere of this planet is thick with greenhouse gases, perpetually feeding an endless heating cycle in the atmosphere.", false, 4100, 2900));
        listOfPlanets.add(new planetType("Frozen World", 2010, 2105, 76,  "", false, 2300, 1700));
        listOfPlanets.add(new planetType("Molten World", 2011, 2102, 101, "", false, 3200, 2000));
        listOfPlanets.add(new planetType("Storm World", 2012, 2103, 56, "Never ending storms batter the surface of this hostile planet, and most of the atmosphere is covered in a thick layer of storm clouds.", false, 3800, 2400));
        listOfPlanets.add(new planetType("Gas Giant", 2013, 2103, 92, "", false, 80000, 32000));
        listOfPlanets.add(new planetType("Barren World", 2014, 2105, 304, "", false, 3000, 2400));
        listOfPlanets.add(new planetType("Damaged World", 2015, 2105, 22, "", false, 2600, 1200));
        listOfPlanets.add(new planetType("Tidal World", 2016, 2104, 40, "", false, 1800, 900));
        listOfPlanets.add(new planetType("Radioactive World", 2017, 2103, 100, "The surface of this planet is littered with radioactive ore deposits. Rapidly degrading, they spew out massive swaths of radiation across the planet's surface.", false, 3200, 2000));
        listOfPlanets.add(new planetType("Carbon World", 2018, 2105, 115, "", false, 2700, 1300));
        listOfPlanets.add(new planetType("Iron World", 2019, 2105, 105, "This world possesses an unusually high iron content, and is fairly dense as a result. It is likely that over 60% of the planet's material is iron based.", false, 4700, 3100));
        listOfPlanets.add(new planetType("Gaia World", 2020, 2106, 15, "A world that possesses traits that make it extremely well-fitted to supporting life. It is, in essence, a perfect world.", true, 2600, 400));
        //listOfPlanets.add();

    }

    //"Class" dedicated to building planet blueprints, which are used when creating different planets.
    private static class planetType {
        //Variable declarations.
        String className;
        int planetID, climateID, sizeWeight, sizeVariation, spawnWeight;
        String classDesc;
        boolean habitable;

        //Constructor method.
        private planetType(String name, int objectID, int climateID, int spawnWeight, String planetDesc, boolean isHabitable, int sizeWeight, int sizeVariation){
            this.className = name; //Name of the world's biome.
            this.planetID = objectID; //The ID used to reference the planet.
            this.spawnWeight = spawnWeight; //The weight assigned to this planet type. Determines how often it spawns relative to other planets in the same climate class.
            this.climateID = climateID; //The ID of this planet's climate.
            this.classDesc = planetDesc; //The planet type's description.
            this.habitable = isHabitable; //Whether or not the player can build a settlement here.
            this.sizeWeight = sizeWeight; //Weighted median size, determines the average size of this type of planet.
            this.sizeVariation = sizeVariation; //Determines how much the size of the planet varies from the median.
        }

        //Accessor methods for calling the planet blueprint variables.
        private int getPlanetID() { return this.planetID; }
        private int getClimateID() { return this.climateID; }
        private String getClassName() { return this.className; }
        private String getClassDesc() { return this.classDesc; }

    }


    //------------------------------------------------------------------------------------------------------------------

    /** Pre-loader Method **/
    //Method that MUST run before anything else in this class.

    public void planetPreloader(){
        createPlanetSizes();
        createPlanetTypes();

    }


    /** Simple Methods **/
    //Simple methods used many times over.

    //generates a random number between 1-1000, generally used for percentages out of 100.0%
    protected int randomNumber(){
        return (1 + (int)(Math.random() * 1000));
    }

    //generates a random number between two defined values
    protected int randomNumber(int low, int high){
        return (low + (int)(Math.random() * high));
    }


    /** General Methods **/
    //General methods used by the global planetCore objects.

    //TODO: Redo everything beyond this point, probably.

    //Checks whether or not the planet is tidally locked to the star.
    protected boolean checkTidalLock(int planetRadius, int starRadius){
        //in order to be tidally locked, the planet must be within 6* the star's radius
        if (planetRadius < (6*starRadius)) {
            //check whether or not it's tidally locked based on the % chance via a random #
            if (randomNumber() <= tidalLockChance) {
                return true;
            }
        }
        return false;
    } //checks whether or not the planet is tidally locked

    protected int calculateSize(int starRadius, int distanceFromStar){

        if (distanceFromStar <= 3*starRadius) {

            if (800 <= randomNumber()) { //what's this?! weighted planet sizes?!
                //80% chance of being a fairly small planet
                return (240 + (int)(Math.random() * 2200));
            } else {
                //20% chance to have a slightly larger range
                return (120 + (int)(Math.random() * 3800));
            }

        } else if (distanceFromStar <= 15*starRadius && distanceFromStar > 3*starRadius) {

            if (900 <= randomNumber()) {
                //90% chance to be a fairly average planet
                return (500 + (int)(Math.random() * 4500));
            } else {
                //10% chance to be a fairly large planet
                return (2000 + (int)(Math.random() * 7900));
            }

        } else if (distanceFromStar <= 25*starRadius && distanceFromStar > 15*starRadius) {

            if (700 <= randomNumber()) {
                //70% chance to be somewhat above average
                return (800 + (int)(Math.random() * 6000));
            } else {
                //30% chance to be fairly large
                return (2000 + (int)(Math.random() * 20000));
            }

        } else if (distanceFromStar <= 40*starRadius && distanceFromStar > 25*starRadius) {

            if (700 <= randomNumber()) {
                //70% chance to be pretty much a certain gas giant
                return (35000 + (int)(Math.random() * 150000));
            } else {
                //30% chance to be fairly huge planet
                return (3600 + (int)(Math.random() * 44000));
            }

        } else if (distanceFromStar <= 60*starRadius && distanceFromStar > 40*starRadius) {

            if (400 <= randomNumber()) {
                //40% chance to be pretty much a certain gas giant
                return (45000 + (int)(Math.random() * 90000));
            } else {
                //60% chance to be fairly average
                return (800 + (int)(Math.random() * 15000));
            }

        } else if (distanceFromStar > 60*starRadius) {

            if (900 <= randomNumber()) {
                //90% chance to be small
                return (200 + (int)(Math.random() * 2500));
            } else {
                //10% chance to be a probable gas giant
                return (6000 + (int)(Math.random() * 70000));
            }

        }

        //arbitrary, should never reach this point
        return 0;

    } //checks the size of the planet

    private boolean isInHabitableZone(int distanceFromStar, int upperBound, int lowerBound) {
        if ((lowerBound * AUtoKM) < distanceFromStar && (upperBound * AUtoKM) > distanceFromStar) {
            return true; //within habitable zone, return true
        } else {
            return false; //outside of habitable zone, return false
        }

    }

}
