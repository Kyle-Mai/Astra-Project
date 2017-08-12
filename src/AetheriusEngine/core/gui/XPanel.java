package AetheriusEngine.core.gui;

/*
Lolita's Revenge
June 29 2017

Extends the JPanel with new methods and constructors for more freedom of manipulation.
 */

import javax.swing.*;
import java.awt.*;

public class XPanel extends JPanel implements XElement {

    /*------------------------------------------------------------------------------------------------------------------
     Variables.
     Used to determine the function of the XPanel, if applicable.
     */

    private int posX, posY;

    /*------------------------------------------------------------------------------------------------------------------
     Constructors.
     Used to construct instances of the XPanel.
     */

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

    /*------------------------------------------------------------------------------------------------------------------
     Accessible methods.
     Can be accessed to edit the XPanel's characteristics.
     */

    public int getPosX() { return this.posX; }
    public int getPosY() { return this.posY; }
    public void setPosValues(int x, int y) { this.posX = x; this.posY = y; }

    public void refresh() {
        revalidate();
        repaint();
    }

    /*------------------------------------------------------------------------------------------------------------------
     Internal methods.
     Methods used by the XPanel that generally shouldn't be accessed.
     */

    @Override
    protected void paintComponent(Graphics gfx) { super.paintComponent(gfx); }

    //------------------------------------------------------------------------------------------------------------------

}
