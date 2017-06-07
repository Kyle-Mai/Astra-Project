package Core.events;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * KM
 * June 06 2017
 * Abstract class for constructing event blueprints.
 */

public abstract class eventBuilder {

    String name; //name of the event
    String desc; //event's description text
    int type; //the event header colour
    BufferedImage image; //the image in the event window
    boolean repeatable; //whether or not the event will trigger more than once
    public ArrayList<Option> button = new ArrayList<>(); //the buttons that will be displayed in the event window

    eventBuilder(String name, int type, boolean willRepeat, String desc) { //constructor
        this.name = name;
        this.desc = desc;
        this.type = type;
        this.repeatable = willRepeat;

    }

    public abstract boolean eventTrigger(); //the conditions required to trigger the event

    public void loadOptions() { //method for constructing buttons
        Option btnDefault = new Option("No button data!", "Uh oh!", 0) {
            @Override
            public void clickButton() {
                System.out.println("Event broken! No buttons initialized!");
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
        int option; //identifier for the button

        Option(String text, String tooltip, int option) { //constructor
            this.buttonText = text;
            this.mouseOverText = tooltip;
            this.option = option;
        }

        public abstract void clickButton(); // method is run when the button is clicked

        public final String getButtonText() { return this.buttonText; }
        public final String getMouseOverText() { return this.mouseOverText; }
        public final int getOption() { return this.option; }

    }

}
