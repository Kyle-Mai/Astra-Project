package Core.GUI.SwingEX;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * KM
 * June 04 2017
 * Creates an image and adds text to the left of it.
 * Essentially the same as creating two XLabels (which is pretty much exactly what this is), but less lines per item by about half.
 */

public class XTextImage extends JPanel {

    XLabel image = new XLabel();
    XLabel text = new XLabel();

    public XTextImage() {
        this.setLayout(null);
        this.setFocusable(false);
        this.setOpaque(false);
        this.setBorder(null);
    }

    public void addImage(BufferedImage img, int x, int y) {
        image.setIcon(new ImageIcon(img));
        image.setAlignments(SwingConstants.CENTER);
        this.setPreferredSize(new Dimension(x, y));
        this.setSize(this.getPreferredSize());
        this.add(image);
        image.setBounds(0, 0, this.getWidth(), this.getHeight());
        image.setVisible(true);
    }

    public void addText(String text, Font font, Color textcolor, int width) {
        this.setPreferredSize(new Dimension((int)this.getPreferredSize().getWidth() + width, (int)this.getPreferredSize().getHeight()));
        this.setSize(this.getPreferredSize());
        this.add(this.text);
        this.text.setText(text, font, textcolor);
        this.text.setBounds(image.getWidth(), 0, width, this.getHeight());
        this.text.setVisible(true);
    }

    public XLabel getImage() { return this.image; }
    public XLabel getText() { return this.text; }

}
