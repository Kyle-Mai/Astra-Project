package Core;

/**
    KM
    May 12 2017
    Core to handle the rendering and display of UI elements.
 */

//import all relevant stuff
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class guiCore extends JFrame {


    private File resourcesFolder = new File(("user.dir") + "/src/Resources"); //stores the resources folder

    /** Stores resource declarations **/
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //gets the screen size of the user
    double screenWidth = screenSize.getWidth();
    double screenHeight = screenSize.getHeight();

    protected ImageIcon gameIcon = new ImageIcon(resourcesFolder + "/icon.png");
    ArrayList<JPanel> bgPanels = new ArrayList<>();
    ArrayList<JButton> btnFramework = new ArrayList<>();

    /** UI scaling code**/
    //handles the scaling of the UI

    private int[] currentUIScale = {0, 0}; //default screen scale

    //methods to return the UI elements
    public int getUIScaleX() { return this.currentUIScale[0]; }

    public int getUIScaleY() { return this.currentUIScale[1]; }

    //sets the window size to the chosen monitor scale
    public void rescaleScreen(int option) {

        switch(option) {
            //Widescreen monitors
            case 1: //4K
                currentUIScale[0] = 3840; //X scale
                currentUIScale[1] = 2160; //Y scale
                break;
            case 2: //2K
                currentUIScale[0] = 2560;
                currentUIScale[1] = 1440;
                break;
            case 3: //standard monitor
                currentUIScale[0] = 1920;
                currentUIScale[1] = 1080;
                break;
            case 4:
                currentUIScale[0] = 1600;
                currentUIScale[1] = 900;
                break;
            case 5:
                currentUIScale[0] = 1366;
                currentUIScale[1] = 768;
                break;
            case 6:
                currentUIScale[0] = 1280;
                currentUIScale[1] = 720;
                break;
            //4:3 and similar monitor scales
            case 7:
                currentUIScale[0] = 1600;
                currentUIScale[1] = 1200;
                break;
            case 8:
                currentUIScale[0] = 1280;
                currentUIScale[1] = 1024;
                break;
            case 9:
                currentUIScale[0] = 1024;
                currentUIScale[1] = 768;
                break;
            case 10:
                currentUIScale[0] = 800;
                currentUIScale[1] = 600;
                break;
            case 11: //Launcher UI scale
                currentUIScale[0] = 600;
                currentUIScale[1] = 400;
                break;
            default: //safety net
                currentUIScale[0] = 500;
                currentUIScale[1] = 500;
                break;
        }

        this.setSize(getUIScaleX(), getUIScaleY());
        bgPanels.get(0).setSize(getUIScaleX(), getUIScaleY());

        System.out.println("UI rescaled.");

    }

    /** Constructors for the UI core **/

    public guiCore(String screenName, boolean unDecorated) {

        this.setName(screenName);

        if (gameIcon != null) {
            this.setIconImage(gameIcon.getImage()); //sets the icon
        } else {
            System.out.println("Error - Game icon is null, may indicate a corrupted resources folder.");
        }

        this.setResizable(false); //not resizable
        this.setSize(this.getUIScaleX(), this.getUIScaleY()); //scales it to the user scaling
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); //when closed, dispose of everything
        this.setUndecorated(unDecorated); //whether or not we want the buttons at the top, generally no
        this.setVisible(true); //sets it to visible

        bgPanels.add(new JPanel()); //adds the main background panel - the content pane
        this.setContentPane(bgPanels.get(0)); //sets the content pane to the default JPanel
        loadLauncher();
        bgPanels.get(0).setBackground(colourValue("Black"));
        bgPanels.get(0).setVisible(true);

    }


    /** Base Methods **/
    //Methods used by the UI core and most UI elements.

    //Takes colour input as a string, verifies that it is a legitimate colour via java colours and outputs it.
    public Color colourValue(String getColour) { //Verifies the colour values.
        String colour = getColour;
        Color colour2;

        colour = colour.toUpperCase(); //Converts the string to all uppercase.
        try {
            Field field = Class.forName("java.awt.Color").getField(colour); //Checks to ensure the colour entered is valid.
            colour2 = (Color)field.get(null); //If it is valid, then we're set.
        } catch (Exception e) { //Otherwise, it isn't defined.
            colour2 = Color.black; // Not defined/valid, default to black
            System.out.println("Invalid colour.");
        }
        return (colour2); //Returns the colour value.
    }

    //methods to return the x and y of the base screen to simplify the code a bit
    public int getWindowScaleX() {
        return bgPanels.get(0).getWidth();
    }

    public int getWindowScaleY() {
        return bgPanels.get(0).getHeight();
    }

    //minimizes the program
    public void minimizeWindow() {
        this.setState(Frame.ICONIFIED);
    }

    //closes the program down
    public void closeProgram() {
        System.exit(0);
    }


    /** Cleaner Methods **/
    //methods that clean up the UI framework to make room for new elements

    public void clearAll() {

        //clears all excess content
        if (bgPanels.size() > 1) { //we don't want to remove the default panel. ever.
            bgPanels.subList(1, bgPanels.size()).clear();
        }
        btnFramework.clear();

    }

    /** UI Designs **/
    //methods for loading and removing the different UI elements

    //loads the launcher data for the UI
    public void loadLauncher() {

        //Constants of different UI element scales, stored here to simplify editing
        final int btnCloseXScale = 30;
        final int btnCloseYScale = 30;
        final String btnCloseText = "X";
        final String btnMinimizeText = "-";
        final Font defaultFont = new Font("Arial", Font.BOLD, 10);

        //load UI elements
        System.out.println("Loading launcher UI module...");

        rescaleScreen(10); //rescales the screen to the launcher size
        this.setLocation( ((int)screenWidth / 2) - (this.getUIScaleX() / 2), ((int)screenHeight / 2) - (this.getUIScaleY() / 2) ); //sets the launcher to the center of the screen

        //adds a close button to the menu
        btnFramework.add(new JButton(btnCloseText));
        getContentPane().add(btnFramework.get(0));
        btnFramework.get(0).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Killing program.");
                closeProgram();
            }
        });
        btnFramework.get(0).setVisible(true);
        btnFramework.get(0).setBounds((getWindowScaleX() - btnCloseXScale), 0, btnCloseXScale, btnCloseYScale); //posx, posy, scalx, scaly
        //btnFramework.get(0).setFont(defaultFont);

        //adds a minimize button to the menu
        btnFramework.add(new JButton(btnMinimizeText));
        getContentPane().add(btnFramework.get(1));
        btnFramework.get(1).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Minimizing program.");
                minimizeWindow();
            }
        });
        btnFramework.get(1).setVisible(true);
        btnFramework.get(1).setBounds((getWindowScaleX() - (btnCloseXScale + btnCloseXScale)), 0, btnCloseXScale, btnCloseYScale); //posx, posy, scalx, scaly
        //btnFramework.get(1).setFont(defaultFont);

        System.out.println("Launcher UI module loaded.");

    }


}
