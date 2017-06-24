package Core.GUI;

import Core.planetCore;
import Core.starCore;
import com.sun.javafx.tk.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
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
 * Java API - Custom font initialization.
 *
 * Self - All of the coding here is done through my own ideas and initiatives, sans the File path, which is listed under the xmlLoader references.
 */

public class gfxRepository implements gfxConstants {

    /** UI Design **/
    //Stores all of the colour and fonts that the game uses.

    private final static File imageFolder = new File(System.getProperty("user.dir") + "/src/Core/GUI");

    public static final String gameVersion = "U1.1 Dev-Build";

    public static Font txtSubtitle;
    public static Font txtItalSubtitle;
    public static Font txtSubheader;
    public static Font txtHeader;
    public static Font txtButtonLarge;
    public static Font txtButtonSmall;
    public static Font txtTitle;
    public static Font txtLargeTitle;
    public static Font txtTiny;
    public static Font txtMicro;
    public static Font txtLargeText;

    public static final Border bdrButtonEnabled = BorderFactory.createCompoundBorder( BorderFactory.createBevelBorder( BevelBorder.RAISED, clrEnable, clrForeground), BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
    public static final Border bdrButtonDisabled = BorderFactory.createCompoundBorder( BorderFactory.createBevelBorder( BevelBorder.RAISED, clrDisableBorder, clrBlk), BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

    private static void initializeFonts() { //custom font designs
        try {
            //set up custom fonts
            Font stylePDark = Font.createFont(Font.TRUETYPE_FONT, new File(imageFolder + "/Resources/fonts/pdark.ttf"));
            Font styleSquare = Font.createFont(Font.TRUETYPE_FONT, new File(imageFolder + "/Resources/fonts/uasquare.ttf"));
            Font styleRexlia = Font.createFont(Font.TRUETYPE_FONT, new File(imageFolder + "/Resources/fonts/rexlia.ttf"));

            //add the new font to some presets
            txtTitle = stylePDark.deriveFont(Font.PLAIN, 32f);
            txtLargeTitle = stylePDark.deriveFont(Font.PLAIN, 60f);
            txtButtonLarge = styleRexlia.deriveFont(Font.PLAIN, 30f);
            txtButtonSmall = styleRexlia.deriveFont(Font.PLAIN, 14f);

            txtLargeText = styleSquare.deriveFont(Font.BOLD, 40f);
            txtSubtitle = styleSquare.deriveFont(Font.PLAIN, 14f);
            txtItalSubtitle = styleSquare.deriveFont(Font.ITALIC, 14f);
            txtHeader = styleSquare.deriveFont(Font.BOLD, 22f);
            txtSubheader = styleSquare.deriveFont(Font.BOLD, 20f);
            txtTiny = styleSquare.deriveFont(Font.PLAIN, 12f);
            txtMicro = styleSquare.deriveFont(Font.PLAIN, 10f);

        } catch (Exception e) {
            e.getMessage();
        }

    }

    /** Element declarations **/

    public static BufferedImage closeButton;
    public static BufferedImage settingsButton;
    public static BufferedImage audioButton;
    public static BufferedImage muteButton;
    public static BufferedImage acceptButton;
    public static BufferedImage rejectButton;
    public static BufferedImage wideButton;
    public static BufferedImage wideButton2;
    public static BufferedImage button435_80;
    public static BufferedImage button99_48;
    public static BufferedImage menuButton;

    public static BufferedImage mainBackground;
    public static BufferedImage launcherBorder;
    public static BufferedImage menuPlanet;
    public static BufferedImage menuGlare;

    private static ArrayList<BufferedImage> loadingScreenBGList = new ArrayList<>();

    public static BufferedImage gameLogo;
    public static BufferedImage gameLogoLarge;
    public static BufferedImage menuSpaceport;

    public static BufferedImage moon1Icon;
    public static BufferedImage moon2Icon;

    public static BufferedImage portraitBorder;
    public static BufferedImage menuBackground;
    public static BufferedImage starPlanetCount;
    public static BufferedImage colonyCount;
    public static BufferedImage homeSystem;
    public static BufferedImage techMenu;
    public static BufferedImage empireMenu;
    public static BufferedImage fleetMenu;
    public static BufferedImage governmentMenu;
    public static BufferedImage mapHighlight;
    public static BufferedImage tallBox;
    public static BufferedImage galaxyReturn;
    public static BufferedImage systemTitle;
    public static BufferedImage leftButton;
    public static BufferedImage rightButton;
    public static BufferedImage pauseBar;
    public static BufferedImage systemOutline;
    public static BufferedImage homePlanet;
    public static BufferedImage colonyIcon;
    public static BufferedImage unknownStar;
    public static BufferedImage systemRefuse;
    public static BufferedImage galaxyDust;
    public static BufferedImage foodIcon;
    public static BufferedImage populationIcon;
    public static BufferedImage unrestIcon;
    public static BufferedImage planetSizeIcon;
    public static BufferedImage resourceIcon;
    public static BufferedImage button532_42;
    public static BufferedImage eventOverlay;
    public static BufferedImage eventBorder;

    public static BufferedImage greenHeader;
    public static BufferedImage blueHeader;
    public static BufferedImage brownHeader;
    public static BufferedImage greyHeader;
    public static BufferedImage redHeader;
    public static BufferedImage yellowHeader;
    public static BufferedImage purpleHeader;
    public static BufferedImage orangeHeader;
    public static BufferedImage cyanHeader;

    public static BufferedImage techBlueBG;
    public static BufferedImage techBlueHeader;
    public static BufferedImage techOrangeBG;
    public static BufferedImage techOrangeHeader;
    public static BufferedImage techGreenBG;
    public static BufferedImage techGreenHeader;
    public static BufferedImage techGreyBG;
    public static BufferedImage techGreyHeader;
    public static BufferedImage techPurpleBG;
    public static BufferedImage techMask;
    public static BufferedImage techRedBG;
    public static BufferedImage techBackground;
    public static BufferedImage techButton;
    public static BufferedImage line_316;
    public static BufferedImage missingIconTech;
    public static BufferedImage techHighlight;

    public static BufferedImage topbar_bg;
    public static BufferedImage topbar_shield;
    public static BufferedImage orbitIndicator;

    public static BufferedImage researchIcon;
    public static BufferedImage mineralsIcon;
    public static BufferedImage energyIcon;

    public static BufferedImage mineralsIconSmall;
    public static BufferedImage energyIconSmall;

    public static BufferedImage shipHighlight;

    public static Icon loadingIcon;

    /** Methods **/

    public static void gfxPreloader() { //preloads the GFX used by the launcher and loader

        initializeFonts();

        System.out.println("Attempting to preload GFX content...");

        try {
            mainBackground = ImageIO.read(new File(imageFolder + "/Resources/background/launcher.png"));
            loadingScreenBGList.add(ImageIO.read(new File(imageFolder + "/Resources/background/loadingBG.jpg")));
            closeButton = ImageIO.read(new File(imageFolder + "/Resources/ui/button_close.png"));
            settingsButton = ImageIO.read(new File(imageFolder + "/Resources/ui/button_settings.png"));
            audioButton = ImageIO.read(new File(imageFolder + "/Resources/ui/button_sound.png"));
            muteButton = ImageIO.read(new File(imageFolder + "/Resources/ui/button_mute.png"));
            acceptButton = ImageIO.read(new File(imageFolder + "/Resources/ui/button_accept.png"));
            rejectButton = ImageIO.read(new File(imageFolder + "/Resources/ui/button_reject.png"));
            wideButton = ImageIO.read(new File(imageFolder + "/Resources/ui/button_large.png"));
            wideButton2 = ImageIO.read(new File(imageFolder + "/Resources/ui/button_large2.png"));

            Thread temp = new Thread() { //creates a temporary thread to continue loading non-essential images in the background
                public void run() {
                    try {
                        //adds other papers to the loading screen randomize
                        loadingScreenBGList.add(ImageIO.read(new File(imageFolder + "/Resources/background/loadingBG_2.jpg")));
                        loadingScreenBGList.add(ImageIO.read(new File(imageFolder + "/Resources/background/loadingBG_3.jpg")));
                        loadingScreenBGList.add(ImageIO.read(new File(imageFolder + "/Resources/background/loadingBG_4.jpg")));
                        loadingScreenBGList.add(ImageIO.read(new File(imageFolder + "/Resources/background/loadingBG_5.jpg")));
                        loadingScreenBGList.add(ImageIO.read(new File(imageFolder + "/Resources/background/loadingBG_6.jpg")));
                        loadingScreenBGList.add(ImageIO.read(new File(imageFolder + "/Resources/background/loadingBG_7.jpg")));
                        loadingScreenBGList.add(ImageIO.read(new File(imageFolder + "/Resources/background/loadingBG_8.jpg")));
                        loadingScreenBGList.add(ImageIO.read(new File(imageFolder + "/Resources/background/loadingBG_9.jpg")));
                        loadingScreenBGList.add(ImageIO.read(new File(imageFolder + "/Resources/background/loadingBG_10.jpg")));
                        loadingScreenBGList.add(ImageIO.read(new File(imageFolder + "/Resources/background/loadingBG_11.jpg")));
                        //loadingScreenBGList.add(ImageIO.read(new File(imageFolder + "/Resources/background/loadingBG_13.jpg")));
                        System.out.println("GFX background images finished loading successfully.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    interrupt();
                }
            };
            temp.start();

            gameLogo = ImageIO.read(new File(imageFolder + "/Resources/ui/icon.png"));
            launcherBorder = ImageIO.read(new File(imageFolder + "/Resources/launcherBorder.png"));
            loadingIcon = new ImageIcon(imageFolder + "/Resources/ui/loadingicon2.gif");

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

        //unloads content to dump from memory
        launcherBorder = null;

        //loads main GFX content
        System.out.println("Loading main GFX content...");

        try {
            mainBackground = ImageIO.read(new File(imageFolder + "/Resources/background/title_background_full.png"));
            gameLogoLarge = ImageIO.read(new File(imageFolder + "/Resources/ui/icon_large.png"));
            menuPlanet = ImageIO.read(new File(imageFolder + "/Resources/title_planet_full.png"));
            menuSpaceport = ImageIO.read(new File(imageFolder + "/Resources/title_spaceport_half.png"));
            moon1Icon = ImageIO.read(new File(imageFolder + "/Resources/title_moon1_half.png"));
            moon2Icon = ImageIO.read(new File(imageFolder + "/Resources/title_moon2_half.png"));
            menuGlare = ImageIO.read(new File(imageFolder + "/Resources/effects/glare.png"));
            button435_80 = ImageIO.read(new File(imageFolder + "/Resources/ui/button_435_80.png"));

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
                directory = new File(System.getProperty("user.dir") + "/src" + starCore.listOfStars.get(i).getGfx()); //sets the star's portrait
                temporaryImage = ImageIO.read(directory);
                starCore.listOfStars.get(i).setGfxImage(temporaryImage);
                directory = new File(System.getProperty("user.dir") + "/src" + starCore.listOfStars.get(i).getIcon()); //sets the star's icon
                temporaryImage = ImageIO.read(directory);
                starCore.listOfStars.get(i).setStarIcon(temporaryImage);
                System.out.print(starCore.listOfStars.get(i).getName() + " GFX content loaded successfully. ");
                //clear variables for garbage collector
                temporaryImage = null;
                directory = null;
            } catch (IOException e) {
                e.printStackTrace(); //TODO: Add a default for if it fails to load.
            }
        }

        for (int i = 0; i < planetCore.listOfPlanets.size(); i++) {

            try {
                directory = new File(System.getProperty("user.dir") + "/src" + planetCore.listOfPlanets.get(i).getGfx()); //sets the star's icon
                temporaryImage = ImageIO.read(directory);
                planetCore.listOfPlanets.get(i).setGfxImage(temporaryImage);
                directory = new File(System.getProperty("user.dir") + "/src" + planetCore.listOfPlanets.get(i).getIcon());
                temporaryImage = ImageIO.read(directory);
                planetCore.listOfPlanets.get(i).setPlanetIcon(temporaryImage);
                //clear variables for garbage collector
                temporaryImage = null;
                directory = null;
            } catch (IOException e) {
                e.printStackTrace(); //TODO: Add a default for if it fails to load.
            }

        }

        System.out.println("\nContent GFX loaded.");
    }

    public static void loadMapGFX() {

        //removes all of the main menu GFX from the active memory
        gameLogoLarge = null;
        menuGlare = null;
        menuSpaceport = null;
        moon1Icon = null;
        moon2Icon = null;
        menuPlanet = null;
        muteButton = null;

        //loads map GFX
        System.out.println("Loading map screen GFX content...");

        try {
            mapHighlight = ImageIO.read(new File(imageFolder + "/Resources/ui/hover_indicator.png"));
            mainBackground = ImageIO.read(new File(imageFolder + "/Resources/mapBG.png"));
            portraitBorder = ImageIO.read(new File(imageFolder + "/Resources/portraits/overlay.png"));
            menuBackground = ImageIO.read(new File(imageFolder + "/Resources/background/general_bg.png"));
            starPlanetCount = ImageIO.read(new File(imageFolder + "/Resources/ui/planet_number.png"));
            colonyCount = ImageIO.read(new File(imageFolder + "/Resources/ui/embassy.png"));
            homeSystem = ImageIO.read(new File(imageFolder + "/Resources/ui/home_system.png"));
            techMenu = ImageIO.read(new File(imageFolder + "/Resources/ui/topbar_tech_button.png"));
            researchIcon = ImageIO.read(new File(imageFolder + "/Resources/ui/research2.png"));
            fleetMenu = ImageIO.read(new File(imageFolder + "/Resources/ui/topbar_fleet_button.png"));
            governmentMenu = ImageIO.read(new File(imageFolder + "/Resources/ui/topbar_government_button.png"));
            empireMenu = ImageIO.read(new File(imageFolder + "/Resources/ui/topbar_empire_button.png"));
            button99_48 = ImageIO.read(new File(imageFolder + "/Resources/ui/button_99_48.png"));
            menuButton = ImageIO.read(new File(imageFolder + "/Resources/ui/topbar_menu_button.png"));
            energyIcon = ImageIO.read(new File(imageFolder + "/Resources/ui/energy2.png"));
            mineralsIcon = ImageIO.read(new File(imageFolder + "/Resources/ui/minerals2.png"));
            tallBox = ImageIO.read(new File(imageFolder + "/Resources/ui/tall_box_1.png"));
            galaxyReturn = ImageIO.read(new File(imageFolder + "/Resources/ui/button_galaxy_map.png"));
            systemTitle = ImageIO.read(new File(imageFolder + "/Resources/ui/starsystem_panel.png"));
            leftButton = ImageIO.read(new File(imageFolder + "/Resources/ui/button_left.png"));
            rightButton = ImageIO.read(new File(imageFolder + "/Resources/ui/button_right.png"));
            pauseBar = ImageIO.read(new File(imageFolder + "/Resources/ui/paused_bar.png"));
            topbar_bg = ImageIO.read(new File(imageFolder + "/Resources/ui/topbar_background.png"));
            topbar_shield = ImageIO.read(new File(imageFolder + "/Resources/ui/topbar_shield_decoration.png"));
            orbitIndicator = ImageIO.read(new File(imageFolder + "/Resources/background/orbit_indicate.png"));
            systemOutline = ImageIO.read(new File(imageFolder + "/Resources/effects/system_outline.png"));
            homePlanet = ImageIO.read(new File(imageFolder + "/Resources/ui/icon_capital.png"));
            colonyIcon = ImageIO.read(new File(imageFolder + "/Resources/ui/icon_colony.png"));
            unknownStar = ImageIO.read(new File(imageFolder + "/Resources/stars/unknown.png"));
            systemRefuse = ImageIO.read(new File(imageFolder + "/Resources/ui/system_refuse.png"));
            galaxyDust = ImageIO.read(new File(imageFolder + "/Resources/effects/galaxy_dust.png"));
            populationIcon = ImageIO.read(new File(imageFolder + "/Resources/ui/icon_pop.png"));
            foodIcon = ImageIO.read(new File(imageFolder + "/Resources/ui/icon_food.png"));
            unrestIcon = ImageIO.read(new File(imageFolder + "/Resources/ui/icon_unrest.png"));
            planetSizeIcon = ImageIO.read(new File(imageFolder + "/Resources/ui/planet_size.png"));
            resourceIcon = ImageIO.read(new File(imageFolder + "/Resources/ui/icon_resource.png"));
            button532_42 = ImageIO.read(new File(imageFolder + "/Resources/ui/button_532_42.png"));

            greenHeader = ImageIO.read(new File(imageFolder + "/Resources/ui/header_green.png"));
            redHeader = ImageIO.read(new File(imageFolder + "/Resources/ui/header_red.png"));
            orangeHeader = ImageIO.read(new File(imageFolder + "/Resources/ui/header_orange.png"));
            blueHeader = ImageIO.read(new File(imageFolder + "/Resources/ui/header_blue.png"));
            cyanHeader = ImageIO.read(new File(imageFolder + "/Resources/ui/header_cyan.png"));
            brownHeader = ImageIO.read(new File(imageFolder + "/Resources/ui/header_brown.png"));
            greyHeader = ImageIO.read(new File(imageFolder + "/Resources/ui/header_grey.png"));
            yellowHeader = ImageIO.read(new File(imageFolder + "/Resources/ui/header_yellow.png"));
            purpleHeader = ImageIO.read(new File(imageFolder + "/Resources/ui/header_purple.png"));
            eventOverlay = ImageIO.read(new File(imageFolder + "/Resources/event/event_overlay.png"));
            eventBorder = ImageIO.read(new File(imageFolder + "/Resources/event/event_border.png"));

            techBlueBG = ImageIO.read(new File(imageFolder + "/Resources/tech/bg_blue.png"));
            techBlueHeader = ImageIO.read(new File(imageFolder + "/Resources/tech/header_blue.png"));
            techGreyBG = ImageIO.read(new File(imageFolder + "/Resources/tech/bg_grey.png"));
            techGreyHeader = ImageIO.read(new File(imageFolder + "/Resources/tech/header_grey.png"));
            techGreenBG = ImageIO.read(new File(imageFolder + "/Resources/tech/bg_green.png"));
            techGreenHeader = ImageIO.read(new File(imageFolder + "/Resources/tech/header_green.png"));
            techOrangeBG = ImageIO.read(new File(imageFolder + "/Resources/tech/bg_orange.png"));
            techOrangeHeader = ImageIO.read(new File(imageFolder + "/Resources/tech/header_orange.png"));
            techPurpleBG = ImageIO.read(new File(imageFolder + "/Resources/tech/bg_rare.png"));
            techRedBG = ImageIO.read(new File(imageFolder + "/Resources/tech/bg_dangerous.png"));
            techMask = ImageIO.read(new File(imageFolder + "/Resources/tech/bg_rare_mask.png"));
            techBackground = ImageIO.read(new File(imageFolder + "/Resources/tech/tech_background.png"));
            techButton = ImageIO.read(new File(imageFolder + "/Resources/tech/tech_button.png"));
            line_316 = ImageIO.read(new File(imageFolder + "/Resources/tech/line.png"));
            missingIconTech = ImageIO.read(new File(imageFolder + "/Resources/tech/unknown.png"));
            techHighlight = ImageIO.read(new File(imageFolder + "/Resources/tech/selection_highlight.png"));

            mineralsIconSmall = ImageIO.read(new File(imageFolder + "/Resources/ui/minerals.png"));
            energyIconSmall = ImageIO.read(new File(imageFolder + "/Resources/ui/energy.png"));

            shipHighlight = ImageIO.read(new File(imageFolder + "/Resources/ships/ship_selection.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
