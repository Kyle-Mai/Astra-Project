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
        techTree.add(new techBuilder("String Drive Prototype", 0, 20, 100, TECH_PROPULSION, false, "tech_string_drive_1.png", "The string drive is the result of our experiments into FTL technology. The final hurdle - staying on course - was solved by laying a long line of specially manufactured material, known as the string-line. String drives utilize this material to maintain course, allowing for extremely fast and efficient travel through the galaxy.") {
            @Override
            public void finishResearch() {
                gameSettings.FTLenabled = true; //enable the FTL portion of the game
                gameSettings.shipbuilder.shipStorage.get(gameSettings.shipbuilder.shipStorage.indexOf(gameSettings.shipbuilder.s_trailblazer)).setUnlocked(); //unlock the trailblazer ship
                System.out.println("String Drive Prototype researched! The FTL system has been enabled.");
            }
        });

        techTree.add(new techBuilder("New Worlds Protocol", 0, 35, 100, TECH_SOCIOLOGY, false, "tech_colonization_1.png", "The time has come to begin researching into long-term stasis and mega-construction techniques. By doing so, we can begin construction of colony ships which will allow us to colonize new worlds in far off systems.") {
            @Override
            public void finishResearch() {

            }
        });

        techTree.add(new techBuilder("Vertical Farming", 0, 50, 100, TECH_AGRICULTURE, false, "", "Did you hear? The guards in Hammerfell have curved swords. Curved. Swords.") {
            @Override
            public void finishResearch() {

            }
        });

        techTree.add(new techBuilder("Arcologies", 0, 50, 8, TECH_INFRASTRUCTURE, false, "tech_vault.png", "The arcology is a massive structure housing a large, self-sustaining population within it. With very minimal ecological impact, we can more effectively utilize the planet's resources without as high a risk of ecological disaster.") {
            @Override
            public void finishResearch() {

            }
        });

        techTree.add(new techBuilder("String Drive", 0, 120, 65, TECH_PROPULSION, false, "tech_string_drive_2.png", "By improving the efficiency of the string drive, we should be able to reduce the amount of material needed to maintain the string-lines.") {
            @Override
            public void finishResearch() {

            }
        });

        techTree.add(new techBuilder("Advanced String Drive", 0, 450, 50, TECH_PROPULSION, true, "tech_string_drive_3.png", "Further improvements to the string-drive concept have lead to faster charge times, lower transit times, and cheaper operating costs.") {
            @Override
            public void finishResearch() {

            }
        });

        techTree.add(new techBuilder("Dyson Sphere Construction", 0, 7500, 6, TECH_INFRASTRUCTURE, false, "tech_dyson_sphere.png", "PLACEHOLDER") {
            @Override
            public void finishResearch() {

            }
        });

    }







}
