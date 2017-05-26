package Core.GUI;

/**
 KM
 May 26 2017
 Enum storage for screen scales.

 SOURCES:
 Mkyong - Enum syntax and examples. https://www.mkyong.com/java/java-enum-example/
 */

public enum screenScale {

    WIDESCREEN_1 (1920, 1080),
    WIDESCREEN_2 (2560, 1440),
    WIDESCREEN_3 (3840, 2160),
    WIDESCREEN_4 (1280, 720),
    STANDARD_1 (1280, 1024),
    STANDARD_2 (0, 0),
    STANDARD_3 (0, 0),
    STANDARD_4 (0, 0),
    DEFAULT (700, 450);

    private int xScale, yScale;

    screenScale(int x, int y) {

    }



}


