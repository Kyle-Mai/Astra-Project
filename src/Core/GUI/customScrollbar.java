package Core.GUI;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

/**
 * Creates a custom scroll bar design.
 * lol I literally have no f///ing clue what I'm doing here
 */
public class customScrollbar extends BasicScrollBarUI {

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(new Color(65, 125, 185, 255));
        g2d.fill(trackBounds);
        g2d.draw(trackBounds);

    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(new Color(45, 55, 75, 255));
        g2d.fill(thumbBounds);

    }

    @Override
    protected JButton createIncreaseButton(int i) { return createZeroButton(); }

    @Override
    protected JButton createDecreaseButton(int orientation) { return createZeroButton(); }

    //removes the buttons
    protected JButton createZeroButton() {
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
