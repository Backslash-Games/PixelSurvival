import com.sun.source.tree.PackageTree;

class Air extends Tile
{
    public int dirtDist = 0;

    public Air()
    {
        tileName = "Air";
        tileColor = new Color(42, 111, 161);
        hasGravity = false;
        stationary = true;
        isSolid = false;
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
        return  "\nColor: " + tileColor.toString() +
                "\nPosition: " + tilePoint.toString() +
                "\nSolid: " + isSolid +
                "\nGravity: " + hasGravity +
                "\nDirt Distance: " + dirtDist;
    }
}