package Tiles.Solid;

import Function.Graphics.Color;
import Tiles.Tile;

public class Obsidian extends Tile {

    public Obsidian()
    {
        tileName = "Obsidian";
        tileColor = new Color(15, 0, 36);
        health = 50000;
        density = 3;

        hasGravity = true;
        stationary = false;
    }
}
