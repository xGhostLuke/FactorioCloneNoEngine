package map;

import controller.OreController;
import controller.PlayerController;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public abstract class Building {
    protected OreController oreController;
    protected PlayerController playerController;
    protected String name;
    protected Map<Item, Integer> craftingCost = new HashMap<Item, Integer>();

    public Building(PlayerController playerController, OreController oreController, String name) {
        this.playerController = playerController;
        this.oreController = oreController;
        this.name = name;
    }

    public abstract void update();
    public abstract void draw(Graphics2D g);

    public abstract void setCraftingCost(Item item, int cost, Item item2, int cost2);
    public abstract Map<Item, Integer> getCraftingCost();

}
