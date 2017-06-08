package Core.GUI.SwingEX;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * KM
 * June 04 2017
 * Advanced XButton designed to handle custom design layouts.
 */

public class XButtonCustom extends JButton {

    private XLabel buttonText = new XLabel();

    public XButtonCustom(BufferedImage image, int position) {
        this.setBorder(null);
        this.setFocusPainted(false);
        this.setBackground(EXColorDefaults.NONE);
        this.setFocusable(false);
        this.setOpaque(false);
        this.setIcon(new ImageIcon(image));
        this.setVerticalAlignment(SwingConstants.CENTER);
        this.setHorizontalAlignment(position);
        super.setContentAreaFilled(false);

    }

    public void setText(String text, Font font, Color color) { //sets the text of the button
        buttonText.setText(text, font, color);
        buttonText.setAlignments(SwingConstants.CENTER);
        buttonText.setVisible(true);
    }

    public void setImage(BufferedImage image) {
        buttonText.setIcon(new ImageIcon(image));
        buttonText.setVisible(true);
    }

    public void placeOn(JComponent parent) { // swing sucks
        parent.add(this);
        parent.add(buttonText);
        buttonText.setBounds(this.getBounds());
        buttonText.setAlignments(SwingConstants.CENTER);
    }

    public void setVisible(boolean visible) { // but does it work??
        super.setVisible(visible);
        this.getParent().add(buttonText);
        buttonText.setBounds(this.getBounds());
        buttonText.setAlignments(SwingConstants.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(EXColorDefaults.NONE); //GOODBYE STUPID WHITE BOX
        super.paintComponent(g);
    }

    @Override
    public void setContentAreaFilled(boolean b) {
    }

    public XLabel getContent() { //returns the XLabel
        return this.buttonText;
    }


}
