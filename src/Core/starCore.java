package Core;

import java.util.ArrayList;

/**
 KM
 May 02 2017
 Handles the core work of the star class.
 Most of the stuff here is run once through the stars at the start of their generation and then never again in the star afterwards.
 Essentially, non-specific methods and variables related to ALL of the stars, rather than specific stars.
 **/


public class starCore {

    /** ArrayLists **/
    //Used to store bulk information of like types.

    public static ArrayList<starType> listOfStars = new ArrayList<>(); //Stores a list of the different starType blueprints.
    private static ArrayList<Integer> spawnWeights = new ArrayList<>(); //Stores a list of the sums of the spawn weights of the star blueprints.


    /** Constants **/
    //The stuff listed here, while variable, is not meant to change after the program starts. It is to simplify editing values going forwards, so only one value (which is easy to find) needs changing.

    private final double sunLuminosity = 3.828;
    private final double bolometricEarthConst = 4.72; //bolometric constant for Earth
    private final int binaryChance = 56; //Chance for a star to be a part of a binary system (2 stars) Measured as 1 = 0.1%.


    /** General variables **/
    //General variables used by the starCore class.

    private static int totalSpawnWeight; //spawn weight of all of the star types combined


    /** Star creation **/
    //Used to set the base description, class, etc of the star.

    //Sets up an ArrayList (listOfStars) with all of the different declared star types in it. Allows for dynamic addition/removal of different star types as we need to add them. Also organizes all of the star blueprint information in one easy to access place.
    private static void createStarTypes() {
        listOfStars.add(new starType("Red Giant", 1000, 210, "", 2800, 4600, true, 0, 0, 4));
        listOfStars.add(new starType("Blue Giant", 1001, 109, "", 20000, 50000, true, 0, 0, 4));
        listOfStars.add(new starType("Yellow Dwarf", 1002, 355, "", 5300, 6000, true, 0, 0, 5));
        listOfStars.add(new starType("Red Dwarf", 1003, 290, "", 2500, 4000, true, 0, 0, 0));
        listOfStars.add(new starType("White Dwarf", 1004, 135, "", 100000, 180000, true, 15, 5, 2));
        listOfStars.add(new starType("Brown Dwarf", 1005, 87, "", 700, 1300, false, 0, 0, 0));
        listOfStars.add(new starType("Wolf Rayet Star", 1006, 32, "The spectra of Wolf-Rayet stars is highly unusual, dominated by highly ionized helium, nitrogen, and carbon. The star possesses a depleted supply of hydrogen, strong solar winds, and a temperature exceeding that of most other non-superdense stars.", 30000, 200000, false, 0, 0, 3));
        listOfStars.add(new starType("Neutron Star", 1007, 62, "", 500000, 720000, false, 12, 4, 3));
        listOfStars.add(new starType("Pulsar", 1008, 45, "A highly magnetized, rotating neutron star with a powerful focused beam of electromagnetic radiation ejected from both poles of the star. The fast, regular rotation of the star allows for incredible accuracy in keeping time - like a stellar clock.", 520000, 750000, false, 12, 4, 3));
        listOfStars.add(new starType("Black Hole", 1009, 31, "", 0, 0, false, 0, 0, 0));
        listOfStars.add(new starType("Protostar", 1010, 98, "A young star, still collecting mass from the molecular cloud it is forming within. Around a million years after forming, the Protostar will contract and form a main-sequence star.",2000, 3000, true, 0, 0, 2));
        listOfStars.add(new starType("Supergiant", 1011, 22, "", 3500, 4500, true, 0, 0, 6));
        listOfStars.add(new starType("Relativistic Star", 1012, 8, "A fast rotating Neutron star with behavior better explained by general relativity than conventional physics. Relativistic stars allow for efficient studying of gravity and its properties.", 500000, 720000, false, 12, 4, 0));
        listOfStars.add(new starType("Magnetar", 1013, 26, "A type of Neutron star with an extremely powerful magnetic field, which powers the continuous emission of high-energy x-rays and gamma rays. Occasional Starquakes rip through the surface of the star, triggering extremely powerful gamma ray flare emissions.", 500000, 720000, false, 12, 4, 3));
        listOfStars.add(new starType("Hypergiant", 1014, 17, "A massively large star, thousands of times larger than most main-sequence stars. Hypergiants possess tremendous luminosities and a very high rate of mass loss through stellar wind. When it dies, it will likely collapse in a supernova that forms a Black Hole.", 4000, 35000, false, 0, 0, 7));
        //listOfStars.add(new starType("Gravastar", 1015, 6, "A Gravastar forms when a collapsing star's mass causes the gravitational acceleration to exceed Planck's length. This creates a region around the star that is a void in the fabric of space-time - a gravitational vacuum. It is theorized that a Gravastar may be responsible for the creation of the known universe.", 0, 0, false, 0, 0, 0));
        //listOfStars.add(new starType("Thorne-Zytkow Object", 1016, 15, "While appearing to look like a Supergiant, the core of this star is actually an active Neutron star. The Neutron star's incredibly high heat emission allows for uncommon isotopes to be formed during this star's fusion processes.", 540000, 780000, false, 0, 0, 5));
        //listOfStars.add(new starType("Exotic Star", 1017, 3, "A strange compact star, composed of quarks or bosons rather than the protons, neutrons, and electrons of conventional stars. While extraordinarily dense, they are unable to collapse due to quantum degeneracy pressure.", 700000, 900000, false, 0, 0, 0));

    }

    //Gets the different spawnChance values of the star blueprints (listOfStars) and organizes them by sum in an ArrayList (spawnWeights).
    private static void starSpawnChanceWeighter() {
        spawnWeights.add(0); //adds the default 0 to the first slot in the spawn weights
        for (int i = 0; i < listOfStars.size(); i++) {
            //The total spawn weight amount of the stars combined, to simulate weights rather than percents
            totalSpawnWeight = totalSpawnWeight + listOfStars.get(i).getSpawn();
            //Stores the sum of (i) plus all of the previously glossed over stars in the spawnWeights array to simulate different markers in a number between 0 and X, with X being the sum of all of the spawnChance values combined.
            spawnWeights.add(totalSpawnWeight);
        }
    }

    //"Class" dedicated to storing star blueprints. Used sort-of like an array that can store multiple value types.
    public static class starType {
        //Variables dedicated to the starType blueprint. See constructor for detailed information.
        String name;
        int spawn;
        String desc;
        int tempLow;
        int tempHigh;
        boolean habitable;
        String spectral;
        int starID;
        int sizeWeight;
        int sizeVariation;
        int planetWeight;

        //Constructor for building different star blueprints in the createStarTypes method listed above.
        public starType(String starName, int objectID, int spawnWeight, String starDesc, int surfaceTempLow, int surfaceTempHigh, boolean isHabitable, int sizeWeight, int sizeVariation, int planetWeight){
            this.name = starName; //the type of star
            this.desc = starDesc; //the description for the star type
            this.starID = objectID; //the ID used to identify the star.
            this.spawn = spawnWeight; //chance of this star being generated, as a weighted system
            this.tempLow = surfaceTempLow; //low end of the surface temperature of the star
            this.tempHigh = surfaceTempHigh; //high end of the surface temperature of the star
            this.habitable = isHabitable; //whether or not the planet generator will attempt to spawn habitable planets around this star
            this.sizeWeight = sizeWeight; //The average radius of this star type.
            this.sizeVariation = sizeVariation; //Adding and subtracted from the median to get the max/min radius of the star.
            this.planetWeight = planetWeight; //the optimal number of planets this star will attempt to generate around it, a zero will give the spawn chance complete randomness

        }

        // Accessor methods for calling the variables of the starType blueprints.
        private int getSpawn() { return this.spawn; }
        private String getSpectral() { return this.spectral; }
        public int getStarID() { return this.starID; }
        private boolean getHabitable() { return this.habitable; }
        private int getSizeWeight() { return this.sizeWeight; }
        private int getSizeVariation() { return this.sizeVariation; }
        private int getPlanetWeight() { return this.planetWeight; }

    }


    //------------------------------------------------------------------------------------------------------------------

    /** Pre-loader method **/
    //Method that MUST be initialized when the class first runs -- ALWAYS! Or else the program will break.

    //Calls and runs all of the vital methods that must run before anything else.
    public static void starPreloader(){
        createStarTypes();
        starSpawnChanceWeighter();
        System.out.println("starCore successfully preloaded.");

    }


    /** Simple methods **/
    //Methods used frequently within the class, such as random number generation and whatnot.

    //generates a random number between 1-1000, generally used for percentages out of 100.0%
    protected int randomNumber(){
        return (1 + (int)(Math.random() * 1000));
    }

    //generates a random number between two defined values
    protected int randomNumber(int low, int high){
        return (low + (int)(Math.random() * high));
    }

    /** General methods **/
    //General starCore methods, generally used for constructing the specific star (starClass) from the blueprint (starType).

    //determines the star's type / class
    protected int chooseStarType(){
        int starToGenerate = randomNumber(1, totalSpawnWeight);

        //go through the spawn weights arraylist to determine which star is being spawned
        for (int i = 1; i < spawnWeights.size(); i++) {
            //if the random number is less than the current value, but greater than the previous one, this is the star we're generating
            if (starToGenerate <= spawnWeights.get(i) && starToGenerate > spawnWeights.get(i - 1)) {
                return listOfStars.get(i-1).getStarID(); //Returns the ID of the star
            }
        }

        return 0; //redundancy
    }

    protected int getStarFromID(int starID) {
        for (int i = 0; i < listOfStars.size(); i++) {
            if (listOfStars.get(i).getStarID() == starID) {
                return i;
            }
        }
        return 0;
    }

    protected int determineNumOfPlanets(int starIndex){
        int weight = listOfStars.get(starIndex).getPlanetWeight();
        int planetsToSpawn = 0; //Number of planets to generate
        int spawnChance = 850; //Initial spawn chance.


        //until the random number is greater than the spawn chance, keep generating planets
        while (randomNumber() < spawnChance) {
            planetsToSpawn++; //adds another planet to the queue

            //changes the spawn chance dynamically based on the weight
            if (planetsToSpawn < weight) {
                spawnChance = spawnChance - 50;
            } else {
                spawnChance = spawnChance - 150;
            }
        }

        return planetsToSpawn;
    }

    //returns a randomized surface temperature of the star based on the upper and lower limits of the star class.
    protected int determineSurfaceTemperature(int starIndex){
        return randomNumber(listOfStars.get(starIndex).tempLow, listOfStars.get(starIndex).tempHigh);
    }

    protected boolean determineHabitability(int starIndex) {
        return listOfStars.get(starIndex).getHabitable();
    }

    //checks whether or not the generated system will have a binary star
    protected boolean determineBinary(){
        if (randomNumber() < binaryChance) {
            return true;
        } else {
            return false;
        }
    }

    //returns the radius of the star based on the weight and variation
    //the weight determines the median size, and the variation determines the max and min
    protected int determineRadius(int starIndex){
        return randomNumber(
                listOfStars.get(starIndex).getSizeWeight() - listOfStars.get(starIndex).getSizeVariation(),
                listOfStars.get(starIndex).getSizeWeight() + listOfStars.get(starIndex).getSizeVariation()
        );
    }

    //determines the spectral class of the star based on the surface temperature
    protected String determineSpectralClass(int surfaceTemperature){
        if (surfaceTemperature >= 25000) {
            return "O"; //blue
        } else if (surfaceTemperature >= 11000 && surfaceTemperature < 25000) {
            return "B"; //blue
        } else if (surfaceTemperature >= 7500 && surfaceTemperature < 11000) {
            return "A"; //blue to white
        } else if (surfaceTemperature >= 6000 && surfaceTemperature < 7500) {
            return "F"; //blue to white
        } else if (surfaceTemperature >= 5000 && surfaceTemperature < 6000) {
            return "G"; //stars similar to the sun, yellow to white
        } else if (surfaceTemperature >= 3500 && surfaceTemperature < 5000) {
            return "K"; //orange to red
        } else if (surfaceTemperature <= 3500 && surfaceTemperature > 400) {
            return "M"; //red
        } else if (surfaceTemperature < 400) {
            return "?"; //unknown, black hole generally
        }

        return "?";
    }

    //Determines the habitable zone of the star
    protected Double determineLuminosity(String starSpectral, int starMagnitude){
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
        } else if (starSpectral.equals("A")) {
            BC = -0.3;
        } else if (starSpectral.equals("F")) {
            BC = -0.15;
        } else if (starSpectral.equals("G")) {
            BC = -0.4;
        } else if (starSpectral.equals("K")) {
            BC = -0.8;
        } else if (starSpectral.equals("O")) {
            BC = -4.0;
        }

        //determines the bolometric magnitude of the star
        bolMagnitude = absMagnitude + BC;

        //determines the absolute luminosity of the star based on the bolometric magnitude and the constant for the Earth (4.72)
        absLum = (Math.pow((10),(bolMagnitude - bolometricEarthConst) / -2.5));

        return absLum;
    }

    protected int habitableZoneMax(Double absoluteLuminosity) {
        Double upperBound = Math.sqrt(absoluteLuminosity / 0.53);
        int habitableZoneUpper = upperBound.intValue();
        return habitableZoneUpper;
    }

    protected int habitableZoneMin(Double absoluteLuminosity) {
        Double lowerBound = Math.sqrt(absoluteLuminosity / 1.1);
        int habitableZoneLower = lowerBound.intValue();
        return habitableZoneLower;
    }


}