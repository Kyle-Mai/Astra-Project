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
    W_WXGA (1280, 720),
    W_HD (1600, 900),
    W_1KHD (1920, 1080),
    W_2KHD (2560, 1440),
    W_4KHD (3840, 2160),
    //Standard 4:3 monitor resolutions
    S_XGA (1024, 768),
    S_XGAP (1152, 864),
    S_UXGA (1600, 1200),
    //Weird monitor resolutions
    U_SXGA (1280, 1024),
    U_WXGAP (1440, 900),
    U_WUXGA (1920, 1200),
    U_WSXGAP (1680, 1050),
    //Screen scale of the launcher screen.
    LAUNCHER (700, 450);
 

    private int xScale, yScale; //scale of the screen in pixels

    screenScale(int x, int y) {
       this.xScale = x;
       this.yScale = y;
    }
 
    public Dimension size() { //gets the screen size's dimensions
       return new Dimension(xScale, yScale);
    }

 
}
