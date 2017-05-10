package Core;

/**
 KM
 May 08 2017
 Handles the loading of XML-based modded content.
 XML core taken from: https://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
 **/

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

public class xmlLoader {
    //IS FINE COMRADE, NO PROBLEMS HERE

    final static File expansionFolder = new File(System.getProperty("user.dir") + "/src/Expansions");
    final static File modFolder = new File(System.getProperty("user.dir") + "/src/Mods");
    final static String xmlTag = ".xml";

    public static void loadXML(final File folder) {
        if (folder.exists()) {
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
                                if (baseNode.equals("newPlanets")) { //this mods is adding or changing planets
                                    modPlanets(elements);
                                } else if (baseNode.equals("newStars")) {
                                    modStars(elements);
                                } else if (baseNode.equals("expansionDescription")) {
                                    loadExpansions(elements); //loads the expansions
                                } else if (baseNode.equals("loadMod")) {
                                    loadMods(elements);
                                } else {
                                    errorPrint(7);
                                }
                        } else {
                            //errorPrint(11);
                        }
                    } else {
                        errorPrint(10);
                    }
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            errorPrint(9);
        }
    } //close loadXML

    //Goes through the expansions and loads the enabled ones.
    private static void loadExpansions(NodeList nodeList) {
        String nodeName;
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
                        if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) { //valid node type
                            if (nodes.item(j).getNodeName().equals("enabled") && Integer.parseInt(nodes.item(j).getTextContent()) == 1) { //check to see if it is enabled
                                System.out.println("Loading expansion " + expansionID + " from " + newExpansion);
                                if (newExpansion.exists()) {
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
                        if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) { //valid node type
                            if (nodes.item(j).getNodeName().equals("modActive") && Integer.parseInt(nodes.item(j).getTextContent()) == 1) { //check to see if it is enabled
                                modIsActive = true;
                            } else if (nodes.item(j).getNodeName().equals("directoryName")) {
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

        newMod = new File( modFolder + "/" + modID); //gets the mod directory

        if (modIsActive && newMod.exists() && !modID.equals("")) { //checks to ensure it is valid and enabled
            System.out.println("Mod directory successfully parsed.\nAttempting to load mod data of mod '" + modName + "' from " + newMod);
            loadXML(newMod); //attempts to load mod data
        } else {
            errorPrint(15);
        }
    }

    //TODO: Redo modPlanets and modStars to fit a more simple format.

    private static void modPlanets(NodeList nodeList) {
        String className = "";
        int planetID = 0, climateID = 0, sizeWeight = 0, sizeVariation = 0, spawnWeight = 0;
        String classDesc = "";
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
                        for (int j = 0; j < starClass.listOfStars.size(); j++) {
                            if (starClass.listOfStars.get(j).getStarID() == planetID) {
                                starClass.listOfStars.remove(j); //remove the content using the same ID
                            }
                        }
                        NodeList nodes = parentNode.getChildNodes();
                        for (int k = 0; k < nodes.getLength(); k++) {
                            if (nodes.item(k).getNodeType() == Node.ELEMENT_NODE) {
                                if (nodes.item(k).getNodeName().equals("name") && nodes.item(k).getTextContent() != null) {
                                    className = nodes.item(k).getTextContent();
                                } else if (nodes.item(k).getNodeName().equals("description") && nodes.item(k).getTextContent() != null) {
                                    classDesc = nodes.item(k).getTextContent();
                                } else if (nodes.item(k).getNodeName().equals("climate") && nodes.item(k).getTextContent() != null) {
                                    if (Integer.parseInt(nodes.item(k).getTextContent()) >= 2100 && Integer.parseInt(nodes.item(k).getTextContent()) < 2200) {
                                        climateID = Integer.parseInt(nodes.item(k).getTextContent());
                                    } else {
                                        climateID = 2107; //defaults climate ID to avoid major errors
                                    }
                                } else if (nodes.item(k).getNodeName().equals("isHabitable") && nodes.item(k).getTextContent() != null) {
                                    if (Integer.parseInt(nodes.item(k).getTextContent()) == 1) {
                                        habitable = true;
                                    } else if (Integer.parseInt(nodes.item(k).getTextContent()) == 0) {
                                        habitable = false;
                                    } else {
                                        errorPrint(17);
                                    }
                                }  else if (nodes.item(k).getNodeName().equals("sizeWeight") && nodes.item(k).getTextContent() != null) {
                                    if (Integer.parseInt(nodes.item(k).getTextContent()) >= 0) {
                                        sizeWeight = Integer.parseInt(nodes.item(k).getTextContent());
                                    } else {
                                        errorPrint(17);
                                    }
                                }  else if (nodes.item(k).getNodeName().equals("sizeVariation") && nodes.item(k).getTextContent() != null) {
                                    if (Integer.parseInt(nodes.item(k).getTextContent()) >= 0) {
                                        sizeVariation = Integer.parseInt(nodes.item(k).getTextContent());
                                    } else {
                                        errorPrint(17);
                                    }
                                }  else if (nodes.item(k).getNodeName().equals("spawnWeight") && nodes.item(k).getTextContent() != null) {
                                    if (Integer.parseInt(nodes.item(k).getTextContent()) >= 0) {
                                        spawnWeight = Integer.parseInt(nodes.item(k).getTextContent());
                                    } else {
                                        errorPrint(17);
                                    }
                                } //if it doesn't fit any of the necessary info, ignore it
                            }
                        }
                    } else {
                        errorPrint(16);
                    }
                    if (modIsValid) { //valid mod, load it in
                        //adds the new planet to the planet list.
                        planetClass.listOfPlanets.add(new planetCore.planetType(className, planetID, climateID, spawnWeight, classDesc, habitable, sizeWeight, sizeVariation));
                        System.out.println("New planet successfully loaded - " + className + " (ID" + planetID + ")");
                    } else { //something in this mod wasn't properly initialized, do not load it
                        System.out.println("An error has occurred while loading planet XML data.");
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
        String desc = "";
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
                        for (int j = 0; j < starClass.listOfStars.size(); j++) {
                            if (starClass.listOfStars.get(j).getStarID() == starID) {
                                starClass.listOfStars.remove(j); //remove the content using the same ID
                            }
                        }
                        NodeList nodes = parentNode.getChildNodes();
                        for (int k = 0; k < nodes.getLength(); k++) {
                            if (nodes.item(k).getNodeType() == Node.ELEMENT_NODE) {
                                if (nodes.item(k).getNodeName().equals("name") && nodes.item(k).getTextContent() != null) {
                                    name = nodes.item(k).getTextContent();
                                } else if (nodes.item(k).getNodeName().equals("description") && nodes.item(k).getTextContent() != null) {
                                    desc = nodes.item(k).getTextContent();
                                } else if (nodes.item(k).getNodeName().equals("spawnWeight") && nodes.item(k).getTextContent() != null) {
                                    if (Integer.parseInt(nodes.item(k).getTextContent()) >= 0) {
                                        spawn = Integer.parseInt(nodes.item(k).getTextContent());
                                    } else {
                                        errorPrint(17);
                                    }
                                } else if (nodes.item(k).getNodeName().equals("isHabitable") && nodes.item(k).getTextContent() != null) {
                                    if (Integer.parseInt(nodes.item(k).getTextContent()) == 1) {
                                        habitable = true;
                                    } else if (Integer.parseInt(nodes.item(k).getTextContent()) == 0) {
                                        habitable = false;
                                    } else {
                                        errorPrint(17);
                                    }
                                }  else if (nodes.item(k).getNodeName().equals("surfaceTempLow") && nodes.item(k).getTextContent() != null) {
                                    if (Integer.parseInt(nodes.item(k).getTextContent()) >= 0) {
                                        tempLow = Integer.parseInt(nodes.item(k).getTextContent());
                                    } else {
                                        errorPrint(17);
                                    }
                                }  else if (nodes.item(k).getNodeName().equals("surfaceTempHigh") && nodes.item(k).getTextContent() != null) {
                                    if (Integer.parseInt(nodes.item(k).getTextContent()) >= 0) {
                                        tempHigh = Integer.parseInt(nodes.item(k).getTextContent());
                                    } else {
                                        errorPrint(17);
                                    }
                                }  else if (nodes.item(k).getNodeName().equals("sizeWeight") && nodes.item(k).getTextContent() != null) {
                                    if (Integer.parseInt(nodes.item(k).getTextContent()) >= 0) {
                                        sizeWeight = Integer.parseInt(nodes.item(k).getTextContent());
                                    } else {
                                        errorPrint(17);
                                    }
                                }  else if (nodes.item(k).getNodeName().equals("sizeVariation") && nodes.item(k).getTextContent() != null) {
                                    if (Integer.parseInt(nodes.item(k).getTextContent()) >= 0) {
                                        sizeVariation = Integer.parseInt(nodes.item(k).getTextContent());
                                    } else {
                                        errorPrint(17);
                                    }
                                }  else if (nodes.item(k).getNodeName().equals("planetWeight") && nodes.item(k).getTextContent() != null) {
                                    if (Integer.parseInt(nodes.item(k).getTextContent()) >= 0) {
                                        planetWeight = Integer.parseInt(nodes.item(k).getTextContent());
                                    } else {
                                        errorPrint(17);
                                    }
                                } //if it doesn't fit any of the necessary info, ignore it
                            }
                        }
                    } else {
                        errorPrint(16);
                    }
                    if (modIsValid) { //valid mod, load it in
                        //adds the new planet to the planet list.
                        starClass.listOfStars.add(new starCore.starType(name, starID, spawn, desc, tempLow, tempHigh, habitable, sizeWeight, sizeVariation, planetWeight));
                        System.out.println("New star successfully loaded - " + name + " (ID" + starID + ")");
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
                System.out.println("Ignoring directory.");
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
        }
    }


}
