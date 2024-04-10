package Tiles.Liquid;

import Function.Math.Point;
import Tiles.Gas.Air;
import Tiles.Gas.Steam;
import Tiles.Solid.Obsidian;
import Tiles.Tile;
import Function.Graphics.Color;
import System.Program;

public class Water extends Tile {

    public Water()
    {
        tileName = "Water";
        tileColor = new Color(0, 0, 255);
        health = 500;
        acidity = 1;

        isSolid = false;
        hasGravity = true;
        canFlow = true;
    }

    @Override
    public void OnUpdate(){
        OnGravity();
        OnFlow();
        OnCheckAcid();

        CheckObsidian();
    }

    public void CheckObsidian(){
        Tile[] adjTiles = Program.gm.PullTouchingTiles(tilePoint);
        int tilesHurt = 0;
        for(int i = 3; i < adjTiles.length; i++){
            // Check if tile is null
            if(adjTiles[i] == null || adjTiles[i].tileName != "Lava")
                continue;
            Point adjPoint = adjTiles[i].tilePoint;
            Program.gm.PlaceTile(adjPoint.X, adjPoint.Y, new Obsidian());
            tilesHurt++;
        }
        if(tilesHurt > 0){
            Program.gm.PlaceTile(tilePoint.X, tilePoint.Y, new Steam());
        }
    }
}
