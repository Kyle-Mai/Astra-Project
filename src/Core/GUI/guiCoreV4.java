package Core.GUI;

//import all relevant stuff
import Core.*;
import Core.GUI.SwingEX.*;
import Core.Player.SaveDirectoryConstants;
import Core.Player.playerData;
import Core.SFX.audioRepository;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.AffineTransformOp;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

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


public class guiCoreV4 {

    /** Stores resource declarations **/

    DecimalFormat uiFormat = new DecimalFormat("###,##0.00");

    private int tileSize = 50;
    private int screenScaleChoice = 5;

    private ArrayList<XPanel> pnlExpansions = new ArrayList<>();
    private ArrayList<XLabel> lblExpansions = new ArrayList<>();
    private ArrayList<XLabel> lblExpanDesc = new ArrayList<>();
    private ArrayList<XButton> btnExpanEnable = new ArrayList<>();
    private ArrayList<XLabel> lblExpanID = new ArrayList<>();
    private ArrayList<XPanel> pnlMods = new ArrayList<>();
    private ArrayList<XLabel> lblMods = new ArrayList<>();
    private ArrayList<XLabel> lblModAuthor = new ArrayList<>();
    private ArrayList<XButton> btnModEnable = new ArrayList<>();

    private Dimension monitorSize = Toolkit.getDefaultToolkit().getScreenSize(); //gets the screen size of the user
    private double screenWidth = monitorSize.getWidth();
    private double screenHeight = monitorSize.getHeight();

    private XPanel pnlExpansionHeader;
    private XPanel pnlModHeader;
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
    private XPanel settingsMenu;
    private animCore menuSpaceport;
    private animCore menuMoon1;
    private animCore menuMoon2;
    private XButton btnQuit;
    private XPanel pnlStarData;
    private XPanel pnlPlanetData;
    private XLabel lblLogo;
    private XPanel pnlMenuBarH;
    private XPanel pnlBG;
    private XPanel pnlLoadSaves;
    private XPanel pnlTimer;
    private XLabel planetName;
    private XLabel lblCurrentDate;
    private XPanel pnlTopBar;
    private XLabel lblTimeScale;
    private XLabel imgPauseBar;
    private XLabel lblPauseBar;

    private boolean pauseMenuOpen = false;

    private int launcherContentLoaded = 0; //tracks the content on the launcher

    private int load;


    /** UI scaling code**/
    //handles the scaling of the UI

    private Dimension screenSize;

    //methods to return the UI elements
    public int getUIScaleX() { return (int)screenSize.getWidth(); }

    public int getUIScaleY() { return (int)screenSize.getHeight(); }

    //sets the window size to the chosen monitor scale
    public void rescaleScreen(int option) {

        Dimension old = screenSize;

        switch (option) {
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


    /** Main Data **/

    //builds the core framework
    public guiCoreV4(int screenScaleOption) {

        window = new XFrame("Astra Launcher", gfxRepository.gameLogo);
        screen = new XPanel(gfxRepository.clrBlk);

        rescaleScreen(screenScaleOption);

        screen.setFocusable(true);
        screen.setVisible(true);
        window.setContentPane(screen);
        //window.getRootPane().setCursor(gfxRepository.defaultCursor);
        window.pack();
        window.setBounds((int)(screenWidth / 2) - (this.getUIScaleX() / 2), (int)(screenHeight / 2) - (this.getUIScaleY() / 2), this.getUIScaleX(), this.getUIScaleY());
        screen.setBounds(0, 0, getUIScaleX(), getUIScaleY());
        window.setVisible(true);

    }

    private void clearUI() {
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
        imgBackground.scaleImage(gfxRepository.mainBackground);
        imgBackground.setVisible(true);

        imgBorder = new XLabel(gfxRepository.launcherBorder);
        layers.add(imgBorder, new Integer(1), 0);
        imgBorder.setBounds(0, 0, getUIScaleX(), getUIScaleY());
        imgBorder.setVisible(true);

        //load exit button
        XButton btnExit = new XButton(gfxRepository.closeButton, SwingConstants.LEFT);

        layers.add(btnExit, new Integer(2), 0);
        btnExit.setBounds(getUIScaleX() - 58, 6, 38, 38);
        btnExit.setOpaque(false);

        btnExit.addMouseListener(new MouseListener() {
            XButton source;

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                audioRepository.buttonClick();
                window.closeProgram();
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
        btnLauncherAudio.setVisible(true);

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

        //load settings button
        XButton btnSettings = new XButton(gfxRepository.settingsButton, SwingConstants.LEFT);

        layers.add(btnSettings, new Integer(2), 0);
        btnSettings.setBounds(btnLauncherAudio.getX() - btnLauncherAudio.getWidth(), btnExit.getY(), btnExit.getWidth(), btnExit.getHeight());
        btnSettings.setOpaque(false);
        btnSettings.setVisible(true);

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
        contentController = new XPanel(gfxRepository.clrBackground);
        layers.add(contentController, new Integer(5), 0);
        contentList = new XScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        layers.add(contentList, new Integer(6), 0);
        contentController.setBounds(getUIScaleX() - 325, 45, 300, 310);
        contentController.setPreferredSize(new Dimension(contentController.getWidth(), contentController.getHeight()));

        XScrollBar contentScroller = new XScrollBar(gfxRepository.clrEnable, gfxRepository.clrDark);
        contentScroller.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent adjustmentEvent) {
                window.refresh();
            }
        });

        contentList.add(contentScroller);
        contentScroller.setBounds(contentController.getX() + contentController.getWidth() - 20, contentController.getY(), 15, contentController.getHeight());
        contentScroller.setVisible(true);

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
        XLabel lblExpHeaderText = new XLabel("Expansion Packs", gfxRepository.txtSubheader, gfxRepository.clrText);
        pnlExpansionHeader.add(lblExpHeaderText);
        lblExpHeaderText.setBounds(5, 5, pnlExpansionHeader.getWidth() - 10, pnlExpansionHeader.getHeight() - 10);
        lblExpHeaderText.setAlignments(SwingConstants.CENTER, SwingConstants.CENTER);
        lblExpHeaderText.setVisible(true);

        //load the mod header
        pnlModHeader = new XPanel(gfxRepository.clrDark);
        pnlModHeader.setBounds(5, pnlExpansionHeader.getY() + pnlExpansionHeader.getHeight() + 5, contentController.getWidth() - 20, 25);
        pnlModHeader.setVisible(true);

        //adds the mod header text data
        XLabel lblModHeaderText = new XLabel("Mods", gfxRepository.txtSubheader, gfxRepository.clrText);
        pnlModHeader.add(lblModHeaderText);
        lblModHeaderText.setBounds(5, 5, pnlModHeader.getWidth() - 10, pnlModHeader.getHeight() - 10);
        lblModHeaderText.setAlignments(SwingConstants.CENTER, SwingConstants.CENTER);
        lblModHeaderText.setVisible(true);

        //load launch button
        XButton btnLaunch = new XButton(gfxRepository.wideButton2, SwingConstants.LEFT);
        layers.add(btnLaunch, new Integer(7), 0);
        btnLaunch.setBounds(contentController.getX() - 10, contentController.getY() + contentController.getHeight(), 319, 80);
        btnLaunch.setOpaque(true);
        XLabel lblLaunch = new XLabel("PLAY", gfxRepository.txtButtonLarge, gfxRepository.clrText);
        layers.add(lblLaunch, new Integer(7), 0);
        lblLaunch.setBounds(btnLaunch.getX(), btnLaunch.getY(), btnLaunch.getWidth(), btnLaunch.getHeight());
        lblLaunch.setAlignments(SwingConstants.CENTER);
        lblLaunch.setVisible(true);

        btnLaunch.addMouseListener(new XMouseListener() {
            XButton source;

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                audioRepository.buttonSelect();
                window.setTitle("Astra Project"); //changes the title from the launcher to the game window
                window.refresh();
                audioRepository.musicTitleScreen(); //plays the title screen music
                clearUI();
                rescaleScreen(screenScaleChoice); //resizes the screen according to the user's choice
                loadLoadingScreen(1);
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

            //TODO: Custom tooltip design.

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
                    audioRepository.buttonHighlight();
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

        if (pnlMods.size() > 0) {
            pnlMods.clear();
            lblMods.clear();
            lblModAuthor.clear();
            btnModEnable.clear();
        }

        contentController.add(pnlModHeader);
        pnlModHeader.setBounds(5, pnlExpansions.get(pnlExpansions.size() - 1).getY() + pnlExpansions.get(pnlExpansions.size() - 1).getHeight() + 5, pnlModHeader.getWidth(), pnlModHeader.getHeight());

        for (int i = 0; i < xmlLoader.listOfMods.size(); i++) {

            pnlMods.add(new XPanel(gfxRepository.clrForeground));
            lblMods.add(new XLabel(xmlLoader.listOfMods.get(i).getModName(), gfxRepository.txtSubheader, gfxRepository.clrText));
            btnModEnable.add(new XButton(gfxRepository.rejectButton, SwingConstants.RIGHT));
            lblModAuthor.add(new XLabel()); //TODO: Add mod author.

            contentController.add(pnlMods.get(i));

            modID = xmlLoader.listOfMods.get(i).getModName();

            pnlMods.get(i).setBounds(5, 5 + (65 * launcherContentLoaded) + (pnlModHeader.getHeight() + 5) + (pnlExpansionHeader.getHeight() + 5), contentController.getWidth() - 20, 60);
            pnlMods.get(i).add(lblMods.get(i));
            pnlMods.get(i).add(btnModEnable.get(i));

            lblMods.get(i).setBounds(5, 5, 195, 25);
            lblMods.get(i).setVerticalAlignment(SwingConstants.TOP);

            btnModEnable.get(i).setBounds(contentController.getWidth() - 50, 5, 30, 30);
            btnModEnable.get(i).setForeground(gfxRepository.clrText);
            btnModEnable.get(i).setOpaque(true);
            btnModEnable.get(i).setFont(gfxRepository.txtSubheader);

            //adjust the enable/disable button based on the current status of the content
            if (xmlLoader.listOfMods.get(i).getModEnabled()) {
                //content is enabled, set the button accordingly
                btnModEnable.get(i).setIcon(new ImageIcon(gfxRepository.acceptButton));
                btnModEnable.get(i).toggleState();
            } else {
                //content is disabled, set the button accordingly
                btnModEnable.get(i).setIcon(new ImageIcon(gfxRepository.rejectButton));
            }

            btnModEnable.get(i).addMouseListener(new XMouseListener(modID) {
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

                    xmlLoader.changeModInfo(getIDValue(), source.isState());

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
                    audioRepository.buttonHighlight();
                    window.refresh();
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                    source = (XButton)mouseEvent.getSource();
                    source.setHorizontalAlignment(SwingConstants.RIGHT);
                    window.refresh();
                }
            });

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
        bgLoadIcon.setBounds((getUIScaleX() / 2) - 240, getUIScaleY() - 400, 480, 320);
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
        XLabel lblInformation = new XLabel("Astra Project - Work In Progress", gfxRepository.txtLargeText, gfxRepository.clrText);
        layers.add(lblInformation, new Integer(3), 0);
        lblInformation.setBounds((getUIScaleX() / 2) - 500, getUIScaleY() - 120, 1000, 50);
        lblInformation.setAlignments(SwingConstants.CENTER, SwingConstants.BOTTOM);
        lblInformation.setVisible(true);

        window.refresh();

        loadingBar.setIndeterminate(true);

        //open a new thread to load content in the background
        backgroundLoader loader = new backgroundLoader();
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
                            gfxRepository.loadMainGFX(); //loads the main GFX content
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
                        case 1:
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
                        case 43: //save the data to the user file
                            break;
                        case 44:
                            break;
                        case 45:
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

            loadingBar.setString("Loading Complete.");

            if (load == 1) { //load main menu

                clearUI();
                loadMainMenu();
                loadMainMenuAnimation();

            } else if (load == 2) { //load map

                audioRepository.musicShuffle();
                audioRepository.ambianceMainGame();
                setMainKeybindings();
                loadMapView();
                gameSettings.turn = new turnTicker();
                gameSettings.turn.start();

            }
        }
    }

    //property listener for the loading bar
    private class propertyListener implements PropertyChangeListener { //monitors the loading bar and changes it

        public void propertyChange(PropertyChangeEvent e) {
            if ("progress".equals(e.getPropertyName())) {
                int progress = (Integer)e.getNewValue();
                loadingBar.setIndeterminate(false);
                loadingBar.setValue(progress);
            }

        }
    }

    private void setMainKeybindings() { //adds keybindings to the game

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
            }
        });

        System.out.println("Main game keybindings added.");

    }


    /** Main menu elements **/

    //separates the main menu animation loading
    private void loadMainMenuAnimation() {

        //TODO: Should eventually position buttons as a metric of 1/4th the screen width rather than side by side, will not port well to higher resolutions right now

        Random randomizePosition = new Random();

        menuSpaceport = new animCore(new ImageIcon(gfxRepository.menuSpaceport), 2, layers, window);
        menuSpaceport.setAnimationSmoothness(0.1, 200);
        menuSpaceport.start();

        menuMoon2 = new animCore(new ImageIcon(gfxRepository.moon2Icon), 3, layers, window, window.getWidth() - 300, -200, 640);
        menuMoon2.setAnimationSmoothness(0.1, 180);
        menuMoon2.setAnimationStartTime(randomizePosition.nextInt(359)); //randomizes the starting position of the moons
        menuMoon2.start();

        menuMoon1 = new animCore(new ImageIcon(gfxRepository.moon1Icon), 3, layers, window, window.getWidth() - 500, -100, 600);
        menuMoon1.setAnimationSmoothness(0.1, 150);
        menuMoon1.setAnimationStartTime(randomizePosition.nextInt(359)); //randomizes the starting position of the moons
        menuMoon1.start();

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

        lblLogo = new XLabel("Astra Project", gfxRepository.txtTitle, gfxRepository.clrText);
        layers.add(lblLogo, new Integer(11), 0);
        lblLogo.setBounds(10, 10, 600, 60);
        lblLogo.setAlignments(SwingConstants.LEFT, SwingConstants.TOP);
        lblLogo.setVisible(true);

        XLabel menuPlanet = new XLabel(gfxRepository.menuPlanet);
        layers.add(menuPlanet, new Integer(8), 0);
        menuPlanet.setBounds(window.getWidth() - 1100, 0, 1500, 450);
        menuPlanet.setVisible(true);

        XLabel lblLensGlare = new XLabel(gfxRepository.menuGlare);
        layers.add(lblLensGlare, new Integer(9), 0);
        lblLensGlare.setBounds(0, 0, screen.getWidth(), screen.getHeight());
        lblLensGlare.setVisible(true);

        pnlMenuBarH = new XPanel(gfxRepository.clrBackground);
        layers.add(pnlMenuBarH, new Integer(14), 0);
        pnlMenuBarH.setBounds(0, getUIScaleY() - 95, getUIScaleX(), 95);
        pnlMenuBarH.setVisible(true);

        XLabel lblBottom = new XLabel("Powered by SwingEX", gfxRepository.txtTiny, gfxRepository.clrText);
        pnlMenuBarH.add(lblBottom);
        lblBottom.setBounds(pnlMenuBarH.getWidth() - 305, pnlMenuBarH.getHeight() - 45, 300, 40);
        lblBottom.setAlignments(SwingConstants.RIGHT, SwingConstants.BOTTOM);

        XLabel lblNewGame = new XLabel("New Game", gfxRepository.txtButtonLarge, gfxRepository.clrText);
        pnlMenuBarH.add(lblNewGame);
        lblNewGame.setBounds(0, 0, 319, 80);
        lblNewGame.setAlignments(SwingConstants.CENTER);
        lblNewGame.setVisible(true);

        XButton btnNewGame = new XButton(gfxRepository.wideButton2, SwingConstants.LEFT);
        pnlMenuBarH.add(btnNewGame);
        btnNewGame.setBounds(lblNewGame.getX(), lblNewGame.getY(), lblNewGame.getWidth(), lblNewGame.getHeight());
        btnNewGame.setOpaque(true);

        btnNewGame.addMouseListener(new MouseListener() {
            XButton source;

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                audioRepository.buttonClick();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                window.refresh();

                pnlMenuBarH.setVisible(false);
                loadNewSettingsMenu();
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

        XLabel lblLoadGame = new XLabel("Load Game", gfxRepository.txtButtonLarge, gfxRepository.clrText);
        pnlMenuBarH.add(lblLoadGame);
        lblLoadGame.setBounds(btnNewGame.getX() + btnNewGame.getWidth(), btnNewGame.getY(), btnNewGame.getWidth(), btnNewGame.getHeight());
        lblLoadGame.setAlignments(SwingConstants.CENTER);
        lblLoadGame.setVisible(true);

        XButton btnLoadGame = new XButton(gfxRepository.wideButton2, SwingConstants.LEFT);
        pnlMenuBarH.add(btnLoadGame);
        btnLoadGame.setBounds(lblLoadGame.getX(), lblLoadGame.getY(), lblLoadGame.getWidth(), lblLoadGame.getHeight());
        btnLoadGame.setVisible(true);

        btnLoadGame.addMouseListener(new XMouseListener() {
            XButton source;

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                audioRepository.buttonClick();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                pnlMenuBarH.setVisible(false);
                window.refresh();

                pnlLoadSaves.setVisible(true);
                loadSavesMenu();
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

        XLabel lblSettings = new XLabel("Options", gfxRepository.txtButtonLarge, gfxRepository.clrText);
        pnlMenuBarH.add(lblSettings);
        lblSettings.setBounds(btnLoadGame.getX() + btnLoadGame.getWidth(), btnNewGame.getY(), btnNewGame.getWidth(), btnNewGame.getHeight());
        lblSettings.setAlignments(SwingConstants.CENTER);
        lblSettings.setVisible(true);

        XButton btnSettings = new XButton(gfxRepository.wideButton2, SwingConstants.LEFT);
        pnlMenuBarH.add(btnSettings);
        btnSettings.setBounds(lblSettings.getX(), lblSettings.getY(), lblSettings.getWidth(), lblSettings.getHeight());
        btnSettings.setVisible(true);

        btnSettings.addMouseListener(new XMouseListener() {
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

        XLabel lblQuit = new XLabel("Quit", gfxRepository.txtButtonLarge, gfxRepository.clrText);
        pnlMenuBarH.add(lblQuit);
        lblQuit.setBounds(btnSettings.getX() + btnSettings.getWidth(), btnNewGame.getY(), btnNewGame.getWidth(), btnNewGame.getHeight());
        lblQuit.setAlignments(SwingConstants.CENTER);
        lblQuit.setVisible(true);

        XButton btnQuit = new XButton(gfxRepository.wideButton2, SwingConstants.LEFT);
        pnlMenuBarH.add(btnQuit);
        btnQuit.setBounds(lblQuit.getX(), lblQuit.getY(), lblQuit.getWidth(), lblQuit.getHeight());
        btnQuit.setVisible(true);

        btnQuit.addMouseListener(new XMouseListener() {
            XButton source;

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                audioRepository.buttonClick();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                window.refresh();
                window.closeProgram();
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

        settingsMenu = new XPanel(gfxRepository.clrBackground);
        layers.add(settingsMenu, new Integer(13), 0);
        settingsMenu.setBounds(5, 50, 495, window.getHeight() - 100);
        settingsMenu.setVisible(false);

        pnlLoadSaves = new XPanel(gfxRepository.clrBackground);
        layers.add(pnlLoadSaves, new Integer(14), 0);
        pnlLoadSaves.setBounds(5, (window.getHeight() / 2) - 425, 750, 850);
        pnlLoadSaves.setVisible(false);

        window.refresh();

    }

    //loads a menu with all of the save files in it
    private void loadSavesMenu() { //TODO: Fill out

        XLabel lblLoadSaves = new XLabel("Load Saved Game", gfxRepository.txtLargeText, gfxRepository.clrText);
        pnlLoadSaves.add(lblLoadSaves);
        lblLoadSaves.setBounds(0, 5, pnlLoadSaves.getWidth(), 40);
        lblLoadSaves.setAlignments(SwingConstants.CENTER);
        lblLoadSaves.setVisible(true);

        XPanel pnlSavesList = new XPanel(gfxRepository.clrDGrey);
        pnlLoadSaves.add(pnlSavesList);
        pnlSavesList.setBounds(5, 80, 319, pnlLoadSaves.getHeight() - 160);
        pnlSavesList.setVisible(true);

        //button to handle returning to the title screen options
        XLabel lblReturn = new XLabel("Back", gfxRepository.txtButtonLarge, gfxRepository.clrText);
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
                window.refresh();

                pnlMenuBarH.setVisible(true);
                lblLogo.setVisible(true);
                pnlLoadSaves.removeAll();
                pnlLoadSaves.setVisible(false);
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
                    System.out.println("Illegal alien detected in saves folder, clearing it."); //"undocumented file"
                    current.delete(); //deport, er, delete the intruding file. shouldn't be here
                }
            }

            for (int i = 0; i < xmlLoader.listOfPlayers.size(); i++) { //lists out all of the save files








            }
        } catch (NullPointerException e) { //catches any null errors so the game doesn't just explode


        }

        window.refresh();

    }

    //loads the menu to set up a new game
    private void loadNewSettingsMenu() {

        settingsMenu.removeAll();
        settingsMenu.setVisible(true);

        XLabel lblSettingsHeader = new XLabel("Game Settings", gfxRepository.txtHeader, gfxRepository.clrText);
        settingsMenu.add(lblSettingsHeader);
        lblSettingsHeader.setBounds(5, 5, settingsMenu.getWidth() - 5, 40);
        lblSettingsHeader.setAlignments(SwingConstants.CENTER);
        lblSettingsHeader.setVisible(true);

        XLabel lblBack = new XLabel("Back", gfxRepository.txtButtonLarge, gfxRepository.clrText);
        settingsMenu.add(lblBack);
        lblBack.setBounds(30, settingsMenu.getHeight() - 80, 435, 80);
        lblBack.setAlignments(SwingConstants.CENTER);
        lblBack.setVisible(true);

        XButton btnBack = new XButton(gfxRepository.button435_80, SwingConstants.LEFT);
        settingsMenu.add(btnBack);
        btnBack.setBounds(lblBack.getBounds());
        btnBack.setVisible(true);

        btnBack.addMouseListener(new XMouseListener() {
            XButton source;

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                audioRepository.buttonDisable();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                window.refresh();

                pnlMenuBarH.setVisible(true);
                lblLogo.setVisible(true);
                settingsMenu.removeAll();
                settingsMenu.setVisible(false);
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

        XLabel lblLaunchNew = new XLabel("Start", gfxRepository.txtButtonLarge, gfxRepository.clrText);
        settingsMenu.add(lblLaunchNew);
        lblLaunchNew.setBounds(btnBack.getX(), btnBack.getY() - 85, btnBack.getWidth(), btnBack.getHeight());
        lblLaunchNew.setAlignments(SwingConstants.CENTER);
        lblLaunchNew.setVisible(true);

        XButton launchNewGame = new XButton(gfxRepository.button435_80, SwingConstants.LEFT);
        settingsMenu.add(launchNewGame);
        launchNewGame.setBounds(lblLaunchNew.getBounds());
        launchNewGame.setOpaque(true);
        launchNewGame.setVisible(true);

        launchNewGame.addMouseListener(new XMouseListener() {
            XButton source;

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                audioRepository.buttonConfirm();
                window.refresh();

                menuSpaceport.stopAnimation();
                menuMoon1.stopAnimation();
                menuMoon2.stopAnimation();
                clearUI();
                loadLoadingScreen(2);
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

        //sets up settings option for resource abundance
        XLabel lblResources = new XLabel("Resource Abundance", gfxRepository.txtSubheader, gfxRepository.clrText);
        lblResources.setAlignments(SwingConstants.CENTER);
        XSlider sldResources = new XSlider(JSlider.HORIZONTAL, gameSettings.resourceAbundanceMin, gameSettings.resourceAbundanceHigh, gameSettings.resourceAbundanceAvg);
        settingsMenu.add(lblResources);
        settingsMenu.add(sldResources);
        lblResources.setBounds(5, 50, settingsMenu.getWidth() - 5, 25);
        lblResources.setVisible(true);
        sldResources.setTicks(25, 5);
        Hashtable hshResources = new Hashtable();
        hshResources.put(new Integer(gameSettings.resourceAbundanceMin), new XLabel("Sparse", gfxRepository.txtTiny, gfxRepository.clrText));
        hshResources.put(new Integer(gameSettings.resourceAbundanceHigh), new XLabel("Abundant", gfxRepository.txtTiny, gfxRepository.clrText));
        hshResources.put(new Integer(gameSettings.resourceAbundanceAvg), new XLabel("Average", gfxRepository.txtTiny, gfxRepository.clrText));
        sldResources.setLabelTable(hshResources);
        sldResources.setFont(gfxRepository.txtTiny);
        sldResources.setForeground(gfxRepository.clrText);
        sldResources.setBounds(20, lblResources.getY() + lblResources.getHeight() + 5, settingsMenu.getWidth() - 20, 50);
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
        XSlider sldStarFreq = new XSlider(JSlider.HORIZONTAL, gameSettings.starFreqMin, gameSettings.starFreqHigh, gameSettings.starFreqAvg);
        settingsMenu.add(lblStarFreq);
        settingsMenu.add(sldStarFreq);
        lblStarFreq.setBounds(5, sldResources.getY() + sldResources.getHeight() + 5, lblResources.getWidth(), lblResources.getHeight());
        lblStarFreq.setVisible(true);
        sldStarFreq.setTicks(15, 5);
        Hashtable hshStarFreq = new Hashtable();
        hshStarFreq.put(new Integer(gameSettings.starFreqMin), new XLabel("Many", gfxRepository.txtTiny, gfxRepository.clrText));
        hshStarFreq.put(new Integer(gameSettings.starFreqHigh), new XLabel("Few", gfxRepository.txtTiny, gfxRepository.clrText));
        hshStarFreq.put(new Integer(gameSettings.starFreqAvg), new XLabel("Average", gfxRepository.txtTiny, gfxRepository.clrText));
        sldStarFreq.setLabelTable(hshStarFreq);
        sldStarFreq.setFont(gfxRepository.txtTiny);
        sldStarFreq.setForeground(gfxRepository.clrText);
        sldStarFreq.setBounds(20, lblStarFreq.getY() + lblStarFreq.getHeight() + 5, settingsMenu.getWidth() - 20, sldResources.getHeight());
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
        XSlider sldMapScale = new XSlider(JSlider.HORIZONTAL, gameSettings.mapScaleMin, gameSettings.mapScaleHigh, gameSettings.mapScaleAvg);
        settingsMenu.add(lblMapScale);
        settingsMenu.add(sldMapScale);
        lblMapScale.setBounds(5, sldStarFreq.getY() + sldStarFreq.getHeight() + 5, lblResources.getWidth(), lblResources.getHeight());
        lblMapScale.setVisible(true);
        sldMapScale.setTicks(20, 5);
        Hashtable hshMapScale = new Hashtable();
        hshMapScale.put(new Integer(gameSettings.mapScaleMin), new XLabel("Small", gfxRepository.txtTiny, gfxRepository.clrText));
        hshMapScale.put(new Integer(gameSettings.mapScaleHigh), new XLabel("Huge", gfxRepository.txtTiny, gfxRepository.clrText));
        hshMapScale.put(new Integer(gameSettings.mapScaleAvg), new XLabel("Normal", gfxRepository.txtTiny, gfxRepository.clrText));
        sldMapScale.setLabelTable(hshMapScale);
        sldMapScale.setFont(gfxRepository.txtTiny);
        sldMapScale.setForeground(gfxRepository.clrText);
        sldMapScale.setBounds(20, lblMapScale.getY() + lblMapScale.getHeight() + 5, settingsMenu.getWidth() - 20, sldResources.getHeight());
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
        XSlider sldDiffOverall = new XSlider(JSlider.HORIZONTAL, gameSettings.overallDifficultyMin, gameSettings.overallDifficultyHigh, gameSettings.overallDifficultyAvg);
        settingsMenu.add(lblDiffOverall);
        settingsMenu.add(sldDiffOverall);
        lblDiffOverall.setBounds(5, sldMapScale.getY() + sldMapScale.getHeight() + 5, lblResources.getWidth(), lblResources.getHeight());
        lblDiffOverall.setVisible(true);
        sldDiffOverall.setTicks(25, 5);
        Hashtable hshDiffOverall = new Hashtable();
        hshDiffOverall.put(new Integer(gameSettings.overallDifficultyMin), new XLabel("Easy", gfxRepository.txtTiny, gfxRepository.clrText));
        hshDiffOverall.put(new Integer(gameSettings.overallDifficultyHigh), new XLabel("Hard", gfxRepository.txtTiny, gfxRepository.clrText));
        hshDiffOverall.put(new Integer(gameSettings.overallDifficultyAvg), new XLabel("Normal", gfxRepository.txtTiny, gfxRepository.clrText));
        sldDiffOverall.setLabelTable(hshDiffOverall);
        sldDiffOverall.setFont(gfxRepository.txtTiny);
        sldDiffOverall.setForeground(gfxRepository.clrText);
        sldDiffOverall.setBounds(20, lblDiffOverall.getY() + lblDiffOverall.getHeight() + 5, settingsMenu.getWidth() - 20, sldResources.getHeight());
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
                    mapGFX.get(i).add(new XLabel(gameSettings.map.mapTiles.get(i).get(j).getStarData().getIconGFX())); //adds the star's icon to the map

                    if (gameSettings.map.mapTiles.get(i).get(j).getStarData().isHomeSystem()) {
                        XLabel homeSystem = new XLabel(gfxRepository.homeSystem);
                        pnlBG.add(homeSystem);
                        homeSystem.setBounds(tileSize * (positionX + 1) - 20, tileSize * (positionY + 1) - 20, tileSize, tileSize);
                        homeSystem.setAlignments(SwingConstants.CENTER);
                        homeSystem.setVisible(true);
                    }

                    XLabel starName = new XLabel(gameSettings.map.mapTiles.get(i).get(j).getStarData().getStarName(), gfxRepository.txtItalSubtitle, gfxRepository.clrText);
                    pnlBG.add(starName);
                    starName.setBounds(tileSize * (positionX + 1) - 25, tileSize * (positionY + 1) + 25, tileSize + 50, tileSize);
                    starName.setAlignments(SwingConstants.CENTER);
                    starName.setVisible(true);

                    mapButton.get(i).add(new XButton(gfxRepository.mapHighlight, SwingConstants.LEFT));
                    mapGFX.get(i).get(j).add(mapButton.get(i).get(j));
                    mapButton.get(i).get(j).setOpaque(false);
                    mapButton.get(i).get(j).setVisible(true);
                    mapButton.get(i).get(j).setBounds(0, 0, tileSize, tileSize);

                    mapButton.get(i).get(j).addMouseListener(new XMouseListener(i, j) {
                        XButton source;

                        @Override
                        public void mouseClicked(MouseEvent mouseEvent) {
                            source = (XButton)mouseEvent.getSource();
                            audioRepository.starSound();
                            source.setHorizontalAlignment(SwingConstants.RIGHT);
                            window.refresh();
                            loadStarData(gameSettings.map.mapTiles.get(getValueX()).get(getValueY()).getStarData());
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
                            window.refresh();
                        }

                        @Override
                        public void mouseExited(MouseEvent mouseEvent) {
                            source = (XButton)mouseEvent.getSource();
                            source.setHorizontalAlignment(SwingConstants.LEFT);
                            window.refresh();
                        }
                    });
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

        pnlTopBar = new XPanel();
        layers.add(pnlTopBar, new Integer(8), 0);
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

        XLabel lblMenu = new XLabel(gfxRepository.menuButton);
        pnlTopBar.add(lblMenu);
        lblMenu.setBounds(pnlTopBar.getWidth() - 99, 2, 99, 48);
        lblMenu.setAlignments(SwingConstants.CENTER);
        lblMenu.setVisible(true);

        XButton btnMenu = new XButton(gfxRepository.button99_48, SwingConstants.LEFT);
        pnlTopBar.add(btnMenu);
        btnMenu.setBounds(lblMenu.getBounds());
        btnMenu.setVisible(true);

        btnMenu.addMouseListener(new XMouseListener() {
            XButton source;

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
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

        XButton btnTech = new XButton(gfxRepository.techMenu, SwingConstants.LEFT);
        pnlTopBar.add(btnTech);
        btnTech.setBounds(70, 0, 73, 53);
        btnTech.setVisible(true);

        btnTech.addMouseListener(new XMouseListener() {
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

        XButton btnEmpire = new XButton(gfxRepository.empireMenu, SwingConstants.LEFT);
        pnlTopBar.add(btnEmpire);
        btnEmpire.setBounds(btnTech.getX() + btnTech.getWidth() + 2, btnTech.getY(), btnTech.getWidth(), btnTech.getHeight());
        btnEmpire.setVisible(true);

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
        pnlTopBar.add(btnGovernment);
        btnGovernment.setBounds(btnEmpire.getX() + btnEmpire.getWidth() + 2, btnEmpire.getY(), btnEmpire.getWidth(), btnEmpire.getHeight());
        btnGovernment.setVisible(true);

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
        pnlTopBar.add(btnFleet);
        btnFleet.setBounds(btnGovernment.getX() + btnGovernment.getWidth() + 2, btnGovernment.getY(), btnGovernment.getWidth(), btnGovernment.getHeight());
        btnFleet.setVisible(true);

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

        XLabel icnTech = new XLabel(gfxRepository.researchIcon);
        pnlTopBar.add(icnTech);
        icnTech.setBounds(btnFleet.getX() + btnFleet.getWidth() + 10, 9, 34, 34);
        icnTech.setVisible(true);

        XLabel lblTech = new XLabel(": " + uiFormat.format(gameSettings.player.getResearchTurn()) + "/mo", gfxRepository.txtSubtitle, gfxRepository.clrText);
        pnlTopBar.add(lblTech);
        lblTech.setBounds(icnTech.getX() + icnTech.getWidth() + 2, icnTech.getY(), 90, icnTech.getHeight());
        lblTech.setVerticalAlignment(SwingConstants.CENTER);
        lblTech.setVisible(true);

        XLabel icnEnergy = new XLabel(gfxRepository.energyIcon);
        pnlTopBar.add(icnEnergy);
        icnEnergy.setBounds(lblTech.getX() + lblTech.getWidth() + 10, icnTech.getY(), 34, 34);
        icnEnergy.setVisible(true);

        XLabel lblEnergy = new XLabel(": " + uiFormat.format(gameSettings.player.getFunds()) + " (" + uiFormat.format(gameSettings.player.getCurrencyTurn()) + "/mo)", gfxRepository.txtSubtitle, gfxRepository.clrText);
        if (gameSettings.player.getCurrencyTurn() < 0) { //if the value is negative, display accordingly
            lblEnergy.setForeground(gfxRepository.clrDisable);
        }
        pnlTopBar.add(lblEnergy);
        lblEnergy.setBounds(icnEnergy.getX() + icnEnergy.getWidth() + 2, icnEnergy.getY(), lblTech.getWidth() + 80, icnEnergy.getHeight());
        lblEnergy.setVerticalAlignment(SwingConstants.CENTER);
        lblEnergy.setVisible(true);

        XLabel icnMinerals = new XLabel(gfxRepository.mineralsIcon);
        pnlTopBar.add(icnMinerals);
        icnMinerals.setBounds(lblEnergy.getX() + lblEnergy.getWidth() + 10, icnTech.getY(), 34, 34);
        icnMinerals.setVisible(true);

        XLabel lblMinerals = new XLabel(": " + uiFormat.format(gameSettings.player.getResources()) + " (" + uiFormat.format(gameSettings.player.getResourcesTurn()) + "/mo)", gfxRepository.txtSubtitle, gfxRepository.clrText);
        if (gameSettings.player.getResourcesTurn() < 0) { //if the value is negative, display accordingly
            lblMinerals.setForeground(gfxRepository.clrDisable);
        }
        pnlTopBar.add(lblMinerals);
        lblMinerals.setBounds(icnMinerals.getX() + icnMinerals.getWidth() + 2, icnMinerals.getY(), lblEnergy.getWidth(), icnMinerals.getHeight());
        lblMinerals.setVerticalAlignment(SwingConstants.CENTER);
        lblMinerals.setVisible(true);

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
        lblBackground.scaleImage(gfxRepository.menuBackground);
        lblBackground.setVisible(true);

        XLabel lblStatsBox = new XLabel(gfxRepository.tallBox);
        lblBackground.add(lblStatsBox);
        lblStatsBox.setBounds(lblBackground.getWidth() - 174, 50, 164, 470);
        lblStatsBox.setVisible(true);

        //adds an icon/text with the number of planets in the system
        XLabel lblPlanetCountImg = new XLabel(gfxRepository.starPlanetCount);
        lblStatsBox.add(lblPlanetCountImg);
        lblPlanetCountImg.setBounds(15, 20, 30, 30);
        lblPlanetCountImg.setToolTipText("Planets");
        lblPlanetCountImg.setVisible(true);

        XLabel lblPlanetCountText = new XLabel(" : " + star.getNumOfPlanets(), gfxRepository.txtSubtitle, gfxRepository.clrText);
        lblStatsBox.add(lblPlanetCountText);
        lblPlanetCountText.setBounds(lblPlanetCountImg.getX() + lblPlanetCountImg.getWidth() + 5, lblPlanetCountImg.getY() - 5, 70, 40);
        lblPlanetCountText.setAlignments(SwingConstants.LEFT, SwingConstants.CENTER);
        lblPlanetCountText.setVisible(true);

        //adds an icon/text with the number of colonies in the system
        XLabel lblColonyCountImg = new XLabel(gfxRepository.colonyCount);
        lblStatsBox.add(lblColonyCountImg);
        lblColonyCountImg.setBounds(lblPlanetCountImg.getX(), lblPlanetCountImg.getY() + lblPlanetCountImg.getHeight() + 10, 30, 30);
        lblColonyCountImg.setToolTipText("Colonies");
        lblColonyCountImg.setVisible(true);

        XLabel lblColonyCountText = new XLabel(" : " + star.getColonyCount(), gfxRepository.txtSubtitle, gfxRepository.clrText);
        lblStatsBox.add(lblColonyCountText);
        lblColonyCountText.setBounds(lblColonyCountImg.getX() + lblColonyCountImg.getWidth() + 5, lblColonyCountImg.getY() - 10, 70, 40);
        lblColonyCountText.setAlignments(SwingConstants.LEFT, SwingConstants.CENTER);
        lblColonyCountText.setVisible(true);

        //adds a button to enter the system view
        XLabel lblEnterSystem = new XLabel("Enter System", gfxRepository.txtButtonSmall, gfxRepository.clrText);
        lblBackground.add(lblEnterSystem);
        lblEnterSystem.setBounds(lblStatsBox.getX() - 4, lblStatsBox.getY() + lblStatsBox.getHeight(), 170, 64);
        lblEnterSystem.setAlignments(SwingConstants.CENTER);
        lblEnterSystem.setVisible(true);

        XButton btnEnterSystem = new XButton(gfxRepository.wideButton, SwingConstants.LEFT);
        lblBackground.add(btnEnterSystem);
        btnEnterSystem.setBounds(lblEnterSystem.getBounds());
        btnEnterSystem.setVisible(true);

        btnEnterSystem.addMouseListener(new XMouseListener(star.getMapLocationX(), star.getMapLocationY()) {

            XButton source;

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                audioRepository.buttonClick();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                window.refresh();

                showSystemView(getValueX(), getValueY());
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

        //displays the star's name
        XLabel lblStarName = new XLabel();
        if (star.isBinarySystem()) {
            lblStarName.setText(star.getStarName() + " - Binary " +star.getStarClassName(), gfxRepository.txtHeader, gfxRepository.clrText);
        } else {
            lblStarName.setText(star.getStarName() + " - " +star.getStarClassName(), gfxRepository.txtHeader, gfxRepository.clrText);
        }
        lblBackground.add(lblStarName);
        lblStarName.setBounds((pnlStarData.getWidth() / 2) - 350, 5, 700, 40);
        lblStarName.setAlignments(SwingConstants.CENTER);
        lblStarName.setVisible(true);

        //displays the star class's description
        XLabel lblStarDesc = new XLabel("<html>" + star.getStarClassDesc() + "</html>", gfxRepository.txtItalSubtitle, gfxRepository.clrText);
        lblBackground.add(lblStarDesc);
        lblStarDesc.setBounds(40, lblStarName.getY() + lblStarName.getHeight() + 15, lblStatsBox.getX() - 40, 200);
        lblStarDesc.setAlignments(SwingConstants.CENTER, SwingConstants.TOP);
        lblStarDesc.setVisible(true);

        //displays the close button
        XButton btnClose = new XButton(gfxRepository.closeButton, SwingConstants.LEFT);
        lblBackground.add(btnClose);
        btnClose.setBounds(lblBackground.getWidth() - 48, 10, 38, 38);
        btnClose.setOpaque(true);
        btnClose.setVisible(true);

        btnClose.addMouseListener(new XMouseListener() {
            XButton source;

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                audioRepository.buttonDisable();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                window.refresh();

                pnlStarData.removeAll();
                pnlStarData.setVisible(false);
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

        window.refresh();


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

                if (source.isState()) {
                    audioRepository.buttonConfirm();
                    window.closeProgram();
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

    private void showSystemView(int x, int y) {

        clearUI();

        int planetPosition = 0;

        ArrayList<planetButton> planet = new ArrayList<>();

        starClass star = gameSettings.map.mapTiles.get(y).get(x).getStarData();

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

            Random r = new Random();

            if (star.planetList.get(i).isHomePlanet()) {
                XLabel imgHomePlanet = new XLabel(gfxRepository.homePlanet);
                pnlBG.add(imgHomePlanet);
                imgHomePlanet.setBounds(((pnlBG.getWidth() / 2) + 225) + planetPosition, (pnlBG.getHeight() / 2) + 7, 32, 32);
                imgHomePlanet.setVisible(true);
            } else if (star.planetList.get(i).getPlanetColony() != null) {
                XLabel imgColony = new XLabel(gfxRepository.colonyIcon);
                pnlBG.add(imgColony);
                imgColony.setBounds(((pnlBG.getWidth() / 2) + 225) + planetPosition, (pnlBG.getHeight() / 2) + 15, 32, 25);
                imgColony.setVisible(true);
            }

            XLabel imgPlanet = new XLabel();
            pnlBG.add(imgPlanet);
            imgPlanet.setBounds(((pnlBG.getWidth() / 2) + 250) + planetPosition, (pnlBG.getHeight() / 2) - 25, 50, 50);
            imgPlanet.scaleImage(planetCore.listOfPlanets.get(star.planetList.get(i).getArrayLoc()).getPlanetIcon());
            imgPlanet.setVisible(true);

            planet.add(new planetButton(star, i));
            //pnlBG.add(planet.get(i));
            //planet.get(i).setBounds(((pnlBG.getWidth() / 2) + 250) + (90 * i), (pnlBG.getHeight() / 2) - 25, 50, 50);
            imgPlanet.add(planet.get(i));
            planet.get(i).setBounds(0, 0, imgPlanet.getWidth(), imgPlanet.getHeight());
            planet.get(i).setBackground(gfxRepository.clrInvisible);
            planet.get(i).setPlanet(star.planetList.get(i));
            planet.get(i).setBorder(null);
            planet.get(i).setOpaque(false);
            planet.get(i).setVisible(true);

            planet.get(i).addMouseListener(new XMouseListener() {
                planetButton source;

                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    source = (planetButton)mouseEvent.getSource();
                    audioRepository.buttonClick();
                    source.setHorizontalAlignment(SwingConstants.RIGHT);
                    window.refresh();

                    loadPlanetData(source.getPlanet());
                }

                @Override
                public void mousePressed(MouseEvent mouseEvent) {
                    source = (planetButton)mouseEvent.getSource();
                    source.setHorizontalAlignment(SwingConstants.RIGHT);
                    window.refresh();
                }

                @Override
                public void mouseReleased(MouseEvent mouseEvent) {
                    source = (planetButton)mouseEvent.getSource();
                    source.setHorizontalAlignment(SwingConstants.LEFT);
                    window.refresh();
                }

                @Override
                public void mouseEntered(MouseEvent mouseEvent) {
                    source = (planetButton)mouseEvent.getSource();
                    source.setHorizontalAlignment(SwingConstants.CENTER);
                    planetName = new XLabel();
                    source.add(planetName);
                    planetName.setBounds(source.getBounds());
                    //planetName.setText(source.getPlanet().getPlanetName(), gfxRepository.txtItalSubtitle, gfxRepository.clrText);
                    planetName.setText("View", gfxRepository.txtItalSubtitle, gfxRepository.clrText);
                    planetName.setAlignments(SwingConstants.CENTER, SwingConstants.BOTTOM);
                    planetName.setVisible(true);

                    window.refresh();
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                    source = (planetButton)mouseEvent.getSource();
                    source.setHorizontalAlignment(SwingConstants.LEFT);
                    source.removeAll();
                    window.refresh();
                }
            });

            planetPosition = planetPosition + (100 + r.nextInt(200)); //TODO: Should eventually weight a little better....

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

    private void loadPlanetData(planetClass planet) {

        //TODO: Can probably reuse star panel...?

        pnlPlanetData.removeAll();

        audioRepository.planetAmbiance(planet.getPlanetType());

        pnlPlanetData.setVisible(true);

        XLabel lblPlanetPortrait = new XLabel(planetCore.listOfPlanets.get(planet.getArrayLoc()).getGfxImage(), gfxRepository.clrTrueBlack);
        pnlPlanetData.add(lblPlanetPortrait);
        lblPlanetPortrait.setBounds((pnlPlanetData.getWidth() / 2) - 280, 0, 560, 185);
        lblPlanetPortrait.setVisible(true);

        XLabel lblPortraitBorder = new XLabel(gfxRepository.portraitBorder);
        lblPlanetPortrait.add(lblPortraitBorder);
        lblPortraitBorder.setBounds(0, 0, lblPlanetPortrait.getWidth(), lblPlanetPortrait.getHeight());
        lblPortraitBorder.setVisible(true);

        //displays the background
        XLabel lblBackground = new XLabel(gfxRepository.menuBackground, gfxRepository.clrBGOpaque);
        pnlPlanetData.add(lblBackground);
        lblBackground.setBounds(0, lblPlanetPortrait.getHeight(), pnlPlanetData.getWidth(), pnlPlanetData.getHeight() - lblPlanetPortrait.getHeight());
        lblBackground.scaleImage(gfxRepository.menuBackground);
        lblBackground.setVisible(true);

        XLabel lblStatsBox = new XLabel(gfxRepository.tallBox);
        lblBackground.add(lblStatsBox);
        lblStatsBox.setBounds(lblBackground.getWidth() - 174, 50, 164, 470);
        lblStatsBox.setVisible(true);

        XLabel lblPlanetName = new XLabel();
        lblPlanetName.setText(planet.getPlanetName() + " - " + planet.getPlanetClassName() , gfxRepository.txtHeader, gfxRepository.clrText);
        lblBackground.add(lblPlanetName);
        lblPlanetName.setBounds((pnlPlanetData.getWidth() / 2) - 350, 5, 700, 40);
        lblPlanetName.setAlignments(SwingConstants.CENTER);
        lblPlanetName.setVisible(true);

        XLabel lblPlanetDesc = new XLabel("<html>" + planet.getPlanetClassDesc() + "</html>", gfxRepository.txtItalSubtitle, gfxRepository.clrText);
        lblBackground.add(lblPlanetDesc);
        lblPlanetDesc.setBounds(40, lblPlanetName.getY() + lblPlanetName.getHeight() + 15, lblStatsBox.getX() - 40, 200);
        lblPlanetDesc.setAlignments(SwingConstants.CENTER, SwingConstants.TOP);
        lblPlanetDesc.setVisible(true);

        //displays the close button
        XButton btnClose = new XButton(gfxRepository.closeButton, SwingConstants.LEFT);
        lblBackground.add(btnClose);
        btnClose.setBounds(lblBackground.getWidth() - 48, 10, 38, 38);
        btnClose.setOpaque(true);
        btnClose.setVisible(true);

        btnClose.addMouseListener(new XMouseListener() {
            XButton source;

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                audioRepository.buttonDisable();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                window.refresh();

                pnlPlanetData.removeAll();
                pnlPlanetData.setVisible(false);
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

        window.refresh();

    }


    /** Turn ticker **/

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

        window.refresh();
    }


}
