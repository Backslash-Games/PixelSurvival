package Tiles.Solid;

import java.util.Random;
import System.Program;
import Function.Math.Point;
import Tiles.Tile;

public class Soil extends Tile {
    @Override
    public void OnGravity() {
        super.OnGravity();
        if(true)
            return;

        // -> Check if there is a tile below
        Tile[][] tiles = storedChunk.tiles;

        // -> Make sure oTile below is stationary
        Tile oTile = null;
        // --> Pull correct oTile


        if(!tiles[tilePoint.X][tilePoint.Y + 1].stationary)
            return;

        int movementCheck = 0;
        Random rand = new Random();
        boolean moveLeft = rand.nextBoolean();

        while(movementCheck < 2) {
            // Move left
            if (moveLeft) {
                oTile = storedChunk.GetTile(new Point(tilePoint.X - 1, tilePoint.Y));
            }
            else{
                oTile = storedChunk.GetTile(new Point(tilePoint.X + 1, tilePoint.Y));
            }

            if(oTile == null)
                return;

            if(!oTile.isSolid && !oTile.tileName.equals(tileName)) {
                storedChunk.SwapTiles(this, oTile);
                updated = true;
                stationary = false;
                moved = true;
                return;
            }
            movementCheck++;
        }
        stationary = true;
    }
}
