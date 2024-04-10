package Tiles.Effect;

import Function.Graphics.Color;
import Function.Math.Random;
import Tiles.Tile;

public class Fire extends Tile {
    public Fire(){
        // Attributes
        tileName = "Fire";
        tileColor = new Color(184, 54, 37);
        health = Random.Range(15, 35);

        // Properties
        deteriorates = true;
        hasGravity = true;
        isSolid = false;
        canFlow = true;
        canFloat = true;
        canBurn = true;
    }
}
