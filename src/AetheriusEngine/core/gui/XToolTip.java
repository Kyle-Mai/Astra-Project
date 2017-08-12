package AetheriusEngine.core.gui;

/*
Lolita's Revenge
June 29 2017

Extended tooltip with better control.
 */

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class XToolTip extends JToolTip implements XElement {

    /*------------------------------------------------------------------------------------------------------------------
     Constructors.
     Used to construct instances of the ToolTip.
     */

    public XToolTip(String text, Font font, Border border, Color background) {
        this.setTipText(text);
        this.setFont(font);
        this.setBorder(border);
        this.setBackground(background);
    }

    /*------------------------------------------------------------------------------------------------------------------
     Accessible methods.
     Can be accessed to edit the Text Image's characteristics.
     */

    public void refresh() {
        repaint();
        revalidate();
    }

    //------------------------------------------------------------------------------------------------------------------

}
