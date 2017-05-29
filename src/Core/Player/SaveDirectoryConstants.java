package Core.Player;

import java.io.File;

/**
 * Constants for save directory related stuff.
 */

public interface SaveDirectoryConstants {
    String BASE = "";
    String DATA = "data";
    File SAVEDIRECTORY = new File(System.getProperty("user.dir") + "/Saves/"); //directory location
}
