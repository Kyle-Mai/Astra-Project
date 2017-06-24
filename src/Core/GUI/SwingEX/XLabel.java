package Core.GUI.SwingEX;

import Core.GUI.gfxRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * KM
 * May 21 2017
 * Extends JLabel with extra functionality and constructors.
 * Essentially simplifies Swing object declaration so I don't have to use as many lines.
 */
public class XLabel extends JLabel implements EXColorDefaults {

    //default constructor
    public XLabel() {
        this.setFocusable(false);
        this.setBackground(null); //transparent background
        this.setFocusable(false);
        this.setOpaque(false);
        this.setBorder(null);
    }

    //animation
    public XLabel(Icon animation) {
        this.setIcon(animation);
        this.setOpaque(false);
        this.setFocusable(false);
        this.setBorder(null);

    }

    //text
    public XLabel(String text, Font font, Color colour) {
        this.setText(text, font, colour);
        this.setOpaque(false);

    }

    //text without text
    public XLabel(Font font, Color textColour) {
        this.setFont(font);
        this.setForeground(textColour);
        this.setOpaque(false);

    }

    //images
    public XLabel(BufferedImage image) {
        this.setIcon(new ImageIcon(image));
        this.setBackground(null); //transparent background
        this.setBorder(null);
        this.setFocusable(false);
        this.setAlignments(SwingConstants.CENTER); //centers the image, maybe?
        this.setOpaque(false);

    }

    //images with background
    public XLabel(BufferedImage image, Color colour) {
        try {
            this.setIcon(new ImageIcon(image));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        this.setBackground(colour); //if the image doesn't work, just render a black background
        this.setBorder(null);
        this.setFocusable(false);
        this.setOpaque(true);

    }

    //in the event that the default constructor is used, these methods will set all of the data

    public void setText(String text, Font font, Color colour) {
        this.setText(text);
        this.setFont(font);
        this.setForeground(colour);
        this.setOpaque(false);

    }

    public void setImage(BufferedImage image) {
        this.setIcon(new ImageIcon(image));
        this.setBackground(BLACK);
        this.setBorder(null);
        this.setOpaque(true);

    }

    //resizes the image to fit the label
    public void scaleImage(BufferedImage image) {
        Image scaledImage = image.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
        this.setIcon(new ImageIcon(scaledImage));
    }

    //sets the horizontal and vertical alignment separately
    public void setAlignments(int alignmentH, int alignmentV) {
        this.setHorizontalAlignment(alignmentH);
        this.setVerticalAlignment(alignmentV);

    }

    //sets both the h and v alignment to the same value
    public void setAlignments(int alignment) {
        this.setHorizontalAlignment(alignment);
        this.setVerticalAlignment(alignment);

    }

    public void refresh() {
        this.revalidate();
        this.repaint();
    }

}
