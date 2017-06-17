package Core.GUI.Design;

import Core.GUI.SwingEX.*;
import Core.GUI.gfxRepository;
import Core.GUI.screenScale;
import Core.SFX.audioRepository;
import Core.gameSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * KM
 * June 16 2017
 * Handles the creation and management of the game launcher and associated elements.
 */

public class LauncherWindow extends JLayeredPane {

    private XPanel pnlContent;

    public LauncherWindow() {
        this.setVisible(true);
        this.setPreferredSize(screenScale.LAUNCHER.size());
        this.setSize(this.getPreferredSize());
        loadCoreInterface();
    }

    private void loadCoreInterface() { //loads the main interface content
        XLabel imgBackground, imgBorder, imgLogo;

        imgBackground = new XLabel(gfxRepository.mainBackground, gfxRepository.clrTrueBlack);
        this.add(imgBackground, DEFAULT_LAYER);
        imgBackground.setBounds(0, 0, this.getWidth(), this.getHeight());
        imgBackground.scaleImage(gfxRepository.mainBackground);
        imgBackground.setVisible(true);

        imgBorder = new XLabel(gfxRepository.launcherBorder);
        imgBackground.add(imgBorder);
        imgBorder.setBounds(0, 0, this.getWidth(), this.getHeight());
        imgBorder.setVisible(true);

        imgLogo = new XLabel(gfxRepository.gameLogo);
        this.add(imgLogo, PALETTE_LAYER);
        imgLogo.setBounds(40, 20, 50, 60);
        imgLogo.setVisible(true);

        XLabel lblTitle = new XLabel("stra Project", gfxRepository.txtTitle, gfxRepository.clrText);
        this.add(lblTitle, PALETTE_LAYER);
        lblTitle.setBounds(imgLogo.getX() + imgLogo.getWidth() - 5, 30, 300, 75);
        lblTitle.setVerticalAlignment(SwingConstants.TOP);
        lblTitle.setVisible(true);

        //load the game version
        XLabel lblVersion = new XLabel("Version; " + gfxRepository.gameVersion, gfxRepository.txtTiny, gfxRepository.clrText);
        this.add(lblVersion, PALETTE_LAYER);
        lblVersion.setBounds(25, this.getHeight() - 50, 200, 35);
        lblVersion.setAlignments(SwingConstants.LEFT, SwingConstants.CENTER);
        lblVersion.setVisible(true);

        //load exit button
        XButton btnExit = new XButton(gfxRepository.closeButton, SwingConstants.LEFT);

        this.add(btnExit, MODAL_LAYER);
        btnExit.setBounds(this.getWidth() - 58, 6, 38, 38);
        btnExit.setOpaque(false);

        btnExit.addMouseListener(new MouseListener() {
            XButton source;

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                audioRepository.buttonClick();
                gameSettings.ui.window.closeProgram();
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

        this.add(btnLauncherAudio, MODAL_LAYER);
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

        this.add(btnSettings, MODAL_LAYER);
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

        pnlContent = new XPanel(gfxRepository.clrBackground);
        this.add(pnlContent, PALETTE_LAYER);
        pnlContent.setBounds(this.getWidth() - 325, 45, 300, 310);
        pnlContent.setPreferredSize(pnlContent.getSize());
        pnlContent.setVisible(true);

        XScrollPane scrWindow = new XScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scrWindow, PALETTE_LAYER);

        XScrollBar contentScroller = new XScrollBar(gfxRepository.clrEnable, gfxRepository.clrDark);
        contentScroller.addAdjustmentListener(new AdjustmentListener() {
            XScrollBar source;
            @Override
            public void adjustmentValueChanged(AdjustmentEvent ae) {
                source = (XScrollBar)ae.getSource();
                source.getParent().revalidate();
                source.getParent().repaint();
            }
        });

        scrWindow.setBounds(pnlContent.getBounds());
        scrWindow.setPreferredSize(scrWindow.getSize());
        scrWindow.add(contentScroller);
        contentScroller.setBounds(scrWindow.getWidth() - 15, 0, 15, scrWindow.getHeight());
        scrWindow.setViewportView(pnlContent);
        scrWindow.getViewport().setPreferredSize(scrWindow.getSize());
        scrWindow.setVerticalScrollBar(contentScroller);
        scrWindow.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        scrWindow.setVisible(true);

        XButtonCustom btcLaunch = new XButtonCustom(gfxRepository.wideButton2, SwingConstants.LEFT);
        imgBackground.add(btcLaunch);
        btcLaunch.setBounds(pnlContent.getX() - 10, pnlContent.getY() + pnlContent.getHeight(), 319, 80);
        btcLaunch.setText("PLAY", gfxRepository.txtButtonLarge, gfxRepository.clrText);
        btcLaunch.addMouseListener(new XMouseListener() {
            XButtonCustom source;

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButtonCustom)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                audioRepository.buttonSelect();
                gameSettings.ui.launchGame();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                source = (XButtonCustom)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.RIGHT);
                gameSettings.ui.window.refresh();
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                source = (XButtonCustom)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                gameSettings.ui.window.refresh();
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                source = (XButtonCustom)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.CENTER);
                audioRepository.menuTab();
                gameSettings.ui.window.refresh();
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                source = (XButtonCustom)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
                gameSettings.ui.window.refresh();

            }
        });
        btcLaunch.setVisible(true);

        audioRepository.musicLauncherScreen();

    }

    private void loadContentList() { //loads the mods/expansions UI

        XListSorter srtLauncherContent = new XListSorter(XConstants.VERTICAL_SORT, 5, 10, 5);

        for (int i = 0; i < 2; i++) {

            XLabel lblHeader = new XLabel();
            lblHeader.setBackground(gfxRepository.clrDark);
            lblHeader.setOpaque(true);
            switch (i) {
                case 0:
                    lblHeader.setText("Expansion Packs", gfxRepository.txtSubheader, gfxRepository.clrText);
                    break;
                case 1:
                    lblHeader.setText("Mods", gfxRepository.txtSubheader, gfxRepository.clrText);
                    break;
            }
            lblHeader.setPreferredSize(new Dimension(pnlContent.getWidth() - 20, 25));
            lblHeader.setSize(lblHeader.getPreferredSize());
            lblHeader.setAlignments(SwingConstants.CENTER);
            srtLauncherContent.addItem(lblHeader);





        }


    }


}
