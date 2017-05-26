package Core.GUI;

import javax.swing.*;

/**
 KM
 May 25 2017
 Extends the JScrollPane class and adds new constructors/methods.
 */

class XScrollPane extends JScrollPane {

    //default constructor
    XScrollPane() {
        this.setOpaque(false);
        this.setBorder(null);

    }

    //constructor with scrollbars
    XScrollPane(int vBar, int hBar) {
        this.setHorizontalScrollBarPolicy(hBar);
        this.setVerticalScrollBarPolicy(vBar);
        this.setOpaque(false);
        this.setBorder(null);

    }


}
