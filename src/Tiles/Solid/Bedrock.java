package Tiles.Solid;

import Function.Graphics.Color;
import Function.Math.Random;
import Tiles.Tile;

public class Bedrock extends Tile
{
        public Bedrock()
        {
                tileName = "Bedrock";
                tileColor = new Color(23, 23, 23, 255, 5);
                health = 15000;
                density = 150;

                hasGravity = false;
                stationary = true;
        }
}

