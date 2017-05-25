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

    private int animationType;
    private rotatedIcon newImage;
    private ImageIcon image;
    private JLabel displayedImage;
    private JLayeredPane layers;
    private JFrame main;
    private JPanel animationPane = new JPanel();
    private double currrot = 1; //current rotation
    private boolean playing = true;
    private int rotationDir = 1;
    private int xVal, yVal;
    private float radius;

    private double smooth = 1;
    private long wait = 100;

    //loads the image to be rendered
    public animCore(ImageIcon image, int animationType, JLayeredPane layers, JFrame main) {
        this.animationType = animationType;
        this.image = image;
        this.layers = layers;
        this.main = main;

    }

    public animCore(ImageIcon image, int animationType, JLayeredPane layers, JFrame main, int x, int y, float radius) {
        this.animationType = animationType;
        this.image = image;
        this.layers = layers;
        this.main = main;
        this.xVal = x;
        this.yVal = y;
        this.radius = radius;

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
                    displayedImage = new JLabel(newImage);
                    animationPane.removeAll();

                    animationPane.add(displayedImage);
                    displayedImage.setVisible(true);
                    displayedImage.setBounds(main.getWidth() - 600, 50, 500, 500);

                    main.revalidate();
                    main.repaint();

                    pauseThread();
                }
            break;

            case 2: //main menu spaceport
                System.out.println("Playing menu spaceport animation.");

                double positionY = 0;
                double positionX = 0;
                int moveX = 1;
                int moveY = 1;
                //add the animation layer to the UI
                layers.add(animationPane, new Integer(11), 0);
                animationPane.setLayout(null);
                animationPane.setBounds(0,0, main.getWidth(), main.getHeight());
                animationPane.setOpaque(false);
                animationPane.setVisible(true);

                displayedImage = new JLabel(image);
                animationPane.add(displayedImage);
                displayedImage.setVisible(true);
                displayedImage.setBounds(main.getWidth() - 550, 20, 550, 382);

                while (playing) {

                    displayedImage.setLocation(stationBob(displayedImage.getX(), displayedImage.getY(), positionY));

                    if (positionY < 2 && moveY == 1) {
                        positionY = positionY + smooth;
                    } else if (positionY > (-2) && moveY == 0) {
                        positionY = positionY - smooth;
                    } else if (positionY <= (-2)) {
                        moveY = 1;
                        positionY = positionY + smooth;
                    } else if (positionY >= 2) {
                        moveY = 0;
                        positionY = positionY - smooth;
                    }

                    main.revalidate();
                    main.repaint();

                    pauseThread();
                }
                break;

                //moon animations
            case 3: //TODO: Should eventually sort out layer/animationPane declaration and make it shorter...

                System.out.println("Playing menu moon animation.");

                layers.add(animationPane, new Integer(2), 0);
                animationPane.setLayout(null);
                animationPane.setBounds(0,0, main.getWidth(), main.getHeight());
                animationPane.setOpaque(false);
                animationPane.setVisible(true);

                displayedImage = new JLabel(image);
                animationPane.add(displayedImage);
                displayedImage.setVisible(true);
                displayedImage.setBounds(xVal, yVal, 300, 300);

                while (playing) {

                    if (currrot < 360) {
                        currrot = currrot + smooth;
                    } else { //reset the angle if we go over 360
                        currrot = 0;
                        currrot = currrot + smooth;
                    }

                    Point pt = getRotationAngle((float)currrot, radius, xVal, yVal);

                    //System.out.println((pt.x) + " - " + (pt.y));

                    displayedImage.setLocation(pt.x, pt.y);

                    main.revalidate();
                    main.repaint();

                    pauseThread();

                }




        }

    }

    private Point stationBob(int X, int Y, double toMove) {
        int y = Y + (int)Math.round(toMove / 2);
        return new Point(X, y);
    }

    private Point getRotationAngle(float degrees, float radius, int x, int y) { //x and y are the center of the circle, radius is the circle's radius, and degrees are the desired position in degrees

        double radians = Math.toRadians(degrees - 90);

        int xy = Math.round((float) (x + Math.cos(radians) * radius));
        int yy = Math.round((float) (y + Math.sin(radians) * radius));

        return new Point(xy, yy);
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

    public void stopAnimation() {
        playing = false;
    }

    public void setAnimationStartTime(int time) { //sets the start time of the moon animations
        this.currrot = time;
    }


}
