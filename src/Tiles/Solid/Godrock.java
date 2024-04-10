package Tiles.Solid;

import Function.Graphics.Color;
import Function.Math.Random;
import Tiles.Tile;

public class Godrock extends Tile
{
    public Godrock()
    {
        tileName = "Godrock";
        tileColor = new Color(255, 247, 209);

        hasGravity = false;
        stationary = true;

        invulnerable = true;
    }
}

