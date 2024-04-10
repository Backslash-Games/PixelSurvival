package System;

import Function.Math.Point;
import Function.Math.Random;
import Tiles.*;
import Tiles.Effect.Fire;
import Tiles.Gas.Air;
import Tiles.Gas.Gas;
import Tiles.Liquid.Acid;
import Tiles.Liquid.Lava;
import Tiles.Liquid.Water;
import Tiles.Plant.Grass;
import Tiles.Plant.Sapling;
import Tiles.Solid.*;

public class GameManager
{
    public Tile[][] tiles;
    public int updateTimes = 0;

    boolean paused = false;

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

    // N, E, S, W
    public Tile[] PullTouchingTiles(Point tilePoint){
        Tile[] returnTiles = new Tile[4];

        returnTiles[0] = GetTile(new Point(tilePoint.X, tilePoint.Y - 1));
        returnTiles[1] = GetTile(new Point(tilePoint.X + 1, tilePoint.Y));
        returnTiles[2] = GetTile(new Point(tilePoint.X, tilePoint.Y + 1));
        returnTiles[3] = GetTile(new Point(tilePoint.X - 1, tilePoint.Y));

        return returnTiles;
    }

    public void UpdateTiles()
    {
        if(paused)
            return;

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
            case "fire":
                return new Fire();
            case "lava":
                return new Lava();
            case "acid":
                return new Acid();
            case "stone":
                return new Stone();
            case "godrock":
                return new Godrock();
            case "gas":
                return new Gas();
            case "fuse":
                return new Fuse();
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
        // Set air
        for(int x = 0; x < tiles.length;  x++) {
            for(int y = 0; y < tiles[0].length;  y++) {
                PlaceTile(x, y, new Air());
            }
        }



        // Build dirt
        float height = 0;
        for(int x = 0; x < tiles.length;  x++)
        {
            height += Random.Range(-1000, 1000) / 1000f;
            int modHeight = Math.round(height);

            int l1 = tiles[0].length - 10 - modHeight;
            int l2 = tiles[0].length - 25 - (modHeight * 2);
            int l3 = tiles[0].length - 50 - (modHeight * 3);

            // -> Bedrock
            for(int y = tiles[0].length - 1; y >= l1; y--)
                PlaceTile(x, y, new Bedrock());
            // -> Stone
            for(int y = l1 - 1; y >= l2; y--)
                PlaceTile(x, y, new Stone());
            // -> Dirt
            for(int y = l2 - 1; y >= l3; y--)
            {
                PlaceTile(x, y, new Dirt());

                if(y == l3){
                    PlaceTile(x, y - 1, new Grass());
                }
            }
        }
    }


    public void TogglePlaytime(){
        paused = !paused;
    }
}
