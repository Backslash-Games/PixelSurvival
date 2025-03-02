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
            // -> Move left
            if (tilePoint.X - 1 >= 0 && !tiles[tilePoint.X - 1][tilePoint.Y + 1].isSolid &&
                    (!tiles[tilePoint.X - 1][tilePoint.Y].isSolid || tiles[tilePoint.X][tilePoint.Y + 1].tileName == "Tiles.Solid.Sand")
                    && moveLeft) {
                //storedChunk.SwapTiles(tilePoint, new Point(tilePoint.X - 1, tilePoint.Y + 1));
                updated = true;
                stationary = false;
                return;
            }
            // -> Move Right
            if (tilePoint.X + 1 < tiles.length && !tiles[tilePoint.X + 1][tilePoint.Y + 1].isSolid &&
                    (!tiles[tilePoint.X + 1][tilePoint.Y].isSolid || tiles[tilePoint.X][tilePoint.Y + 1].tileName == "Tiles.Solid.Sand")
                    && !moveLeft) {
                //storedChunk.SwapTiles(tilePoint, new Point(tilePoint.X + 1, tilePoint.Y + 1));
                updated = true;
                stationary = false;
                return;
            }
            moveLeft = !moveLeft;
            movementCheck++;
        }
        stationary = true;
    }
}
