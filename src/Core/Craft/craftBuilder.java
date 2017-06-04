package Core.Craft;

/**
 * KM
 * June 03 2017
 * Constructs different ships.
 */


public class craftBuilder {

    public static craftCore s_trailblazer;

    public static void buildScienceShips() {
        System.out.println("Preparing ships...");

        s_trailblazer = new shipScience("Trailblazer", 20, 4, 25, 40, 20, 0.45, "/Core/GUI/Resources/ships/trailblazer_01.png", 15);

    }


}
