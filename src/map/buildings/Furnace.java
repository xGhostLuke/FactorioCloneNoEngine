package map.buildings;

import controller.OreController;
import controller.PlayerController;
import main.GamePanel;
import map.items.Item;

import java.awt.*;
import java.util.Map;

public class Furnace extends Building {


    public Furnace(PlayerController playerController, OreController oreController, String name, GamePanel gamePanel, int x, int y, Direction direction) {
        super(playerController, oreController, name, gamePanel, x, y, direction);

        setCraftingCost(oreController.stoneOre, 2, oreController.ironOre, 5);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g) {

    }

    @Override
    public void setCraftingCost(Item item, int cost, Item item2, int cost2) {
        craftingCost.put(item, cost);
        craftingCost.put(item2, cost2);
    }

    @Override
    public Map<Item, Integer> getCraftingCost() {
        return
    }

    @Override
    public Map<Item, Integer> getOutputInventory() {
        return outputInventory;
    }

    @Override
    public Item getOneItem() {
        return null;
    }

    @Override
    public void takeItem(Item item) {

    }

    @Override
    public void addItemToInventory(Item item, int amount) {

    }

    @Override
    public Map<Item, Integer> getInventory() {
        return Map.of();
    }
}
