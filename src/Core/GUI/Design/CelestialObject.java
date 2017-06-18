package Core.GUI.Design;

import Core.GUI.SwingEX.*;
import Core.GUI.gfxRepository;
import Core.SFX.audioRepository;
import Core.gameSettings;
import Core.planetClass;
import Core.starClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

/**
 * KM
 * June 17 2017
 * Handles the core design of the UI window for celestial bodies, such as stars and planets.
 */

public class CelestialObject extends XPanel {

    private XLabel imgPortrait = new XLabel();
    private XLabel lblStats = new XLabel();
    private XLabel lblName = new XLabel();
    private XLabel lblDesc = new XLabel();
    private XLabel main = new XLabel();
    private XButton btnClose = new XButton();
    private DecimalFormat uiFormat = new DecimalFormat("###,##0.00");

    public CelestialObject() { //constructor that sets up core UI elements

        this.setPreferredSize(new Dimension(700, 800));
        this.setSize(this.getPreferredSize());
        this.setOpaque(false);
        this.setLayout(null);
        this.setVisible(false);

        this.add(imgPortrait);
        imgPortrait.setBounds((this.getWidth() / 2) - 280, 0, 560, 185);
        imgPortrait.setBackground(gfxRepository.clrTrueBlack);
        imgPortrait.setOpaque(true);
        imgPortrait.setVisible(true);

        XLabel imgPortraitBorder = new XLabel(gfxRepository.portraitBorder);
        imgPortrait.add(imgPortraitBorder);
        imgPortraitBorder.setBounds(0, 0, imgPortrait.getWidth(), imgPortrait.getHeight());
        imgPortraitBorder.setVisible(true);

        this.add(main);
        main.setOpaque(true);
        main.setBounds(0, imgPortrait.getHeight(), this.getWidth(), this.getHeight() - imgPortrait.getHeight());
        main.scaleImage(gfxRepository.menuBackground);
        main.setBackground(gfxRepository.clrBGOpaque);
        main.setVisible(true);

        main.add(lblStats);
        lblStats.setBounds(main.getWidth() - 174, 50, 164, 470);
        lblStats.scaleImage(gfxRepository.tallBox);
        lblStats.setVisible(true);

        //displays the close button
        btnClose = new XButton(gfxRepository.closeButton, SwingConstants.LEFT);
        main.add(btnClose);
        btnClose.setBounds(main.getWidth() - 48, 10, 38, 38);
        btnClose.setOpaque(true);
        btnClose.setVisible(true);
        btnClose.addMouseListener(new XMouseListener() {
            XButton source;
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                audioRepository.buttonDisable();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                main.revalidate();
                main.repaint();
                main.getParent().setVisible(false);
                main.removeAll();
            }
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                main.revalidate();
                main.repaint();
            }
            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                main.revalidate();
                main.repaint();
            }
            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                audioRepository.menuTab();
                source.setHorizontalAlignment(SwingConstants.CENTER);
                main.revalidate();
                main.repaint();
            }
            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                main.revalidate();
                main.repaint();
            }
        });

        main.add(lblName);
        lblName.setText("owo what's this", gfxRepository.txtHeader, gfxRepository.clrText);
        lblName.setBounds((this.getWidth() / 2) - 350, 5, 700, 40);
        lblName.setAlignments(SwingConstants.CENTER);
        lblName.setVisible(true);

        lblDesc.setPreferredSize(new Dimension(lblStats.getX() - 50, 100));
        lblDesc.setSize(lblDesc.getPreferredSize());
        lblDesc.setText("o no", gfxRepository.txtHeader, gfxRepository.clrText);
        lblDesc.setAlignments(SwingConstants.CENTER, SwingConstants.TOP);

    }

    public void showStar(starClass star) { //shows the star data
        main.removeAll();
        main.add(lblStats);
        main.add(btnClose);
        main.add(lblName);
        XListSorter starSorter = new XListSorter(XConstants.VERTICAL_SORT, 5, lblStats.getX() + 15, lblStats.getY() + 15); //adds a new list sorter

        imgPortrait.setIcon(new ImageIcon(star.getPortraitGFX()));

        XLabel lblStar = new XLabel("Star", gfxRepository.txtSubtitle, gfxRepository.clrText);
        lblStar.setPreferredSize(new Dimension(lblStats.getWidth() - 30, 15));
        lblStar.setAlignments(SwingConstants.CENTER);

        XPanel imgDivider = new XPanel(gfxRepository.clrDGrey);
        imgDivider.setPreferredSize(new Dimension(lblStats.getWidth() - 30, 3));
        XPanel imgDivider2 = new XPanel(gfxRepository.clrDGrey);
        imgDivider2.setPreferredSize(new Dimension(lblStats.getWidth() - 30, 3));

        XTextImage tmgPlanets = new XTextImage();
        tmgPlanets.addImage(gfxRepository.starPlanetCount, 30, 30);
        tmgPlanets.addText(" : " + star.getNumOfPlanets(), gfxRepository.txtSubtitle, gfxRepository.clrText, 80);
        tmgPlanets.getImage().setToolTipText("Planets");
        tmgPlanets.getText().setAlignments(SwingConstants.LEFT, SwingConstants.CENTER);

        XTextImage tmgColonies = new XTextImage();
        tmgColonies.addImage(gfxRepository.colonyCount, 30, 30);
        tmgColonies.addText(" : " + star.getColonyCount(), gfxRepository.txtSubtitle, gfxRepository.clrText, 80);
        tmgColonies.getImage().setToolTipText("Colonies");
        tmgColonies.getText().setAlignments(SwingConstants.LEFT, SwingConstants.CENTER);

        starSorter.addItems(imgDivider, lblStar, tmgPlanets, tmgColonies, imgDivider2);
        starSorter.placeItems(main);

        //adds a button to enter the system view
        XButtonCustom btcEnterSystem = new XButtonCustom(gfxRepository.wideButton, SwingConstants.LEFT);
        main.add(btcEnterSystem);
        btcEnterSystem.setText("Enter System", gfxRepository.txtButtonSmall, gfxRepository.clrText);
        btcEnterSystem.setBounds(lblStats.getX() - 4, lblStats.getY() + lblStats.getHeight(), 170, 64);
        btcEnterSystem.addMouseListener(new XMouseListener(star.getMapLocationX(), star.getMapLocationY()) {
            XButtonCustom source;
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButtonCustom) mouseEvent.getSource();
                audioRepository.buttonClick();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                main.revalidate();
                main.repaint();
                main.getParent().setVisible(false);
                gameSettings.ui.showSystemView(getValueX(), getValueY());
                main.removeAll();
            }
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                source = (XButtonCustom) mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                main.revalidate();
                main.repaint();
            }
            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                source = (XButtonCustom) mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                main.revalidate();
                main.repaint();
            }
            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                source = (XButtonCustom) mouseEvent.getSource();
                audioRepository.menuTab();
                source.setHorizontalAlignment(SwingConstants.CENTER);
                main.revalidate();
                main.repaint();
            }
            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                source = (XButtonCustom)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                main.revalidate();
                main.repaint();
            }
        });
        btcEnterSystem.setVisible(true);

        XListSorter srtInfo = new XListSorter(XConstants.VERTICAL_SORT, 5, 40, lblName.getY() + lblName.getHeight() + 15);

        //displays the star's name
        if (star.isBinarySystem()) {
            lblName.setText(star.getStarName() + " - Binary " +star.getStarClassName(), gfxRepository.txtHeader, gfxRepository.clrText);
        } else {
            lblName.setText(star.getStarName() + " - " +star.getStarClassName(), gfxRepository.txtHeader, gfxRepository.clrText);
        }
        lblName.revalidate();
        lblName.repaint();

        //displays the star class's description
        lblDesc.setText("<html>" + star.getStarClassDesc() + "</html>", gfxRepository.txtItalSubtitle, gfxRepository.clrText);
        lblDesc.revalidate();
        lblDesc.repaint();

        XPanel imgDivider4 = new XPanel(gfxRepository.clrDGrey);
        imgDivider4.setPreferredSize(new Dimension(lblStats.getX() - 50, 3));

        srtInfo.addItems(lblDesc, imgDivider4);
        srtInfo.placeItems(main);

        audioRepository.starSound();
        main.setVisible(true);
        this.revalidate();
        this.repaint();
        this.setVisible(true);
    }

    public void showPlanet(planetClass planet) { //shows the planet data
        main.removeAll();
        main.add(lblStats);
        main.add(btnClose);
        main.add(lblName);
        XListSorter planetSorter = new XListSorter(XConstants.VERTICAL_SORT, 5, lblStats.getX() + 15, lblStats.getY() + 15); //adds a new list sorter

        imgPortrait.setIcon(new ImageIcon(planet.getPlanetPortrait()));

        //planet related details
        XLabel lblPlanet = new XLabel("Planet", gfxRepository.txtSubtitle, gfxRepository.clrText);
        lblPlanet.setPreferredSize(new Dimension(lblStats.getWidth() - 30, 15));
        lblPlanet.setAlignments(SwingConstants.CENTER);

        XTextImage tmgSize = new XTextImage();
        tmgSize.addImage(gfxRepository.planetSizeIcon, 30, 30);
        tmgSize.addText(": " + planet.getPlanetRadius(), gfxRepository.txtSubtitle, gfxRepository.clrText, 80);
        tmgSize.getImage().setToolTipText("Planet Size");

        XTextImage tmgPlanetMinerals = new XTextImage();
        tmgPlanetMinerals.addImage(gfxRepository.mineralsIcon, 30, 30);
        tmgPlanetMinerals.addText(" : " + uiFormat.format(planet.getResources()), gfxRepository.txtSubtitle, gfxRepository.clrText, 80);
        tmgPlanetMinerals.getImage().setToolTipText("Mineral Deposits");

        XPanel imgDivider = new XPanel(gfxRepository.clrDGrey);
        imgDivider.setPreferredSize(new Dimension(lblStats.getWidth() - 30, 3));
        XPanel imgDivider2 = new XPanel(gfxRepository.clrDGrey);
        imgDivider2.setPreferredSize(new Dimension(lblStats.getWidth() - 30, 3));
        planetSorter.addItems(imgDivider, lblPlanet, tmgSize, tmgPlanetMinerals, imgDivider2); //TODO: Switch divider over to a class, maybe?

        //colony related details (if applicable)
        try {

            XTextImage tmgPop = new XTextImage(); //tfw replacing 12 lines of tedious addition with 5 easy lines :ok_hand:
            tmgPop.addImage(gfxRepository.populationIcon, 30, 30);
            tmgPop.addText(" : " + planet.getPlanetColony().getPopulation(), gfxRepository.txtSubtitle, gfxRepository.clrText, 80);
            tmgPop.getText().setAlignments(SwingConstants.LEFT, SwingConstants.CENTER);
            tmgPop.getImage().setToolTipText("Population");

            XTextImage tmgFood = new XTextImage();
            tmgFood.addImage(gfxRepository.foodIcon, 30, 30);
            tmgFood.addText(" : " + uiFormat.format(planet.getPlanetColony().getCurrentFood()), gfxRepository.txtSubtitle, gfxRepository.clrText, 80);
            tmgFood.getText().setAlignments(SwingConstants.LEFT, SwingConstants.CENTER);
            tmgFood.getImage().setToolTipText("Stored Food");

            XTextImage tmgUnrest = new XTextImage();
            tmgUnrest.addImage(gfxRepository.unrestIcon, 30, 30);
            tmgUnrest.addText(" : " + uiFormat.format(planet.getPlanetColony().getUnrest()), gfxRepository.txtSubtitle, gfxRepository.clrText, 80);
            tmgUnrest.getText().setAlignments(SwingConstants.LEFT, SwingConstants.CENTER);
            tmgUnrest.getImage().setToolTipText("Unrest");

            XTextImage tmgPlanetEnergy = new XTextImage();
            tmgPlanetEnergy.addImage(gfxRepository.energyIcon, 30, 30);
            tmgPlanetEnergy.addText(" : " + uiFormat.format(planet.getPlanetColony().getTaxProduction()), gfxRepository.txtSubtitle, gfxRepository.clrText, 80);
            tmgPlanetEnergy.getText().setAlignments(SwingConstants.LEFT, SwingConstants.CENTER);
            tmgPlanetEnergy.getImage().setToolTipText("Energy Production");

            XTextImage tmgPlanetResources = new XTextImage();
            tmgPlanetResources.addImage(gfxRepository.resourceIcon, 30, 30);
            tmgPlanetResources.addText(" : " + uiFormat.format(planet.getPlanetColony().getResourceProduction()), gfxRepository.txtSubtitle, gfxRepository.clrText, 80);
            tmgPlanetResources.getImage().setToolTipText("Resource Production");

            XTextImage tmgPlanetResearch = new XTextImage();
            tmgPlanetResearch.addImage(gfxRepository.researchIcon, 30, 30);
            tmgPlanetResearch.addText(" : " + uiFormat.format(planet.getPlanetColony().getResearchProduction()), gfxRepository.txtSubtitle, gfxRepository.clrText, 80);
            tmgPlanetResearch.getImage().setToolTipText("Research Production");

            XLabel lblColony = new XLabel("Colony", gfxRepository.txtSubtitle, gfxRepository.clrText);
            lblColony.setPreferredSize(new Dimension(lblStats.getWidth() - 30, 15));
            lblColony.setAlignments(SwingConstants.CENTER);

            XPanel imgDivider3 = new XPanel(gfxRepository.clrDGrey);
            imgDivider3.setPreferredSize(new Dimension(lblStats.getWidth() - 30, 3));

            planetSorter.addItems(lblColony, tmgPop, tmgUnrest, tmgFood, tmgPlanetEnergy, tmgPlanetResources, tmgPlanetResearch, imgDivider3); //initialization order does not matter, such a breath of fresh air

        } catch (NullPointerException e) { //no colony found, just skip the block
        }

        planetSorter.placeItems(main); //place the items in the list

        lblName.setText(planet.getPlanetName() + " - " + planet.getPlanetClassName() , gfxRepository.txtHeader, gfxRepository.clrText);
        lblName.revalidate();
        lblName.repaint();

        XListSorter srtInfo = new XListSorter(XConstants.VERTICAL_SORT, 5, 40, lblName.getY() + lblName.getHeight() + 15);

        lblDesc.setText("<html>" + planet.getPlanetClassDesc() + "</html>", gfxRepository.txtItalSubtitle, gfxRepository.clrText);
        lblDesc.revalidate();
        lblDesc.repaint();

        XPanel imgDivider4 = new XPanel(gfxRepository.clrDGrey);
        imgDivider4.setPreferredSize(new Dimension(lblStats.getX() - 50, 3));

        srtInfo.addItems(lblDesc, imgDivider4);
        srtInfo.placeItems(main);

        audioRepository.planetAmbiance(planet.getPlanetType());
        main.setVisible(true);
        this.revalidate();
        this.repaint();
        this.setVisible(true);
    }


}
