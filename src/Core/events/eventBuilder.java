package Core.events;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * KM
 * June 06 2017
 * Abstract class for constructing event blueprints.
 */

public abstract class eventBuilder implements eventConstants {

    private String name; //name of the event
    private String desc; //event's description text
    private int type; //the event header colour
    private BufferedImage image; //the image in the event window
    private boolean repeatable; //whether or not the event will trigger more than once
    public ArrayList<Option> button = new ArrayList<>(); //the buttons that will be displayed in the event window
    private final File imageFolder = new File(System.getProperty("user.dir") + "/src/Core/GUI/Resources/event/");

    eventBuilder(String name, int type, boolean willRepeat, String desc) { //constructor
        this.name = name;
        this.desc = desc;
        this.type = type;
        this.repeatable = willRepeat;

    }

    eventBuilder(String name, int type, boolean willRepeat, String image, String desc) { //constructor with image
        this.name = name;
        this.desc = desc;
        this.type = type;
        this.repeatable = willRepeat;

        try { //breaking my usual UI initialization just this once
            this.image = ImageIO.read(new File(imageFolder + "/" + image));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public abstract boolean eventTrigger(); //the conditions required to trigger the event

    public void loadOptions() { //method for constructing buttons, can be overwritten
        Option btnDefault = new Option("Okay.", "") {
            @Override
            public void clickButton() {
                System.out.println("Default button pressed - were the event's proper buttons not initialized?");
            }
        };
        button.add(btnDefault); //load a default button in case none are initialized
    }

    public abstract  void eventOpen(); //what happens when the event window is 'activated'

    public final String getName() { return this.name; }
    public final String getDesc() { return this.desc; }
    public final int getType() { return this.type; }
    public final BufferedImage getImage() { return this.image; }
    public final boolean isRepeatable() { return this.repeatable; }
    public final Option getButton(int buttonNumber) { return button.get(buttonNumber); }
    final void addOption(Option newbutton) { //adds options to the buttons
        button.add(newbutton);
    }

    public abstract class Option { //a way of creating and storing the different buttons in the event
        String buttonText; //text on the button itself
        String mouseOverText; //text displayed when moused over

        Option(String text, String tooltip) { //constructor
            this.buttonText = text;
            this.mouseOverText = tooltip;
        }

        public abstract void clickButton(); // method is run when the button is clicked

        public final String getButtonText() { return this.buttonText; }
        public final String getMouseOverText() { return this.mouseOverText; }

    }

}
