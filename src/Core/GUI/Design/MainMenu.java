package Core.GUI.Design;

import AetheriusEngine.core.gui.*;
import Core.*;
import Core.Craft.craftBuilder;
import Core.GUI.animCore;
import Core.GUI.gfxConstants;
import Core.GUI.gfxRepository;
import Core.Player.SaveDirectoryConstants;
import Core.Player.playerData;
import Core.SFX.audioRepository;
import Core.events.eventCoreV2;
import Core.techTree.techCoreV2;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Hashtable;
import java.util.Random;

/**
 * KM
 * August 18 2017
 * Stores graphical elements for the main menu
 */

public class MainMenu extends JLayeredPane implements gfxConstants {

    private final String logoName = "Astra Project";

    private XLabel bgPanel;
    private XPanel pnlMenuBarH;
    private XLabel imgLogo;
    private XLabel lblLogo;
    private XPanel settingsMenu;
    private animCore menuSpaceport;
    private animCore menuMoon1;
    private animCore menuMoon2;
    private XPanel pnlLoadSaves;

    private XPanel pnlTechTree;
    private XPanel pnlTechSelect;
    private XPanel pnlShipBuilder;
    private XPanel pnlPauseMenu;
    private XPanel pnlOverlay;

    public MainMenu() {
        setSize(gameSettings.ui.getUIScaleX(), gameSettings.ui.getUIScaleY());
    }

    public void loadMainMenu() {
        //background image
        bgPanel = new XLabel();
        add(bgPanel, 0, 0);
        bgPanel.setBounds(0, 0, gameSettings.ui.getUIScaleX(), gameSettings.ui.getUIScaleY());
        bgPanel.scaleImage(gfxRepository.mainBackground);
        bgPanel.setOpaque(true);
        bgPanel.setVisible(true);

        //game logo
        imgLogo = new XLabel(gfxRepository.gameLogoLarge);
        add(imgLogo, 10, 0);
        imgLogo.setBounds(gameSettings.ui.getUIScaleX() - 115, 5, 120, 120);
        imgLogo.setVisible(true);

        //game name
        lblLogo = new XLabel(logoName, gfxRepository.txtTitle, gfxRepository.clrText);
        add(lblLogo, 11, 0);
        lblLogo.setBounds(10, 10, 600, 60);
        lblLogo.setAlignments(SwingConstants.LEFT, SwingConstants.TOP);
        lblLogo.setVisible(true);

        //planet
        XLabel menuPlanet = new XLabel(gfxRepository.menuPlanet);
        add(menuPlanet, 8, 0);
        menuPlanet.setBounds(gameSettings.ui.getUIScaleX() - 1100, 0, 1500, 450);
        menuPlanet.setVisible(true);

        //lens flares
        XLabel lblLensGlare = new XLabel();
        add(lblLensGlare, 9, 0);
        lblLensGlare.setBounds(0, 0, gameSettings.ui.getUIScaleX(), gameSettings.ui.getUIScaleY());
        lblLensGlare.scaleImage(gfxRepository.menuGlare);
        lblLensGlare.setVisible(true);

        //bottom menu bar
        pnlMenuBarH = new XPanel(gfxRepository.clrBackground);
        add(pnlMenuBarH, 14, 0);
        pnlMenuBarH.setBounds(0, gameSettings.ui.getUIScaleY() - 95, gameSettings.ui.getUIScaleX(), 95);
        pnlMenuBarH.setVisible(true);

        //flavor
        XLabel lblBottom = new XLabel("Powered by the Aetherius Engine", gfxRepository.txtTiny, gfxRepository.clrText);
        pnlMenuBarH.add(lblBottom);
        lblBottom.setBounds(pnlMenuBarH.getWidth() - 305, pnlMenuBarH.getHeight() - 45, 300, 40);
        lblBottom.setAlignments(SwingConstants.RIGHT, SwingConstants.BOTTOM);

        XListSorter srtMenuButtons = new XListSorter(XConstants.HORIZONTAL_SORT, (gameSettings.ui.getUIScaleX() - (319 * 4)) / 5, (gameSettings.ui.getUIScaleX() - (319 * 4)) / 5, 0);

        //button to start new game
        XButtonCustom btcNewGame = new XButtonCustom(gfxRepository.wideButton2, SwingConstants.LEFT); //replaced 10 lines with 4 using this
        btcNewGame.setText("New Game", gfxRepository.txtButtonLarge, gfxRepository.clrText);
        btcNewGame.setPreferredSize(new Dimension(319, 80));
        btcNewGame.setSize(btcNewGame.getPreferredSize());
        btcNewGame.addMouseListener(new MouseListener() {
            XButtonCustom source;

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButtonCustom)mouseEvent.getSource();
                audioRepository.buttonClick();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                refresh();

                pnlMenuBarH.setVisible(false);
                loadNewSettingsMenu();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                source = (XButtonCustom)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                refresh();
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                source = (XButtonCustom)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                refresh();
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                source = (XButtonCustom)mouseEvent.getSource();
                audioRepository.menuTab();
                source.setHorizontalAlignment(SwingConstants.CENTER);
                refresh();
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                source = (XButtonCustom)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                refresh();
            }
        });
        srtMenuButtons.addItem(btcNewGame);

        //button to load game
        XButtonCustom btcLoadGame = new XButtonCustom(gfxRepository.wideButton2, SwingConstants.LEFT);
        btcLoadGame.setText("Load Game", gfxRepository.txtButtonLarge, gfxRepository.clrText);
        btcLoadGame.setPreferredSize(btcNewGame.getPreferredSize());
        btcLoadGame.setSize(btcLoadGame.getPreferredSize());
        btcLoadGame.addMouseListener(new XMouseListener() {
            XButtonCustom source;

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButtonCustom)mouseEvent.getSource();
                audioRepository.buttonClick();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                pnlMenuBarH.setVisible(false);
                refresh();

                pnlLoadSaves.setVisible(true);
                loadSavesMenu();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                source = (XButtonCustom)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                refresh();
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                source = (XButtonCustom)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                refresh();
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                source = (XButtonCustom)mouseEvent.getSource();
                audioRepository.menuTab();
                source.setHorizontalAlignment(SwingConstants.CENTER);
                refresh();
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                source = (XButtonCustom)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                refresh();
            }
        });
        srtMenuButtons.addItem(btcLoadGame);

        //button to adjust game options, such as gfx and sound
        XButtonCustom btcSettings = new XButtonCustom(gfxRepository.wideButton2, SwingConstants.LEFT);
        btcSettings.setText("Options", gfxRepository.txtButtonLarge, gfxRepository.clrText);
        btcSettings.setPreferredSize(btcNewGame.getPreferredSize());
        btcSettings.setSize(btcSettings.getPreferredSize());
        btcSettings.addMouseListener(new XMouseListener() {
            XButtonCustom source;

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButtonCustom)mouseEvent.getSource();
                audioRepository.buttonClick();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                refresh();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                source = (XButtonCustom)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                refresh();
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                source = (XButtonCustom)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                refresh();
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                source = (XButtonCustom)mouseEvent.getSource();
                audioRepository.menuTab();
                source.setHorizontalAlignment(SwingConstants.CENTER);
                refresh();
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                source = (XButtonCustom)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                refresh();
            }
        });
        srtMenuButtons.addItem(btcSettings);

        //quit button
        XButtonCustom btcQuit = new XButtonCustom(gfxRepository.wideButton2, SwingConstants.LEFT);
        btcQuit.setText("Quit", gfxRepository.txtButtonLarge, gfxRepository.clrText);
        btcQuit.setPreferredSize(btcNewGame.getPreferredSize());
        btcQuit.setSize(btcQuit.getPreferredSize());
        btcQuit.addMouseListener(new XMouseListener() {
            XButtonCustom source;

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButtonCustom)mouseEvent.getSource();
                audioRepository.buttonClick();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                refresh();
                gameSettings.ui.getWindow().close();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                source = (XButtonCustom)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                refresh();
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                source = (XButtonCustom)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                refresh();
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                source = (XButtonCustom)mouseEvent.getSource();
                audioRepository.menuTab();
                source.setHorizontalAlignment(SwingConstants.CENTER);
                refresh();
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                source = (XButtonCustom)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                refresh();
            }
        });
        srtMenuButtons.addItem(btcQuit);

        srtMenuButtons.placeItems(pnlMenuBarH); //place the buttons

        settingsMenu = new XPanel(gfxRepository.clrBackground);
        add(settingsMenu, 13, 0);
        settingsMenu.setBounds(5, 50, 495, gameSettings.ui.getUIScaleY() - 100);
        settingsMenu.setVisible(false);

        pnlLoadSaves = new XPanel(gfxRepository.clrBackground);
        add(pnlLoadSaves, 14, 0);
        pnlLoadSaves.setBounds(5, (gameSettings.ui.getUIScaleY() / 2) - 425, 750, 850);
        pnlLoadSaves.setVisible(false);

        refresh();

        Random randomizePosition = new Random();

        menuSpaceport = new animCore(new ImageIcon(gfxRepository.menuSpaceport), 2, this, gameSettings.ui.getWindow());
        menuSpaceport.setAnimationSmoothness(0.1, 200);
        menuSpaceport.start();

        menuMoon2 = new animCore(new ImageIcon(gfxRepository.moon2Icon), 3, this, gameSettings.ui.getWindow(), gameSettings.ui.getWindow().getWidth() - 300, -200, 640);
        menuMoon2.setAnimationSmoothness(0.1, 180);
        menuMoon2.setAnimationStartTime(randomizePosition.nextInt(359)); //randomizes the starting position of the moons
        menuMoon2.start();

        menuMoon1 = new animCore(new ImageIcon(gfxRepository.moon1Icon), 3, this, gameSettings.ui.getWindow(), gameSettings.ui.getWindow().getWidth() - 500, -100, 600);
        menuMoon1.setAnimationSmoothness(0.1, 150);
        menuMoon1.setAnimationStartTime(randomizePosition.nextInt(359)); //randomizes the starting position of the moons
        menuMoon1.start();

    }

    private void loadNewSettingsMenu() { //configuration options for a new game

        settingsMenu.removeAll();
        settingsMenu.setVisible(true);

        XListSorter srtSettings = new XListSorter(XConstants.VERTICAL_SORT, 5, 5, 5);

        XLabel lblSettingsHeader = new XLabel("Game Settings", gfxRepository.txtHeader, gfxRepository.clrText);
        lblSettingsHeader.setPreferredSize(new Dimension(settingsMenu.getWidth() - 10, 40));
        lblSettingsHeader.setAlignments(SwingConstants.CENTER);

        //close new game loading, go back to main menu
        XButtonCustom btcBack = new XButtonCustom(gfxRepository.button435_80, SwingConstants.LEFT);
        btcBack.setText("Back", gfxRepository.txtButtonLarge, gfxRepository.clrText);
        btcBack.setBounds(30, settingsMenu.getHeight() - 80, 435, 80);
        settingsMenu.add(btcBack);
        btcBack.setVisible(true);
        btcBack.addMouseListener(new XMouseListener() {
            XButtonCustom source;

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButtonCustom)mouseEvent.getSource();
                audioRepository.buttonDisable();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                refresh();

                pnlMenuBarH.setVisible(true);
                lblLogo.setVisible(true);
                settingsMenu.removeAll();
                settingsMenu.setVisible(false);
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                source = (XButtonCustom)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                refresh();
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                source = (XButtonCustom)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                refresh();
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                source = (XButtonCustom)mouseEvent.getSource();
                audioRepository.menuTab();
                source.setHorizontalAlignment(SwingConstants.CENTER);
                refresh();
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                source = (XButtonCustom)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                refresh();
            }
        });

        //button to start new game at specified settings
        XButtonCustom btcLaunchNew = new XButtonCustom(gfxRepository.button435_80, SwingConstants.LEFT);
        btcLaunchNew.setText("Start", gfxRepository.txtButtonLarge, gfxRepository.clrText);
        btcLaunchNew.setBounds(btcBack.getX(), btcBack.getY() - 85, btcBack.getWidth(), btcBack.getHeight());
        settingsMenu.add(btcLaunchNew);
        btcLaunchNew.setVisible(true);
        btcLaunchNew.addMouseListener(new XMouseListener() {
            XButtonCustom source;

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButtonCustom)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                audioRepository.buttonConfirm();
                refresh();

                menuSpaceport.stopAnimation();
                menuMoon1.stopAnimation();
                menuMoon2.stopAnimation();
                gameSettings.ui.clearUI();

                XLoader loadNewGame = new XLoader() {
                    @Override
                    protected void loadOperation(int percent) {
                        Random r = new Random();
                        try {
                            Thread.sleep(30 + r.nextInt(80));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        switch(percent) {
                            case 1: //creates player data and loads main map gfx content
                                gameSettings.player = new playerData();
                                gfxRepository.loadMapGFX();
                                break;
                            case 15: //create the new user
                                gameSettings.player.newPlayer("Test Player");
                                break;
                            case 20: //set up the map
                                gameSettings.map  = new mapGenerator(gameSettings.currMapScaleX, gameSettings.currMapScaleY, gameSettings.starFrequency);
                                break;
                            case 32: //load the map string
                                gameLoader.mapLoader(gameSettings.map, gameSettings.player);
                                break;
                            case 43: //load the events core
                                gameSettings.eventhandler = new eventCoreV2();
                                break;
                            case 44: //load tech tree
                                gameSettings.techtree = new techCoreV2();
                                break;
                            case 48: //load ships
                                gameSettings.shipbuilder = new craftBuilder();
                                gameSettings.shipbuilder.buildScienceShips();
                                break;
                            case 49:
                                gameSettings.shipbuilder.refreshArray();
                                break;
                            case 80: //set up some of the UI content
                                gameSettings.ui.setPnlOverlay(new XPanel(clrBlkTransparent));
                                gameSettings.ui.setPnlPauseMenu(new XPanel(clrBGOpaque));
                                gameSettings.ui.setCelestialObject(new CelestialObject());
                                gameSettings.ui.setTechTreeView(new TechTreeView());
                                //pnlShipBuilder = new XPanel(); //TODO: Fix.
                                //pnlShipBuilder.setVisible(false);
                                break;
                        }
                    }

                    @Override
                    protected void done() {
                        audioRepository.musicShuffle(); //shuffle the music
                        audioRepository.ambianceMainGame(); //ambiance
                        gameSettings.ui.setMainKeybindings();
                        gameSettings.ui.loadMapView();
                        gameSettings.turn = new turnTicker();
                        gameSettings.turn.start();
                        gameSettings.player.tickStats();
                        gameSettings.ui.turnTick();
                    }
                };

                gameSettings.ui.loadGame(loadNewGame);
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                source = (XButtonCustom)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                refresh();
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                source = (XButtonCustom)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                refresh();
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                source = (XButtonCustom)mouseEvent.getSource();
                audioRepository.menuTab();
                source.setHorizontalAlignment(SwingConstants.CENTER);
                refresh();
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                source = (XButtonCustom)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                refresh();
            }
        });

        //sets up settings option for resource abundance
        XLabel lblResources = new XLabel("Resource Abundance", gfxRepository.txtSubheader, clrText);
        lblResources.setAlignments(SwingConstants.CENTER);
        XSlider sldResources = new XSlider(JSlider.HORIZONTAL, gameSettings.resourceAbundanceMin, gameSettings.resourceAbundanceHigh, gameSettings.resourceAbundanceAvg);
        lblResources.setPreferredSize(new Dimension(settingsMenu.getWidth() - 10, 25));
        sldResources.setTicks(25, 5);
        Hashtable hshResources = new Hashtable();
        hshResources.put(gameSettings.resourceAbundanceMin, new XLabel("Sparse", gfxRepository.txtTiny, clrText));
        hshResources.put(gameSettings.resourceAbundanceHigh, new XLabel("Abundant", gfxRepository.txtTiny, clrText));
        hshResources.put(gameSettings.resourceAbundanceAvg, new XLabel("Average", gfxRepository.txtTiny, clrText));
        sldResources.setLabelTable(hshResources);
        sldResources.setFont(gfxRepository.txtTiny);
        sldResources.setForeground(clrText);
        sldResources.setPreferredSize(new Dimension(settingsMenu.getWidth() - 20, 50));
        sldResources.addChangeListener(new ChangeListener() { //adds a listener to keep track of the slider's value and translate it to the gameSettings class
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                XSlider source = (XSlider)changeEvent.getSource();
                if (source.getValueIsAdjusting()) {
                    gameSettings.currentResources = source.getValue();
                }
                refresh();
            }
        });

        //sets up settings option for star abundance
        XLabel lblStarFreq = new XLabel("Star Frequency", gfxRepository.txtSubheader, clrText);
        lblStarFreq.setAlignments(SwingConstants.CENTER);
        XSlider sldStarFreq = new XSlider(JSlider.HORIZONTAL, gameSettings.starFreqMin, gameSettings.starFreqHigh, gameSettings.starFreqAvg);
        lblStarFreq.setPreferredSize(new Dimension(settingsMenu.getWidth() - 10, 25));
        sldStarFreq.setTicks(15, 5);
        Hashtable hshStarFreq = new Hashtable();
        hshStarFreq.put(gameSettings.starFreqMin, new XLabel("Many", gfxRepository.txtTiny, clrText));
        hshStarFreq.put(gameSettings.starFreqHigh, new XLabel("Few", gfxRepository.txtTiny, clrText));
        hshStarFreq.put(gameSettings.starFreqAvg, new XLabel("Average", gfxRepository.txtTiny, clrText));
        sldStarFreq.setLabelTable(hshStarFreq);
        sldStarFreq.setFont(gfxRepository.txtTiny);
        sldStarFreq.setForeground(gfxRepository.clrText);
        sldStarFreq.setPreferredSize(new Dimension(settingsMenu.getWidth() - 20, 50));
        sldStarFreq.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                XSlider source = (XSlider)changeEvent.getSource();
                if (source.getValueIsAdjusting()) {
                    gameSettings.starFrequency = source.getValue();
                }
                refresh();
            }
        });

        //sets up settings option for map scaling
        XLabel lblMapScale = new XLabel("Map Scale", gfxRepository.txtSubheader, clrText);
        lblMapScale.setAlignments(SwingConstants.CENTER);
        XSlider sldMapScale = new XSlider(JSlider.HORIZONTAL, gameSettings.mapScaleMin, gameSettings.mapScaleHigh, gameSettings.mapScaleAvg);
        lblMapScale.setPreferredSize(new Dimension(settingsMenu.getWidth() - 10, 25));
        sldMapScale.setTicks(20, 5);
        Hashtable hshMapScale = new Hashtable();
        hshMapScale.put(gameSettings.mapScaleMin, new XLabel("Small", gfxRepository.txtTiny, clrText));
        hshMapScale.put(gameSettings.mapScaleHigh, new XLabel("Huge", gfxRepository.txtTiny, clrText));
        hshMapScale.put(gameSettings.mapScaleAvg, new XLabel("Normal", gfxRepository.txtTiny, clrText));
        sldMapScale.setLabelTable(hshMapScale);
        sldMapScale.setFont(gfxRepository.txtTiny);
        sldMapScale.setForeground(gfxRepository.clrText);
        sldMapScale.setPreferredSize(new Dimension(settingsMenu.getWidth() - 20, 50));
        sldMapScale.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                XSlider source = (XSlider)changeEvent.getSource();
                if (source.getValueIsAdjusting()) {
                    gameSettings.currMapScaleX = source.getValue();
                    gameSettings.currMapScaleY = source.getValue();
                }
                refresh();
            }
        });

        XLabel lblDiffOverall = new XLabel("Overall Difficulty", gfxRepository.txtSubheader, clrText);
        lblDiffOverall.setAlignments(SwingConstants.CENTER);
        XSlider sldDiffOverall = new XSlider(JSlider.HORIZONTAL, gameSettings.overallDifficultyMin, gameSettings.overallDifficultyHigh, gameSettings.overallDifficultyAvg);
        lblDiffOverall.setPreferredSize(new Dimension(settingsMenu.getWidth() - 10, 25));
        sldDiffOverall.setTicks(25, 5);
        Hashtable hshDiffOverall = new Hashtable();
        hshDiffOverall.put(gameSettings.overallDifficultyMin, new XLabel("Easy", gfxRepository.txtTiny, clrText));
        hshDiffOverall.put(gameSettings.overallDifficultyHigh, new XLabel("Hard", gfxRepository.txtTiny, clrText));
        hshDiffOverall.put(gameSettings.overallDifficultyAvg, new XLabel("Normal", gfxRepository.txtTiny, clrText));
        sldDiffOverall.setLabelTable(hshDiffOverall);
        sldDiffOverall.setFont(gfxRepository.txtTiny);
        sldDiffOverall.setForeground(gfxRepository.clrText);
        sldDiffOverall.setPreferredSize(new Dimension(settingsMenu.getWidth() - 20, 50));
        sldDiffOverall.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                XSlider source = (XSlider)changeEvent.getSource();
                if (source.getValueIsAdjusting()) {
                    gameSettings.currDifficulty = source.getValue();
                }
                refresh();
            }
        });

        srtSettings.addItems(lblSettingsHeader, lblDiffOverall, sldDiffOverall, lblMapScale, sldMapScale, lblStarFreq, sldStarFreq, lblResources, sldResources);
        srtSettings.placeItems(settingsMenu);

        refresh();
    }

    private void loadSavesMenu() { //loads the saved games
        XLabel lblLoadSaves = new XLabel("Load Saved Game", gfxRepository.txtLargeText, clrText);
        pnlLoadSaves.add(lblLoadSaves);
        lblLoadSaves.setBounds(0, 5, pnlLoadSaves.getWidth(), 40);
        lblLoadSaves.setAlignments(SwingConstants.CENTER);
        lblLoadSaves.setVisible(true);

        XPanel pnlSavesList = new XPanel(clrDGrey);
        pnlLoadSaves.add(pnlSavesList);
        pnlSavesList.setBounds(5, 80, 319, pnlLoadSaves.getHeight() - 160);
        pnlSavesList.setVisible(true);

        //button to handle returning to the title screen options
        XLabel lblReturn = new XLabel("Back", gfxRepository.txtButtonLarge, clrText);
        pnlLoadSaves.add(lblReturn);
        lblReturn.setBounds(pnlSavesList.getX(), pnlSavesList.getY() + pnlSavesList.getHeight(), 319, 80);
        lblReturn.setAlignments(SwingConstants.CENTER);
        lblReturn.setVisible(true);

        XButton btnReturn = new XButton(gfxRepository.wideButton2, SwingConstants.LEFT);
        pnlLoadSaves.add(btnReturn);
        btnReturn.setBounds(lblReturn.getBounds());
        btnReturn.setVisible(true);

        btnReturn.addMouseListener(new XMouseListener() {
            XButton source;

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                audioRepository.buttonDisable();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                refresh();

                pnlMenuBarH.setVisible(true);
                lblLogo.setVisible(true);
                pnlLoadSaves.removeAll();
                pnlLoadSaves.setVisible(false);
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                refresh();
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                refresh();
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                audioRepository.menuTab();
                source.setHorizontalAlignment(SwingConstants.CENTER);
                refresh();
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                refresh();
            }
        });

        XScrollPane spSavesList = new XScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);


        try {
            File[] loadSaves = SaveDirectoryConstants.SAVEDIRECTORY.listFiles();

            for (File current : loadSaves) {
                if (current.isDirectory()) { //only want directories
                    File[] temp = current.listFiles();
                    for (File player : temp) { //find the player's data file
                        if (player.getName().equals("playerinfo.xml")) { //FOUND IT
                            xmlLoader.loadPlayerInfo(player); //loads the player's info
                        }
                    }
                } else {
                    System.err.println("Illegal alien detected in saves folder, clearing it."); //"undocumented file"
                    current.delete(); //deport, er, delete the intruding file. shouldn't be here
                }
            }

            for (int i = 0; i < xmlLoader.listOfPlayers.size(); i++) { //lists out all of the save files








            }
        } catch (NullPointerException e) { //catches any null errors so the game doesn't just explode


        }

        refresh();

    }

    public void refresh() {
        repaint();
        revalidate();
    }


}
