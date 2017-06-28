package Core.IO;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

// Created By VulcanDev
class DataManager {
    boolean saved = false;
    boolean loaded = false;

    DataManager(){
        if (!new File("Saves").exists()) {new File("Saves").mkdir();} // Create "Saves" directory if it doesn't exist
    }

    // Basic save method making a test file
    void save(String saveName){
        File save = new File("Saves/"+saveName);
        // Try to create save
        try {
            Files.write(Paths.get(save.toURI()), "Test".getBytes());
            saved = true;
        }catch (IOException ioE){
            ioE.printStackTrace();
            saved = false;
        }

    }
    // Basic load method testing loading
    void load(String saveName){
        File save = new File("Saves/"+saveName);
        // Try to read file
        try {
            Scanner scanner = new Scanner(save);
            while (scanner.hasNextLine()){
                System.out.println(scanner.nextLine()); // Not really loading into a variable but proves the point
            }
            loaded = true;
        }catch (IOException e){
            e.printStackTrace();
            loaded = false;
        }
    }

}
