package Core.Craft;

import java.util.ArrayList;

/**
 * KM
 * June 03 2017
 * Constructs different ships.
 */


public class craftBuilder {

    public transient ArrayList<craftCore>  shipStorage = new ArrayList<>();

    public craftCore s_trailblazer;

    public craftBuilder() {
    }

    public void buildScienceShips() { //builds the science ships
        System.out.println("Preparing science ships...");
        s_trailblazer = new shipScience("Trailblazer", 20, 4, 25, 40, 20, 0.45, "trailblazer_01.png", 15);

    }

    public void refreshArray() { //adds the ships into the array
        shipStorage.add(s_trailblazer);
    }


}
