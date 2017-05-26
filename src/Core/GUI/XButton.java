package Core.GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 KM
 May 25 2017
 Extends the JButton class and adds new constructors and methods.

 SOURCES:
 Stack Overflow - Help with removing white background on button press. https://stackoverflow.com/questions/14627223/how-to-change-a-jbutton-color-on-mouse-pressed
 Self - All else.
*/

class XButton extends JButton {

    private boolean state = false;

    XButton() {
        this.setFocusPainted(false);

    }

    XButton(String text, Font font, Color textColor, Color bgColor) {
        this.setText(text);
        this.setFont(font);
        this.setBackground(bgColor);
        this.setForeground(textColor);
        this.setFocusPainted(false);

    }

    XButton(String text, Font font, Color txtColor, Color bgColor, Border border) {
        this.setText(text);
        this.setFont(font);
        this.setBackground(bgColor);
        this.setForeground(txtColor);
        this.setBorder(border);
        this.setFocusPainted(false);

    }

    XButton(BufferedImage image, int position) {
        this.setBorder(null);
        this.setFocusPainted(false);
        this.setBackground(gfxRepository.clrInvisible);
        this.setOpaque(false);
        this.setIcon(new ImageIcon(image));
        this.setVerticalAlignment(SwingConstants.CENTER);
        this.setHorizontalAlignment(position);
        super.setContentAreaFilled(false);

    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(gfxRepository.clrInvisible); //GOODBYE STUPID WHITE BOX
        super.paintComponent(g);

    }

    @Override
    public void setContentAreaFilled(boolean b) {
    }

    public void toggleState() {
        state = !state;
    }

    public boolean isState() {
        return this.state;
    }


}
