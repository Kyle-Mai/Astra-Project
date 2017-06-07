package Core.events;

import java.awt.event.MouseListener;

/**
 KM
 June 07 2017
 Mouse Listener for the event buttons.
*/

public abstract class EMouseListener implements MouseListener {

    private eventBuilder.Option button;

    public EMouseListener(eventBuilder.Option button) {
        this.button = button;
    }

    public eventBuilder.Option getButton() { return this.button; }

}
