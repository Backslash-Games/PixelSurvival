package Generation.Structure;

import Debug.Console;
import Function.Math.*;
import Generation.GenerationHandler;
import Generation.GenerationType;
import Tiles.*;
import System.GameManager;

// When created (by world) it will populate itself with tiles based on a passed through generation type
// -> Holds a reference to the world
public class Chunk {
    public static Point ChunkSize = new Point(64, 64);
    public GenerationType type = null;
    public Point location = Point.zero;
    public Tile[][] tiles;
    public Chunk[] adjacentChunks = new Chunk[8];
    public World storedWorld = null;
    public boolean chunkActive = false;

    // Flags
    boolean generated = false;
    public String consoleTag = "CHUNK";

    public Chunk(Point location, World storedWorld, GenerationType type){
        Console.out(consoleTag, Console.BLUE, "Generating Chunk at " + location + " with type " + type.name);

        this.location = location;
        this.type = type;
        this.tiles = new Tile[ChunkSize.X][ChunkSize.Y];
        this.storedWorld = storedWorld;
        type.Generate(this);

        SetAllAdjacents();

        generated = true;
    }
    void SetAllAdjacents(){
        SetAdjacent(Point.up, 0); // N
        SetAdjacent(new Point(1, -1), 1); // NE

        SetAdjacent(Point.right, 2); // E
        SetAdjacent(new Point(1, 1), 3); // SE

        SetAdjacent(Point.down, 4); // S
        SetAdjacent(new Point(-1, 1), 5); // SW

        SetAdjacent(Point.left, 6); // W
        SetAdjacent(new Point(-1, -1), 7); // NW
    }
    void SetAdjacent(Point adjDirection, int index){
        Chunk adjChunk = storedWorld.GetChunk(Point.add(location, adjDirection));
        if(adjChunk == null)
            return;
        adjacentChunks[index] = adjChunk;
        adjChunk.adjacentChunks[(index + 4) % 8] = this;
    }

    public void UpdateTiles()
    {
        if(GameManager.Instance.paused)
            return;

        //Console.out(consoleTag, Console.BLUE, "Updating Chunk at " + location);


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


    public void PlaceTile(Point position, Tile tile){
        PlaceTile(position.X, position.Y, tile);
    }
    public void PlaceTile(int x, int y, Tile tile)
    {
        // -> Make sure x & y are in bounds
        if(x < 0 || y < 0) return;
        if(x >= tiles.length || y >= tiles[0].length) return;

        Console.out(consoleTag, Console.BLUE, "Placing " + tile.tileName + " at " + tile.tilePoint);

        // -> Place tile
        tiles[x][y] = tile;
        tiles[x][y].storedChunk = this;
        tiles[x][y].SetPosition(new Point(x, y));
        tiles[x][y].updated = true;
        tiles[x][y].updatedThisFrame = true;
    }
    public void SwapTiles(Tile first, Tile second) {
        // -> Swap tiles
        Console.out(consoleTag, Console.BLUE, "Swapping positions F:" + first.tilePoint + " S:" + second.tilePoint);
        // Try to hold the tile
        Tile hTile = null;
        try {
            hTile = (Tile) second.clone();
        }
        catch (Exception e) { return; }

        first.storedChunk.PlaceTile(first.tilePoint.X, first.tilePoint.Y, second);
        second.storedChunk.PlaceTile(second.tilePoint.X, second.tilePoint.Y, hTile);
    }


    public Tile GetTile(Point position){
        // Make sure point is in position
        Console.out(consoleTag, Console.BLUE, "Attempting grab at " + position);
        if(!PointInBounds(position)){
            // -> Chek ADJ chunks
            Console.out(consoleTag, Console.BLUE, "Attempting grab in adj chunk");
            int dir = PointAdjDir(position);
            if(dir == -1)
                return null;

            // -> Get tile
            Chunk adjChunk = adjacentChunks[dir];
            position = PointInAdj(position);
            return adjChunk.GetTile(position);
        }
        Tile rTile = tiles[position.X][position.Y];
        Console.out(consoleTag, Console.BLUE, "Found tile in chunk at " + rTile.tilePoint);
        return rTile;
    }
    public Tile GetTile(Point position, int adjFlag){
        return null;
    }
    // N, E, S, W
    public Tile[] PullTouchingTiles(Point tilePoint){
        Tile[] returnTiles = new Tile[4];

        returnTiles[0] = GetTile(new Point(tilePoint.X, tilePoint.Y - 1));
        returnTiles[1] = GetTile(new Point(tilePoint.X + 1, tilePoint.Y));
        returnTiles[2] = GetTile(new Point(tilePoint.X, tilePoint.Y + 1));
        returnTiles[3] = GetTile(new Point(tilePoint.X - 1, tilePoint.Y));

        return returnTiles;
    }


    public boolean PointInBounds(Point position){
        // -> Make sure x & y are in bounds
        if(position.X < 0 || position.Y < 0) return false;
        if(position.X >= tiles.length || position.Y >= tiles[0].length) return false;

        return true;
    }
    public int PointAdjDir(Point position){
        // -> Make sure x & y are in bounds
        if(position.X < 0 && adjacentChunks[6] != null) return 6;
        if(position.X >= tiles.length && adjacentChunks[2] != null) return 2;

        if(position.Y < 0 && adjacentChunks[0] != null) return 0;
        if(position.Y >= tiles[0].length && adjacentChunks[4] != null) return 4;

        return -1;
    }
    public Point PointInAdj(Point position){
        Point rPoint = position;
        // Figure out of the point underflow-ed or overflowed
        if(rPoint.X < 0)
            rPoint.X += ChunkSize.X;
        else if (rPoint.X >= tiles.length)
            rPoint.X -= ChunkSize.X;

        if(rPoint.Y < 0)
            rPoint.Y += ChunkSize.Y;
        else if (rPoint.Y >= tiles.length)
            rPoint.Y -= ChunkSize.Y;

        return rPoint;
    }


    public void PushTiles(Tile[][] master, Point relativeLocation){
        if(!generated)
            return;

        Point simOrigin = new Point(relativeLocation.X * ChunkSize.X, relativeLocation.Y * ChunkSize.Y);

        for(int x = 0; x < ChunkSize.X; x++){

            for(int y = 0; y < ChunkSize.Y; y++){
                master[x + simOrigin.X][y + simOrigin.Y] = tiles[x][y];
            }
        }
    }
    public boolean CompareChunkLocation(Point location){
        return (location.X == this.location.X) && (location.Y == this.location.Y);
    }
    public Point LocalToWorld(Point local){
        Point worldOrigin = Point.multiply(location, ChunkSize);
        return Point.add(worldOrigin, local);
    }
}
