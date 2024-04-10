import java.util.Random;

public class Tile
{

    // -> Processing <-
    public boolean updated = false;
    public boolean updatedThisFrame = false;
    public boolean stationary = false;

    // -> Attributes <-
    public String tileName = "Powder";
    public Color tileColor = new Color(255, 225, 125);
    public Point tilePoint = new Point(0, 0);
    public String passThrough[] = new String[0];

    // -> Properties <-
    public boolean isSolid = true;
    public boolean hasGravity = true;
    public boolean canFlow = false;


    // -> Events
    public void OnUpdate()
    {
        if (hasGravity)
        {
            OnGravity();
        }
        if(canFlow){
            OnFlow();
        }
    }

    public void OnGravity()
    {
        // -> Make sure game manager is set
        if (Program.gm == null)
            return;

        // -> Check if there is a tile below
        Tile[][] tiles = Program.gm.tiles;
        // --> Make sure tile is in bounds
        if(tilePoint.Y + 1 < tiles[0].length)
        {
            // -> Move down
            if ((!tiles[tilePoint.X][tilePoint.Y + 1].isSolid || CheckPassThrough(tiles[tilePoint.X][tilePoint.Y + 1].tileName)) && tiles[tilePoint.X][tilePoint.Y + 1].tileName != tileName) {
                Program.gm.SwapTiles(tilePoint, new Point(tilePoint.X, tilePoint.Y + 1));
                updated = true;
                stationary = false;
                return;
            }
        }
        stationary = true;
    }

    public void OnFlow(){
        // -> Check adjacent
        // -> Make sure game manager is set
        if (Program.gm == null)
            return;

        // -> Check if there is a tile below
        Tile[][] tiles = Program.gm.tiles;
        Random rand = new Random();
        boolean moveLeft = rand.nextBoolean();

        int moveChecks = 0;
        while(moveChecks < 2) {
            // Move left
            if (tilePoint.X - 1 >= 0 && !tiles[tilePoint.X - 1][tilePoint.Y].isSolid &&
                    tiles[tilePoint.X - 1][tilePoint.Y].tileName != tileName && moveLeft) {
                Program.gm.SwapTiles(tilePoint, new Point(tilePoint.X - 1, tilePoint.Y));
                updated = true;
                stationary = false;
                return;
            }
            // Move right
            if (tilePoint.X + 1 < tiles.length && !tiles[tilePoint.X + 1][tilePoint.Y].isSolid &&
                    tiles[tilePoint.X + 1][tilePoint.Y].tileName != tileName && !moveLeft) {
                Program.gm.SwapTiles(tilePoint, new Point(tilePoint.X + 1, tilePoint.Y));
                updated = true;
                stationary = false;
                return;
            }
            stationary = true;
            moveChecks++;
        }
    }

    // -> Check pass through
    public boolean CheckPassThrough(String id){
        for(int i = 0; i < passThrough.length; i++)
            if(passThrough[i] == id)
                return true;
            return false;
    }

    // -> Set/Get Methods
    public void SetPosition(Point position)
    {
        tilePoint = position;
    }

    @Override
    public String toString() {
        return  "\nColor: " + tileColor.toString() +
                "\nPosition: " + tilePoint.toString() +
                "\nSolid: " + isSolid +
                "\nGravity: " + hasGravity +
                "\nFlowing: " + canFlow +
                "\nStationary: " + stationary;
    }
}
