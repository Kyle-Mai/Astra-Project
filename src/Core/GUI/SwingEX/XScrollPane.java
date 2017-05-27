package Core.GUI.SwingEX;

import javax.swing.*;

/**
 KM
 May 25 2017
 Extends the JScrollPane class and adds new constructors/methods.
 */

public class XScrollPane extends JScrollPane {

    //default constructor
    public XScrollPane() {
        this.setOpaque(false);
        this.setBorder(null);

    }

    //constructor with scrollbars
    public XScrollPane(int vBar, int hBar) {
        this.setHorizontalScrollBarPolicy(hBar);
        this.setVerticalScrollBarPolicy(vBar);
        this.setOpaque(false);
        this.setBorder(null);

    }


}
