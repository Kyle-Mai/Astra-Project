package AetheriusEngine.core.gui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

/*
Lolita's Revenge
June 29 2017

Creates a custom scroll bar design.
 */

public class XScrollBarUI extends BasicScrollBarUI implements XConstants {

    /*------------------------------------------------------------------------------------------------------------------
     Variables.
     Used to determine the appearance and function of the scrollbar.
     */

    Color foreground = WHITE;
    Color background = GREY;
    boolean showButtons = false;
    JButton topButton;
    JButton bottomButton;

    /*------------------------------------------------------------------------------------------------------------------
     Constructors.
     Used to construct instances of the scrollbar.
     */

    public XScrollBarUI(Color fore, Color back) { //sets up the UI colors
        this.foreground = fore;
        this.background = back;
    }

    public XScrollBarUI(Color fore, Color back, JButton inc, JButton dcr) {
        this.foreground = fore;
        this.background = back;
        this.topButton = inc;
        this.bottomButton = dcr;
    }

    public XScrollBarUI(Color fore, Color back, JButton inc, JButton dcr, boolean buttonvisible) {
        this.foreground = fore;
        this.background = back;
        this.topButton = inc;
        this.bottomButton = dcr;
        this.showButtons = buttonvisible;
    }

    /*------------------------------------------------------------------------------------------------------------------
     Accessible methods.
     Can be accessed to edit the scrollbar's characteristics.
     */

    public void setForegroundColor(Color c) { this.foreground = c; }
    public void setBackgroundColor(Color c) { this.background = c; }
    public Color getForeground() { return this.foreground; }
    public Color getBackground() { return this.background; }

    public void setTopButton(JButton button) { this.topButton = button; } //Creates the increase button's appearance
    public void setBottomButton(JButton button) { this.bottomButton = button; } //Creates the decrease button's appearance
    public void showButtons(boolean b) { this.showButtons = b; } //Shows the buttons on the scrollbar.
    public boolean isShowButtons() { return this.showButtons; } //Whether or not the buttons are visible.
    public JButton getTopButton() { return this.topButton; }
    public JButton getBottomButton() { return this.bottomButton; }

    /*------------------------------------------------------------------------------------------------------------------
     Internal methods.
     Methods used by the scrollbar that generally shouldn't be changed or accessed.
     */

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(background);
        g2d.fill(trackBounds);
        g2d.draw(trackBounds);
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(foreground);
        g2d.fill(thumbBounds);
    }

    @Override
    protected JButton createIncreaseButton(int i) {
        if (showButtons && topButton != null) {
            return topButton;
        } else {
            return createZeroButton();
        }
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        if (showButtons && bottomButton != null) {
            return bottomButton;
        } else {
            return createZeroButton();
        }
    }

    private JButton createZeroButton() { //Used to generate no buttons on the scrollbar.
        JButton button = new JButton("What button?");
        Dimension zeroDim = new Dimension(0,0);
        button.setPreferredSize(zeroDim);
        button.setMinimumSize(zeroDim);
        button.setMaximumSize(zeroDim);
        return button;
    }

    //------------------------------------------------------------------------------------------------------------------

}
