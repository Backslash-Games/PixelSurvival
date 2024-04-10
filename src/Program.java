import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

public class Program extends PApplet {

    public static GameManager gm;
    public static Program instance;

    public static int screenWidth = 1200;
    public static int screenHeight = 900;
    public static int pixelSize = 10;

    public static boolean drawDebug = false;

    public int selectedPen = 0;
    public String[] penTypes = { "Air", "Bedrock", "Sand", "Dirt", "Water", "Grass", "Sapling", "Wood"};

    public PowderButton[] buttons;

    PImage menuImage;
    List<Integer> uXvals = new ArrayList<>();

    public void settings(){
        size(screenWidth + 500,screenHeight);
        pixelWidth = pixelSize;
        noSmooth();
    }

    public void setup()
    {
        gm = new GameManager();
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
        loop();
    }


    public void draw()
    {
        // Update loop
        if(gm != null)
            gm.UpdateTiles();



        // Update Atmosphere
        int lowestDirtY = 0;
        for(int i = 0; i < uXvals.size(); i++){
            if(i == 0)
                System.out.println(uXvals.size());
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
        // Print out uXVals
        for(int i = 0; i < uXvals.size(); i++){
            System.out.print((uXvals.get(i) / pixelSize) + ", ");
            if(i == uXvals.size() - 1)
                System.out.println();
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
        image(menuImage, screenWidth, 0, menuImage.width * pixelSize, menuImage.height * pixelSize);
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
        if(key == ' '){
            CyclePenType();
        }
    }

    public static void main(String[] args) {
        PApplet.main("Program");
    }



    public void PaintTile(){
        // -> Get location of mouse
        Point mousePos = gm.ScreenToWorld(new Point(mouseX, mouseY));
        // -> Check if the location is air
        Tile cTile = gm.GetTile(mousePos);
        if(cTile != null && (cTile.tileName == "Air" || penTypes[selectedPen] == "Air")){
            gm.PlaceTile(mousePos, gm.TileFromName(penTypes[selectedPen]));
        }
    }
    public void CyclePenType(){
        selectedPen = (selectedPen + 1) % penTypes.length;
    }
    public void SetPenType(int id) {selectedPen = id;}
}