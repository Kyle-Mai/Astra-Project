package Core;

/**
 KM
 May 08 2017
 Handles the loading of XML data.
 **/

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import Mods.*;
import Core.*;

public class modLoader {
    //IS FINE COMRADE, NO PROBLEMS HERE

    final File folder = new File("src/Mods");

    public static void loadMods(final File folder) {
        try {
        for (final File fileEntry : folder.listFiles()) {
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = dBuilder.parse(fileEntry);

            if (doc.getDocumentElement().getNodeName().equals("newPlanets")) { //this mods is adding or changing planets
                modPlanets(doc.getChildNodes());
            } else {
                System.out.println("A mod was detected, but the syntax was not valid for loading. Please ensure the element is moddable and that the syntax is correct.");
            }
        }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    } //close loadMods

    private static void modPlanets(NodeList nodeList) {
        String className = "";
        int planetID = 0, climateID = 0, sizeWeight = 0, sizeVariation = 0, spawnWeight = 0;
        String classDesc = "";
        boolean habitable = false;
        boolean modIsValid = true;
        int errorMessage = 0;

        //gather all of the nodes
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node tempNode = nodeList.item(i);
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) { //this is an element (aka planet)
                if (tempNode.hasAttributes()) { //the node has valid attributes
                    NamedNodeMap nodeMap = tempNode.getAttributes(); //gathers the node's attributes
                    if (tempNode.getTextContent() != null) {
                        if (Integer.parseInt(tempNode.getTextContent()) >= 2000 && Integer.parseInt(tempNode.getTextContent()) <= 2099) { //the ID is a valid planet ID
                            planetID = Integer.parseInt(tempNode.getTextContent());
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
                        System.out.println("An error has occurred while loading user mods.");
                        switch (errorMessage) {
                            case 1:
                                System.out.println("No ID was detected for the planet. Please ensure an ID is properly declared.");
                                break;
                            case 2:
                                System.out.println("One or more values was not found. Please ensure that all values have been entered and no blank values remain.");
                                break;
                            case 3:
                                System.out.println("The ID entered for the planet was invalid. Planet IDs must be between 2000 and 2100.");
                                break;
                        }
                    }

                }
            }
        }

    } //close modPlanets


}
