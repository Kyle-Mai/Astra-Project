package Core;

/**
    KM
    April 19 2017
    Handles the construction and manipulation of the planet class.
 */

//TODO: Rebuild in the same format as the starClass/starCore.

public class planetClass {

    /** other conversion constants **/
    private final double AUtoKM = 14959.7870700; //converts au to million KM
    private final double bolometricEarthConst = 4.72; //bolometric constant for Earth

    /** tidal lock checks **/
    //chance that the planet will be tidally locked
    private final int tidalLockChance = 13; //1.3%

    /** atmosphere generation **/
    //parameters are passed to the checkAtmosphere function which determines whether or not the planet has an atmosphere based on the radius
    private final int atmosphereChanceTiny = 15; //700m radius and below, 1.5%
    private final int atmosphereChanceSmall = 180; // 700-1500m radius, 18% chance
    private final int atmosphereChanceAverage = 360; //1500 - 2500, 36%
    private final int atmosphereChanceLarge = 650; //2500 - 4000, 65%
    private final int atmosphereChanceHuge = 950; //4000+, 95%
    private final int atmosphereChanceGiant = 1000; //gas giant
    //next set checks how close it is to the star and adjusts it accordingly
    private final double atmosphereChanceMultiplierClose = 0; //unused right now

    /** planet size declarations **/
    //these declare the upper bounds of the different radiuses in which the planets are classified
    private final int planetTiny = 700; //0 to this number
    private final int planetSmall = 1500; //planetTiny to this number, etc
    private final int planetAverage = 2500;
    private final int planetLarge = 4000;
    private final int planetHuge = 40000;
    //giant is not declared, because it goes on to inf.

    /** planet types **/
    //used when determining the type of planet generated

    planetType continentalWorld = new planetType("Continental World", "The landscape is dotted with numerous large continents and a temperate climate.", 1, 1, 1);
    planetType oceanicWorld = new planetType("Oceanic World", "Small islands poke out of the massive oceans encompassing this world. Very little of the planet is actually above water.", 2, 1, 1);
    planetType wetlandsWorld = new planetType("Wetlands World", "Thousands of rivers flow through the landscape of this planet. Much of the planet is covered with perpetual monsoon weather.", 3, 1, 1);
    planetType alpineWorld = new planetType("Alpine World", "Large mountain ranges, young and old, spread across the landscape of this planet.", 4, 2, 1);
    planetType tundraWorld = new planetType("Tundra World", "The surface of this planet is fairly barren, with large swaths of frozen ground. Some liquid water can be found around the equatorial region, but it is otherwise frozen.", 5, 2, 1);
    planetType iceWorld = new planetType("Ice World", "This planet is covered in a surface almost completely composed of frozen water. In some places, the ice extends for kilometers under the surface before thawing.", 6, 2, 2);
    planetType desertWorld = new planetType("Desert World", "This planet is covered in a surface almost completely composed of frozen water. In some places, the ice extends for kilometers under the surface before thawing.", 7, 3, 1);
    planetType swampWorld = new planetType("Swamp World", "", 8, 3, 1);
    planetType tropicalWorld = new planetType("Tropical World", "", 9, 3, 1);
    planetType greenhouseWorld = new planetType("Greenhouse World", "The atmosphere of this planet is thick with greenhouse gases, perpetually feeding an endless heating cycle in the atmosphere.", 10, 4, 1);
    planetType toxicWorld = new planetType("Frozen World", "", 11, 4, 2);
    planetType moltenWorld = new planetType("Molten World", "", 12, 4, 2);
    planetType stormWorld = new planetType("Storm World", "Never ending storms batter the surface of this hostile planet, and most of the atmosphere is covered in a thick layer of storm clouds.", 13, 4, 1);
    planetType gasGiantWorld = new planetType("Gas Giant", "", 14, 5, 1);
    planetType barrenWorld = new planetType("Barren World", "", 15, 5, 0);
    planetType damagedWorld = new planetType("Damaged World", "", 16, 5, 0);
    planetType tidalWorld = new planetType("Tidal World", "", 17, 6, 0);
    planetType radioactiveWorld = new planetType("Radioactive World", "The surface of this planet is littered with radioactive ore deposits. Rapidly degrading, they spew out massive swaths of radiation across the planet's surface.", 18, 5, 0);
    planetType carbonWorld = new planetType("Carbon World", "", 19, 5, 0);
    planetType ironWorld = new planetType("Iron World", "This world possesses an unusually high iron content, and is unusually dense as a result. It is likely that nearly 60% of the planet's material is iron based.", 20, 5, 0);

    //------------------------------------------------------------------------------------------------------------------

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
        this.isTidallyLocked = checkTidalLock(); //runs a check to determine tidal lock
        this.planetRadius = planetSize();

        this.hasAtmosphere = checkAtmosphere();

    }

    public planetClass(int parentStar, String planetName) {
        this.parentStar = parentStar;

    }

    private int randomNumber(){
        return (1 + (int)(Math.random() * 1000));
    } //returns a random number

    private boolean checkTidalLock(){
        //in order to be tidally locked, the planet must be within 6* the star's radius
        if (this.planetRadius < (6*starRadius)) {
            //check whether or not it's tidally locked based on the % chance via a random #
            if (randomNumber() <= tidalLockChance) {
                return true;
            }
        }
        return false;
    } //checks whether or not the planet is tidally locked

    private int planetSize(){

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

    private boolean checkAtmosphere(){
        //first run a check to determine proximity to star
        //planets too close to star will have a very thin or no atmosphere
        if (distanceFromStar <= starRadius * 2) {
            return false;
        }

        switch (planetType) { //uses the planet type to run the next check


        }

        //next, check the size of the planet
        if (planetRadius <= planetTiny) { //tiny planet
            if (randomNumber() <= atmosphereChanceTiny) {
                return true;
            }
        } else if (planetRadius > planetTiny && planetRadius <= planetSmall) { //small planet
            if (randomNumber() <= atmosphereChanceSmall) {
                return true;
            }
        } else if (planetRadius > planetSmall && planetRadius <= planetAverage) { //average planet
            if (randomNumber() <= atmosphereChanceAverage) {
                return true;
            }
        } else if (planetRadius > planetAverage && planetRadius <= planetLarge) { //large planet
            if (randomNumber() <= atmosphereChanceLarge) {
                return true;
            }
        } else if (planetRadius > planetLarge && planetRadius <= planetHuge) { //huge planet
            if (randomNumber() <= atmosphereChanceHuge) {
                return true;
            }
        } else if (planetRadius > planetHuge) {
            if (randomNumber() <= atmosphereChanceGiant) { //giant planet
                return true;
            }
        }
        //failed the checks, no atmosphere
        return false;
    } //checks whether or not the planet has an atmosphere

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


    private static class planetType {

        private String className;
        private int IDvalue, climateID, atmosphere;
        private String classDesc;

        /** Planet IDs **/
        //


        private planetType(String name, String description, int ID, int climate, int atmosphereType){
            this.className = name;
            this.IDvalue = ID;
            this.climateID = climate;
            this.classDesc = description;
            this.atmosphere = atmosphereType;
        }


    }



}
