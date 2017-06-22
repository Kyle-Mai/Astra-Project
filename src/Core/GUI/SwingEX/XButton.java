package Core.GUI.SwingEX;

import Core.GUI.gfxRepository;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 KM
 May 25 2017
 Extends the JButton class and adds new constructors and methods.

 SOURCES:
 Stack Overflow - Help with removing white background on button press. https://stackoverflow.com/questions/14627223/how-to-change-a-jbutton-color-on-mouse-pressed
 Self - All else.
*/

public class XButton extends JButton implements EXColorDefaults {

    private boolean state = false;
    private int identifier = 0;

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

    public void scaleImage(BufferedImage image) {
        Image scaledImage = image.getScaledInstance(this.getWidth() * 3, this.getHeight(), Image.SCALE_SMOOTH);
        this.setIcon(new ImageIcon(scaledImage));
    }

    public void setIdentifier(int id) { this.identifier = id; }
    public int getIdentifier() { return this.identifier; }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(EXColorDefaults.NONE); //GOODBYE STUPID WHITE BOX
        super.paintComponent(g);
    }

    @Override
    public void setContentAreaFilled(boolean b) {
    }

    public void toggleState() {
        state = !state;
    }

    public boolean isState() {
        return this.state;
    }


}
