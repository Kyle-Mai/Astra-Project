// Name: Theo Joyce
// Describe: NNAI
import java.io.*;
import java.nio.file.*;
import java.util.Scanner;
public class NNAIHandler {
    public static void main(String[] args) {
        // Variables
        int pHealth = 100; // Player health
        int pMana = 100; // Player mana
        int eHealth = 100; // Enemy health
        int eMana = 100; // Enemy mana
        int w[] = {0,0,0,0,0,0}; // Enemy AI Weights array. {myHealth,pHealth,myMana,pMana,pArmor,myArmor}
        int cycN = 0; // NNAI cycle number
        Boolean goneOnce = false;
        String inF = "CycleCount.txt"; // Input file name

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
            cycN++; // Up cycle count
            Files.write(Paths.get(inF), String.valueOf(cycN).getBytes()); // Write to file
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
