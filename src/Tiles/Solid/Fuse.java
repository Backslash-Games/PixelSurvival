package Tiles.Solid;

import Function.Graphics.Color;
import System.Program;
import Tiles.Effect.Fire;
import Tiles.Tile;

public class Fuse extends Tile
{
    public Fuse()
    {
        tileName = "Fuse";
        tileColor = new Color(201, 155, 75);
        health = 1;

        hasGravity = false;
        stationary = true;

        flammable = true;
    }

    @Override
    public void Destroy(){
        // -> Sets current position to air
        storedChunk.PlaceTile(tilePoint.X, tilePoint.Y, new Fire());
    }
}

