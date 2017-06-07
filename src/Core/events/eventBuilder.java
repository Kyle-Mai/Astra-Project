package Core.events;

import java.awt.image.BufferedImage;

/**
 * KM
 * June 06 2017
 * Abstract class for constructing event blueprints.
 */

public abstract class eventBuilder {

    String name;
    String desc;
    int type;
    BufferedImage image;

    public eventBuilder(String name, int type, String desc) {
        this.name = name;
        this.desc = desc;
        this.type = type;

    }

    public abstract  void eventOpen(); //what happens when the event window is 'activated'

    public abstract void eventTrigger(); //method that runs when the event is triggered

    public final String getName() { return this.name; }
    public final String getDesc() { return this.desc; }
    public final int getType() { return this.type; }
    public final BufferedImage getImage() { return this.image; }

}
