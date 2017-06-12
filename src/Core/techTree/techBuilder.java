package Core.techTree;

import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicLong;

/**
 * KM
 * June 06 2017
 * Abstract class used to build the different anonymous classes that define the different techs.
 *
 * CONCEPTS:
 * - Abstract classes.
 */

public abstract class techBuilder implements techConstants {

    private static final AtomicLong tech_id = new AtomicLong(0); //creates a unique ID for the techs

    private String name; //name of the tech
    private String desc; //description of the tech
    private int cost; //how much research points it costs
    private int tier; //what tier the tech is
    private int rarity; //the chance if it appearing in the research queue
    private int type; //the type of research
    private long techID; //creates an ID for the tech
    private boolean dangerous = false; //if the tech is dangerous
    private String icon; //icon for the tech
    private double progress; //the current progress towards researching the tech

    //default constructor layout
    public techBuilder(String name, int tier, int cost, int rarity, int type, boolean dangerous, String icon, String desc) { //sorted by importance
        this.name = name;
        this.tier = tier;
        this.cost = cost;
        this.desc = desc;
        this.rarity = rarity;
        this.type = type;
        this.dangerous = dangerous;
        this.icon = icon;
        assignID(); //gives the tech a unique ID
    }

    private void assignID() {
        this.techID = tech_id.getAndIncrement();
    }

    public abstract void finishResearch(); //method that's run when the research finishes

    public final String getName() { return this.name; }
    public final String getDesc() { return this.desc; }
    public final int getCost() { return this.cost; }
    public final int getTier() { return this.tier; }
    public final int getRarity() { return this.rarity; }
    public final int getType() { return this.type; }
    public final long getTechID() { return this.techID; }
    public final boolean isDangerous() { return this.dangerous; }
    public final String getIcon() { return this.icon; }
    public final double getProgress() { return this.progress; }

    public final void setProgress(double prog) {
        this.progress += prog;

        if (progress > cost) { //run a couple of checks just to ensure the input is valid
            progress = cost;
        } else if (progress < 0) {
            progress = 0;
        }
    }

    public final String getResearchTree() { //gets the tree of research
        switch (this.type) {
            case TECH_PROPULSION:
                return "Propulsion";
            case TECH_INFRASTRUCTURE:
                return "Infrastructure";
            case TECH_AGRICULTURE:
                return "Agriculture";
            case TECH_KINETICS:
                return "Kinetics";
            case TECH_GENETICS:
                return "Genetics";
            case TECH_COMPUTING:
                return "Computing";
            case TECH_CHEMISTRY:
                return "Chemistry";
            case TECH_ECOLOGY:
                return "Ecology";
            case TECH_GEOLOGY:
                return "Geology";
            case TECH_OPTICS:
                return "Optics";
            case TECH_SOCIOLOGY:
                return "Sociology";
            default:
                return "Unknown";
        }
    }


}
