package Core;

import java.util.ArrayList;

/**

 KM
 May 02 2017
 Handles the core work of the star class.
 Most of the stuff here is run once through the stars at the start of their generation and then never again in the star afterwards.
 Essentially, non-specific methods and variables related to ALL of the stars, rather than specific stars.

 **/

//TODO: Finish the listOfStars array variables and whatnot.

public class starCore {

    /** ArrayLists **/
    //Used to store bulk information of like types.

    private ArrayList<starType> listOfStars = new ArrayList<>(); //Stores a list of the different starType blueprints.
    private ArrayList<Integer> spawnWeights = new ArrayList<>(); //Stores a list of the sums of the spawn weights of the star blueprints.


    /** Constants **/
    //The stuff listed here, while variable, is not meant to change after the program starts. It is to simplify editing values going forwards, so only one value (which is easy to find) needs changing.

    private final double sunLuminosity = 3.828;
    private final int binaryChance = 56; //Chance for a star to be a part of a binary system (2 stars) Measured as 1 = 0.1%.


    /** Star creation **/
    //Used to set the base description, class, etc of the star.

    //Sets up an ArrayList (listOfStars) with all of the different declared star types in it. Allows for dynamic addition/removal of different star types as we need to add them. Also organizes all of the star blueprint information in one easy to access place.
    private void createStarTypes() {
        listOfStars.add(new starType("Red Giant", 1000, 210, "", 2800, 4600, true));
        listOfStars.add(new starType("Blue Giant", 1001, 109, "", 20000, 50000, true));
        listOfStars.add(new starType("Yellow Dwarf", 1002, 355, "", 5300, 6000, true));
        listOfStars.add(new starType("Red Dwarf", 1003, 290, "", 2500, 4000, true));
        listOfStars.add(new starType("White Dwarf", 1004, 135, "", 100000, 180000, true));
        listOfStars.add(new starType("Brown Dwarf", 1005, 87, "", 700, 1300, false));
        listOfStars.add(new starType("Wolf Rayet Star", 1006, 32, "The spectra of Wolf-Rayet stars is highly unusual, dominated by highly ionized helium, nitrogen, and carbon. The star possesses a depleted supply of hydrogen, strong solar winds, and a temperature exceeding that of most other non-superdense stars.", 30000, 200000, false));
        listOfStars.add(new starType("Neutron Star", 1007, 62, "", 500000, 720000, false));
        listOfStars.add(new starType("Pulsar", 1008, 45, "A highly magnetized, rotating neutron star with a powerful focused beam of electromagnetic radiation ejected from both poles of the star. The fast, regular rotation of the star allows for incredible accuracy in keeping time - like a stellar clock.", 520000, 750000, false));
        listOfStars.add(new starType("Black Hole", 1009, 31, "", 0, 0, false));
        listOfStars.add(new starType("Protostar", 1010, 98, "A young star, still collecting mass from the molecular cloud it is forming within. Around a million years after forming, the Protostar will contract and form a main-sequence star.",2000, 3000, true));
        listOfStars.add(new starType("Supergiant", 1011, 22, "", 3500, 4500, true));
        listOfStars.add(new starType("Relativistic Star", 1012, 8, "A fast rotating Neutron star with behavior better explained by general relativity than conventional physics. Relativistic stars allow for efficient studying of gravity and its properties.", 500000, 720000, false));
        listOfStars.add(new starType("Magnetar", 1013, 26, "A type of Neutron star with an extremely powerful magnetic field, which powers the continuous emission of high-energy x-rays and gamma rays. Occasional Starquakes rip through the surface of the star, triggering extremely powerful gamma ray flare emissions.", 500000, 720000, false));
        listOfStars.add(new starType("Hypergiant", 1014, 17, "A massively large star, thousands of times larger than most main-sequence stars. Hypergiants possess tremendous luminosities and a very high rate of mass loss through stellar wind. When it dies, it will likely collapse in a supernova that forms a Black Hole.", 4000, 35000, false));
        listOfStars.add(new starType("Gravastar", 1015, 6, "A Gravastar forms when a collapsing star's mass causes the gravitational acceleration to exceed Planck's length. This creates a region around the star that is a void in the fabric of space-time - a gravitational vacuum. It is theorized that a Gravastar may be responsible for the creation of the known universe." 0, 0, false));
        listOfStars.add(new starType("Thorne-Zytkow Object", 15, 1016, "While appearing to look like a Supergiant, the core of this star is actually an active Neutron star. The Neutron star's incredibly high heat emission allows for uncommon isotopes to be formed during this star's fusion processes.", 540000, 780000, false));
        listOfStars.add(new starType("Exotic Star", 3, 1017, "A strange compact star, composed of quarks or bosons rather than the protons, neutrons, and electrons of conventional stars. While extraordinarily dense, they are unable to collapse due to quantum degeneracy pressure.", 700000, 900000, false));

    }

    //Gets the different spawnChance values of the star blueprints (listOfStars) and organizes them by sum in an ArrayList (spawnWeights).
    private void starSpawnChanceWeighter() {
        spawnWeights.add(0); //adds the default 0 to the first slot in the spawn weights
        for (int i = 0; i < listOfStars.size(); i++) {
            //The total spawn weight amount of the stars combined, to simulate weights rather than percents
            totalSpawnWeight = totalSpawnWeight + listOfStars.get(i).getSpawn();
            //Stores the sum of (i) plus all of the previously glossed over stars in the spawnWeights array to simulate different markers in a number between 0 and X, with X being the sum of all of the spawnChance values combined.
            spawnWeights.add(totalSpawnWeight);
        }
    }

    //"Class" dedicated to storing star blueprints. Used sort-of like an array that can store multiple value types.
    private static class starType {
        //Variables dedicated to the starType blueprint. See constructor for detailed information.
        String name;
        int spawn;
        String desc;
        int[] Temp = new int[2];
        boolean habitable;
        String spectral;
        int starID;

        //Constructor for building different star blueprints in the createStarTypes method listed above.
        private starType(String starName, int objectID, int spawnWeight, String starDesc, int surfaceTempLow, int surfaceTempHigh, boolean isHabitable){
            this.name = starName; //the type of star
            this.desc = starDesc; //the description for the star type
            this.starID = objectID; //the ID used to identify the star.
            this.spawn = spawnWeight; //chance of this star being generated, as a weighted system
            this.Temp[0] = surfaceTempLow; //low end of the surface temperature of the star
            this.Temp[1] = surfaceTempHigh; //high end of the surface temperature of the star
            this.habitable = isHabitable; //whether or not the planet generator will attempt to spawn habitable planets around this star
        }

        // Accessor methods for calling the variables of the starType blueprints.
        private int getSpawn() { return this.spawn; }
        private String getSpectral() { return this.spectral; }
        private int getStarID() { return this.starID; }

    }


    /** General variables **/
    //General variables used by the starCore class.

    protected int totalSpawnWeight; //spawn weight of all of the star types combined

    //------------------------------------------------------------------------------------------------------------------

    /** Pre-loader method **/
    //Method that MUST be initialized when the class first runs -- ALWAYS! Or else the program will break.

    //Calls and runs all of the vital methods that must run before anything else.
    public void starPreloader(){
        createStarTypes();
        starSpawnChanceWeighter();

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
                return (i - 1);
            }
        }
        return 0; //redundancy
    }

    //returns a randomized surface temperature of the star based on the upper and lower limits of the star class.
    protected int determineSurfaceTemperature(int starIndex){
        return randomNumber(listOfStars.get(starIndex).Temp[0], listOfStars.get(starIndex).Temp[1]);
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


}