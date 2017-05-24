package Core;

import Core.Player.playerData;

import java.io.Serializable;

/**
KM
 May 23 2017
 Handles the planet colonies.
*/

public class colonyCore implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {

        StringBuffer data = new StringBuffer();

        data.append("/");


        return data.toString();

    }

    planetClass planet;
    private double resourceProduction;
    private double taxProduction;
    private double foodProduction;
    private double currentFood;
    private int population;
    private double unrest;
    private int industryFocus;
    private int entertainmentFocus;
    private int colonyAge;
    private int colonyID;

    public colonyCore(planetClass planet) { //creates a new colony

        this.planet = planet;
        this.population = 1; //population of new colonies always starts at 1
        this.unrest = 0;
        this.industryFocus = 0;
        this.entertainmentFocus = 0;
        this.colonyAge = 0;
        planet.setHabited(true); //sets the planet to inhabited

        this.colonyID = gameSettings.objectIDValue;
        planet.setColonyID(this.colonyID);
        gameSettings.objectIDValue++;

    }

    //creates a colony from predefined values
    public colonyCore (planetClass planet, int population, int colonyAge, int industryFocus, int entertainmentFocus, double currentFood, double resourceProduction, double taxProduction, double foodProduction) {

        this.planet = planet;
        this.population = population;
        this.colonyAge = colonyAge;
        this.industryFocus = industryFocus;
        this.entertainmentFocus = entertainmentFocus;
        this.currentFood = currentFood;
        this.resourceProduction = resourceProduction;
        this.taxProduction = taxProduction;
        this.foodProduction = foodProduction;

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
        taxProduction = (population * gameSettings.player.getTaxMultiplier()) - (0.4 * unrest);
    }

    private void setResourceProduction() { //sets the resources this planet currently produces
        resourceProduction = (population * gameSettings.player.getProductionMultiplier()) - (0.2 * unrest);
        planet.setResources(-resourceProduction);
    }

    private void setUnrest() { //sets the current unrest of the colony
         unrest = (1.2 * gameSettings.player.getTaxMultiplier()) + (0.1 * population) - (0.2 * entertainmentFocus) + (0.3 * industryFocus);

        if (unrest < 0) {
            unrest = 0;
        } else if (unrest > 10) {
            unrest = 10;
        }
    }

    public int getColonyID() { return this.colonyID; }
    public int getPopulation() { return this.population; }
    public int getColonyAge() { return this.colonyAge; }
    public int getIndustryFocus() { return this.industryFocus; }
    public int getEntertainmentFocus() { return this.entertainmentFocus; }
    public double getUnrest() { return this.unrest; }
    public double getCurrentFood() { return this.currentFood; }
    public double getResourceProduction() { return this.resourceProduction; }
    public double getTaxProduction() { return this.taxProduction; }
    public double getFoodProduction() { return this.foodProduction; }

}
