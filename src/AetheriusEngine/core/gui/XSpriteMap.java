package AetheriusEngine.core.gui;

/*
Lolita's Revenge
June 30 2017

Builds a sprite map to be used on a SwingEX component.
*/

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;
import java.util.ArrayList;

public class XSpriteMap extends ArrayList<BufferedImage> implements XSpriteConstants {

    /*------------------------------------------------------------------------------------------------------------------
     Variables.
     Defines the function of the sprite map.
     */

    private int currentSprite = 0; //Index that will be called when getCurrentSprite() is called - can be used for animation maps
    private BufferedImage source; //Source image used to create the sprites
    private boolean dumpingSource = false;

    private String spriteName; //Identifiers used to identify the sprite map, unnecessary
    private int ID;

    /*------------------------------------------------------------------------------------------------------------------
     Constructors.
     Used to construct instances of the sprite map.
     */

    public XSpriteMap() {}
    public XSpriteMap(String name) { spriteName = name; }
    public XSpriteMap(int identifier) { ID = identifier; }
    public XSpriteMap(BufferedImage s) { source = s; }

    public XSpriteMap(String name, BufferedImage s) {
        spriteName = name;
        source = s;
    }

    public XSpriteMap(int identifier, BufferedImage s) {
        source = s;
        ID = identifier;
    }

    public XSpriteMap(String name, int identifier, BufferedImage s) {
        spriteName = name;
        source = s;
        ID = identifier;
    }

    /*------------------------------------------------------------------------------------------------------------------
     Accessible methods.
     Can be accessed to edit the sprite map's characteristics.
     */

    public BufferedImage getSource() { return source; }
    public String getSpriteName() { return spriteName; }
    public int getID() { return ID; }
    public int getCurrentIndex() { return currentSprite; } //gets the current index, used with getCurrentSprite and defined by 'currentSprite'
    public void clearSource() { source = null; } //just for when you want the source to be removed for whatever reason
    public boolean isDumpingSource() { return dumpingSource; }
    public void setDumpSource(boolean b) { dumpingSource = b; }

    public void setSource(BufferedImage s) { source = s; } //Sets the source image used to create sprites.
    public void setCurrentIndex(int i) { currentSprite = i; } //sets the current indexed sprite that will be called with getCurrentSprite()
    public void setID(int i) { ID = i; } //sets the sprite map's ID - purely used to identify the sprite map in arrays and whatnot
    public void setSpriteName(String s) { spriteName = s; } //sets the sprite map's name - purely used to identify the sprite map in arrays and whatnot
    public void indexNextSprite() {
        currentSprite++;
        if (currentSprite > size() - 1) {
            currentSprite = 0;
        }
    }

    public void resizeSprite(int i, int x, int y) { get(i).getScaledInstance(x, y, Image.SCALE_SMOOTH); } //resizes the sprite at the specified index

    public void createSprites(boolean custom, int i1, int i2) { //Builds sprites from the source image.
        if (source == null) throw new NullPointerException("Source image was not declared or is invalid.");
        create(source, custom, i1, i2);
        if (dumpingSource) { source = null; }
    }

    public void createSprites(BufferedImage img, boolean custom, int i1, int i2) { //Builds sprites from a chosen image
        if (img == null) throw new NullPointerException("Image was either unspecified or invalid.");
        if (!dumpingSource) { source = img; }
        create(img, custom, i1, i2);
    }

    public void createSprite(int x, int y, int w, int h) { add(source.getSubimage(x, y, w, h)); }
    public void createSprite(BufferedImage i, int x, int y, int w, int h) { add(i.getSubimage(x, y, w, h)); }

    public BufferedImage flip(BufferedImage s) { //flips a sprite image
        AffineTransform at = new AffineTransform();
        at.concatenate(AffineTransform.getScaleInstance(1, -1));
        at.concatenate(AffineTransform.getTranslateInstance(0, -s.getHeight()));
        return createRotated(createTransformed(s, at));
    }

    public void flipAll() { //Replaces all images in the SpriteMap with flipped versions
        ArrayList<BufferedImage> temp = new ArrayList<>();
        for (BufferedImage s : this) { temp.add(flip(s)); }
        clear();
        addAll(temp);
        temp.clear();
    }

    /*------------------------------------------------------------------------------------------------------------------
     Core methods.
     These should NOT be touched or accessed directly.
     */

    private BufferedImage createTransformed(BufferedImage image, AffineTransform at) {
        BufferedImage newImage = new BufferedImage(
                image.getWidth(), image.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.transform(at);
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }

    private BufferedImage createRotated(BufferedImage image) {
        AffineTransform at = AffineTransform.getRotateInstance(
                Math.PI, image.getWidth()/2, image.getHeight()/2.0);
        return createTransformed(image, at);
    }

    private void create(BufferedImage img, boolean custom, int i1, int i2) {
        int width, height; //Width and height of each sprite.
        int x = 0, y = 0; //The current position index.
        if (i1 < 1 || i2 < 1) throw new IllegalArgumentException("Invalid input in the integer parameters. Value must be greater than 0.");

        if (!custom) { //Preset sprite map, i1 defines the style of sprites (HORIZONTAL_MAP / VERTICAL_MAP), i2 defines the number of sprites on the map
            if (i1 == HORIZONTAL_MAP) { //Creates sprites from a left-right oriented sprite map.
                height = img.getHeight();
                width = img.getWidth() / i2;
                for (int i = 0; i < i2; i++) {
                    try {
                        add(img.getSubimage(x, y, width, height));
                    } catch (Exception e) { //If an error occurs while generating the sprite, replace it with a null.
                        System.err.println("Error when generating sprite image: " + e.getMessage());
                        add(null);
                    }
                    x += width;
                }
                System.out.println("Sprites created.");
            } else if (i1 == VERTICAL_MAP) { //Creates sprites from a top-down oriented sprite map.
                height = img.getHeight() / i2;
                width = img.getWidth();
                for (int i = 0; i < i2; i++) {
                    try {
                        add(img.getSubimage(x, y, width, height));
                    } catch (Exception e) { //If an error occurs while generating the sprite, replace it with a null.
                        System.err.println("Error when generating sprite image: " + e.getMessage());
                        add(null);
                    }
                    y += height;
                }
                System.out.println("Sprites created.");
            } else { //Unknown map.
                System.err.println("Unknown mapping style.");
            }

        } else { //Custom sprite map, such as one with multiple rows and columns. i1 defines the number of sprites in the x-coordinate, i2 defines the number of sprites in the y-coordinate.
            width = img.getWidth() / i1;
            height = img.getHeight() / i2;

            for (int i = 0; i < i2; i++) {
                x = 0;
                for (int j = 0; j < i1; j++) {
                    try {
                        add(img.getSubimage(x, y, width, height));
                    } catch (Exception e) { //If an error occurs while generating the sprite, replace it with a null.
                        System.err.println("Error when generating sprite image: " + e.getMessage());
                        add(null);
                    }
                    x += width;
                }
                y += height;
            }
            System.out.println("Sprites created.");
        }
    }

    //------------------------------------------------------------------------------------------------------------------

}
