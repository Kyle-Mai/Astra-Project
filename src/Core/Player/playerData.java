package Core.Player;

import Core.colonyCore;
import Core.gameSettings;
import Core.mapGenerator;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * KM
 * May 19 2017
 * Repository that stores the player's information so it's easy to manipulate
 *
 * SOURCES:
 * Stack Overflow - Random string/encryption via http://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string
 * Serializable handling via https://stackoverflow.com/questions/447898/what-is-object-serialization
 * Mkyong - Getting the current date and time via https://www.mkyong.com/java/java-how-to-get-current-date-time-date-and-calender/
 * Self - All other work.
 */

public class playerData {

    private saveData save;

    private String userID;
    private String userName;
    private String date;

    private double research;
    private int techLevel;

    private double researchTurn = 0;
    private double currencyTurn = 0;
    private double resourcesTurn = 0;

    private String tech1;
    private String tech2;
    private String tech3;

    private String mapData;
    private String starData;
    private String planetData;

    private int difficulty;

    private double resources;
    private double funds;

    private double taxMultiplier = 1;
    private double productionMultiplier = 1;

    private ArrayList<colonyCore> playerColonies = new ArrayList<>();

    public playerData() {
    }

    public void newPlayer(String username) {
        System.out.println("Creating new player data...");

        this.userID = newUserID(); //gets the unique ID used to identify this player
        this.date = currentDate(); //gets the current time, used to order the save files
        this.userName = username; //user's username

        //sets the default player values
        this.funds = 0;
        this.research = 0;
        this.resources = 0;
        this.techLevel = 0;

        this.difficulty = gameSettings.currDifficulty; //saves the selected user difficulty

        save = new saveData(userID);
        save.create();
        save.add(save.get(), DataConstants.FOLDER, SaveDirectoryConstants.DATA);
        save.add(save.get(SaveDirectoryConstants.DATA), DataConstants.FOLDER, "map");
        save.add(save.get(SaveDirectoryConstants.DATA), DataConstants.FOLDER, "stars");
        save.add(save.get(SaveDirectoryConstants.DATA), DataConstants.FOLDER, "planets");

        try { //creates a base XML file to store simple player info

            DocumentBuilderFactory dfac = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dfac.newDocumentBuilder();

            Document player = db.newDocument();

            Element root = player.createElement("player");
            player.appendChild(root);
            Attr name = player.createAttribute("name");
            name.setValue(userName);
            root.setAttributeNode(name);

            Element date = player.createElement("datecreated");
            date.appendChild(player.createTextNode(this.date));
            root.appendChild(date);

            save.write(new File(save.get() + "/playerinfo.xml"), player);

        } catch (Exception e) { //just grab all of the exceptions
            e.printStackTrace();
        }

        System.out.println("Player data created.");

    }

    public void existingPlayer(String playerID) {

    }

    public double getResearchTurn() { return this.researchTurn; }
    public double getResourcesTurn() { return this.resourcesTurn; }
    public double getCurrencyTurn() { return this.currencyTurn; }

    //creates a unique encrypted string to store the user data under as an identifier
    private String newUserID() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }

    private String currentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date current = new Date();

        return dateFormat.format(current);
    }

    public void addMapString(mapGenerator map) { //saves the map data to the user data

        //this.mapData = map.toString();

        try {
            save.write(new File(save.get(SaveDirectoryConstants.DATA) + "/map/map"), map);
        } catch (IOException e) {
            e.printStackTrace();
        }

       // System.out.println("Map data saved " + this.mapData);

    }

    public void addStarString(mapGenerator map) { //saves the star data to the user data

        StringBuffer starBuffer = new StringBuffer();

        for (int i = 0; i < map.generatedStars.size(); i++) {
            starBuffer.append(map.generatedStars.get(i).toString());
        }

        starData = starBuffer.toString();

        //System.out.println("Star data saved " + this.starData);

    }

    public void addPlanetString(mapGenerator map) { //saves the planet data to the user data

        StringBuffer planetBuffer = new StringBuffer();

        for (int i = 0; i < map.generatedStars.size(); i++) {

            for (int j = 0; j < map.generatedStars.get(i).planetList.size(); j++) {
                planetBuffer.append(map.generatedStars.get(i).planetList.get(j).toString());
            }
        }

        planetData = planetBuffer.toString();

        //System.out.println("Planet data saved " + this.planetData);

    }

    public void addPlanetColony(colonyCore colony) { //adds colonies to the player's data
        playerColonies.add(colony);
    }

    public double getTaxMultiplier() { return this.taxMultiplier; }

    public double getProductionMultiplier() { return this.productionMultiplier; }

    public double getResources() { return this.resources; }

    public double getFunds() { return this.funds; }



}
