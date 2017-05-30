package Core.GUI.SwingEX;

import javax.swing.*;
import java.awt.*;

/**
 KM
 May 25 2017
 Extends the JScrollBar and adds new constructors, methods.
 */

public class XScrollBar extends JScrollBar {

    //default constructor
    public XScrollBar(Color fore, Color back) {
        this.setBorder(null);
        this.setOpaque(true);
        this.setUI(new XScrollBarUI(fore, back));

    }


}
