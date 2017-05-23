package Core;

import Core.Player.playerData;

/**
KM
 May 23 2017
 Handles the planet colonies.
*/

public class colonyCore {

    private planetClass planet;
    private playerData player;
    private double resourceProduction;
    private double taxProduction;
    private double foodProduction;
    private double currentFood;
    private int population;
    private double unrest;
    private int industryFocus;
    private int entertainmentFocus;
    private int colonyAge;

    public colonyCore(planetClass planet, playerData player) { //creates a new colony
        this.planet = planet; //the planet the colony is on
        this.player = player; //gets the user data
        this.population = 1; //population of new colonies always starts at 1
        this.unrest = 0;
        this.industryFocus = 0;
        this.entertainmentFocus = 0;
        this.colonyAge = 0;
        planet.setHabited(true); //sets the planet to inhabited

    }

    public void cycleTurn() { //cycles the turn and changes the values accordingly
        setUnrest();
        setResourceProduction();
        setTaxProduction();
        setCurrentFood();
        setPopulation();
        colonyAge++;
    }

    private void setCurrentFood() {
        currentFood = currentFood + foodProduction;
    }

    private void setPopulation() {
        if (currentFood >= population) {
            currentFood = currentFood - population;
            population++;
        }
    }

    private void setTaxProduction() { //sets the taxes this planet currently produces
        taxProduction = (population * player.getTaxMultiplier()) - (0.4 * unrest);
    }

    private void setResourceProduction() { //sets the resources this planet currently produces
        resourceProduction = (population * player.getProductionMultiplier()) - (0.2 * unrest);
        planet.setResources(-resourceProduction);
    }

    private void setUnrest() { //sets the current unrest of the colony
         unrest = (1.2 * player.getTaxMultiplier()) + (0.1 * population) - (0.2 * entertainmentFocus) + (0.3 * industryFocus);

        if (unrest < 0) {
            unrest = 0;
        } else if (unrest > 10) {
            unrest = 10;
        }
    }

}
