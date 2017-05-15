package Core;

import java.util.ArrayList;

/**
    KM
    May 13 2017
    Manages the player's technological progression through the game.
 */
public class techCore {

    public static ArrayList<ArrayList<tech>> techTree = new ArrayList<ArrayList<tech>>(); //arraylist that stores all of the different techs

    /** Constants **/

    final static String[] techPaths = {"Propulsion", "Infrastructure", "", ""}; //Names of the different tech paths


    /** Variable declarations **/

    int difficultyLevel;
    double techMultiplier;


    /** Constructors **/

    public techCore(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel; //gets the player's level of difficulty

    }


    /** Pre-loader method **/

    public static void techPreloader() {
        //go through and finish setting up the arraylist of techs
        for (int i = 0; i < techPaths.length; i++) {
            techTree.add(new ArrayList<tech>());
        }

        System.out.println("Tech tree data finished pre-loading.");
    }


    /** Simple Methods **/
    //Simple methods used by multiple other methods, generally to simplify functions.

    //generates a random number between 1-1000, generally used for percentages out of 100.0%
    protected int randomNumber(){
        return (1 + (int)(Math.random() * 1000));
    }

    //generates a random number between two defined values
    protected int randomNumber(int low, int high){
        return (low + (int)(Math.random() * high));
    }


    /** General Methods **/
    //General methods related to the tech core.



    /** Tech list **/
    //Stores the different technologies the player can unlock

    public static class tech {

        private int Line;
        private int ID;
        private int Cost;
        private int Level;
        private String Name;
        private String Desc;
        private int Rarity;

        public tech(String techName, String techDesc, int techID, int techLine, int techLevel, int techCost, int techRarity) {
            this.Name = techName; //the name of the technology
            this.Desc = techDesc; //description of what the tech does
            this.ID = techID;
            this.Line = techLine; //the type of tech
            this.Level = techLevel; //the level of tech it is
            this.Cost = techCost; //how much the tech costs to research
            this.Rarity = techRarity; //how rare the tech is

        }
    }


}