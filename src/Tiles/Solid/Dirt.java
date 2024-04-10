package Tiles.Solid;

import Function.Graphics.Color;

public class Dirt extends Soil {

    public Dirt() {
        tileName = "Dirt";
        tileColor = new Color(117, 84, 38, 255, 5);
        health = 150;
        density = 5;

        hasGravity = true;
        isSolid = true;
    }
}
