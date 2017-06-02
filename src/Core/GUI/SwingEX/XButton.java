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

public class XButton extends JButton {

    private boolean state = false;
    private int identifier = 0;

    public XButton() {
        this.setFocusPainted(false);

    }

    public XButton(int identifier) {
        this.setFocusPainted(false);
        this.identifier = identifier;

    }

    public XButton(String text, Font font, Color textColor, Color bgColor) {
        this.setText(text);
        this.setFont(font);
        this.setBackground(bgColor);
        this.setForeground(textColor);
        this.setFocusPainted(false);

    }

    public XButton(String text, Font font, Color txtColor, Color bgColor, Border border) {
        this.setText(text);
        this.setFont(font);
        this.setBackground(bgColor);
        this.setForeground(txtColor);
        this.setBorder(border);
        this.setFocusPainted(false);

    }

    public XButton(BufferedImage image, int position) {
        this.setBorder(null);
        this.setFocusPainted(false);
        this.setBackground(EXColorDefaults.NONE);
        this.setOpaque(false);
        this.setIcon(new ImageIcon(image));
        this.setVerticalAlignment(SwingConstants.CENTER);
        this.setHorizontalAlignment(position);
        super.setContentAreaFilled(false);

    }

    public XButton(BufferedImage image, int position, String text, Font font, Color txtclr) {
        this.setBorder(null);
        this.setFocusPainted(false);
        this.setBackground(EXColorDefaults.NONE);
        this.setFont(font);
        this.setForeground(txtclr);
        this.setText(text);
        this.setOpaque(false);
        this.setIcon(new ImageIcon(image));
        this.setVerticalAlignment(SwingConstants.CENTER);
        this.setHorizontalAlignment(position);
        super.setContentAreaFilled(false);

    }

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
