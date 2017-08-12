package AetheriusEngine.core.gui;

/*
Lolita's Revenge
June 29 2017

Sorts components in a list based on the user's input. Can sort an indefinite number of items, and can also resize the parent container when requested.
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class XListSorter implements XConstants {

    /*------------------------------------------------------------------------------------------------------------------
     Variables.
     Used to determine the function of the List Sorter.
     */

    private ArrayList<JComponent> list = new ArrayList<>(); //Items that will be sent for placement
    private int sizex, sizey; //When a components size is forced via (autosize = true), this is the size that will be enforced on all components in the sorter
    private int posx, posy; //The initial position that the list will start sorting from
    private int space, sort; //The space between the components, and the style of sorting being used (IE. horizontal, vertical, etc)
    private boolean resize = false; //Whether or not the sorter will automatically resize the parent container when required
    private boolean autosize = false; //Whether or not the sorter will force a specific size on all of the components it is sorting

    /*------------------------------------------------------------------------------------------------------------------
     Constructors.
     Used to construct instances of the List Sorter.
     */

    public XListSorter() {} //blank constructor

    public XListSorter(int sort) { this.sort = sort; }

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

    /*------------------------------------------------------------------------------------------------------------------
     Container editor methods.
     Used to edit the List Sorter's parameters.
     */

    public void forceItemSize(int x, int y) { this.sizex = x; this.sizey = y; this.autosize = true; } //sets the scale of the items in the list
    public void setLocation(int x, int y) { this.posx = x; this.posy = y; } //sets the list's position on the component
    public void setItemSpacing(int space) { this.space = space; } //spacing between components
    public void scaleParent(boolean b) { this.resize = b; } //whether or not the list will resize the container to fit the items (default false)
    public void setAlignment(int sortStyle) { this.sort = sortStyle; } //sets the alignment of the sorter

    public boolean isAutoSizing() { return autosize; }
    public boolean isResizing() { return resize; }

    public int getPosX() { return posx; }
    public int getPosY() { return  posy; }
    public int getSizeX() { return sizex; }
    public int getSizeY() { return  sizey; }
    public int getSortStyle() { return sort; }
    public int getSpace() { return space; }

    public void resizeParent(boolean b) { this.resize = b; }

    /*------------------------------------------------------------------------------------------------------------------
     List editor methods.
     Used to manipulate the List Sorter's items.
     */

    public void addItem(JComponent c) { list.add(c); } //adds a component to the list sorter
    public void addItem(int layer, JComponent item) { list.add(layer, item); } //adds an items to a specific layer
    public void addItems(JComponent... c) { list.addAll(Arrays.asList(c)); } //adds a group of components to the list sorter
    public void replaceItem(JComponent newComponent, JComponent oldComponent) { list.add(list.indexOf(oldComponent), newComponent); } //replaces an item in the list

    public void removeItem(JComponent c) { list.remove(c); }
    public void removeItem(int loc) { list.remove(loc); }
    public void removeAll() { list.clear(); }

    public JComponent getItem(int arraypos) { return list.get(arraypos); }
    public JComponent getItem(JComponent c) { return list.get(list.indexOf(c)); } //gets a component from the list
    public int getItemCount() { return list.size(); } //returns the number of items in the sorter
    public int getItemIndex(JComponent component) { return list.indexOf(component); } //returns the index of an item in the list

    /*------------------------------------------------------------------------------------------------------------------
     MainInterface methodology.
     */

    public void placeItems(JComponent c) { //Adds the components to the specified component

        if (list.size() == 0) { //no items > don't attempt to place
            System.out.println("XListSorter - No items in the queue, aborting item placer.");
            return;
        }

        for (JComponent item : list) { //Get the inputted components
            try { //Attempt to add item to the specified component
                c.add(item);
            } catch (Exception e) { //if it errors during generation, skip it and move to the next iteration
                System.out.println("ListSorter component was not initialized: " + e.getMessage());
                continue;
            }

            if (autosize) { //Force specific scale on components
                item.setSize(sizex, sizey);
            } else { //use component's existing size
                item.setSize(item.getPreferredSize());
            }

            switch (sort) {
                case HORIZONTAL_SORT:
                    item.setLocation(posx, posy);
                    posx += item.getWidth() + space;
                    break;
                case VERTICAL_SORT:
                    item.setLocation(posx, posy);
                    posy += item.getHeight() + space;
                    break;
                case VERTICAL_SORT_REVERSE:
                    posy -= item.getHeight() + space;
                    item.setLocation(posx, posy);
                    break;
                case HORIZONTAL_SORT_REVERSE:
                    posx -= item.getWidth() + space;
                    item.setLocation(posx, posy);
                    break;
                default:
                    System.out.println("ListSorter sort method not properly specified. Defaulting to HORIZONTAL_SORT.");
                    item.setLocation(posx, posy);
                    posx += item.getWidth() + space;
                    break;
            }

            if (resize) { //If the sorter is allowed to resize the parent container, resize it when necessary
                if (posx + space > c.getWidth() && sort == HORIZONTAL_SORT) {
                    c.setPreferredSize(new Dimension(posx + space, c.getHeight()));
                    c.setSize(posx + space, c.getHeight());
                } else if (posy + space > c.getHeight() && sort == VERTICAL_SORT) {
                    c.setPreferredSize(new Dimension(c.getWidth(), posy + space));
                    c.setSize(c.getWidth(), posy + space);
                }
                //todo: add auto-sizing for reverse sorts
            }

            item.setVisible(true);
        }

    } //placeItems method complete

    //------------------------------------------------------------------------------------------------------------------

}
