package Core.GUI;

//import all relevant stuff
import Core.xmlLoader;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;

/**
 KM
 May 16 2017
 Round FOUR of attempting to create a GUI core to manage and display UI elements.
 Just kill me now.
 */


public class guiCoreV4 {

    private File resourcesFolder = new File(("user.dir") + "/src/Core/GUI/Resources"); //stores the resources folder

    /** Stores resource declarations **/

    ArrayList<JPanel> pnlExpansions = new ArrayList<>();
    ArrayList<JLabel> lblExpansions = new ArrayList<>();
    ArrayList<JLabel> lblExpanDesc = new ArrayList<>();
    ArrayList<JButton> btnExpanEnable = new ArrayList<>();
    ArrayList<JLabel> lblExpanID = new ArrayList<>();

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //gets the screen size of the user
    double screenWidth = screenSize.getWidth();
    double screenHeight = screenSize.getHeight();
    JFrame window;
    newPanel screen;

    ImageIcon gameIcon = new ImageIcon(resourcesFolder + "/icon.png");

    /** Stores UI element design properties **/

    final String gameVersion = "PTB-A Build 62a";

    final Color clrBlk = new Color(25, 35, 35, 255);
    final Color clrDGrey = new Color(55, 55, 55, 255);
    final Color clrDisableBorder = new Color(75, 5, 25, 255);
    final Color clrDisable = new Color(135, 15, 55, 255);
    final Color clrEnable = new Color(0, 155, 105, 255);
    final Color clrDark = new Color(0, 105, 45, 100);
    final Color clrBackground = new Color(0, 185, 140, 105);
    final Color clrForeground = new Color(0, 185, 110, 155);
    final Color clrText = new Color(255, 255, 255, 255);

    final Font txtStandard = new Font("Comic Sans", Font.PLAIN, 15);
    final Font txtSubtitle = new Font("Arial", Font.BOLD, 14);
    final Font txtSubheader = new Font("Arial", Font.BOLD, 16);
    final Font txtHeader = new Font("Arial", Font.BOLD, 25);
    final Font txtTitle = new Font("Arial", Font.BOLD, 40);
    final Font txtTiny = new Font("Arial", Font.PLAIN, 12);

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
        window.setIconImage(gameIcon.getImage());
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

    public void loadLauncherScreen() {
        System.out.println("Loading launcher data...");
        JLayeredPane layers; //sorts the layers of the screen
        BufferedImage launcherBG;
        JLabel imgBackground;


        //initialize the layers
        layers = new JLayeredPane();
        layers.setBounds(0, 0, getUIScaleX(), getUIScaleY());

        //attempt to load images
        try {
            launcherBG = ImageIO.read(this.getClass().getResource("Resources/launcherBG.jpg"));
            imgBackground = new JLabel(new ImageIcon(launcherBG));
            layers.add(imgBackground, new Integer(0), 0);
            imgBackground.setBounds(0, 0, getUIScaleX(), getUIScaleY());
            imgBackground.setVisible(true);
        } catch (IOException e) {
            System.out.println("Error when loading images: " + e.getMessage());
        }

        //load exit button
        JButton btnExit = new JButton();

        layers.add(btnExit, new Integer(1), 0);
        btnExit.setBounds(getUIScaleX() - 35, 5, 30, 30);
        btnExit.setBackground(clrBackground);
        btnExit.setFocusPainted(false);
        btnExit.setForeground(clrText);
        //btnExit.setBorderPainted(false);
        btnExit.setBorder(BorderFactory.createCompoundBorder( BorderFactory.createBevelBorder( BevelBorder.RAISED, clrForeground, clrBackground), BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));
        btnExit.setOpaque(false);
        btnExit.setFont(txtStandard);
        btnExit.setText("X");
        btnExit.addActionListener(new ActionListener() { //closes the program when clicked
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Killing program.");
                window.dispose(); //ensure the thread dies
                System.exit(0); //close the program
            }
        });
        btnExit.setVisible(true);

        //load minimize button
        JButton btnMinimize = new JButton();

        layers.add(btnMinimize, new Integer(2), 0);
        btnMinimize.setBounds(getUIScaleX() - 70, 5, 30, 30);
        btnMinimize.setBackground(clrBackground);
        btnMinimize.setFocusPainted(false);
        btnMinimize.setForeground(clrText);
        //btnMinimize.setBorderPainted(false);
        btnMinimize.setBorder(BorderFactory.createCompoundBorder( BorderFactory.createBevelBorder( BevelBorder.RAISED, clrForeground, clrBackground), BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));
        btnMinimize.setOpaque(false);
        btnMinimize.setFont(txtStandard);
        btnMinimize.setText("-");
        btnMinimize.addActionListener(new ActionListener() { //closes the program when clicked
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Minimizing program.");
                window.setState(Frame.ICONIFIED);
            }
        });
        btnMinimize.setVisible(true);

        //load the game title
        JLabel lblTitle = new JLabel();

        layers.add(lblTitle, new Integer(3), 0);
        lblTitle.setBounds(20, 20, 300, 75);
        lblTitle.setOpaque(false);
        lblTitle.setFocusable(false);
        lblTitle.setFont(txtTitle);
        lblTitle.setText("Astra Project");
        lblTitle.setForeground(clrText);
        lblTitle.setVisible(true);

        //load the game version
        JLabel lblVersion = new JLabel();

        layers.add(lblVersion, new Integer(4), 0);
        lblVersion.setBounds(getUIScaleX() - 205, getUIScaleY() - 40, 200, 35);
        lblVersion.setOpaque(false);
        lblVersion.setFocusable(false);
        lblVersion.setFont(txtSubtitle);
        lblVersion.setText("Version; " + gameVersion);
        lblVersion.setForeground(clrText);
        lblVersion.setHorizontalAlignment(SwingConstants.RIGHT); //sets the text to center to the right side instead of the left
        lblVersion.setVisible(true);

        //load the scroll window
        JPanel contentController = new JPanel();

        layers.add(contentController, new Integer(5), 0);
        contentController.setBounds(getUIScaleX() - 305, 110, 300, 300);
        contentController.setOpaque(true);
        contentController.setLayout(null);
        contentController.setBackground(clrBackground);
        contentController.setVisible(true);

        JScrollPane contentList = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        layers.add(contentList, new Integer(6), 0);
        contentList.setBounds(getUIScaleX() - (contentController.getWidth() + 5), 110, contentController.getWidth(), 300);
        contentList.setViewportView(contentController);
        contentList.setOpaque(false);
        contentList.setVisible(true);

        for (int i = 0; i < xmlLoader.listOfExpansions.size(); i ++) {
            pnlExpansions.add(new JPanel());
            lblExpansions.add(new JLabel());
            lblExpanDesc.add(new JLabel());
            btnExpanEnable.add(new JButton());
            lblExpanID.add(new JLabel());

            contentController.add(pnlExpansions.get(i)); //moves all of the content to the content controller

            pnlExpansions.get(i).setLayout(null);

            if (5 + (75 * i) > contentController.getHeight()) { //increases the window size if needed
                contentController.setBounds(contentController.getX(), contentController.getY(), contentController.getWidth(), contentController.getHeight() + 75);
            }

            pnlExpansions.get(i).setBounds(5, 5 + (75 * i), contentController.getWidth() - 25, 70);
            pnlExpansions.get(i).setBackground(clrForeground);
            pnlExpansions.get(i).add(lblExpansions.get(i));
            pnlExpansions.get(i).add(lblExpanDesc.get(i));
            pnlExpansions.get(i).add(btnExpanEnable.get(i));
            pnlExpansions.get(i).add(lblExpanID.get(i));
            pnlExpansions.get(i).setOpaque(true);

            lblExpansions.get(i).setBounds(5, 5, 195, 30);
            lblExpansions.get(i).setForeground(clrText);
            lblExpansions.get(i).setOpaque(false);
            lblExpansions.get(i).setFont(txtSubheader);
            lblExpansions.get(i).setText(xmlLoader.listOfExpansions.get(i).getName());

            lblExpanDesc.get(i).setBounds(5, 40, 170, 25);
            lblExpanDesc.get(i).setOpaque(false);
            lblExpanDesc.get(i).setForeground(clrText);
            lblExpanDesc.get(i).setFont(txtSubtitle);
            lblExpanDesc.get(i).setText(xmlLoader.listOfExpansions.get(i).getSubtitle());

            //TODO: Add an action listener to the button that enables/disables the content.
            btnExpanEnable.get(i).setBounds(contentController.getWidth() - 55, 5, 25, 25);
            btnExpanEnable.get(i).setForeground(clrText);
            btnExpanEnable.get(i).setOpaque(true);
            btnExpanEnable.get(i).setFocusable(false);
            btnExpanEnable.get(i).setFont(txtSubtitle);

            if (xmlLoader.listOfExpansions.get(i).getEnabledStatus()) {
                btnExpanEnable.get(i).setText("-");
                btnExpanEnable.get(i).setToolTipText("Disable");
                btnExpanEnable.get(i).setBackground(clrEnable);
                btnExpanEnable.get(i).setBorder(BorderFactory.createCompoundBorder( BorderFactory.createBevelBorder( BevelBorder.RAISED, clrForeground, clrBackground), BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));
            } else {
                btnExpanEnable.get(i).setText("+");
                btnExpanEnable.get(i).setToolTipText("Enable");
                btnExpanEnable.get(i).setBackground(clrDisable);
                btnExpanEnable.get(i).setBorder(BorderFactory.createCompoundBorder( BorderFactory.createBevelBorder( BevelBorder.RAISED, clrDisableBorder, clrBlk), BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));
            }

            lblExpanID.get(i).setBounds(contentController.getWidth() - 85, 35, 55, 25);
            lblExpanID.get(i).setHorizontalAlignment(SwingConstants.RIGHT);
            lblExpanID.get(i).setForeground(clrText);
            lblExpanID.get(i).setFont(txtTiny);
            lblExpanID.get(i).setText(xmlLoader.listOfExpansions.get(i).getID());


            //enable everything lel
            lblExpanID.get(i).setVisible(true);
            btnExpanEnable.get(i).setVisible(true);
            lblExpanDesc.get(i).setVisible(true);
            lblExpansions.get(i).setVisible(true);
            pnlExpansions.get(i).setVisible(true);

            System.out.println("Loaded expansion data into GUI.");

        }







        //add the layered window to the content pane
        window.getContentPane().add(layers);

        //make sure the content is visible and render it
        window.revalidate();
        window.repaint();

    }





}
