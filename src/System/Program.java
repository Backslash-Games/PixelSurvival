package System;

import Debug.Console;
import Function.Graphics.Color;
import Function.Graphics.PowderButton;
import Function.Math.Point;
import Generation.Structure.Chunk;
import Tiles.Gas.Air;
import Tiles.Tile;
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class Program extends PApplet {

    public static GameManager gm;
    public static Program instance;

    public static int screenWidth = 960;
    public static int screenHeight = 960;
    public static int pixelSize = 5;

    public static boolean drawDebug = false;
    public static boolean drawChunkOutline = true;

    public int selectedPen = 0;
    public String[] penTypes = { "Air", "Godrock", "Sand", "Dirt", "Water", "Grass", "Sapling", "Wood",
            "Fire", "Lava", "Acid", "Stone", "Bedrock", "Gas", "Fuse", "Torch", "Steam"};

    public PowderButton[] buttons;

    public PImage menuImage;
    public List<Integer> uXvals = new ArrayList<>();

    // Console information
    public static String consoleTag = "PROGRAM";
    float zoom = 1;


    // Sets window settings
    public void settings(){
        Console.out(consoleTag, Console.GREEN, "Enforcing settings");

        // Create game manager - for system information
        gm = new GameManager();

        Console.out(consoleTag, Console.GREEN, "Width - " + (screenWidth) + " | Height - " + screenHeight);
        Console.out(consoleTag, Console.GREEN, "Pixel Size - " + pixelSize);
        Console.out(consoleTag, Console.GREEN, "Sim.W - " + (screenWidth / pixelSize) +
                " | Sim.H - " + (screenHeight / pixelSize));
        Console.out(consoleTag, Console.GREEN, "No smoothing");


        size(screenWidth,screenHeight);
        pixelWidth = pixelSize;
        noSmooth();
    }

    // Setup the window
    public void setup()
    {
        Console.out(consoleTag, Console.GREEN, "Setting up window");

        instance = this;

        menuImage = loadImage("TileDisplay.png");

        // Put all x into uXvals
        for(int x = 0; x < screenWidth; x += pixelSize) {
            uXvals.add(x);
        }

        noStroke();
        Console.out(consoleTag, Console.GREEN, "Starting loop");
        loop();
    }

    // Draw the window
    public void draw()
    {
        if(gm == null)
            return;

        // Update loop
        gm.Update();



        // Update Atmosphere
        int lowestDirtY = 0;
        /*for(int i = 0; i < uXvals.size(); i++){
            int x = uXvals.get(i);

            Tile cTile;

            // -> Get lowest dirt
            for(int y = 0; y < screenHeight; y += pixelSize){
                cTile = gm.tiles[x / pixelSize][y / pixelSize];

                if(cTile.isSolid)
                    lowestDirtY = y / pixelSize;
            }

            // -> Set air dirt distance
            for(int y = 0; y < screenHeight; y += pixelSize){
                cTile = gm.tiles[x / pixelSize][y / pixelSize];

                if(cTile.tileName == "Air") {
                    Air air = (Air) cTile;
                    air.dirtDist = lowestDirtY - (y / pixelSize);
                    air.UpdateAtmosphere();
                }
            }
        }*/
        // Set pointer for the x values updated
        uXvals.clear();



        // Draw World
        stroke(0, 0, 0, 0);
        for(int x = 0; x < gm.simulatedTiles.length * pixelSize; x += pixelSize){
            for(int y = 0; y < gm.simulatedTiles[0].length * pixelSize; y += pixelSize){
                Tile cTile = gm.simulatedTiles[x / pixelSize][y / pixelSize];

                if(drawDebug){
                    if(cTile.updated)
                        fill(0, 255, 0);
                    else
                        fill(255, 0, 0);
                    rect(x, y, 3, 3);

                    if(cTile.stationary)
                        fill(0, 255, 0);
                    else
                        fill(255, 0, 0);
                    rect(x + 3, y, 3, 3);
                }

                Color pc = cTile.tileColor;
                fill(pc.r, pc.g, pc.b);

                if(cTile.updated){
                    rect(x, y, pixelSize, pixelSize);
                    gm.simulatedTiles[x / pixelSize][y / pixelSize].updated = false;

                    // -> Check if cTile is stationary
                    if(!uXvals.contains(x) && cTile.isSolid) {
                        uXvals.add(x);
                    }
                }
            }
        }
        // Draw chunk outline
        if(drawChunkOutline){
            stroke(150, 0, 0, 255);
            fill(0, 0, 0, 0);
            // Draw square
            for(int x = 0; x < gm.viewedChunks.length; x++){
                for(int y = 0; y < gm.viewedChunks[0].length; y++){
                    rect(x * Chunk.ChunkSize.X * pixelSize, y * Chunk.ChunkSize.Y * pixelSize,
                            Chunk.ChunkSize.X * pixelSize, Chunk.ChunkSize.Y * pixelSize);
                }
            }
        }

        // Update Lighting


        // Draw menu image
        image(menuImage, screenWidth, 0, menuImage.width * 10, menuImage.height * 10);

        /*Tile hoverTile = gm.generationHandler.currentWorld.GetTile(gm.ScreenToWorld(new Point(mouseX, mouseY)));
        String stateText = "Hovering";
        if(hoverTile == null)
        {
            hoverTile = gm.TileFromName(penTypes[selectedPen]);
            stateText = "Drawing";
        }

        Color hc = hoverTile.tileColor;
        fill(hc.r, hc.g, hc.b);
        rect(screenWidth + 90, 120, 80, 80);

        fill(255, 255, 255);
        textSize(10);
        text(stateText, screenWidth + 188, 72);

        fill(255, 255, 255);
        textSize(30);
        text(hoverTile.tileName, screenWidth + 180, 97);

        fill(200, 200, 200);
        textSize(15);
        text(hoverTile.toString(), screenWidth + 190, 100);*/

        // Draw Menu Contents
        int columnSize = 9;
        for(int i = 0; i < penTypes.length; i++){
            if(i == selectedPen){
                // -> Draw a rectangle
                fill(255, 0, 0, 140);
                rect((screenWidth + 70) + (130 * (Math.max(0, i / columnSize))), 320 + ((i % columnSize) * 60), 100, 30);
            }

            // -> Get color of tile
            Color sc = gm.TileFromName(penTypes[i]).tileColor.Normalize();

            fill(sc.r, sc.g, sc.b);
            textSize(20);
            text(penTypes[i], (screenWidth + 73) + (130 * (Math.max(0, i / columnSize))), 340 + ((i % columnSize) * 60));
        }

        // -> Draw buttons on debug
        if(drawDebug)
            for(int i = 0; i < buttons.length; i++) {
                fill(0, 0, 255, 130);
                rect(buttons[i].origin.X, buttons[i].origin.Y, buttons[i].size.X, buttons[i].size.Y);
            }



    }

    @Override
    public void mousePressed() {
        PaintTile();

        if(buttons != null) {
            for (int i = 0; i < buttons.length; i++) {
                if (buttons[i].PointInside(new Point(mouseX, mouseY)))
                    buttons[i].OnClicked();
            }
        }
    }

    @Override
    public void mouseDragged() {
        PaintTile();
    }

    @Override
    public void keyPressed() {
        if(Console.writeKeypress)
            Console.out("INPUT", Console.RED, "Key pressed '" + key + "'");
        if(key == ' '){
            CyclePenType();
        }
        if(key == 'q' && gm != null){
            gm.TogglePlaytime();
        }
        switch (key){
            case '1':
                ChangePenSize(0);
                break;
            case '2':
                ChangePenSize(1);
                break;
            case '3':
                ChangePenSize(2);
                break;
            case '4':
                ChangePenSize(3);
                break;
            case '5':
                ChangePenSize(4);
                break;
            case '6':
                ChangePenSize(5);
                break;
            case '7':
                ChangePenSize(6);
                break;
            case '8':
                ChangePenSize(7);
                break;
            case '9':
                ChangePenSize(8);
                break;
            case '0':
                ChangePenSize(9);
                break;
        }
    }
    @Override
    public void mouseWheel(MouseEvent event) {
        if(event.getCount() == -1)
            SetZoomAmount(zoom - 0.1f);
        else if(event.getCount() == 1)
            SetZoomAmount(zoom + 0.1f);
    }

    // Arguments list
    // 0 - Game Mode
    public static void main(String[] args) {
        // Pull mode from args
        int mode = 0; // default to modeless
        if(args.length > 0){
            mode = Integer.parseInt(args[0]);
        }

        BootMode(mode);
    }
    static void BootMode(int mode){
        switch(mode){
            case 1: // Fortress Mode
                PApplet.main("System.Modes.FortressMode");
                break;
            case 2: // Sandbox Mode
                PApplet.main("System.Modes.SandboxMode");
                break;
            default: // Modeless
                PApplet.main("System.Program");
                break;
        }
    }



    int penSize = 0;
    public void ChangePenSize(int size){
        penSize = size;
    }
    public void PaintTile(){
        // -> Get location of mouse
        Point mousePos = gm.ScreenToWorld(new Point(mouseX, mouseY));
        // Get hovering chunk
        Point cLocation = gm.WorldToChunk(mousePos);
        Chunk wChunk = gm.generationHandler.currentWorld.GetChunk(cLocation);
        Point localPosition = gm.WorldToLocal(mousePos);

        Point pSize = new Point(penSize, penSize);

        for(int x = -pSize.X; x <= pSize.X; x++){
            for(int y = -pSize.Y; y <= pSize.Y; y++){
                Point mPos = Point.add(localPosition, new Point(x, y));
                Tile pTile = gm.TileFromName(penTypes[selectedPen]);
                // -> Check if the location is air
                Tile cTile = wChunk.GetTile(mPos);
                if(cTile != null && (cTile.tileName == "Air" || penTypes[selectedPen] == "Air")){
                    wChunk.PlaceTile(mPos, pTile);
                }
                if(Console.writeDraw)
                    Console.out(consoleTag, Console.GREEN, "Painting at mPos: " + mPos + " | mousePosition: " + mousePos + " | localPosition: " + localPosition);
            }
        }
    }
    public void CyclePenType(){
        selectedPen = (selectedPen + 1) % penTypes.length;
    }
    public void SetPenType(int id) {selectedPen = id;}
    public void SetZoomAmount(float zoom){
        // Unusable rn
        /*
        if(zoom <= 0)
            return;

        Console.out(consoleTag, Console.GREEN, "Adjusting zoom: " + zoom);

        rect(screenWidth>>1, screenHeight>>1, zoom, zoom);
        this.zoom = zoom;*/
    }
}