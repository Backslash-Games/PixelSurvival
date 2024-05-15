package Tiles.Object;

import Function.Graphics.Color;
import Function.Math.Point;
import Function.Math.Random;
import Tiles.Tile;
import Tiles.Effect.Fire;
import System.GameManager;

public class Torch extends Tile {
    public Torch(){
        tileName = "Torch";
        tileColor = new Color(252, 186, 3);
        health = 350;

        isSolid = true;
        hasGravity = false;
    }

    @Override
    public void OnUpdate() {
        super.OnUpdate();

        // -> Set tile above to fire
        Tile aTile = storedChunk.GetTile(Point.add(tilePoint, Point.up));
        if(Random.Range(0, 4) == 0 && aTile.tileName == "Air"){
            storedChunk.PlaceTile(Point.add(tilePoint, Point.up), new Fire());
        }
    }
}
