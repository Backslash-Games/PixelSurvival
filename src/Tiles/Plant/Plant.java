package Tiles.Plant;

import Tiles.Effect.Ember;
import System.Program;
import Tiles.Tile;

public class Plant extends Tile {
    public boolean canGrow = true;

    @Override
    public void OnUpdate() {
        super.OnUpdate();

        if(canGrow)
            OnGrow();
    }

    public void OnGrow() { }
}
