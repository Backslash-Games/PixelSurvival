package System;

import Debug.Console;
import Function.Math.Point;

import java.awt.*;

public class Information {

    static String consoleTag = "INFO";

    public static Point screenSize;

    // -> On creation, pull system information
    public Information(){
        // Store system information
        screenSize = new Point((int)Math.round(Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 10f) * 10,
                (int)Math.round(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 10f) * 10);

        // Debug out system information
        Console.out(consoleTag, Console.YELLOW, "Screen Size: " + screenSize);
    }
}
