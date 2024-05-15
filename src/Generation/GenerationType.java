package Generation;

import Debug.Console;
import Generation.Structure.Chunk;
import System.GameManager;
import Tiles.Gas.Air;
import Tiles.Solid.Bedrock;
import Tiles.Tile;
import Function.Math.*;

public class GenerationType {

    public String name = "NULL";
    public Point chunkSize = Point.zero;

    public String consoleTag = "G.TYPE";

    // Returned a simulated tile set to be pushed into a chunk.
    public void Generate(Chunk chunk){
        Console.out(consoleTag, Console.CYAN, "Generating tile set using type " + name);
        chunkSize = Chunk.ChunkSize;

        // Set default generation
        for(int x = 0; x < chunkSize.X; x++){
            for(int y = 0; y < chunkSize.Y; y++){
                // Get world position
                Point worldPosition = chunk.LocalToWorld(new Point(x, y));

                if(worldPosition.Y >= 54)
                    chunk.PlaceTile(new Point(x, y), new Bedrock());
                else
                    chunk.PlaceTile(new Point(x, y), new Air());
            }
        }
    }
}
