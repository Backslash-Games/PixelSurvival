package Tiles.Liquid;

import Function.Math.Random;
import Tiles.Tile;
import Function.Graphics.Color;

public class Acid extends Tile {

    public Acid()
    {
        tileName = "Acid";
        tileColor = new Color(0, 255, 0);
        health = Random.Range(40, 80);

        isSolid = false;
        hasGravity = true;
        canFlow = true;
        acidity = 1000;
    }
}
