package Generation.Structure;

import Debug.Console;
import Function.Math.Point;
import Generation.*;

import java.util.ArrayList;
import java.util.Iterator;

// When a world is created (by generation handler) it will take in a generation type
// Using the generation type it will populate itself with chunks
// -> Handles interactions between separated chunks
public class World {
    public static GenerationType generationType = null;
    public ArrayList<Chunk> chunks = new ArrayList<Chunk>();

    public String consoleTag = "WORLD";
    public World(GenerationType type){
        Console.out(consoleTag, Console.PURPLE, "Generating world with type " + type.name);

        // Ensure the world has only one type
        if(generationType != null){
            Console.out(Console.ERROR_TAG, Console.RED, "Generation Type is already defined");
            return;
        }
        generationType = type;

        // Start generating chunks
        GenerateChunk(Point.zero);
    }

    public Chunk[] GenerateChunks(Point origin, Point distance){
        ArrayList<Chunk> gChunks = new ArrayList<Chunk>();

        for(int x = origin.X - distance.X; x <= origin.X + distance.X; x++){
            for(int y = origin.Y - distance.Y; y <= origin.Y + distance.Y; y++){
                gChunks.add(GenerateChunk(new Point(x, y)));
            }
        }
        return gChunks.toArray(new Chunk[0]);
    }

    public Chunk GenerateChunk(Point location){
        // Check if the chunk has already been generated
        if(ChunkExists(location))
            return GetChunk(location);

        // Otherwise generate new chunk
        Chunk nChunk = new Chunk(location, this, generationType);
        chunks.add(nChunk);
        return nChunk;
    }
    public boolean ChunkExists(Point location){
        Chunk tChunk = GetChunk(location);
        if(tChunk == null)
            return false;
        return true;
    }
    public Chunk GetChunk(Point location){
        Iterator<Chunk> chunkIterator = chunks.iterator();
        while(chunkIterator.hasNext()){
            Chunk cChunk = chunkIterator.next();
            if(cChunk.CompareChunkLocation(location)){
                return cChunk;
            }
        }
        return null;
    }

    public void UpdateChunks(){
        for (Chunk cChunk : chunks) {
            if(cChunk.chunkActive)
                cChunk.UpdateTiles();
        }
    }
    public void DisableAllChunks(){
        for (Chunk cChunk : chunks) {
            cChunk.chunkActive = false;
        }
    }
}
