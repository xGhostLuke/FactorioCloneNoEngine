package map;

import controller.OreController;
import controller.PlayerController;
import main.GamePanel;
import main.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public abstract  class Placeable {

    protected OreController oreController;
    protected PlayerController playerController;
    protected GamePanel gamePanel;

    protected BufferedImage image;

    protected String name;
    protected int xPos, yPos;
    protected Map<Item, Integer> craftingCost = new HashMap<>();

    protected Direction direction;

    public Placeable(int x, int y, GamePanel gamePanel, String name, Direction direction) {
        this.gamePanel = gamePanel;

        this.xPos = x*gamePanel.TILESIZE;
        this.yPos = y*gamePanel.TILESIZE;

        this.name = name;
        this.direction = direction;

        this.image = ImageLoader.getImageWithRotation(this.name, this.direction);
    }

    public abstract void update();
    public abstract void draw(Graphics2D g);

    public abstract void setCraftingCost(Item item, int cost, Item item2, int cost2);
    public abstract Map<Item, Integer> getCraftingCost();
    public abstract Map<Item, Integer> getOutputInventory();

    /**
     * Should check if the Item is present in Inventory
     * @return Item
     */
    public abstract Item getOneItem();

    /**
     * Should remove Item from Inventory
     * @param item Item to take
     */
    public abstract void takeItem(Item item);
    public abstract void addItemToInventory(Item item, int amount);
    public abstract Map<Item, Integer> getInventory();

    public BufferedImage getImage(){
        return image;
    }
}
