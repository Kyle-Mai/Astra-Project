package Core.GUI;

//import all relevant stuff
import Core.SFX.audioCore;
import Core.SFX.audioRepository;
import Core.gameLoader;
import Core.xmlLoader;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

/**
 KM
 May 16 2017
 Round FOUR of attempting to create a GUI core to manage and display UI elements.
 Just kill me now.
 */


public class guiCoreV4 {

    private File resourcesFolder = new File(("user.dir") + "/src/Core/GUI/Resources"); //stores the resources folder

    /** Stores resource declarations **/

    private ArrayList<JPanel> pnlExpansions = new ArrayList<>();
    private ArrayList<JLabel> lblExpansions = new ArrayList<>();
    private ArrayList<JLabel> lblExpanDesc = new ArrayList<>();
    private ArrayList<JButton> btnExpanEnable = new ArrayList<>();
    private ArrayList<JLabel> lblExpanID = new ArrayList<>();
    private ArrayList<addExpAL> actionEnabler = new ArrayList<>();

    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //gets the screen size of the user
    private double screenWidth = screenSize.getWidth();
    private double screenHeight = screenSize.getHeight();
    private JFrame window;
    private JLayeredPane layers; //sorts the layers of the screen
    private newPanel screen;
    private JPanel contentController;
    private JScrollPane contentList;
    private JPanel pnlExpansionHeader;
    private JLabel lblExpHeaderText;
    private JProgressBar loadingBar;
    private JLabel bgPanel;
    private JLabel imgLogo;
    private int screenScaleChoice;
    private BufferedImage backgroundImage;
    private BufferedImage gameLogo;
    private audioCore music;

    private int load;

    private backgroundLoader loader;

    /** Stores UI element design properties **/

    final String gameVersion = "PTB-A Build 64b";

    private final Color clrBlk = new Color(25, 35, 35, 255);
    private final Color clrDGrey = new Color(45, 55, 75, 255);
    private final Color clrDisableBorder = new Color(75, 5, 25, 255);
    private final Color clrDisable = new Color(135, 15, 55, 255);
    private final Color clrEnable = new Color(0, 165, 225, 255);
    private final Color clrDark = new Color(0, 90, 145, 255);
    private final Color clrButtonBackground = new Color(0, 90, 125, 220);
    private final Color clrButtonMain = new Color(0, 90, 155, 255);
    private final Color clrBackground = new Color(0, 130, 195, 105);
    private final Color clrForeground = new Color(0, 110, 185, 155);
    private final Color clrText = new Color(255, 255, 255, 255);

    private final Font txtStandard = new Font("Comic Sans", Font.PLAIN, 15);
    private final Font txtSubtitle = new Font("Arial", Font.BOLD, 14);
    private final Font txtItalSubtitle = new Font("Arial", Font.ITALIC, 14);
    private final Font txtSubheader = new Font("Arial", Font.BOLD, 16);
    private final Font txtHeader = new Font("Arial", Font.BOLD, 25);
    private final Font txtTitle = new Font("Arial", Font.BOLD, 40);
    private final Font txtTiny = new Font("Arial", Font.PLAIN, 12);

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

        window = new JFrame("Astra Launcher");
        screen = new newPanel();

        rescaleScreen(screenScaleOption);

        window.setIconImage(gfxRepository.gameLogo);
        window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        screen.setLayout(null); //CARDINAL SIN, BUT I DON'T REALLY CARE AT THIS POINT
        screen.setVisible(true);
        window.setContentPane(screen);
        window.setUndecorated(true);
        window.setResizable(false);
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

    public void loadLauncherScreen() {
        System.out.println("Loading launcher data...");
        JLabel imgBackground, imgBorder;

        //initialize the layers
        layers = new JLayeredPane();
        layers.setBounds(0, 0, getUIScaleX(), getUIScaleY());
        //add the layered window to the content pane
        window.getContentPane().add(layers);

        //attempt to load images
        imgBackground = new JLabel(new ImageIcon(gfxRepository.launcherBackground)); //TODO: Add a way to rescale images.

        imgLogo = new JLabel(new ImageIcon(gfxRepository.gameLogo));
        layers.add(imgBackground, new Integer(0), 0);
        layers.add(imgLogo, new Integer(9), 0);
        imgLogo.setBounds(40, 20, 50, 60);
        imgLogo.setVisible(true);
        imgBackground.setBounds(0, 0, getUIScaleX(), getUIScaleY());
        imgBackground.setVisible(true);

        imgBorder = new JLabel(new ImageIcon(gfxRepository.launcherBorder));
        layers.add(imgBorder, new Integer(1), 0);
        imgBorder.setBounds(0, 0, getUIScaleX(), getUIScaleY());
        imgBorder.setFocusable(false);
        imgBorder.setVisible(true);

        //load exit button
        JButton btnExit = new JButton();

        layers.add(btnExit, new Integer(2), 0);
        btnExit.setBounds(getUIScaleX() - 55, 10, 30, 30);
        btnExit.setBackground(clrButtonBackground);
        btnExit.setFocusPainted(false);
        btnExit.setForeground(clrText);
        //btnExit.setBorderPainted(false);
        btnExit.setBorder(BorderFactory.createCompoundBorder( BorderFactory.createBevelBorder( BevelBorder.RAISED, clrForeground, clrButtonBackground), BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));
        btnExit.setOpaque(false);
        btnExit.setFont(txtStandard);
        btnExit.setText("X");
        btnExit.addActionListener(new ActionListener() { //closes the program when clicked
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Killing program.");
                audioRepository.buttonClick();
                window.dispose(); //ensure the thread dies
                System.exit(0); //close the program
            }
        });
        btnExit.setVisible(true);

        //load minimize button
        JButton btnMinimize = new JButton();

        layers.add(btnMinimize, new Integer(2), 0);
        btnMinimize.setBounds(getUIScaleX() - 90, 10, 30, 30);
        btnMinimize.setBackground(clrButtonBackground);
        btnMinimize.setFocusPainted(false);
        btnMinimize.setForeground(clrText);
        //btnMinimize.setBorderPainted(false);
        btnMinimize.setBorder(BorderFactory.createCompoundBorder( BorderFactory.createBevelBorder( BevelBorder.RAISED, clrForeground, clrButtonBackground), BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));
        btnMinimize.setOpaque(false);
        btnMinimize.setFont(txtStandard);
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

        layers.add(btnSettings, new Integer(3), 0);
        btnSettings.setBounds(getUIScaleX() - 125, 10, 30, 30);
        btnSettings.setBackground(clrButtonBackground);
        btnSettings.setFocusPainted(false);
        btnSettings.setForeground(clrText);
        btnSettings.setBorder(BorderFactory.createCompoundBorder( BorderFactory.createBevelBorder( BevelBorder.RAISED, clrForeground, clrButtonBackground), BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));
        btnSettings.setOpaque(false);
        btnSettings.setFont(txtStandard);
        btnSettings.setText("*");

        btnSettings.setVisible(true);

        //load the game title
        JLabel lblTitle = new JLabel();

        layers.add(lblTitle, new Integer(3), 0);
        lblTitle.setBounds(imgLogo.getX() + 45, 30, 300, 75);
        lblTitle.setVerticalAlignment(SwingConstants.TOP);
        lblTitle.setOpaque(false);
        lblTitle.setFocusable(false);
        lblTitle.setFont(txtTitle);
        lblTitle.setText("stra Project");
        lblTitle.setForeground(clrText);
        lblTitle.setVisible(true);

        //load the game version
        JLabel lblVersion = new JLabel();

        layers.add(lblVersion, new Integer(4), 0);
        lblVersion.setBounds(25, getUIScaleY() - 50, 200, 35);
        lblVersion.setOpaque(false);
        lblVersion.setFocusable(false);
        lblVersion.setFont(txtTiny);
        lblVersion.setText("Version; " + gameVersion);
        lblVersion.setForeground(clrText);
        lblVersion.setVerticalAlignment(SwingConstants.BOTTOM);
        lblVersion.setVisible(true);

        //load the scroll window
        contentController = new JPanel();
        layers.add(contentController, new Integer(5), 0);
        contentList = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        layers.add(contentList, new Integer(6), 0);

        JScrollBar contentScroller = new JScrollBar();

        contentList.add(contentScroller);
        contentScroller.setBorder(null);
        contentScroller.setUI(new customScrollbar());
        contentScroller.setBounds(contentController.getX() + contentController.getWidth() - 20, contentController.getY(), 15, contentController.getHeight());
        contentScroller.setOpaque(true);
        contentScroller.setVisible(true);

        contentController.setBounds(getUIScaleX() - 325, 45, 300, 320);
        contentController.setOpaque(true);
        contentController.setLayout(null);
        contentController.setBackground(clrBackground);
        contentController.setVisible(true);

        contentList.setBounds(getUIScaleX() - (contentController.getWidth() + 25), 45, contentController.getWidth(), contentController.getHeight());
        contentList.setViewportView(contentController);
        contentList.setVerticalScrollBar(contentScroller); //custom scroll bar design
        contentList.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0)); //dirty way of changing scroll bar width
        contentList.setOpaque(false);
        contentList.setVisible(true);
        contentList.setBorder(null);

        //load the expansion header
        pnlExpansionHeader = new JPanel();
        pnlExpansionHeader.setLayout(null);
        pnlExpansionHeader.setBounds(5, 5, contentController.getWidth() - 20, 25);
        pnlExpansionHeader.setOpaque(true);
        pnlExpansionHeader.setBackground(clrDark);
        pnlExpansionHeader.setVisible(true);

        //load expansion header text
        lblExpHeaderText = new JLabel();
        lblExpHeaderText.setBounds(5, 5, pnlExpansionHeader.getWidth() - 10, pnlExpansionHeader.getHeight() - 10);
        lblExpHeaderText.setOpaque(false);
        lblExpHeaderText.setFont(txtSubheader);
        lblExpHeaderText.setForeground(clrText);
        lblExpHeaderText.setText("Expansion Packs");
        lblExpHeaderText.setHorizontalAlignment(SwingConstants.CENTER);
        lblExpHeaderText.setVerticalAlignment(SwingConstants.CENTER);
        lblExpHeaderText.setVisible(true);

        //load launch button
        JButton btnLaunch = new JButton();
        layers.add(btnLaunch, new Integer(7), 0);
        btnLaunch.setBounds(contentController.getX(), contentController.getY() + contentController.getHeight() + 5, contentController.getWidth(), 55);
        btnLaunch.setBackground(clrButtonMain);
        btnLaunch.setForeground(clrText);
        btnLaunch.setBorder(BorderFactory.createCompoundBorder( BorderFactory.createBevelBorder( BevelBorder.RAISED, clrForeground, clrButtonBackground), BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));
        btnLaunch.setFont(txtHeader);
        btnLaunch.setOpaque(true); //TODO: Find a way to do semi-transparent buttons
        btnLaunch.setFocusPainted(false);
        btnLaunch.setHorizontalAlignment(SwingConstants.CENTER);
        btnLaunch.setVerticalAlignment(SwingConstants.CENTER);
        btnLaunch.setText("PLAY");
        btnLaunch.addActionListener(new ActionListener() { //closes the program when clicked
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Launching game...");
                window.setTitle("Astra Project");
                clearUI();
                audioRepository.buttonClick();
                music.interrupt();
                music = new audioCore("imperial_fleet.mp3", audioRepository.musicVolume);
                music.start();
                loadLoadingScreen(1);

            }
        });

        music = new audioCore("new_dawn.mp3", audioRepository.musicVolume);
        music.start();

        loadLauncherExpansions();

        //make sure the content is visible and render it
        window.revalidate();
        window.repaint();

    }

    public void loadLauncherExpansions() { //Loads the expansion content for the launcher

        String expansionID;
        int expansionEnabled;

        //empties and refactors content
        if (pnlExpansions.size() > 0) {
            contentController.removeAll();
            pnlExpansions.clear();
            lblExpansions.clear();
            lblExpanDesc.clear();
            btnExpanEnable.clear();
            lblExpanID.clear();
        }

        //add the expansion pack header to the content window
        contentController.add(pnlExpansionHeader);
        pnlExpansionHeader.add(lblExpHeaderText);

        for (int i = 0; i < xmlLoader.listOfExpansions.size(); i ++) {

            pnlExpansions.add(new JPanel());
            lblExpansions.add(new JLabel());
            lblExpanDesc.add(new JLabel());
            btnExpanEnable.add(new JButton());
            lblExpanID.add(new JLabel());

            expansionID = xmlLoader.listOfExpansions.get(i).getID();

            contentController.add(pnlExpansions.get(i)); //moves all of the content to the content controller

            pnlExpansions.get(i).setLayout(null);

            if (5 + 5 + (65 * i) + (pnlExpansionHeader.getHeight() + 5) > contentController.getHeight()) { //increases the window size if needed
                System.out.println("Resizing content window.");
                contentController.setBounds(contentController.getX(), contentController.getY(), contentController.getWidth(), contentController.getHeight() + 75);
            } else {
                contentController.setBounds(getUIScaleX() - 325, 110, 300, 300);
            }

            pnlExpansions.get(i).setBounds(5, 5 + (65 * i) + (pnlExpansionHeader.getHeight() + 5), contentController.getWidth() - 20, 60);
            pnlExpansions.get(i).setBackground(clrForeground);
            pnlExpansions.get(i).add(lblExpansions.get(i));
            pnlExpansions.get(i).add(lblExpanDesc.get(i));
            pnlExpansions.get(i).add(btnExpanEnable.get(i));
            pnlExpansions.get(i).add(lblExpanID.get(i));
            pnlExpansions.get(i).setOpaque(true);

            lblExpansions.get(i).setBounds(5, 5, 195, 25);
            lblExpansions.get(i).setForeground(clrText);
            lblExpansions.get(i).setOpaque(false);
            lblExpansions.get(i).setFont(txtSubheader);
            lblExpansions.get(i).setText(xmlLoader.listOfExpansions.get(i).getName());
            lblExpansions.get(i).setVerticalAlignment(SwingConstants.TOP);

            lblExpanDesc.get(i).setBounds(5, 35, 170, 20);
            lblExpanDesc.get(i).setOpaque(false);
            lblExpanDesc.get(i).setForeground(clrText);
            lblExpanDesc.get(i).setFont(txtItalSubtitle);
            lblExpanDesc.get(i).setText(xmlLoader.listOfExpansions.get(i).getSubtitle());

            btnExpanEnable.get(i).setBounds(contentController.getWidth() - 50, 5, 25, 25);
            btnExpanEnable.get(i).setForeground(clrText);
            btnExpanEnable.get(i).setOpaque(true);
            btnExpanEnable.get(i).setFocusable(false);
            btnExpanEnable.get(i).setFont(txtSubheader);

            actionEnabler.add(new addExpAL());

            btnExpanEnable.get(i).addActionListener(actionEnabler.get(i));
            actionEnabler.get(i).setExpID(expansionID);

            //adjust the enable/disable button based on the current status of the content
            if (xmlLoader.listOfExpansions.get(i).getEnabledStatus()) {
                //content is enabled, set the button accordingly
                System.out.println("Expansion " + xmlLoader.listOfExpansions.get(i).getID() + " is enabled.");
                btnExpanEnable.get(i).setText("-");
                btnExpanEnable.get(i).setToolTipText("Disable");
                expansionEnabled = 1;
                btnExpanEnable.get(i).setBackground(clrEnable);
                btnExpanEnable.get(i).setBorder(BorderFactory.createCompoundBorder( BorderFactory.createBevelBorder( BevelBorder.RAISED, clrForeground, clrBackground), BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));
            } else {
                //content is disabled, set the button accordingly
                System.out.println("Expansion " + xmlLoader.listOfExpansions.get(i).getID() + " is disabled.");
                btnExpanEnable.get(i).setText("+");
                btnExpanEnable.get(i).setToolTipText("Enable");
                expansionEnabled = 0;
                btnExpanEnable.get(i).setBackground(clrDisable);
                btnExpanEnable.get(i).setBorder(BorderFactory.createCompoundBorder( BorderFactory.createBevelBorder( BevelBorder.RAISED, clrDisableBorder, clrBlk), BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));
            }

            actionEnabler.get(i).setEnable(expansionEnabled); //update the action listener tied to the enable button with the current status of the content

            lblExpanID.get(i).setBounds(contentController.getWidth() - 80, 35, 55, 20);
            lblExpanID.get(i).setHorizontalAlignment(SwingConstants.RIGHT);
            lblExpanID.get(i).setForeground(clrText);
            lblExpanID.get(i).setFont(txtTiny);
            lblExpanID.get(i).setHorizontalAlignment(SwingConstants.RIGHT);
            lblExpanID.get(i).setText(xmlLoader.listOfExpansions.get(i).getID());


            //enable everything lel
            lblExpanID.get(i).setVisible(true);
            btnExpanEnable.get(i).setVisible(true);
            lblExpanDesc.get(i).setVisible(true);
            lblExpansions.get(i).setVisible(true);
            pnlExpansions.get(i).setVisible(true);

        }

        System.out.println("Loaded expansion data into GUI.");

        window.revalidate();
        window.repaint();
    }

    //load the loading screen and game content
    public void loadLoadingScreen(int toLoad) {

        screenScaleChoice = 8;

        //TODO: Eventually re-sort swing objects so I don't have a bunch of a empty ones lying around. Arraylists are the best bet.

        JLabel bgLoadIcon;

        load = toLoad;


        rescaleScreen(screenScaleChoice);
        window.setBounds((int)(screenWidth / 2) - (this.getUIScaleX() / 2), (int)(screenHeight / 2) - (this.getUIScaleY() / 2), this.getUIScaleX(), this.getUIScaleY());
        screen.setBounds(0, 0, getUIScaleX(), getUIScaleY());
        layers.setBounds(0, 0, getUIScaleX(), getUIScaleY()); //reset the size

        bgPanel = new JLabel(new ImageIcon(gfxRepository.loaderBackground));
        layers.add(bgPanel, new Integer(0), 0);
        bgPanel.setBounds(0, 0, getUIScaleX(), getUIScaleY());
        bgPanel.setOpaque(true);
        bgPanel.setFocusable(false);
        bgPanel.setVisible(true);

        bgLoadIcon = new JLabel(gfxRepository.loadingIcon);
        layers.add(bgLoadIcon, new Integer(1), 0);
        bgLoadIcon.setBounds((getUIScaleX() / 2) - 150, getUIScaleY() - 380, 300, 300);
        bgLoadIcon.setOpaque(false);
        bgLoadIcon.setFocusable(false);
        bgLoadIcon.setVisible(true);

        //TODO: Add a way to rescale images.


        loadingBar = new JProgressBar(0, 100);
        layers.add(loadingBar, new Integer(2), 0);
        loadingBar.setBounds(40, getUIScaleY() - 60, getUIScaleX() - 80, 30);
        loadingBar.setFocusable(false);
        loadingBar.setOpaque(true);
        loadingBar.setValue(0);
        loadingBar.setFont(txtSubheader);
        loadingBar.setForeground(clrEnable);
        loadingBar.setBackground(clrDGrey);
        loadingBar.setStringPainted(true);
        loadingBar.setBorderPainted(false);
        loadingBar.setVisible(true);

        JLabel lblInformation = new JLabel();
        layers.add(lblInformation, new Integer(3), 0);
        lblInformation.setBounds((getUIScaleX() / 2) - 300, getUIScaleY() - 120, 600, 50);
        lblInformation.setOpaque(false);
        lblInformation.setFocusable(false);
        lblInformation.setFont(txtHeader);
        lblInformation.setHorizontalAlignment(SwingConstants.CENTER);
        lblInformation.setVerticalAlignment(SwingConstants.BOTTOM);
        lblInformation.setForeground(clrText);
        lblInformation.setText("Astra Project - Work In Progress");
        lblInformation.setVisible(true);

        window.revalidate();
        window.repaint();

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
                        case 21:
                            gfxRepository.loadMainGFX();
                            break;
                        case 42:
                            gameLoader.loadXMLData();
                            break;
                        case 84:
                            gameLoader.cleanContent();
                            break;
                    }
                } else if (load == 2) {

                    Thread.sleep(40 + random.nextInt(180)); //unnecessary, but will help to reduce load

                    switch(i) {
                        case 11:
                            gameLoader.mapLoader(20, 20, 6); //TODO: Assign variables
                            break;

                    }

                }

            }

            setProgress(100);

            Thread.sleep(2000); //wait a couple before loading main screen

            return null;
        }

        protected void done() {

            loadingBar.setString("Complete");

            if (load == 1) { //load main menu

                loadMainMenu();

            } else if (load == 2) { //load map



            }


        }
    }

    private void loadMainMenu() {

        clearUI();

        try {
            backgroundImage = ImageIO.read(this.getClass().getResource("Resources/menuBG.jpg"));
            bgPanel = new JLabel(new ImageIcon(backgroundImage));
            layers.add(bgPanel, new Integer(0), 0);
            bgPanel.setBounds(0, 0, getUIScaleX(), getUIScaleY());
            bgPanel.setOpaque(true);
            bgPanel.setFocusable(false);
            bgPanel.setVisible(true);

            gameLogo = ImageIO.read(this.getClass().getResource("Resources/icon_large.png"));
            imgLogo = new JLabel(new ImageIcon(gameLogo));
            layers.add(imgLogo, new Integer(1), 0);
            imgLogo.setBounds(20, 20, 120, 120);
            imgLogo.setVisible(true);

            //TODO: Add a way to rescale images.

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        JPanel pnlMenuBarH = new JPanel();

        layers.add(pnlMenuBarH, new Integer(2), 0);
        pnlMenuBarH.setBounds(0, getUIScaleY() - 250, getUIScaleX(), 250);
        pnlMenuBarH.setLayout(null);
        pnlMenuBarH.setBackground(clrForeground);
        pnlMenuBarH.setFocusable(false);
        pnlMenuBarH.setBorder(null);
        pnlMenuBarH.setVisible(true);

        JButton btnNewGame = new JButton();
        layers.add(btnNewGame, new Integer(7), 0);
        btnNewGame.setBounds(5, getUIScaleY() - 60, 180, 55);
        btnNewGame.setBackground(clrButtonMain);
        btnNewGame.setForeground(clrText);
        btnNewGame.setBorder(BorderFactory.createCompoundBorder( BorderFactory.createBevelBorder( BevelBorder.RAISED, clrForeground, clrButtonBackground), BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));
        btnNewGame.setFont(txtHeader);
        btnNewGame.setOpaque(true); //TODO: Find a way to do semi-transparent buttons
        btnNewGame.setFocusPainted(false);
        btnNewGame.setHorizontalAlignment(SwingConstants.CENTER);
        btnNewGame.setVerticalAlignment(SwingConstants.CENTER);
        btnNewGame.setText("NEW GAME");
        btnNewGame.addActionListener(new ActionListener() { //closes the program when clicked
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Loading new game");
                clearUI();
                audioRepository.buttonClick();
                music.interrupt();
                music = new audioCore("to_the_ends_of_the_galaxy.mp3", audioRepository.musicVolume);
                music.start();
                loadLoadingScreen(2);

            }
        });


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

            audioRepository.buttonClick();

            if (enable == 1) {
                enable = 0;
                System.out.println("Disabling content for " + expID);
            } else {
                enable = 1;
                System.out.println("Enabling content for " + expID);
            }

            xmlLoader.changeExpansionInfo(expID, enable);

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




}
