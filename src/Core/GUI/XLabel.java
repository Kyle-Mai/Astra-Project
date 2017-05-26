package Core.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * KM
 * May 21 2017
 * Extends JLabel with extra functionality and constructors.
 * Essentially simplifies Swing object declaration so I don't have to use as many lines.
 */
class XLabel extends JLabel {

    //default constructor
    XLabel() {
        this.setFocusable(false);

    }

    //animation
    XLabel(Icon animation) {
        this.setIcon(animation);
        this.setOpaque(false);
        this.setFocusable(false);
        this.setBorder(null);

    }

    //text
    XLabel(String text, Font font, Color colour) {
        this.setText(text, font, colour);
        this.setOpaque(false);

    }

    //text without text
    XLabel(Font font, Color textColour) {
        this.setFont(font);
        this.setForeground(textColour);
        this.setOpaque(false);

    }

    //images
    XLabel(BufferedImage image) {
        this.setIcon(new ImageIcon(image));
        this.setBackground(null); //transparent background
        this.setBorder(null);
        this.setFocusable(false);
        this.setAlignments(SwingConstants.CENTER); //centers the image, maybe?
        this.setOpaque(false);

    }

    //images with background
    XLabel(BufferedImage image, Color colour) {
        this.setIcon(new ImageIcon(image));
        this.setBackground(colour); //if the image doesn't work, just render a black background
        this.setBorder(null);
        this.setFocusable(false);
        this.setOpaque(true);

    }

    //in the event that the default constructor is used, these methods will set all of the data

    void setText(String text, Font font, Color colour) {
        this.setText(text);
        this.setFont(font);
        this.setForeground(colour);
        this.setOpaque(false);

    }

    void setImage(BufferedImage image) {
        this.setIcon(new ImageIcon(image));
        this.setBackground(gfxRepository.clrTrueBlack);
        this.setBorder(null);
        this.setOpaque(true);

    }

    //sets the horizontal and vertical alignment separately
    void setAlignments(int alignmentH, int alignmentV) {
        this.setHorizontalAlignment(alignmentH);
        this.setVerticalAlignment(alignmentV);

    }

    //sets both the h and v alignment to the same value
    void setAlignments(int alignment) {
        this.setHorizontalAlignment(alignment);
        this.setVerticalAlignment(alignment);

    }

}
