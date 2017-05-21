package Core.GUI;

import com.sun.corba.se.impl.orbutil.graph.Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * KM
 * May 19 2017
 * Animation Core class, used to play animations for objects in their own dedicated thread.
 *
 * WORK IN PROGRESS
 *
 * SOURCES:
 * Java API - Used the Java API and examples to help me understand the syntax for animating.
 */


public class animCore extends Thread {

    int animationType;
    rotatedIcon newImage;
    ImageIcon image;
    JLabel rotatedImage;
    JLayeredPane layers;
    JFrame main;
    JPanel animationPane = new JPanel();
    double currrot = 1; //current rotation
    boolean playing = true;
    int rotationDir = 1;

    double smooth = 0.1;
    long wait = 60;

    //loads the image to be rendered
    public animCore(ImageIcon image, int animationType, JLayeredPane layers, JFrame main) {
        this.animationType = animationType;
        this.image = image;
        this.layers = layers;
        this.main = main;

    }

    @Override
    public void run() {

        playAnimation();

        System.out.println("Animation complete. Killing thread.");
        interrupt();
    }

    //loads and plays the animation
    private void playAnimation() throws NullPointerException {

        switch (animationType) {
            case 1:
                //add the animation layer to the UI
                layers.add(animationPane, new Integer(10), 0);
                animationPane.setLayout(null);
                animationPane.setBounds(0,0, main.getWidth(), main.getHeight());
                animationPane.setOpaque(false);
                animationPane.setVisible(true);

                //TODO: Fix float point error

                while (playing) {

                    if (currrot < 11 && rotationDir == 1) { //continue rotating until we hit max/min then switch directions
                        currrot = currrot + smooth;
                    } else if (currrot > (-6) && rotationDir == 0) {
                        currrot = currrot - smooth;
                    } else if (currrot <= -6) {
                        rotationDir = 1;
                        currrot = currrot + smooth;
                    } else if (currrot >= 11) {
                        rotationDir = 0;
                        currrot = currrot - smooth;
                    }

                    newImage = new rotatedIcon(image, currrot);
                    rotatedImage = new JLabel(newImage);
                    animationPane.removeAll();

                    animationPane.add(rotatedImage);
                    rotatedImage.setVisible(true);
                    rotatedImage.setBounds(main.getWidth() - 600, 50, 500, 500);

                    main.revalidate();
                    main.repaint();

                    pauseThread();
                }
            break;

        }

    }

    //temporarily pauses the thread
    private void pauseThread() {
        try {
            sleep(wait);
        } catch (InterruptedException uhoh) {
            uhoh.getMessage();
        }
    }

    public void setPlaying(boolean isPlaying) {
        this.playing = isPlaying;
    }

    //changes the smoothness of the animation
    public void setAnimationSmoothness(double smoothAmount, long waitTime) {
        smooth = smoothAmount;
        wait = waitTime;
    }


}