public class Water extends Tile{

    public Water()
    {
        tileName = "Water";
        tileColor = new Color(0, 0, 255);
        isSolid = false;
        hasGravity = true;
        canFlow = true;
    }
}
