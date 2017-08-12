package AetheriusEngine.core.gui;

/*
Lolita's Revenge
July 09 2017

Takes a BufferedImage and converts it to a grey-scaled instance of itself.
*/

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class XGreyscale {

    /*------------------------------------------------------------------------------------------------------------------
     Variables.
     Defines the function of the class
     */

    private File greyscaleOutput;
    private BufferedImage source;
    private BufferedImage greyscaledImage;
    private boolean imageConverted = false; //Whether or not the source image has been converted via convertImage()

    /*------------------------------------------------------------------------------------------------------------------
     Constructors.
     Used to construct instances of the class
     */

    public XGreyscale() {}
    public XGreyscale(BufferedImage s) { source = s; }

    /*------------------------------------------------------------------------------------------------------------------
     Accessible methods.
     Methods that can be accessed from outside of the class
     */

    public BufferedImage getGreyscaledImage() { return greyscaledImage; }
    public BufferedImage getSource() { return source; }
    public File getGreyscaleOutput() { return greyscaleOutput; }
    public void setSource(BufferedImage b) { source = b; }
    public boolean isImageConverted() { return imageConverted; }

    public void convertImage() {
        if (source == null) throw new NullPointerException();

        //copies the source image, rather than utilizing the source itself rendering it unusable
        ColorModel model = source.getColorModel();
        WritableRaster raster = source.copyData(null);
        greyscaledImage = new BufferedImage(model, raster, model.isAlphaPremultiplied(), null);

        for (int i = 0; i < greyscaledImage.getWidth(); i++) {
            for (int j = 0; j < greyscaledImage.getHeight(); j++) {
                int p = greyscaledImage.getRGB(i,j);
                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;
                int avg = (r+g+b)/3;
                p = (a<<24) | (avg<<16) | (avg<<8) | avg;
                greyscaledImage.setRGB(i, j, p);
            }
        }
        imageConverted = true;
    }

    public void exportToFile(File folder, String name) { //Exports the greyscaled image to a specified folder
        try {
            if (!folder.exists()) { folder.mkdir(); }
            greyscaleOutput = new File(folder + "/" + name + ".jpg");
            if (greyscaleOutput.exists()) { greyscaleOutput.delete(); }
            ImageIO.write(greyscaledImage, "jpg", greyscaleOutput);
            System.out.println("Greyscaled image successfully exported.");
        } catch (IOException e) {
            System.out.println("Export failed.");
            e.printStackTrace();
        }
    }

    //------------------------------------------------------------------------------------------------------------------

}
