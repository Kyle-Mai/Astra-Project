package Core.GUI.SwingEX;
import java.awt.event.MouseListener;

/**
 KM
 May 26 2017
 Abstract class to add extra functionality to the MouseListener.
 */


public abstract class XMouseListener implements MouseListener {

    //variables
    private String IDValue;
    private int Xv;
    private int Yv;

    //constructors
    public  XMouseListener() { //blank constructor
    }

    public XMouseListener(String designatedID) { //constructor that takes in an ID number
        this.setIDValue(designatedID);
    }

    public XMouseListener(int x, int y) { //constructor that takes in an x and y coordinate (for say, referencing a 2D array)
        this.Xv = x;
        this.Yv = y;
    }

    //setters
    public void setIDValue(String ID) { this.IDValue = ID; }

    public void setXY(int x, int y) {
        this.Xv = x;
        this.Xv = y;
    }

    //getters
    public String getIDValue() { return this.IDValue; }
    public int getValueX() {
        return this.Xv;
    }
    public int getValueY() {
        return this.Yv;
    }

}
