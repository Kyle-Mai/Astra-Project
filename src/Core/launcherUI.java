package Core;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class launcherUI extends coreUI {

    coreUI UI;
    Graphics gc;

    public launcherUI(coreUI UI) {
        this.UI = UI;
    }

    public void loadLauncherUI() {
        UI.loadFrame("Astra Project Launcher", 11, false);
        addContent();

    }

    private void addContent() {
        UI.refreshFrame();
        JButton closeBtn = new JButton("Close");
        UI.GUI.getContentPane().add(closeBtn);
        closeBtn.addActionListener(new CloseListener());
        closeBtn.setBounds(300, 300, 200, 80); //posx, posy, scalx, scaly
        closeBtn.setVisible(true);

    }

    private class CloseListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //DO SOMETHING
            System.exit(0);
        }
    }



}
