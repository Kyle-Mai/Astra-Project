package Core.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * KM
 * May 21 2017
 * Extends JLabel with extra functionality and constructor.
 */
public class extendedLabel extends JLabel {

    public extendedLabel(String name, Font font, Color textColour) {
        this.setText(name);
        this.setFont(font);
        this.setForeground(textColour);

    }




}
