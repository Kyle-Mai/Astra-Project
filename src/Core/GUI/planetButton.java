package Core.GUI;

import AetheriusEngine.core.gui.*;
import Core.planetClass;
import Core.starClass;

import javax.swing.*;
import java.awt.*;

/**
 * KM
 * June 01 2017
 * Extension of an extension... neat.
 * Adds nonsense to the XButton for planets.
 */

public class planetButton extends JButton {

    starClass star;
    planetClass planet;
    int identifier;

    public planetButton(starClass star, int identifier) { //creates a planet button
        this.star = star;
        this.identifier = identifier;
        this.setFocusPainted(false);
        this.setFocusable(false);
        this.setBackground(gfxRepository.clrInvisible);
        this.setOpaque(false);
        this.setBorder(null);

    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(XConstants.NONE); //GOODBYE STUPID WHITE BOX
        super.paintComponent(g);

    }

    @Override
    public void setContentAreaFilled(boolean b) {
    }

    public planetClass getPlanet() { return this.planet; }
    public void setPlanet(planetClass planet) { this.planet = planet; }

    public int getIdentifier() {return this.identifier;}
    public starClass getStar() {return  this.star;}

}
