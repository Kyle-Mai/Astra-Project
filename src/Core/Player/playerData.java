package Core.Player;

import Core.mapGenerator;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * KM
 * May 19 2017
 * Repository that stores the player's information so it's easy to manipulate
 *
 * SOURCES:
 * Stack Overflow - Random string/encryption via http://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string
 * Mkyong - Getting the current date and time via https://www.mkyong.com/java/java-how-to-get-current-date-time-date-and-calender/
 * Self - All other work.
 */

public class playerData {

    private String userID;
    private String userName;
    private String date;

    private double currency;
    private double research;
    private int techLevel;

    private String tech1;
    private String tech2;
    private String tech3;

    private String mapData;
    private String starData;
    private String planetData;


    public playerData() {
    }

    public void newPlayer(String username) {
        System.out.println("Creating new player data...");

        this.userID = returnUserID(); //gets the unique ID used to identify this player
        this.date = currentDate(); //gets the current time, used to order the save files
        this.userName = username; //user's username

        //sets the default player values
        this.currency = 0;
        this.research = 0;
        this.techLevel = 0;

        System.out.println("Player data successfully created.");

    }

    public void existingPlayer() {

    }

    //creates a unique encrypted string to store the user data under as an identifier
    private String returnUserID() {
        SecureRandom random = new SecureRandom();

        return new BigInteger(130, random).toString(32);
    }

    private String currentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date current = new Date();

        return dateFormat.format(current);
    }

    public void addMapString(mapGenerator map) { //saves the map data to the user data

        this.mapData = map.toString();

        System.out.println("Map data saved " + this.mapData);

    }

    public void addStarString(mapGenerator map) { //saves the star data to the user data

        StringBuffer starBuffer = new StringBuffer();

        for (int i = 0; i < map.generatedStars.size(); i++) {
            starBuffer.append(map.generatedStars.get(i).toString());
        }

        starData = starBuffer.toString();

        System.out.println("Star data saved " + this.starData);

    }

    public void addPlanetString(mapGenerator map) { //saves the planet data to the user data

        StringBuffer planetBuffer = new StringBuffer();

        for (int i = 0; i < map.generatedStars.size(); i++) {

            for (int j = 0; j < map.generatedStars.get(i).planetList.size(); j++) {
                planetBuffer.append(map.generatedStars.get(i).planetList.get(j).toString());
            }
        }

        planetData = planetBuffer.toString();

        System.out.println("Planet data saved " + this.planetData);

    }





}
