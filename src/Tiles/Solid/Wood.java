package Tiles.Solid;

import Tiles.Effect.Ember;
import Tiles.Tile;
import Function.Graphics.Color;
import System.Program;

public class Wood extends Tile {
    public Wood(){
        tileName = "Wood";
        tileColor = new Color(74, 43, 15);

        hasGravity = false;
        stationary = true;

        health = 3;
        flammable = true;
        flammableChance = 36;
    }
}
