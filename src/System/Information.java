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
        screenSize = new Point((int)Toolkit.getDefaultToolkit().getScreenSize().getHeight(), (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth());

        // Debug out system information
        Console.out(consoleTag, Console.YELLOW, "Screen Size: " + screenSize);
    }
}
