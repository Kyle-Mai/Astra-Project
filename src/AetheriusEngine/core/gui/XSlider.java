package AetheriusEngine.core.gui;

/*
Lolita's Revenge
June 29 2017

Extends the JSlider with new constructors and variables.
 */

import javax.swing.*;
import java.util.Dictionary;

public class XSlider extends JSlider implements XConstants, XElement {

    /*------------------------------------------------------------------------------------------------------------------
     Constructors.
     Used to construct instances of the XSlider.
     */

    public XSlider(int var1, int var2, int var3, int var4) {
        super(var1, var2, var3, var4);
        this.setOpaque(false);
        this.setFocusable(false);
    }

    /*------------------------------------------------------------------------------------------------------------------
     Accessible methods.
     Can be accessed to edit the XSlider's characteristics.
     */

    public void setTicks(int major, int minor) { //Sets the tick spacing characteristics.
        this.setMajorTickSpacing(major);
        this.setMinorTickSpacing(minor);
        this.setPaintTicks(true);
    }

    public void setLabelTable(Dictionary resource) { //Sets the labels used on the Slider.
        super.setLabelTable(resource);
        this.setPaintLabels(true);
    }

    public void refresh() { //Refreshes the appearance of the XSlider.
        this.revalidate();
        this.repaint();
    }

    //------------------------------------------------------------------------------------------------------------------

}
