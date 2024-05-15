package Tiles;

import java.util.Random;
import Function.Graphics.Color;
import Function.Math.Point;
import System.Program;
import Tiles.Effect.Ember;
import Tiles.Gas.Air;
import System.*;
import Generation.Structure.Chunk;

public class Tile
{
    public Chunk storedChunk = null;

    // -> Processing <-
    public boolean updated = false;
    public boolean updatedThisFrame = false;
    public boolean stationary = false;

    // -> Attributes <-
    public String tileName = "Powder";
    public Color tileColor = new Color(255, 225, 125);
    public Point tilePoint = new Point(0, 0);
    public String passThrough[] = new String[0];
    public int health = -1;

    // -> Properties <-
    public boolean deteriorates = false;
    public boolean isSolid = true;
    public boolean hasGravity = true;
    public boolean canFlow = false;
    public boolean canFloat = false;
    public boolean canBurn = false;
    public boolean flammable = false;
    public int flammableChance = 1;
    public int acidity = 0;
    public boolean invulnerable = false;
    public int density = 1;


    // -> Events
    public void OnUpdate()
    {
        OnGravity();
        OnFlow();
        OnDeteriorate();

        OnCheckBurn();
        OnCheckAcid();
    }

    // Moves pixel vertically
    public void OnGravity()
    {
        if(!hasGravity)
            return;

        // -> Make sure game manager is set
        if (Program.gm == null)
            return;

        // Shows gravity direction
        int gravityDir = 1;
        if(canFloat)
            gravityDir = -1;
        // Pulls reference to new point
        Point newPoint = new Point(tilePoint.X, tilePoint.Y + gravityDir);

        // -> Check if there is a tile below
        Tile[][] tiles = storedChunk.tiles;
        Tile compTile = null;
        int adjFlag = -1;
        // --> Make sure tile is in bounds
        if(storedChunk.PointInBounds(newPoint)) // Checks for a movement within chunk
        {
            compTile = tiles[newPoint.X][newPoint.Y];
        }
        else if((adjFlag = storedChunk.PointAdjDir(newPoint)) != -1)
        {
            newPoint = storedChunk.PointInAdj(newPoint);
            compTile = storedChunk.adjacentChunks[adjFlag].tiles[newPoint.X][newPoint.Y];
        }

        // Check if the comp tile is still null
        if(compTile == null){
            stationary = true;
            return;
        }

        // -> Move in direction of gravity
        if ((CheckCollision(compTile) || CheckPassThrough(compTile.tileName)) && compTile.tileName != tileName) {
            storedChunk.SwapTiles(tilePoint, newPoint, adjFlag);
            updated = true;
            stationary = false;
        }
    }

    // Wiggles back and forth
    public void OnFlow(){
        if(!canFlow)
            return;

        // -> Check adjacent
        // -> Make sure game manager is set
        if (Program.gm == null)
            return;

        // -> Check if there is a tile below
        Random rand = new Random();

        // Check if the tile can flow
        Tile[] touchingTiles = storedChunk.PullTouchingTiles(tilePoint);
        boolean touchingAir = false;
        for(int i = 0; i < touchingTiles.length; i++){
            if(touchingTiles[i] == null)
                continue;
            if(touchingTiles[i].tileName == "Air"){
                touchingAir = true;
                break;
            }
        }
        if(!touchingAir)
            return;

        Point checkDir = new Point(1, canFloat ? -1 : 1);
        // Find the path of least resistance
        Point checkPos = tilePoint;
        // -> Check right
        int rDist = 0;
        boolean tileFoundR = false;
        while(!tileFoundR && rDist < 500){
            // Check next tile
            checkPos = Point.add(checkPos, new Point(checkDir.X, 0));
            Tile cTile = storedChunk.GetTile(checkPos);
            // Check if the tile is solid
            if(cTile == null)
                break;
            if(cTile.isSolid)
                break;

            // Check if the vertical tile is available to move to
            Point vertPos = Point.add(checkPos, new Point(0, checkDir.Y));
            cTile = storedChunk.GetTile(vertPos);
            if(cTile == null)
                break;
            if(cTile.tileName == "Air")
                tileFoundR = true;

            rDist++;
        }

        // -> Check Left
        checkDir = new Point(-1, canFloat ? -1 : 1);
        checkPos = tilePoint;
        int lDist = 0;
        boolean tileFoundL = false;
        while(!tileFoundL && lDist < 500){
            // Check next tile
            checkPos = Point.add(checkPos, new Point(checkDir.X, 0));
            Tile cTile = storedChunk.GetTile(checkPos);
            // Check if the tile is solid
            if(cTile == null)
                break;
            if(cTile.isSolid)
                break;

            // Check if the vertical tile is available to move to
            Point vertPos = Point.add(checkPos, new Point(0, checkDir.Y));
            cTile = storedChunk.GetTile(vertPos);
            if(cTile == null)
                break;
            if(cTile.tileName == "Air")
                tileFoundL = true;

            lDist++;
        }

        // If no direction is found, randomize
        Tile[][] tiles = storedChunk.tiles;
        boolean moveLeft = rand.nextBoolean();
        if(tileFoundR || tileFoundL){
            // Go for best direction
            if(rDist < lDist && tileFoundR)
                moveLeft = false;
            else if(lDist < rDist && tileFoundL)
                moveLeft = true;
        }

        // Move tile
        int moveChecks = 0;
        while(moveChecks < 2) {
            // Move left
            if (tilePoint.X - 1 >= 0 && !tiles[tilePoint.X - 1][tilePoint.Y].isSolid &&
                    tiles[tilePoint.X - 1][tilePoint.Y].tileName != tileName && moveLeft) {
                storedChunk.SwapTiles(tilePoint, new Point(tilePoint.X - 1, tilePoint.Y));
                updated = true;
                stationary = false;
                return;
            }
            // Move right
            if (tilePoint.X + 1 < tiles.length && !tiles[tilePoint.X + 1][tilePoint.Y].isSolid &&
                    tiles[tilePoint.X + 1][tilePoint.Y].tileName != tileName && !moveLeft) {
                storedChunk.SwapTiles(tilePoint, new Point(tilePoint.X + 1, tilePoint.Y));
                updated = true;
                stationary = false;
                return;
            }
            stationary = true;
            moveChecks++;
        }
    }

    // Increment lifetime
    public void OnDeteriorate(){
        if(!deteriorates)
            return;

        // -> Reduce life
        // -> Destroy tile
        health--;
        if(health <= 0)
            Destroy();
    }

    // Checks if a flammable object is next to a burning object
    public void OnCheckBurn(){
        if(!flammable)
            return;

        Tile[] adjTiles = storedChunk.PullTouchingTiles(tilePoint);
        for(int i = 0; i < adjTiles.length; i++){
            // Check if tile is null
            if(adjTiles[i] == null)
                continue;
            // Check for a burn
            if(adjTiles[i].canBurn && Function.Math.Random.Range(flammableChance) == 0){
                OnHurt(health, true);
            }
        }
    }

    // Acid adjacent
    public void OnCheckAcid(){
        if(acidity <= 0)
            return;

        Tile[] adjTiles = storedChunk.PullTouchingTiles(tilePoint);
        int tilesHurt = 0;
        for(int i = 0; i < adjTiles.length; i++){
            // Check if tile is null
            if(adjTiles[i] == null || adjTiles[i].invulnerable)
                continue;
            if(adjTiles[i].tileName == tileName)
                continue;
            adjTiles[i].OnHurt(acidity, false);
            tilesHurt++;
        }
        OnHurt(tilesHurt, false);
    }


    // -> Check pass through
    public boolean CheckPassThrough(String id){
        for(int i = 0; i < passThrough.length; i++)
            if(passThrough[i] == id)
                return true;
            return false;
    }

    public boolean CheckCollision(Tile oTile){
        if(!isSolid){
            return !oTile.isSolid && oTile.density < density;
        }
        return !oTile.isSolid;
    }
    // -> Set/Get Methods
    public void SetPosition(Point position)
    {
        tilePoint = position;
    }

    public void OnHurt(int damage, boolean burning){
        if(health == -1)
            return;

        // -> Reduce HP
        health -= damage;

        // -> If health is 0 destory
        if(health <= 0)
            Destroy(burning);
    }

    // -> Destroys tile
    public void Destroy(){
        // -> Sets current position to air
        storedChunk.PlaceTile(tilePoint.X, tilePoint.Y, new Air());
    }
    public void Destroy(boolean burning){
        if(!burning) {
            Destroy();
            return;
        }
        // -> Sets current position to air
        storedChunk.PlaceTile(tilePoint.X, tilePoint.Y, new Ember());
    }

    // Checks surrounding tiles
    public boolean CompareSurrounding(String oName){
        Point cPos = new Point(0, -1);
        for(int i = 0; i < 4; i++){

            // -> Get point from list
            switch(i){
                case 1:
                    cPos = new Point(1, 0);
                    break;
                case 2:
                    cPos = new Point(0, 1);
                    break;
                case 3:
                    cPos = new Point(-1, 0);
                    break;
                default:
                    break;
            }
            Point uPos = Point.add(cPos, tilePoint);
            if(!storedChunk.PointInBounds(uPos))
                continue;

            Tile cTile = storedChunk.GetTile(uPos);
            if(cTile != null && !cTile.tileName.equals(oName))
                return false;
        }
        return true;
    }
    public boolean CheckAdj(String oName){
        Point cPos = new Point(0, -1);
        for(int i = 0; i < 4; i++){

            // -> Get point from list
            switch(i){
                case 1:
                    cPos = new Point(1, 0);
                    break;
                case 2:
                    cPos = new Point(0, 1);
                    break;
                case 3:
                    cPos = new Point(-1, 0);
                    break;
                default:
                    break;
            }
            Point uPos = Point.add(cPos, tilePoint);
            if(!storedChunk.PointInBounds(uPos))
                continue;

            Tile cTile = storedChunk.GetTile(uPos);
            if(cTile != null && cTile.tileName.equals(oName))
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return  "\nFunction.Graphics.Color: " + tileColor.toString() +
                "\nPosition: " + tilePoint.toString() +
                "\nSolid: " + isSolid +
                "\nGravity: " + hasGravity +
                "\nFlowing: " + canFlow +
                "\nStationary: " + stationary +
                "\nLife: " + health + " (" + deteriorates + ")" +
                "\nDensity: " + density +
                "\nAcidity: " + acidity;
    }
}
