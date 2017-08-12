package AetheriusEngine.core.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;

/*
Lolita's Revenge
June 29 2017

Extends the JButton class with new constructors and methods in order to simplify declarations.
 */

public class XButton extends JButton implements XConstants, XElement {

    /*------------------------------------------------------------------------------------------------------------------
     Variables.
     Defines the function of the XButton.
     */

    private boolean state = false; //determines the current 'state' of the button, is generally unused unless given an implementation
    private int identifier = 0; //same as the state boolean

    /*------------------------------------------------------------------------------------------------------------------
     Constructors.
     Used to construct instances of the XButton.
     */

    public XButton() {
        this.setFocusPainted(false);
        this.setFocusable(false);
    }

    public XButton(int identifier) {
        this.setFocusPainted(false);
        this.identifier = identifier;
        this.setFocusable(false);
    }

    public XButton(String text, Font font, Color textColor, Color bgColor) {
        this.setText(text);
        this.setFont(font);
        this.setBackground(bgColor);
        this.setForeground(textColor);
        this.setFocusable(false);
        this.setFocusPainted(false);
    }

    public XButton(String text, Font font, Color txtColor, Color bgColor, Border border) {
        this.setText(text);
        this.setFont(font);
        this.setBackground(bgColor);
        this.setForeground(txtColor);
        this.setFocusable(false);
        this.setBorder(border);
        this.setFocusPainted(false);
    }

    public XButton(BufferedImage image, int position) {
        this.setBorder(null);
        this.setFocusPainted(false);
        this.setBackground(NONE);
        this.setFocusable(false);
        this.setOpaque(false);
        this.setIcon(new ImageIcon(image));
        this.setVerticalAlignment(SwingConstants.CENTER);
        this.setHorizontalAlignment(position);
        super.setContentAreaFilled(false);
    }

    public XButton(BufferedImage image, int position, String text, Font font, Color txtclr) {
        this.setBorder(null);
        this.setFocusPainted(false);
        this.setBackground(NONE);
        this.setFont(font);
        this.setForeground(txtclr);
        this.setFocusable(false);
        this.setText(text);
        this.setOpaque(false);
        this.setIcon(new ImageIcon(image));
        this.setVerticalAlignment(SwingConstants.CENTER);
        this.setHorizontalAlignment(position);
        super.setContentAreaFilled(false);
    }

    /*------------------------------------------------------------------------------------------------------------------
     Accessible methods.
     Can be accessed to edit the XButton's characteristics.
     */

    public void setIcon(BufferedImage image, int alignment) {
        this.setVerticalAlignment(SwingConstants.CENTER);
        this.setHorizontalAlignment(alignment);
        this.setIcon(new ImageIcon(image));
        this.setFocusable(false);
        this.setOpaque(false);
        this.setBorder(null);
        this.setFocusPainted(false);
        super.setContentAreaFilled(false);
    }

    public void setImage(BufferedImage image) {
        try {
            this.setIcon(new ImageIcon(image));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        this.setBorder(null);
        this.setOpaque(true);
    }

    public void scaleImage(BufferedImage image, int scale) { //Scale the image up to either the size of the button or triple that.
        if (scale == SCALE_TRIPLE) {
            Image scaledImage = image.getScaledInstance(this.getWidth() * 3, this.getHeight(), Image.SCALE_SMOOTH);
            this.setIcon(new ImageIcon(scaledImage));
        } else if (scale == SCALE_FULL) {
            Image scaledImage = image.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
            this.setIcon(new ImageIcon(scaledImage));
        } else {
            System.out.println("XButton could not resize the image - Unknown scaling operation. Aborted process.");
        }
    }

    public void scaleImage(BufferedImage image, int x, int y) { //Scale the image up to a specified size.
        Image scaledImage = image.getScaledInstance(x, y, Image.SCALE_SMOOTH);
        this.setIcon(new ImageIcon(scaledImage));
    }

    public void setIdentifier(int id) { this.identifier = id; }
    public int getIdentifier() { return this.identifier; }

    public void toggleState() { state = !state; }
    public boolean getCurrentState() { return this.state; }

    public void refresh() { //Refreshes the GFX.
        revalidate();
        repaint();
    }

    /*------------------------------------------------------------------------------------------------------------------
     Internal methods.
     Methods used by the XButton that generally shouldn't be accessed.
     */

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(NONE);
        super.paintComponent(g);
    }

    @Override
    public void setContentAreaFilled(boolean b) {}

    //------------------------------------------------------------------------------------------------------------------

}
