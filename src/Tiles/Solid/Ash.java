package Tiles.Solid;

import Function.Graphics.Color;

public class Ash extends Soil {
    public Ash() {
        tileName = "Ash";
        tileColor = new Color(51, 51, 51);
        health = 10;

        hasGravity = true;
        isSolid = true;
    }
}
