package map;

import controller.OreController;
import controller.PlayerController;
import main.GamePanel;

import java.util.AbstractMap;
import java.util.Map;

public abstract class Building extends Placeable {



    public Building(PlayerController playerController, OreController oreController, String name, GamePanel gamePanel, int x, int y, Direction direction) {
        super(x, y, gamePanel, name, direction);
        this.playerController = playerController;
        this.oreController = oreController;
        this.name = name;
    }



}
