package Core.GUI;

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


public class newPanel extends JPanel {


    /** Main Constructor **/

    public newPanel() {
        this.setBackground(newColour("black"));

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




}
