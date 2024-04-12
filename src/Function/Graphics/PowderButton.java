package Function.Graphics;

import Debug.Console;
import Function.Math.Point;
import System.Program;

public class PowderButton extends Button {

    int id = 0;

    String consoleTag = "P.BUTTON";

    public PowderButton(int x, int y, int width, int height, int powderID)
    {
        super(x, y, width, height);

        id = powderID;

        if(Console.writeButtonCreation)
            Console.out(consoleTag, Console.PURPLE, "Logged with id " + id);
    }
    @Override
    public void OnClicked() {
        Program.instance.SetPenType(id);
    }
}
