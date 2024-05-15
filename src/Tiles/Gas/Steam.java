package Tiles.Gas;

import Function.Graphics.Color;
import Function.Math.Random;
import Tiles.Tile;
import System.Program;

public class Steam extends Tile {
    public Steam(){
        // Attributes
        tileName = "Steam";
        tileColor = new Color(181, 181, 181);
        health = 100;

        // Properties
        deteriorates = true;
        hasGravity = true;
        isSolid = false;
        canFlow = true;
        canFloat = true;
        density = -2;
    }

    @Override
    public void OnUpdate() {
        super.OnUpdate();

        if(CompareSurrounding(tileName))
            storedChunk.PlaceTile(tilePoint, new Cloud());
    }
}
