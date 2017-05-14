package Core;

import java.util.ArrayList;

/**
    KM
    May 13 2017
    Manages the player's technological progression through the game.
 */
public class techCore {

    ArrayList<ArrayList<tech>> techTree = new ArrayList<ArrayList<tech>>(); //arraylist that stores all of the different techs

    /** Constants **/

    final String[] techPaths = {"FTL Travel", "Infrastructure", "Kinetics", ""}; //Names of the different tech paths


    /** Variable declarations **/

    int difficultyLevel;
    double techMultiplier;


    /** Constructors **/

    public techCore(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel; //gets the player's level of difficulty

    }


    /** Pre-loader method **/

    public void techPreloader() {
        //go through and finish setting up the arraylist of techs
        for (int i = 0; i < techPaths.length; i++) {
            techTree.add(new ArrayList<tech>());
        }

        fillTechTree(); //fills out the tech tree with level

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

    private class tech {

        private double Cost;
        private int Level;
        private String Name;
        private String Desc;
        private int Rarity;

        private tech(String techName, String techDesc, int techLevel, double techCost, int techRarity) {
            this.Name = techName; //the name of the technology
            this.Desc = techDesc; //description of what the tech does
            this.Level = techLevel; //the level of tech it is
            this.Cost = techCost; //how much the tech costs to research
            this.Rarity = techRarity;

        }
    }

    //fills the tech tree with all necessary information
    private void fillTechTree() {
        //TODO: Maybe convert techs over to XML format...? Unknown as to the best format to handle this.

        //goofy method of sorting different techs using a for/switch
        for (int i = 0; i < techPaths.length; i++) {
            switch (i) {

                case 0: // FTL Drive tech line
                    techTree.get(i).add(new tech(
                            "String Drive", "Our research into the mystery behind FTL travel is nearly complete. With this final step, we should be able to create a working FTL drive large enough to use on interstellar spacecraft.", 1, 50 * techMultiplier, 1
                    ));

                    techTree.get(i).add(new tech(
                            "Advanced String Drive", "By improving the efficiency of the string drive, we should be able to reduce the amount of material needed to maintain the string-lines.", 2, 120 * techMultiplier, 1
                    ));
                    break;
                case 1: //Infrastructure tech line
                    techTree.get(i).add(new tech(
                            "", "", 2, 80 * techMultiplier, 1
                    ));
                    break;
                case 2:
                    break;
                case 3:
                    break;
            }
        }


    }


}