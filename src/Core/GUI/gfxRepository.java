package Core.GUI;

import Core.starCore;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
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

    /** UI Design **/
    //Stores all of the colour and fonts that the game uses.

    static final String gameVersion = "PTB-B Build 72a";

    static final CompoundBorder bdrButtonEnabled = BorderFactory.createCompoundBorder( BorderFactory.createBevelBorder( BevelBorder.RAISED, gfxRepository.clrEnable, gfxRepository.clrForeground), BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
    static final CompoundBorder bdrButtonDisabled = BorderFactory.createCompoundBorder( BorderFactory.createBevelBorder( BevelBorder.RAISED, gfxRepository.clrDisableBorder, gfxRepository.clrBlk), BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

    static final Color clrText = new Color(255, 255, 255, 255);
    static final Color clrInvisible = new Color(0, 0, 0, 0);
    static final Color clrTrueBlack = new Color(0, 0, 0, 255);
    static final Color clrBlkTransparent = new Color(15, 35, 25, 175);
    static final Color clrBlk = new Color(25, 35, 35, 255);
    static final Color clrDGrey = new Color(45, 75, 65, 255);
    static final Color clrDisableBorder = new Color(75, 5, 25, 255);
    static final Color clrDisable = new Color(135, 15, 55, 255);
    static final Color clrEnable = new Color(0, 225, 165, 255);
    static final Color clrDark = new Color(0, 145, 90, 255);
    static final Color clrButtonBackground = new Color(0, 125, 90, 255);
    static final Color clrBGOpaque = new Color(25, 90, 60, 255);
    static final Color clrButtonMain = new Color(0, 155, 90, 255);
    static final Color clrBackground = new Color(0, 195, 130, 105);
    static final Color clrForeground = new Color(0, 185, 110, 155);

    static final Font txtStandard = new Font("Comic Sans", Font.PLAIN, 15);
    static final Font txtSubtitle = new Font("Arial", Font.BOLD, 14);
    static final Font txtItalSubtitle = new Font("Arial", Font.ITALIC, 14);
    static final Font txtSubheader = new Font("Arial", Font.BOLD, 16);
    static final Font txtHeader = new Font("Arial", Font.BOLD, 25);
    static final Font txtTitle = new Font("Arial", Font.BOLD, 40);
    static final Font txtTiny = new Font("Arial", Font.PLAIN, 12);

    /** Element declarations **/

    final static File imageFolder = new File(System.getProperty("user.dir") + "/src/Core/GUI");

    static BufferedImage mainBackground;
    static BufferedImage launcherBorder;
    static BufferedImage menuPlanet;

    static ArrayList<BufferedImage> loadingScreenBGList = new ArrayList<>();

    static BufferedImage gameLogo;
    static BufferedImage gameLogoLarge;
    static BufferedImage menuShip;
    static BufferedImage menuSpaceport;

    static BufferedImage planetIcon;
    static BufferedImage moon1Icon;
    static BufferedImage moon2Icon;

    static Icon loadingIcon;

    public static BufferedImage getLauncherBorder() {
        return launcherBorder;
    }

    /** Methods **/

    public static void gfxPreloader() { //preloads the GFX used by the launcher and loader

        System.out.println("Attempting to preload GFX content...");

        try {
            mainBackground = ImageIO.read(new File(imageFolder + "/Resources/launcherBG.jpg"));
            loadingScreenBGList.add(ImageIO.read(new File(imageFolder + "/Resources/background/loadingBG.jpg")));
            gameLogo = ImageIO.read(new File(imageFolder + "/Resources/ui/icon.png"));
            launcherBorder = ImageIO.read(new File(imageFolder + "/Resources/launcherBorder.png"));
            loadingIcon = new ImageIcon(imageFolder + "/Resources/ui/ok_hand.gif");

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
            mainBackground = ImageIO.read(new File(imageFolder + "/Resources/background/title_background_full.png"));
            gameLogoLarge = ImageIO.read(new File(imageFolder + "/Resources/ui/icon_large.png"));
            menuShip = ImageIO.read(new File(imageFolder + "/Resources/spacecraft_gfx_small.png"));
            menuPlanet = ImageIO.read(new File(imageFolder + "/Resources/title_planet_full.png"));
            menuSpaceport = ImageIO.read(new File(imageFolder + "/Resources/title_spaceport_half.png"));
            moon1Icon = ImageIO.read(new File(imageFolder + "/Resources/title_moon1_half.png"));
            moon2Icon = ImageIO.read(new File(imageFolder + "/Resources/title_moon2_half.png"));

            //adds other papers to the loading screen randomize
            loadingScreenBGList.add(ImageIO.read(new File(imageFolder + "/Resources/background/loadingBG_2.jpg")));
            loadingScreenBGList.add(ImageIO.read(new File(imageFolder + "/Resources/background/loadingBG_3.jpg")));
            loadingScreenBGList.add(ImageIO.read(new File(imageFolder + "/Resources/background/loadingBG_4.jpg")));
            loadingScreenBGList.add(ImageIO.read(new File(imageFolder + "/Resources/background/loadingBG_5.jpg")));
            loadingScreenBGList.add(ImageIO.read(new File(imageFolder + "/Resources/background/loadingBG_6.jpg")));
            loadingScreenBGList.add(ImageIO.read(new File(imageFolder + "/Resources/background/loadingBG_7.jpg")));
            loadingScreenBGList.add(ImageIO.read(new File(imageFolder + "/Resources/background/loadingBG_8.jpg")));

            System.out.println("GFX content loaded successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void loadContentGFX() { //loads the GFX of stars, planets, etc
        BufferedImage temporaryImage;
        File directory;

        for (int i = 0; i < starCore.listOfStars.size(); i++) {
            try {
                directory = new File(System.getProperty("user.dir") + "/src" + starCore.listOfStars.get(i).getGfx());
                temporaryImage = ImageIO.read(directory);
                starCore.listOfStars.get(i).setGfxImage(temporaryImage);
                System.out.print(starCore.listOfStars.get(i).getName() + " GFX content loaded successfully. ");
            } catch (IOException e) {
                e.printStackTrace(); //TODO: Add a default for if it fails to load.
            }
        }

        System.out.println("\nContent GFX loaded.");
    }

    public static void loadMapGFX() {

        System.out.println("Loading map screen GFX content...");

        try {
            mainBackground = ImageIO.read(new File(imageFolder + "/Resources/mapBG.png"));
            planetIcon = ImageIO.read(new File(imageFolder + "/Resources/no_moon.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }





}
