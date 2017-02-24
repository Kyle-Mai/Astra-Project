// Name: Theo Joyce
// Describe: NNAI
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NNAIHandler {
    public static void main(String[] args) {
        // Variables
        int pHealth = 100; // Player health
        int pMana = 100; // Player mana
        int eHealth = 100; // Enemy health
        int eMana = 100; // Enemy mana
        int w[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        List<String> reader = new ArrayList<>(1); // Stores lines temp
        String[] cycN = null; // NNAI cycle number

        String inF = "CycleCount.txt"; // Input file name
        String outF = "output.txt"; // Output file name

        // Objects
        Path in = Paths.get(inF);


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
                reader.add(sc.nextLine());
            }
            cycN = reader.toArray(new String[0]);
            sc.close();

        }catch (IOException e){
            e.printStackTrace();
        }


        // Write to cycles file
        try {
            Writer writer = new PrintWriter("CycleCount.txt","UTF-8");
            //cycN[0] = Integer.parseInt(cycN[0])+ 1; -- FIX
            writer.write(cycN[0]);
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
