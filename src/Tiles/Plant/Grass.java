package Tiles.Plant;

import java.util.Random;
import Function.Graphics.Color;
import Function.Math.Point;
import System.Program;
import Tiles.Tile;

public class Grass extends Plant {

    public boolean expanded = false;

    public int upValue = 0;
    public int downValue = 0;

    public Grass()
    {
        tileName = "Grass";
        tileColor = new Color(59, 181, 53, 255, 30);

        Random rand = new Random();
        upValue = rand.nextInt(5);
        downValue = rand.nextInt(3) + 3;

        health = 100;
        flammable = true;
        flammableChance = 10;
        hasGravity = true;
    }

    @Override
    public void OnGrow() {
        // -> Hold reference to tiles
        Tile[][] cTiles = storedChunk.tiles;

        // -> Only attempt to grow out if on dirt
        if(!storedChunk.PointInBounds(new Point(tilePoint.X, tilePoint.Y + 1)))
            return;
        if(cTiles[tilePoint.X][tilePoint.Y + 1].tileName != "Dirt")
            return;

        // -> Check adjacent for dirt
        // --> Check for build up

        if(!expanded) {
            if (storedChunk.PointInBounds(new Point(tilePoint.X - 1, tilePoint.Y - 1))) {
                if (cTiles[tilePoint.X - 1][tilePoint.Y - 1].tileName == "Dirt") {
                    storedChunk.PlaceTile(tilePoint.X, tilePoint.Y - 1, new Grass());
                }
            }
            if (storedChunk.PointInBounds(new Point(tilePoint.X + 1, tilePoint.Y - 1))) {
                if (cTiles[tilePoint.X + 1][tilePoint.Y - 1].tileName == "Dirt") {
                    storedChunk.PlaceTile(tilePoint.X, tilePoint.Y - 1, new Grass());
                }
            }
            // --> Check for build up out
            if (storedChunk.PointInBounds(new Point(tilePoint.X - 1, tilePoint.Y - 1))) {
                if (cTiles[tilePoint.X - 1][tilePoint.Y].tileName == "Dirt" && cTiles[tilePoint.X - 1][tilePoint.Y - 1].tileName == "Tiles.Gas.Air") {
                    storedChunk.PlaceTile(tilePoint.X - 1, tilePoint.Y - 1, new Grass());
                }
            }
            if (storedChunk.PointInBounds(new Point(tilePoint.X + 1, tilePoint.Y - 1))) {
                if (cTiles[tilePoint.X + 1][tilePoint.Y].tileName == "Dirt" && cTiles[tilePoint.X + 1][tilePoint.Y - 1].tileName == "Tiles.Gas.Air") {
                    storedChunk.PlaceTile(tilePoint.X + 1, tilePoint.Y - 1, new Grass());
                }
            }
            // --> Check for build out
            if (storedChunk.PointInBounds(new Point(tilePoint.X - 1, tilePoint.Y + 1))) {
                if (cTiles[tilePoint.X - 1][tilePoint.Y + 1].tileName == "Dirt" && cTiles[tilePoint.X - 1][tilePoint.Y].tileName == "Tiles.Gas.Air") {
                    storedChunk.PlaceTile(tilePoint.X - 1, tilePoint.Y, new Grass());
                }
            }
            if (storedChunk.PointInBounds(new Point(tilePoint.X + 1, tilePoint.Y + 1))) {
                if (cTiles[tilePoint.X + 1][tilePoint.Y + 1].tileName == "Dirt" && cTiles[tilePoint.X + 1][tilePoint.Y].tileName == "Tiles.Gas.Air") {
                    storedChunk.PlaceTile(tilePoint.X + 1, tilePoint.Y, new Grass());
                }
            }
            // --> Check for build down out
            if (storedChunk.PointInBounds(new Point(tilePoint.X - 1, tilePoint.Y + 2))) {
                if (cTiles[tilePoint.X - 1][tilePoint.Y + 2].tileName == "Dirt" && cTiles[tilePoint.X - 1][tilePoint.Y + 1].tileName == "Tiles.Gas.Air") {
                    storedChunk.PlaceTile(tilePoint.X - 1, tilePoint.Y + 1, new Grass());
                }
            }
            if (storedChunk.PointInBounds(new Point(tilePoint.X + 1, tilePoint.Y + 2))) {
                if (cTiles[tilePoint.X + 1][tilePoint.Y + 2].tileName == "Dirt" && cTiles[tilePoint.X + 1][tilePoint.Y + 1].tileName == "Tiles.Gas.Air") {
                    storedChunk.PlaceTile(tilePoint.X + 1, tilePoint.Y + 1, new Grass());
                }
            }
            expanded = true;
        }
        // -> Grow up and down
        else
        {
            for(int i = 0; i < upValue; i++){
                Plant grassExp = new Grass();
                grassExp.canGrow = false;
                Point currentPoint = new Point(tilePoint.X, tilePoint.Y - i);
                if(storedChunk.PointInBounds(currentPoint)){
                    if(!storedChunk.GetTile(currentPoint).isSolid){
                        storedChunk.PlaceTile(tilePoint.X, tilePoint.Y - i, grassExp);
                    }
                }
            }
            for(int i = 0; i < downValue; i++){
                Plant grassExp = new Grass();
                grassExp.canGrow = false;
                Point currentPoint = new Point(tilePoint.X, tilePoint.Y + i);
                if(storedChunk.PointInBounds(currentPoint)){
                    if(storedChunk.GetTile(currentPoint).tileName == "Dirt"){
                        storedChunk.PlaceTile(tilePoint.X, tilePoint.Y + i, grassExp);
                    }
                }
            }
            canGrow = false;
        }
    }
}
