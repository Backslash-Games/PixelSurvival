import java.util.Random;

public class Soil extends Tile {
    @Override
    public void OnGravity() {
        // -> Make sure game manager is set
        if (Program.gm == null)
            return;

        // -> Check if there is a tile below
        Tile[][] tiles = Program.gm.tiles;
        // --> Make sure tile is in bounds
        if(tilePoint.Y + 1 < tiles[0].length)
        {
            // -> Move down
            if (!tiles[tilePoint.X][tilePoint.Y + 1].isSolid) {
                Program.gm.SwapTiles(tilePoint, new Point(tilePoint.X, tilePoint.Y + 1));
                updated = true;
                stationary = false;
                return;
            }

            // -> Make sure tile below is stationary
            if(!tiles[tilePoint.X][tilePoint.Y + 1].stationary)
                return;

            int movementCheck = 0;
            Random rand = new Random();
            boolean moveLeft = rand.nextBoolean();

            while(movementCheck < 2) {
                // -> Move left
                if (tilePoint.X - 1 >= 0 && !tiles[tilePoint.X - 1][tilePoint.Y + 1].isSolid &&
                        (!tiles[tilePoint.X - 1][tilePoint.Y].isSolid || tiles[tilePoint.X][tilePoint.Y + 1].tileName == "Sand")
                        && moveLeft) {
                    Program.gm.SwapTiles(tilePoint, new Point(tilePoint.X - 1, tilePoint.Y + 1));
                    updated = true;
                    stationary = false;
                    return;
                }
                // -> Move Right
                if (tilePoint.X + 1 < tiles.length && !tiles[tilePoint.X + 1][tilePoint.Y + 1].isSolid &&
                        (!tiles[tilePoint.X + 1][tilePoint.Y].isSolid || tiles[tilePoint.X][tilePoint.Y + 1].tileName == "Sand")
                        && !moveLeft) {
                    Program.gm.SwapTiles(tilePoint, new Point(tilePoint.X + 1, tilePoint.Y + 1));
                    updated = true;
                    stationary = false;
                    return;
                }
                moveLeft = !moveLeft;
                movementCheck++;
            }
        }
        stationary = true;
    }
}
