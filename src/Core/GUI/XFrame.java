package Core.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 KM
 May 25 2017
 Extends the JFrame class with new constructors, methods.
*/

class XFrame extends JFrame {

    XFrame(String text, BufferedImage icon) {
        this.setUndecorated(true);
        this.setResizable(false);
        this.setTitle(text);
        this.setIconImage(icon);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    //refreshes the GFX
    void refresh() {
        this.revalidate();
        this.repaint();
    }

    void minimize() { //minimizes the frame
        this.setState(Frame.ICONIFIED);
    }


}
