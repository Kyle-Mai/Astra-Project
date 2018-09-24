package Core.GUI.Design;

import AetheriusEngine.core.gui.*;
import javax.swing.*;

/**
 * KM
 * August 20 2017
 * Tech tree view menu.
 */

public class TechTreeView extends JLayeredPane implements XElement, XConstants {

    private XPanel pnlTechTree;
    private XPanel pnlTechSelect;

    public TechTreeView() {
        pnlTechTree = new XPanel();
        pnlTechSelect = new XPanel();
        pnlTechSelect.setVisible(false);
        pnlTechTree.setVisible(false);
    }

    public void refresh() {
        repaint();
        revalidate();
    }


}
