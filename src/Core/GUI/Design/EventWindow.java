package Core.GUI.Design;

import Core.GUI.gfxRepository;
import Core.SFX.audioRepository;
import Core.events.EMouseListener;
import Core.events.eventBuilder;
import Core.gameSettings;
import AetheriusEngine.core.gui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * KM
 * June 16 2017
 * Handles the design and display of the event window.
 */

public class EventWindow extends XPanel {

    private eventBuilder event;
    private XLabel main;
    private XLabel title;
    private XLabel desc;

    public EventWindow() { //creates the base for the event window
        this.setPreferredSize(new Dimension(635, 600));
        this.setSize(this.getPreferredSize());
        main = new XLabel();
        this.add(main);
        main.setBounds(0, 25, this.getWidth(), this.getHeight() - 25);
        main.scaleImage(gfxRepository.menuBackground);
        main.setVisible(true);
        this.setVisible(false);

    }

    public void loadEvent(eventBuilder evt) { //sets the event

        main.removeAll();
        this.removeAll();

        try {
            this.add(main);
            this.event = evt;

            XLabel titleBG = new XLabel(gameSettings.eventhandler.getHeader(event.getType()));
            this.add(titleBG);
            titleBG.setBounds(0, 0, 635, 25);

            title = new XLabel(event.getName(), gfxRepository.txtSubtitle, gfxRepository.clrText);
            titleBG.add(title);
            title.setAlignments(SwingConstants.LEFT, SwingConstants.CENTER);
            title.setBounds(5, 0, 630, 25);
            title.setVisible(true);

            desc = new XLabel("<html>" + event.getDesc() + "</html>", gfxRepository.txtSubtitle, gfxRepository.clrText);
            main.add(desc);
            desc.setAlignments(SwingConstants.LEFT, SwingConstants.TOP);
            desc.setBounds(15, 165, main.getWidth() - 30, 230);
            desc.setVisible(true);

            XLabel lblEvtImage = new XLabel();
            main.add(lblEvtImage);
            lblEvtImage.setBounds((main.getWidth() / 2) - 235, 0, 470, 164);
            lblEvtImage.setAlignments(SwingConstants.CENTER);
            try { //attempt to load image
                lblEvtImage.setIcon(new ImageIcon(event.getImage()));
            } catch (NullPointerException e) { //if the image fails to load, load no pic text instead
                lblEvtImage.setText("[ NO SIGNAL ]", gfxRepository.txtButtonLarge, gfxRepository.clrText);
                lblEvtImage.setOpaque(true);
                lblEvtImage.setBackground(gfxRepository.clrTrueBlack);
            }

            XLabel imgEvtOverlay = new XLabel(gfxRepository.eventOverlay); //overlay in front of the image
            lblEvtImage.add(imgEvtOverlay);
            imgEvtOverlay.setBounds(0, 0, lblEvtImage.getWidth(), lblEvtImage.getHeight());
            imgEvtOverlay.setAlignments(SwingConstants.CENTER);
            imgEvtOverlay.setVisible(true);
            lblEvtImage.setVisible(true);

            XLabel imgEvtBorder = new XLabel(gfxRepository.eventBorder); //border around the image
            lblEvtImage.add(imgEvtBorder);
            imgEvtBorder.setBounds(0, 0, lblEvtImage.getWidth(), lblEvtImage.getHeight());
            imgEvtBorder.setAlignments(SwingConstants.CENTER);
            imgEvtBorder.setVisible(true);

            event.loadOptions();
            event.eventOpen();

            XListSorter eventButtons = new XListSorter(XConstants.VERTICAL_SORT_REVERSE, 0, (this.getWidth() / 2) - 266, this.getHeight() - 30);

            for (int i = 0; i < event.button.size(); i++) { //load in the buttons
                XButtonCustom btnOption = new XButtonCustom(gfxRepository.button532_42, SwingConstants.LEFT);
                btnOption.setText(event.button.get(i).getButtonText(), gfxRepository.txtSubtitle, gfxRepository.clrText);
                btnOption.setToolTipText(event.button.get(i).getMouseOverText());
                btnOption.setPreferredSize(new Dimension(532, 42));
                btnOption.setSize(btnOption.getPreferredSize());
                btnOption.addMouseListener(new EMouseListener(event.button.get(i)) {
                    XButtonCustom source;
                    @Override
                    public void mouseClicked(MouseEvent mouseEvent) {
                        source = (XButtonCustom)mouseEvent.getSource();
                        source.setHorizontalAlignment(SwingConstants.RIGHT);
                        getButton().clickButton();
                        audioRepository.buttonConfirm();
                        main.repaint();
                        main.revalidate();
                        main.getParent().setVisible(false);
                    }

                    @Override
                    public void mousePressed(MouseEvent mouseEvent) {
                        source = (XButtonCustom)mouseEvent.getSource();
                        source.setHorizontalAlignment(SwingConstants.RIGHT);
                        main.repaint();
                        main.revalidate();
                    }

                    @Override
                    public void mouseReleased(MouseEvent mouseEvent) {
                        source = (XButtonCustom)mouseEvent.getSource();
                        source.setHorizontalAlignment(SwingConstants.LEFT);
                        main.repaint();
                        main.revalidate();
                    }

                    @Override
                    public void mouseEntered(MouseEvent mouseEvent) {
                        source = (XButtonCustom)mouseEvent.getSource();
                        audioRepository.menuTab();
                        source.setHorizontalAlignment(SwingConstants.CENTER);
                        main.repaint();
                        main.revalidate();
                    }

                    @Override
                    public void mouseExited(MouseEvent mouseEvent) {
                        source = (XButtonCustom)mouseEvent.getSource();
                        source.setHorizontalAlignment(SwingConstants.LEFT);
                        main.repaint();
                        main.revalidate();
                    }
                });
                eventButtons.addItem(btnOption); //add the button to the sorter
            }
            System.out.println("Loading event with [" + event.button.size() + "] button(s).");
            eventButtons.placeItems(main); //place the event window on the panel

            this.setVisible(true);

        } catch (Exception e) { //problem occurred during loading, abort process
            e.printStackTrace();
            main.removeAll();
            this.setVisible(false);
            System.err.println("[EVT-WIN](loadEvent) Error occurred while loading, aborting process - " + e.getMessage());
        }
    }

    public String getTitle() { return title.getText(); }
    public String getDesc() { return desc.getText(); }
    public eventBuilder getCurrentEvent() { return this.event; }


}
