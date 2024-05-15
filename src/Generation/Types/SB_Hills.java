package Generation.Types;

import Function.Math.Random;
import Generation.GenerationType;
import Generation.Structure.Chunk;
import Tiles.Tile;
import Tiles.Gas.Air;
import Tiles.Plant.Grass;
import Tiles.Solid.Bedrock;
import Tiles.Solid.Dirt;
import Tiles.Solid.Stone;
import System.*;

// Sandbox Hills - Default used in the prototyping of the sandbox mode
public class SB_Hills extends GenerationType {

    public SB_Hills(){
        name = "Sandbox Hills";
    }

    @Override
    public void Generate(Chunk chunk) {
        // Set air
        /*for(int x = 0; x < gm.tiles.length;  x++) {
            for(int y = 0; y < gm.tiles[0].length;  y++) {
                gm.PlaceTile(x, y, new Air());
            }
        }



        // Build dirt
        float height = 0;
        for(int x = 0; x < gm.tiles.length;  x++)
        {
            height += Random.Range(-1000, 1000) / 1000f;
            int modHeight = Math.round(height);

            int l1 = gm.tiles[0].length - 10 - modHeight;
            int l2 = gm.tiles[0].length - 25 - (modHeight * 2);
            int l3 = gm.tiles[0].length - 50 - (modHeight * 3);

            // -> Bedrock
            for(int y = gm.tiles[0].length - 1; y >= l1; y--)
                gm.PlaceTile(x, y, new Bedrock());
            // -> Stone
            for(int y = l1 - 1; y >= l2; y--)
                gm.PlaceTile(x, y, new Stone());
            // -> Dirt
            for(int y = l2 - 1; y >= l3; y--)
            {
                gm.PlaceTile(x, y, new Dirt());

                if(y == l3){
                    gm.PlaceTile(x, y - 1, new Grass());
                }
            }
        }
*/
    }
}
