package Core.GUI;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

/**
 * KM
 * May 19 2017
 * Creates a custom scroll bar design.
 * lol I literally have no f///ing clue what I'm doing here

 * SOURCES:
 * Stack Overflow - Used Stack Overflow to help me understand how to create a custom scrollbar to begin with.
 * Java API - Used Java API to help me understand the methods I needed to replace, along with Stack Overflow.
 */
class XScrollBarUI extends BasicScrollBarUI {

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(new Color(55, 95, 65, 255));
        g2d.fill(trackBounds);
        g2d.draw(trackBounds);

    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(new Color(85, 140, 105, 255));
        g2d.fill(thumbBounds);

    }

    @Override
    protected JButton createIncreaseButton(int i) { return createZeroButton(); }

    @Override
    protected JButton createDecreaseButton(int orientation) { return createZeroButton(); }

    //removes the buttons
    private JButton createZeroButton() {
        JButton button = new JButton("What button?");
        Dimension zeroDim = new Dimension(0,0);
        //button.setBackground(new Color(45, 55, 75, 255));
        //button.setForeground(new Color(255, 255, 255, 255));
        //button.setText("K");
        button.setPreferredSize(zeroDim);
        button.setMinimumSize(zeroDim);
        button.setMaximumSize(zeroDim);
        
        return button;

    }

}
