package Core.techTree;

import Core.gameSettings;

import java.util.ArrayList;

/**
 * KM
 * June 06 2017
 * Framework designed to handle the processing of the tech tree and associated materials.
 *
 * CONCEPTS:
 * - Anonymous classes
 */


public class techCoreV2 implements techConstants {

    public ArrayList<techBuilder> techTree = new ArrayList<>(); //array list to store the tech tree information

    //stores a reference to the tech currently being researched in each line, default to null references
    public techBuilder currentResearch_1 = null;
    public techBuilder currentResearch_2 = null;
    public techBuilder currentResearch_3 = null;

    //constructor for the tech core
    public techCoreV2() {
        initializeTechTree();
    }

    private void initializeTechTree() { //load the tech tree data
        techTree.add(new techBuilder("String Drive Prototype", 0, 20, 1, TECH_PROPULSION, false, "The string drive is the result of our experiments into FTL technology. The final hurdle - staying on course - was solved by laying a long line of specially manufactured material, known as the string-line. String drives utilize this material to maintain course, allowing for extremely fast and efficient travel through the galaxy.") {
            @Override
            public void finishResearch() {
                gameSettings.FTLenabled = true; //enable the FTL portion of the game
            }
        });



    }

    /*

    techBuilder prop_StringDrive_02 = new techBuilder("String Drive", 2, 120, 100, TECH_PROPULSION, "By improving the efficiency of the string drive, we should be able to reduce the amount of material needed to maintain the string-lines.") {
        @Override
        public void finishResearch() {

        }
    };

    techBuilder prop_StringDrive_03 = new techBuilder("Advanced String Drive", 4, 450, 65, TECH_PROPULSION, "") {
        @Override
        public void finishResearch() {

        }
    };

    techBuilder infr_Arcologies = new techBuilder("Arcologies", 6, 2100, 1, TECH_INFRASTRUCTURE, "The arcology is a massive structure housing a large, self-sustaining population within it. With very minimal ecological impact, we can more effectively utilize the planet's resources without as high a risk of ecological disaster.") {
        @Override
        public void finishResearch() {

        }
    };

    techBuilder agri_VerticalFarming = new techBuilder("Vertical Farming", 1, 70, 90, TECH_AGRICULTURE, "") {
        @Override
        public void finishResearch() {

        }
    };

    */







}
