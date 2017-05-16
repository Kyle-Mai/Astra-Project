package Core.GUI;

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

public class guiCore extends JPanel {


    private File resourcesFolder = new File(("user.dir") + "/src/Core/Resources"); //stores the resources folder

    /** Stores resource declarations **/
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //gets the screen size of the user
    double screenWidth = screenSize.getWidth();
    double screenHeight = screenSize.getHeight();
    JFrame mainScreen = new JFrame(); //stores the main window

    protected ImageIcon gameIcon = new ImageIcon(resourcesFolder + "/icon.png");

    /** UI scaling code**/
    //handles the scaling of the UI

    private int[] currentUIScale = {0, 0}; //default screen scale

    //methods to return the UI elements
    public int getUIScaleX() { return this.currentUIScale[0]; }

    public int getUIScaleY() { return this.currentUIScale[1]; }

    //sets the window size to the chosen monitor scale
    public void rescaleScreen(int option) {

        int oldX, oldY; //stores the previous values in case something goes wrong

        oldX = this.getUIScaleX();
        oldY = this.getUIScaleY();

        switch(option) {
            //Widescreen monitors
            case 1: //4K
                this.currentUIScale[0] = 3840; //X scale
                this.currentUIScale[1] = 2160; //Y scale
                break;
            case 2: //2K
                this.currentUIScale[0] = 2560;
                this.currentUIScale[1] = 1440;
                break;
            case 3: //standard monitor
                this.currentUIScale[0] = 1920;
                this.currentUIScale[1] = 1080;
                break;
            case 4:
                this.currentUIScale[0] = 1600;
                this.currentUIScale[1] = 900;
                break;
            case 5:
                this.currentUIScale[0] = 1366;
                this.currentUIScale[1] = 768;
                break;
            case 6:
                this.currentUIScale[0] = 1280;
                this.currentUIScale[1] = 720;
                break;
            //4:3 and similar monitor scales
            case 7:
                this.currentUIScale[0] = 1600;
                this.currentUIScale[1] = 1200;
                break;
            case 8:
                this.currentUIScale[0] = 1280;
                this.currentUIScale[1] = 1024;
                break;
            case 9:
                this.currentUIScale[0] = 1024;
                this.currentUIScale[1] = 768;
                break;
            case 10:
                this.currentUIScale[0] = 800;
                this.currentUIScale[1] = 600;
                break;
            case 11: //Launcher UI scale
                this.currentUIScale[0] = 600;
                this.currentUIScale[1] = 400;
                break;
            default: //safety net
                this.currentUIScale[0] = 500;
                this.currentUIScale[1] = 500;
                break;
        }

        if (this.getUIScaleX() > screenWidth || this.getUIScaleY() > screenHeight) {
            System.out.println("Monitor is not large enough to support this resolution.");
            this.currentUIScale[0] = oldX; //resets the screen scale to the previous values
            this.currentUIScale[1] = oldY;
        } else {
            this.setBounds(0, 0, this.getUIScaleX(), this.getUIScaleY());
        }


        System.out.println("UI rescaled.");

    }

    /** Constructors for the UI core **/

    public guiCore(int screenScale) {
        this.setBackground(newColour("black"));
        this.rescaleScreen(screenScale);
        jFrNew();

    }

    /** JFrame handling **/

    private void jFrNew() { //instances a new JFrame
        mainScreen = new JFrame();
        mainScreen.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        mainScreen.setIconImage(gameIcon.getImage()); //sets the game icon
        mainScreen.getContentPane().add(this);
        mainScreen.setVisible(true);
        jFrDecoration(true);

    }

    private void jFrName(String name) { //sets the name of the JFrame
        mainScreen.setName(name);
    }

    private void jFrScale(boolean fullScreen) { //Rescales the JFrame
        if (!fullScreen) {
            //not fullscreen, set the frame to the center of the screen
            mainScreen.setBounds((int)(screenWidth / 2) - (this.getUIScaleX() / 2), (int)(screenHeight / 2) - (this.getUIScaleY() / 2), this.getUIScaleX(), this.getUIScaleY());
        } else {
            //fullscreen, set the position to the top corner of the screen
            mainScreen.setBounds(0, 0, this.getUIScaleX(), this.getUIScaleY());
        }

    }

    private void jFrDecoration(boolean Undecorated) { //adds or removes the top bar
        mainScreen.setUndecorated(Undecorated);

    }

    /** Button handling **/
    //Handles the button related work

    private class newButton extends JButton { //creates a new button

        private newButton(String text, int positionX, int positionY, int scaleX, int scaleY) {
            this.setText(text);
            this.setBounds(positionX, positionY, scaleX, scaleY);

        }

    }



    /** Base Methods **/
    //Methods used by the UI core and most UI elements.

    //paints components onto the main window
    @Override
    public void paintComponent(Graphics gfx) {
        super.paintComponent(gfx);

    }

    //Takes colour input as a string, verifies that it is a legitimate colour via java colours and outputs it.
    public Color newColour(String getColour) { //Verifies the colour values.
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

    //minimizes the program
    public void minimizeWindow() { mainScreen.setState(Frame.ICONIFIED); }

    //closes the program down
    public void closeProgram() { System.exit(0); }


    /** Cleaner Methods **/
    //methods that clean up the UI framework to make room for new elements

    public void jFrClear() { //clears the JFrame's content pane
        mainScreen.getContentPane().removeAll();
        mainScreen.getContentPane().add(this);

    }


    /** UI Designs **/
    //methods for loading and removing the different UI elements

    public void loadLauncherScreen() {

        jFrName("Astra Launcher");

        newButton btnExit = new newButton("X", (int)(screenWidth - 35), 5, 30, 30);
        this.add(btnExit);
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Killing program.");
                closeProgram();
            }
        });
        btnExit.setVisible(true);


    }

}
