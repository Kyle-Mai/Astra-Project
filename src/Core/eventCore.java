package Core;

/**
 * KM
 * May 24 2017
 * Handles storage of in-game events.
 */
public class eventCore {

    private double fundMultiplier; //multiplier applied to player's cash income
    private double resourceMultiplier; //multiplier applied to player's resource income
    private double unrestMultiplier; //multiplier applied to player's global unrest
    private int eventTime; //how long the event lasts for (in turns)

    private String eventName; //event name
    private String eventDesc; //event description/text
    private int eventID; //ID number of the event

    public eventCore() {
    }

    public eventCore(String name, String desc, int ID, int duration, double fund, double resource, double unrest) {
        this.eventName = name;
        this.eventDesc = desc;
        this.eventID = ID;
        this.eventTime = duration;
        this.fundMultiplier = fund;
        this.unrestMultiplier = unrest;
        this.resourceMultiplier = resource;

    }

    public String getEventName() { return this.eventName; }
    public String getEventDesc() { return this.eventDesc; }

    public int getEventTime() { return this.eventTime; }
    public int getEventID() { return this.eventID; }

    public double getFundMultiplier() { return this.fundMultiplier; }
    public double getResourceMultiplier() { return this.resourceMultiplier; }
    public double getUnrestMultiplier() { return this.unrestMultiplier; }

}
