package Core.GUI;

import java.awt.*;

/**
 KM
 May 26 2017
 Enum storage for screen scale resolution options.

 SOURCES:
 Mkyong - Enum syntax and examples. https://www.mkyong.com/java/java-enum-example/
 */

public enum screenScale {
 
    //Widescreen 16:9 monitor resolutions
    W_4KHD (3840, 2160, true),
    W_2KHD (2560, 1440, true),
    W_1KHD (1920, 1080, true),
    W_HD (1600, 900, true),
    W_WXGA (1280, 720, true),
    //Standard 4:3 monitor resolutions
    S_XGA (1024, 768, true),
    S_XGAP (1152, 864, true),
    S_UXGA (1600, 1200, true),
    //Weird monitor resolutions
    U_SXGA (1280, 1024, true),
    U_WXGAP (1440, 900, true),
    U_WUXGA (1920, 1200, true),
    U_WSXGAP (1680, 1050, true),
    //Screen scale of the launcher screen.
    LAUNCHER (700, 450, false);

    public static Dimension monitorSize = Toolkit.getDefaultToolkit().getScreenSize(); //gets the screen size of the user
    public static Dimension screenSize = new Dimension(W_1KHD.size());
    private int xScale, yScale; //scale of the screen in pixels
    private boolean selectable;

    screenScale(int x, int y, boolean selectable) {
       this.xScale = x;
       this.yScale = y;
       this.selectable = selectable;
    }

    public Dimension size() { return new Dimension(xScale, yScale); } //gets the screen size's dimensions
    public String getOptionName() { return xScale + "x" + yScale; }
    public boolean isSelectable() { return this.selectable; } //whether or not the screen size can be selected

    public static void setScreenSize(Dimension newSize) {
        if (newSize.getWidth() > monitorSize.getWidth() || newSize.getHeight() > monitorSize.getHeight()) {
            System.err.println("Unexpected Error - Monitor is not large enough to support this resolution.");
        } else {
            //no incompatibilities, proceed
            screenSize = newSize;
            System.out.println("UI option successfully set to " + (int)screenSize.getWidth() + "x" + (int)screenSize.getHeight() + ".");
        }
    }

}
