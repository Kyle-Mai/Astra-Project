package AetheriusEngine.core.locale;

import java.util.*;

/*
Lolita's Revenge
July 07 2017

Handles lists of strings that can be pulled from to generate something like a name.
*/

public class NameList extends LinkedList<String> {

    /*------------------------------------------------------------------------------------------------------------------
     Variables.
     Used to determine the function of the class.
     */

    private boolean useDuplicates = false; //Whether or not the NameList will use the same strings multiple times.
    private boolean storeUsed = true; //Whether or not the NameList will store the used strings in the 'used' ArrayList.
    private List<String> used = new LinkedList<>();

    /*------------------------------------------------------------------------------------------------------------------
     Constructors.
     Used to construct instances of the NameList.
     */

    public NameList() {}

    public NameList(String... s) { Collections.addAll(this, s); }

    public NameList(boolean store, boolean dupl) {
        storeUsed = store;
        useDuplicates = dupl;
    }

    public NameList(boolean store, boolean dupl, String... s) {
        Collections.addAll(this, s);
        storeUsed = store;
        useDuplicates = dupl;
    }

    /*------------------------------------------------------------------------------------------------------------------
     Accessible methods.
     Methods that can be accessed from outside the name list class
     */

    public boolean isUsingDuplicates() { return useDuplicates; }
    public void setDuplicate(boolean b) { useDuplicates = b; }

    public boolean isStoringUsed() { return storeUsed; }
    public void setStoreUsed(boolean b) { storeUsed = b; }

    public void add(String... s) { Collections.addAll(this, s); }

    public String getUsed(int i) { return used.get(i); }
    public int getUsedCount() { return used.size(); }
    public void clearUsed() { used.clear(); }
    public void clearAll() {
        clear();
        used.clear();
    }

    public void reset() { //Resets the list to the default state.
        if (used.size() > 0) {
            addAll(used);
            used.clear();
        }
    }

    public String generate() { //Gets a name from the name list.
        if (size() <= 0) throw new NoSuchElementException("No strings were found in the NameList. Cannot generate.");
        Random r = new Random();
        int i = r.nextInt(size());
        if (storeUsed) { used.add(get(i)); } //Stores a list of the used strings if applicable.
        if (!useDuplicates) { remove(i); } //If we aren't reusing names, remove this one from the primary list.
        return used.get(used.size() - 1);
    }

    //------------------------------------------------------------------------------------------------------------------

}
