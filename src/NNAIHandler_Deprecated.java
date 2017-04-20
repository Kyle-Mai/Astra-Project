// Name: Theo Joyce
// Describe: NNAI
import java.io.*;
import java.nio.file.*;
import java.util.Scanner;
public class NNAIHandler_Deprecated {
    public static void main(String[] args) {
        // Variables
        int pHealth = 100; // Player health
        int pHealthMax = 100; // Player's max health
        int pMana = 100; // Player mana
        int pManaMax = 100; // Player's max mana
        int eHealth = 100; // Enemy health
        int eHealthMax = 100; // Enemy max health
        int eMana = 100; // Enemy mana
        int eManaMax = 100; // Enemy max mana
        int pArmor = 10; // Player's armor [Constant Temp]
        int eArmor = 15; // AI's armor [Constant Temp]
        int pDamage = 30; // Player's attack damage
        int eDamage = 20; // AI's attack damage
        int w[] = {20, 10, 5, 8, 10, 12}; // Enemy AI weights array. {myHealth,pHealth,myMana,pMana,pArmor,myArmor}
        Boolean turn = false; // Turn. false = Player, true = AI
        int cycN = 0; // NNAI cycle number
        Boolean goneOnce = false;
        String inF = "CycleCount.txt"; // Input file name


        // Begin NNAI logic
        while (pHealth > 0 && eHealth > 0){
            if (!turn) {
                if (pHealth > 0 && w[0] > w[1]) {
                    // AI attacks
                    pHealth = pHealth + ((pArmor-eDamage<0) ? pArmor-eDamage : 0);
                }
                turn = true;
                System.out.println("AI ATTACK "+pHealth);
            }else{
                // Player attacks
                if (eHealth > 0) {
                    eHealth = eHealth + ((eArmor-pDamage<0) ? eArmor-pDamage : 0);
                }
                turn = false;
                System.out.println("Player ATTACK "+eHealth);
            }

        }
        System.out.println("Battle Results: ");
        if (eHealth<=0){
            System.out.println("Player WIN");
        }else {
            System.out.println("AI WIN");
        }
        // Objects


        // Attempt to create input file
        try {
            File file = new File(inF); // New file

            // Check if file was created
            if (file.createNewFile()) {
                System.out.println("File created!");
            } else {
                System.out.println("File already exits..");
            }
        }catch (IOException e){
            e.printStackTrace(); // Print error
        }
        try {
            Scanner sc = new Scanner(new File(inF));
            // Cycles read from file
            while (sc.hasNext()) {
                if (!goneOnce) {
                    cycN = Integer.parseInt(sc.next()); // Reads cycle count to variable
                    goneOnce = true; // Stops variable from being overwritten by anything
                }
            }
            sc.close(); // Closes scanner stream
        }catch (IOException e){
            e.printStackTrace();
        }

        // Write to cycles file
        try {
            //cycN++; // Up cycle count -- Commented out temp due to no progress per cycle
            Files.write(Paths.get(inF), String.valueOf(cycN).getBytes()); // Write to file
            //System.out.println(cycN);
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
