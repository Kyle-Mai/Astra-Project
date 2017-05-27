package Core.GUI.SwingEX;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * KM
 * May 26 2017
 * Changes the style of the tooltip.
 *
 * SOURCES:
 * Stack Overflow - https://stackoverflow.com/questions/10155124/how-to-change-tooltip-colour
 */

public class XToolTip extends JToolTip {

    public XToolTip(String text, Font font, Border border, Color background) {
        this.setTipText(text);
        this.setFont(font);
        this.setBorder(border);
        this.setBackground(background);

    }


}
