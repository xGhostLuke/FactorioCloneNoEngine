package map.buildings;

import controller.OreController;
import controller.PlayerController;
import main.GamePanel;
import map.items.Item;

import java.util.HashMap;
import java.util.Map;

public abstract class Building extends Placeable {

    protected Map<Item, Integer> inputInventory = new HashMap<Item, Integer>();
    protected Map<Item, Integer> outputInventory = new HashMap<Item, Integer>();

    public Building(PlayerController playerController, OreController oreController, String name, GamePanel gamePanel, int x, int y, Direction direction) {
        super(x, y, gamePanel, name, direction);
        this.playerController = playerController;
        this.oreController = oreController;
        this.name = name;
    }



}
