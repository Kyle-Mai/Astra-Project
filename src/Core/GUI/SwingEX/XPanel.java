package Core.GUI.SwingEX;

/**
 * KM
 * May 15 2017
 * Used as a core for some panel declarations, more freedom of manipulation.
 */


//import all relevant stuff
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class XPanel extends JPanel {


    /** Main Constructor **/

    public XPanel(Color backgroundColor) {
        this.setLayout(null);
        this.setBackground(backgroundColor);
        this.setFocusable(false);
        this.setOpaque(true);
        this.setBorder(null);

    }

    public XPanel() {
        this.setLayout(null);
        this.setFocusable(false);
        this.setOpaque(false);
        this.setBorder(null);

    }


    /** Base Methods **/
    //Methods used by the UI core and most UI elements.

    //paints components onto the main window
    @Override
    protected void paintComponent(Graphics gfx) {
        super.paintComponent(gfx);

    }





}
