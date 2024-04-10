package Tiles.Gas;

import Function.Graphics.Color;
import System.Program;
import Tiles.Effect.Fire;
import Tiles.Tile;

public class Gas extends Tile {
    public Gas(){
        // Attributes
        tileName = "Gas";
        tileColor = new Color(254, 222, 255);
        health = 1;

        // Properties
        hasGravity = true;
        isSolid = false;
        canFlow = true;
        canFloat = true;
        density = 0;
        flammable = true;
    }

    @Override
    public void Destroy(){
        // -> Sets current position to air
        Program.gm.PlaceTile(tilePoint.X, tilePoint.Y, new Fire());
    }
}
