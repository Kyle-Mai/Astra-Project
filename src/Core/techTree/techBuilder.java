package Core.techTree;

/**
 * KM
 * June 06 2017
 * Abstract class used to build the different anonymous classes that define the different techs.
 *
 * CONCEPTS:
 * - Abstract classes.
 */

public abstract class techBuilder {

    private String name;
    private String desc;
    private int cost;
    private int tier;
    private int rarity;
    private int type;

    //default constructor layout
    public techBuilder(String name, int tier, int cost, int rarity, int type, String desc) { //sorted by importance
        this.name = name;
        this.tier = tier;
        this.cost = cost;
        this.desc = desc;
        this.rarity = rarity;
        this.type = type;
    }

    public abstract void finishResearch(); //method that's run when the research finishes

    public final String getName() { return this.name; }
    public final String getDesc() { return this.desc; }
    public final int getCost() { return this.cost; }
    public final int getTier() { return this.tier; }
    public final int getRarity() { return this.rarity; }
    public final int getType() { return this.type; }


}
