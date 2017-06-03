package Core.GUI;

import Core.GUI.SwingEX.EXColorDefaults;
import Core.GUI.SwingEX.XButton;
import Core.planetClass;
import Core.starClass;

import java.awt.*;

/**
 * KM
 * June 01 2017
 * Extension of an extension... neat.
 * Adds nonsense to the XButton for planets.
 */

public class planetButton extends XButton {

    starClass star;
    planetClass planet;
    int identifier;

    public planetButton(starClass star, int identifier) { //creates a planet button
        this.star = star;
        this.identifier = identifier;
        this.setFocusPainted(false);
        this.setFocusable(false);
        this.setOpaque(false);

    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(EXColorDefaults.NONE); //GOODBYE STUPID WHITE BOX
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
