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
        // -> Attempt to pull game manager
        if(Program.gm == null)
            return;

        // -> Hold reference to tiles
        Tile[][] cTiles = Program.gm.tiles;

        // -> Only attempt to grow out if on dirt
        if(!Program.gm.PointInBounds(new Point(tilePoint.X, tilePoint.Y + 1)))
            return;
        if(cTiles[tilePoint.X][tilePoint.Y + 1].tileName != "Dirt")
            return;

        // -> Check adjacent for dirt
        // --> Check for build up

        if(!expanded) {
            if (Program.gm.PointInBounds(new Point(tilePoint.X - 1, tilePoint.Y - 1))) {
                if (cTiles[tilePoint.X - 1][tilePoint.Y - 1].tileName == "Dirt") {
                    Program.gm.PlaceTile(tilePoint.X, tilePoint.Y - 1, new Grass());
                }
            }
            if (Program.gm.PointInBounds(new Point(tilePoint.X + 1, tilePoint.Y - 1))) {
                if (cTiles[tilePoint.X + 1][tilePoint.Y - 1].tileName == "Dirt") {
                    Program.gm.PlaceTile(tilePoint.X, tilePoint.Y - 1, new Grass());
                }
            }
            // --> Check for build up out
            if (Program.gm.PointInBounds(new Point(tilePoint.X - 1, tilePoint.Y - 1))) {
                if (cTiles[tilePoint.X - 1][tilePoint.Y].tileName == "Dirt" && cTiles[tilePoint.X - 1][tilePoint.Y - 1].tileName == "Tiles.Gas.Air") {
                    Program.gm.PlaceTile(tilePoint.X - 1, tilePoint.Y - 1, new Grass());
                }
            }
            if (Program.gm.PointInBounds(new Point(tilePoint.X + 1, tilePoint.Y - 1))) {
                if (cTiles[tilePoint.X + 1][tilePoint.Y].tileName == "Dirt" && cTiles[tilePoint.X + 1][tilePoint.Y - 1].tileName == "Tiles.Gas.Air") {
                    Program.gm.PlaceTile(tilePoint.X + 1, tilePoint.Y - 1, new Grass());
                }
            }
            // --> Check for build out
            if (Program.gm.PointInBounds(new Point(tilePoint.X - 1, tilePoint.Y + 1))) {
                if (cTiles[tilePoint.X - 1][tilePoint.Y + 1].tileName == "Dirt" && cTiles[tilePoint.X - 1][tilePoint.Y].tileName == "Tiles.Gas.Air") {
                    Program.gm.PlaceTile(tilePoint.X - 1, tilePoint.Y, new Grass());
                }
            }
            if (Program.gm.PointInBounds(new Point(tilePoint.X + 1, tilePoint.Y + 1))) {
                if (cTiles[tilePoint.X + 1][tilePoint.Y + 1].tileName == "Dirt" && cTiles[tilePoint.X + 1][tilePoint.Y].tileName == "Tiles.Gas.Air") {
                    Program.gm.PlaceTile(tilePoint.X + 1, tilePoint.Y, new Grass());
                }
            }
            // --> Check for build down out
            if (Program.gm.PointInBounds(new Point(tilePoint.X - 1, tilePoint.Y + 2))) {
                if (cTiles[tilePoint.X - 1][tilePoint.Y + 2].tileName == "Dirt" && cTiles[tilePoint.X - 1][tilePoint.Y + 1].tileName == "Tiles.Gas.Air") {
                    Program.gm.PlaceTile(tilePoint.X - 1, tilePoint.Y + 1, new Grass());
                }
            }
            if (Program.gm.PointInBounds(new Point(tilePoint.X + 1, tilePoint.Y + 2))) {
                if (cTiles[tilePoint.X + 1][tilePoint.Y + 2].tileName == "Dirt" && cTiles[tilePoint.X + 1][tilePoint.Y + 1].tileName == "Tiles.Gas.Air") {
                    Program.gm.PlaceTile(tilePoint.X + 1, tilePoint.Y + 1, new Grass());
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
                if(Program.gm.PointInBounds(currentPoint)){
                    if(!Program.gm.GetTile(currentPoint).isSolid){
                        Program.gm.PlaceTile(tilePoint.X, tilePoint.Y - i, grassExp);
                    }
                }
            }
            for(int i = 0; i < downValue; i++){
                Plant grassExp = new Grass();
                grassExp.canGrow = false;
                Point currentPoint = new Point(tilePoint.X, tilePoint.Y + i);
                if(Program.gm.PointInBounds(currentPoint)){
                    if(Program.gm.GetTile(currentPoint).tileName == "Dirt"){
                        Program.gm.PlaceTile(tilePoint.X, tilePoint.Y + i, grassExp);
                    }
                }
            }
            canGrow = false;
        }
    }
}
