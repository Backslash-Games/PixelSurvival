package Tiles.Effect;

import Function.Graphics.Color;
import Function.Math.Random;
import Tiles.Gas.Air;
import Tiles.Solid.Ash;
import Tiles.Tile;
import System.Program;

public class Ember extends Tile {
    public Ember(){
        // Attributes
        tileName = "Ember";
        tileColor = new Color(138, 69, 21, 255, 15);

        // Properties
        health = Random.Range(75, 105);
        deteriorates = true;
        hasGravity = false;
        isSolid = true;
        canFlow = false;
        canFloat = false;
        canBurn = true;
    }

    @Override
    public void Destroy(){
        // -> Sets current position to air
        Program.gm.PlaceTile(tilePoint.X, tilePoint.Y, new Ash());
    }
}
