package System;

import Debug.Console;
import Function.Graphics.Color;
import Function.Graphics.PowderButton;
import Function.Math.Point;
import Tiles.Gas.Air;
import Tiles.Tile;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

public class Program extends PApplet {

    public static GameManager gm;
    public static Program instance;

    public static int screenWidth = 1200;
    public static int screenHeight = 900;
    public static int pixelSize = 5;

    public static boolean drawDebug = false;

    public int selectedPen = 0;
    public String[] penTypes = { "Air", "Godrock", "Sand", "Dirt", "Water", "Grass", "Sapling", "Wood",
            "Fire", "Lava", "Acid", "Stone", "Bedrock", "Gas", "Fuse"};

    public PowderButton[] buttons;

    PImage menuImage;
    List<Integer> uXvals = new ArrayList<>();

    // Console information
    static String consoleTag = "PROGRAM";

    // Sets window settings
    public void settings(){
        Console.out(consoleTag, Console.GREEN, "Enforcing settings");

        // Create game manager - for system information
        gm = new GameManager();
        /*screenWidth = gm.sysInfo.screenSize.X - 500;
        screenHeight = gm.sysInfo.screenSize.Y;*/

        Console.out(consoleTag, Console.GREEN, "Width - " + (screenWidth + 500) + " | Height - " + screenHeight);
        Console.out(consoleTag, Console.GREEN, "Pixel Size - " + pixelSize);
        Console.out(consoleTag, Console.GREEN, "No smoothing");


        size(screenWidth + 500,screenHeight);
        pixelWidth = pixelSize;
        noSmooth();
    }

    // Setup the window
    public void setup()
    {
        Console.out(consoleTag, Console.GREEN, "Setting up window");

        instance = this;

        menuImage = loadImage("TileDisplay.png");

        // -> Setup buttons
        buttons = new PowderButton[penTypes.length];
        int columnSize = 9;
        for(int i = 0; i < penTypes.length; i++){
            // -> Get color of tile
            buttons[i] = new PowderButton((screenWidth + 70) + (130 * (Math.max(0, i / columnSize))), 320 + ((i % columnSize) * 60), 100, 30, i);
        }

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
        // Update loop
        if(gm != null)
            gm.UpdateTiles();



        // Update Atmosphere
        int lowestDirtY = 0;
        for(int i = 0; i < uXvals.size(); i++){
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
        }
        // Set pointer for the x values updated
        uXvals.clear();



        // Draw World
        for(int x = 0; x < screenWidth; x += pixelSize){
            for(int y = 0; y < screenHeight; y += pixelSize){
                Tile cTile = gm.tiles[x / pixelSize][y / pixelSize];

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
                    gm.tiles[x / pixelSize][y / pixelSize].updated = false;

                    // -> Check if cTile is stationary
                    if(!uXvals.contains(x) && cTile.isSolid) {
                        uXvals.add(x);
                    }
                }
            }
        }

        // Update Lighting


        // Draw menu image
        image(menuImage, screenWidth, 0, menuImage.width * 10, menuImage.height * 10);
        Tile hoverTile = gm.GetTile(gm.ScreenToWorld(new Point(mouseX, mouseY)));
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
        text(hoverTile.toString(), screenWidth + 190, 100);

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
        for(int i = 0; i < buttons.length; i++){
            if(buttons[i].PointInside(new Point(mouseX, mouseY)))
                buttons[i].OnClicked();
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

    public static void main(String[] args) {
        PApplet.main("System.Program");
    }



    int penSize = 0;
    public void ChangePenSize(int size){
        penSize = size;
    }
    public void PaintTile(){
        // -> Get location of mouse
        Point mousePos = gm.ScreenToWorld(new Point(mouseX, mouseY));

        Point pSize = new Point(penSize, penSize);

        for(int x = -pSize.X; x <= pSize.X; x++){
            for(int y = -pSize.Y; y <= pSize.Y; y++){
                Point mPos = Point.add(mousePos, new Point(x, y));
                Tile pTile = gm.TileFromName(penTypes[selectedPen]);
                // -> Check if the location is air
                Tile cTile = gm.GetTile(mPos);
                if(cTile != null && (cTile.tileName == "Air" || penTypes[selectedPen] == "Air")){
                    gm.PlaceTile(mPos, pTile);
                }
                if(Console.writeDraw)
                    Console.out(consoleTag, Console.GREEN, "Painting at " + mPos + " | " + mousePos);
            }
        }
    }
    public void CyclePenType(){
        selectedPen = (selectedPen + 1) % penTypes.length;
    }
    public void SetPenType(int id) {selectedPen = id;}
}