import java.awt.*;
import java.io.IOException;
import javax.microedition.lcdui.game.*;
// Name: Theo Joyce
// Describe: Battle logic class
public class battle {
    public static void main(String[] args){
        System.out.println("「めんどくさい！」"); // Changes to how I feel about the class at the time of editing
        Image sprite;
        javax.microedition.lcdui.Image img;

        try {
            img = javax.microedition.lcdui.Image.createImage("spritesheet.jpg");
        }catch (IOException e){
            e.printStackTrace();
            sprite = null;
            img = null;
        }
        // Sprite deemed unusable
        Sprite test = new Sprite(img, 90, 90);
    }
}
