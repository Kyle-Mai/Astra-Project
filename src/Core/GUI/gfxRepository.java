package Core.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * KM
 * May 18 2017
 * Stores images.
 */
public class gfxRepository {

    final static File imageFolder = new File(System.getProperty("user.dir") + "/src/Core/GUI");

    static BufferedImage launcherBackground;
    static BufferedImage loaderBackground;
    static BufferedImage titleScreenBackground;
    static BufferedImage launcherBorder;

    static BufferedImage gameLogo;
    static BufferedImage gameLogoLarge;

    static Icon loadingIcon;


    public static void gfxPreloader() { //preloads the GFX used by the launcher and loader

        System.out.println("Attempting to preload GFX content...");

        try {
            launcherBackground = ImageIO.read(new File(imageFolder + "/Resources/launcherBG.jpg"));
            loaderBackground = ImageIO.read(new File(imageFolder + "/Resources/loadingBG.jpg"));
            gameLogo = ImageIO.read(new File(imageFolder + "/Resources/icon.png"));
            launcherBorder = ImageIO.read(new File(imageFolder + "/Resources/launcherBorder.png"));
            loadingIcon = new ImageIcon(imageFolder + "/Resources/ok_hand.gif");

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("GFX content preloaded.");

    }

    public static void loadMainGFX() { //loads the main chunk of the GFX content

        System.out.println("Loading main GFX content...");

        try {
            titleScreenBackground = ImageIO.read(new File(imageFolder + "/Resources/menuBG.jpg"));
            gameLogoLarge = ImageIO.read(new File(imageFolder + "/Resources/icon_large.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("GFX content loaded.");


    }





}
