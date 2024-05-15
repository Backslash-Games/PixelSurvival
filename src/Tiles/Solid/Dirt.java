package Tiles.Solid;

import Function.Graphics.Color;
import Function.Math.Point;
import Tiles.Liquid.Water;
import System.*;

public class Dirt extends Soil {

    public Dirt() {
        tileName = "Dirt";
        tileColor = new Color(117, 84, 38, 255, 5);
        health = 1500;
        density = 5;

        hasGravity = true;
        isSolid = true;
    }

    @Override
    public void OnUpdate() {
        super.OnUpdate();

        if(CheckAdj("Water"))
            storedChunk.PlaceTile(tilePoint, new Mud());
    }
}
