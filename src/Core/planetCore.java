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
    protected ArrayList<Integer> spawnWeights = new ArrayList<>();


    /** Constants **/
    //Variables used in place of constant numbers either for simplicity or reusability. Also stores it in one easy-to-access place at the top of the class.

    //Conversion constants.
    private final double AUtoKM = 14959.7870700; //converts au to million KM
    private final double bolometricEarthConst = 4.72; //bolometric constant for Earth

    private final int tidalLockChance = 13; //chance that the planet will be tidally locked


    /** Atmosphere generation **/
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


    /** Planet creation **/
    //Used to create the blueprints for different planet types from a blueprint class (planetTypes).

    //Sets all of the predefined blueprints into a variable ArrayList (listOfPlanets) to alow for dynamic addition/removal of planet types.
    private void createPlanetTypes(){
        listOfPlanets.add(new planetType("Continental World", "The landscape is dotted with numerous large continents and a temperate climate.", 1, 1, 1));
        listOfPlanets.add(new planetType("Oceanic World", "Small islands poke out of the massive oceans encompassing this world. Very little of the planet is actually above water.", 2, 1, 1));
        listOfPlanets.add(new planetType("Wetlands World", "Thousands of rivers flow through the landscape of this planet. Much of the planet is covered with perpetual monsoon weather.", 3, 1, 1));
        listOfPlanets.add(new planetType("Alpine World", "Large mountain ranges, young and old, spread across the landscape of this planet.", 4, 2, 1));
        listOfPlanets.add(new planetType("Tundra World", "The surface of this planet is fairly barren, with large swaths of frozen ground. Some liquid water can be found around the equatorial region, but it is otherwise frozen.", 5, 2, 1));
        listOfPlanets.add(new planetType("Ice World", "This planet is covered in a surface almost completely composed of frozen water. In some places, the ice extends for kilometers under the surface before thawing.", 6, 2, 2));
        listOfPlanets.add(new planetType("Desert World", "This planet is covered in a surface almost completely composed of frozen water. In some places, the ice extends for kilometers under the surface before thawing.", 7, 3, 1));
        listOfPlanets.add(new planetType("Swamp World", "", 8, 3, 1));
        listOfPlanets.add(new planetType("Tropical World", "", 9, 3, 1));
        listOfPlanets.add(new planetType("Greenhouse World", "The atmosphere of this planet is thick with greenhouse gases, perpetually feeding an endless heating cycle in the atmosphere.", 10, 4, 1));
        listOfPlanets.add(new planetType("Frozen World", "", 11, 4, 2));
        listOfPlanets.add(new planetType("Molten World", "", 12, 4, 2));
        listOfPlanets.add(new planetType("Storm World", "Never ending storms batter the surface of this hostile planet, and most of the atmosphere is covered in a thick layer of storm clouds.", 13, 4, 1));
        listOfPlanets.add(new planetType("Gas Giant", "", 14, 5, 1));
        listOfPlanets.add(new planetType("Barren World", "", 15, 5, 0));
        listOfPlanets.add(new planetType("Damaged World", "", 16, 5, 0));
        listOfPlanets.add(new planetType("Tidal World", "", 17, 6, 0));
        listOfPlanets.add(new planetType("Radioactive World", "The surface of this planet is littered with radioactive ore deposits. Rapidly degrading, they spew out massive swaths of radiation across the planet's surface.", 18, 5, 0));
        listOfPlanets.add(new planetType("Carbon World", "", 19, 5, 0));
        listOfPlanets.add(new planetType("Iron World", "This world possesses an unusually high iron content, and is unusually dense as a result. It is likely that nearly 60% of the planet's material is iron based.", 20, 5, 0));
        //listOfPlanets.add();

    }

    private static class planetType {

        private String className;
        private int IDvalue, climateID, atmosphere;
        private String classDesc;

        private planetType(String name, String description, int ID, int climate, int atmosphereType){
            this.className = name;
            this.IDvalue = ID;
            this.climateID = climate;
            this.classDesc = description;
            this.atmosphere = atmosphereType;
        }


    }

    //------------------------------------------------------------------------------------------------------------------

    protected boolean checkTidalLock(){
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

}
