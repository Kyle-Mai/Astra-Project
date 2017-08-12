package AetheriusEngine.core.gui;

/*
Lolita's Revenge
June 29 2017

Extends the JLabel class, adding new methods and constructors to simplify declaration.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class XLabel extends JLabel implements XConstants, XElement {

    /*------------------------------------------------------------------------------------------------------------------
     Constructors.
     Used to construct instances of the XLabel.
     */

    public XLabel() { //Default constructor.
        this.setFocusable(false);
        this.setBackground(null);
        this.setFocusable(false);
        this.setOpaque(false);
        this.setBorder(null);
    }

    public XLabel(Icon animation) { //Constructor for an animated gif.
        this.setIcon(animation);
        this.setOpaque(false);
        this.setFocusable(false);
        this.setBorder(null);
    }

    public XLabel(String text, Font font, Color colour) { //Constructor for text.
        this.setText(text, font, colour);
        this.setOpaque(false);
    }

    public XLabel(Font font, Color textColour) { //Secondary constructor for text.
        this.setFont(font);
        this.setForeground(textColour);
        this.setOpaque(false);
    }

    public XLabel(BufferedImage image) { //Constructor for an image with a transparent background.
        try {
            this.setIcon(new ImageIcon(image));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        this.setBackground(null);
        this.setBorder(null);
        this.setFocusable(false);
        this.setAlignments(SwingConstants.CENTER);
        this.setOpaque(false);
    }

    public XLabel(BufferedImage image, Color colour) { //Constructor for an image with a set background.
        try {
            this.setIcon(new ImageIcon(image));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        this.setBackground(colour);
        this.setBorder(null);
        this.setFocusable(false);
        this.setOpaque(true);
    }

    /*------------------------------------------------------------------------------------------------------------------
     Accessible methods.
     Can be accessed to edit the XLabel's characteristics.
     */

    public void setText(String text, Font font, Color colour) { //Sets the text of the XLabel.
        this.setText(text);
        this.setFont(font);
        this.setForeground(colour);
        this.setOpaque(false);
    }

    public void setImage(BufferedImage image) { //Sets the image displayed by the XLabel.
        try {
            this.setIcon(new ImageIcon(image));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        this.setBorder(null);
        this.setOpaque(true);
    }

    public void scaleImage(BufferedImage image) { //Resizes the image to fit the XLabel.
        Image scaledImage = image.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
        this.setIcon(new ImageIcon(scaledImage));
    }

    public void scaleImage(BufferedImage image, int x, int y) { //Resizes the image to a given scale.
        Image scaledImage = image.getScaledInstance(x, y, Image.SCALE_SMOOTH);
        this.setIcon(new ImageIcon(scaledImage));
    }

    public void setAlignments(int alignmentH, int alignmentV) { //Sets the horizontal and vertical alignments of the XLabel.
        this.setHorizontalAlignment(alignmentH);
        this.setVerticalAlignment(alignmentV);
    }

    public void setAlignments(int alignment) { //Sets both the horizontal and vertical alignments to the same value.
        this.setHorizontalAlignment(alignment);
        this.setVerticalAlignment(alignment);
    }

    public void refresh() { //Refreshes the GFX.
        revalidate();
        repaint();
    }

    //------------------------------------------------------------------------------------------------------------------

}
