package Function.Graphics;

import Debug.Console;
import Function.Math.Point;

public class Button {
    public Point origin;
    public Point size;

    String consoleTag = "BUTTON";
    public Button() { }
    public Button(int x, int y, int width, int height)
    {
        origin = new Point(x, y);
        size = new Point(width, height);

        if(Console.writeButtonCreation)
            Console.out(consoleTag, Console.CYAN, "Creating button at " + origin + " with size " + size);
    }

    // Checks if the position on screen is inside the button
    public boolean PointInside(Point position)
    {
        return position.X > origin.X &&
                position.Y > origin.Y &&
                position.X <= origin.X + size.X &&
                position.Y <= origin.Y + size.Y;
    }

    public void OnClicked() { }
}
