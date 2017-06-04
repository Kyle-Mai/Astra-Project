package Core;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**

 KM
 May 04 2017
 Handles the core work of the planet class.
Essentially works the same as the starCore, but for planets.

 **/

public class planetCore {

    /** ArrayLists **/

    public static ArrayList<planetType> listOfPlanets = new ArrayList<>();
    protected static ArrayList<planetSizes> listOfScales = new ArrayList<>();
    protected ArrayList<Integer> planetsToSpawn = new ArrayList<>();


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
    private static void createPlanetSizes(){
        listOfScales.add(new planetSizes("Dwarf", 0, 0));
        listOfScales.add(new planetSizes("Small", 1500, 1));
        listOfScales.add(new planetSizes("Average", 2500, 2));
        listOfScales.add(new planetSizes("Large", 4000, 5));
        listOfScales.add(new planetSizes("Huge", 40000, 50));
        listOfScales.add(new planetSizes("Absurd", 10000000, 0)); //redundancy just to make my moon weighter work a little nicer for now, will need to recode eventually

    }

    private static class planetSizes {
        int moonWeight;
        int planetScale;
        String sizeName;

        //Constructor for building the ArrayList.
        private planetSizes(String sizeName, int planetScaleKM, int moonWeight) {

            this.sizeName = sizeName; //Name of the scale, redundant, just used for identification.
            this.planetScale = planetScaleKM; //Minimum radius of the planet for it to fit in this class
            this.moonWeight = moonWeight; //number of moons average for a planet of this scale
        }

        //Accessor methods for calling variables related to planet size.
        private int getPlanetScale() { return this.planetScale; }
        private int getMoonWeight() { return this.moonWeight; }

    }


    /** Planet creation **/
    //Used to create the blueprints for different planet types from a blueprint class (planetTypes).

    //Sets all of the predefined blueprints into a variable ArrayList (listOfPlanets) to allow for dynamic addition/removal of planet types.
    private static void createPlanetTypes(){
        listOfPlanets.add(new planetType("Continental World", 2000, 2100, 82, "The landscape is dotted with numerous large continents and a temperate climate.", true, 3500, 2300, "/Core/GUI/Resources/portraits/b_star.png", "/Core/GUI/Resources/planets/continental_planet.png"));
        listOfPlanets.add(new planetType("Oceanic World", 2001, 2100, 58, "Small islands poke out of the massive oceans encompassing this world. Very little of the planet is actually above water.", true, 3600, 2100, "/Core/GUI/Resources/portraits/b_star.png", "/Core/GUI/Resources/planets/water_planet.png"));
        listOfPlanets.add(new planetType("Wetlands World", 2002, 2100, 55, "Thousands of rivers flow through the landscape of this planet. Much of the planet is covered with perpetual monsoon weather.", true, 2700, 1300, "/Core/GUI/Resources/portraits/b_star.png", "/Core/GUI/Resources/planets/toxic_planet_02.png"));
        listOfPlanets.add(new planetType("Alpine World", 2003, 2101, 76, "Large mountain ranges, young and old, spread across the landscape of this planet.", false, 2500, 1300, "/Core/GUI/Resources/portraits/b_star.png", "/Core/GUI/Resources/planets/toxic_planet_02.png"));
        listOfPlanets.add(new planetType("Tundra World", 2004, 2101, 58, "The surface of this planet is fairly barren, with large swaths of frozen ground. Some liquid water can be found around the equatorial region, but it is otherwise frozen.", true, 2300, 800, "/Core/GUI/Resources/portraits/b_star.png", "/Core/GUI/Resources/planets/tundra_planet.png"));
        listOfPlanets.add(new planetType("Ice World", 2005, 2101, 49,  "This planet is covered in a surface almost completely composed of frozen water. In some places, the ice extends for kilometers under the surface before thawing.", true, 2800, 1400, "/Core/GUI/Resources/portraits/ice_planet.png", "/Core/GUI/Resources/planets/ice_planet.png"));
        listOfPlanets.add(new planetType("Desert World", 2006, 2102, 70, "This planet is covered in a surface almost completely composed of frozen water. In some places, the ice extends for kilometers under the surface before thawing.", true, 3100, 2000, "/Core/GUI/Resources/portraits/b_star.png", "/Core/GUI/Resources/planets/toxic_planet_02.png"));
        listOfPlanets.add(new planetType("Swamp World", 2007, 2102, 60, "", true, 2400, 600, "/Core/GUI/Resources/portraits/b_star.png", "/Core/GUI/Resources/planets/swamp_planet.png"));
        listOfPlanets.add(new planetType("Tropical World", 2008, 2102, 52, "", true, 2500, 900, "/Core/GUI/Resources/portraits/b_star.png", "/Core/GUI/Resources/planets/toxic_planet_02.png"));
        listOfPlanets.add(new planetType("Greenhouse World", 2009, 2109, 28, "The atmosphere of this planet is thick with greenhouse gases, perpetually feeding an endless heating cycle in the atmosphere.", false, 4100, 2900, "/Core/GUI/Resources/portraits/toxic_planet.png", "/Core/GUI/Resources/planets/toxic_planet_01.png"));
        listOfPlanets.add(new planetType("Frozen World", 2010, 2110, 35,  "", false, 2300, 1700, "/Core/GUI/Resources/portraits/frozen_planet.png", "/Core/GUI/Resources/planets/frozen_planet.png"));
        listOfPlanets.add(new planetType("Molten World", 2011, 2109, 42, "", false, 3200, 2000, "/Core/GUI/Resources/portraits/molten_planet.png", "/Core/GUI/Resources/planets/molten_planet.png"));
        listOfPlanets.add(new planetType("Storm World", 2012, 2103, 11, "Never ending storms batter the surface of this hostile planet, and most of the atmosphere is covered in a thick layer of storm clouds.", false, 3800, 2400, "/Core/GUI/Resources/portraits/storm_planet.png", "/Core/GUI/Resources/planets/storm_planet.png"));
        listOfPlanets.add(new planetType("Gas Giant", 2013, 2108, 17, "", false, 80000, 32000, "/Core/GUI/Resources/portraits/gas_giant.png", "/Core/GUI/Resources/planets/gas_giant_01.png"));
        listOfPlanets.add(new planetType("Barren World", 2014, 2105, 84, "", false, 3000, 2400, "/Core/GUI/Resources/portraits/barren_planet.png", "/Core/GUI/Resources/planets/barren_planet_01.png"));
        listOfPlanets.add(new planetType("Damaged World", 2015, 2105, 14, "", false, 2600, 1200, "/Core/GUI/Resources/portraits/asteroid_sky.png", "/Core/GUI/Resources/planets/toxic_planet_02.png"));
        listOfPlanets.add(new planetType("Tidal World", 2016, 2104, 10, "", false, 1800, 900, "/Core/GUI/Resources/portraits/b_star.png", "/Core/GUI/Resources/planets/toxic_planet_02.png"));
        listOfPlanets.add(new planetType("Radioactive World", 2017, 2103, 12, "The surface of this planet is littered with radioactive ore deposits. Rapidly degrading, they spew out massive swaths of radiation across the planet's surface.", false, 3200, 2000, "/Core/GUI/Resources/portraits/radiation_planet.png", "/Core/GUI/Resources/planets/nuclear_planet.png"));
        listOfPlanets.add(new planetType("Carbon World", 2018, 2105, 18, "", false, 2700, 1300, "/Core/GUI/Resources/portraits/b_star.png", "/Core/GUI/Resources/planets/carbon_planet.png"));
        listOfPlanets.add(new planetType("Iron World", 2019, 2105, 10, "This world possesses an unusually high iron content, and is fairly dense as a result. It is likely that over 60% of the planet's material is iron based.", false, 4700, 3100, "/Core/GUI/Resources/portraits/b_star.png", "/Core/GUI/Resources/planets/iron_planet.png"));
        listOfPlanets.add(new planetType("Gaia World", 2020, 2106, 2, "A world that possesses traits that make it extremely well-fitted to supporting life. It is, in essence, a perfect world.", true, 2600, 400, "/Core/GUI/Resources/portraits/b_star.png", "/Core/GUI/Resources/planets/lush_planet.png"));
        listOfPlanets.add(new planetType("Ice Giant", 2021, 2108, 10, "An ice giant, while similar to gas giants, contains more 'icey' elements such as water, ammonia, and methane.", false, 320000, 10000, "/Core/GUI/Resources/portraits/gas_giant.png", "/Core/GUI/Resources/planets/ice_giant.png"));

    }

    //"Class" dedicated to building planet blueprints, which are used when creating different planets.
    public static class planetType {
        //Variable declarations.
        String className;
        int planetID, climateID, sizeWeight, sizeVariation, spawnWeight;
        String classDesc;
        boolean habitable;
        String icon;
        String gfx;
        BufferedImage gfxImage;
        BufferedImage planetIcon;

        //Constructor method.
        public planetType(String Name, int objectID, int climateID, int spawnWeight, String planetDesc, boolean isHabitable, int sizeWeight, int sizeVariation, String gfx, String icon){
            this.className = Name; //Name of the world's biome.
            this.planetID = objectID; //The ID used to reference the planet.
            this.spawnWeight = spawnWeight; //The weight assigned to this planet type. Determines how often it spawns relative to other planets in the same climate class.
            this.climateID = climateID; //The ID of this planet's climate.
            this.classDesc = planetDesc; //The planet type's description.
            this.habitable = isHabitable; //Whether or not the player can build a settlement here.
            this.sizeWeight = sizeWeight; //Weighted median size, determines the average size of this type of planet.
            this.sizeVariation = sizeVariation; //Determines how much the size of the planet varies from the median.
            this.icon = icon;
            this.gfx = gfx;
        }

        //Accessor methods for calling the planet blueprint variables.
        protected int getPlanetID() { return this.planetID; }
        protected int getClimateID() { return this.climateID; }
        protected String getClassName() { return this.className; }
        protected String getClassDesc() { return this.classDesc; }
        protected boolean getHabitable() { return this.habitable; }
        protected int getSpawnWeight() { return this.spawnWeight; }
        public BufferedImage getGfxImage() { return this.gfxImage; }
        public BufferedImage getPlanetIcon() { return this.planetIcon; }
        public String getIcon() { return this.icon; }
        public String getGfx() { return this.gfx; }

        public void setGfxImage(BufferedImage gfxa) { this.gfxImage = gfxa; }
        public void setPlanetIcon(BufferedImage icona) { this.planetIcon = icona; }

    }


    //------------------------------------------------------------------------------------------------------------------

    /** Pre-loader Method **/
    //Method that MUST run before anything else in this class.

    public static void planetPreloader(){
        createPlanetSizes();
        createPlanetTypes();
        System.out.println("Preloading of planetCore complete.");

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

    //gets the number of moons orbiting the planet based on the size of the planet
    protected int determineMoons(int planetRadius) {

        int moons = 0; //keep track of the number of moons
        int moonsAverage = 0; //average moons to be generated

        //index the list of planet sizes to see what category this planet falls under
        for (int i = 0; i < listOfScales.size(); i++) {
            if (planetRadius < listOfScales.get(i).getPlanetScale()) {
                moonsAverage = listOfScales.get(i - 1).getMoonWeight();
                break; //indexing complete, close the for loop
            }
        }




        return 0;
    }

    //Determines the planet's distance from the star
    protected int determineDistanceFromStar(starClass parentStar, int planetNumber){
        int distanceMin; //Values
        int distanceMax;
        Double maximumDistance; //Multipliers
        Double minimumDistance;
        int distanceFromStar;
        boolean incompatible = true;

        if (parentStar.getNumOfPlanets() > 6) {
            maximumDistance = 4.2; //the minimum distance from the star that a planet will be generated, measured as a multiplier of the star's radius
            minimumDistance = 1.4; //the minimum distance from the star that a planet will be generated

        } else if (parentStar.getNumOfPlanets() < 3) {
            maximumDistance = 6.4;
            minimumDistance = 1.4;

        } else {
            maximumDistance = 8.5;
            minimumDistance = 1.4;

        }

        //Calculates the range of the planet's possible orbits
        distanceMin = (int)(parentStar.getStarRadius() * (minimumDistance * (planetNumber + 1)));
        distanceMax = (int)(parentStar.getStarRadius() * (maximumDistance * (planetNumber + 1)));

        distanceFromStar = randomNumber(distanceMin, distanceMax);

        //If this isn't the first planet, we need to make sure the planet orbits don't overlap
        /*if (planetNumber > 0) {
            //until the conditions are met
            while (incompatible) {
                incompatible = false;
                for (int i = 0; i < parentStar.planetList.size(); i++) { //check the other planets around the star
                    //if this planet is too close to another,
                    if (distanceFromStar < parentStar.planetList.get(i).getDistanceFromStar() + (parentStar.planetList.get(i).getPlanetRadius() * 4) || distanceFromStar > parentStar.planetList.get(i).getDistanceFromStar() - (parentStar.planetList.get(i).getPlanetRadius() * 4)) {
                        incompatible = true;
                    }
                }
                //if there's an incompatibility, regenerate the planet distance
                if (incompatible) {
                    distanceFromStar = randomNumber(distanceMin, distanceMax);
                }
            }
        } */

        return distanceFromStar;
    }

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

    //gets the habitability of the planet class
    protected boolean determineHabitability(int planetID) {
        return listOfPlanets.get(planetID).getHabitable();
    }

    protected int getPlanetFromID(int planetID) {
        for (int i = 0; i < listOfPlanets.size(); i++) {
            if (listOfPlanets.get(i).getPlanetID()== planetID) {
                return i;
            }
        }
        return 0;
    }

    //determines the class of the planet generated
    protected int determinePlanetClass(long distanceFromStar, int planetRadius, boolean tidalLock, boolean isHabitableZone, starClass parentStar){ //sets the planetType of the planet
        int randomPlanet; //gets a random number to determine the type of planet generated

        //first, clear the planetsToSpawn arraylist so we don't mix spawns.
        planetsToSpawn.clear();

        //if the planet is tidally locked, we want to give it a tidal climate
        if (tidalLock) {
            for (int i = 0; i < listOfPlanets.size(); i++) {
                if (listOfPlanets.get(i).getClimateID() == 2104) {
                    //this next check allows for spawn weighting, so a higher weight = higher chance to spawn
                    for (int j = 0; j <= listOfPlanets.get(i).getSpawnWeight(); j++) {
                        planetsToSpawn.add(listOfPlanets.get(i).getPlanetID()); //adds all planets with the tidal climate type to the list
                    }
                }
            }
        }

        //planet is very large, therefore spawn a gas giant
        if (planetRadius > 45000) {
            //Planet radius is very large, therefore it is going to be a Gas Giant.
            for (int i = 0; i < listOfPlanets.size(); i++) {
                if (listOfPlanets.get(i).getClimateID() == 2108) {
                    for (int j = 0; j <= listOfPlanets.get(i).getSpawnWeight(); j++) {
                        planetsToSpawn.add(listOfPlanets.get(i).getPlanetID()); //adds all planets with the gas giant type to the list
                    }
                }
            }
        }

        //planet is within the habitable zone and not a gas giant
        else if (isHabitableZone) {
            int hotSpawnMult = 0, coldSpawnMult = 0, tempSpawnMult = 0;
            boolean guaranteeGaia = false;

            //planet is closer to the far reaches of the habitable zone, therefore should be slightly colder in bias
            if (parentStar.getHabitableZoneMax() * AUtoKM - distanceFromStar > distanceFromStar - parentStar.getHabitableZoneMin() * AUtoKM) {
                coldSpawnMult = 20;
                hotSpawnMult = 0;
                tempSpawnMult = 5;
            } else if (parentStar.getHabitableZoneMax() * AUtoKM - distanceFromStar > distanceFromStar - parentStar.getHabitableZoneMin() * AUtoKM) {
                hotSpawnMult = 20;
                coldSpawnMult = 0;
                tempSpawnMult = 5;
            } else {
                guaranteeGaia = true;
            }

            //(Longest equation ever...) Temperate bias if the planet is close to the center of the habitable zone.
            if ((parentStar.getHabitableZoneMax() * AUtoKM - parentStar.getHabitableZoneMin() * AUtoKM) / 2 < distanceFromStar - parentStar.getHabitableZoneMin() * AUtoKM && ((((parentStar.getHabitableZoneMax() * AUtoKM) - (parentStar.getHabitableZoneMin() * AUtoKM)) / 2) * 3) > distanceFromStar - (parentStar.getHabitableZoneMin() * AUtoKM)) {
                tempSpawnMult = 50;
            }

            //if the planet is perfectly centered in the habitable zone, guarantee a gaia world
            if (guaranteeGaia) {
                for (int i = 0; i < listOfPlanets.size(); i++) {
                    if (listOfPlanets.get(i).getClimateID() == 2106) {
                        planetsToSpawn.add(listOfPlanets.get(i).getPlanetID());
                    }
                }
            } else {
                for (int i = 0; i < listOfPlanets.size(); i++) {
                    //gather all of the habitable planets and place them into the list
                    if (listOfPlanets.get(i).getClimateID() == 2106) { //Adds a weighted list of gaia type planets.
                        for (int j = 0; j <= listOfPlanets.get(i).getSpawnWeight(); j++) {
                            planetsToSpawn.add(listOfPlanets.get(i).getPlanetID());
                        }
                    } else if (listOfPlanets.get(i).getClimateID() == 2100) { //Adds a weighted list of temperate planets.
                        for (int j = 0; j <= listOfPlanets.get(i).getSpawnWeight() + tempSpawnMult; j++) {
                            planetsToSpawn.add(listOfPlanets.get(i).getPlanetID());
                        }
                    } else if (listOfPlanets.get(i).getClimateID() == 2101) { //Adds a weighted list of hot planets.
                        for (int j = 0; j <= listOfPlanets.get(i).getSpawnWeight() + hotSpawnMult; j++) {
                            planetsToSpawn.add(listOfPlanets.get(i).getPlanetID());
                        }
                    } else if (listOfPlanets.get(i).getClimateID() == 2102) { //Adds a weighted list of cold planets.
                        for (int j = 0; j <= listOfPlanets.get(i).getSpawnWeight() + coldSpawnMult; j++) {
                            planetsToSpawn.add(listOfPlanets.get(i).getPlanetID());
                        }
                    } else if (listOfPlanets.get(i).getClimateID() == 2105 || listOfPlanets.get(i).getClimateID() == 2103 || listOfPlanets.get(i).getClimateID() == 2107 || listOfPlanets.get(i).getClimateID() == 2106 ) { //Adds a weighted list of different barren/extreme planets as well as gaia
                        for (int j = 0; j <= listOfPlanets.get(i).getSpawnWeight(); j++) {
                            planetsToSpawn.add(listOfPlanets.get(i).getPlanetID());
                        }
                    }
                }
            }

        }

        //Planet is not within habitable zone, act accordingly
        else if (distanceFromStar < parentStar.getHabitableZoneMin() * AUtoKM) { //planet is closer to the star than the habitable zone, it'll be an extreme warm planet
            int extremeHotMult = 0;
            if (distanceFromStar < parentStar.getStarRadius() * 5) { //Planet is very close to the star, going to be extreme hot.
            extremeHotMult = 25;
            }
            for (int i = 0; i < listOfPlanets.size(); i++) {
                if (listOfPlanets.get(i).getClimateID() == 2109) { //Adds a weighted list of extreme hot planets
                    for (int j = 0; j <= listOfPlanets.get(i).getSpawnWeight() + extremeHotMult; j++) {
                        planetsToSpawn.add(listOfPlanets.get(i).getPlanetID());
                    }
                } else if (listOfPlanets.get(i).getClimateID() == 2105 || listOfPlanets.get(i).getClimateID() == 2103 || listOfPlanets.get(i).getClimateID() == 2107) { //Adds a weighted list of different barren/extreme planets
                    for (int j = 0; j <= listOfPlanets.get(i).getSpawnWeight(); j++) {
                        planetsToSpawn.add(listOfPlanets.get(i).getPlanetID());
                    }
                }
            }

        } else if (distanceFromStar > parentStar.getHabitableZoneMax() * AUtoKM) {
            //planet is further from the star than the habitable zone, it'll be a extreme cold planet
            int extremeColdMult = 0;
            if (distanceFromStar < parentStar.getStarRadius() * 5) { //Planet is very close to the star, going to be extreme hot.
                extremeColdMult = 25;
            }
            for (int i = 0; i < listOfPlanets.size(); i++) {
                if (listOfPlanets.get(i).getClimateID() == 2110) { //Adds a weighted list of extreme hot planets
                    for (int j = 0; j <= listOfPlanets.get(i).getSpawnWeight() + extremeColdMult; j++) {
                        planetsToSpawn.add(listOfPlanets.get(i).getPlanetID());
                    }
                } else if (listOfPlanets.get(i).getClimateID() == 2105 || listOfPlanets.get(i).getClimateID() == 2103 || listOfPlanets.get(i).getClimateID() == 2107) { //Adds a weighted list of different barren/extreme planets
                    for (int j = 0; j <= listOfPlanets.get(i).getSpawnWeight(); j++) {
                        planetsToSpawn.add(listOfPlanets.get(i).getPlanetID());
                    }
                }
            }
        }

        randomPlanet = randomNumber(0, planetsToSpawn.size() - 1); //gets a random number from the list
        return planetsToSpawn.get(randomPlanet); //returns the planet ID of the planet generated
    }

    protected int calculateSize(int starRadius, long distanceFromStar){

        if (distanceFromStar <= 4*starRadius) {

            if (800 <= randomNumber()) { //what's this?! weighted planet sizes?!
                //80% chance of being a fairly small planet
                return (240 + (int)(Math.random() * 2200));
            } else {
                //20% chance to have a slightly larger range
                return (120 + (int)(Math.random() * 3800));
            }

        } else if (distanceFromStar <= 15*starRadius && distanceFromStar > 4*starRadius) {

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

    protected boolean isInHabitableZone(long distanceFromStar, int upperBound, int lowerBound) {
        if ((lowerBound * AUtoKM) <= distanceFromStar && (upperBound * AUtoKM) >= distanceFromStar) {
            return true; //within habitable zone, return true
        } else {
            return false; //outside of habitable zone, return false
        }

    }

}
