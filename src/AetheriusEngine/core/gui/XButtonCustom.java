package AetheriusEngine.core.gui;

/*
Lolita's Revenge
June 29 2017

Advanced XButton designed to handle custom button designs and layouts.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class XButtonCustom extends JButton implements XConstants, XElement {

    /*------------------------------------------------------------------------------------------------------------------
     Variables.
     Defines the function of the XButton.
     */

    private XLabel buttonText = new XLabel(); //label that stores the text that will appear on the button.

    /*------------------------------------------------------------------------------------------------------------------
     Constructors.
     Used to construct instances of the XButtonCustom.
     */

    public XButtonCustom(BufferedImage image, int position) {
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

    /*------------------------------------------------------------------------------------------------------------------
     Accessible methods.
     Can be accessed to edit the XButton's characteristics.
     */

    public void setText(String text, Font font, Color color) { //Sets the text of the button.
        buttonText.setText(text, font, color);
        buttonText.setAlignments(SwingConstants.CENTER);
        buttonText.setVisible(true);
    }

    public void setImage(BufferedImage image) { //Sets the image for the button.
        buttonText.setIcon(new ImageIcon(image));
        buttonText.setVisible(true);
    }

    public void setVisible(boolean visible) { //When the button's visibility is changed, it will place the text over top of itself.
        super.setVisible(visible);
        this.getParent().add(buttonText);
        buttonText.setBounds(this.getBounds());
        buttonText.setAlignments(SwingConstants.CENTER);
    }

    public XLabel getContent() { return this.buttonText; } //Returns the button's text label

    public void refresh() { //Refreshes the button's appearance.
        revalidate();
        repaint();
    }

    /*------------------------------------------------------------------------------------------------------------------
     Internal methods.
     Methods used by the XButton that generally shouldn't be accessed.
     */

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(NONE); //GOODBYE STUPID WHITE BOX
        super.paintComponent(g);
    }

    @Override
    public void setContentAreaFilled(boolean b) {}

    //------------------------------------------------------------------------------------------------------------------

}
