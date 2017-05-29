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

    public XMouseListener(int x, int y) {
        this.Xv = x;
        this.Yv = y;
    }

    private String IDValue;
    private int Xv;
    private int Yv;

    public void setIDValue(String ID) { this.IDValue = ID; }
    public String getIDValue() { return this.IDValue; }

    public void setXY(int x, int y) {
        this.Xv = x;
        this.Xv = y;
    }
    public int getValueX() {
        return this.Xv;
    }
    public int getValueY() {
        return this.Yv;
    }


}
