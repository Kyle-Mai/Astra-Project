package AetheriusEngine.core.gui;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;

/**
 * Lolita's Revenge
 * August 03 2017
 * Adds bloom to a buffered image.
 */

public class XBloom {

    //TODO: Everything.

    private int bloomAmount = 1;
    private int bloomSmooth = 1;
    private BufferedImage image;

    public XBloom(BufferedImage b, int amount, int smooth) {
        image = b;
    }

    public BufferedImage getImage() { return image; }


    private void applyBloom() {

        //duplicates the original image and adds extra pixels for the bloom
        BufferedImage temp = new BufferedImage(image.getWidth() + (bloomAmount * 2), image.getHeight() + (bloomAmount * 2), BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                //adds the original image back onto the new image
                temp.setRGB(i + bloomAmount, j + bloomAmount, image.getRGB(i, j));
            }
        }


    }


}
