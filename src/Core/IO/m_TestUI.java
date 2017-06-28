package Core.IO;

import Core.GUI.SwingEX.*;

import javax.swing.*;
import java.awt.*;

// Created By VulcanDev
class m_TestUI extends JFrame{
    private DataManager dataManager = new DataManager(); // DataManger Init

    // Main Method
    public static void main(String[] args) {
        new m_TestUI();
    }

    private m_TestUI() {
        XPanel panel = new XPanel(new GridLayout(3,1), new Color(50, 50, 50));
        XButton save = new XButton("Save", new Font("Roboto", Font.PLAIN, 32), new Color(202, 202, 202), new Color(30, 60, 30));
        XButton load = new XButton("Load", new Font("Roboto", Font.PLAIN, 32), new Color(202, 202, 202), new Color(30, 30, 60));
        XLabel success = new XLabel("Success: Not Yet Attempted", new Font("Roboto", Font.PLAIN, 32), new Color(202,202,202));
        success.setHorizontalAlignment(SwingConstants.CENTER);

        save.addActionListener(e -> {
            dataManager.save("Tester");
            if (dataManager.saved){
                success.setText("Success: Saved");
            }else {
                success.setText("Success: Save failed");
            }
        }); // Lambda style action listener
        load.addActionListener(e -> {
            dataManager.load("Tester");
            if (dataManager.loaded){
                success.setText("Success: Loaded");
            }else {
                success.setText("Success: Load failed");
            }
        });
        panel.add(save);
        panel.add(load);
        panel.add(success);

        panel.setSize(new Dimension(512, 512));
        setSize(new Dimension(512, 512));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(panel);
        setVisible(true);
    }
}
