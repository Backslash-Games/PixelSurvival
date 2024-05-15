package Generation;

import Debug.Console;
import Function.Math.*;
import Generation.Structure.Chunk;
import Generation.Structure.World;
import System.GameManager;
import Tiles.Tile;

import java.util.Iterator;

// Generation handler will create a world with definitions based around a preset (or generation type)
// It will be what other functions call when they want to modify the world
public class GenerationHandler {
    public static GenerationHandler Instance = null;
    public static World currentWorld = null;
    public String consoleTag = "GENERATION.H";

    public GenerationHandler(){
        if(Instance != null){
            Console.out(Console.ERROR_TAG, Console.RED, "GEN.HNDLR INSTANCE ALREADY SET!!");
        }
        Instance = this;
    }

    public void GenerateWorld(GenerationType type, Point chunkDist){
        Console.out(consoleTag, Console.RED, "Generating world with type " + type.name);

        currentWorld = new World(type);
        // Sets up viewpoint in the gamemanager
        SetViewpoint(Point.zero, new Point(1, 1));
    }

    public void SetViewpoint(Point location, Point distance){
        currentWorld.GenerateChunks(location, distance);
        Chunk[][] viewPoint = new Chunk[(distance.X * 2) + 1][(distance.Y * 2) + 1];

        currentWorld.DisableAllChunks();

        // Log viewed chunks
        Point cPoint = Point.subtract(location, distance);
        for(int x = 0; x < (distance.X * 2) + 1; x++){
            for(int y = 0; y < (distance.Y * 2) + 1; y++){
                viewPoint[x][y] = currentWorld.GetChunk(cPoint);
                viewPoint[x][y].chunkActive = true;
                cPoint.Y++;
            }
            cPoint.X++;
            cPoint.Y = location.Y - distance.Y;
        }

        // Push viewpoint
        GameManager.Instance.viewedChunks = viewPoint;
        GameManager.Instance.simulatedTiles = new Tile[viewPoint.length * Chunk.ChunkSize.X][viewPoint[0].length * Chunk.ChunkSize.Y];
        GameManager.Instance.viewPosition = location;
        GameManager.Instance.viewDistance = distance;
    }
}
