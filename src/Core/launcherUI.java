package Core;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class launcherUI extends coreUI {

    coreUI UI;
    Graphics gc;
    JFrame GUI;

    public launcherUI(coreUI UI) {
        this.UI = UI;
    }

    public void loadUI(String screenName, int UIscaleOption) {
        UI.rescaleScreen(UIscaleOption);

        this.GUI = new JFrame(screenName);

        GUI.getContentPane().add(UI);
        GUI.setResizable(false);
        GUI.setSize(UI.getUIScaleX(), UI.getUIScaleY());
        GUI.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        GUI.setUndecorated(true);
        GUI.pack();
        GUI.setVisible(true);

    }

    public void addContent() {
        JButton close = new JButton("Close");
        close.setBounds(300, 300, 200, 80);
        GUI.getContentPane().add(close);
        close.addActionListener(new CloseListener());

    }

    private class CloseListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //DO SOMETHING
            System.exit(0);
        }
    }



}
