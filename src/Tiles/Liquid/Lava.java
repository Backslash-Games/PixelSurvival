package Tiles.Liquid;

import Tiles.Tile;
import Function.Graphics.Color;

public class Lava extends Tile {

    public Lava()
    {
        tileName = "Lava";
        tileColor = new Color(255, 123, 28);
        health = 2500;

        isSolid = false;
        hasGravity = true;
        canFlow = true;
        canBurn = true;
        acidity = 10;
        density = 10;
    }
}
