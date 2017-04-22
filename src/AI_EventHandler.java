// Author : VulcanDev

import java.util.ArrayList;

public class AI_EventHandler {
    private int eventType; // Type of event
    private ArrayList<Integer> facStat; // Faction's and their status
    private int sysStatus; // Status of the current planet system

    // Default Constructor - Initialization of AI
    public AI_EventHandler (ArrayList<Integer> factionStatus, int systemStatus){
        // Statuses of factions and the system overall collected
        facStat = factionStatus;
        sysStatus = systemStatus;
    }
    // Event generation TODO: Pretty much everything
    void generateEvent(){
        // Currently just roughly figures out and displays the current situation
        System.out.print("Planetary system is currently ");
        if (sysStatus == 0){ // Neutral
            System.out.println("neutral.");
        }else if (sysStatus == 1){ // Combatant
            System.out.println("combatant.");
            for (int i = 0; i<facStat.size(); i++) {
                if (facStat.get(i) == 0) {
                    System.out.println("Faction "+(i+1)+" is stable.");
                }else if (facStat.get(i) == 1){
                    System.out.println("Faction "+(i+1)+" is unstable.");
                }else if (facStat.get(i) == 2){
                    System.out.println("Faction "+(i+1)+" is undergoing a crisis.");
                }
            }
        }else if (sysStatus == 2){ // Chaotic
            System.out.println("chaotic.");
        }// TODO: Continue this chain of statuses
    }
}
