package System.Modes;

import System.*;
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

public class SandboxMode extends Program {// Sets window settings
    @Override
    public void settings(){
        Console.out(consoleTag, Console.GREEN, "Enforcing settings");

        // Create game manager - for system information
        gm = new GameManager();
        //int simulationSize = Math.min(gm.sysInfo.screenSize.X, gm.sysInfo.screenSize.Y);
        //int simulationEdgeBuffer = simulationSize / 10;
        //screenWidth = screenHeight = simulationSize - simulationEdgeBuffer;

        Console.out(consoleTag, Console.GREEN, "Width - " + (screenWidth + 500) + " | Height - " + screenHeight);
        Console.out(consoleTag, Console.GREEN, "Pixel Size - " + pixelSize);
        Console.out(consoleTag, Console.GREEN, "Sim.W - " + (screenWidth / pixelSize) +
                " | Sim.H - " + (screenHeight / pixelSize));
        Console.out(consoleTag, Console.GREEN, "No smoothing");


        size(screenWidth + 500,screenHeight);
        pixelWidth = pixelSize;
        noSmooth();
    }

    // Setup the window
    @Override
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
    @Override
    public void draw()
    {
        // Update loop
        /*if(gm != null)
            gm.UpdateTiles();*/



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
        /*for(int x = 0; x < screenWidth; x += pixelSize){
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
        }*/

        // Update Lighting


        // Draw menu image
        image(menuImage, screenWidth, 0, menuImage.width * 10, menuImage.height * 10);
        /*Tile hoverTile = gm.GetTile(gm.ScreenToWorld(new Point(mouseX, mouseY)));
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
}
