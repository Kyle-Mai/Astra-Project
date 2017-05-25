package Core.GUI;

import javax.swing.*;
import java.awt.*;

/**
 KM
 May 25 2017
 Extends the JButton class and adds new constructors and methods.
*/

class XButton extends JButton {

    XButton() {
    }

    XButton(String text, Font font, Color textColor, Color bgColor) {
        this.setText(text);
        this.setFont(font);
        this.setBackground(bgColor);
        this.setForeground(textColor);
        this.setFocusPainted(false);

    }

}
