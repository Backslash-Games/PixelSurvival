package System;

import Debug.Console;
import Function.Math.Point;
import Generation.GenerationHandler;
import Generation.GenerationType;
import Generation.Structure.World;
import Tiles.*;
import Tiles.Effect.Fire;
import Tiles.Gas.Air;
import Tiles.Gas.Gas;
import Tiles.Gas.Steam;
import Tiles.Liquid.Acid;
import Tiles.Liquid.Lava;
import Tiles.Liquid.Water;
import Tiles.Object.Torch;
import Tiles.Plant.Grass;
import Tiles.Plant.Sapling;
import Tiles.Solid.*;
import Generation.Structure.Chunk;

public class GameManager
{
    // -> Camera details
    public Tile[][] simulatedTiles;
    public Chunk[][] viewedChunks;
    public Point viewPosition; // in chunk space
    public Point viewDistance; // in chunk space


    public int updateTimes = 0;
    public static Information sysInfo = null;
    public boolean paused = false;

    public static GameManager Instance = null;
    public static GenerationHandler generationHandler = null;
    public String consoleTag = "GAME.MNGR";

    public GameManager()
    {
        if(Instance != null)
            return;
        Instance = this;


        // -> Create system information
        if(sysInfo == null)
            sysInfo = new Information();

        // -> On start it will initialize the world
        BuildWorld();
    }


    // Takes in a world position and returns a chunk location
    public Point WorldToChunk(Point position){
        Point worldZero = Point.divide(position, new Point(Chunk.ChunkSize.X, Chunk.ChunkSize.Y));
        Point rVal = Point.subtract(worldZero, viewDistance);
        //Console.out(consoleTag, Console.YELLOW, "World to Chunk, value " + rVal);
        return rVal;
    }
    public Point WorldToLocal(Point world){
        return new Point(world.X % Chunk.ChunkSize.X, world.Y % Chunk.ChunkSize.Y);
    }
    public Point ScreenToWorld(Point position){
        Point rVal = new Point(Math.max(0, Math.min(Program.screenWidth - 1, position.X / Program.pixelSize)),
                Math.max(0, Math.min(Program.screenHeight - 1, position.Y / Program.pixelSize)));
        //Console.out(consoleTag, Console.YELLOW, "Screen to world, value " + rVal);
        return rVal;
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
            case "torch":
                return new Torch();
            case "steam":
                return new Steam();
            default:
                return new Tile();
        }
    }


    // Might need to move to chunk
    /*void FillTiles()
    {
        for(int x = 0; x < tiles.length; x++)
        {
            for(int y = 0; y < tiles[0].length; y++)
            {
                tiles[x][y] = new Air();
                tiles[x][y].updated = true;
            }
        }
    }*/

    void BuildWorld()
    {
        Point chunkSize = Chunk.ChunkSize;

        // Handles generation creation
        if(generationHandler == null)
            generationHandler = new GenerationHandler();
        generationHandler.GenerateWorld(new GenerationType(), Point.one);
    }
    public void Update(){
        generationHandler.currentWorld.UpdateChunks();
        UpdateSimulatedTiles();
    }
    void UpdateSimulatedTiles(){
        for(int x = 0; x < viewedChunks.length; x++){
            for(int y = 0; y < viewedChunks[0].length; y++){

                if(viewedChunks[x][y] == null){
                    Console.out(Console.ERROR_TAG, Console.RED,
                            "No chunk found at " + x + ", " + y);
                    return;
                }

                viewedChunks[x][y].PushTiles(simulatedTiles, new Point(x, y));
            }
        }
    }


    public void TogglePlaytime(){
        paused = !paused;
    }
}
