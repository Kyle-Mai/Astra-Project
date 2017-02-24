// Name: Theo Joyce
// Describe: NNAI
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class NNAIHandler {
    public static void main(String[] args) {
        // Variables
        int pHealth = 100; // Player health
        int pMana = 100; // Player mana
        int eHealth = 100; // Enemy health
        int eMana = 100; // Enemy mana
        int w[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        int cycN = 0; // NNAI cycle number

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
        // Cycles read from file
        //cycN = [Read file to it here]

        // Write to cycles file
        Path file = Paths.get(inF); // Stores path to file
        // Uncomment below once cycN fixed
        //Files.write(file, cycN, Charset.forName("UTF-8"), StandardOpenOption.TRUNCATE_EXISTING); // Writes to file ([path], [lines], [Charset], [Write Type])

    }
    static String readFile(Path path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(path);
        return new String(encoded, encoding);
    }
}
