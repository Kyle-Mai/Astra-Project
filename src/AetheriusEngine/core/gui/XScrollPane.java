package AetheriusEngine.core.gui;

/*
Lolita's Revenge
June 29 2017

Extends the JScrollPane class and adds new constructors/methods.
 */

import javax.swing.*;

public class XScrollPane extends JScrollPane implements XElement {

    /*------------------------------------------------------------------------------------------------------------------
     Constructors.
     Used to construct instances of the ScrollPane.
     */

    public XScrollPane() {
        this.setOpaque(false);
        this.setBorder(null);
    }

    public XScrollPane(int vBar, int hBar) {
        this.setHorizontalScrollBarPolicy(hBar);
        this.setVerticalScrollBarPolicy(vBar);
        this.setOpaque(false);
        this.setBorder(null);
    }

    /*------------------------------------------------------------------------------------------------------------------
     Accessible methods.
     Can be accessed to edit the scroll pane's characteristics.
     */

    public void refresh() { //Refreshes the XFrame's GFX.
        revalidate();
        repaint();
    }

    //------------------------------------------------------------------------------------------------------------------

}
