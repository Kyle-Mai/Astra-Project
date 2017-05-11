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

    private int defaultScreenScaleX, defaultScreenScaleY; //Screen size

    public coreUI() {
    }

    public coreUI(String bgColour, int scaleX, int scaleY) {
        //adds mouse and key listeners to the frame
        mouseHandler mouseEvents = new mouseHandler();
        keyHandler keyboardEvents = new keyHandler();
        addMouseMotionListener(mouseEvents);
        addKeyListener(keyboardEvents);
        addMouseListener(mouseEvents);

        this.defaultScreenScaleX = scaleX;
        this.defaultScreenScaleY = scaleY;
        this.setBackground(colourValue(bgColour));
        this.setPreferredSize(new Dimension(this.defaultScreenScaleX, this.defaultScreenScaleY));

        System.out.println("Successfully loaded UI core.");

    }

    //TODO: Separate into another class.

    public void loadUI(String screenName, boolean isResizable) {
        JFrame GUIbuilder = new JFrame(screenName);

        GUIbuilder.getContentPane().add(this);
        GUIbuilder.setResizable(isResizable);
        GUIbuilder.setDefaultCloseOperation(3);
        GUIbuilder.pack();
        GUIbuilder.setVisible(true);

    }

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
            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent event) { //Moves the car in tandem with the mouse.
            //carObject.moveMouse(event.getX(), event.getY());
            repaint();  //will manually re-call the paintComponent method
        }


        @Override
        public void mousePressed(MouseEvent event) { //Activates the headlights when the mouse button is pressed.
            if (event.getButton() == 1) { //Left mouse button.
            }
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent event) {
            //carObject.setHeadlights(false);
            repaint();  //will manually re-call the paintComponent method
        }

        @Override
        public void mouseClicked(MouseEvent event) {
            //carObject.setHeadlights(true);
            repaint();  //will manually re-call the paintComponent method
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
