package Core;

import java.io.Serializable;

/**
KM
 May 23 2017
 Handles the planet colonies.
*/

public class colonyCore implements Serializable {

    private static final long serialVersionUID = 1L;

    private planetClass planet;
    private double resourceProduction;
    private double taxProduction;
    private double foodProduction;
    private double researchProduction;
    private double currentFood;
    private int population;
    private double unrest;
    private int industryFocus;
    private int entertainmentFocus;
    private int colonyAge;
    private int colonyID;
    private boolean homePlanet;

    public colonyCore(planetClass planet) { //creates a new colony

        this.planet = planet;
        this.population = 1; //population of new colonies always starts at 1
        this.unrest = 0;
        this.industryFocus = 0;
        this.entertainmentFocus = 0;
        this.colonyAge = 0;
        this.currentFood = 0;
        planet.setHabited(true); //sets the planet to inhabited

        this.colonyID = gameSettings.objectIDValue;
        planet.setColonyID(this.colonyID);
        gameSettings.objectIDValue++;

    }

    //creates a colony from predefined values
    public colonyCore (planetClass planet, int population, int colonyAge, int industryFocus, int entertainmentFocus, double currentFood, double resourceProduction, double taxProduction, double foodProduction, double researchProduction) {

        this.planet = planet;
        this.population = population;
        this.colonyAge = colonyAge;
        this.industryFocus = industryFocus;
        this.entertainmentFocus = entertainmentFocus;
        this.currentFood = currentFood;
        this.resourceProduction = resourceProduction;
        this.taxProduction = taxProduction;
        this.foodProduction = foodProduction;
        this.researchProduction = researchProduction;

    }

    public void cycleTurn() { //cycles the turn and changes the values accordingly
        setUnrest();
        adjustTaxes();
        growPopulation();
        adjustProduction();
        colonyAge++;
    }

    public void cycleCollect() {
        harvestFood();
        planet.setResources(resourceProduction);
    }

    private void harvestFood() {
        currentFood = currentFood + foodProduction;
    }

    private void growPopulation() { //increase planet population if food reaches an appropriate amount
        if (currentFood >= population && population <= 6 * planet.getPlanetRadius()) {
            currentFood = currentFood - population;
            population++;
        }
    }

    public void setFoodProduction(double prod) { this.foodProduction = prod; }
    public void setResourceProduction(double prod) { this.resourceProduction = prod; }
    public void setTaxProduction(double prod) { this.taxProduction = prod; }

    private void adjustTaxes() { //sets the taxes this planet currently produces
        taxProduction = (population * gameSettings.player.getTaxMultiplier()) - (0.7 * unrest);
    }

    private void adjustProduction() { //sets the resources this planet currently produces
        resourceProduction = (population * gameSettings.player.getProductionMultiplier()) - (0.2 * unrest);
        if (planet.getResources() <= resourceProduction) {
            resourceProduction = planet.getResources();
        } else if (planet.getResources() <= 0) {
            resourceProduction = 0;
        }

    }

    private void setUnrest() { //sets the current unrest of the colony
         unrest = (1.1 * gameSettings.player.getTaxMultiplier()) + (0.06 * population) - (0.3 * entertainmentFocus) + (0.4 * industryFocus); //TODO: Switch multipliers to allow for variable

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
    public double getResearchProduction() { return this.researchProduction; }
    public double getFoodProduction() { return this.foodProduction; }
    public boolean isHomePlanet() { return this.homePlanet; }
    //public planetClass getPlanet() { return this.planet; }

    public void setHomePlanet(boolean home) { this.homePlanet = home; }

}
