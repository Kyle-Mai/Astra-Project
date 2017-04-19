// Author : VulcanDev

import java.util.ArrayList;

public class AI_EventHandler {
    private int eventType; // Type of event
    private ArrayList<Integer> facStat; // Faction's and their status

    // Default Constructor - Initialization of AI
    public AI_EventHandler(ArrayList<Integer> factionStatus){
        facStat = factionStatus;
    }
}
