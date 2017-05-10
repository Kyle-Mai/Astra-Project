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

    final static File expansionFolder = new File(System.getProperty("user.dir") + "/src/Expansions/");
    static File mod;
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
                            errorPrint(11);
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
        System.out.println("Loading expansions");
        File newExpansion;
        int expansionID;

        if (nodeList != null) { //checks to ensure the input isn't null
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node parentNode = nodeList.item(i);
                if (parentNode.getNodeName().equals("expansion")) { //checks the subnode of the XML to see what we're loading
                    System.out.println("Expansion found.");
                    NodeList nodes = parentNode.getChildNodes(); //TODO: Correctly parse expansion ID and subnode data.
                    expansionID = Integer.parseInt(parentNode.getTextContent());
                    newExpansion = new File(expansionFolder + parentNode.getNodeValue());
                    for (int j = 0; j < nodes.getLength(); j++) {
                        if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) { //valid node type
                            if (nodes.item(i).getNodeName().equals("enabled")) {
                                System.out.println("Loading expansion " + expansionID);
                                loadXML(newExpansion);
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
        System.out.println("Loading mods");
        String directory = "";
        if (nodeList != null) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node tempNode = nodeList.item(i);
                if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) tempNode;
                    if (eElement.getAttribute("name") != null) { //Mod has a valid name
                        if (Integer.parseInt(eElement.getElementsByTagName("enabled").item(0).getTextContent()) == 1) { //Mod is enabled, load its content.
                            System.out.println("Loading mod content...");
                            if (tempNode.hasAttributes()) { //looks through the modList's mod to find the directory of the mod
                                NamedNodeMap nodeMap = tempNode.getAttributes();
                                for (int j = 0; j < nodeMap.getLength(); j++) {
                                    Node node = nodeMap.item(j);
                                    if (node.getNodeName().equals("directoryName") && node.getTextContent() != null) { //gets the directory of the mod.
                                        directory = node.getTextContent();
                                    } else {
                                        errorPrint(6);
                                    }
                                }
                            }
                            if (!directory.equals("")) {
                                mod = new File("/src/Mods/" + directory);
                                if (mod.exists()) {
                                    loadXML(mod);
                                } else {
                                    errorPrint(5);
                                }
                            }
                        } else {
                            System.out.println("Mod disabled.");
                        }
                        //Mod isn't enabled, don't load content.
                    }
                }
            }
        } else {
            errorPrint(12);
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

        System.out.println("Loading new planets");
        //gather all of the nodes
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node tempNode = nodeList.item(i);
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) { //this is an element (aka planet)
                if (tempNode.hasAttributes()) { //the node has valid attributes
                    NamedNodeMap nodeMap = tempNode.getAttributes(); //gathers the node's attributes
                    if (tempNode.getTextContent() != null) {
                        if (Integer.parseInt(tempNode.getTextContent()) >= 2000 && Integer.parseInt(tempNode.getTextContent()) <= 2099) { //the ID is a valid planet ID
                            for (int k = 0; k < starClass.listOfStars.size(); k++) { //if the ID is already used, refuse the mod
                                if (planetClass.listOfPlanets.get(k).getPlanetID() == Integer.parseInt(tempNode.getTextContent())) { //TODO: Maybe redo this to instead overwrite existing objects?
                                    modIsValid = false;
                                    errorMessage = 4;
                                }
                            }
                            if (modIsValid) {
                                planetID = Integer.parseInt(tempNode.getTextContent());
                            }
                        } else { //if it's not valid, dump an error
                            modIsValid = false;
                            errorMessage = 3;
                        }
                    } else {
                        modIsValid = false;
                        errorMessage = 1;
                    }
                    //collects the data from the different nodes
                    for (int j = 0; j < nodeMap.getLength(); j++) {
                        Node node = nodeMap.item(j);
                        if (node.getNodeName().equals("name")) {
                            if (node.getTextContent() != null) {
                                className = node.getTextContent();
                            } else {
                                modIsValid = false;
                                errorMessage = 2;
                            }
                        } else if (node.getNodeName().equals("description")) {
                            if (node.getTextContent() != null) {
                                classDesc = node.getTextContent();
                            } else {
                                modIsValid = false;
                                errorMessage = 2;
                            }
                        } else if (node.getNodeName().equals("climate")) {
                            if (node.getTextContent() != null) {
                                climateID = Integer.parseInt(node.getTextContent());
                            } else {
                                modIsValid = false;
                                errorMessage = 2;
                            }
                        } else if (node.getNodeName().equals("spawnWeight")) {
                            if (node.getTextContent() != null) {
                                spawnWeight = Integer.parseInt(node.getTextContent());
                            } else {
                                modIsValid = false;
                                errorMessage = 2;
                            }
                        } else if (node.getNodeName().equals("isHabitable")) {
                            if (node.getTextContent() != null) {
                                if (Integer.parseInt(node.getTextContent()) == 1) {
                                    habitable = true;
                                } else {
                                    habitable = false;
                                }
                            } else {
                                modIsValid = false;
                                errorMessage = 2;
                            }
                        } else if (node.getNodeName().equals("sizeWeight")) {
                            if (node.getTextContent() != null) {
                                sizeWeight = Integer.parseInt(node.getTextContent());
                            } else {
                                modIsValid = false;
                                errorMessage = 2;
                            }
                        } else if (node.getNodeName().equals("sizeVariation")) {
                            if (node.getTextContent() != null) {
                                sizeVariation = Integer.parseInt(node.getTextContent());
                                if (sizeVariation > sizeWeight) {
                                    sizeVariation = sizeWeight - 1;
                                }
                            } else {
                                modIsValid = false;
                                errorMessage = 2;
                            }
                        }
                    }

                    if (modIsValid) { //valid mod, load it in
                        //adds the new planet to the planet list.
                        planetClass.listOfPlanets.add(new planetCore.planetType(className, planetID, climateID, spawnWeight, classDesc, habitable, sizeWeight, sizeVariation));
                        System.out.println("Modded planet successfully loaded.");
                    } else { //something in this mod wasn't properly initialized, do not load it
                        System.out.println("An error has occurred while loading planet XML data.");
                        errorPrint(errorMessage);
                    }

                }
            }
        }

    } //close modPlanets

    private static void modStars(NodeList nodeList) {
        String name = "";
        int spawn = 0;
        String desc = "";
        int[] Temp = new int[2];
        boolean habitable = false;
        String spectral = "";
        int starID = 0;
        int sizeWeight = 0;
        int sizeVariation = 0;
        int planetWeight = 0;
        boolean modIsValid = true;
        int errorMessage = 0;

        System.out.println("Loading new stars");
        //gather all of the nodes
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node tempNode = nodeList.item(i);
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) { //this is an element (aka planet)
                if (tempNode.hasAttributes()) { //the node has valid attributes
                    NamedNodeMap nodeMap = tempNode.getAttributes(); //gathers the node's attributes
                    if (tempNode.getTextContent() != null) {
                        if (Integer.parseInt(tempNode.getTextContent()) >= 1000 && Integer.parseInt(tempNode.getTextContent()) <= 1099) { //the ID is valid
                            for (int k = 0; k < starClass.listOfStars.size(); k++) { //if the ID is already used, refuse the mod
                                if (starClass.listOfStars.get(k).getStarID() == Integer.parseInt(tempNode.getTextContent())) {
                                    modIsValid = false;
                                    errorMessage = 4;
                                }
                            }
                            if (modIsValid) {
                                starID = Integer.parseInt(tempNode.getTextContent());
                            }
                        } else { //if it's not valid, dump an error
                            modIsValid = false;
                            errorMessage = 3;
                        }
                    } else {
                        modIsValid = false;
                        errorMessage = 1;
                    }
                    //collects the data from the different nodes
                    for (int j = 0; j < nodeMap.getLength(); j++) {
                        Node node = nodeMap.item(j);
                        if (node.getNodeName().equals("name")) {
                            if (node.getTextContent() != null) {
                                name = node.getTextContent();
                            } else {
                                modIsValid = false;
                                errorMessage = 2;
                            }
                        } else if (node.getNodeName().equals("description")) {
                            if (node.getTextContent() != null) {
                                desc = node.getTextContent();
                            } else {
                                modIsValid = false;
                                errorMessage = 2;
                            }
                        } else if (node.getNodeName().equals("spawnWeight")) {
                            if (node.getTextContent() != null) {
                                spawn = Integer.parseInt(node.getTextContent());
                            } else {
                                modIsValid = false;
                                errorMessage = 2;
                            }
                        } else if (node.getNodeName().equals("isHabitable")) {
                            if (node.getTextContent() != null) {
                                if (Integer.parseInt(node.getTextContent()) == 1) {
                                    habitable = true;
                                } else {
                                    habitable = false;
                                }
                            } else {
                                modIsValid = false;
                                errorMessage = 2;
                            }
                        } else if (node.getNodeName().equals("surfaceTempHigh")) {
                            if (node.getTextContent() != null) {
                                Temp[1] = Integer.parseInt(node.getTextContent());
                            } else {
                                modIsValid = false;
                                errorMessage = 2;
                            }
                        } else if (node.getNodeName().equals("surfaceTempLow")) {
                            if (node.getTextContent() != null) {
                                Temp[0] = Integer.parseInt(node.getTextContent());
                            } else {
                                modIsValid = false;
                                errorMessage = 2;
                            }
                        } else if (node.getNodeName().equals("planetWeight")) {
                            if (node.getTextContent() != null) {
                                planetWeight = Integer.parseInt(node.getTextContent());
                            } else {
                                modIsValid = false;
                                errorMessage = 2;
                            }
                        } else if (node.getNodeName().equals("sizeWeight")) {
                            if (node.getTextContent() != null) {
                                sizeWeight = Integer.parseInt(node.getTextContent());
                            } else {
                                modIsValid = false;
                                errorMessage = 2;
                            }
                        } else if (node.getNodeName().equals("sizeVariation")) {
                            if (node.getTextContent() != null) {
                                sizeVariation = Integer.parseInt(node.getTextContent());
                                if (sizeVariation > sizeWeight) {
                                    sizeVariation = sizeWeight - 1;
                                }
                            } else {
                                modIsValid = false;
                                errorMessage = 2;
                            }
                        }
                    }

                    if (modIsValid) { //valid mod, load it in
                        //adds the new planet to the planet list.
                        starClass.listOfStars.add(new starCore.starType(name, starID, spawn, desc, Temp[0], Temp[1], habitable, sizeWeight, sizeVariation, planetWeight));
                        System.out.println("Modded star successfully loaded.");
                    } else { //something in this mod wasn't properly initialized, do not load it
                        System.out.println("An error has occurred while loading star XML data.");
                        errorPrint(errorMessage);
                    }
                }

            }
        }
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
        }
    }


}
