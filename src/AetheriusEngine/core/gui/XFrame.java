package AetheriusEngine.core.gui;

/*
Lolita's Revenge
June 29 2017

Extends the JFrame with new constructors and methods.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class XFrame extends JFrame implements XElement {

    /*------------------------------------------------------------------------------------------------------------------
     Constructors.
     Used to construct instances of the XFrame.
     */

    public XFrame() {
        this.setUndecorated(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    public XFrame(String text) {
        this.setUndecorated(true);
        this.setResizable(false);
        this.setTitle(text); //sets the name of the program
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    public XFrame(String text, BufferedImage icon) {
        this.setUndecorated(true);
        this.setResizable(false);
        this.setTitle(text); //sets the name of the program
        this.setIconImage(icon); //sets the program's icon image
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    public XFrame(String text, BufferedImage icon, boolean undecorated) {
        this.setUndecorated(undecorated);
        this.setResizable(false);
        this.setTitle(text); //sets the name of the program
        this.setIconImage(icon); //sets the program's icon image
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    public XFrame(String text, BufferedImage icon, boolean undecorated, boolean resizable) {
        this.setUndecorated(undecorated);
        this.setResizable(resizable);
        this.setTitle(text); //sets the name of the program
        this.setIconImage(icon); //sets the program's icon image
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    public XFrame(String text, boolean undecorated, boolean resizable) {
        this.setUndecorated(undecorated);
        this.setResizable(resizable);
        this.setTitle(text); //sets the name of the program
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    /*------------------------------------------------------------------------------------------------------------------
     Accessible methods.
     Can be accessed to edit the XFrame's characteristics.
     */

    public void minimize() { this.setState(Frame.ICONIFIED); } //Minimizes the XFrame to the tray.

    public void close() { //Closes the current XFrame and the program.
        this.dispose();
        System.exit(0);
    }

    public void refresh() { //Refreshes the XFrame's GFX.
        revalidate();
        repaint();
    }

    public Dimension getScreenSize() { return Toolkit.getDefaultToolkit().getScreenSize(); }

    public void centerOnScreen() {
        if (getWidth() == 0 || getHeight() == 0) throw new NullPointerException("Frame was not correctly sized before the operation was requested.");
        setLocation((getScreenSize().width / 2) - (getWidth() / 2), (getScreenSize().height / 2) - (getHeight() / 2));
    }

    //------------------------------------------------------------------------------------------------------------------

}
