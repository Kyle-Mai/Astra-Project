package Core.GUI.Design;

import Core.Craft.craftCore;
import Core.GUI.SwingEX.XButton;
import Core.GUI.SwingEX.XLabel;
import Core.GUI.SwingEX.XMouseListener;
import Core.GUI.SwingEX.XPanel;
import Core.GUI.gfxRepository;
import Core.SFX.audioRepository;
import Core.gameSettings;
import Core.starClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * KM
 * June 17 2017
 * Handles the spacecraft functionality.
 */

public class SpaceCraft {

    private craftCore ship;
    private starClass currentSystem;
    private XPanel pnlShip;

    private int actionStart; //stores the turn the action was started on
    private int actionDuration; //stores the length of the action

    private boolean shipBuilt = false; //whether or not the ship is constructed

    public SpaceCraft(craftCore newship) {
        this.ship = newship;
        pnlShip = new XPanel();
        pnlShip.setPreferredSize(new Dimension(120, 130));
        pnlShip.setSize(pnlShip.getPreferredSize());
        pnlShip.setOpaque(false);
        pnlShip.setVisible(false);

        XLabel imgShip = new XLabel(ship.getShipGFX());
        pnlShip.add(imgShip);
        imgShip.setBounds(0, 0, 120, 120);
        imgShip.setVisible(true);

        XLabel lblName = new XLabel(ship.getCraftName(), gfxRepository.txtSubtitle, gfxRepository.clrText);
        pnlShip.add(lblName);
        lblName.setBounds(0, 120, 120, 10);
        lblName.setAlignments(SwingConstants.CENTER);
        lblName.setVisible(true);

        if (ship.isActive()) { //allow the ship to perform actions
            ship.toggleActive();
        }
    }

    public void startConstruction() {
        pnlShip.setVisible(false);
        actionStart = gameSettings.currentDate;
        actionDuration = ship.getBuildTime();
        if (!ship.isActive()) { //allow the ship to perform actions
            ship.toggleActive();
        }
    }

    public void finishConstruction() { //ship has finished construction, allow it to move

        if (!ship.isActive()) { //allow the ship to perform actions
            ship.toggleActive();
        }

        XButton btnShipHighlight = new XButton(gfxRepository.shipHighlight, SwingConstants.LEFT);
        pnlShip.add(btnShipHighlight);
        btnShipHighlight.setBounds(0, 0, 120, 120);
        btnShipHighlight.setVisible(true);
        btnShipHighlight.addMouseListener(new XMouseListener() {
            XButton source;
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                if (!ship.isSelected()) {
                    source.setHorizontalAlignment(SwingConstants.RIGHT);
                } else {
                    source.setHorizontalAlignment(SwingConstants.LEFT);
                }
                pnlShip.revalidate();
                pnlShip.repaint();
            }
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                if (!ship.isSelected()) {
                    source.setHorizontalAlignment(SwingConstants.RIGHT);
                } else {
                    source.setHorizontalAlignment(SwingConstants.LEFT);
                }
                pnlShip.revalidate();
                pnlShip.repaint();
            }
            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                if (ship.isSelected()) {
                    source.setHorizontalAlignment(SwingConstants.RIGHT);
                } else {
                    source.setHorizontalAlignment(SwingConstants.LEFT);
                }
                pnlShip.revalidate();
                pnlShip.repaint();
            }
            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.CENTER);
                pnlShip.revalidate();
                pnlShip.repaint();
            }
            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                if (ship.isSelected()) {
                    source.setHorizontalAlignment(SwingConstants.RIGHT);
                } else {
                    source.setHorizontalAlignment(SwingConstants.LEFT);
                }
                pnlShip.revalidate();
                pnlShip.repaint();
            }
        });

        shipBuilt = true;
        audioRepository.constructionComplete();
        pnlShip.setVisible(true);

        pnlShip.revalidate();
        pnlShip.repaint();

    }

    public void setSystemLocation(int x, int y) { ship.setPosition(x, y); } //sets the position of the ship in the system
    public Dimension getSystemLocation() { return new Dimension(ship.getSystemX(), ship.getSystemY()); } //gets the ships location in the system

    public void setMapLocation(int x, int y) { ship.setMapLocation(x, y); } //sets the ship's location on the map
    public int getMapLocationX() { return ship.getMapX(); }
    public int getMapLocationY() { return ship.getMapY(); }

    public XPanel getShipInterface() { return this.pnlShip; } //returns the ship UI
    public craftCore getShipData() { return this.ship; } //gets the ship

    public starClass getCurrentSystem() { return this.currentSystem; } //gets the system the ship is currently in
    public void setCurrentSystem(starClass system) { this.currentSystem = system; } //sets the ship's location

    public boolean isUnderConstruction() { return !this.shipBuilt; }

    ///handlers for the ship's actions
    public int getActionStart() { return this.actionStart; }
    public int getActionDuration() { return this.actionDuration; }
    public int getActionEnd() { return (this.actionStart + this.actionDuration); }


}
