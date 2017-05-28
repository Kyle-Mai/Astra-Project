package Core.GUI.SwingEX;

import javax.swing.*;
import java.util.Dictionary;

/**
 * KM
 * May 28 2017
 * Extends the JSlider with new constructors and variables.
 */

public class XSlider extends JSlider {

    public XSlider(int var1, int var2, int var3, int var4) {
        super(var1, var2, var3, var4);
        this.setOpaque(false);
        this.setFocusable(false);
    }

    public void setTicks(int major, int minor) { //sets both the ticks at once
        this.setMajorTickSpacing(major);
        this.setMinorTickSpacing(minor);
        this.setPaintTicks(true);
    }

    public void setLabelTable(Dictionary resource) {
        super.setLabelTable(resource);
        this.setPaintLabels(true);
    }


}
