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

    private final double sunLuminosity = 3.828;

    protected ArrayList<starType> listOfStars = new ArrayList<>();
    protected ArrayList<Integer> spawnWeights = new ArrayList<>();

    /**
     Star type objects. Used to set the base description, class, etc of the star.
     **/

    private void createStarTypes() {
        listOfStars.add(new starType("Red Giant", "", 88,2800, 4600, true));
        listOfStars.add(new starType("Blue Giant", "", 56, 20000, 50000, true));
        listOfStars.add(new starType("Yellow Dwarf", "", 286, 5300, 6000, true));
        listOfStars.add(new starType("Red Dwarf", "", 210, 2500, 4000, true));
        listOfStars.add(new starType("White Dwarf", "", 52, 100000, 180000, true));
        listOfStars.add(new starType("Brown Dwarf", "", 53, 700, 1300, false));
        listOfStars.add(new starType("Wolf Rayet Star", "The spectra of Wolf-Rayet stars is highly unusual, dominated by highly ionized helium, nitrogen, and carbon. The star possesses a depleted supply of hydrogen, strong solar winds, and a temperature exceeding that of most other non-superdense stars.", 20, 30000, 200000, false));
        listOfStars.add(new starType("Neutron Star", "", 29, 500000, 720000, false));
        listOfStars.add(new starType("Pulsar", "A highly magnetized, rotating neutron star with a powerful focused beam of electromagnetic radiation ejected from both poles of the star. The fast, regular rotation of the star allows for incredible accuracy in keeping time - like a stellar clock.", 36, 520000, 750000, false));
        listOfStars.add(new starType("Black Hole", "", 25, 0, 0, false));
        listOfStars.add(new starType("Protostar", "A young star, still collecting mass from the molecular cloud it is forming within. Around a million years after forming, the Protostar will contract and form a main-sequence star.", 65, 2000, 3000, true));
        listOfStars.add(new starType("Supergiant", "", 15, 3500, 4500, true));
        listOfStars.add(new starType("Relativistic Star", "A fast rotating Neutron star with behavior better explained by general relativity than conventional physics. Relativistic stars allow for efficient studying of gravity and its properties.", 6, 500000, 720000, false));
        listOfStars.add(new starType("Magnetar", "A type of Neutron star with an extremely powerful magnetic field, which powers the continuous emission of high-energy x-rays and gamma rays. Occasional Starquakes rip through the surface of the star, triggering extremely powerful gamma ray flare emissions.", 19, 500000, 720000, false));
        listOfStars.add(new starType("Hypergiant", "A massively large star, thousands of times larger than most main-sequence stars. Hypergiants possess tremendous luminosities and a very high rate of mass loss through stellar wind. When it dies, it will likely collapse in a supernova that forms a Black Hole.", 11, 4000, 35000, false));
        listOfStars.add(new starType("Gravastar", "A Gravastar forms when a collapsing star's mass causes the gravitational acceleration to exceed Planck's length. This creates a region around the star that is a void in the fabric of space-time - a gravitational vacuum. It is theorized that a Gravastar may be responsible for the creation of the known universe.", 4, 0, 0, false));
        listOfStars.add(new starType("Thorne-Zytkow Object", "While appearing to look like a Supergiant, the core of this star is actually an active Neutron star. The Neutron star's incredibly high heat emission allows for uncommon isotopes to be formed during this star's fusion processes.", 11, 540000, 780000, false));
        listOfStars.add(new starType("Exotic Star", "A strange compact star, composed of quarks or bosons rather than the protons, neutrons, and electrons of conventional stars. While extraordinarily dense, they are unable to collapse due to quantum degeneracy pressure.", 2, 700000, 900000, false));

        starSpawnChanceWeighter();
    }
    //TODO: Tune the spawn weights to a more balanced level

    protected int totalSpawnWeight; //spawn weight of all of the star types combined

    private void starSpawnChanceWeighter() {
        spawnWeights.add(0); //adds the default 0 to the first slot in the spawn weights
        for (int i = 0; i < listOfStars.size(); i++) {
            //The total spawn weight amount of the stars combined, to simulate weights rather than percents
            totalSpawnWeight = totalSpawnWeight + listOfStars.get(i).getSpawn();
            //Creates an arraylist of the different spawn weights for each star (as a sum), this allows the program to figure out which star it is trying to generate.
            spawnWeights.add(totalSpawnWeight);
        }
    }

    //generates a random number between 1-1000, generally used for percentages out of 100.0%
    protected int randomNumber(){
        return (1 + (int)(Math.random() * 1000));
    }

    //generates a random number between two defined values
    protected int randomNumber(int low, int high){
        return (low + (int)(Math.random() * high));
    }

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

    private static class starType {

        private String name;
        private int spawn;
        private String desc;
        private int[] Temp = new int[2];
        boolean habitable;
        String spectral;

        /** Star IDs **/
        //

        private starType(String starName, String starDesc, int spawnChance, int surfaceTempLow, int surfaceTempHigh, boolean isHabitable){
            this.name = starName; //the type of star
            this.desc = starDesc; //the description for the star type
            this.spawn = spawnChance; //chance of this star being generated, as a weighted system
            this.Temp[0] = surfaceTempLow; //low end of the surface temperature of the star
            this.Temp[1] = surfaceTempHigh; //high end of the surface temperature of the star
            this.habitable = isHabitable; //whether or not the planet generator will attempt to spawn habitable planets around this star
        }

        private int getSpawn(){
            return this.spawn;
        }

    }


}