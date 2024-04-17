package Tiles.Solid;

import Function.Graphics.Color;
import Tiles.Tile;

public class Mud extends Tile {
    public Mud() {
        tileName = "Mud";
        tileColor = new Color(97, 64, 18, 255, 5);
        health = 1500;
        density = 5;

        hasGravity = true;
        isSolid = true;
    }
}
