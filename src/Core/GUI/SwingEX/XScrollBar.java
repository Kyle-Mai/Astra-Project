package Core.GUI.SwingEX;

import javax.swing.*;

/**
 KM
 May 25 2017
 Extends the JScrollBar and adds new constructors, methods.
 */

public class XScrollBar extends JScrollBar {

    //default constructor
    public XScrollBar() {
        this.setBorder(null);
        this.setOpaque(true);
        this.setUI(new XScrollBarUI());

    }


}
