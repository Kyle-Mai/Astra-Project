import java.util.ArrayList;

// Author: VulcanDev
public class temp_Controller {
    public static void main(String args[]){
        // Temporary "factions"
        int fac1Stat = 1;
        int fac2Stat = 0;
        ArrayList<Integer> factionStatuses = new ArrayList<>();
        factionStatuses.add(fac1Stat);
        factionStatuses.add(fac2Stat);
        // Temporary system status
        int systemStatus = 1;
        // Event handler created and run
        AI_EventHandler eventHandler = new AI_EventHandler(factionStatuses, systemStatus);
        eventHandler.generateEvent();
    }
}
