package Core.GUI.SwingEX;

/**
 * KM
 * May 15 2017
 * Used as a core for some panel declarations, more freedom of manipulation.
 */


//import all relevant stuff
import javax.swing.*;
import java.awt.*;


public class XPanel extends JPanel {

    private int posX, posY;

    /** Main Constructor **/

    public XPanel(Color backgroundColor) {
        this.setLayout(null);
        this.setBackground(backgroundColor);
        this.setFocusable(false);
        this.setOpaque(true);
        this.setBorder(null);

    }
    // VUILCANDEV EDIT Layouts cannn be useful
    public XPanel(LayoutManager layoutStyle, Color backgroundColor){
        this.setLayout(layoutStyle);
        this.setBackground(backgroundColor);
        this.setFocusable(false);
        this.setOpaque(true);
        this.setBorder(null);
    }
    // VULCANDEV EDIT

    public XPanel() {
        this.setLayout(null);
        this.setFocusable(false);
        this.setOpaque(false);
        this.setBorder(null);

    }

    public int getPosX() { return this.posX; }
    public int getPosY() { return this.posY; }
    public void setPosValues(int x, int y) { this.posX = x; this.posY = y; }


    /** Base Methods **/
    //Methods used by the UI core and most UI elements.

    //paints components onto the main window
    @Override
    protected void paintComponent(Graphics gfx) {
        super.paintComponent(gfx);

    }

    public void refresh() {
        this.revalidate();
        this.repaint();
    }

}
