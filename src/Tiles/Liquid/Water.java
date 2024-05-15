package Tiles.Liquid;

import Function.Math.Point;
import Tiles.Gas.Air;
import Tiles.Gas.Steam;
import Tiles.Solid.Mud;
import Tiles.Solid.Obsidian;
import Tiles.Tile;
import Function.Graphics.Color;
import System.*;

public class Water extends Tile {

    public Water()
    {
        tileName = "Water";
        tileColor = new Color(0, 0, 255);
        health = 500;
        acidity = 0;

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

        Tile[] tTiles = storedChunk.PullTouchingTiles(tilePoint);
        for(int i = 0; i < tTiles.length; i++) {
            if(tTiles[i] == null)
                continue;

            if(tTiles[i].tileName.equals("Grass")){
                tTiles[i].OnHurt(1, false);
            }
        }
    }

    public void CheckObsidian(){
        Tile[] adjTiles = storedChunk.PullTouchingTiles(tilePoint);
        int tilesHurt = 0;
        for(int i = 3; i < adjTiles.length; i++){
            // Check if tile is null
            if(adjTiles[i] == null || adjTiles[i].tileName != "Lava")
                continue;
            Point adjPoint = adjTiles[i].tilePoint;
            storedChunk.PlaceTile(adjPoint.X, adjPoint.Y, new Obsidian());
            tilesHurt++;
        }
        if(tilesHurt > 0){
            storedChunk.PlaceTile(tilePoint.X, tilePoint.Y, new Steam());
        }
    }
}
