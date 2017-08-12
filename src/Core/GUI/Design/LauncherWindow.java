package Core.GUI.Design;

import Core.GUI.gfxConstants;
import Core.GUI.gfxRepository;
import Core.GUI.screenScale;
import Core.SFX.audioRepository;
import Core.gameSettings;
import Core.xmlLoader;
import AetheriusEngine.core.gui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * KM
 * June 16 2017
 * Handles the creation and management of the game launcher and associated elements.
 */

public class LauncherWindow extends JLayeredPane implements gfxConstants {

    private XPanel pnlContent;
    private XPanel pnlSettings;

    public LauncherWindow() {
        this.setVisible(true);
        this.setPreferredSize(screenScale.LAUNCHER.size());
        this.setSize(this.getPreferredSize());
        loadCoreInterface();
    }

    //TODO: Custom tooltip design.

    private void loadCoreInterface() { //loads the main interface content
        XLabel imgBackground, imgBorder, imgLogo;

        imgBackground = new XLabel(gfxRepository.mainBackground, clrTrueBlack);
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

        XLabel lblTitle = new XLabel("stra Project", gfxRepository.txtTitle, clrText);
        this.add(lblTitle, PALETTE_LAYER);
        lblTitle.setBounds(imgLogo.getX() + imgLogo.getWidth() - 5, 30, 300, 75);
        lblTitle.setVerticalAlignment(SwingConstants.TOP);
        lblTitle.setVisible(true);

        //load the game version
        XLabel lblVersion = new XLabel("Version; " + gfxRepository.gameVersion, gfxRepository.txtTiny, clrText);
        this.add(lblVersion, PALETTE_LAYER);
        lblVersion.setBounds(25, this.getHeight() - 50, 200, 35);
        lblVersion.setAlignments(SwingConstants.LEFT, SwingConstants.CENTER);
        lblVersion.setVisible(true);

        //load exit button
        XButton btnExit = new XButton(gfxRepository.spriteExitButton.get(0), SwingConstants.CENTER);

        this.add(btnExit, MODAL_LAYER);
        btnExit.setBounds(this.getWidth() - 58, 6, 38, 38);
        btnExit.setOpaque(false);

        btnExit.addMouseListener(new MouseListener() {
            XButton source;
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setIcon(gfxRepository.spriteExitButton.get(2), SwingConstants.CENTER);
                audioRepository.buttonClick();
                source.refresh();
                gameSettings.ui.window.close();
            }
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setIcon(gfxRepository.spriteExitButton.get(2), SwingConstants.CENTER);
                source.refresh();
            }
            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setIcon(gfxRepository.spriteExitButton.get(0), SwingConstants.CENTER);
                source.refresh();
            }
            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setIcon(gfxRepository.spriteExitButton.get(1), SwingConstants.CENTER);
                audioRepository.menuTab();
                source.refresh();
            }
            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                source = (XButton)mouseEvent.getSource();
                source.setIcon(gfxRepository.spriteExitButton.get(0), SwingConstants.CENTER);
                source.refresh();
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
                audioRepository.buttonClick();
                source.toggleState();

                if (source.getCurrentState()) {
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
                pnlSettings.setVisible(!pnlSettings.isVisible()); //make the menu visible
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

        pnlSettings = new XPanel(clrBackground);
        this.add(pnlSettings, PALETTE_LAYER);
        pnlSettings.setBounds(25, 90, 340, 275);
        pnlSettings.setVisible(false);
        loadLaunchSettings();

        pnlContent = new XPanel(clrBackground);
        this.add(pnlContent, PALETTE_LAYER);
        pnlContent.setBounds(this.getWidth() - 325, 45, 300, 310);
        pnlContent.setPreferredSize(pnlContent.getSize());
        pnlContent.setVisible(true);

        XScrollPane scrWindow = new XScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scrWindow, PALETTE_LAYER);

        XScrollBar contentScroller = new XScrollBar(gfxRepository.clrEnable, clrDark); //scrollbar design
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
        btcLaunch.setText("PLAY", gfxRepository.txtButtonLarge, clrText);
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
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                source = (XButtonCustom)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                source = (XButtonCustom)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.CENTER);
                audioRepository.menuTab();
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                source = (XButtonCustom)mouseEvent.getSource();
                source.setHorizontalAlignment(SwingConstants.LEFT);
            }
        });
        btcLaunch.setVisible(true);

        audioRepository.musicLauncherScreen();

        loadContentList();

    }

    private void loadContentList() { //loads the mods/expansions UI

        pnlContent.removeAll(); //clear the content window, just in case
        pnlContent.setBounds(this.getWidth() - 325, 45, 300, 310);
        pnlContent.setPreferredSize(pnlContent.getSize());

        XListSorter srtLauncherContent = new XListSorter(XConstants.VERTICAL_SORT, 5, 5, 5); //sorter used to sort and display the info
        srtLauncherContent.resizeParent(true);

        for (int i = 0; i < 2; i++) { //load mod/expansion pack contents to the launcher
            XPanel pnlHeader = new XPanel(clrDark);
            pnlHeader.setPreferredSize(new Dimension(pnlContent.getWidth() - 20, 25));
            pnlHeader.setSize(pnlHeader.getPreferredSize());
            XLabel lblHeader = new XLabel();
            pnlHeader.add(lblHeader);
            lblHeader.setSize(pnlHeader.getPreferredSize());
            lblHeader.setAlignments(SwingConstants.CENTER);

            switch (i) {
                case 0: //add the expansions to the launcher

                    lblHeader.setText("Expansion Packs", gfxRepository.txtSubheader, clrText);
                    srtLauncherContent.addItem(pnlHeader);

                    for (int j = 0; j < xmlLoader.listOfExpansions.size(); j++) { //for every expansion pack, create and add a new display panel to list

                        XPanel pnlItem = new XPanel(clrForeground);
                        pnlItem.setPreferredSize(new Dimension(pnlContent.getWidth() - 20, 60));
                        XLabel lblTitle = new XLabel(xmlLoader.listOfExpansions.get(j).getName(), gfxRepository.txtSubheader, clrText);
                        pnlItem.add(lblTitle);
                        lblTitle.setBounds(5, 5, 195, 25);
                        lblTitle.setAlignments(SwingConstants.LEFT, SwingConstants.TOP);
                        XLabel lblDesc = new XLabel(xmlLoader.listOfExpansions.get(j).getDesc(), gfxRepository.txtSubtitle, clrText);
                        pnlItem.add(lblDesc);
                        lblDesc.setToolTipText(xmlLoader.listOfExpansions.get(j).getDesc());
                        lblDesc.setBounds(5, 35, 200, 20);

                        XButton btnEnable = new XButton(gfxRepository.rejectButton, SwingConstants.RIGHT);
                        pnlItem.add(btnEnable);
                        btnEnable.setBounds(pnlContent.getWidth() - 50, 0, 30, 30);
                        btnEnable.setToolTipText("Enable");

                        if (xmlLoader.listOfExpansions.get(j).getEnabledStatus()) { //if the content happens to be enabled, show it accordingly
                            btnEnable.setIcon(new ImageIcon(gfxRepository.acceptButton));
                            btnEnable.toggleState();
                            btnEnable.setToolTipText("Disable");
                        }

                        btnEnable.addMouseListener(new XMouseListener(xmlLoader.listOfExpansions.get(j).getID()) { //button to enable/disable the content
                            XButton source;
                            @Override
                            public void mouseClicked(MouseEvent mouseEvent) {
                                source = (XButton)mouseEvent.getSource();
                                source.setHorizontalAlignment(SwingConstants.LEFT);
                                source.toggleState();

                                if (source.getCurrentState()) {
                                    source.setIcon(new ImageIcon(gfxRepository.acceptButton));
                                    audioRepository.buttonConfirm();
                                    source.setToolTipText("Disable");
                                } else {
                                    source.setIcon(new ImageIcon(gfxRepository.rejectButton));
                                    audioRepository.buttonDisable();
                                    source.setToolTipText("Enable");
                                }
                                xmlLoader.changeExpansionInfo(getIDValue(), source.getCurrentState());
                                pnlContent.refresh();
                            }
                            @Override
                            public void mousePressed(MouseEvent mouseEvent) {
                                source = (XButton)mouseEvent.getSource();
                                source.setHorizontalAlignment(SwingConstants.LEFT);
                                pnlContent.refresh();
                            }
                            @Override
                            public void mouseReleased(MouseEvent mouseEvent) {
                                source = (XButton)mouseEvent.getSource();
                                source.setHorizontalAlignment(SwingConstants.RIGHT);
                                pnlContent.refresh();
                            }
                            @Override
                            public void mouseEntered(MouseEvent mouseEvent) {
                                source = (XButton)mouseEvent.getSource();
                                source.setHorizontalAlignment(SwingConstants.CENTER);
                                audioRepository.buttonHighlight();
                                pnlContent.refresh();
                            }
                            @Override
                            public void mouseExited(MouseEvent mouseEvent) {
                                source = (XButton)mouseEvent.getSource();
                                source.setHorizontalAlignment(SwingConstants.RIGHT);
                                pnlContent.refresh();
                            }
                        });
                        srtLauncherContent.addItem(pnlItem); //add the item to the sorter to be placed on the content
                    }
                    break;
                case 1: //add the mods to the launcher

                    lblHeader.setText("Mods", gfxRepository.txtSubheader, clrText);
                    srtLauncherContent.addItem(pnlHeader);

                    for (int j = 0; j < xmlLoader.listOfMods.size(); j++) {

                        XPanel pnlItem = new XPanel(clrForeground);
                        pnlItem.setPreferredSize(new Dimension(pnlContent.getWidth() - 20, 60));
                        XLabel lblTitle = new XLabel(xmlLoader.listOfMods.get(j).getModName(), gfxRepository.txtSubheader, clrText);
                        pnlItem.add(lblTitle);
                        lblTitle.setBounds(5, 5, 195, 25);
                        lblTitle.setAlignments(SwingConstants.LEFT, SwingConstants.TOP);
                        XLabel lblDesc = new XLabel(xmlLoader.listOfMods.get(j).getModDesc(), gfxRepository.txtSubtitle, clrText);
                        lblDesc.setToolTipText(xmlLoader.listOfMods.get(j).getModDesc());
                        pnlItem.add(lblDesc);
                        lblDesc.setBounds(5, 35, 200, 20);

                        XButton btnEnable = new XButton(gfxRepository.rejectButton, SwingConstants.RIGHT);
                        pnlItem.add(btnEnable);
                        btnEnable.setBounds(pnlContent.getWidth() - 50, 0, 30, 30);
                        btnEnable.setToolTipText("Enable");

                        if (xmlLoader.listOfMods.get(j).getModEnabled()) { //if the content happens to be enabled, show it accordingly
                            btnEnable.setIcon(new ImageIcon(gfxRepository.acceptButton));
                            btnEnable.toggleState();
                            btnEnable.setToolTipText("Disable");
                        }

                        btnEnable.addMouseListener(new XMouseListener(xmlLoader.listOfMods.get(j).getModDirectory()) {
                            XButton source;
                            @Override
                            public void mouseClicked(MouseEvent mouseEvent) {
                                source = (XButton)mouseEvent.getSource();
                                source.setHorizontalAlignment(SwingConstants.LEFT);
                                source.toggleState();

                                if (source.getCurrentState()) {
                                    source.setIcon(new ImageIcon(gfxRepository.acceptButton));
                                    audioRepository.buttonConfirm();
                                    source.setToolTipText("Disable");
                                } else {
                                    source.setIcon(new ImageIcon(gfxRepository.rejectButton));
                                    audioRepository.buttonDisable();
                                    source.setToolTipText("Enable");
                                }
                                xmlLoader.changeModInfo(getIDValue(), source.getCurrentState());
                                pnlContent.refresh();
                            }
                            @Override
                            public void mousePressed(MouseEvent mouseEvent) {
                                source = (XButton)mouseEvent.getSource();
                                source.setHorizontalAlignment(SwingConstants.LEFT);
                                pnlContent.refresh();
                            }
                            @Override
                            public void mouseReleased(MouseEvent mouseEvent) {
                                source = (XButton)mouseEvent.getSource();
                                source.setHorizontalAlignment(SwingConstants.RIGHT);
                                pnlContent.refresh();
                            }
                            @Override
                            public void mouseEntered(MouseEvent mouseEvent) {
                                source = (XButton)mouseEvent.getSource();
                                source.setHorizontalAlignment(SwingConstants.CENTER);
                                audioRepository.buttonHighlight();
                                pnlContent.refresh();
                            }
                            @Override
                            public void mouseExited(MouseEvent mouseEvent) {
                                source = (XButton)mouseEvent.getSource();
                                source.setHorizontalAlignment(SwingConstants.RIGHT);
                                pnlContent.refresh();
                            }
                        });
                        srtLauncherContent.addItem(pnlItem); //add the item to the sorter to be placed on the content
                    }
                    break;
            }
        } //content finished loading
        srtLauncherContent.placeItems(pnlContent); //places the content on the launcher
        System.out.println("Content loaded into launcher.");
        pnlContent.refresh();
    } //close content loader

    private void loadLaunchSettings() { //loads a settings menu

        XListSorter srtSettings = new XListSorter(XConstants.VERTICAL_SORT, 5, 5, 5);

        JComboBox<String> cbxScreenScale = new JComboBox<>();
        for (screenScale option : screenScale.values()) {
            if (option.size().getWidth() <= screenScale.monitorSize.getWidth() && option.size().getHeight() <= screenScale.monitorSize.getHeight() && option.isSelectable()) {
                cbxScreenScale.addItem(option.getOptionName());
            } //if the option is larger than the monitor (or not valid/selectable) do not display it as an option
        }
        cbxScreenScale.setMaximumRowCount(6);
        cbxScreenScale.setPreferredSize(new Dimension(pnlSettings.getWidth() - 10, 25));
        cbxScreenScale.setForeground(clrText);
        cbxScreenScale.setBackground(clrBGOpaque);
        cbxScreenScale.setFont(gfxRepository.txtSubtitle);
        cbxScreenScale.addActionListener(new ActionListener() {
            JComboBox<String> source;
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                source = (JComboBox<String>)actionEvent.getSource();
                for (screenScale choice : screenScale.values()) {
                    if (source.getItemAt(source.getSelectedIndex()).equals(choice.getOptionName())) { //rescale the screen
                        screenScale.setScreenSize(choice.size());
                        break;
                    }
                }
                source.revalidate();
                source.repaint();
            }
        });

        for (screenScale choice : screenScale.values()) {
            if (cbxScreenScale.getItemAt(cbxScreenScale.getSelectedIndex()).equals(choice.getOptionName())) { //rescale the screen
                screenScale.setScreenSize(choice.size());
                break;
            }
        }

        XLabel lblScreen = new XLabel("Game Resolution", gfxRepository.txtSubtitle, clrText);
        lblScreen.setPreferredSize(new Dimension(pnlSettings.getWidth() - 10, 25));
        lblScreen.setAlignments(SwingConstants.CENTER);

        srtSettings.addItems(lblScreen, cbxScreenScale);

        srtSettings.placeItems(pnlSettings);

        this.revalidate();
        this.repaint();
    }

}
