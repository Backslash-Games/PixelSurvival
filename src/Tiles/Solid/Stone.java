package Tiles.Solid;

import Function.Graphics.Color;

public class Stone extends Soil {

    public Stone() {
        tileName = "Stone";
        tileColor = new Color(41, 41, 41, 255, 7);
        health = 1500;
        density = 50;

        hasGravity = true;
        isSolid = true;
    }
}
