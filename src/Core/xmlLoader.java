package Core;

/**
 KM
 May 08 2017
 Handles the loading of XML-based content.
 Basis for XML core structure taken from: https://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
 WARNING: The code here is very poorly optimized for space, but functions fairly well.
 **/

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

public class xmlLoader {
    //grabs the file locations for the folders
    final static File expansionFolder = new File(System.getProperty("user.dir") + "/src/Expansions");
    final static File modFolder = new File(System.getProperty("user.dir") + "/src/Mods");
    final static File techTreeFolder = new File(System.getProperty("user.dir") + "/src/Core/techTree");
    final static String xmlTag = ".xml"; //unused, may be necessary in the future

    //loads the XML content
    public static void loadContent() {
        System.out.println("Attempting to load tech tree data...");
        loadXML(techTreeFolder); //loads the tech tree content
        System.out.println("Attempting to load expansion files...");
        loadXML(expansionFolder); //loads the content in the expansion pack folder
        System.out.println("Attempting to load mods...");
        loadXML(modFolder); //loads the content in the mod folder

    }

    private static void loadXML(final File folder) {
        if (folder.exists()) { //obviously, the folder must exist if we want to examine it
            try {
                for (File fileEntry : folder.listFiles()) { //look through the directory for files
                    DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                    if (!fileEntry.isDirectory()) { //makes sure we don't try to read a directory as a standalone
                        Document doc = dBuilder.parse(fileEntry);
                        if (doc.hasChildNodes()) { //check to see if the file has any nodes
                            doc.getDocumentElement().normalize();
                            String baseNode = doc.getDocumentElement().getNodeName();
                            NodeList elements = doc.getDocumentElement().getChildNodes();
                            System.out.println("XML file successfully parsed");
                                if (baseNode.equals("newPlanets")) { //adding or changing planets
                                    modPlanets(elements);
                                } else if (baseNode.equals("newStars")) { //adding/changing stars
                                    modStars(elements);
                                } else if (baseNode.equals("expansionDescription")) { //loading expansions
                                    loadExpansions(elements);
                                } else if (baseNode.equals("loadMod")) { //loading mods
                                    loadMods(elements);
                                } else if (baseNode.equals("techTree")) {
                                    loadTechTree(elements);
                                } else { //the stuff found was not valid XML
                                    errorPrint(7);
                                }
                        } else {
                            errorPrint(11);
                        }
                    } else {
                        //The file indexed was a directory file (folder), and should NOT be read
                    }
                }

            } catch (Exception e) {
                System.out.println("Exception when parsing XML data: " + e.getMessage());
            }
        } else {
            errorPrint(9);
        }
    } //close loadXML

    private static void writeXML(final File folder, String name, int action) {
        if (folder.exists()) { //obviously, the folder must exist if we want to examine it
            try {
                for (File fileEntry : folder.listFiles()) { //look through the directory for files
                    DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                    if (!fileEntry.isDirectory()) { //makes sure we don't try to read a directory as a standalone
                        Document doc = dBuilder.parse(fileEntry);
                        if (doc.hasChildNodes()) { //check to see if the file has any nodes
                            doc.getDocumentElement().normalize();
                            String baseNode = doc.getDocumentElement().getNodeName();
                            NodeList elements = doc.getDocumentElement().getChildNodes();
                            System.out.println("XML file successfully parsed");
                            if (baseNode.equals("expansionDescription")) { //loading expansions
                                changeExpansions(elements, name, action);
                            } else if (baseNode.equals("loadMod")) { //loading mods

                            }  else { //the stuff found was not valid XML
                                errorPrint(7);
                            }
                        } else {
                            errorPrint(11);
                        }
                    } else {
                        //The file indexed was a directory file (folder), and should NOT be read
                    }
                }

            } catch (Exception e) {
                System.out.println("Exception when parsing XML data: " + e.getMessage());
            }
        } else {
            errorPrint(9);
        }
    } //close loadXML

    private static void loadTechTree(NodeList nodeList) {
        final int numOfTechLines = 6; //easy access when editing the total types of techs

        String nodeName;
        System.out.println("Attempting to load tech tree data from " + techTreeFolder);
        File newTech;
        boolean techIsValid = false;

        int techLine = 0;
        int techID = 0;
        int techCost = 0;
        int techLevel = 0;
        String techName = "NO NAME";
        String techDesc = "No description.";
        int techRarity = 0;

        if (nodeList != null) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node parentNode = nodeList.item(i);
                if (parentNode.getNodeName().equals("newTech")) {
                    System.out.println("New tech found. Attempting to load...");
                    NodeList nodes = parentNode.getChildNodes();
                    techLine = Integer.parseInt(parentNode.getAttributes().getNamedItem("techline").getNodeValue()); //gets the ID of the tech
                    if (techLine > 0 && techLine < numOfTechLines) {
                        techIsValid = true; //probably valid, begin indexing information
                        for (int j = 0; j < nodes.getLength(); j++) {
                            if (nodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                                if (nodes.item(j).getNodeName().equals("name") && nodes.item(j).getTextContent() != null) {
                                    techName = nodes.item(j).getTextContent();
                                } else if (nodes.item(j).getNodeName().equals("description")) {
                                    if (nodes.item(j).getTextContent() != null) { //don't try to parse nothing, safety net code
                                        techDesc = nodes.item(j).getTextContent();
                                    }
                                } else if (nodes.item(j).getNodeName().equals("id") && nodes.item(j).getTextContent() != null) {
                                    techID = Integer.parseInt(nodes.item(j).getTextContent());
                                    if (techID > 200 || techID < 1) { //invalid ID
                                        techIsValid = false;
                                        break;
                                    }
                                } else if (nodes.item(j).getNodeName().equals("level") && nodes.item(j).getTextContent() != null) {
                                    techLevel = Integer.parseInt(nodes.item(j).getTextContent());
                                    if (techLevel < 0 && techLevel > 10) { //tech is not a valid level
                                        techIsValid = false;
                                        break;
                                    }
                                } else if (nodes.item(j).getNodeName().equals("cost") && nodes.item(j).getTextContent() != null) {
                                    techCost = Integer.parseInt(nodes.item(j).getTextContent());
                                    if (techCost < 1) {
                                        techIsValid = false;
                                        break;
                                    }
                                } else if (nodes.item(j).getNodeName().equals("rarity") && nodes.item(j).getTextContent() != null) {
                                    techRarity = Integer.parseInt(nodes.item(j).getTextContent());
                                    if (techRarity < 0) {
                                        techIsValid = false;
                                        break;
                                    }
                                }
                                //TODO: Add handling for the different things that techs unlock.

                            } else {
                                //not an element node, don't attempt to index
                            }
                        }
                    } else {
                        //invalid tech ID, mod should not be loaded
                    }

                    if (techIsValid) {
                        boolean replacesContent = false;

                        for (int k = 0; k < techCore.techTree.size(); k++) { //find duplicate IDs
                            for (int j = 0; j < techCore.techTree.get(k).size(); j++) {
                                if (techCore.techTree.get(k).get(j).getID() == techID) {
                                    replacesContent = true;
                                    techCore.techTree.get(k).add(j, new techCore.tech(techName, techName, techID, techLine, techLevel, techCost, techRarity));
                                    System.out.println("[OVERWRITE] Tech '" + techName + "' successfully added to " + techCore.techPaths[techLine -1]);
                                    break; //once the duplicate is found, there's no need to continue indexing
                                }
                            }
                        }
                        if (!replacesContent) {
                            techCore.techTree.get(techLine - 1).add(new techCore.tech(techName, techName, techID, techLine, techLevel, techCost, techRarity));
                            System.out.println("[NEW] Tech '" + techName + "' successfully added to line #" + techLine + " - " + techCore.techPaths[techLine -1]);
                        }
                    } else {
                        System.out.println("An error has occurred while loading tech tree XML data. Loading aborted.");
                    }
                }
            }
        } else {
            errorPrint(12);
        }
    }

    //collects the expansion pack list information, and also changes the enabled status
    private static void changeExpansions(NodeList nodeList, String expansion, int action) {
        File expansionList;
        String expansionID;

        int enabledStatus; //whether or not the pack is enabled or disabled, represented by 1 and 0 respectively
        //name, subtitle, and description of the expansion pack
        String name;
        String subtitle;
        String desc;

        if (nodeList != null) { //no input > no output
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node parentNode = nodeList.item(i);
                if (parentNode.getNodeName().equals("expansion")) {
                    NodeList nodes = parentNode.getChildNodes();
                    expansionID = parentNode.getAttributes().getNamedItem("id").getNodeValue();
                    if (expansionID == expansion) { //this is the mod we're editing
                        for (int j = 0; j < nodes.getLength(); j++) {
                            if (nodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                                if (nodes.item(j).getNodeName().equals("enabled")) { //gets the status of whether or not the mod is enabled
                                    if (action == 1 || action == 0) { //enabling/disabling the expansion
                                        nodes.item(j).setTextContent(Integer.toString(action));
                                        System.out.println("Expansion pack " + expansionID + " enabled status changed to " + action);
                                    }
                                    if (nodes.item(j).getTextContent().equals("1") || nodes.item(j).getTextContent().equals("0")) {
                                        enabledStatus = Integer.parseInt(nodes.item(j).getTextContent());
                                    } else {
                                        nodes.item(j).setTextContent("0"); //if the value returned by the enabled status is invalid, default it to 0 (disabled)
                                        enabledStatus = 0;
                                    }
                                } else if (nodes.item(j).getNodeName().equals("expansionName")) { //gets the name of the expansion pack
                                    name = nodes.item(j).getTextContent();
                                } else if (nodes.item(j).getNodeName().equals("subtitle")) {
                                    subtitle = nodes.item(j).getTextContent();
                                } else if (nodes.item(j).getNodeName().equals("description")) {
                                    desc = nodes.item(j).getTextContent();
                                }
                            }
                        }
                    }
                }
            }
            //TODO: Add code in to send this information to the UI for display.
        } else {
            errorPrint((12));
        }

    }

    //Goes through the expansions and loads the enabled ones.
    private static void loadExpansions(NodeList nodeList) {
        System.out.println("Expansion pack directory found. Loading expansions...");
        File newExpansion;
        String expansionID;

        if (nodeList != null) { //checks to ensure the input isn't null
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node parentNode = nodeList.item(i);
                if (parentNode.getNodeName().equals("expansion")) { //checks the subnode of the XML to see what we're loading
                    System.out.println("Expansion pack found. Attempting to load...");
                    NodeList nodes = parentNode.getChildNodes();
                    expansionID = parentNode.getAttributes().getNamedItem("id").getNodeValue();
                    newExpansion = new File( expansionFolder + "/" + expansionID);
                    for (int j = 0; j < nodes.getLength(); j++) {
                        if (nodes.item(j).getNodeType() == Node.ELEMENT_NODE) { //valid node type
                            if (nodes.item(j).getNodeName().equals("enabled") && Integer.parseInt(nodes.item(j).getTextContent()) == 1) { //checks the status of the enabled parameter
                                    System.out.println("Loading expansion " + expansionID + " from " + newExpansion);
                                    if (newExpansion.exists()) { //check to ensure the directory is valid
                                        loadXML(newExpansion);
                                    } else {
                                        errorPrint(14);
                                    }
                            } else {
                            }
                        } else {
                        }
                    }
                } else {
                    //errorPrint(13);
                }
            }
        } else {
            errorPrint(12);
        }


    }

    private static void loadMods(NodeList nodeList) {
        String nodeName;
        System.out.println("Mod directory found. Loading mods...");
        File newMod;
        String modID = "";
        boolean modIsActive = false;
        String modName = "";

        if (nodeList != null) { //checks to ensure the input isn't null
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node parentNode = nodeList.item(i);
                if (parentNode.getNodeName().equals("mod")) { //checks the subnode of the XML to see what we're loading
                    System.out.println("Mod found. Attempting to load...");
                    modName = parentNode.getAttributes().getNamedItem("name").getNodeValue();
                    NodeList nodes = parentNode.getChildNodes();
                    for (int j = 0; j < nodes.getLength(); j++) {
                        if (nodes.item(j).getNodeType() == Node.ELEMENT_NODE) { //valid node type
                            if (nodes.item(j).getNodeName().equals("modActive") && Integer.parseInt(nodes.item(j).getTextContent()) == 1) { //checking to see if mod is active before we load the mod
                                modIsActive = true; //mod is enabled, load the information
                            } else if (nodes.item(j).getNodeName().equals("directoryName")) { //get the mod's directory
                                modID = nodes.item(j).getTextContent();
                            }
                        }
                    }
                } else {
                    //errorPrint(13);
                }
            }
        } else {
            errorPrint(12);
        }

        newMod = new File(modFolder + "/" + modID); //gets the mod directory

        if (modIsActive && newMod.exists() && !modID.equals("")) { //checks to ensure it is valid and enabled
            System.out.println("Mod directory successfully parsed.\nAttempting to load mod data of mod '" + modName + "' from " + newMod);
            loadXML(newMod); //attempts to load mod data
        } else {
            errorPrint(15);
        }

    }

    private static void modPlanets(NodeList nodeList) {
        String className = "";
        int planetID = 0, climateID = 0, sizeWeight = 0, sizeVariation = 0, spawnWeight = 0;
        String classDesc = "Not found.";
        boolean habitable = false;
        boolean modIsValid = true;
        int errorMessage = 0;

        System.out.println("Loading new planet data...");
        //gather all of the nodes

        if (nodeList != null) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node parentNode = nodeList.item(i);
                if (parentNode.getNodeName().equals("planet")) {
                    System.out.println("New planet found. Attempting to load data...");
                    planetID = Integer.parseInt(parentNode.getAttributes().getNamedItem("id").getNodeValue());
                    if (planetID >= 2000 && planetID < 3000) {
                        modIsValid = true;
                        NodeList nodes = parentNode.getChildNodes();
                        for (int k = 0; k < nodes.getLength(); k++) {
                            if (nodes.item(k).getNodeType() == Node.ELEMENT_NODE) {
                                if (nodes.item(k).getNodeName().equals("name")) {
                                    if (nodes.item(k).getTextContent() != null) {
                                        className = nodes.item(k).getTextContent();
                                    } else {
                                        modIsValid = false;
                                        errorPrint(20);
                                        break; //stops indexing the mod since it's already invalid
                                    }
                                } else if (nodes.item(k).getNodeName().equals("description") && nodes.item(k).getTextContent() != null) {
                                    classDesc = nodes.item(k).getTextContent();
                                } else if (nodes.item(k).getNodeName().equals("climate") && nodes.item(k).getTextContent() != null) {
                                    if (Integer.parseInt(nodes.item(k).getTextContent()) >= 2100 && Integer.parseInt(nodes.item(k).getTextContent()) < 2200) {
                                        climateID = Integer.parseInt(nodes.item(k).getTextContent());
                                    } else {
                                        errorPrint(19);
                                        climateID = 2107; //defaults climate ID to attempt to load the star even if the climate isn't valid
                                    }
                                } else if (nodes.item(k).getNodeName().equals("isHabitable") && nodes.item(k).getTextContent() != null) {
                                    if (Integer.parseInt(nodes.item(k).getTextContent()) == 1) {
                                        habitable = true;
                                    } else if (Integer.parseInt(nodes.item(k).getTextContent()) == 0) {
                                        habitable = false;
                                    } else {
                                        modIsValid = false;
                                        errorPrint(17);
                                        break; //stops indexing the mod since it's already invalid
                                    }
                                }  else if (nodes.item(k).getNodeName().equals("sizeWeight") && nodes.item(k).getTextContent() != null) {
                                    if (Integer.parseInt(nodes.item(k).getTextContent()) >= 0) {
                                        sizeWeight = Integer.parseInt(nodes.item(k).getTextContent());
                                    } else {
                                        modIsValid = false;
                                        errorPrint(17);
                                        break; //stops indexing the mod since it's already invalid
                                    }
                                }  else if (nodes.item(k).getNodeName().equals("sizeVariation") && nodes.item(k).getTextContent() != null) {
                                    if (Integer.parseInt(nodes.item(k).getTextContent()) >= 0) {
                                        sizeVariation = Integer.parseInt(nodes.item(k).getTextContent());
                                    } else {
                                        errorPrint(22);
                                        sizeVariation = 0; //can default this value to attempt to continue loading the file, so no point in stopping here.
                                    }
                                }  else if (nodes.item(k).getNodeName().equals("spawnWeight") && nodes.item(k).getTextContent() != null) {
                                    if (Integer.parseInt(nodes.item(k).getTextContent()) >= 0) {
                                        spawnWeight = Integer.parseInt(nodes.item(k).getTextContent());
                                    } else {
                                        errorPrint(21);
                                        spawnWeight = 0;
                                    }
                                } //if it doesn't fit any of the necessary info, ignore it
                            }
                        }
                    } else {
                        errorPrint(16);
                    }
                    if (modIsValid) { //valid mod, load it in
                        boolean replacesContent = false;
                        for (int j = 0; j < planetClass.listOfPlanets.size(); j++) {
                            if (planetClass.listOfPlanets.get(j).getPlanetID() == planetID) {
                                planetClass.listOfPlanets.add(j, new planetCore.planetType(className, planetID, climateID, spawnWeight, classDesc, habitable, sizeWeight, sizeVariation));
                                System.out.println("[OVERWRITE] Planet successfully loaded - " + className + " (ID" + planetID + ")");
                                replacesContent = true;
                                break; //duplicate already found and replaced, no point in continuing the index
                            }
                        }
                        //adds the new planet to the planet list if it wasn't already added
                        if (!replacesContent) {
                            planetClass.listOfPlanets.add(new planetCore.planetType(className, planetID, climateID, spawnWeight, classDesc, habitable, sizeWeight, sizeVariation));
                            System.out.println("[NEW] Planet successfully loaded - " + className + " (ID" + planetID + ")");
                        }
                    } else { //something in this mod wasn't properly initialized, do not load it
                        System.out.println("An error has occurred while loading planet XML data. Loading aborted.");
                    }
                }
            }
        } else {
            errorPrint(12);
        }

        System.out.println("Loading complete.");

    } //close modPlanets

    private static void modStars(NodeList nodeList) {
        String name = "";
        int spawn = 0;
        String desc = "Not found.";
        int tempLow = 0, tempHigh = 0;
        boolean habitable = false;
        String spectral = "";
        int starID = 0;
        int sizeWeight = 0;
        int sizeVariation = 0;
        int planetWeight = 0;
        boolean modIsValid = false;
        int errorMessage = 0;
        int replaceID = -1;

        System.out.println("Loading new star data...");
        //gather all of the nodes

        if (nodeList != null) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node parentNode = nodeList.item(i);
                if (parentNode.getNodeName().equals("star")) {
                    System.out.println("New star found. Attempting to load data...");
                    starID = Integer.parseInt(parentNode.getAttributes().getNamedItem("id").getNodeValue());
                    if (starID >= 1000 && starID < 2000) {
                        modIsValid = true;
                        NodeList nodes = parentNode.getChildNodes();
                        for (int k = 0; k < nodes.getLength(); k++) {
                            if (nodes.item(k).getNodeType() == Node.ELEMENT_NODE) {
                                if (nodes.item(k).getNodeName().equals("name") && nodes.item(k).getTextContent() != null) {
                                    if (nodes.item(k).getTextContent() != null) {
                                        name = nodes.item(k).getTextContent();
                                    } else {
                                        modIsValid = false;
                                        errorPrint(20);
                                        break; //stops indexing the mod since it's already invalid
                                    }
                                } else if (nodes.item(k).getNodeName().equals("description") && nodes.item(k).getTextContent() != null) {
                                    desc = nodes.item(k).getTextContent();
                                } else if (nodes.item(k).getNodeName().equals("spawnWeight") && nodes.item(k).getTextContent() != null) {
                                    if (Integer.parseInt(nodes.item(k).getTextContent()) >= 0) {
                                        spawn = Integer.parseInt(nodes.item(k).getTextContent());
                                    } else {
                                        spawn = 0;
                                        errorPrint(21);
                                    }
                                } else if (nodes.item(k).getNodeName().equals("isHabitable") && nodes.item(k).getTextContent() != null) {
                                    if (Integer.parseInt(nodes.item(k).getTextContent()) == 1) {
                                        habitable = true;
                                    } else if (Integer.parseInt(nodes.item(k).getTextContent()) == 0) {
                                        habitable = false;
                                    } else {
                                        modIsValid = false;
                                        errorPrint(17);
                                        break;
                                    }
                                }  else if (nodes.item(k).getNodeName().equals("surfaceTempLow") && nodes.item(k).getTextContent() != null) {
                                    if (Integer.parseInt(nodes.item(k).getTextContent()) >= 0) {
                                        tempLow = Integer.parseInt(nodes.item(k).getTextContent());
                                    } else {
                                        modIsValid = false;
                                        errorPrint(17);
                                        break;
                                    }
                                }  else if (nodes.item(k).getNodeName().equals("surfaceTempHigh") && nodes.item(k).getTextContent() != null) {
                                    if (Integer.parseInt(nodes.item(k).getTextContent()) >= 0) {
                                        tempHigh = Integer.parseInt(nodes.item(k).getTextContent());
                                    } else {
                                        modIsValid = false;
                                        errorPrint(17);
                                        break;
                                    }
                                }  else if (nodes.item(k).getNodeName().equals("sizeWeight") && nodes.item(k).getTextContent() != null) {
                                    if (Integer.parseInt(nodes.item(k).getTextContent()) >= 0) {
                                        sizeWeight = Integer.parseInt(nodes.item(k).getTextContent());
                                    } else {
                                        modIsValid = false;
                                        errorPrint(17);
                                        break;
                                    }
                                }  else if (nodes.item(k).getNodeName().equals("sizeVariation") && nodes.item(k).getTextContent() != null) {
                                    if (Integer.parseInt(nodes.item(k).getTextContent()) >= 0) {
                                        sizeVariation = Integer.parseInt(nodes.item(k).getTextContent());
                                    } else {
                                        sizeVariation = 0;
                                        errorPrint(20);
                                    }
                                }  else if (nodes.item(k).getNodeName().equals("planetWeight") && nodes.item(k).getTextContent() != null) {
                                    if (Integer.parseInt(nodes.item(k).getTextContent()) >= 0) {
                                        planetWeight = Integer.parseInt(nodes.item(k).getTextContent());
                                    } else {
                                        errorPrint(23);
                                        planetWeight = 0;
                                    }
                                } //if it doesn't fit any of the necessary info, ignore it
                            }
                        }
                    } else {
                        errorPrint(16);
                    }
                    if (modIsValid) { //valid mod, load it in
                        boolean replacesContent = false;
                        for (int j = 0; j < starClass.listOfStars.size(); j++) {
                            if (starClass.listOfStars.get(j).getStarID() == starID) {
                                replacesContent = true;
                                starClass.listOfStars.remove(j); //remove the content using the same ID
                                System.out.println("[OVERWRITE] Star successfully loaded - " + name + " (ID" + starID + ")");
                                break; //no point in continuing to index since we've already located the duplicate ID
                            }
                        }
                        //adds the new planet to the planet list if one hasn't already been added
                        if (!replacesContent) {
                            starClass.listOfStars.add(new starCore.starType(name, starID, spawn, desc, tempLow, tempHigh, habitable, sizeWeight, sizeVariation, planetWeight));
                            System.out.println("[NEW] Star successfully loaded - " + name + " (ID" + starID + ")");
                        }
                    } else { //something in this mod wasn't properly initialized, do not load it
                        System.out.println("An error has occurred while loading star XML data.");
                    }
                }
            }
        } else {
            errorPrint(12);
        }

        System.out.println("Loading complete.");

    } //close modPlanets

    //Takes all of the different errors that may occur during XML generation and prints them out.
    private static void errorPrint(int errorMessage) {
        switch (errorMessage) {
            case 1:
                System.out.println("No ID was detected for the object. Please ensure an ID is properly declared.");
                break;
            case 2:
                System.out.println("One or more values was not found. Please ensure that all values have been entered and no blank values remain.");
                break;
            case 3:
                System.out.println("The ID entered for the object was invalid. Object IDs should not overwrite existing objects..");
                break;
            case 4:
                System.out.println("The star attempts to use an already declared star ID. Please ensure the star's ID isn't already used.");
                break;
            case 5:
                System.out.println("An error has occurred - the mod directory was not found.");
                break;
            case 6:
                System.out.println("Error loading mod - directory declaration invalid.");
                break;
            case 7:
                System.out.println("An XML file was detected, but the syntax doesn't match any known hierarchies. Please ensure the XML file uses valid syntax.");
                break;
            case 8:
                System.out.println("An error has occurred while loading Exotica - directory was not valid.");
                break;
            case 9:
                System.out.println("An error occurred loading XML data - the XML directory was not valid.");
                break;
            case 10:
                System.out.println(" ");
                break;
            case 11:
                System.out.println("Error - XML file not loaded. Data not found in the directory.");
                break;
            case 12:
                System.out.println("Error - XML loader attempted to index a null node list.");
                break;
            case 13:
                System.out.println("Error when parsing XML data. Sub-node was incorrectly declared.");
                break;
            case 14:
                System.out.println("Error - Content directory was not found. Please ensure the XML content directory is valid.");
                break;
            case 15:
                System.out.println("Error while parsing mod info - Either the directory was not properly declared, or the mod is disabled.");
                break;
            case 16:
                System.out.println("Error while loading star data - star does not have a valid ID code.");
                break;
            case 17:
                System.out.println("Error while loading star data - invalid data.");
                break;
            case 18:
                System.out.println("Error while loading planet data - invalid data.");
                break;
            case 19:
                System.out.println("Error - The planet's climate data was invalid. Defaulting to ID2107 - Unique.");
                break;
            case 20:
                System.out.println("Error when loading XML data - name was not found. Check XML file integrity.");
                break;
            case 21:
                System.out.println("Error when loading XML data - size variation was not found, defaulting to 0.");
                break;
            case 22:
                System.out.println("Error when loading XML data - spawn weight was not found. Defaulting to 0 - object will not spawn.");
                break;
            case 23:
                System.out.println("Error when loading star data - planet spawn weight not found. Defaulting to 0.");
                break;
        }
    }

}
