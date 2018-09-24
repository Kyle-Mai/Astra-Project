package Core.GUI;

//import all relevant stuff
import Core.*;
import Core.Craft.craftBuilder;
import Core.GUI.Design.*;
import Core.Player.SaveDirectoryConstants;
import Core.Player.playerData;
import Core.SFX.audioRepository;
import Core.events.eventCoreV2;
import Core.techTree.techCoreV2;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
import AetheriusEngine.core.gui.*;

/**
 KM
 May 16 2017
 Fourth iteration of my UI core.
 Handles the creation and display of all UI elements, as well as the general event calls.

 NOTE: Code is incredibly poorly optimized for space and efficiency... I eventually stopped giving half a damn about efficiency and favoured speed instead.

 SOURCES:
 Stack Overflow - Gif handling syntax, idea/syntax for creating layered panes, syntax for declaring new fonts.
 Java API - Multithreading syntax, handling of the loading bar's work.
 Self - All non-cited work.
 */

//TODO: Garbage coding... really need to clean it up.


public class guiCoreV4 implements gfxConstants {

    /** Stores resource declarations **/

    DecimalFormat uiFormat = new DecimalFormat("###,##0.00");

    private int tileSize = 50;

    public ArrayList<XPanel> shipList = new ArrayList<>();

    private Dimension monitorSize = Toolkit.getDefaultToolkit().getScreenSize(); //gets the screen size of the user
    private double screenWidth = monitorSize.getWidth();
    private double screenHeight = monitorSize.getHeight();

    public XFrame window;
    private JLayeredPane layers; //sorts the layers of the screen
    private XPanel screen;
    private XLabel bgPanel;
    private XLabel imgLogo;
    private XPanel settingsMenu;
    private animCore menuSpaceport;
    private animCore menuMoon1;
    private animCore menuMoon2;
    private XPanel pnlStarData;
    private XPanel pnlPlanetData;
    private XLabel lblLogo;
    private XPanel pnlMenuBarH;
    private XPanel pnlBG;
    private XPanel pnlLoadSaves;
    private XPanel pnlTimer;
    private XLabel lblCurrentDate;
    private XPanel pnlTopBar;
    private XLabel lblTimeScale;
    private XLabel imgPauseBar;
    private XLabel lblPauseBar;
    private XPanel pnlTechTree;
    private XPanel pnlTechSelect;
    private XPanel pnlShipBuilder;
    private XPanel pnlPauseMenu;
    private XPanel pnlOverlay;

    public EventWindow eventWindow;
    private LoadingScreen loadingScreen;
    private CelestialObject celestialObject;
    private MainMenu mainMenu;

    private XTextImage tmgMinerals;
    private XTextImage tmgEnergy;
    private XTextImage tmgTech;
    private XTextImage tmgPlanetMinerals;
    private XTextImage tmgPop;
    private XTextImage tmgFood;
    private XTextImage tmgUnrest;
    private XTextImage tmgPlanetEnergy;
    private XTextImage tmgPlanetResources;
    private XTextImage tmgResearch;
    private XTextImage tmgPlanetResearch;

    private planetClass currentPlanet;
    private starClass currentStar;

    private boolean pauseMenuOpen = false;

    private TechTreeView techTreeView;


    /** UI scaling code**/
    //handles the scaling of the UI

    //methods to return the UI elements
    public int getUIScaleX() { return window.getWidth(); }

    public int getUIScaleY() { return window.getHeight(); }

    public void resizeWindow() {
        window.setBounds((int)(screenScale.monitorSize.getWidth() / 2) - ((int)screenScale.screenSize.getWidth() / 2), (int)(screenScale.monitorSize.getHeight() / 2) - ((int)screenScale.screenSize.getHeight() / 2), (int)screenScale.screenSize.getWidth(), (int)screenScale.screenSize.getHeight());
        screen.setBounds(0, 0, getUIScaleX(), getUIScaleY());
    }

    /** Main Data **/

    //builds the core framework
    public guiCoreV4() {

        window = new XFrame("Astra Launcher", gfxRepository.gameLogo);
        screen = new XPanel(gfxRepository.clrBlk);

        screen.setFocusable(true);
        screen.setVisible(true);
        window.setContentPane(screen);
        //window.getRootPane().setCursor(gfxRepository.defaultCursor);
        window.pack();
        window.setBounds((int)(screenScale.monitorSize.getWidth() / 2) - ((int)screenScale.LAUNCHER.size().getWidth() / 2), (int)(screenScale.monitorSize.getHeight() / 2) - ((int)screenScale.LAUNCHER.size().getHeight() / 2), (int)screenScale.LAUNCHER.size().getWidth(), (int)screenScale.LAUNCHER.size().getHeight());
        screen.setBounds(0, 0, getUIScaleX(), getUIScaleY());
        window.setVisible(true);

    }

    public XFrame getWindow() { return window; }

    public TechTreeView getTechTreeView() { return techTreeView; }
    public void setTechTreeView(TechTreeView t) { techTreeView = t; }

    public CelestialObject getCelestialObject() { return celestialObject; }
    public void setCelestialObject(CelestialObject c) { celestialObject = c; }

    public void setPnlPauseMenu(XPanel p) { pnlPauseMenu = p; }
    public void setPnlOverlay(XPanel p) { pnlOverlay = p; }

    public void clearUI() {
        //clears the content off of the UI
        screen.removeAll();
        layers.removeAll(); //clean the layer slate
        window.refresh();
        window.getContentPane().add(layers);
    }


    /** Launcher Builder **/
    //builds the game launcher UI

    public void loadLauncherScreen() {
        System.out.println("Loading launcher data...");

        //initialize the layers
        layers = new JLayeredPane();
        layers.setBounds(0, 0, getUIScaleX(), getUIScaleY());
        //add the layered window to the content pane
        window.getContentPane().add(layers);

        LauncherWindow launcher = new LauncherWindow();
        layers.add(launcher, 0);

        //loadLauncherExpansions(); //load the expansion packs

    }

    public void launchGame() {
        window.setTitle("Astra Project"); //changes the title from the launcher to the game window
        window.refresh();
        audioRepository.musicTitleScreen(); //plays the title screen music
        clearUI();
        resizeWindow();
        loadingScreen = new LoadingScreen();

        XLoader loadMainContent = new XLoader() {
            @Override
            protected void loadOperation(int i) {
                Random r = new Random();
                try {
                    Thread.sleep(80 + r.nextInt(100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                switch(i) {
                    case 1:
                        gfxRepository.loadMainGFX();
                        break;
                    case 42:
                        gameLoader.loadXMLData();
                        break;
                    case 65:
                        gfxRepository.loadContentGFX();
                        break;
                    case 84:
                        gameLoader.cleanContent();
                        break;
                }
            }

            @Override
            protected void done() {
                layers.removeAll();
                screen.remove(loadingScreen);
                mainMenu = new MainMenu();
                mainMenu.loadMainMenu();
                layers.add(mainMenu, 3, 1);
                mainMenu.setVisible(true);
                window.refresh();
            }
        };
        loadGame(loadMainContent);

    }

    /** Loading screen UI elements **/
    //shown when the game is loading new content

    //load the loading screen and game content
    public void loadGame(XLoader loader) {

        clearUI();
        window.setBounds((int)(screenWidth / 2) - (this.getUIScaleX() / 2), (int)(screenHeight / 2) - (this.getUIScaleY() / 2), this.getUIScaleX(), this.getUIScaleY());
        screen.setBounds(0, 0, getUIScaleX(), getUIScaleY());
        layers.setBounds(0, 0, getUIScaleX(), getUIScaleY()); //reset the size

        screen.add(loadingScreen);
        loadingScreen.load(loader);

    }

    public void setMainKeybindings() { //adds keybindings to the game

        screen.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "pause");
        screen.getActionMap().put("pause", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.out.println("Space bar pressed - pausing game.");
                gameSettings.gameIsPaused = !gameSettings.gameIsPaused;
                audioRepository.gamePaused();
            }
        });
        screen.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "menu");
        screen.getActionMap().put("menu", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                showPauseMenu();
            }
        });
        screen.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, 0), "speed");
        screen.getActionMap().put("speed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (gameSettings.currentTime > 0) {
                    gameSettings.currentTime--;
                    audioRepository.gameFaster();
                } else {
                    audioRepository.gameInvalid();
                }
                lblTimeScale.setText(gameSettings.timeLocale[gameSettings.currentTime], gfxRepository.txtItalSubtitle, gfxRepository.clrText);
                if (gameSettings.gameIsPaused) { lblTimeScale.setText("PAUSED", gfxRepository.txtItalSubtitle, gfxRepository.clrDisable); } //if the game is paused, load the pause bar
            }
        });
        screen.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, 0), "slow");
        screen.getActionMap().put("slow", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (gameSettings.currentTime < 4) {
                    gameSettings.currentTime++;
                    audioRepository.gameSlower();
                } else {
                    audioRepository.gameInvalid();
                }
                lblTimeScale.setText(gameSettings.timeLocale[gameSettings.currentTime], gfxRepository.txtItalSubtitle, gfxRepository.clrText);
                if (gameSettings.gameIsPaused) { lblTimeScale.setText("PAUSED", gfxRepository.txtItalSubtitle, gfxRepository.clrDisable); } //if the game is paused, load the pause bar
            }
        });

        System.out.println("Main game keybindings added.");

    }

    public void removeKeybindings() {
        screen.getActionMap().clear(); //clears the keybindings
    }


    /** Map screen UI **/
    //Handles the display of the map screen's UI.

    //loads the map view
    public void loadMapView() {

        Random r = new Random();

        clearUI();

        XLabel lblBackdrop = new XLabel(gfxRepository.mainBackground);
        layers.add(lblBackdrop, new Integer(0), 0);
        lblBackdrop.setBounds(0, 0, getUIScaleX(), getUIScaleY());
        lblBackdrop.setVisible(true);

        pnlBG = new XPanel(gfxRepository.clrInvisible) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(Math.max(window.getWidth(), (tileSize * (gameSettings.map.mapTiles.size() + 4))), Math.max(window.getHeight(), (tileSize * (gameSettings.map.mapTiles.get(0).size() + 4))));
            };
        } ;
        layers.add(pnlBG, new Integer(2), 0);
        pnlBG.setBounds(0, 0, Math.max(window.getWidth(), (tileSize * (gameSettings.map.mapTiles.size() + 4))), Math.max(window.getHeight(), (tileSize * (gameSettings.map.mapTiles.get(0).size() + 4))));
        pnlBG.setAutoscrolls(true);
        pnlBG.setVisible(true);

        XScrollPane mapView = new XScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        layers.add(mapView, new Integer(3), 0);
        mapView.setViewportView(pnlBG);
        mapView.setBounds(0, 0, window.getWidth(), window.getHeight());
        mapView.setVisible(true);

        MouseAdapter mapScroller = new MouseAdapter() { //Taken from - https://stackoverflow.com/questions/31171502/scroll-jscrollpane-by-dragging-mouse-java-swing

            private Point origin;

            @Override
            public void mousePressed(MouseEvent e) {
                origin = new Point(e.getPoint());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (origin != null) {
                    JViewport viewPort = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, pnlBG);
                    if (viewPort != null) {
                        int deltaX = origin.x - e.getX();
                        int deltaY = origin.y - e.getY();

                        Rectangle view = viewPort.getViewRect();
                        view.x += deltaX;
                        view.y += deltaY;

                        pnlBG.scrollRectToVisible(view);
                    }
                }
            }

        };

        pnlBG.addMouseListener(mapScroller);
        pnlBG.addMouseMotionListener(mapScroller);

        XLabel lblDustEffect = new XLabel(gfxRepository.galaxyDust);
        pnlBG.add(lblDustEffect);
        lblDustEffect.setBounds(300 + r.nextInt(1100), 300 + r.nextInt(1100), 900, 560);
        lblDustEffect.setVisible(true);

        ArrayList<ArrayList<XLabel>> mapGFX = new ArrayList<>();
        ArrayList<ArrayList<XButton>> mapButton = new ArrayList<>();

        int positionX;
        int positionY = 0;

        //sets up the map tiles
        for (int i = 0; i < gameSettings.map.mapTiles.size(); i++) {
            mapGFX.add(new ArrayList<XLabel>());
            mapButton.add(new ArrayList<XButton>());
            positionX = 0;

            for (int j = 0; j < gameSettings.map.mapTiles.get(i).size(); j++) {
                if (gameSettings.map.mapTiles.get(i).get(j).getStar()) {

                    //if (gameSettings.map.mapTiles.get(i).get(j).getVisibility()) { //tile visible

                        mapGFX.get(i).add(new XLabel(gameSettings.map.mapTiles.get(i).get(j).getStarData().getIconGFX())); //adds the star's icon to the map
                        XLabel starName = new XLabel(gameSettings.map.mapTiles.get(i).get(j).getStarData().getStarName(), gfxRepository.txtItalSubtitle, gfxRepository.clrText);
                        pnlBG.add(starName);
                        starName.setBounds(tileSize * (positionX + 1) - 25, tileSize * (positionY + 1) + 25, tileSize + 50, tileSize);
                        starName.setAlignments(SwingConstants.CENTER);
                        starName.setVisible(true);

                    /*} else { //tile not visible

                        mapGFX.get(i).add(new XLabel(gfxRepository.unknownStar)); //star isn't visible, add an unknown icon
                        XLabel starName = new XLabel("???", gfxRepository.txtItalSubtitle, gfxRepository.clrText); //who knows?
                        pnlBG.add(starName);
                        starName.setBounds(tileSize * (positionX + 1) - 25, tileSize * (positionY + 1) + 25, tileSize + 50, tileSize);
                        starName.setAlignments(SwingConstants.CENTER);
                        starName.setVisible(true);
                    } */

                    if (gameSettings.map.mapTiles.get(i).get(j).getStarData().isHomeSystem()) {
                        XLabel homeSystem = new XLabel(gfxRepository.homeSystem);
                        pnlBG.add(homeSystem);
                        homeSystem.setBounds(tileSize * (positionX + 1) - 20, tileSize * (positionY + 1) - 20, tileSize, tileSize);
                        homeSystem.setAlignments(SwingConstants.CENTER);
                        homeSystem.setVisible(true);
                    }

                    mapButton.get(i).add(new XButton(gfxRepository.mapHighlight, SwingConstants.LEFT));
                    mapGFX.get(i).get(j).add(mapButton.get(i).get(j));
                    mapButton.get(i).get(j).setOpaque(false);
                    mapButton.get(i).get(j).setVisible(true);
                    mapButton.get(i).get(j).setBounds(0, 0, tileSize, tileSize);

                    //if (gameSettings.map.mapTiles.get(i).get(j).getVisibility()) { //poorly optimized, don't care

                        mapButton.get(i).get(j).addMouseListener(new XMouseListener(i, j) {
                            XButton source;
                            @Override
                            public void mouseClicked(MouseEvent mouseEvent) {
                                source = (XButton) mouseEvent.getSource();
                                source.setHorizontalAlignment(SwingConstants.RIGHT);
                                celestialObject.showStar(gameSettings.map.mapTiles.get(getValueX()).get(getValueY()).getStarData());
                                window.refresh();
                            }

                            @Override
                            public void mousePressed(MouseEvent mouseEvent) {
                                source = (XButton) mouseEvent.getSource();
                                source.setHorizontalAlignment(SwingConstants.RIGHT);
                                window.refresh();
                            }

                            @Override
                            public void mouseReleased(MouseEvent mouseEvent) {
                                source = (XButton) mouseEvent.getSource();
                                source.setHorizontalAlignment(SwingConstants.LEFT);
                                window.refresh();
                            }

                            @Override
                            public void mouseEntered(MouseEvent mouseEvent) {
                                source = (XButton) mouseEvent.getSource();
                                source.setHorizontalAlignment(SwingConstants.CENTER);
                                window.refresh();
                            }

                            @Override
                            public void mouseExited(MouseEvent mouseEvent) {
                                source = (XButton) mouseEvent.getSource();
                                source.setHorizontalAlignment(SwingConstants.LEFT);
                                window.refresh();
                            }
                        });

                    /* } else { //tile hasn't been surveyed yet, don't display any information

                        mapButton.get(i).get(j).setIcon(gfxRepository.systemRefuse, SwingConstants.LEFT);

                        mapButton.get(i).get(j).addMouseListener(new XMouseListener(i, j) {
                            XButton source;

                            @Override
                            public void mouseClicked(MouseEvent mouseEvent) {
                                source = (XButton) mouseEvent.getSource();
                                audioRepository.gameInvalid();
                                source.setHorizontalAlignment(SwingConstants.RIGHT);
                                window.refresh();
                            }

                            @Override
                            public void mousePressed(MouseEvent mouseEvent) {
                                source = (XButton) mouseEvent.getSource();
                                source.setHorizontalAlignment(SwingConstants.RIGHT);
                                window.refresh();
                            }

                            @Override
                            public void mouseReleased(MouseEvent mouseEvent) {
                                source = (XButton) mouseEvent.getSource();
                                source.setHorizontalAlignment(SwingConstants.LEFT);
                                window.refresh();
                            }

                            @Override
                            public void mouseEntered(MouseEvent mouseEvent) {
                                source = (XButton) mouseEvent.getSource();
                                source.setHorizontalAlignment(SwingConstants.CENTER);
                                window.refresh();
                            }

                            @Override
                            public void mouseExited(MouseEvent mouseEvent) {
                                source = (XButton) mouseEvent.getSource();
                                source.setHorizontalAlignment(SwingConstants.LEFT);
                                window.refresh();
                            }
                        });


                    } */

                } else {
                    mapGFX.get(i).add(new XLabel());
                    mapButton.get(i).add(null); //lololo cheating
                }
                pnlBG.add(mapGFX.get(i).get(j));
                mapGFX.get(i).get(j).setBounds(tileSize * (positionX + 1), tileSize * (positionY + 1), tileSize, tileSize);
                mapGFX.get(i).get(j).setVisible(true);

                positionX++;
            }

            positionY++;
        }

        //sets the viewport on the center of the map
        Rectangle mapViewSize = mapView.getViewport().getViewRect();
        Dimension mapSize = mapView.getViewport().getViewSize();
        mapView.getViewport().setViewPosition(new Point(((mapSize.width - mapViewSize.width) / 2) - (screen.getWidth() / 2), ((mapSize.height - mapViewSize.height) / 2) - (screen.getHeight() / 2)));

        pnlStarData = new XPanel();
        layers.add(pnlStarData, new Integer(10), 0);
        pnlStarData.setBounds((screen.getWidth() / 2) - 400, 100, 800, screen.getHeight() - 200);
        pnlStarData.setVisible(false);

        loadPlayerBar();

        window.refresh();

    }


    /** General in-game UI elements **/
    //includes the top bar, the pause menu, etc

    private void loadPlayerBar() { //loads the bar at the top of the screen with the relevant player information

        eventWindow = new EventWindow();
        eventWindow.setLocation((screen.getWidth() / 2) - (eventWindow.getWidth() / 2), (screen.getHeight() / 2) - (eventWindow.getHeight() / 2));

        celestialObject = new CelestialObject();
        layers.add(celestialObject, 12, 0);
        celestialObject.setBounds((screen.getWidth() / 2) - (celestialObject.getWidth() / 2), (screen.getHeight() / 2) - (celestialObject.getHeight() / 2), celestialObject.getWidth(), celestialObject.getHeight());

        pnlTopBar = new XPanel();
        layers.add(pnlTopBar, new Integer(8), 0);
        layers.add(eventWindow, new Integer(15), 0);
        pnlTopBar.setBounds(0, 0, screen.getWidth(), 53);

        XLabel lblTopBarBackground = new XLabel(gfxRepository.topbar_bg);
        layers.add(lblTopBarBackground, new Integer(7), 0);
        lblTopBarBackground.setBounds(0, 0, screen.getWidth(), 53);
        lblTopBarBackground.scaleImage(gfxRepository.topbar_bg);
        lblTopBarBackground.setVisible(true);

        XLabel lblTopBarShield = new XLabel(gfxRepository.topbar_shield);
        layers.add(lblTopBarShield, new Integer(8), 0);
        lblTopBarShield.setBounds(0, 0, 66, 76);
        lblTopBarShield.setVisible(true);

        XButtonCustom btnMenu = new XButtonCustom(gfxRepository.button99_48, SwingConstants.LEFT);
        btnMenu.setBounds(pnlTopBar.getWidth() - 99, 2, 99, 48);
        btnMenu.setImage(gfxRepository.menuButton);
        pnlTopBar.add(btnMenu);
        btnMenu.setVisible(true);
        btnMenu.addMouseListener(new XMouseListener() {
            XButtonCustom source;

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButtonCustom) mouseEvent.getSource();
                audioRepository.buttonClick();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                window.refresh();

                if (!pauseMenuOpen) {
                    audioRepository.buttonClick();
                    showPauseMenu();
                } else {
                    audioRepository.buttonDisable();
                    pnlOverlay.setVisible(false);
                    pnlPauseMenu.removeAll();
                    pauseMenuOpen = false;
                }
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                source = (XButtonCustom) mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                window.refresh();
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                source = (XButtonCustom) mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                window.refresh();
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                source = (XButtonCustom) mouseEvent.getSource();
                audioRepository.menuTab();
                source.setHorizontalAlignment(SwingConstants.CENTER);
                window.refresh();
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                source = (XButtonCustom) mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                window.refresh();
            }
        });

        XButton btnTech = new XButton(gfxRepository.techMenu, SwingConstants.LEFT);
        btnTech.addMouseListener(new XMouseListener() {
            XButton source;

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                window.refresh();

                if (pnlTechTree.isVisible()) { //check whether or not tech tree is already visible
                    audioRepository.buttonDisable();
                    pnlTechSelect.removeAll();
                    pnlTechSelect.setVisible(false);
                    pnlTechTree.removeAll();
                    pnlTechTree.setVisible(false);
                } else {
                    audioRepository.buttonClick();
                    showTechTree(); //load the tech tree screen
                }
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                window.refresh();
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                window.refresh();
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                audioRepository.menuTab();
                source.setHorizontalAlignment(SwingConstants.CENTER);
                window.refresh();
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                window.refresh();
            }
        });

        XButton btnEmpire = new XButton(gfxRepository.empireMenu, SwingConstants.LEFT);
        btnEmpire.addMouseListener(new XMouseListener() {
            XButton source;

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                audioRepository.buttonClick();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                window.refresh();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                window.refresh();
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                window.refresh();
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                audioRepository.menuTab();
                source.setHorizontalAlignment(SwingConstants.CENTER);
                window.refresh();
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                window.refresh();
            }
        });

        XButton btnGovernment = new XButton(gfxRepository.governmentMenu, SwingConstants.LEFT);
        btnGovernment.addMouseListener(new XMouseListener() {
            XButton source;

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                audioRepository.buttonClick();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                window.refresh();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                window.refresh();
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                window.refresh();
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                audioRepository.menuTab();
                source.setHorizontalAlignment(SwingConstants.CENTER);
                window.refresh();
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                window.refresh();
            }
        });

        XButton btnFleet = new XButton(gfxRepository.fleetMenu, SwingConstants.LEFT);
        btnFleet.addMouseListener(new XMouseListener() {
            XButton source;

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                audioRepository.buttonClick();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                window.refresh();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                window.refresh();
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                window.refresh();
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                audioRepository.menuTab();
                source.setHorizontalAlignment(SwingConstants.CENTER);
                window.refresh();
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                window.refresh();
            }
        });

        XListSorter srtTopButtons = new XListSorter(XConstants.HORIZONTAL_SORT, 0, 70, 0); //sorts the buttons
        srtTopButtons.forceItemSize(73, 53);
        srtTopButtons.addItems(btnTech, btnEmpire, btnGovernment, btnFleet);
        srtTopButtons.placeItems(pnlTopBar);

        tmgTech = new XTextImage();
        tmgTech.addImage(gfxRepository.researchIcon, 34, 34);
        tmgTech.addText(": " + uiFormat.format(gameSettings.player.getResearchTurn()) + "/mo", gfxRepository.txtSubtitle, gfxRepository.clrText, 90);
        tmgTech.getText().setVerticalAlignment(SwingConstants.CENTER);

        tmgEnergy = new XTextImage();
        tmgEnergy.addImage(gfxRepository.energyIcon, 34, 34);
        tmgEnergy.addText(": " + uiFormat.format(gameSettings.player.getFunds()) + " (" + uiFormat.format(gameSettings.player.getCurrencyTurn()) + "/mo)", gfxRepository.txtSubtitle, gfxRepository.clrText, 170);
        if (gameSettings.player.getCurrencyTurn() < 0) { //if the value is negative, display accordingly
            tmgEnergy.getText().setForeground(gfxRepository.clrDisable);
        }
        tmgEnergy.getText().setVerticalAlignment(SwingConstants.CENTER);

        tmgMinerals = new XTextImage();
        tmgMinerals.addImage(gfxRepository.resourceIcon, 34, 34);
        tmgMinerals.addText(": " + uiFormat.format(gameSettings.player.getResources()) + " (" + uiFormat.format(gameSettings.player.getResourcesTurn()) + "/mo)", gfxRepository.txtSubtitle, gfxRepository.clrText, 170);
        if (gameSettings.player.getResourcesTurn() < 0) { //if the value is negative, display accordingly
            tmgMinerals.getText().setForeground(gfxRepository.clrDisable);
        }
        tmgMinerals.getText().setVerticalAlignment(SwingConstants.CENTER);

        XListSorter srtPlayerBar = new XListSorter(XConstants.HORIZONTAL_SORT, 10, btnFleet.getX() + btnFleet.getWidth() + 10, 9); //sorts the icons
        srtPlayerBar.addItems(tmgTech, tmgEnergy, tmgMinerals);
        srtPlayerBar.placeItems(pnlTopBar);

        //displays the time scale
        pnlTimer = new XPanel();
        pnlTopBar.add(pnlTimer);
        pnlTimer.setBounds(btnMenu.getX() - 155, 0, 150, pnlTopBar.getHeight());
        pnlTimer.setVisible(true);

        layers.add(pnlOverlay, new Integer(14), 0);
        pnlOverlay.setBounds(0, pnlTopBar.getHeight(), window.getWidth(), window.getHeight() - pnlTopBar.getHeight());
        pnlOverlay.add(pnlPauseMenu);
        pnlPauseMenu.setBounds((screen.getWidth() / 2) - 450, (screen.getHeight() / 2) - 400, 900, 800);
        pnlPauseMenu.setVisible(true);
        pnlOverlay.setVisible(false);

        imgPauseBar = new XLabel(gfxRepository.pauseBar);
        layers.add(imgPauseBar, new Integer(11), 0);
        imgPauseBar.setBounds((screen.getWidth() / 2) - 170, pnlTopBar.getHeight(), 340, 37);
        imgPauseBar.setVisible(true);

        lblPauseBar = new XLabel("---- Game Paused ----", gfxRepository.txtButtonSmall, gfxRepository.clrText);
        imgPauseBar.add(lblPauseBar);
        lblPauseBar.setBounds(0, 0, imgPauseBar.getWidth(), imgPauseBar.getHeight());
        lblPauseBar.setAlignments(SwingConstants.CENTER);
        lblPauseBar.setVisible(true);

        loadDate();

    }

    private void loadDate() { //tracks the current date and displays it

        pnlTimer.removeAll(); //clear panel content

        XButton btnSlowTime = new XButton(gfxRepository.leftButton, SwingConstants.LEFT);
        pnlTimer.add(btnSlowTime);
        btnSlowTime.setBounds(0, (pnlTimer.getHeight() / 2) - 19, 38, 38);
        btnSlowTime.setVisible(true);

        btnSlowTime.addMouseListener(new XMouseListener() {
            XButton source;

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                window.refresh();

                if (gameSettings.currentTime < 4) {
                    gameSettings.currentTime++;
                    audioRepository.gameSlower();
                } else {
                    audioRepository.gameInvalid();
                }

                lblTimeScale.setText(gameSettings.timeLocale[gameSettings.currentTime], gfxRepository.txtItalSubtitle, gfxRepository.clrText);

                if (gameSettings.gameIsPaused) { //if the game is paused, load the pause bar
                    lblTimeScale.setText("PAUSED", gfxRepository.txtItalSubtitle, gfxRepository.clrDisable);
                }
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                window.refresh();
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                window.refresh();
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                audioRepository.menuTab();
                source.setHorizontalAlignment(SwingConstants.CENTER);
                window.refresh();
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                window.refresh();
            }
        });

        XButton btnSpeedTime = new XButton(gfxRepository.rightButton, SwingConstants.LEFT);
        pnlTimer.add(btnSpeedTime);
        btnSpeedTime.setBounds(pnlTimer.getWidth() - 38, (pnlTimer.getHeight() / 2) - 19, 38, 38);
        btnSpeedTime.setVisible(true);

        btnSpeedTime.addMouseListener(new XMouseListener() {
            XButton source;

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                window.refresh();

                if (gameSettings.currentTime > 0) {
                    gameSettings.currentTime--;
                    audioRepository.gameFaster();
                } else {
                    audioRepository.gameInvalid();
                }

                lblTimeScale.setText(gameSettings.timeLocale[gameSettings.currentTime], gfxRepository.txtItalSubtitle, gfxRepository.clrText);

                if (gameSettings.gameIsPaused) { //if the game is paused, load the pause bar
                    lblTimeScale.setText("PAUSED", gfxRepository.txtItalSubtitle, gfxRepository.clrDisable);
                }
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                window.refresh();
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                window.refresh();
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                audioRepository.menuTab();
                source.setHorizontalAlignment(SwingConstants.CENTER);
                window.refresh();
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                window.refresh();
            }
        });

        lblCurrentDate = new XLabel();
        pnlTimer.add(lblCurrentDate);
        lblCurrentDate.setBounds(btnSlowTime.getX() + btnSlowTime.getWidth(), 10, btnSpeedTime.getX() - (btnSlowTime.getX() + btnSlowTime.getWidth()), pnlTimer.getHeight() - 20);
        lblCurrentDate.setText("Turn: " + gameSettings.currentDate, gfxRepository.txtSubtitle, gfxRepository.clrText);
        lblCurrentDate.setAlignments(SwingConstants.CENTER, SwingConstants.TOP);
        lblCurrentDate.setVisible(true);

        lblTimeScale = new XLabel();
        pnlTimer.add(lblTimeScale);
        lblTimeScale.setBounds(btnSlowTime.getX() + btnSlowTime.getWidth(), 10, btnSpeedTime.getX() - (btnSlowTime.getX() + btnSlowTime.getWidth()), pnlTimer.getHeight() - 20);
        lblTimeScale.setText(gameSettings.timeLocale[gameSettings.currentTime], gfxRepository.txtItalSubtitle, gfxRepository.clrText);
        lblTimeScale.setAlignments(SwingConstants.CENTER, SwingConstants.BOTTOM);
        lblTimeScale.setVisible(true);

        /*
        XButton btnPause = new XButton("test", gfxRepository.txtSubtitle, gfxRepository.clrText, gfxRepository.clrDisable);
        layers.add(btnPause, new Integer(15), 0);
        btnPause.setBounds(0, 500, 100, 60);
        btnPause.setVisible(true);

        btnPause.addMouseListener(new XMouseListener() {
            XButton source;

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                //source.setHorizontalAlignment(SwingConstants.RIGHT);
                window.refresh();

                if (gameSettings.gameIsPaused) {
                    gameSettings.gameIsPaused = false;
                    audioRepository.buttonSelect();
                } else {
                    audioRepository.buttonDisable();
                    gameSettings.gameIsPaused = true;
                }
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                //source.setHorizontalAlignment(SwingConstants.RIGHT);
                window.refresh();
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                //source.setHorizontalAlignment(SwingConstants.LEFT);
                window.refresh();
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                audioRepository.menuTab();
                //source.setHorizontalAlignment(SwingConstants.CENTER);
                window.refresh();
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                //source.setHorizontalAlignment(SwingConstants.LEFT);
                window.refresh();
            }
        });
        */

    }


    private void showPauseMenu() {

        gameSettings.gameIsPaused = true; //pauses the game

        pnlPauseMenu.removeAll();
        pauseMenuOpen = true;

        //adds the title to the pause menu
        XLabel lblMenuTitle = new XLabel("Pause Menu", gfxRepository.txtHeader, gfxRepository.clrText);
        pnlPauseMenu.add(lblMenuTitle);
        lblMenuTitle.setBounds(5, 10, pnlPauseMenu.getWidth() - 10, 40);
        lblMenuTitle.setAlignments(SwingConstants.CENTER);
        lblMenuTitle.setVisible(true);

        XButton btnQuit = new XButton();
        pnlPauseMenu.add(btnQuit);
        btnQuit.setBounds(10, pnlPauseMenu.getHeight() - 50, pnlPauseMenu.getWidth() - 20, 40);
        btnQuit.setOpaque(true);
        btnQuit.setBackground(gfxRepository.clrButtonBackground);
        btnQuit.setForeground(gfxRepository.clrText);
        btnQuit.setFont(gfxRepository.txtSubheader);
        btnQuit.setText("Quit Game");
        btnQuit.setBorder(gfxRepository.bdrButtonEnabled);
        btnQuit.setVisible(true);

        btnQuit.addMouseListener(new XMouseListener() {
            XButton source;

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                audioRepository.buttonClick();
                //source.setHorizontalAlignment(SwingConstants.RIGHT);
                window.refresh();

                if (source.getCurrentState()) {
                    audioRepository.buttonConfirm();
                    window.close();
                } else {
                    audioRepository.buttonDisable();
                    source.setText("Are you sure?");
                }

                source.toggleState();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                //source.setHorizontalAlignment(SwingConstants.RIGHT);
                window.refresh();
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                //source.setHorizontalAlignment(SwingConstants.LEFT);
                window.refresh();
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                audioRepository.menuTab();
                //source.setHorizontalAlignment(SwingConstants.CENTER);
                window.refresh();
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                //source.setHorizontalAlignment(SwingConstants.LEFT);
                window.refresh();
            }
        });

        XButton btnReturn = new XButton("Continue", gfxRepository.txtSubheader, gfxRepository.clrText, gfxRepository.clrButtonBackground, gfxRepository.bdrButtonEnabled);
        pnlPauseMenu.add(btnReturn);
        btnReturn.setBounds(10, lblMenuTitle.getY() + lblMenuTitle.getHeight() + 10, pnlPauseMenu.getWidth() - 20, 40);
        btnReturn.setOpaque(true);
        btnReturn.setVisible(true);

        btnReturn.addMouseListener(new XMouseListener() {
            XButton source;

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                audioRepository.buttonClick();
                //source.setHorizontalAlignment(SwingConstants.RIGHT);
                window.refresh();
                pauseMenuOpen = false;
                audioRepository.buttonSelect();
                pnlOverlay.setVisible(false);
                pnlPauseMenu.removeAll();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                //source.setHorizontalAlignment(SwingConstants.RIGHT);
                window.refresh();
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                //source.setHorizontalAlignment(SwingConstants.LEFT);
                window.refresh();
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                audioRepository.menuTab();
                //source.setHorizontalAlignment(SwingConstants.CENTER);
                window.refresh();
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                //source.setHorizontalAlignment(SwingConstants.LEFT);
                window.refresh();
            }
        });

        //Adds a slider to change music volume
        XLabel lblMusicVolume = new XLabel("Music Volume", gfxRepository.txtSubtitle, gfxRepository.clrText);
        pnlPauseMenu.add(lblMusicVolume);
        lblMusicVolume.setBounds(5, btnReturn.getY() + btnReturn.getHeight() + 5, pnlPauseMenu.getWidth() - 10, 25);
        lblMusicVolume.setAlignments(SwingConstants.CENTER);
        lblMusicVolume.setVisible(true);

        XSlider sldMusicVolume = new XSlider(JSlider.HORIZONTAL, 0, 100, audioRepository.musicVolume);
        pnlPauseMenu.add(sldMusicVolume);
        sldMusicVolume.setTicks(10, 2);
        sldMusicVolume.setPaintLabels(false);
        sldMusicVolume.setForeground(gfxRepository.clrText);
        sldMusicVolume.setBounds(10, lblMusicVolume.getY() + lblMusicVolume.getHeight() + 5, pnlPauseMenu.getWidth() - 20, 40);
        sldMusicVolume.setVisible(true);

        sldMusicVolume.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                JSlider source = (JSlider)changeEvent.getSource();
                if (source.getValueIsAdjusting()) {
                    audioRepository.musicVolume = source.getValue();
                    audioRepository.setMusicVolume();
                }
                window.refresh();
            }
        });

        //Adds a slider to change ambiance volume
        XLabel lblAmbianceVolume = new XLabel("Ambiance Volume", gfxRepository.txtSubtitle, gfxRepository.clrText);
        pnlPauseMenu.add(lblAmbianceVolume);
        lblAmbianceVolume.setBounds(5, sldMusicVolume.getY() + sldMusicVolume.getHeight() + 5, pnlPauseMenu.getWidth() - 10, lblMusicVolume.getHeight());
        lblAmbianceVolume.setAlignments(SwingConstants.CENTER);
        lblAmbianceVolume.setVisible(true);

        XSlider sldAmbianceVolume = new XSlider(JSlider.HORIZONTAL, 0, 100, audioRepository.ambianceVolume);
        pnlPauseMenu.add(sldAmbianceVolume);
        sldAmbianceVolume.setTicks(10, 2);
        sldAmbianceVolume.setPaintLabels(false);
        sldAmbianceVolume.setForeground(gfxRepository.clrText);
        sldAmbianceVolume.setBounds(10, lblAmbianceVolume.getY() + lblAmbianceVolume.getHeight() + 5, pnlPauseMenu.getWidth() - 20, sldMusicVolume.getHeight());
        sldAmbianceVolume.setVisible(true);

        sldAmbianceVolume.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                JSlider source = (JSlider)changeEvent.getSource();
                if (source.getValueIsAdjusting()) {
                    audioRepository.ambianceVolume = source.getValue();
                    audioRepository.setAmbianceVolume();
                }
                window.refresh();
            }
        });

        XLabel lblUIVolume = new XLabel("Interface Volume", gfxRepository.txtSubtitle, gfxRepository.clrText);
        pnlPauseMenu.add(lblUIVolume);
        lblUIVolume.setBounds(5, sldAmbianceVolume.getY() + sldMusicVolume.getHeight() + 5, pnlPauseMenu.getWidth() - 10, lblAmbianceVolume.getHeight());
        lblUIVolume.setAlignments(SwingConstants.CENTER);
        lblUIVolume.setVisible(true);

        XSlider sldUIVolume = new XSlider(JSlider.HORIZONTAL, 0, 100, audioRepository.uiVolume);
        pnlPauseMenu.add(sldUIVolume);
        sldUIVolume.setTicks(10, 2);
        sldUIVolume.setPaintLabels(false);
        sldUIVolume.setForeground(gfxRepository.clrText);
        sldUIVolume.setBounds(10, lblUIVolume.getY() + lblUIVolume.getHeight() + 5, pnlPauseMenu.getWidth() - 20, sldMusicVolume.getHeight());
        sldUIVolume.setVisible(true);

        sldUIVolume.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                JSlider source = (JSlider)changeEvent.getSource();
                if (source.getValueIsAdjusting()) {
                    audioRepository.uiVolume = source.getValue();
                    audioRepository.setAmbianceVolume();
                }
                window.refresh();
            }
        });


        pnlOverlay.setVisible(true);

    }


    /** System view **/
    //Handles the UI specific to the system view. Namely, the display of planet data and the star system itself.

    public void showSystemView(int x, int y) {

        clearUI();

        layers.add(celestialObject, 14);
        celestialObject.setBounds((screen.getWidth() / 2) - (celestialObject.getWidth() / 2), (screen.getHeight() / 2) - (celestialObject.getHeight() / 2), celestialObject.getWidth(), celestialObject.getHeight());

        int planetPosition = 0;

        ArrayList<planetButton> planet = new ArrayList<>();

        starClass star = gameSettings.map.mapTiles.get(y).get(x).getStarData();
        currentStar = gameSettings.map.mapTiles.get(y).get(x).getStarData();

        bgPanel = new XLabel(gfxRepository.mainBackground);
        layers.add(bgPanel, new Integer(0), 0);
        bgPanel.setBounds(0, 0, screen.getWidth(), screen.getHeight());
        bgPanel.setVisible(true);

        XLabel imgSystemName = new XLabel(gfxRepository.systemTitle);
        layers.add(imgSystemName, new Integer(12), 0);
        imgSystemName.setBounds((screen.getWidth() / 2) - 220, screen.getHeight() - 80, 440, 60);
        imgSystemName.setVisible(true);

        //System.out.println(x + "|" + y + " - " + gameSettings.map.mapTiles.get(y).get(x).getStarData().getStarName());

        XLabel lblSystemName = new XLabel(star.getStarName() + " System", gfxRepository.txtButtonSmall, gfxRepository.clrText);
        layers.add(lblSystemName, new Integer(14), 0);
        lblSystemName.setBounds(imgSystemName.getBounds());
        lblSystemName.setAlignments(SwingConstants.CENTER);
        lblSystemName.setVisible(true);

        XButton btnGalaxy = new XButton(gfxRepository.galaxyReturn, SwingConstants.LEFT);
        imgSystemName.add(btnGalaxy);
        btnGalaxy.setBounds(0, 0, 60, 60);
        btnGalaxy.setToolTipText("Return to Galaxy View");
        btnGalaxy.setVisible(true);

        btnGalaxy.addMouseListener(new XMouseListener() {
            XButton source;

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                audioRepository.buttonClick();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                window.refresh();

                loadMapView();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                window.refresh();
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                window.refresh();
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                audioRepository.menuTab();
                source.setHorizontalAlignment(SwingConstants.CENTER);
                window.refresh();
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                window.refresh();
            }
        });

        //lol reusing code from galaxy map

        pnlBG = new XPanel(gfxRepository.clrInvisible) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(5000, 5000);
            } // Y U G E
        } ;
        layers.add(pnlBG, new Integer(2), 0);
        pnlBG.setBounds(0, 0, 5000, 5000);
        pnlBG.setAutoscrolls(true);
        pnlBG.setVisible(true);

        XScrollPane mapView = new XScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        layers.add(mapView, new Integer(3), 0);
        mapView.setViewportView(pnlBG);
        mapView.setBounds(0, 0, window.getWidth(), window.getHeight());
        mapView.setVisible(true);

        MouseAdapter mapScroller = new MouseAdapter() { //Taken from - https://stackoverflow.com/questions/31171502/scroll-jscrollpane-by-dragging-mouse-java-swing

            private Point origin;

            @Override
            public void mousePressed(MouseEvent e) {
                origin = new Point(e.getPoint());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (origin != null) {
                    JViewport viewPort = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, pnlBG);
                    if (viewPort != null) {
                        int deltaX = origin.x - e.getX();
                        int deltaY = origin.y - e.getY();

                        Rectangle view = viewPort.getViewRect();
                        view.x += deltaX;
                        view.y += deltaY;

                        pnlBG.scrollRectToVisible(view);
                    }
                }
            }

        };

        pnlBG.addMouseListener(mapScroller);
        pnlBG.addMouseMotionListener(mapScroller);

        if (star.isBinarySystem()) { //system has binary stars, show accordingly
            XLabel imgStar = new XLabel();
            pnlBG.add(imgStar);
            imgStar.setBounds((pnlBG.getWidth() / 2) - 300, (pnlBG.getHeight() / 2) - 150, 300, 300);
            imgStar.scaleImage(star.getIconGFX());
            imgStar.setVisible(true);

            XLabel imgStar2 = new XLabel();
            pnlBG.add(imgStar2);
            imgStar2.setBounds((pnlBG.getWidth() / 2), (pnlBG.getHeight() / 2) - 150, 300, 300);
            imgStar2.scaleImage(star.getIconGFX());
            imgStar2.setVisible(true);

            //visual for the orbit of the binary stars
            XLabel binaryOrbit = new XLabel();
            pnlBG.add(binaryOrbit);
            binaryOrbit.setBounds((pnlBG.getWidth() / 2) - 150, (pnlBG.getHeight() / 2) - 150, 300, 300);
            binaryOrbit.scaleImage(gfxRepository.orbitIndicator);
            binaryOrbit.setVisible(true);

        } else { //not binary - just one star
            XLabel imgStar = new XLabel();
            pnlBG.add(imgStar);
            imgStar.setBounds((pnlBG.getWidth() / 2) - 150, (pnlBG.getHeight() / 2) - 150, 300, 300);
            imgStar.scaleImage(star.getIconGFX());
            imgStar.setVisible(true);

        }

        for (int i = 0; i < star.planetList.size(); i++) {

            int size = 0;

            Random r = new Random();

            XLabel imgPlanet = new XLabel();
            pnlBG.add(imgPlanet);
            size = (6 * star.planetList.get(i).getPlanetRadius());
            imgPlanet.setBounds(((pnlBG.getWidth() / 2) + 250) + planetPosition, (pnlBG.getHeight() / 2) - (size/2), size, size);
            star.planetList.get(i).setSystemPosX(imgPlanet.getX() + (imgPlanet.getWidth() / 2));
            star.planetList.get(i).setSystemPosY(imgPlanet.getY() + (imgPlanet.getHeight() / 2));
            imgPlanet.scaleImage(planetCore.listOfPlanets.get(star.planetList.get(i).getArrayLoc()).getPlanetIcon());
            imgPlanet.setVisible(true);

            if (star.planetList.get(i).isHomePlanet()) {
                XLabel imgHomePlanet = new XLabel(gfxRepository.homePlanet);
                pnlBG.add(imgHomePlanet);
                imgHomePlanet.setBounds(imgPlanet.getX(), imgPlanet.getY() + imgPlanet.getHeight(), 32, 32);
                imgHomePlanet.setVisible(true);
                imgHomePlanet.setToolTipText("Capital");
            } else if (star.planetList.get(i).getPlanetColony() != null) {
                XLabel imgColony = new XLabel(gfxRepository.colonyIcon);
                pnlBG.add(imgColony);
                imgColony.setBounds(imgPlanet.getX(), imgPlanet.getY() + imgPlanet.getHeight(), 32, 25);
                imgColony.setVisible(true);
                imgColony.setToolTipText("Colony");
            }

            planet.add(new planetButton(star, i));
            imgPlanet.add(planet.get(i));
            planet.get(i).setBounds(0, 0, imgPlanet.getWidth(), imgPlanet.getHeight());
            planet.get(i).setPlanet(star.planetList.get(i));
            planet.get(i).setVisible(true);

            planet.get(i).addMouseListener(new XMouseListener() {
                planetButton source;
                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    source = (planetButton)mouseEvent.getSource();
                    audioRepository.buttonClick();
                    //source.setHorizontalAlignment(SwingConstants.RIGHT);
                    celestialObject.showPlanet(source.getPlanet());
                    window.refresh();
                }

                @Override
                public void mousePressed(MouseEvent mouseEvent) {
                    source = (planetButton)mouseEvent.getSource();
                    //source.setHorizontalAlignment(SwingConstants.RIGHT);
                    window.refresh();
                }

                @Override
                public void mouseReleased(MouseEvent mouseEvent) {
                    source = (planetButton)mouseEvent.getSource();
                    //source.setHorizontalAlignment(SwingConstants.LEFT);
                    window.refresh();
                }

                @Override
                public void mouseEntered(MouseEvent mouseEvent) {
                    source = (planetButton)mouseEvent.getSource();
                    //source.setHorizontalAlignment(SwingConstants.CENTER);
                    window.refresh();
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                    source = (planetButton)mouseEvent.getSource();
                    //source.setHorizontalAlignment(SwingConstants.LEFT);
                    source.removeAll();
                    window.refresh();
                }
            });

            planetPosition = planetPosition + (150 + r.nextInt(220)); //TODO: Should eventually weight a little better....

        }

        for (int i = 0; i < currentStar.shipsInSystem.size(); i++) {
            pnlBG.add(currentStar.shipsInSystem.get(i).getShipInterface());
            currentStar.shipsInSystem.get(i).getShipInterface().setBounds(currentStar.shipsInSystem.get(i).getShipData().getSystemX(), currentStar.shipsInSystem.get(i).getShipData().getSystemY(), currentStar.shipsInSystem.get(i).getShipInterface().getWidth(), currentStar.shipsInSystem.get(i).getShipInterface().getHeight());
            currentStar.shipsInSystem.get(i).getShipInterface().setVisible(true);
        }

        XLabel imgSystemOutline = new XLabel(gfxRepository.systemOutline);
        pnlBG.add(imgSystemOutline);
        imgSystemOutline.setBounds((pnlBG.getWidth() / 2) - (planetPosition + 500), (pnlBG.getHeight() / 2) - (planetPosition + 500), (planetPosition + 500) * 2, (planetPosition + 500) * 2);
        imgSystemOutline.scaleImage(gfxRepository.systemOutline);
        imgSystemOutline.setVisible(true);

        //sets the viewport on the center of the map
        Rectangle mapViewSize = mapView.getViewport().getViewRect();
        Dimension mapSize = mapView.getViewport().getViewSize();
        mapView.getViewport().setViewPosition(new Point(((mapSize.width - mapViewSize.width) / 2) - (screen.getWidth() / 2), ((mapSize.height - mapViewSize.height) / 2) - (screen.getHeight() / 2)));

        pnlPlanetData = new XPanel();
        layers.add(pnlPlanetData, new Integer(10), 0);
        pnlPlanetData.setBounds((screen.getWidth() / 2) - 400, 100, 800, screen.getHeight() - 200);
        pnlPlanetData.setVisible(false);

        loadPlayerBar();

        window.refresh();

    }

    private void loadShipBuilder() { //builds ships at the specified colony
        layers.add(pnlShipBuilder, new Integer(12), 0);
        pnlShipBuilder.setBounds(pnlPlanetData.getX() - 200, pnlPlanetData.getY() + 200, 190, pnlPlanetData.getHeight() - 200);
        pnlShipBuilder.setVisible(true);

        XLabel imgShipBuilder = new XLabel();
        pnlShipBuilder.add(imgShipBuilder);
        imgShipBuilder.setBounds(0, 0, pnlShipBuilder.getWidth(), pnlShipBuilder.getHeight());
        imgShipBuilder.scaleImage(gfxRepository.menuBackground);
        imgShipBuilder.setVisible(true);

        XLabel lblBuilderTitle = new XLabel("Orbital Shipyard", gfxRepository.txtSubheader, gfxRepository.clrText);
        imgShipBuilder.add(lblBuilderTitle);
        lblBuilderTitle.setBounds(0, 0, imgShipBuilder.getWidth(), 30);
        lblBuilderTitle.setAlignments(SwingConstants.CENTER);
        lblBuilderTitle.setVisible(true);

        XPanel pnlViewer = new XPanel();
        imgShipBuilder.add(pnlViewer);
        pnlViewer.setBounds(5, 30, imgShipBuilder.getWidth() - 10, imgShipBuilder.getHeight() - 50);
        pnlViewer.setPreferredSize(new Dimension(pnlViewer.getWidth(), pnlViewer.getHeight()));
        pnlViewer.setBackground(gfxRepository.clrBlkTransparent);
        pnlViewer.setOpaque(true);
        pnlViewer.setVisible(true);

        XScrollPane scrBuilder = new XScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        imgShipBuilder.add(scrBuilder);
        scrBuilder.setBounds(5, 20, imgShipBuilder.getWidth() - 10, imgShipBuilder.getHeight() - 25);
        scrBuilder.setViewportView(pnlViewer);
        scrBuilder.setViewportBorder(null);
        scrBuilder.setVisible(true);

        XListSorter srtShips = new XListSorter(XConstants.VERTICAL_SORT, 5, 0, 0);

        for (int i = 0; i < gameSettings.shipbuilder.shipStorage.size(); i++) {

            if (gameSettings.shipbuilder.shipStorage.get(i).isUnlocked()) { //check whether or not ship is available for building

                XPanel pnlShip = new XPanel();
                pnlShip.setPreferredSize(new Dimension(pnlViewer.getWidth(), 60));
                pnlShip.setSize(pnlShip.getPreferredSize());
                pnlShip.setOpaque(false);
                pnlShip.setVisible(true);

                XLabel imgMinerals = new XLabel(gfxRepository.mineralsIconSmall);
                pnlShip.add(imgMinerals);
                imgMinerals.setBounds(pnlShip.getWidth() - 18, pnlShip.getHeight() - 18, 18, 18);
                imgMinerals.setVisible(true);

                XLabel lblMinerals = new XLabel(gameSettings.shipbuilder.shipStorage.get(i).getBuildCost() + " ", gfxRepository.txtSubtitle, gfxRepository.clrText);
                pnlShip.add(lblMinerals);
                lblMinerals.setBounds(imgMinerals.getX() - 100, imgMinerals.getY(), 100, imgMinerals.getHeight());
                lblMinerals.setAlignments(SwingConstants.RIGHT, SwingConstants.CENTER);
                lblMinerals.setVisible(true);

                XLabel lblShipName = new XLabel(gameSettings.shipbuilder.shipStorage.get(i).getCraftName(), gfxRepository.txtSubtitle, gfxRepository.clrText);
                pnlShip.add(lblShipName);
                lblShipName.setBounds(0, 0, pnlShip.getWidth(), 25);
                lblShipName.setAlignments(SwingConstants.LEFT, SwingConstants.TOP);
                lblShipName.setVisible(true);

                XButton btnBuildShip = new XButton(gfxRepository.techHighlight, SwingConstants.LEFT);
                pnlShip.add(btnBuildShip);
                btnBuildShip.setBounds(0, 0, pnlShip.getWidth(), pnlShip.getHeight());
                btnBuildShip.scaleImage(gfxRepository.techHighlight, XConstants.SCALE_FULL);
                btnBuildShip.setVisible(true);
                btnBuildShip.addMouseListener(new XMouseListener(i) {
                    XButton source;
                    @Override
                    public void mouseClicked(MouseEvent mouseEvent) {
                        source = (XButton)mouseEvent.getSource();
                        source.setHorizontalAlignment(SwingConstants.RIGHT);
                        window.refresh();

                        try { //build a ship if the player has enough resources to do so
                            if (gameSettings.player.getResources() >= gameSettings.shipbuilder.shipStorage.get(getIdentifier()).getBuildCost()) {
                                gameSettings.player.setResources(gameSettings.player.getResources() - gameSettings.shipbuilder.shipStorage.get(getIdentifier()).getBuildCost());
                                audioRepository.constructShip();
                                SpaceCraft newCraft = new SpaceCraft(gameSettings.shipbuilder.shipStorage.get(getIdentifier())); //build new ship
                                newCraft.setCurrentSystem(currentStar);
                                newCraft.startConstruction();
                                newCraft.setSystemLocation(currentPlanet.getSystemPosX(), currentPlanet.getSystemPosY());
                                currentStar.shipsInSystem.add(newCraft);
                            } else {
                                audioRepository.buttonDisable();
                            }
                        } catch (NullPointerException e) {
                            //o no ship doesn't exist wat do
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void mousePressed(MouseEvent mouseEvent) {
                        source = (XButton)mouseEvent.getSource();
                        source.setHorizontalAlignment(SwingConstants.RIGHT);
                        window.refresh();
                    }

                    @Override
                    public void mouseReleased(MouseEvent mouseEvent) {
                        source = (XButton)mouseEvent.getSource();
                        source.setHorizontalAlignment(SwingConstants.LEFT);
                        window.refresh();
                    }

                    @Override
                    public void mouseEntered(MouseEvent mouseEvent) {
                        source = (XButton)mouseEvent.getSource();
                        source.setHorizontalAlignment(SwingConstants.CENTER);
                        audioRepository.menuTab();
                        window.refresh();
                    }

                    @Override
                    public void mouseExited(MouseEvent mouseEvent) {
                        source = (XButton)mouseEvent.getSource();
                        source.setHorizontalAlignment(SwingConstants.LEFT);
                        window.refresh();
                    }
                });

                srtShips.addItem(pnlShip);
            }
        }

        srtShips.placeItems(pnlViewer);


    }


    /** Tech Tree Window **/
    //Displays the tech tree screen and elements.

    private void showTechTree() {
        //clear and reload the tech tree panel
        layers.add(pnlTechTree, new Integer(14), 0);
        pnlTechTree.setBounds(0, pnlTopBar.getHeight() + 38, 452, 520);
        pnlTechTree.removeAll();
        pnlTechTree.setVisible(true);

        XLabel imgTechBG = new XLabel();
        pnlTechTree.add(imgTechBG);
        imgTechBG.setBounds(0, 0, pnlTechTree.getWidth(), pnlTechTree.getHeight());
        imgTechBG.scaleImage(gfxRepository.techBackground);
        imgTechBG.setVisible(true);

        XLabel lblTechTitle = new XLabel("Research Overview", gfxRepository.txtSubheader, gfxRepository.clrText);
        imgTechBG.add(lblTechTitle);
        lblTechTitle.setBounds(5, 5, imgTechBG.getWidth() - 20, 40);
        lblTechTitle.setAlignments(SwingConstants.LEFT, SwingConstants.TOP);
        lblTechTitle.setVisible(true);

        XLabel lblLine = new XLabel(gfxRepository.line_316); //line decoration under the header text
        imgTechBG.add(lblLine);
        lblLine.setBounds(5, 20, 316, 17);
        lblLine.setVisible(true);

        //sets up panel for the first tech
        XPanel tech_1 = new XPanel();
        tech_1.setOpaque(false);
        imgTechBG.add(tech_1);
        tech_1.setBounds(0, 50, 452, 110);
        XLabel tech_1_header = new XLabel(gfxRepository.greyHeader);
        tech_1_header.setOpaque(false);
        tech_1.add(tech_1_header);
        tech_1_header.setBounds(0, 0, 452, 25);
        tech_1_header.setAlignments(SwingConstants.CENTER);
        XLabel tech_1_header_text = new XLabel();
        tech_1_header.add(tech_1_header_text);
        tech_1_header_text.setAlignments(SwingConstants.LEFT, SwingConstants.CENTER);
        tech_1_header_text.setBounds(5, 0, tech_1_header.getWidth() - 20, tech_1_header.getHeight());
        XLabel tech_1_main = new XLabel();
        tech_1.add(tech_1_main);
        tech_1_main.setBounds(0, 25, 452, 85);
        XLabel tech_1_name = new XLabel();
        tech_1_main.add(tech_1_name);
        tech_1_name.setBounds(5, 1, tech_1_main.getWidth() - 10, 30);
        tech_1_name.setAlignments(SwingConstants.LEFT, SwingConstants.TOP);
        XButton tech_1_button = new XButton(gfxRepository.techButton, SwingConstants.LEFT);
        tech_1_main.add(tech_1_button);
        tech_1_button.setBounds(-13, -5, 478, 110);
        tech_1_button.addMouseListener(new XMouseListener() {
            XButton source;
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                audioRepository.buttonConfirm();
                window.refresh();
                if (!pnlTechSelect.isVisible()) {
                    selectResearch(1);
                } else {
                    pnlTechSelect.removeAll();
                    pnlTechSelect.setVisible(false);
                }
            }
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                window.refresh();
            }
            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                window.refresh();
            }
            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.CENTER);
                audioRepository.menuTab();
                window.refresh();
            }
            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                window.refresh();
            }
        });

        if (gameSettings.techtree.currentResearch_1 != null) {
            tech_1_header.setIcon(new ImageIcon(gfxRepository.techBlueHeader));
            tech_1_header_text.setText(gameSettings.techtree.currentResearch_1.getResearchTree(), gfxRepository.txtHeader, gfxRepository.clrText);
            tech_1_name.setText(gameSettings.techtree.currentResearch_1.getName(), gfxRepository.txtSubtitle, gfxRepository.clrText);
            XLabel tech_1_icon = new XLabel();
            tech_1_main.add(tech_1_icon);
            tech_1_icon.setBounds(70, 30, 52, 52);
            try {
            tech_1_icon.scaleImage(ImageIO.read(new File(System.getProperty("user.dir") + "/src/Core/GUI/Resources/tech/" + gameSettings.techtree.currentResearch_1.getIcon())));
            } catch (IOException e) {
                tech_1_icon.scaleImage(gfxRepository.missingIconTech);
            }
            tech_1_icon.setVisible(true);
            if (gameSettings.techtree.currentResearch_1.getRarity() < 10 && !gameSettings.techtree.currentResearch_1.isDangerous()) { //tech is rare, showcase rare colour
                tech_1_main.setIcon(new ImageIcon(gfxRepository.techPurpleBG));
                XLabel tech_1_main_mask = new XLabel(gfxRepository.techMask);
                tech_1_main.add(tech_1_main_mask);
                tech_1_main_mask.setBounds(0, 0, tech_1_main.getWidth(), tech_1_main.getHeight());
            } else if (gameSettings.techtree.currentResearch_1.isDangerous()) { //dangerous technology, display accordingly
                tech_1_main.setIcon(new ImageIcon(gfxRepository.techRedBG));
            } else { //regular tech, display regular colour
                tech_1_main.setIcon(new ImageIcon(gfxRepository.techBlueBG));
            }
        } else { //no current research
            tech_1_header.setIcon(new ImageIcon(gfxRepository.techGreyHeader));
            tech_1_header_text.setText("Physics", gfxRepository.txtButtonSmall, gfxRepository.clrText);
            tech_1_main.setIcon(new ImageIcon(gfxRepository.techGreyBG));
            tech_1_name.setText("No active research project.", gfxRepository.txtSubtitle, gfxRepository.clrText);
        }
        tech_1_button.setVisible(true);
        tech_1_name.setVisible(true);
        tech_1_header_text.setVisible(true);
        tech_1.setVisible(true);
        tech_1_header.setVisible(true);
        tech_1_main.setVisible(true);

        XPanel tech_2 = new XPanel();
        tech_2.setOpaque(false);
        imgTechBG.add(tech_2);
        tech_2.setBounds(0, tech_1.getY() + tech_1.getHeight() + 15, 452, 110);
        XLabel tech_2_header = new XLabel(gfxRepository.greyHeader);
        tech_2_header.setOpaque(false);
        tech_2.add(tech_2_header);
        tech_2_header.setBounds(0, 0, 452, 25);
        tech_2_header.setAlignments(SwingConstants.CENTER);
        XLabel tech_2_header_text = new XLabel();
        tech_2_header.add(tech_2_header_text);
        tech_2_header_text.setAlignments(SwingConstants.LEFT, SwingConstants.CENTER);
        tech_2_header_text.setBounds(5, 0, tech_2_header.getWidth() - 20, tech_2_header.getHeight());
        XLabel tech_2_main = new XLabel();
        tech_2.add(tech_2_main);
        tech_2_main.setBounds(0, 25, 452, 85);
        XLabel tech_2_name = new XLabel();
        tech_2_main.add(tech_2_name);
        tech_2_name.setBounds(5, 1, tech_2_main.getWidth() - 10, 30);
        tech_2_name.setAlignments(SwingConstants.LEFT, SwingConstants.TOP);
        XButton tech_2_button = new XButton(gfxRepository.techButton, SwingConstants.LEFT);
        tech_2_main.add(tech_2_button);
        tech_2_button.setBounds(-13, -5, 478, 110);
        tech_2_button.addMouseListener(new XMouseListener() {
            XButton source;
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                audioRepository.buttonConfirm();
                window.refresh();
                if (!pnlTechSelect.isVisible()) {
                    selectResearch(2);
                } else {
                    pnlTechSelect.removeAll();
                    pnlTechSelect.setVisible(false);
                }
            }
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                window.refresh();
            }
            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                window.refresh();
            }
            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.CENTER);
                audioRepository.menuTab();
                window.refresh();
            }
            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                window.refresh();
            }
        });

        if (gameSettings.techtree.currentResearch_2 != null) {
            tech_2_header.setIcon(new ImageIcon(gfxRepository.techGreenHeader));
            tech_2_header_text.setText(gameSettings.techtree.currentResearch_2.getResearchTree(), gfxRepository.txtHeader, gfxRepository.clrText);
            tech_2_name.setText(gameSettings.techtree.currentResearch_2.getName(), gfxRepository.txtSubtitle, gfxRepository.clrText);
            XLabel tech_2_icon = new XLabel();
            tech_2_main.add(tech_2_icon);
            tech_2_icon.setBounds(70, 30, 52, 52);
            try {
                tech_2_icon.scaleImage(ImageIO.read(new File(System.getProperty("user.dir") + "/src/Core/GUI/Resources/tech/" + gameSettings.techtree.currentResearch_2.getIcon())));
            } catch (IOException e) {
                tech_2_icon.scaleImage(gfxRepository.missingIconTech);
            }
            tech_2_icon.setVisible(true);
            if (gameSettings.techtree.currentResearch_2.getRarity() < 10 && !gameSettings.techtree.currentResearch_2.isDangerous()) { //tech is rare, showcase rare colour
                tech_2_main.setIcon(new ImageIcon(gfxRepository.techPurpleBG));
                XLabel tech_2_main_mask = new XLabel(gfxRepository.techMask);
                tech_2_main.add(tech_2_main_mask);
                tech_2_main_mask.setBounds(0, 0, tech_2_main.getWidth(), tech_2_main.getHeight());
            } else if (gameSettings.techtree.currentResearch_2.isDangerous()) { //dangerous technology, display accordingly
                tech_2_main.setIcon(new ImageIcon(gfxRepository.techRedBG));
            } else { //regular tech, display regular colour
                tech_2_main.setIcon(new ImageIcon(gfxRepository.techGreenBG));
            }
        } else { //no current research
            tech_2_header.setIcon(new ImageIcon(gfxRepository.techGreyHeader));
            tech_2_header_text.setText("Biology", gfxRepository.txtButtonSmall, gfxRepository.clrText);
            tech_2_main.setIcon(new ImageIcon(gfxRepository.techGreyBG));
            tech_2_name.setText("No active research project.", gfxRepository.txtSubtitle, gfxRepository.clrText);
        }
        tech_2_button.setVisible(true);
        tech_2_name.setVisible(true);
        tech_2_header_text.setVisible(true);
        tech_2.setVisible(true);
        tech_2_header.setVisible(true);
        tech_2_main.setVisible(true);

        XPanel tech_3 = new XPanel();
        tech_3.setOpaque(false);
        imgTechBG.add(tech_3);
        tech_3.setBounds(0, tech_2.getY() + tech_2.getHeight() + 15, 452, 110);
        XLabel tech_3_header = new XLabel(gfxRepository.greyHeader);
        tech_3_header.setOpaque(false);
        tech_3.add(tech_3_header);
        tech_3_header.setBounds(0, 0, 452, 25);
        tech_3_header.setAlignments(SwingConstants.CENTER);
        XLabel tech_3_header_text = new XLabel();
        tech_3_header.add(tech_3_header_text);
        tech_3_header_text.setAlignments(SwingConstants.LEFT, SwingConstants.CENTER);
        tech_3_header_text.setBounds(5, 0, tech_3_header.getWidth() - 20, tech_3_header.getHeight());
        XLabel tech_3_main = new XLabel();
        tech_3.add(tech_3_main);
        tech_3_main.setBounds(0, 25, 452, 85);
        XLabel tech_3_name = new XLabel();
        tech_3_main.add(tech_3_name);
        tech_3_name.setBounds(5, 1, tech_3_main.getWidth() - 10, 30);
        tech_3_name.setAlignments(SwingConstants.LEFT, SwingConstants.TOP);
        XButton tech_3_button = new XButton(gfxRepository.techButton, SwingConstants.LEFT);
        tech_3_main.add(tech_3_button);
        tech_3_button.setBounds(-13, -5, 478, 110);
        tech_3_button.addMouseListener(new XMouseListener() {
            XButton source;
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                audioRepository.buttonConfirm();
                window.refresh();
                if (!pnlTechSelect.isVisible()) {
                    selectResearch(3);
                } else {
                    pnlTechSelect.removeAll();
                    pnlTechSelect.setVisible(false);
                }
            }
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                window.refresh();
            }
            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                window.refresh();
            }
            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.CENTER);
                audioRepository.menuTab();
                window.refresh();
            }
            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                window.refresh();
            }
        });

        if (gameSettings.techtree.currentResearch_3 != null) {
            tech_3_header.setIcon(new ImageIcon(gfxRepository.techOrangeHeader));
            tech_3_header_text.setText(gameSettings.techtree.currentResearch_3.getResearchTree(), gfxRepository.txtHeader, gfxRepository.clrText);
            tech_3_name.setText(gameSettings.techtree.currentResearch_3.getName(), gfxRepository.txtSubtitle, gfxRepository.clrText);
            XLabel tech_3_icon = new XLabel();
            tech_3_main.add(tech_3_icon);
            tech_3_icon.setBounds(70, 30, 52, 52);
            try {
                tech_3_icon.scaleImage(ImageIO.read(new File(System.getProperty("user.dir") + "/src/Core/GUI/Resources/tech/" + gameSettings.techtree.currentResearch_3.getIcon())));
            } catch (IOException e) {
                tech_3_icon.scaleImage(gfxRepository.missingIconTech);
            }
            tech_3_icon.setVisible(true);
            if (gameSettings.techtree.currentResearch_3.getRarity() < 10 && !gameSettings.techtree.currentResearch_3.isDangerous()) { //tech is rare, showcase rare colour
                tech_3_main.setIcon(new ImageIcon(gfxRepository.techPurpleBG));
                XLabel tech_3_main_mask = new XLabel(gfxRepository.techMask);
                tech_3_main.add(tech_3_main_mask);
                tech_3_main_mask.setBounds(0, 0, tech_3_main.getWidth(), tech_3_main.getHeight());
            } else if (gameSettings.techtree.currentResearch_3.isDangerous()) { //dangerous technology, display accordingly
                tech_3_main.setIcon(new ImageIcon(gfxRepository.techRedBG));
            } else { //regular tech, display regular colour
                tech_3_main.setIcon(new ImageIcon(gfxRepository.techOrangeBG));
            }
        } else { //no current research
            tech_3_header.setIcon(new ImageIcon(gfxRepository.techGreyHeader));
            tech_3_header_text.setText("Engineering", gfxRepository.txtButtonSmall, gfxRepository.clrText);
            tech_3_main.setIcon(new ImageIcon(gfxRepository.techGreyBG));
            tech_3_name.setText("No active research project.", gfxRepository.txtSubtitle, gfxRepository.clrText);
        }
        tech_3_button.setVisible(true);
        tech_3_name.setVisible(true);
        tech_3_header_text.setVisible(true);
        tech_3.setVisible(true);
        tech_3_header.setVisible(true);
        tech_3_main.setVisible(true);

        window.refresh();
    }

    private void selectResearch(int tech_line) { //window to select new research from available options
        pnlTechSelect.removeAll();

        layers.add(pnlTechSelect, new Integer(14), 0);
        pnlTechSelect.setBounds(pnlTechTree.getX() + pnlTechTree.getWidth() + 10, pnlTopBar.getHeight() + 45, 500, 500);

        XLabel imgTechSelect = new XLabel(); //background image
        pnlTechSelect.add(imgTechSelect);
        imgTechSelect.setBounds(0, 0, pnlTechSelect.getWidth(), pnlTechSelect.getHeight());
        imgTechSelect.scaleImage(gfxRepository.menuBackground);
        imgTechSelect.setVisible(true);

        XLabel lblHeader = new XLabel("Choose New Research:", gfxRepository.txtButtonSmall, gfxRepository.clrText);
        imgTechSelect.add(lblHeader);
        lblHeader.setBounds(0, 0, imgTechSelect.getWidth(), 30);
        lblHeader.setAlignments(SwingConstants.CENTER);
        lblHeader.setVisible(true);

        XButton btnClose = new XButton(gfxRepository.closeButton, SwingConstants.LEFT); //closes the tech selection window
        imgTechSelect.add(btnClose);
        btnClose.setBounds(imgTechSelect.getWidth() - 38, 0, 38, 38);
        btnClose.addMouseListener(new XMouseListener() {
            XButton source;
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                audioRepository.buttonDisable();
                window.refresh();

                pnlTechSelect.setVisible(false);
                pnlTechSelect.removeAll();
            }
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                window.refresh();
            }
            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                window.refresh();
            }
            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.CENTER);
                audioRepository.menuTab();
                window.refresh();
            }
            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                window.refresh();
            }
        });
        btnClose.setVisible(true);

        XListSorter lstTech = new XListSorter(XConstants.VERTICAL_SORT, 5, 5, 40);

        for (int i = 0; i < gameSettings.techtree.techTree.size(); i++) { //searches through the tech tree for different techs

            boolean display = false; //by default, we're not displaying the currently indexed tech unless it asks for it
            //TODO: Add in generation weights.
            switch (tech_line) { //search for valid tech of the same type
                case 1:
                    if (gameSettings.techtree.techTree.get(i).getType() == techCoreV2.TECH_PROPULSION || gameSettings.techtree.techTree.get(i).getType() == techCoreV2.TECH_OPTICS || gameSettings.techtree.techTree.get(i).getType() == techCoreV2.TECH_COMPUTING) {
                        if (gameSettings.techtree.techTree.get(i).getTier() == gameSettings.player.getTechLevel()) { //make sure tech is of the correct tier before displaying
                            display = true;
                        }
                    }
                    break;
                case 2:
                    if (gameSettings.techtree.techTree.get(i).getType() == techCoreV2.TECH_AGRICULTURE || gameSettings.techtree.techTree.get(i).getType() == techCoreV2.TECH_ECOLOGY || gameSettings.techtree.techTree.get(i).getType() == techCoreV2.TECH_GENETICS || gameSettings.techtree.techTree.get(i).getType() == techCoreV2.TECH_SOCIOLOGY) {
                        if (gameSettings.techtree.techTree.get(i).getTier() == gameSettings.player.getTechLevel()) { //make sure tech is of the correct tier before displaying
                            display = true;
                        }
                    }
                    break;
                case 3:
                    if (gameSettings.techtree.techTree.get(i).getType() == techCoreV2.TECH_INFRASTRUCTURE || gameSettings.techtree.techTree.get(i).getType() == techCoreV2.TECH_KINETICS || gameSettings.techtree.techTree.get(i).getType() == techCoreV2.TECH_GEOLOGY) {
                        if (gameSettings.techtree.techTree.get(i).getTier() == gameSettings.player.getTechLevel()) { //make sure tech is of the correct tier before displaying
                            display = true;
                        }
                    }
                    break;
                default:
                    System.out.println("Error: Unknown tech line!");
                    break;
            }

            if (display) { //generating UI elements
                XPanel pnlTechOption = new XPanel();
                pnlTechOption.setPreferredSize(new Dimension(pnlTechSelect.getWidth() - 10, 110));
                pnlTechOption.setOpaque(false);
                pnlTechOption.setSize(pnlTechOption.getPreferredSize());

                XLabel imgTechIcon = new XLabel();
                pnlTechOption.add(imgTechIcon);
                imgTechIcon.setBounds( 6, 6, 52, 52);
                try { //attempt to apply the tech's image
                    imgTechIcon.scaleImage(ImageIO.read(new File(System.getProperty("user.dir") + "/src/Core/GUI/Resources/tech/" + gameSettings.techtree.techTree.get(i).getIcon())));
                } catch (IOException e) {
                    imgTechIcon.scaleImage(gfxRepository.missingIconTech);
                }
                imgTechIcon.setVisible(true);

                XLabel lblTechTitle = new XLabel("[" + gameSettings.techtree.techTree.get(i).getResearchTree() + "] " + gameSettings.techtree.techTree.get(i).getName(), gfxRepository.txtSubtitle, gfxRepository.clrText);
                pnlTechOption.add(lblTechTitle);
                lblTechTitle.setBounds(imgTechIcon.getX() + imgTechIcon.getWidth() + 6, 0, pnlTechOption.getWidth() - imgTechIcon.getWidth() - 12, 20);
                lblTechTitle.setAlignments(SwingConstants.LEFT, SwingConstants.CENTER);
                lblTechTitle.setVisible(true);

                XLabel lblTechDesc = new XLabel();
                lblTechDesc.setFont(gfxRepository.txtMicro);
                lblTechDesc.setForeground(gfxRepository.clrText);
                lblTechDesc.setAlignments(SwingConstants.LEFT, SwingConstants.TOP);
                lblTechDesc.setOpaque(false);
                lblTechDesc.setText("<html>" + gameSettings.techtree.techTree.get(i).getDesc() + "</html>");
                pnlTechOption.add(lblTechDesc);
                lblTechDesc.setBounds(imgTechIcon.getX() + imgTechIcon.getWidth() + 6, 20, pnlTechOption.getWidth() - imgTechIcon.getWidth() - 12, pnlTechOption.getHeight() - 45);
                lblTechDesc.setVisible(true);

                XButton btnSelect = new XButton(gfxRepository.techHighlight, SwingConstants.LEFT);
                pnlTechOption.add(btnSelect);
                btnSelect.setBounds(0, 0, pnlTechOption.getWidth(), pnlTechOption.getHeight());
                btnSelect.scaleImage(gfxRepository.techHighlight, XConstants.SCALE_FULL);
                btnSelect.setIdentifier(i);
                if (gameSettings.techtree.currentResearch_1 == gameSettings.techtree.techTree.get(i)) { //if this is the current tech, show it accordingly
                    btnSelect.toggleState();
                    btnSelect.setHorizontalAlignment(SwingConstants.RIGHT);
                }
                if (!btnSelect.getCurrentState()) { //if this isn't the currently selected tech, enable the button's listener
                    btnSelect.addMouseListener(new XMouseListener(tech_line) {
                        XButton source;
                        @Override
                        public void mouseClicked(MouseEvent mouseEvent) {
                            source = (XButton) mouseEvent.getSource();
                            source.setHorizontalAlignment(SwingConstants.RIGHT);
                            audioRepository.startResearch();
                            window.refresh();

                            switch (this.getIdentifier()) {
                                case 1:
                                    gameSettings.techtree.currentResearch_1 = gameSettings.techtree.techTree.get(source.getIdentifier());
                                    break;
                                case 2:
                                    gameSettings.techtree.currentResearch_2 = gameSettings.techtree.techTree.get(source.getIdentifier());
                                    break;
                                case 3:
                                    gameSettings.techtree.currentResearch_3 = gameSettings.techtree.techTree.get(source.getIdentifier());
                                    break;
                                default:
                                    System.out.println("What?");
                                    break;
                            }
                            pnlTechSelect.setVisible(false);
                            showTechTree();
                            pnlTechSelect.removeAll();
                        }
                        @Override
                        public void mousePressed(MouseEvent mouseEvent) {
                            source = (XButton) mouseEvent.getSource();
                            source.setHorizontalAlignment(SwingConstants.RIGHT);
                            window.refresh();
                        }
                        @Override
                        public void mouseReleased(MouseEvent mouseEvent) {
                            source = (XButton) mouseEvent.getSource();
                            source.setHorizontalAlignment(SwingConstants.LEFT);
                            window.refresh();
                        }
                        @Override
                        public void mouseEntered(MouseEvent mouseEvent) {
                            source = (XButton) mouseEvent.getSource();
                            source.setHorizontalAlignment(SwingConstants.CENTER);
                            audioRepository.menuTab();
                            window.refresh();
                        }
                        @Override
                        public void mouseExited(MouseEvent mouseEvent) {
                            source = (XButton) mouseEvent.getSource();
                            source.setHorizontalAlignment(SwingConstants.LEFT);
                            window.refresh();
                        }
                    });
                } else { //if tech is selected already, show accordingly
                    XLabel lblSelected = new XLabel("ACTIVE", gfxRepository.txtButtonLarge, gfxRepository.clrTextTranslucent);
                    pnlTechOption.add(lblSelected);
                    lblSelected.setAlignments(SwingConstants.CENTER);
                    lblSelected.setBounds(0, 0, pnlTechOption.getWidth(), pnlTechOption.getHeight());
                    lblSelected.setVisible(true);
                }
                btnSelect.setVisible(true);

                JProgressBar barProgress = new JProgressBar(0, gameSettings.techtree.techTree.get(i).getCost());
                pnlTechOption.add(barProgress);
                barProgress.setBounds(6, pnlTechOption.getHeight() - 20, pnlTechOption.getWidth() - 12, 16);
                barProgress.setValue((int)gameSettings.techtree.techTree.get(i).getProgress());
                barProgress.setForeground(gfxRepository.clrEnable);
                barProgress.setBackground(gfxRepository.clrBGOpaque);
                barProgress.setBorderPainted(false);
                barProgress.setFont(gfxRepository.txtTiny);
                barProgress.setString(uiFormat.format(gameSettings.techtree.techTree.get(i).getProgress()) + " / " + gameSettings.techtree.techTree.get(i).getCost());
                barProgress.setStringPainted(true);
                barProgress.setVisible(true);

                lstTech.addItem(pnlTechOption); //add the tech display to the list sorter
            }

        }
        lstTech.placeItems(imgTechSelect);

        pnlTechSelect.setVisible(true);
    }

    /** Turn ticker **/
    //Handles the refreshing of elements during the turn ticker.

    public void turnTick() { //refreshes the UI elements that need it when the turn ticks up
        lblCurrentDate.setText("Turn " + gameSettings.currentDate, gfxRepository.txtSubtitle, gfxRepository.clrText);
        lblTimeScale.setText(gameSettings.timeLocale[gameSettings.currentTime], gfxRepository.txtItalSubtitle, gfxRepository.clrText);

        if (gameSettings.gameIsPaused) { //if the game is paused, load the pause bar
            lblPauseBar.setVisible(true);
            imgPauseBar.setVisible(true);
            lblTimeScale.setText("PAUSED", gfxRepository.txtItalSubtitle, gfxRepository.clrDisable);

        } else {
            lblPauseBar.setVisible(false);
            imgPauseBar.setVisible(false);
        }

        //TODO: Finish allowing dynamic display of elements.

        //if either the star panel or planet panel are visible, refresh the text in them when the ticker runs
        if (pnlStarData != null && pnlStarData.isVisible()) {

        } else if (pnlPlanetData != null && pnlPlanetData.isVisible() && currentPlanet != null) {
            tmgPlanetEnergy.getText().setText(" : " + uiFormat.format(currentPlanet.getPlanetColony().getTaxProduction()));
            tmgPlanetResearch.getText().setText(" : " + uiFormat.format(currentPlanet.getPlanetColony().getResearchProduction()));
            tmgPlanetResources.getText().setText(" : " + uiFormat.format(currentPlanet.getPlanetColony().getResourceProduction()));
            tmgUnrest.getText().setText(" : " + uiFormat.format(currentPlanet.getPlanetColony().getUnrest()));
            tmgFood.getText().setText(" : " + uiFormat.format(currentPlanet.getPlanetColony().getCurrentFood()));
            tmgPop.getText().setText(" : " + currentPlanet.getPlanetColony().getPopulation());
            tmgPlanetMinerals.getText().setText(" : " + uiFormat.format(currentPlanet.getResources()));

            pnlPlanetData.revalidate();
            pnlPlanetData.repaint();
        }

        tmgTech.getText().setText(": " + uiFormat.format(gameSettings.player.getResearchTurn()) + "/mo");
        tmgEnergy.getText().setText(": " + uiFormat.format(gameSettings.player.getFunds()) + " (" + uiFormat.format(gameSettings.player.getCurrencyTurn()) + "/mo)");
        tmgMinerals.getText().setText(": " + uiFormat.format(gameSettings.player.getResources()) + " (" + uiFormat.format(gameSettings.player.getResourcesTurn()) + "/mo)");

        pnlTopBar.revalidate();
        pnlTopBar.repaint();


        window.refresh();
    }


}
