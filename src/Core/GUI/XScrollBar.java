package Core.GUI;

import javax.swing.*;

/**
 KM
 May 25 2017
 Extends the JScrollBar and adds new constructors, methods.
 */

class XScrollBar extends JScrollBar {

    //default constructor
    XScrollBar() {
        this.setBorder(null);
        this.setOpaque(true);
        this.setUI(new XScrollBarUI());

    }


}
