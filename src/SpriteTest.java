import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;
//import java.awt.*;

// Name: Theo Joyce
// Describe: Sprite testing class
public class SpriteTest extends Application {
    public static void main(String[] args){
        launch(args);
    }
    // Reference: https://gamedevelopment.tutsplus.com/tutorials/introduction-to-javafx-for-game-development--cms-23835
    public void start (Stage stage){
        // Test code from reference
        stage.setTitle("Hello");
        stage.setTitle( "Canvas Example" );

        Group root = new Group();
        Scene scene = new Scene( root );
        stage.setScene(scene);

        Canvas canvas = new Canvas(1024, 1024);
        root.getChildren().add(canvas);

        final GraphicsContext gc = canvas.getGraphicsContext2D();

        final Image earth = new Image("earth.png");
        final Image space = new Image("space.jpg");
        final Image sun = new Image("sun.png");

        final long startNanoTime = System.nanoTime();
        new AnimationTimer(){
            public void handle(long currentNanoTime){
                double t = (currentNanoTime - startNanoTime) / (1*Math.pow(10,9));

                double x = 232 + 128 * Math.cos(t);
                double y = 232 + 128 * Math.sin(t);

                gc.drawImage(space,0,0);
                gc.drawImage(earth,x,y);
                gc.drawImage(sun,196,196);

            }
        }.start();

        stage.show();
    }
}
