package Core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;

/**
    KM
    May 11 2017
    Handles the generation and display of UI elements.
 */

public class coreUI extends JPanel {

    private int[] currentUIScale = {400, 600}; //default screen scale

    public int getUIScaleX() {
        return this.currentUIScale[0];
    }

    public int getUIScaleY() {
        return this.currentUIScale[1];
    }

    //sets the window size to the chosen monitor scale
    public void rescaleScreen(int option) {
        switch(option) {
            case 1: //4K
                currentUIScale[0] = 3840;
                currentUIScale[1] = 2160;
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
            default:
                currentUIScale[0] = 400;
                currentUIScale[1] = 600;
                break;
        }

        this.setPreferredSize(new Dimension(this.currentUIScale[0], this.currentUIScale[1]));

        System.out.println("UI rescaled.");

    }

    public coreUI() {
    }

    public coreUI(String bgColour) {
        //adds mouse and key listeners to the frame
        mouseHandler mouseEvents = new mouseHandler();
        keyHandler keyboardEvents = new keyHandler();
        addMouseMotionListener(mouseEvents);
        addKeyListener(keyboardEvents);
        addMouseListener(mouseEvents);

        this.setPreferredSize(new Dimension(this.currentUIScale[0], this.currentUIScale[1]));
        this.setBackground(colourValue(bgColour));

        System.out.println("Successfully loaded UI core.");

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

    public Color colourValue(String getColour) { //Verifies the colour values.
        String colour = getColour;
        Color colour2;

        colour = colour.toUpperCase(); //Converts the string to all uppercase.
        try {
            Field field = Class.forName("java.awt.Color").getField(colour); //Checks to ensure the colour entered is valid.
            colour2 = (Color)field.get(null); //If it is valid, then we're set.
        } catch (Exception e) { //Otherwise, it isn't defined.
            colour2 = Color.black; // Not defined, default to black
            System.out.println("Invalid colour.");
        }
        return (colour2); //Returns the colour value.
    }

}
