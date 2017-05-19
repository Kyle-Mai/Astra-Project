package Core.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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

    static BufferedImage launcherBackground;
    static BufferedImage loaderBackground;
    static BufferedImage titleScreenBackground;
    static BufferedImage launcherBorder;

    static BufferedImage[] loaderBGList = new BufferedImage[7];

    static BufferedImage gameLogo;
    static BufferedImage gameLogoLarge;

    static Icon loadingIcon;


    public static void gfxPreloader() { //preloads the GFX used by the launcher and loader

        System.out.println("Attempting to preload GFX content...");

        try {
            launcherBackground = ImageIO.read(new File(imageFolder + "/Resources/launcherBG.jpg"));
            loaderBGList[0] = ImageIO.read(new File(imageFolder + "/Resources/loadingBG.jpg"));
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
        int randomBackground = 1 + randBG.nextInt(4);
        loaderBackground = loaderBGList[randomBackground];
        
    }

    public static void loadMainGFX() { //loads the main chunk of the GFX content

        System.out.println("Loading main GFX content...");

        try {
            loaderBGList[1] = ImageIO.read(new File(imageFolder + "/Resources/loadingBG_2.jpg"));
            loaderBGList[2] = ImageIO.read(new File(imageFolder + "/Resources/loadingBG_3.jpg"));
            loaderBGList[3] = ImageIO.read(new File(imageFolder + "/Resources/loadingBG_4.jpg"));
            loaderBGList[4] = ImageIO.read(new File(imageFolder + "/Resources/loadingBG_5.jpg"));
            loaderBGList[6] = ImageIO.read(new File(imageFolder + "/Resources/loadingBG_6.jpg"));
            titleScreenBackground = ImageIO.read(new File(imageFolder + "/Resources/menuBG.jpg"));
            gameLogoLarge = ImageIO.read(new File(imageFolder + "/Resources/icon_large.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("GFX content loaded.");


    }





}
