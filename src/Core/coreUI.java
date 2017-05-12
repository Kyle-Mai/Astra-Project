package Core;

//import all relevant stuff
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;

/**
    KM
    May 11 2017
    Handles the management and display of the UI elements.
 */

public class coreUI extends JPanel {

    private File resourcesFolder = new File(("user.dir") + "/src/Resources"); //stores the resources folder

    /** Stores resource declarations **/
    protected ImageIcon gameIcon = new ImageIcon(resourcesFolder + "/icon.png");

    protected JFrame GUI; //stores the main frame

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
                currentUIScale[0] = 400;
                currentUIScale[1] = 600;
                break;
            default:
                currentUIScale[0] = 500;
                currentUIScale[1] = 500;
                break;
        }

        this.setSize(new Dimension(this.currentUIScale[1], this.currentUIScale[0]));

        System.out.println("UI rescaled.");

    }

    /** Constructors for the UI core **/

    public coreUI() {
    }

    public coreUI(String bgColour) {
        //adds mouse and key listeners to the frame
        mouseHandler mouseEvents = new mouseHandler();
        keyHandler keyboardEvents = new keyHandler();
        addMouseMotionListener(mouseEvents);
        addKeyListener(keyboardEvents);
        addMouseListener(mouseEvents);

        this.setBackground(colourValue(bgColour));

        System.out.println("Successfully loaded UI core.");

    }

    /** Loads core UI elements **/

    //loads a JFrame
    public void loadFrame(String screenName, int UIscaleOption, boolean unDecorated) {
        this.rescaleScreen(UIscaleOption);

        this.GUI = new JFrame(screenName);

        if (gameIcon != null) {
            GUI.setIconImage(gameIcon.getImage()); //sets the icon
        } else {
            System.out.println("Error - Game icon is null, may indicate a corrupted resources folder.");
        }

        GUI.getContentPane().add(this); //adds the JFrame to the JPanel
        GUI.setResizable(false); //not resizable
        GUI.setSize(this.getUIScaleX(), this.getUIScaleY()); //scales it to the user scaling
        GUI.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); //when closed, dispose of everything
        GUI.setUndecorated(unDecorated); //whether or not we want the buttons at the top, generally no
        GUI.pack(); //packs the new frame
        GUI.setVisible(true); //sets it to visible

        System.out.println("UI frame loaded.");

    }

    //removes the JFrame and all of its elements from the active pane
    public void unloadFrame() {
        GUI.dispose();
    }

    public void refreshFrame(){ //refreshes the UI scaling
        this.setSize(this.getUIScaleY(), this.getUIScaleX());
        GUI.setSize(this.getUIScaleY(), this.getUIScaleX());
    }

    //TODO: Separate into another class.

    @Override
    public void paintComponent(Graphics gc) {
        super.paintComponent(gc);
    }

    /** Mouse and Keyboard Handlers **/
    //Base handlers for both mouse and keyboard events.

    private class mouseHandler implements MouseMotionListener, MouseListener {

        @Override
        public void mouseMoved(MouseEvent event) { //Move the car with the mouse.
            //carObject.moveMouse(event.getX(), event.getY());
            //repaint();
        }

        @Override
        public void mouseDragged(MouseEvent event) { //Moves the car in tandem with the mouse.
            //carObject.moveMouse(event.getX(), event.getY());
            //repaint();  //will manually re-call the paintComponent method
        }


        @Override
        public void mousePressed(MouseEvent event) { //Activates the headlights when the mouse button is pressed.
            if (event.getButton() == 1) { //Left mouse button.
            }
            //repaint();
        }

        @Override
        public void mouseReleased(MouseEvent event) {
            //carObject.setHeadlights(false);
            //repaint();  //will manually re-call the paintComponent method
        }

        @Override
        public void mouseClicked(MouseEvent event) {
            //carObject.setHeadlights(true);
            //repaint();  //will manually re-call the paintComponent method
        }

        @Override
        public void mouseExited(MouseEvent event) {
        }  //Invoked when the mouse exits a component.

        @Override
        public void mouseEntered(MouseEvent event) {
        } //Invoked when the mouse enters a component.

    }

    private class keyHandler implements KeyListener {

        @Override
        public void keyPressed(KeyEvent e) {
            repaint();
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

        @Override
        public void keyTyped(KeyEvent e) {

        }
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

}
