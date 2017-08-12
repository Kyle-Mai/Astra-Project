package AetheriusEngine.core.gui;

import javax.swing.*;
import java.awt.image.BufferedImage;

/**
 * Lolita's Revenge
 * July 28 2017
 * A sort of 'super' Swing element that is designed to handle layering and access, similar in function to a LayeredPane.
 */

public class XEnvironment extends JLayeredPane implements XConstants, XElement {

    private XLabel backdrop = new XLabel();

    public XEnvironment() {}

    public void setBackdrop(BufferedImage i, boolean scale) { //add a backdrop from a bufferedimage with an option to scale to screen size or not
        backdrop = new XLabel(new ImageIcon(i));
        add(backdrop, 0, 0);
        backdrop.setBounds(0, 0, getWidth(), getHeight());
        if (scale) {
            backdrop.scaleImage(i);
        }
        //backdrop added
    }

    public void setBackdrop(XLabel c) { //add backdrop using a pre-configured xlabel
        backdrop = c;
        add(c, 0, 0);
        //backdrop added
    }

    public XLabel getBackdrop() { return backdrop; }

    public void clearBackdrop() { remove(backdrop); }

    public void refresh() {
        repaint();
        revalidate();
    }

}
