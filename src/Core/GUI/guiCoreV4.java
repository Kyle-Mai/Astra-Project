package Core.GUI;

//import all relevant stuff
import Core.*;
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

    private File resourcesFolder = new File(("user.dir") + "/src/Core/GUI/Resources"); //stores the resources folder

    /** Stores resource declarations **/

    private ArrayList<XPanel> pnlExpansions = new ArrayList<>();
    private ArrayList<XLabel> lblExpansions = new ArrayList<>();
    private ArrayList<XLabel> lblExpanDesc = new ArrayList<>();
    private ArrayList<XButton> btnExpanEnable = new ArrayList<>();
    private ArrayList<XLabel> lblExpanID = new ArrayList<>();
    private ArrayList<addExpAL> actionEnabler = new ArrayList<>();
    private XPanel pnlExpansionHeader;
    private XLabel lblExpHeaderText;

    private ArrayList<XPanel> pnlMods = new ArrayList<>();
    private ArrayList<XLabel> lblMods = new ArrayList<>();
    private ArrayList<XLabel> lblModAuthor = new ArrayList<>();
    private ArrayList<XButton> btnModEnable = new ArrayList<>();
    private ArrayList<addModAL> modEnabler = new ArrayList<>();
    private XPanel pnlModHeader;
    private XLabel lblModHeaderText;

    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //gets the screen size of the user
    private double screenWidth = screenSize.getWidth();
    private double screenHeight = screenSize.getHeight();
    private JFrame window;
    private JLayeredPane layers; //sorts the layers of the screen
    private XPanel screen;
    private JPanel contentController;
    private JScrollPane contentList;
    private JProgressBar loadingBar;
    private JLabel bgPanel;
    private JLabel imgLogo;
    private JPanel pnlPauseMenu;
    private JPanel pnlOverlay;
    private int screenScaleChoice = 8;
    private JPanel settingsMenu;
    private JButton btnNewGame;
    private animCore menuSpaceport;
    private animCore menuMoon1;
    private  animCore menuMoon2;
    private JButton btnQuit;
    private JPanel pnlStarData;

    private int launcherContentLoaded = 0;

    playerData playerInfo = new playerData();

    private int load;

    private backgroundLoader loader;

    /** UI scaling code**/
    //handles the scaling of the UI

    private int[] currentUIScale = {0, 0}; //default screen scale

    //methods to return the UI elements
    public int getUIScaleX() { return this.currentUIScale[0]; }

    public int getUIScaleY() { return this.currentUIScale[1]; }

    //sets the window size to the chosen monitor scale
    public void rescaleScreen(int option) {

        int oldX, oldY; //stores the previous values in case something goes wrong

        oldX = this.getUIScaleX();
        oldY = this.getUIScaleY();

        switch(option) {
            //Widescreen monitors
            case 1: //4K
                this.currentUIScale[0] = 3840; //X scale
                this.currentUIScale[1] = 2160; //Y scale
                break;
            case 2: //2K
                this.currentUIScale[0] = 2560;
                this.currentUIScale[1] = 1440;
                break;
            case 3: //standard monitor
                this.currentUIScale[0] = 1920;
                this.currentUIScale[1] = 1080;
                break;
            case 4:
                this.currentUIScale[0] = 1600;
                this.currentUIScale[1] = 900;
                break;
            case 5:
                this.currentUIScale[0] = 1366;
                this.currentUIScale[1] = 768;
                break;
            case 6:
                this.currentUIScale[0] = 1280;
                this.currentUIScale[1] = 720;
                break;
            //4:3 and similar monitor scales
            case 7:
                this.currentUIScale[0] = 1600;
                this.currentUIScale[1] = 1200;
                break;
            case 8:
                this.currentUIScale[0] = 1280;
                this.currentUIScale[1] = 1024;
                break;
            case 9:
                this.currentUIScale[0] = 1024;
                this.currentUIScale[1] = 768;
                break;
            case 10:
                this.currentUIScale[0] = 800;
                this.currentUIScale[1] = 600;
                break;
            case 11: //Launcher UI scale
                this.currentUIScale[0] = 700;
                this.currentUIScale[1] = 450;
                break;
            default: //safety net
                this.currentUIScale[0] = 500;
                this.currentUIScale[1] = 500;
                break;
        }

        if (this.getUIScaleX() > screenWidth || this.getUIScaleY() > screenHeight) {
            System.out.println("Monitor is not large enough to support this resolution.");
            this.currentUIScale[0] = oldX; //resets the screen scale to the previous values
            this.currentUIScale[1] = oldY;
        } else {
            //no incompatibilities, proceed
            System.out.println("UI successfully rescaled to " + currentUIScale[0] + "x" + currentUIScale[1] + ".");
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

    private void refreshUI() {
        //refreshes the UI content
        window.revalidate();
        window.repaint();
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
        XButton btnExit = new XButton("X", gfxRepository.txtStandard, gfxRepository.clrText, gfxRepository.clrButtonBackground);

        layers.add(btnExit, new Integer(2), 0);
        btnExit.setBounds(getUIScaleX() - 55, 10, 30, 30);
        btnExit.setBorder(gfxRepository.bdrButtonEnabled);
        btnExit.setOpaque(false);
        btnExit.addActionListener(new ActionListener() { //closes the program when clicked
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Killing program.");
                audioRepository.buttonClick();
                closeProgram();
            }
        });
        btnExit.setVisible(true);

        //load minimize button
        JButton btnMinimize = new JButton();

        layers.add(btnMinimize, new Integer(2), 0);
        btnMinimize.setBounds(getUIScaleX() - 90, 10, 30, 30);
        btnMinimize.setBackground(gfxRepository.clrButtonBackground);
        btnMinimize.setFocusPainted(false);
        btnMinimize.setForeground(gfxRepository.clrText);
        //btnMinimize.setBorderPainted(false);
        btnMinimize.setBorder(gfxRepository.bdrButtonEnabled);
        btnMinimize.setOpaque(false);
        btnMinimize.setFont(gfxRepository.txtStandard);
        btnMinimize.setText("-");
        btnMinimize.addActionListener(new ActionListener() { //closes the program when clicked
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Minimizing program.");
                window.setState(Frame.ICONIFIED);
                audioRepository.buttonClick();
            }
        });
        btnMinimize.setVisible(true);

        //load settings button
        JButton btnSettings = new JButton();

        layers.add(btnSettings, new Integer(2), 0);
        btnSettings.setBounds(getUIScaleX() - 125, 10, 30, 30);
        btnSettings.setBackground(gfxRepository.clrButtonBackground);
        btnSettings.setFocusPainted(false);
        btnSettings.setForeground(gfxRepository.clrText);
        btnSettings.setBorder(gfxRepository.bdrButtonEnabled);
        btnSettings.setOpaque(false);
        btnSettings.setFont(gfxRepository.txtStandard);
        btnSettings.setText("*");

        btnSettings.setVisible(true);

        //load the game title
        JLabel lblTitle = new JLabel();

        layers.add(lblTitle, new Integer(3), 0);
        lblTitle.setBounds(imgLogo.getX() + 45, 30, 300, 75);
        lblTitle.setVerticalAlignment(SwingConstants.TOP);
        lblTitle.setOpaque(false);
        lblTitle.setFocusable(false);
        lblTitle.setFont(gfxRepository.txtTitle);
        lblTitle.setText("stra Project");
        lblTitle.setForeground(gfxRepository.clrText);
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
        contentList = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        layers.add(contentList, new Integer(6), 0);

        JScrollBar contentScroller = new JScrollBar();

        contentList.add(contentScroller);
        contentScroller.setBorder(null);
        contentScroller.setUI(new XScrollBar());
        contentScroller.setBounds(contentController.getX() + contentController.getWidth() - 20, contentController.getY(), 15, contentController.getHeight());
        contentScroller.setOpaque(true);
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
        contentList.setOpaque(false);
        contentList.setVisible(true);
        contentList.setBorder(null);

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
        JButton btnLaunch = new JButton();
        layers.add(btnLaunch, new Integer(7), 0);
        btnLaunch.setBounds(contentController.getX(), contentController.getY() + contentController.getHeight() + 5, contentController.getWidth(), 55);
        btnLaunch.setBackground(gfxRepository.clrButtonMain);
        btnLaunch.setForeground(gfxRepository.clrText);
        btnLaunch.setBorder(gfxRepository.bdrButtonEnabled);
        btnLaunch.setFont(gfxRepository.txtHeader);
        btnLaunch.setOpaque(true);
        btnLaunch.setFocusPainted(false);
        btnLaunch.setHorizontalAlignment(SwingConstants.CENTER);
        btnLaunch.setVerticalAlignment(SwingConstants.CENTER);
        btnLaunch.setText("PLAY");
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
        int expansionEnabled;
        launcherContentLoaded = 0;

        //empties and refactors content
        if (pnlExpansions.size() > 0) {
            contentController.removeAll();
            pnlExpansions.clear();
            lblExpansions.clear();
            lblExpanDesc.clear();
            btnExpanEnable.clear();
            lblExpanID.clear();
            actionEnabler.clear();
        }

        contentController.setPreferredSize(new Dimension(contentController.getWidth(), contentList.getHeight()));

        //add the expansion pack header to the content window
        contentController.add(pnlExpansionHeader);
        //contentLayout.layoutContainer(pnlExpansionHeader);

        for (int i = 0; i < xmlLoader.listOfExpansions.size(); i ++) {

            pnlExpansions.add(new XPanel());
            lblExpansions.add(new XLabel());
            lblExpanDesc.add(new XLabel());
            btnExpanEnable.add(new XButton());
            lblExpanID.add(new XLabel());

            expansionID = xmlLoader.listOfExpansions.get(i).getID();

            contentController.add(pnlExpansions.get(i)); //moves all of the content to the content controller

            pnlExpansions.get(i).setLayout(null);

            pnlExpansions.get(i).setBounds(5, 5 + (65 * launcherContentLoaded) + (pnlExpansionHeader.getHeight() + 5), contentController.getWidth() - 20, 60);
            pnlExpansions.get(i).setBackground(gfxRepository.clrForeground);
            pnlExpansions.get(i).add(lblExpansions.get(i));
            pnlExpansions.get(i).add(lblExpanDesc.get(i));
            pnlExpansions.get(i).add(btnExpanEnable.get(i));
            pnlExpansions.get(i).add(lblExpanID.get(i));
            pnlExpansions.get(i).setOpaque(true);

            lblExpansions.get(i).setBounds(5, 5, 195, 25);
            lblExpansions.get(i).setText(xmlLoader.listOfExpansions.get(i).getName(), gfxRepository.txtSubheader, gfxRepository.clrText);
            lblExpansions.get(i).setAlignments(SwingConstants.TOP, SwingConstants.LEFT);

            lblExpanDesc.get(i).setBounds(5, 35, 170, 20);
            lblExpanDesc.get(i).setText(xmlLoader.listOfExpansions.get(i).getSubtitle(), gfxRepository.txtItalSubtitle, gfxRepository.clrText);

            btnExpanEnable.get(i).setBounds(contentController.getWidth() - 50, 5, 25, 25);
            btnExpanEnable.get(i).setForeground(gfxRepository.clrText);
            btnExpanEnable.get(i).setOpaque(true);
            btnExpanEnable.get(i).setFocusable(false);
            btnExpanEnable.get(i).setFont(gfxRepository.txtSubheader);

            actionEnabler.add(new addExpAL());

            btnExpanEnable.get(i).addActionListener(actionEnabler.get(i));
            actionEnabler.get(i).setExpID(expansionID);

            //adjust the enable/disable button based on the current status of the content
            if (xmlLoader.listOfExpansions.get(i).getEnabledStatus()) {
                //content is enabled, set the button accordingly
                //System.out.println("Expansion " + xmlLoader.listOfExpansions.get(i).getID() + " is enabled.");
                btnExpanEnable.get(i).setText("-");
                btnExpanEnable.get(i).setToolTipText("Disable");
                expansionEnabled = 1;
                btnExpanEnable.get(i).setBackground(gfxRepository.clrEnable);
                btnExpanEnable.get(i).setBorder(gfxRepository.bdrButtonEnabled);
            } else {
                //content is disabled, set the button accordingly
                //System.out.println("Expansion " + xmlLoader.listOfExpansions.get(i).getID() + " is disabled.");
                btnExpanEnable.get(i).setText("+");
                btnExpanEnable.get(i).setToolTipText("Enable");
                expansionEnabled = 0;
                btnExpanEnable.get(i).setBackground(gfxRepository.clrDisable);
                btnExpanEnable.get(i).setBorder(gfxRepository.bdrButtonDisabled);
            }

            actionEnabler.get(i).setEnable(expansionEnabled); //update the action listener tied to the enable button with the current status of the content

            lblExpanID.get(i).setBounds(contentController.getWidth() - 80, 35, 55, 20);
            lblExpanID.get(i).setHorizontalAlignment(SwingConstants.RIGHT);
            lblExpanID.get(i).setForeground(gfxRepository.clrText);
            lblExpanID.get(i).setFont(gfxRepository.txtTiny);
            lblExpanID.get(i).setHorizontalAlignment(SwingConstants.RIGHT);
            lblExpanID.get(i).setText(xmlLoader.listOfExpansions.get(i).getID());

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

            pnlMods.add(new XPanel());
            lblMods.add(new XLabel());
            btnModEnable.add(new XButton());
            lblModAuthor.add(new XLabel());

            contentController.add(pnlMods.get(i));

            modID = xmlLoader.listOfMods.get(i).getModName();

            pnlMods.get(i).setLayout(null);

            pnlMods.get(i).setBounds(5, 5 + (65 * launcherContentLoaded) + (pnlModHeader.getHeight() + 5) + (pnlExpansionHeader.getHeight() + 5), contentController.getWidth() - 20, 60);
            pnlMods.get(i).setBackground(gfxRepository.clrForeground);
            pnlMods.get(i).add(lblMods.get(i));
            pnlMods.get(i).add(btnModEnable.get(i));

            lblMods.get(i).setBounds(5, 5, 195, 25);
            lblMods.get(i).setForeground(gfxRepository.clrText);
            lblMods.get(i).setOpaque(false);
            lblMods.get(i).setFont(gfxRepository.txtSubheader);
            lblMods.get(i).setText(xmlLoader.listOfMods.get(i).getModName());
            lblMods.get(i).setVerticalAlignment(SwingConstants.TOP);

            btnModEnable.get(i).setBounds(contentController.getWidth() - 50, 5, 25, 25);
            btnModEnable.get(i).setForeground(gfxRepository.clrText);
            btnModEnable.get(i).setOpaque(true);
            btnModEnable.get(i).setFocusable(false);
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

        refreshUI();

    }

    /** Loading screen UI elements **/
    //shown when the game is loading new content

    //load the loading screen and game content
    private void loadLoadingScreen(int toLoad) {

        //TODO: Eventually re-sort swing objects so I don't have a bunch of a empty ones lying around. Arraylists are the best bet.

        JLabel bgLoadIcon;

        load = toLoad;

        gfxRepository.randomBackground(); //picks a random background image for the loading screen

        window.setBounds((int)(screenWidth / 2) - (this.getUIScaleX() / 2), (int)(screenHeight / 2) - (this.getUIScaleY() / 2), this.getUIScaleX(), this.getUIScaleY());
        screen.setBounds(0, 0, getUIScaleX(), getUIScaleY());
        layers.setBounds(0, 0, getUIScaleX(), getUIScaleY()); //reset the size

        bgPanel = new JLabel(new ImageIcon(gfxRepository.mainBackground));
        layers.add(bgPanel, new Integer(0), 0);
        bgPanel.setBounds(0, 0, getUIScaleX(), getUIScaleY());
        bgPanel.setOpaque(true);
        bgPanel.setFocusable(false);
        bgPanel.setVisible(true);

        //loads the loading icon gif that plays during the loading screen
        bgLoadIcon = new JLabel(gfxRepository.loadingIcon);
        layers.add(bgLoadIcon, new Integer(1), 0);
        bgLoadIcon.setBounds((getUIScaleX() / 2) - 150, getUIScaleY() - 380, 300, 300);
        bgLoadIcon.setOpaque(false);
        bgLoadIcon.setFocusable(false);
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
        JLabel lblInformation = new JLabel();
        layers.add(lblInformation, new Integer(3), 0);
        lblInformation.setBounds((getUIScaleX() / 2) - 300, getUIScaleY() - 120, 600, 50);
        lblInformation.setOpaque(false);
        lblInformation.setFocusable(false);
        lblInformation.setFont(gfxRepository.txtHeader);
        lblInformation.setHorizontalAlignment(SwingConstants.CENTER);
        lblInformation.setVerticalAlignment(SwingConstants.BOTTOM);
        lblInformation.setForeground(gfxRepository.clrText);
        lblInformation.setText("Astra Project - Work In Progress");
        lblInformation.setVisible(true);

        refreshUI();

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
                            pnlOverlay = new JPanel();
                            pnlPauseMenu = new JPanel();
                            btnQuit = new JButton();
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

        bgPanel = new JLabel(new ImageIcon(gfxRepository.mainBackground));
        layers.add(bgPanel, new Integer(0), 0);
        bgPanel.setBounds(0, 0, getUIScaleX(), getUIScaleY());
        bgPanel.setOpaque(true);
        bgPanel.setFocusable(false);
        bgPanel.setVisible(true);

        imgLogo = new JLabel(new ImageIcon(gfxRepository.gameLogoLarge));
        layers.add(imgLogo, new Integer(10), 0);
        imgLogo.setBounds(window.getWidth() - 140, 20, 120, 120);

        JLabel menuPlanet = new JLabel(new ImageIcon(gfxRepository.menuPlanet));
        layers.add(menuPlanet, new Integer(8), 0);
        menuPlanet.setBounds(window.getWidth() - 1100, 0, 1500, 450);
        menuPlanet.setOpaque(false);
        menuPlanet.setFocusable(false);
        menuPlanet.setVisible(true);

        imgLogo.setVisible(true);

        //testAnimation = new animCore(new ImageIcon(gfxRepository.menuShip), 1, layers, window);
        //testAnimation.start();

        JPanel pnlMenuBarH = new JPanel();

        layers.add(pnlMenuBarH, new Integer(3), 0);
        pnlMenuBarH.setBounds(0, getUIScaleY() - 150, getUIScaleX(), 250);
        pnlMenuBarH.setLayout(null);
        pnlMenuBarH.setBackground(gfxRepository.clrBackground);
        pnlMenuBarH.setFocusable(false);
        pnlMenuBarH.setBorder(null);
        pnlMenuBarH.setVisible(true);

        btnNewGame = new JButton();
        layers.add(btnNewGame, new Integer(4), 0);
        btnNewGame.setBounds(5, getUIScaleY() - 60, 180, 55);
        btnNewGame.setBackground(gfxRepository.clrButtonMain);
        btnNewGame.setForeground(gfxRepository.clrText);
        btnNewGame.setBorder(gfxRepository.bdrButtonEnabled);
        btnNewGame.setFont(gfxRepository.txtHeader);
        btnNewGame.setOpaque(true); //TODO: Find a way to do semi-transparent buttons
        btnNewGame.setFocusPainted(false);
        //btnNewGame.setHorizontalAlignment(SwingConstants.CENTER);
        //btnNewGame.setVerticalAlignment(SwingConstants.CENTER);
        btnNewGame.setText("NEW GAME");

        btnNewGame.addActionListener(new ActionListener() { //closes the program when clicked
            @Override
            public void actionPerformed(ActionEvent e) {
                btnNewGame.setVisible(false);
                loadNewSettingsMenu();
                audioRepository.buttonClick();
            }
        });

        refreshUI();

    }

    //loads the menu to set up a new game
    private void loadNewSettingsMenu() {

        settingsMenu = new JPanel();
        settingsMenu.setLayout(null);
        layers.add(settingsMenu, new Integer(4), 0);
        settingsMenu.setVisible(true);
        settingsMenu.setOpaque(false);
        settingsMenu.setBorder(null);
        settingsMenu.setBounds(0, 0, 400, window.getHeight() - 250);

        JPanel pnlSettings = new JPanel();
        pnlSettings.setLayout(null);
        pnlSettings.setBounds(settingsMenu.getX(), settingsMenu.getY(), settingsMenu.getWidth(), settingsMenu.getHeight());
        pnlSettings.setBackground(gfxRepository.clrBackground);
        pnlSettings.setVisible(true);
        pnlSettings.setOpaque(true);
        settingsMenu.add(pnlSettings);

        JButton launchNewGame = new JButton();
        pnlSettings.add(launchNewGame);
        launchNewGame.setBounds(5, pnlSettings.getHeight() - 110, pnlSettings.getWidth() - 10, 50);
        launchNewGame.setBackground(gfxRepository.clrButtonMain);
        launchNewGame.setFocusPainted(false);
        launchNewGame.setForeground(gfxRepository.clrText);
        launchNewGame.setFont(gfxRepository.txtSubheader);
        launchNewGame.setText("Start");
        launchNewGame.setOpaque(true);
        launchNewGame.setBorder(gfxRepository.bdrButtonEnabled);
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

        JButton btnBack = new JButton();
        pnlSettings.add(btnBack);
        btnBack.setBounds(5, pnlSettings.getHeight() - 55, pnlSettings.getWidth() - 10, 50);
        btnBack.setBackground(gfxRepository.clrButtonMain);
        btnBack.setFocusPainted(false);
        btnBack.setForeground(gfxRepository.clrText);
        btnBack.setFont(gfxRepository.txtSubheader);
        btnBack.setOpaque(true);
        btnBack.setText("Back");
        btnBack.setBorder(gfxRepository.bdrButtonEnabled);
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
        JLabel lblResources = new JLabel();
        JSlider sldResources = new JSlider(JSlider.HORIZONTAL, gameSettings.resourceAbundanceMin, gameSettings.resourceAbundanceHigh, gameSettings.resourceAbundanceAvg);
        pnlSettings.add(lblResources);
        pnlSettings.add(sldResources);
        lblResources.setBounds(5, 15, settingsMenu.getWidth() - 5, 25);
        lblResources.setForeground(gfxRepository.clrText);
        lblResources.setFont(gfxRepository.txtSubheader);
        lblResources.setHorizontalAlignment(SwingConstants.CENTER);
        lblResources.setVerticalAlignment(SwingConstants.CENTER);
        lblResources.setText("Resource Abundance");
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
                refreshUI();
            }
        });

        //sets up settings option for star abundance
        JLabel lblStarFreq = new JLabel();
        JSlider sldStarFreq = new JSlider(JSlider.HORIZONTAL, gameSettings.starFreqMin, gameSettings.starFreqHigh, gameSettings.starFreqAvg);
        pnlSettings.add(lblStarFreq);
        pnlSettings.add(sldStarFreq);
        lblStarFreq.setBounds(5, sldResources.getY() + sldResources.getHeight() + 5, lblResources.getWidth(), lblResources.getHeight());
        lblStarFreq.setForeground(gfxRepository.clrText);
        lblStarFreq.setFont(gfxRepository.txtSubheader);
        lblStarFreq.setHorizontalAlignment(SwingConstants.CENTER);
        lblStarFreq.setVerticalAlignment(SwingConstants.CENTER);
        lblStarFreq.setText("Star Frequency");
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
                refreshUI();
            }
        });

        //sets up settings option for map scaling
        JLabel lblMapScale = new JLabel();
        JSlider sldMapScale = new JSlider(JSlider.HORIZONTAL, gameSettings.mapScaleMin, gameSettings.mapScaleHigh, gameSettings.mapScaleAvg);
        pnlSettings.add(lblMapScale);
        pnlSettings.add(sldMapScale);
        lblMapScale.setBounds(5, sldStarFreq.getY() + sldStarFreq.getHeight() + 5, lblResources.getWidth(), lblResources.getHeight());
        lblMapScale.setForeground(gfxRepository.clrText);
        lblMapScale.setFont(gfxRepository.txtSubheader);
        lblMapScale.setHorizontalAlignment(SwingConstants.CENTER);
        lblMapScale.setVerticalAlignment(SwingConstants.CENTER);
        lblMapScale.setText("Map Scale");
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
                refreshUI();
            }
        });

        JLabel lblDiffOverall = new JLabel();
        JSlider sldDiffOverall = new JSlider(JSlider.HORIZONTAL, gameSettings.overallDifficultyMin, gameSettings.overallDifficultyHigh, gameSettings.overallDifficultyAvg);
        pnlSettings.add(lblDiffOverall);
        pnlSettings.add(sldDiffOverall);
        lblDiffOverall.setBounds(5, sldMapScale.getY() + sldMapScale.getHeight() + 5, lblResources.getWidth(), lblResources.getHeight());
        lblDiffOverall.setForeground(gfxRepository.clrText);
        lblDiffOverall.setFont(gfxRepository.txtSubheader);
        lblDiffOverall.setHorizontalAlignment(SwingConstants.CENTER);
        lblDiffOverall.setVerticalAlignment(SwingConstants.CENTER);
        lblDiffOverall.setText("Overall Difficulty");
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
                refreshUI();
            }
        });

        refreshUI();

    }

    /** Map screen UI **/

    //loads the map view
    private void loadMapView() {

        clearUI();

        audioRepository.musicShuffle();
        audioRepository.ambianceMainGame();

        bgPanel = new JLabel(new ImageIcon(gfxRepository.mainBackground));
        layers.add(bgPanel, new Integer(0), 0);
        bgPanel.setBounds(0, 0, getUIScaleX(), getUIScaleY());
        bgPanel.setOpaque(true);
        bgPanel.setFocusable(false);
        bgPanel.setVisible(true);

        JScrollPane mapView = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        layers.add(mapView, new Integer(3), 0);
        mapView.setViewportView(bgPanel);
        mapView.setOpaque(false);
        mapView.setBounds(0, 0, window.getWidth(), window.getHeight());
        mapView.setVisible(true);

        ArrayList<ArrayList<JLabel>> mapGFX = new ArrayList<>();
        ArrayList<ArrayList<JButton>> mapButton = new ArrayList<>();

        int positionX;
        int positionY = 0;
        int tileSize = 30;

        for (int i = 0; i < gameSettings.map.mapTiles.size(); i++) {
            mapGFX.add(new ArrayList<JLabel>());
            mapButton.add(new ArrayList<JButton>());
            positionX = 0;

            for (int j = 0; j < gameSettings.map.mapTiles.get(i).size(); j++) {
                if (gameSettings.map.mapTiles.get(i).get(j).getStar()) {
                    mapGFX.get(i).add(new JLabel(new ImageIcon(gfxRepository.planetIcon)));
                    mapButton.get(i).add(new JButton());
                    mapGFX.get(i).get(j).add(mapButton.get(i).get(j));
                    mapButton.get(i).get(j).setOpaque(false);
                    mapButton.get(i).get(j).setFocusPainted(false);
                    mapButton.get(i).get(j).setBorder(null);
                    mapButton.get(i).get(j).setBackground(gfxRepository.clrInvisible);
                    mapButton.get(i).get(j).setVisible(true);
                    mapButton.get(i).get(j).setBounds(0, 0, tileSize, tileSize);
                    mapButton.get(i).get(j).addActionListener(new addMapAL(i, j));
                } else {
                    mapGFX.get(i).add(new JLabel());
                    mapButton.get(i).add(null); //lololo cheating
                }
                bgPanel.add(mapGFX.get(i).get(j));
                mapGFX.get(i).get(j).setBounds(tileSize * positionX, tileSize * positionY, tileSize, tileSize);
                mapGFX.get(i).get(j).setFont(gfxRepository.txtTiny);
                mapGFX.get(i).get(j).setForeground(gfxRepository.clrText);
                //mapGFX.get(i).get(j).setText("");
                mapGFX.get(i).get(j).setVisible(true);

                positionX++;
            }

            positionY++;
        }

        pnlStarData = new JPanel();
        layers.add(pnlStarData, new Integer(10), 0);
        pnlStarData.setLayout(null);
        pnlStarData.setBounds((screen.getWidth() / 2) - 400, 100, 800, screen.getHeight() - 200);
        pnlStarData.setBackground(gfxRepository.clrBGOpaque);
        pnlStarData.setVisible(false);

        loadPlayerBar();

        refreshUI();

    }

    /** General in-game UI elements **/
    //includes the top bar, the pause menu, etc

    private void loadPlayerBar() { //loads the bar at the top of the screen with the relevant player information

        JPanel pnlTopBar = new JPanel();
        pnlTopBar.setLayout(null);
        layers.add(pnlTopBar, new Integer(8), 0);
        pnlTopBar.setBounds(0, 0, screen.getWidth(), 50);
        pnlTopBar.setBackground(gfxRepository.clrDGrey);

        JButton btnMenu = new JButton();
        pnlTopBar.add(btnMenu);
        btnMenu.setBounds(pnlTopBar.getWidth() - 85, 5, 80, pnlTopBar.getHeight() - 10);
        btnMenu.setOpaque(false);
        btnMenu.setFocusable(false);
        btnMenu.setFocusPainted(false);
        btnMenu.setForeground(gfxRepository.clrText);
        btnMenu.setBackground(gfxRepository.clrButtonMain);
        btnMenu.setFont(gfxRepository.txtSubheader);
        btnMenu.setText("Menu");
        btnMenu.setBorder(gfxRepository.bdrButtonEnabled);
        btnMenu.setVisible(true);

        layers.add(pnlOverlay, new Integer(15), 0);
        pnlOverlay.setLayout(null);
        pnlOverlay.setBounds(0, pnlTopBar.getHeight(), window.getWidth(), window.getHeight() - pnlTopBar.getHeight());
        pnlOverlay.setBackground(gfxRepository.clrBlkTransparent);
        pnlOverlay.setFocusable(false);
        pnlOverlay.add(pnlPauseMenu);
        pnlPauseMenu.setLayout(null);
        pnlPauseMenu.setBounds((screen.getWidth() / 2) - 450, (screen.getHeight() / 2) - 400, 900, 800);
        pnlPauseMenu.setBackground(gfxRepository.clrBGOpaque);
        pnlPauseMenu.setFocusable(false);
        pnlPauseMenu.setVisible(true);
        pnlOverlay.setVisible(false);

        btnMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!pnlOverlay.isVisible()) {
                    showPauseMenu();
                    audioRepository.buttonClick();
                    refreshUI();
                } else {
                    pnlOverlay.setVisible(false);
                    pnlPauseMenu.removeAll();
                    audioRepository.buttonDisable();
                }
            }
        });

    }

    protected void loadStarData(starClass star) { //shows the star information screen

        pnlStarData.setVisible(true);

        //displays the star's portrait
        JLabel lblStarPortrait = new JLabel(new ImageIcon(star.getPortraitGFX()));
        pnlStarData.add(lblStarPortrait);
        lblStarPortrait.setOpaque(true);
        lblStarPortrait.setBounds((pnlStarData.getWidth() / 2) - 280, 0, 560, 185);
        lblStarPortrait.setBackground(gfxRepository.clrTrueBlack);
        lblStarPortrait.setVisible(true);

        //displays the star's name
        JLabel lblStarName = new JLabel();
        pnlStarData.add(lblStarName);
        lblStarName.setFont(gfxRepository.txtHeader);
        lblStarName.setForeground(gfxRepository.clrText);
        lblStarName.setText(star.getStarClassName());
        lblStarName.setBounds(570, 40, pnlStarData.getWidth() - 580, 60);
        lblStarName.setHorizontalAlignment(SwingConstants.CENTER);
        lblStarName.setVisible(true);

        //displays the close button
        JButton btnClose = new JButton();
        pnlStarData.add(btnClose);
        btnClose.setBounds(pnlStarData.getWidth() - 40, 10, 30, 30);
        btnClose.setOpaque(true);
        btnClose.setFocusPainted(false);
        btnClose.setFocusable(false);
        btnClose.setForeground(gfxRepository.clrText);
        btnClose.setFont(gfxRepository.txtSubheader);
        btnClose.setBackground(gfxRepository.clrButtonBackground);
        btnClose.setText("X");
        btnClose.setVisible(true);

        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                audioRepository.buttonClick();
                pnlStarData.removeAll();
                pnlStarData.setVisible(false);
                refreshUI();
            }
        });

        //displays the star class's description
        JLabel lblStarDesc = new JLabel();
        pnlStarData.add(lblStarDesc);
        lblStarDesc.setBounds(10, 200, pnlStarData.getWidth(), 100);
        lblStarDesc.setVerticalAlignment(SwingConstants.TOP);
        lblStarDesc.setForeground(gfxRepository.clrText);
        lblStarDesc.setFont(gfxRepository.txtSubtitle);
        lblStarDesc.setText("<html>" + star.getStarClassDesc() + "</html>");

        lblStarDesc.setVisible(true);

        refreshUI();

    }

    private void showPauseMenu() {

        gameSettings.gameIsPaused = true; //pauses the game

        //adds the title to the pause menu
        JLabel lblMenuTitle = new JLabel();
        pnlPauseMenu.add(lblMenuTitle);
        lblMenuTitle.setBounds(5, 10, pnlPauseMenu.getWidth() - 10, 40);
        lblMenuTitle.setForeground(gfxRepository.clrText);
        lblMenuTitle.setFont(gfxRepository.txtHeader);
        lblMenuTitle.setFocusable(false);
        lblMenuTitle.setText("Pause Menu");
        lblMenuTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblMenuTitle.setVerticalAlignment(SwingConstants.CENTER);
        lblMenuTitle.setVisible(true);

        pnlPauseMenu.add(btnQuit);
        btnQuit.setBounds(10, pnlPauseMenu.getHeight() - 50, pnlPauseMenu.getWidth() - 20, 40);
        btnQuit.setOpaque(true);
        btnQuit.setBackground(gfxRepository.clrButtonBackground);
        btnQuit.setFocusPainted(false);
        btnQuit.setFocusable(false);
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

        JButton btnReturn = new JButton();
        pnlPauseMenu.add(btnReturn);
        btnReturn.setBounds(10, lblMenuTitle.getY() + lblMenuTitle.getHeight() + 10, pnlPauseMenu.getWidth() - 20, 40);
        btnReturn.setOpaque(true);
        btnReturn.setBackground(gfxRepository.clrButtonBackground);
        btnReturn.setFocusPainted(false);
        btnReturn.setFocusable(false);
        btnReturn.setForeground(gfxRepository.clrText);
        btnReturn.setFont(gfxRepository.txtSubheader);
        btnReturn.setText("Continue");
        btnReturn.setBorder(gfxRepository.bdrButtonEnabled);
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
        JLabel lblMusicVolume = new JLabel();
        pnlPauseMenu.add(lblMusicVolume);
        lblMusicVolume.setBounds(5, btnReturn.getY() + btnReturn.getHeight() + 5, pnlPauseMenu.getWidth() - 10, 25);
        lblMusicVolume.setFont(gfxRepository.txtSubtitle);
        lblMusicVolume.setForeground(gfxRepository.clrText);
        lblMusicVolume.setHorizontalAlignment(SwingConstants.CENTER);
        lblMusicVolume.setVerticalAlignment(SwingConstants.CENTER);
        lblMusicVolume.setText("Music Volume");
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
                refreshUI();
            }
        });

        //Adds a slider to change ambiance volume
        JLabel lblAmbianceVolume = new JLabel();
        pnlPauseMenu.add(lblAmbianceVolume);
        lblAmbianceVolume.setBounds(5, sldMusicVolume.getY() + sldMusicVolume.getHeight() + 5, pnlPauseMenu.getWidth() - 10, lblMusicVolume.getHeight());
        lblAmbianceVolume.setFont(gfxRepository.txtSubtitle);
        lblAmbianceVolume.setForeground(gfxRepository.clrText);
        lblAmbianceVolume.setHorizontalAlignment(SwingConstants.CENTER);
        lblAmbianceVolume.setVerticalAlignment(SwingConstants.CENTER);
        lblAmbianceVolume.setText("Ambiance Volume");
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
                refreshUI();
            }
        });



        pnlOverlay.setVisible(true);

    }


    private class propertyListener implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent e) {
            if ("progress".equals(e.getPropertyName())) {
                int progress = (Integer)e.getNewValue();
                loadingBar.setIndeterminate(false);
                loadingBar.setValue(progress);
            }

        }
    }

    //action listened for enabling/disabling expansions
    private class addExpAL implements ActionListener {

        public addExpAL() {
        }

        int enable;
        String expID;

        @Override
        public void actionPerformed(ActionEvent e) {

            if (enable == 1) {
                audioRepository.buttonDisable();
                enable = 0;
                System.out.println("Disabling content for " + expID);
            } else {
                audioRepository.buttonClick();
                enable = 1;
                System.out.println("Enabling content for " + expID);
            }

            xmlLoader.changeExpansionInfo(this.expID, this.enable);

            System.out.println("Refreshing UI...");

            loadLauncherExpansions();

        }

        public void setEnable(int enabled) {
            this.enable = enabled;
        }

        public void setExpID(String ID) {
            this.expID = ID;
        }

    }

    //action listened for enabling/disabling mods
    private class addModAL implements ActionListener {

        public addModAL() {
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

        public void setEnable(int enabled) {
            this.enable = enabled;
        }

        public void setModID(String ID) {
            this.modID = ID;
        }

    }

    private class addMapAL implements ActionListener {

        int x, y;

        public addMapAL(int x, int y) {
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
