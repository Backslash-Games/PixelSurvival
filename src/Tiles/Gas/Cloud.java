package Tiles.Gas;

import Function.Graphics.Color;
import Tiles.Tile;
import System.Program;

public class Cloud extends Tile {
    public Cloud(){
        tileName = "Cloud";
        tileColor = new Color(255, 255, 255);
        health = 1000;

        deteriorates = true;
        hasGravity = true;
        isSolid = false;
        canFlow = true;
        canFloat = true;
        density = -1;
        flammable = true;
    }


    @Override
    public void OnUpdate() {
        super.OnUpdate();

        if(CompareSurrounding(tileName))
            storedChunk.PlaceTile(tilePoint, new RainCloud());
    }
}
