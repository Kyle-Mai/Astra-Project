package Core.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * KM
 * May 18 2017
 * Stores images.
 *
 * SOURCES:
 * Internet Searches - All GFX content is NOT MY OWN CREATION. I take NO CREDIT for all of the GFX used in this program.
 * I have edited some of them to better fit the game, but none of the GFX images/gifs are originals.
 *
 * Self - All of the coding here is done through my own ideas and initiatives, sans the File path, which is listed under the xmlLoader references.
 */

public class gfxRepository {

    final static File imageFolder = new File(System.getProperty("user.dir") + "/src/Core/GUI");

    static BufferedImage mainBackground;
    static BufferedImage launcherBorder;
    static BufferedImage menuPlanet;

    static ArrayList<BufferedImage> loadingScreenBGList = new ArrayList<>();

    static BufferedImage gameLogo;
    static BufferedImage gameLogoLarge;
    static BufferedImage menuShip;
    static BufferedImage menuSpaceport;

    static Icon loadingIcon;


    public static void gfxPreloader() { //preloads the GFX used by the launcher and loader

        System.out.println("Attempting to preload GFX content...");

        try {
            mainBackground = ImageIO.read(new File(imageFolder + "/Resources/launcherBG.jpg"));
            loadingScreenBGList.add(ImageIO.read(new File(imageFolder + "/Resources/loadingBG.jpg")));
            gameLogo = ImageIO.read(new File(imageFolder + "/Resources/icon.png"));
            launcherBorder = ImageIO.read(new File(imageFolder + "/Resources/launcherBorder.png"));
            loadingIcon = new ImageIcon(imageFolder + "/Resources/ok_hand.gif");

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("GFX content preloaded.");

    }

    public static void randomBackground() {
        Random randBG = new Random();
        int randomBackground = randBG.nextInt(loadingScreenBGList.size());
        mainBackground = loadingScreenBGList.get(randomBackground);
        
    }

    public static void loadMainGFX() { //loads the main chunk of the GFX content

        System.out.println("Loading main GFX content...");

        try {
            mainBackground = ImageIO.read(new File(imageFolder + "/Resources/title_background_full.png"));
            gameLogoLarge = ImageIO.read(new File(imageFolder + "/Resources/icon_large.png"));
            menuShip = ImageIO.read(new File(imageFolder + "/Resources/spacecraft_gfx_small.png"));
            menuPlanet = ImageIO.read(new File(imageFolder + "/Resources/title_planet_full.png"));
            menuSpaceport = ImageIO.read(new File(imageFolder + "/Resources/title_spaceport_half.png"));

            //adds other papers to the loading screen randomize
            loadingScreenBGList.add(ImageIO.read(new File(imageFolder + "/Resources/loadingBG_2.jpg")));
            loadingScreenBGList.add(ImageIO.read(new File(imageFolder + "/Resources/loadingBG_3.jpg")));
            loadingScreenBGList.add(ImageIO.read(new File(imageFolder + "/Resources/loadingBG_4.jpg")));
            loadingScreenBGList.add(ImageIO.read(new File(imageFolder + "/Resources/loadingBG_5.jpg")));
            loadingScreenBGList.add(ImageIO.read(new File(imageFolder + "/Resources/loadingBG_6.jpg")));
            loadingScreenBGList.add(ImageIO.read(new File(imageFolder + "/Resources/loadingBG_7.jpg")));
            loadingScreenBGList.add(ImageIO.read(new File(imageFolder + "/Resources/loadingBG_8.jpg")));

            System.out.println("GFX content loaded successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void loadMapGFX() {

        System.out.println("Loading map screen GFX content...");

        try {
            mainBackground = ImageIO.read(new File(imageFolder + "/Resources/mapBG.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }





}
