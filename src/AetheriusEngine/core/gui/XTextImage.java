package AetheriusEngine.core.gui;

/*
Lolita's Revenge
June 29 2017

Creates an image and adds text to the left of it.
Essentially the same as creating two XLabels (which is pretty much exactly what this is), but less lines per item by about half.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class XTextImage extends JPanel implements XElement {

    /*------------------------------------------------------------------------------------------------------------------
     Variables.
     Used to determine the appearance and function of the TextImage.
     */

    XLabel image = new XLabel(); //Label that stores the image used.
    XLabel text = new XLabel(); //Label that stores the text used.

    /*------------------------------------------------------------------------------------------------------------------
     Constructors.
     Used to construct instances of the TextImage.
     */

    public XTextImage() {
        this.setLayout(null);
        this.setFocusable(false);
        this.setOpaque(false);
        this.setBorder(null);
    }

    /*------------------------------------------------------------------------------------------------------------------
     Accessible methods.
     Can be accessed to edit the Text Image's characteristics.
     */

    public void addImage(BufferedImage img, int x, int y) { //Adds the image to be used in the text image.
        image.setIcon(new ImageIcon(img));
        image.setAlignments(SwingConstants.CENTER);
        this.setPreferredSize(new Dimension(x, y));
        this.setSize(this.getPreferredSize());
        this.add(image);
        image.setBounds(0, 0, this.getWidth(), this.getHeight());
        image.setVisible(true);
    }

    public void addText(String text, Font font, Color textcolor, int width) { //Adds the text to be displayed alongside the image.
        this.setPreferredSize(new Dimension((int)this.getPreferredSize().getWidth() + width, (int)this.getPreferredSize().getHeight()));
        this.setSize(this.getPreferredSize());
        this.add(this.text);
        this.text.setText(text, font, textcolor);
        this.text.setBounds(image.getWidth(), 0, width, this.getHeight());
        this.text.setVisible(true);
    }

    public XLabel getImage() { return this.image; } //Returns the image label.
    public XLabel getText() { return this.text; } //Returns the text label.

    public void refresh() { //Refreshes the GFX.
        this.revalidate();
        this.repaint();
    }

    //------------------------------------------------------------------------------------------------------------------

}
