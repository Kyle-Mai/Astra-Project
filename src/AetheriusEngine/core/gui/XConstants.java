package AetheriusEngine.core.gui;

import java.awt.*;

public interface XConstants {

    Color NULL = new Color(200, 80, 255, 255);
    Color BLACK = new Color(0, 0, 0, 255);
    Color GREY = new Color(100, 100, 100, 255);
    Color WHITE = new Color(255, 255, 255, 255);
    Color RED = new Color(130, 30, 30, 255);
    Color BLUE = new Color(100, 150, 255, 255);
    Color GREEN = new Color(100, 200, 120, 255);
    Color YELLOW = new Color(225, 225, 80, 255);
    Color NONE = new Color(0,0,0,0);

    Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 12);

    int HORIZONTAL_SORT = 1;
    int VERTICAL_SORT = 2;
    int VERTICAL_SORT_REVERSE = 3;
    int HORIZONTAL_SORT_REVERSE = 4;

    int SCALE_FULL = 5;
    int SCALE_TRIPLE = 6;

}
