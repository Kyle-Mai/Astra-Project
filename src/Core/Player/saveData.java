package Core.Player;

import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 KM
 May 26 2017
 Handles the saving of player data.

 SOURCES:
 Mkyong - Syntax for writing to XML via https://www.mkyong.com/java/how-to-create-xml-file-in-java-dom/
 */


public class saveData {

    private String userID;
    private File saveDirectory;

    saveData(String userID) {
        this.userID = userID;
        saveDirectory = new File(SaveDirectoryConstants.SAVEDIRECTORY + "/" + userID);

    }

    public boolean validate() { //checks to see if the directory already exists or not
        return saveDirectory.exists() && saveDirectory.isDirectory();
    }

    public void create() { //creates a new folder if one doesn't already exist

        if (!SaveDirectoryConstants.SAVEDIRECTORY.exists()) { //if the saves folder is compromised, create a new one
            SaveDirectoryConstants.SAVEDIRECTORY.mkdir();
            System.out.println("[SD](create) Unexpected token - Save directory not found. Creating...");
        }

        if (!validate()) { //check to ensure we don't overwrite an existing save
            saveDirectory.mkdir(); //attempt to create the save folder
            if (validate()) {
                System.out.println("[SD](create) New save folder created successfully."); //if it is created successfully, output accordingly
            }
        } else {
            System.out.println("[SD](create) Unexpected Error - User directory already exists.");
        }
    }

    public void add(File directory, int dataType, String fileName) { //adds new folders to the current folder

        File temp;

        success:{
            switch (dataType) {
                case 1: //folder
                    temp = new File(directory + "/" + fileName);
                    if (!temp.exists()) {
                        temp.mkdir();
                    }
                    break success; //if the file is created, end the method

                case 2: //serial file
                    temp = new File(directory + "/" + fileName + ".ser");
                    if (!temp.exists()) {
                        temp.mkdir();
                    }
                    break;

                case 3:
                    break;

                case 4:
                    break;

                case 5:
                    temp = new File(directory + "/" + fileName + ".xml");

                    break;

                default:
                    System.out.println("[SD](add) Unknown file type.");
                    break success;

                }
            System.out.println("[SD](add) File creation failed.");
        }
    }

    public File get() {
        return this.saveDirectory;
    }

    public File get(String directory) {
        return new File(saveDirectory + "/" + directory);
    }

    public File get(File location, String name) {
        return new File(location + "/" + name);
    }

    public void write(File file, Object obj) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file + ".ser"));
        outputStream.writeObject(obj);
        outputStream.close();
    }

    public void write(File file, Document xml) throws ParserConfigurationException, TransformerException { //writes XML data

        if (file.exists()) {
            file.delete();
        }

        TransformerFactory tff = TransformerFactory.newInstance();
        Transformer tf = tff.newTransformer();
        DOMSource source = new DOMSource(xml);
        StreamResult input = new StreamResult(file);
        tf.transform(source, input); //outputs the new XML

    }


}
