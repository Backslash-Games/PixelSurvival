package Tiles.Gas;

import Function.Graphics.Color;
import Function.Math.Point;
import Function.Math.Random;
import Tiles.Effect.Fire;
import Tiles.Liquid.Water;
import Tiles.Tile;
import System.GameManager;

public class RainCloud extends Tile {
    public RainCloud(){
        tileName = "RainCloud";
        tileColor = new Color(170, 170, 170);
        health = 50000;

        deteriorates = true;
        hasGravity = true;
        isSolid = false;
        canFlow = true;
        canFloat = true;
        density = 0;
        flammable = true;
    }

    @Override
    public void OnUpdate() {
        super.OnUpdate();

        // -> Set tile below to water
        Tile aTile = storedChunk.GetTile(Point.add(tilePoint, Point.down));
        if(Random.Range(0, 10) == 0 && aTile.tileName == "Air"){
            storedChunk.PlaceTile(Point.add(tilePoint, Point.down), new Water());
        }
    }
}
