package Core.GUI.SwingEX;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * KM
 * June 04 2017
 * A special (new!) class designed to create and sort items in a list, similar to a Swing layout, but with more function.
 * It can create either a vertical or horizontal list of items, and use either preset spacing or custom spacing, among other things.

 * SOURCES:
 * None in specific. Completely custom created through previously acquired knowledge.
 *
 * CONCEPTS:
 * Swing, ArrayLists, Classes/Objects,
 */

public class XListSorter implements XConstants {

    private ArrayList<JComponent> list = new ArrayList<>(); //items that will be sent for placement

    private int sort;
    //private int orientationx, orientationy;
    private int sizex, sizey, posx, posy;
    private int space;
    //private boolean resize = false;
    private boolean autosize = false;
    private int inx, iny;

    public XListSorter() { //blank constructor
    }

    public XListSorter(int sort) {
        this.sort = sort;
    }

    public XListSorter(int sort, int space) {
        this.space = space;
        this.sort = HORIZONTAL_SORT;
    }

    public XListSorter(int sort, int x, int y) {
        this.posx = x;
        this.posy = y;
        this.sort = sort;
    }

    public XListSorter(int sort, int space, int x, int y) {
        this.posx = x;
        this.posy = y;
        this.sort = sort;
        this.space = space;
    }

    public void forceItemSize(int x, int y) { this.sizex = x; this.sizey = y; this.autosize = true; } //sets the scale of the items in the list
    public void setListPosition(int x, int y) { this.posx = x; this.posy = y; } //sets the list's position on the component
    public void setSpacing(int space) { this.space = space; } //spacing between components
    //public void resizeContainer(boolean b) { this.resize = b; } //whether or not the list will resize the container to fit the items (default false)

    public void setOrientation(int orientation) { this.sort = orientation; } //sets the orientation of the list sorter
    //public void setXAlignment(int orientation) { this.orientationx = orientation; } //sets the horizontal alignment of the list
    //public void setYAlignment(int orientation) { this.orientationy = orientation; } //sets the vertical alignment of the list
    //public void setAlignments(int o, int o2) { this.orientationx = o; this.orientationy = o2; } //sets both orientations at the same time

    //adds items to the listsorter, either singular or multiple at once
    public void addItem(JComponent c) { list.add(c); } //adds a component to the list sorter
    public void addItems(JComponent... c) { list.addAll(Arrays.asList(c)); } //adds a group of components to the list sorter

    //clears items from the list
    public void removeItem(JComponent c) { list.remove(c); }
    public void removeItem(int loc) { list.remove(loc); }
    public void removeAll() { list.clear(); }

    public JComponent getComponent(JComponent c) { return list.get(list.indexOf(c)); } //gets a component from the list

    public void placeItems(JComponent c) { //adds the components to the specified component
        int s1, s2; // size
        int counter = 0;

        if (list.size() == 0) { //no items > don't attempt to place
            System.out.println("XListSorter - No items in the queue, aborting item placer.");
            return;
        }

        switch (sort) { //sets the orientation parameters
            case HORIZONTAL_SORT: //horizontal list
                s1 = sizex + space;
                s2 = 0;
            break;
            case VERTICAL_SORT: //vertical list
                s2 = sizey + space;
                s1 = 0;
                break;
            default: //default to horizontal in worst case
                s1 = sizex + space;
                s2 = 0;
                break;
        }

        for (JComponent item : list) { //get the inputted components
            try { //attempt to add item to the specified component
                c.add(item);
            } catch (Exception e) { //if it errors during generation, skip it and move to the next iteration
                System.out.println("ListSorter component #" + (counter + 1) + " was not initialized: " + e.getMessage());
                continue;
            }
            if (autosize) { //automatically scale components
                item.setBounds(posx + (s1 * counter), posy + (s2 * counter), sizex, sizey);
            } else { //use component's current size
                s1 = 0;
                s2 = 0;
                item.setSize(item.getPreferredSize());
                switch (sort) {
                    case HORIZONTAL_SORT:
                        item.setLocation(posx + s1, posy);
                        posx += item.getWidth() + space;
                        s1 = item.getWidth();
                        break;
                    case VERTICAL_SORT:
                        item.setLocation(posx, posy + s2);
                        posy += item.getHeight() + space;
                        s2 = item.getHeight();
                        break;
                    default:
                        item.setLocation(posx + s1, posy);
                        posx += item.getWidth() + space;
                        s1 = item.getWidth();
                        break;
                }
            }
            item.setVisible(true);
            counter++;
        }
    } //placeItems method complete


}
