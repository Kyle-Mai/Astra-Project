package Core.GUI;

//import all relevant stuff
import Core.*;
import Core.GUI.SwingEX.*;
import Core.Player.playerData;
import Core.SFX.audioRepository;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

/**
 KM
 May 16 2017
 Fourth iteration of my UI core.
 Handles the creation and display of all UI elements, as well as the general event calls.

 SOURCES:
 Stack Overflow - Gif handling syntax, idea/syntax for creating layered panes, syntax for declaring new fonts.
 Java API - Multithreading syntax, handling of the loading bar's work.
 Self - All non-cited work.
 */


public class guiCoreV4 {

    /** Stores resource declarations **/

    private ArrayList<XPanel> pnlExpansions = new ArrayList<>();
    private ArrayList<XLabel> lblExpansions = new ArrayList<>();
    private ArrayList<XLabel> lblExpanDesc = new ArrayList<>();
    private ArrayList<XButton> btnExpanEnable = new ArrayList<>();
    private ArrayList<XLabel> lblExpanID = new ArrayList<>();
    private XPanel pnlExpansionHeader;
    private XLabel lblExpHeaderText;

    private ArrayList<XPanel> pnlMods = new ArrayList<>();
    private ArrayList<XLabel> lblMods = new ArrayList<>();
    private ArrayList<XLabel> lblModAuthor = new ArrayList<>();
    private ArrayList<XButton> btnModEnable = new ArrayList<>();
    private ArrayList<addModAL> modEnabler = new ArrayList<>();
    private XPanel pnlModHeader;
    private XLabel lblModHeaderText;

    private Dimension monitorSize = Toolkit.getDefaultToolkit().getScreenSize(); //gets the screen size of the user
    private double screenWidth = monitorSize.getWidth();
    private double screenHeight = monitorSize.getHeight();
    private XFrame window;
    private JLayeredPane layers; //sorts the layers of the screen
    private XPanel screen;
    private XPanel contentController;
    private XScrollPane contentList;
    private JProgressBar loadingBar;
    private XLabel bgPanel;
    private XLabel imgLogo;
    private XPanel pnlPauseMenu;
    private XPanel pnlOverlay;
    private int screenScaleChoice = 8;
    private XPanel settingsMenu;
    private XButton btnNewGame;
    private animCore menuSpaceport;
    private animCore menuMoon1;
    private animCore menuMoon2;
    private XButton btnQuit;
    private XPanel pnlStarData;
    private XButton btnExit;

    //TODO: Work on converting as many as possible to local variables.

    private int launcherContentLoaded = 0;

    playerData playerInfo = new playerData();

    private int load;

    private backgroundLoader loader;

    /** UI scaling code**/
    //handles the scaling of the UI

    private Dimension screenSize;

    //methods to return the UI elements
    public int getUIScaleX() { return (int)screenSize.getWidth(); }

    public int getUIScaleY() { return (int)screenSize.getHeight(); }

    //sets the window size to the chosen monitor scale
    public void rescaleScreen(int option) {

        Dimension old = screenSize;

        switch(option) { //TODO: Finish enum conversion, possible change to make more efficient
            //Widescreen monitors
            case 1: //4K
                screenSize = screenScale.W_4KHD.size();
                break;
            case 2: //2K
                screenSize = screenScale.W_2KHD.size();
                break;
            case 3: //1K
                screenSize = screenScale.W_1KHD.size();
                break;
            case 4:
                screenSize = screenScale.W_HD.size();
                break;
            case 5:
                screenSize = screenScale.U_SXGA.size();
                break;
            case 6:
                screenSize = screenScale.U_WSXGAP.size();
                break;
            //4:3 and similar monitor scales
            case 7:
                screenSize = screenScale.U_WUXGA.size();
                break;
            case 8:
                screenSize = screenScale.U_WXGAP.size();
                break;
            case 9:

                break;
            case 10:

                break;
            case 11: //Launcher UI scale
                screenSize = screenScale.LAUNCHER.size();
                break;
            default: //safety net

                break;
        }

        if (this.getUIScaleX() > screenWidth || this.getUIScaleY() > screenHeight) {
            System.out.println("Monitor is not large enough to support this resolution.");
            screenSize = old;
        } else {
            //no incompatibilities, proceed
            System.out.println("UI successfully rescaled to " + getUIScaleX() + "x" + getUIScaleY() + ".");
        }

    }

    /** Main Constructor **/

    //builds the core framework
    public guiCoreV4(int screenScaleOption) {

        window = new XFrame("Astra Launcher", gfxRepository.gameLogo);
        screen = new XPanel(gfxRepository.clrBlk);

        rescaleScreen(screenScaleOption);

        screen.setVisible(true);
        window.setContentPane(screen);
        window.pack();
        window.setBounds((int)(screenWidth / 2) - (this.getUIScaleX() / 2), (int)(screenHeight / 2) - (this.getUIScaleY() / 2), this.getUIScaleX(), this.getUIScaleY());
        screen.setBounds(0, 0, getUIScaleX(), getUIScaleY());
        window.setVisible(true);

    }

    /** Screen Builders **/

    private void clearUI() {
        //clears the content off of the UI
        screen.removeAll();
        layers.removeAll(); //clean the layer slate
        window.getContentPane().add(layers);
    }

    private void closeProgram() {
        window.dispose(); //ensure the thread dies
        System.exit(0); //close the program
    }

    public void loadLauncherScreen() {
        System.out.println("Loading launcher data...");
        XLabel imgBackground, imgBorder;

        //initialize the layers
        layers = new JLayeredPane();
        layers.setBounds(0, 0, getUIScaleX(), getUIScaleY());
        //add the layered window to the content pane
        window.getContentPane().add(layers);

        //attempt to load images
        imgBackground = new XLabel(gfxRepository.mainBackground, gfxRepository.clrTrueBlack);

        imgLogo = new XLabel(gfxRepository.gameLogo);
        layers.add(imgBackground, new Integer(0), 0);
        layers.add(imgLogo, new Integer(9), 0);
        imgLogo.setBounds(40, 20, 50, 60);
        imgLogo.setVisible(true);
        imgBackground.setBounds(0, 0, getUIScaleX(), getUIScaleY());
        imgBackground.setVisible(true);

        imgBorder = new XLabel(gfxRepository.launcherBorder);
        layers.add(imgBorder, new Integer(1), 0);
        imgBorder.setBounds(0, 0, getUIScaleX(), getUIScaleY());
        imgBorder.setVisible(true);

        //load exit button
        btnExit = new XButton(gfxRepository.closeButton, SwingConstants.LEFT);

        layers.add(btnExit, new Integer(2), 0);
        btnExit.setBounds(getUIScaleX() - 51, 6, 38, 38);
        btnExit.setOpaque(false);

        btnExit.addMouseListener(new MouseListener() {
            XButton source;

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                System.out.println("Killing program.");
                audioRepository.buttonClick();
                closeProgram();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.CENTER);
                audioRepository.menuTab();
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
            }
        });

        btnExit.setVisible(true);

        //load minimize button
        XButton btnLauncherAudio = new XButton(gfxRepository.audioButton, SwingConstants.LEFT);

        layers.add(btnLauncherAudio, new Integer(2), 0);
        btnLauncherAudio.setBounds(btnExit.getX() - btnExit.getWidth(), btnExit.getY(), btnExit.getWidth(), btnExit.getHeight());
        btnLauncherAudio.setOpaque(false);

        btnLauncherAudio.addMouseListener(new MouseListener() {
            XButton source;

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                source.toggleState();
                audioRepository.buttonClick();

                if (source.isState()) {
                    source.setIcon(new ImageIcon(gfxRepository.muteButton));
                    audioRepository.muteMusic();
                } else {
                    source.setIcon(new ImageIcon(gfxRepository.audioButton));
                    audioRepository.setMusicVolume();
                }

            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.CENTER);
                audioRepository.menuTab();
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
            }
        });

        btnLauncherAudio.setVisible(true);

        //load settings button
        XButton btnSettings = new XButton(gfxRepository.settingsButton, SwingConstants.LEFT);

        layers.add(btnSettings, new Integer(2), 0);
        btnSettings.setBounds(btnLauncherAudio.getX() - btnLauncherAudio.getWidth(), btnExit.getY(), btnExit.getWidth(), btnExit.getHeight());
        btnSettings.setOpaque(false);

        btnSettings.addMouseListener(new MouseListener() {
            XButton source;

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                audioRepository.buttonClick();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.CENTER);
                audioRepository.menuTab();
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
            }
        });

        btnSettings.setVisible(true);

        //load the game title
        XLabel lblTitle = new XLabel("stra Project", gfxRepository.txtTitle, gfxRepository.clrText);

        layers.add(lblTitle, new Integer(3), 0);
        lblTitle.setBounds(imgLogo.getX() + 45, 30, 300, 75);
        lblTitle.setVerticalAlignment(SwingConstants.TOP);
        lblTitle.setVisible(true);

        //load the game version
        XLabel lblVersion = new XLabel("Version; " + gfxRepository.gameVersion, gfxRepository.txtTiny, gfxRepository.clrText);

        layers.add(lblVersion, new Integer(4), 0);
        lblVersion.setBounds(25, getUIScaleY() - 50, 200, 35);
        lblVersion.setAlignments(SwingConstants.LEFT, SwingConstants.BOTTOM);
        lblVersion.setVisible(true);

        //load the scroll window
        contentController = new XPanel(gfxRepository.clrDGrey);
        layers.add(contentController, new Integer(5), 0);
        contentList = new XScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        layers.add(contentList, new Integer(6), 0);

        XScrollBar contentScroller = new XScrollBar();

        contentList.add(contentScroller);
        contentScroller.setBounds(contentController.getX() + contentController.getWidth() - 20, contentController.getY(), 15, contentController.getHeight());
        contentScroller.setVisible(true);

        contentController.setBounds(getUIScaleX() - 325, 45, 300, 320);
        contentController.setPreferredSize(new Dimension(contentController.getWidth(), contentController.getHeight()));
        contentController.setVisible(true);

        contentList.setBounds(getUIScaleX() - (contentController.getWidth() + 25), 45, contentController.getWidth(), contentController.getHeight());
        contentList.setPreferredSize(new Dimension(contentList.getWidth(), contentList.getHeight()));
        contentList.setViewportView(contentController);
        contentList.getViewport().setPreferredSize(new Dimension(contentList.getWidth(), contentList.getHeight()));
        contentList.setVerticalScrollBar(contentScroller); //custom scroll bar design
        contentList.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0)); //dirty way of changing scroll bar width
        contentList.setVisible(true);

        //load the expansion header
        pnlExpansionHeader = new XPanel(gfxRepository.clrDark);
        pnlExpansionHeader.setBounds(5, 5, contentController.getWidth() - 20, 25);
        pnlExpansionHeader.setVisible(true);

        //load expansion header text
        lblExpHeaderText = new XLabel("Expansion Packs", gfxRepository.txtSubheader, gfxRepository.clrText);
        pnlExpansionHeader.add(lblExpHeaderText);
        lblExpHeaderText.setBounds(5, 5, pnlExpansionHeader.getWidth() - 10, pnlExpansionHeader.getHeight() - 10);
        lblExpHeaderText.setAlignments(SwingConstants.CENTER, SwingConstants.CENTER);
        lblExpHeaderText.setVisible(true);

        //load the mod header
        pnlModHeader = new XPanel(gfxRepository.clrDark);
        pnlModHeader.setBounds(5, pnlExpansionHeader.getY() + pnlExpansionHeader.getHeight() + 5, contentController.getWidth() - 20, 25);
        pnlModHeader.setVisible(true);

        //adds the mod header text data
        lblModHeaderText = new XLabel("Mods", gfxRepository.txtSubheader, gfxRepository.clrText);
        pnlModHeader.add(lblModHeaderText);
        lblModHeaderText.setBounds(5, 5, pnlModHeader.getWidth() - 10, pnlModHeader.getHeight() - 10);
        lblModHeaderText.setAlignments(SwingConstants.CENTER, SwingConstants.CENTER);
        lblModHeaderText.setVisible(true);

        //load launch button
        XButton btnLaunch = new XButton("PLAY", gfxRepository.txtHeader, gfxRepository.clrText, gfxRepository.clrButtonMain, gfxRepository.bdrButtonEnabled);
        layers.add(btnLaunch, new Integer(7), 0);
        btnLaunch.setBounds(contentController.getX(), contentController.getY() + contentController.getHeight() + 5, contentController.getWidth(), 55);
        btnLaunch.setOpaque(true);
        //btnLaunch.setHorizontalAlignment(SwingConstants.CENTER);
        //btnLaunch.setVerticalAlignment(SwingConstants.CENTER);
        btnLaunch.addActionListener(new ActionListener() { //closes the program when clicked
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Launching game...");
                window.setTitle("Astra Project"); //changes the title from the launcher to the game window
                clearUI();
                audioRepository.buttonClick();
                audioRepository.musicTitleScreen(); //plays the title screen music
                rescaleScreen(screenScaleChoice); //resizes the screen according to the user's choice
                loadLoadingScreen(1);

            }
        });

        audioRepository.musicLauncherScreen();

        loadLauncherExpansions(); //load the expansion packs

    }

    public void loadLauncherExpansions() { //Loads the expansion content for the launcher

        String expansionID;
        launcherContentLoaded = 0;

        //empties and refactors content
        if (pnlExpansions.size() > 0) {
            contentController.removeAll();
            pnlExpansions.clear();
            lblExpansions.clear();
            lblExpanDesc.clear();
            btnExpanEnable.clear();
            lblExpanID.clear();
        }

        contentController.setPreferredSize(new Dimension(contentController.getWidth(), contentList.getHeight()));

        //add the expansion pack header to the content window
        contentController.add(pnlExpansionHeader);

        for (int i = 0; i < xmlLoader.listOfExpansions.size(); i ++) {

            pnlExpansions.add(new XPanel(gfxRepository.clrForeground));
            lblExpansions.add(new XLabel(xmlLoader.listOfExpansions.get(i).getName(), gfxRepository.txtSubheader, gfxRepository.clrText));
            lblExpanDesc.add(new XLabel(xmlLoader.listOfExpansions.get(i).getSubtitle(), gfxRepository.txtItalSubtitle, gfxRepository.clrText));
            btnExpanEnable.add(new XButton(gfxRepository.rejectButton, SwingConstants.RIGHT));
            lblExpanID.add(new XLabel(xmlLoader.listOfExpansions.get(i).getID(), gfxRepository.txtTiny, gfxRepository.clrText));

            expansionID = xmlLoader.listOfExpansions.get(i).getID();

            contentController.add(pnlExpansions.get(i)); //moves all of the content to the content controller

            pnlExpansions.get(i).setBounds(5, 5 + (65 * launcherContentLoaded) + (pnlExpansionHeader.getHeight() + 5), contentController.getWidth() - 20, 60);
            pnlExpansions.get(i).add(lblExpansions.get(i));
            pnlExpansions.get(i).add(lblExpanDesc.get(i));
            pnlExpansions.get(i).add(btnExpanEnable.get(i));
            pnlExpansions.get(i).add(lblExpanID.get(i));

            lblExpansions.get(i).setBounds(5, 5, 195, 25);
            lblExpansions.get(i).setAlignments(SwingConstants.LEFT, SwingConstants.TOP);

            lblExpanDesc.get(i).setBounds(5, 35, 170, 20);

            btnExpanEnable.get(i).setBounds(contentController.getWidth() - 50, 5, 30, 30);
            btnExpanEnable.get(i).setForeground(gfxRepository.clrText);
            btnExpanEnable.get(i).setOpaque(true);
            btnExpanEnable.get(i).setFocusable(false);
            btnExpanEnable.get(i).setFont(gfxRepository.txtSubheader);

            if (xmlLoader.listOfExpansions.get(i).getEnabledStatus()) { //checks the current status and adjusts the button accordingly
                btnExpanEnable.get(i).setIcon(new ImageIcon(gfxRepository.acceptButton));
                btnExpanEnable.get(i).toggleState();
            } else {
                btnExpanEnable.get(i).setIcon(new ImageIcon(gfxRepository.rejectButton));
            }

            btnExpanEnable.get(i).addMouseListener(new XMouseListener(expansionID) { //adds the mouse listener to the enable/disable button
                XButton source;

                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    source = (XButton)mouseEvent.getSource();
                    source.setHorizontalAlignment(SwingConstants.LEFT);
                    source.toggleState();

                    if (source.isState()) {
                        source.setIcon(new ImageIcon(gfxRepository.acceptButton));
                        audioRepository.buttonConfirm();
                        source.setToolTipText("Disable");
                    } else {
                        source.setIcon(new ImageIcon(gfxRepository.rejectButton));
                        audioRepository.buttonDisable();
                        source.setToolTipText("Enable");
                    }

                    xmlLoader.changeExpansionInfo(getIDValue(), source.isState());

                    window.refresh();

                }

                @Override
                public void mousePressed(MouseEvent mouseEvent) {
                    source = (XButton)mouseEvent.getSource();
                    source.setHorizontalAlignment(SwingConstants.LEFT);
                    window.refresh();
                }

                @Override
                public void mouseReleased(MouseEvent mouseEvent) {
                    source = (XButton)mouseEvent.getSource();
                    source.setHorizontalAlignment(SwingConstants.RIGHT);
                    window.refresh();
                }

                @Override
                public void mouseEntered(MouseEvent mouseEvent) {
                    source = (XButton)mouseEvent.getSource();
                    source.setHorizontalAlignment(SwingConstants.CENTER);
                    audioRepository.menuTab2();
                    window.refresh();
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                    source = (XButton)mouseEvent.getSource();
                    source.setHorizontalAlignment(SwingConstants.RIGHT);
                    window.refresh();
                }
            });

            lblExpanID.get(i).setBounds(contentController.getWidth() - 80, 35, 55, 20);
            lblExpanID.get(i).setHorizontalAlignment(SwingConstants.RIGHT);

            //enable everything lel
            lblExpanID.get(i).setVisible(true);
            btnExpanEnable.get(i).setVisible(true);
            lblExpanDesc.get(i).setVisible(true);
            lblExpansions.get(i).setVisible(true);
            pnlExpansions.get(i).setVisible(true);

            if (contentController.getHeight() < 10 + (65 * launcherContentLoaded) + pnlExpansionHeader.getHeight()) {
                System.out.println("Increasing content window scale.");
                contentController.setPreferredSize(new Dimension(contentController.getWidth(), contentController.getHeight() + 70));
            }

            launcherContentLoaded++;

        }

        System.out.println("Loaded expansion data into GUI.");

        loadLauncherMods();

    }

    //loads the mods to the launcher
    public void loadLauncherMods() {

        String modID;
        int modEnabled;

        if (pnlMods.size() > 0) {
            pnlMods.clear();
            lblMods.clear();
            lblModAuthor.clear();
            btnModEnable.clear();
            modEnabler.clear();
        }

        contentController.add(pnlModHeader);
        pnlModHeader.setBounds(5, pnlExpansions.get(pnlExpansions.size() - 1).getY() + pnlExpansions.get(pnlExpansions.size() - 1).getHeight() + 5, pnlModHeader.getWidth(), pnlModHeader.getHeight());

        for (int i = 0; i < xmlLoader.listOfMods.size(); i++) {

            pnlMods.add(new XPanel(gfxRepository.clrForeground));
            lblMods.add(new XLabel(xmlLoader.listOfMods.get(i).getModName(), gfxRepository.txtSubheader, gfxRepository.clrText));
            btnModEnable.add(new XButton());
            lblModAuthor.add(new XLabel()); //TODO: Add mod author.

            contentController.add(pnlMods.get(i));

            modID = xmlLoader.listOfMods.get(i).getModName();

            pnlMods.get(i).setBounds(5, 5 + (65 * launcherContentLoaded) + (pnlModHeader.getHeight() + 5) + (pnlExpansionHeader.getHeight() + 5), contentController.getWidth() - 20, 60);
            pnlMods.get(i).add(lblMods.get(i));
            pnlMods.get(i).add(btnModEnable.get(i));

            lblMods.get(i).setBounds(5, 5, 195, 25);
            lblMods.get(i).setVerticalAlignment(SwingConstants.TOP);

            btnModEnable.get(i).setBounds(contentController.getWidth() - 50, 5, 25, 25);
            btnModEnable.get(i).setForeground(gfxRepository.clrText);
            btnModEnable.get(i).setOpaque(true);
            btnModEnable.get(i).setFont(gfxRepository.txtSubheader);

            modEnabler.add(new addModAL());

            btnModEnable.get(i).addActionListener(modEnabler.get(i));
            modEnabler.get(i).setModID(modID);

            //adjust the enable/disable button based on the current status of the content
            if (xmlLoader.listOfMods.get(i).getModEnabled()) {
                //content is enabled, set the button accordingly
                //System.out.println("Mod " + xmlLoader.listOfMods.get(i).getModName() + " is enabled.");
                btnModEnable.get(i).setText("-");
                btnModEnable.get(i).setToolTipText("Disable");
                modEnabled = 1;
                btnModEnable.get(i).setBackground(gfxRepository.clrEnable);
                btnModEnable.get(i).setBorder(gfxRepository.bdrButtonEnabled);
            } else {
                //content is disabled, set the button accordingly
                //System.out.println("Mod " + xmlLoader.listOfMods.get(i).getModName() + " is disabled.");
                btnModEnable.get(i).setText("+");
                btnModEnable.get(i).setToolTipText("Enable");
                modEnabled = 0;
                btnModEnable.get(i).setBackground(gfxRepository.clrDisable);
                btnModEnable.get(i).setBorder(gfxRepository.bdrButtonDisabled);
            }

            modEnabler.get(i).setEnable(modEnabled);

            //enable all
            pnlMods.get(i).setVisible(true);
            lblMods.get(i).setVisible(true);
            btnModEnable.get(i).setVisible(true);

            if (contentController.getHeight() < 15 + (65 * launcherContentLoaded) + pnlExpansionHeader.getHeight() + pnlModHeader.getHeight()) {
                System.out.println("Increasing content window scale.");
                contentController.setPreferredSize(new Dimension(contentController.getWidth(), contentController.getHeight() + 70));
            }

            launcherContentLoaded++;

        }

        System.out.println("Loaded mod data into GUI.");

        window.refresh();

    }

    /** Loading screen UI elements **/
    //shown when the game is loading new content

    //load the loading screen and game content
    private void loadLoadingScreen(int toLoad) {

        load = toLoad;

        gfxRepository.randomBackground(); //picks a random background image for the loading screen

        window.setBounds((int)(screenWidth / 2) - (this.getUIScaleX() / 2), (int)(screenHeight / 2) - (this.getUIScaleY() / 2), this.getUIScaleX(), this.getUIScaleY());
        screen.setBounds(0, 0, getUIScaleX(), getUIScaleY());
        layers.setBounds(0, 0, getUIScaleX(), getUIScaleY()); //reset the size

        bgPanel = new XLabel(gfxRepository.mainBackground, gfxRepository.clrTrueBlack);
        layers.add(bgPanel, new Integer(0), 0);
        bgPanel.setBounds(0, 0, getUIScaleX(), getUIScaleY());
        bgPanel.setVisible(true);

        //loads the loading icon gif that plays during the loading screen
        XLabel bgLoadIcon = new XLabel(gfxRepository.loadingIcon);
        layers.add(bgLoadIcon, new Integer(1), 0);
        bgLoadIcon.setBounds((getUIScaleX() / 2) - 150, getUIScaleY() - 380, 300, 300);
        bgLoadIcon.setVisible(true);

        //loads the loading bar at the bottom of the screen that shows the progress of the loading
        loadingBar = new JProgressBar(0, 100);
        layers.add(loadingBar, new Integer(2), 0);
        loadingBar.setBounds(40, getUIScaleY() - 60, getUIScaleX() - 80, 30);
        loadingBar.setFocusable(false);
        loadingBar.setOpaque(true);
        loadingBar.setValue(0);
        loadingBar.setFont(gfxRepository.txtSubheader);
        loadingBar.setForeground(gfxRepository.clrEnable);
        loadingBar.setBackground(gfxRepository.clrDGrey);
        loadingBar.setStringPainted(true);
        loadingBar.setBorderPainted(false);
        loadingBar.setVisible(true);

        //loads the text box above the loading bar
        XLabel lblInformation = new XLabel("Astra Project - Work In Progress", gfxRepository.txtHeader, gfxRepository.clrText);
        layers.add(lblInformation, new Integer(3), 0);
        lblInformation.setBounds((getUIScaleX() / 2) - 300, getUIScaleY() - 120, 600, 50);
        lblInformation.setAlignments(SwingConstants.CENTER, SwingConstants.BOTTOM);
        lblInformation.setVisible(true);

        window.refresh();

        loadingBar.setIndeterminate(true);

        //open a new thread to load content in the background
        loader = new backgroundLoader();
        loader.addPropertyChangeListener(new propertyListener());
        loader.execute();
    }

    //opens a new thread to load content in the background without interrupting the UI
    private class backgroundLoader extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            Random random = new Random();
            int progress;

            for (int i = 1; i <= 100; i++) {
                progress = i;
                setProgress(Math.min(progress, 100));

                if (load == 1) { //loading core content

                    Thread.sleep(80 + random.nextInt(600)); //unnecessary, but will help to reduce load

                    switch (i) { //using a switch so i can set individual functions to each % up to 100%
                        case 1:
                            gfxRepository.loadMainGFX();
                            break;
                        case 20:
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
                } else if (load == 2) { //loading new game

                    Thread.sleep(30 + random.nextInt(150)); //unnecessary, but will help to reduce load

                    switch(i) {
                        case 1: //create the new user
                            playerInfo.newPlayer("Test Player");
                            gameSettings.player = playerInfo;
                            break;
                        case 5: //set up the map
                            gameSettings.map  = new mapGenerator(gameSettings.currMapScaleX, gameSettings.currMapScaleY, gameSettings.starFrequency);
                            break;
                        case 11: //load the map string
                            gameLoader.mapLoader(gameSettings.map, playerInfo);
                            break;
                        case 18: //save the data to the user file
                            playerInfo.addMapString(gameSettings.map);
                            break;
                        case 20:
                            playerInfo.addStarString(gameSettings.map);
                            break;
                        case 24:
                            playerInfo.addPlanetString(gameSettings.map);
                            break;
                        case 45: //load the GFX content
                            gfxRepository.loadMapGFX();
                            break;
                        case 80: //set up some of the UI content
                            pnlOverlay = new XPanel(gfxRepository.clrBlkTransparent);
                            pnlPauseMenu = new XPanel(gfxRepository.clrBGOpaque);
                            btnQuit = new XButton();
                            break;
                    }
                }

            }

            System.out.println("Loading of [" + load + "] complete.");

            setProgress(100);

            Thread.sleep(2000); //wait a couple before loading main screen

            return null;
        }

        protected void done() {

            loadingBar.setString("Complete");

            if (load == 1) { //load main menu

                clearUI();
                loadMainMenu();
                loadMainMenuAnimation();

            } else if (load == 2) { //load map


                loadMapView();

            }
        }
    }

    /** Main menu elements **/

    //separates the main menu animation loading
    private void loadMainMenuAnimation() {

        Random randomizePosition = new Random();

        menuSpaceport = new animCore(new ImageIcon(gfxRepository.menuSpaceport), 2, layers, window);
        menuSpaceport.setAnimationSmoothness(0.1, 200);
        menuSpaceport.start();

        menuMoon1 = new animCore(new ImageIcon(gfxRepository.moon1Icon), 3, layers, window, window.getWidth() - 500, -100, 500);
        menuMoon1.setAnimationSmoothness(0.1, 150);
        menuMoon1.setAnimationStartTime(randomizePosition.nextInt(359)); //randomizes the starting position of the moons
        menuMoon1.start();

        menuMoon2 = new animCore(new ImageIcon(gfxRepository.moon2Icon), 3, layers, window, window.getWidth() - 550, 50, 420);
        menuMoon2.setAnimationSmoothness(0.1, 150);
        menuMoon2.setAnimationStartTime(randomizePosition.nextInt(359)); //randomizes the starting position of the moons
        menuMoon2.start();

    }

    private void loadMainMenu() {

        bgPanel = new XLabel(gfxRepository.mainBackground);
        layers.add(bgPanel, new Integer(0), 0);
        bgPanel.setBounds(0, 0, getUIScaleX(), getUIScaleY());
        bgPanel.setOpaque(true);
        bgPanel.setVisible(true);

        imgLogo = new XLabel(gfxRepository.gameLogoLarge);
        layers.add(imgLogo, new Integer(10), 0);
        imgLogo.setBounds(window.getWidth() - 115, 5, 120, 120);
        imgLogo.setVisible(true);

        XLabel menuPlanet = new XLabel(gfxRepository.menuPlanet);
        layers.add(menuPlanet, new Integer(8), 0);
        menuPlanet.setBounds(window.getWidth() - 1100, 0, 1500, 450);
        menuPlanet.setVisible(true);

        XLabel lblLensGlare = new XLabel(gfxRepository.menuGlare);
        layers.add(lblLensGlare, new Integer(9), 0);
        lblLensGlare.setBounds(0, 0, screen.getWidth(), screen.getHeight());
        lblLensGlare.setVisible(true);

        XPanel pnlMenuBarH = new XPanel(gfxRepository.clrBackground);
        layers.add(pnlMenuBarH, new Integer(14), 0);
        pnlMenuBarH.setBounds(0, getUIScaleY() - 150, getUIScaleX(), 250);
        pnlMenuBarH.setVisible(true);

        btnNewGame = new XButton(gfxRepository.wideButton, SwingConstants.LEFT, "NEW GAME", gfxRepository.txtSubtitle, gfxRepository.clrText);
        layers.add(btnNewGame, new Integer(15), 0);
        btnNewGame.setBounds(5, getUIScaleY() - 60, 170, 64);
        btnNewGame.setOpaque(true);

        btnNewGame.addActionListener(new ActionListener() { //closes the program when clicked
            @Override
            public void actionPerformed(ActionEvent e) {
                btnNewGame.setVisible(false);
                loadNewSettingsMenu();
                audioRepository.buttonClick();
            }
        });

        XButton btnLoadGame = new XButton(gfxRepository.wideButton, SwingConstants.LEFT, "LOAD GAME", gfxRepository.txtSubtitle, gfxRepository.clrText);
        layers.add(btnLoadGame, new Integer(15), 0);
        btnLoadGame.setBounds(btnNewGame.getX() + btnNewGame.getWidth() + 20, btnNewGame.getY(), btnNewGame.getWidth(), btnNewGame.getHeight());
        btnLoadGame.setOpaque(true);

        btnLoadGame.addMouseListener(new XMouseListener() {

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                XButton source = (XButton)mouseEvent.getSource();
                audioRepository.buttonClick();
                source.setHorizontalAlignment(SwingConstants.RIGHT);

            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                XButton source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                XButton source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                XButton source = (XButton)mouseEvent.getSource();
                audioRepository.menuTab();
                source.setHorizontalAlignment(SwingConstants.CENTER);

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                XButton source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);


            }
        });

        window.refresh();

    }

    //loads a menu with all of the save files in it
    private void loadSavesMenu() { //TODO: Fill out







    }



    //loads the menu to set up a new game
    private void loadNewSettingsMenu() {

        settingsMenu = new XPanel();
        layers.add(settingsMenu, new Integer(14), 0);
        settingsMenu.setVisible(true);
        settingsMenu.setBounds(0, 0, 400, window.getHeight() - 250);

        XPanel pnlSettings = new XPanel(gfxRepository.clrBackground);
        pnlSettings.setBounds(settingsMenu.getX(), settingsMenu.getY(), settingsMenu.getWidth(), settingsMenu.getHeight());
        pnlSettings.setVisible(true);
        settingsMenu.add(pnlSettings);

        XButton launchNewGame = new XButton("Start", gfxRepository.txtSubheader, gfxRepository.clrText, gfxRepository.clrButtonMain, gfxRepository.bdrButtonEnabled);
        pnlSettings.add(launchNewGame);
        launchNewGame.setBounds(5, pnlSettings.getHeight() - 110, pnlSettings.getWidth() - 10, 50);
        launchNewGame.setOpaque(true);
        launchNewGame.setVisible(true);

        launchNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                audioRepository.buttonConfirm();
                menuSpaceport.stopAnimation();
                menuMoon1.stopAnimation();
                menuMoon2.stopAnimation();
                clearUI();
                loadLoadingScreen(2);
            }
        });

        XButton btnBack = new XButton("Back", gfxRepository.txtSubheader, gfxRepository.clrText, gfxRepository.clrButtonMain, gfxRepository.bdrButtonEnabled);
        pnlSettings.add(btnBack);
        btnBack.setBounds(5, pnlSettings.getHeight() - 55, pnlSettings.getWidth() - 10, 50);
        btnBack.setOpaque(true);
        btnBack.setVisible(true);

        btnBack.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                btnNewGame.setVisible(true);
                audioRepository.buttonDisable();
                settingsMenu.removeAll();
            }
        }));

        //sets up settings option for resource abundance
        XLabel lblResources = new XLabel("Resource Abundance", gfxRepository.txtSubheader, gfxRepository.clrText);
        lblResources.setAlignments(SwingConstants.CENTER);
        JSlider sldResources = new JSlider(JSlider.HORIZONTAL, gameSettings.resourceAbundanceMin, gameSettings.resourceAbundanceHigh, gameSettings.resourceAbundanceAvg);
        pnlSettings.add(lblResources);
        pnlSettings.add(sldResources);
        lblResources.setBounds(5, 15, settingsMenu.getWidth() - 5, 25);
        lblResources.setVisible(true);
        sldResources.setMajorTickSpacing(25);
        sldResources.setMinorTickSpacing(5);
        sldResources.setPaintTicks(true);
        Hashtable hshResources = new Hashtable();
        hshResources.put(new Integer(gameSettings.resourceAbundanceMin), new XLabel("Sparse", gfxRepository.txtTiny, gfxRepository.clrText));
        hshResources.put(new Integer(gameSettings.resourceAbundanceHigh), new XLabel("Abundant", gfxRepository.txtTiny, gfxRepository.clrText));
        hshResources.put(new Integer(gameSettings.resourceAbundanceAvg), new XLabel("Average", gfxRepository.txtTiny, gfxRepository.clrText));
        sldResources.setLabelTable(hshResources);
        sldResources.setPaintLabels(true);
        sldResources.setFont(gfxRepository.txtTiny);
        sldResources.setFocusable(false);
        sldResources.setForeground(gfxRepository.clrText);
        sldResources.setBounds(20, 45, settingsMenu.getWidth() - 20, 50);
        sldResources.setOpaque(false);
        sldResources.setVisible(true);

        sldResources.addChangeListener(new ChangeListener() { //adds a listener to keep track of the slider's value and translate it to the gameSettings class
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                JSlider source = (JSlider)changeEvent.getSource();
                if (source.getValueIsAdjusting()) {
                    gameSettings.currentResources = source.getValue();
                }
                window.refresh();
            }
        });

        //sets up settings option for star abundance
        XLabel lblStarFreq = new XLabel("Star Frequency", gfxRepository.txtSubheader, gfxRepository.clrText);
        lblStarFreq.setAlignments(SwingConstants.CENTER);
        JSlider sldStarFreq = new JSlider(JSlider.HORIZONTAL, gameSettings.starFreqMin, gameSettings.starFreqHigh, gameSettings.starFreqAvg);
        pnlSettings.add(lblStarFreq);
        pnlSettings.add(sldStarFreq);
        lblStarFreq.setBounds(5, sldResources.getY() + sldResources.getHeight() + 5, lblResources.getWidth(), lblResources.getHeight());
        lblStarFreq.setVisible(true);
        sldStarFreq.setMajorTickSpacing(15);
        sldStarFreq.setMinorTickSpacing(5);
        sldStarFreq.setPaintTicks(true);
        Hashtable hshStarFreq = new Hashtable();
        hshStarFreq.put(new Integer(gameSettings.starFreqMin), new XLabel("Many", gfxRepository.txtTiny, gfxRepository.clrText));
        hshStarFreq.put(new Integer(gameSettings.starFreqHigh), new XLabel("Few", gfxRepository.txtTiny, gfxRepository.clrText));
        hshStarFreq.put(new Integer(gameSettings.starFreqAvg), new XLabel("Average", gfxRepository.txtTiny, gfxRepository.clrText));
        sldStarFreq.setLabelTable(hshStarFreq);
        sldStarFreq.setPaintLabels(true);
        sldStarFreq.setFont(gfxRepository.txtTiny);
        sldStarFreq.setFocusable(false);
        sldStarFreq.setForeground(gfxRepository.clrText);
        sldStarFreq.setBounds(20, lblStarFreq.getY() + lblStarFreq.getHeight() + 5, settingsMenu.getWidth() - 20, sldResources.getHeight());
        sldStarFreq.setOpaque(false);
        sldStarFreq.setVisible(true);

        sldStarFreq.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                JSlider source = (JSlider)changeEvent.getSource();
                if (source.getValueIsAdjusting()) {
                    gameSettings.starFrequency = source.getValue();
                }
                window.refresh();
            }
        });

        //sets up settings option for map scaling
        XLabel lblMapScale = new XLabel("Map Scale", gfxRepository.txtSubheader, gfxRepository.clrText);
        lblMapScale.setAlignments(SwingConstants.CENTER);
        JSlider sldMapScale = new JSlider(JSlider.HORIZONTAL, gameSettings.mapScaleMin, gameSettings.mapScaleHigh, gameSettings.mapScaleAvg);
        pnlSettings.add(lblMapScale);
        pnlSettings.add(sldMapScale);
        lblMapScale.setBounds(5, sldStarFreq.getY() + sldStarFreq.getHeight() + 5, lblResources.getWidth(), lblResources.getHeight());
        lblMapScale.setVisible(true);
        sldMapScale.setMajorTickSpacing(20);
        sldMapScale.setMinorTickSpacing(5);
        sldMapScale.setPaintTicks(true);
        Hashtable hshMapScale = new Hashtable();
        hshMapScale.put(new Integer(gameSettings.mapScaleMin), new XLabel("Small", gfxRepository.txtTiny, gfxRepository.clrText));
        hshMapScale.put(new Integer(gameSettings.mapScaleHigh), new XLabel("Huge", gfxRepository.txtTiny, gfxRepository.clrText));
        hshMapScale.put(new Integer(gameSettings.mapScaleAvg), new XLabel("Normal", gfxRepository.txtTiny, gfxRepository.clrText));
        sldMapScale.setLabelTable(hshMapScale);
        sldMapScale.setPaintLabels(true);
        sldMapScale.setFocusable(false);
        sldMapScale.setFont(gfxRepository.txtTiny);
        sldMapScale.setForeground(gfxRepository.clrText);
        sldMapScale.setBounds(20, lblMapScale.getY() + lblMapScale.getHeight() + 5, settingsMenu.getWidth() - 20, sldResources.getHeight());
        sldMapScale.setOpaque(false);
        sldMapScale.setVisible(true);

        sldMapScale.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                JSlider source = (JSlider)changeEvent.getSource();
                if (source.getValueIsAdjusting()) {
                    gameSettings.currMapScaleX = source.getValue();
                    gameSettings.currMapScaleY = source.getValue();
                }
                window.refresh();
            }
        });

        XLabel lblDiffOverall = new XLabel("Overall Difficulty", gfxRepository.txtSubheader, gfxRepository.clrText);
        lblDiffOverall.setAlignments(SwingConstants.CENTER);
        JSlider sldDiffOverall = new JSlider(JSlider.HORIZONTAL, gameSettings.overallDifficultyMin, gameSettings.overallDifficultyHigh, gameSettings.overallDifficultyAvg);
        pnlSettings.add(lblDiffOverall);
        pnlSettings.add(sldDiffOverall);
        lblDiffOverall.setBounds(5, sldMapScale.getY() + sldMapScale.getHeight() + 5, lblResources.getWidth(), lblResources.getHeight());
        lblDiffOverall.setVisible(true);
        sldDiffOverall.setMajorTickSpacing(25);
        sldDiffOverall.setMinorTickSpacing(5);
        sldDiffOverall.setPaintTicks(true);
        Hashtable hshDiffOverall = new Hashtable();
        hshDiffOverall.put(new Integer(gameSettings.overallDifficultyMin), new XLabel("Easy", gfxRepository.txtTiny, gfxRepository.clrText));
        hshDiffOverall.put(new Integer(gameSettings.overallDifficultyHigh), new XLabel("Hard", gfxRepository.txtTiny, gfxRepository.clrText));
        hshDiffOverall.put(new Integer(gameSettings.overallDifficultyAvg), new XLabel("Normal", gfxRepository.txtTiny, gfxRepository.clrText));
        sldDiffOverall.setLabelTable(hshDiffOverall);
        sldDiffOverall.setPaintLabels(true);
        sldDiffOverall.setFocusable(false);
        sldDiffOverall.setFont(gfxRepository.txtTiny);
        sldDiffOverall.setForeground(gfxRepository.clrText);
        sldDiffOverall.setBounds(20, lblDiffOverall.getY() + lblDiffOverall.getHeight() + 5, settingsMenu.getWidth() - 20, sldResources.getHeight());
        sldDiffOverall.setOpaque(false);
        sldDiffOverall.setVisible(true);

        sldDiffOverall.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                JSlider source = (JSlider)changeEvent.getSource();
                if (source.getValueIsAdjusting()) {
                    gameSettings.currDifficulty = source.getValue();
                }
                window.refresh();
            }
        });

        window.refresh();

    }

    /** Map screen UI **/

    //loads the map view
    private void loadMapView() {

        clearUI();

        audioRepository.musicShuffle();
        audioRepository.ambianceMainGame();

        bgPanel = new XLabel(gfxRepository.mainBackground, gfxRepository.clrTrueBlack);
        layers.add(bgPanel, new Integer(0), 0);
        bgPanel.setBounds(0, 0, getUIScaleX(), getUIScaleY());
        bgPanel.setVisible(true);

        XScrollPane mapView = new XScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        layers.add(mapView, new Integer(3), 0);
        mapView.setViewportView(bgPanel);
        mapView.setBounds(0, 0, window.getWidth(), window.getHeight());
        mapView.setVisible(true);

        ArrayList<ArrayList<XLabel>> mapGFX = new ArrayList<>();
        ArrayList<ArrayList<XButton>> mapButton = new ArrayList<>();

        int positionX;
        int positionY = 0;
        int tileSize = 45;

        for (int i = 0; i < gameSettings.map.mapTiles.size(); i++) {
            mapGFX.add(new ArrayList<XLabel>());
            mapButton.add(new ArrayList<XButton>());
            positionX = 0;

            for (int j = 0; j < gameSettings.map.mapTiles.get(i).size(); j++) {
                if (gameSettings.map.mapTiles.get(i).get(j).getStar()) {
                    mapGFX.get(i).add(new XLabel(gfxRepository.planetIcon));

                    if (gameSettings.map.mapTiles.get(i).get(j).getStarData().isHomeSystem()) {
                        XLabel homeSystem = new XLabel(gfxRepository.homeSystem);
                        bgPanel.add(homeSystem);
                        homeSystem.setBounds(tileSize * positionX - 15, tileSize * positionY - 15, tileSize - 15, tileSize - 15);
                        homeSystem.setVisible(true);
                    }

                    mapButton.get(i).add(new XButton());
                    mapGFX.get(i).get(j).add(mapButton.get(i).get(j));
                    mapButton.get(i).get(j).setOpaque(false);
                    mapButton.get(i).get(j).setBorder(null);
                    mapButton.get(i).get(j).setBackground(gfxRepository.clrInvisible);
                    mapButton.get(i).get(j).setVisible(true);
                    mapButton.get(i).get(j).setBounds(0, 0, tileSize, tileSize);
                    mapButton.get(i).get(j).addActionListener(new addMapAL(i, j));
                } else {
                    mapGFX.get(i).add(new XLabel());
                    mapButton.get(i).add(null); //lololo cheating
                }
                bgPanel.add(mapGFX.get(i).get(j));
                mapGFX.get(i).get(j).setBounds(tileSize * positionX, tileSize * positionY, tileSize, tileSize);
                mapGFX.get(i).get(j).setVisible(true);

                positionX++;
            }

            positionY++;
        }

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

        XPanel pnlTopBar = new XPanel(gfxRepository.clrDGrey);
        layers.add(pnlTopBar, new Integer(8), 0);
        pnlTopBar.setBounds(0, 0, screen.getWidth(), 50);

        XButton btnMenu = new XButton("Menu", gfxRepository.txtSubheader, gfxRepository.clrText, gfxRepository.clrButtonMain, gfxRepository.bdrButtonEnabled);
        pnlTopBar.add(btnMenu);
        btnMenu.setBounds(pnlTopBar.getWidth() - 85, 5, 80, pnlTopBar.getHeight() - 10);
        btnMenu.setOpaque(false);
        btnMenu.setVisible(true);



        layers.add(pnlOverlay, new Integer(14), 0);
        pnlOverlay.setBounds(0, pnlTopBar.getHeight(), window.getWidth(), window.getHeight() - pnlTopBar.getHeight());
        pnlOverlay.add(pnlPauseMenu);
        pnlPauseMenu.setBounds((screen.getWidth() / 2) - 450, (screen.getHeight() / 2) - 400, 900, 800);
        pnlPauseMenu.setVisible(true);
        pnlOverlay.setVisible(false);

        btnMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!pnlOverlay.isVisible()) {
                    showPauseMenu();
                    audioRepository.buttonClick();
                    window.refresh();
                } else {
                    pnlOverlay.setVisible(false);
                    pnlPauseMenu.removeAll();
                    audioRepository.buttonDisable();
                }
            }
        });

    }

    private void loadStarData(starClass star) { //shows the star information screen

        pnlStarData.removeAll(); //clears the UI to avoid overlap

        pnlStarData.setVisible(true);

        //displays the star's portrait
        XLabel lblStarPortrait = new XLabel(star.getPortraitGFX(), gfxRepository.clrTrueBlack);
        pnlStarData.add(lblStarPortrait);
        lblStarPortrait.setBounds((pnlStarData.getWidth() / 2) - 280, 0, 560, 185);
        lblStarPortrait.setVisible(true);

        XLabel lblPortraitBorder = new XLabel(gfxRepository.portraitBorder);
        lblStarPortrait.add(lblPortraitBorder);
        lblPortraitBorder.setBounds(0, 0, lblStarPortrait.getWidth(), lblStarPortrait.getHeight());
        lblPortraitBorder.setVisible(true);

        //displays the background

        XLabel lblBackground = new XLabel(gfxRepository.menuBackground, gfxRepository.clrBGOpaque);
        pnlStarData.add(lblBackground);
        lblBackground.setBounds(0, lblStarPortrait.getHeight(), pnlStarData.getWidth(), pnlStarData.getHeight() - lblStarPortrait.getHeight());
        lblBackground.setVisible(true);

        //adds an icon/text with the number of planets in the system
        XLabel lblPlanetCountImg = new XLabel(gfxRepository.starPlanetCount);
        pnlStarData.add(lblPlanetCountImg);
        lblPlanetCountImg.setBounds(5, 15, 30, 30);
        lblPlanetCountImg.setVisible(true);

        XLabel lblPlanetCountText = new XLabel(" : " + star.getNumOfPlanets(), gfxRepository.txtSubtitle, gfxRepository.clrText);
        pnlStarData.add(lblPlanetCountText);
        lblPlanetCountText.setBounds(lblPlanetCountImg.getX() + lblPlanetCountImg.getWidth() + 5, lblPlanetCountImg.getY() - 5, 70, 40);
        lblPlanetCountText.setAlignments(SwingConstants.LEFT, SwingConstants.CENTER);
        lblPlanetCountText.setVisible(true);

        //adds an icon/text with the number of colonies in the system
        XLabel lblColonyCountImg = new XLabel(gfxRepository.colonyCount);
        pnlStarData.add(lblColonyCountImg);
        lblColonyCountImg.setBounds(5, lblPlanetCountImg.getY() + lblPlanetCountImg.getHeight() + 10, 30, 30);
        lblColonyCountImg.setVisible(true);

        XLabel lblColonyCountText = new XLabel(" : " + star.getColonyCount(), gfxRepository.txtSubtitle, gfxRepository.clrText);
        pnlStarData.add(lblColonyCountText);
        lblColonyCountText.setBounds(lblColonyCountImg.getX() + lblColonyCountImg.getWidth() + 5, lblColonyCountImg.getY() - 10, 70, 40);
        lblColonyCountText.setAlignments(SwingConstants.LEFT, SwingConstants.CENTER);
        lblColonyCountText.setVisible(true);

        //displays the star's name
        XLabel lblStarName = new XLabel(star.getStarClassName(), gfxRepository.txtHeader, gfxRepository.clrText);
        lblBackground.add(lblStarName);
        lblStarName.setBounds((pnlStarData.getWidth() / 2) - 100, 5, 200, 40);
        lblStarName.setAlignments(SwingConstants.CENTER);
        lblStarName.setVisible(true);

        //displays the star class's description
        XLabel lblStarDesc = new XLabel("<html>" + star.getStarClassDesc() + "</html>", gfxRepository.txtItalSubtitle, gfxRepository.clrText);
        lblBackground.add(lblStarDesc);
        lblStarDesc.setBounds(40, lblStarName.getY() + lblStarName.getHeight() + 15, pnlStarData.getWidth() - 80, 100);
        lblStarDesc.setAlignments(SwingConstants.CENTER, SwingConstants.TOP);

        lblStarDesc.setVisible(true);

        //displays the close button
        XButton btnClose = new XButton("X", gfxRepository.txtSubheader, gfxRepository.clrText, gfxRepository.clrButtonBackground, gfxRepository.bdrButtonEnabled);
        pnlStarData.add(btnClose);
        btnClose.setBounds(pnlStarData.getWidth() - 40, 10, 30, 30);
        btnClose.setOpaque(true);
        btnClose.setVisible(true);

        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                audioRepository.buttonClick();
                pnlStarData.removeAll();
                pnlStarData.setVisible(false);
                window.refresh();
            }
        });

        window.refresh();

    }

    private void showPauseMenu() {

        gameSettings.gameIsPaused = true; //pauses the game

        //adds the title to the pause menu
        XLabel lblMenuTitle = new XLabel("Pause Menu", gfxRepository.txtHeader, gfxRepository.clrText);
        pnlPauseMenu.add(lblMenuTitle);
        lblMenuTitle.setBounds(5, 10, pnlPauseMenu.getWidth() - 10, 40);
        lblMenuTitle.setAlignments(SwingConstants.CENTER);
        lblMenuTitle.setVisible(true);

        pnlPauseMenu.add(btnQuit);
        btnQuit.setBounds(10, pnlPauseMenu.getHeight() - 50, pnlPauseMenu.getWidth() - 20, 40);
        btnQuit.setOpaque(true);
        btnQuit.setBackground(gfxRepository.clrButtonBackground);
        btnQuit.setForeground(gfxRepository.clrText);
        btnQuit.setFont(gfxRepository.txtSubheader);
        btnQuit.setText("Quit Game");
        btnQuit.setBorder(gfxRepository.bdrButtonEnabled);
        btnQuit.setVisible(true);

        btnQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (btnQuit.getText().equals("Quit Game")) { //TODO: Add a way to save user data when quitting.
                    audioRepository.buttonDisable();
                    btnQuit.setText("Are you sure?");
                } else {
                    audioRepository.buttonConfirm();
                    closeProgram();
                }
            }
        });

        XButton btnReturn = new XButton("Continue", gfxRepository.txtSubheader, gfxRepository.clrText, gfxRepository.clrButtonBackground, gfxRepository.bdrButtonEnabled);
        pnlPauseMenu.add(btnReturn);
        btnReturn.setBounds(10, lblMenuTitle.getY() + lblMenuTitle.getHeight() + 10, pnlPauseMenu.getWidth() - 20, 40);
        btnReturn.setOpaque(true);
        btnReturn.setVisible(true);

        btnReturn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                audioRepository.buttonSelect();
                pnlOverlay.setVisible(false);
                pnlPauseMenu.removeAll();
            }
        });

        //Adds a slider to change music volume
        XLabel lblMusicVolume = new XLabel("Music Volume", gfxRepository.txtSubtitle, gfxRepository.clrText);
        pnlPauseMenu.add(lblMusicVolume);
        lblMusicVolume.setBounds(5, btnReturn.getY() + btnReturn.getHeight() + 5, pnlPauseMenu.getWidth() - 10, 25);
        lblMusicVolume.setAlignments(SwingConstants.CENTER);
        lblMusicVolume.setVisible(true);

        JSlider sldMusicVolume = new JSlider(JSlider.HORIZONTAL, 0, 100, audioRepository.musicVolume);
        pnlPauseMenu.add(sldMusicVolume);
        sldMusicVolume.setMajorTickSpacing(10);
        sldMusicVolume.setMinorTickSpacing(2);
        sldMusicVolume.setPaintTicks(true);
        sldMusicVolume.setPaintLabels(false);
        sldMusicVolume.setFocusable(false);
        sldMusicVolume.setForeground(gfxRepository.clrText);
        sldMusicVolume.setOpaque(false);
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

        JSlider sldAmbianceVolume = new JSlider(JSlider.HORIZONTAL, 0, 100, audioRepository.ambianceVolume);
        pnlPauseMenu.add(sldAmbianceVolume);
        sldAmbianceVolume.setMajorTickSpacing(10);
        sldAmbianceVolume.setMinorTickSpacing(2);
        sldAmbianceVolume.setPaintTicks(true);
        sldAmbianceVolume.setPaintLabels(false);
        sldAmbianceVolume.setFocusable(false);
        sldAmbianceVolume.setForeground(gfxRepository.clrText);
        sldAmbianceVolume.setOpaque(false);
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



        pnlOverlay.setVisible(true);

    }


    private class propertyListener implements PropertyChangeListener { //monitors the loading bar and changes it

        public void propertyChange(PropertyChangeEvent e) {
            if ("progress".equals(e.getPropertyName())) {
                int progress = (Integer)e.getNewValue();
                loadingBar.setIndeterminate(false);
                loadingBar.setValue(progress);
            }

        }
    }

    /** Special ActionListeners **/

    //action listened for enabling/disabling mods
    private class addModAL implements ActionListener {

        addModAL() {
        }

        int enable;
        String modID;

        @Override
        public void actionPerformed(ActionEvent e) {

            if (enable == 1) {
                audioRepository.buttonDisable();
                enable = 0;
                System.out.println("Disabling content for " + modID);
            } else {
                audioRepository.buttonClick();
                enable = 1;
                System.out.println("Enabling content for " + modID);
            }

            xmlLoader.changeModInfo(this.modID, this.enable);

            System.out.println("Refreshing UI...");

            loadLauncherExpansions();

        }

        void setEnable(int enabled) {
            this.enable = enabled;
        }

        void setModID(String ID) {
            this.modID = ID;
        }

    }

    private class addMapAL implements ActionListener {

        int x, y;

        addMapAL(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            audioRepository.buttonClick();
            loadStarData(gameSettings.map.mapTiles.get(x).get(y).getStarData());
        }

    }


}
