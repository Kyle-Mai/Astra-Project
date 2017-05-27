package Core.GUI.SwingEX;
import java.awt.event.MouseListener;

/**
 KM
 May 26 2017
 Abstract class to add extra functionality to the MouseListener.
 */


public abstract class XMouseListener implements MouseListener {

    public  XMouseListener() { //blank constructor
    }

    public XMouseListener(String designatedID) { //constructor that takes in an ID number
        this.setIDValue(designatedID);
    }

    private String IDValue;

    public void setIDValue(String ID) { this.IDValue = ID; }
    public String getIDValue() { return this.IDValue; }


}
