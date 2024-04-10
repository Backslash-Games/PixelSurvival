import java.util.Random;

public class GameManager
{
    public Tile[][] tiles;
    public int updateTimes = 0;

    public GameManager()
    {
        // -> On start it will initialize the game
        tiles = new Tile[Program.screenWidth / Program.pixelSize][Program.screenHeight / Program.pixelSize];

        FillTiles();
        BuildWorld();
    }

    public void PlaceTile(Point position, Tile tile){
        PlaceTile(position.X, position.Y, tile);
    }
    public void PlaceTile(int x, int y, Tile tile)
    {
        // -> Make sure x & y are in bounds
        if(x < 0 || y < 0) return;
        if(x >= tiles.length || y >= tiles[0].length) return;

        // -> Place tile
        tiles[x][y] = tile;
        tiles[x][y].SetPosition(new Point(x, y));
        tiles[x][y].updated = true;
        tiles[x][y].updatedThisFrame = true;
    }
    public void SwapTiles(Point begin, Point end)
    {
        // -> Make sure begin and end are in bounds
        if(begin.X < 0 ||  end.X < 0) return;
        if(begin.X >= tiles.length || end.Y >= tiles[0].length) return;

        // -> Swap tiles
        Tile holdTile = tiles[begin.X][begin.Y];
        PlaceTile(begin.X, begin.Y, tiles[end.X][end.Y]);
        PlaceTile(end.X, end.Y, holdTile);
    }

    public void UpdateTiles()
    {
        for (int x = 0; x < tiles.length; x++)
        {
            for (int y = 0; y < tiles[0].length; y++)
            {
                if(tiles[x][y].updatedThisFrame){
                    tiles[x][y].updatedThisFrame = false;
                    continue;
                }
                tiles[x][y].OnUpdate();
            }
        }
    }


    public Point ScreenToWorld(Point position){
        return new Point(Math.max(0, Math.min(Program.screenWidth - 1, position.X / Program.pixelSize)),
                Math.max(0, Math.min(Program.screenHeight - 1, position.Y / Program.pixelSize)));
    }
    public Tile GetTile(Point position){
        // Make sure point is in position
        if(!PointInBounds(position))
            return null;
        return tiles[position.X][position.Y];
    }
    public boolean PointInBounds(Point position){
        // -> Make sure x & y are in bounds
        if(position.X < 0 || position.Y < 0) return false;
        if(position.X >= tiles.length || position.Y >= tiles[0].length) return false;

        return true;
    }

    public Tile TileFromName(String name){
        switch(name.toLowerCase()){
            case "sand":
                return new Sand();
            case "bedrock":
                return new Bedrock();
            case "water":
                return new Water();
            case "dirt":
                return new Dirt();
            case "air":
                return new Air();
            case "grass":
                return new Grass();
            case "sapling":
                return new Sapling();
            case "wood":
                return new Wood();
            default:
                return new Tile();
        }
    }


    void FillTiles()
    {
        for(int x = 0; x < tiles.length; x++)
        {
            for(int y = 0; y < tiles[0].length; y++)
            {
                tiles[x][y] = new Air();
                tiles[x][y].updated = true;
            }
        }
    }

    void BuildDebugRoom()
    {
        for(int x = 0; x < tiles.length;  x++)
        {
            for(int y = tiles[0].length - 1; y >= tiles[0].length - 10; y--)
            {
                PlaceTile(x, y, new Bedrock());
            }
        }
    }
    void BuildWorld()
    {
        Random rand = new Random();
        int rng = rand.nextInt(5);

        // Set air
        for(int x = 0; x < tiles.length;  x++) {
            for(int y = 0; y < tiles[0].length;  y++) {
                PlaceTile(x, y, new Air());
            }
        }



        // Build dirt
        for(int x = 0; x < tiles.length;  x++)
        {
            int inc = rand.nextInt(3) - 1;
            inc = Math.min(1, inc);

            rng = Math.max(-5, rng + inc);
            for(int y = tiles[0].length - 1; y >= tiles[0].length - 10 - rng; y--)
            {
                PlaceTile(x, y, new Dirt());

                if(y == tiles[0].length - 10 - rng){
                    PlaceTile(x, y - 1, new Grass());
                }
            }
        }
    }
}
