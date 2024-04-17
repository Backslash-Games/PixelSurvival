package Tiles.Gas;

import Function.Graphics.Color;
import Tiles.Tile;
import com.sun.source.tree.PackageTree;

public class Air extends Tile
{
    public int dirtDist = 0;

    public Air()
    {
        tileName = "Air";
        tileColor = new Color(42, 111, 161);
        hasGravity = false;
        stationary = true;
        isSolid = false;
        invulnerable = true;
        density = -100;
    }

    public void UpdateAtmosphere() {
        // -> Set air color
        if(dirtDist >= 0)
            tileColor = new Color(105 - dirtDist, 151 - dirtDist, 201- dirtDist);
        else
            tileColor = new Color(0, 0, 0);

        updated = true;
    }

    @Override
    public String toString() {
        return  "\nFunction.Graphics.Color: " + tileColor.toString() +
                "\nPosition: " + tilePoint.toString() +
                "\nSolid: " + isSolid +
                "\nGravity: " + hasGravity +
                "\nTiles.Solid.Dirt Distance: " + dirtDist;
    }
}