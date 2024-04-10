package Tiles.Gas;

import Function.Graphics.Color;
import Function.Math.Random;
import Tiles.Tile;

public class Steam extends Tile {
    public Steam(){
        // Attributes
        tileName = "Steam";
        tileColor = new Color(181, 181, 181);
        health = 10;

        // Properties
        hasGravity = true;
        isSolid = false;
        canFlow = true;
        canFloat = true;
        density = 0;
    }
}
