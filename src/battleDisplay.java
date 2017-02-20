import javax.swing.*;
import java.awt.*;

public class battleDisplay extends Frame{
    // Components
    private JPanel mainPanel;
    private JButton attackButton;
    private JButton abilitiesButton;
    private JPanel buttons;
    private JLabel bg;

    public static void main(String[] args) {
        // Setup frame and pack
        JFrame frame = new JFrame("battleDisplay");
        frame.setContentPane(new battleDisplay().mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
