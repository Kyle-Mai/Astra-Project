package AetheriusEngine.core.gui;

import javax.swing.*;
import java.awt.*;

/*
 Lolita's Revenge
 June 29 2017

 Extends the JScrollBar and allows for more freedom of manipulation.
 */

public class XScrollBar extends JScrollBar implements XElement {

    /*------------------------------------------------------------------------------------------------------------------
     Constructors.
     Used to construct instances of the XScrollBar.
     */

    public XScrollBar() {}

    public XScrollBar(XScrollBarUI ui) { setUI(ui); }

    public XScrollBar(Color fore, Color back) {
        setBorder(null);
        setOpaque(true);
        setUI(new XScrollBarUI(fore, back));
    }

    /*------------------------------------------------------------------------------------------------------------------
     Accessible methods.
     Can be accessed to edit the XPanel's characteristics.
     */

    public void refresh() {
        revalidate();
        repaint();
    }

    //------------------------------------------------------------------------------------------------------------------

}
