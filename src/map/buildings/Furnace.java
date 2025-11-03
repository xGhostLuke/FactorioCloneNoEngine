package map.buildings;

import main.ImageLoader;
import main.ItemMananger;
import controller.PlayerController;
import main.GamePanel;
import map.items.Item;

import java.awt.*;

public class Furnace extends Building {

    private long lastTimeCrafted = 0;
    int craftingSpeed;
    int craftedItems;

    public Furnace(PlayerController playerController, String name, GamePanel gamePanel, int x, int y, Direction direction, int craftingSpeed) {
        super(playerController, name, gamePanel, x, y, direction);
        this.craftingSpeed = craftingSpeed;

        setCraftingCost(ItemMananger.stoneOre, 2, ItemMananger.ironOre, 5);
    }

    @Override
    public void update() {
        craft();
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(image, xPos, yPos, null);
    }

    /**
     * produces an Item based on factors
     * is enough coal in inventory
     * is enough item1 in inventory
     */
    public void craft(){
        long now = System.currentTimeMillis();

        Item itemNeeded1 = ItemMananger.copperOre;
        int neededCopperOre = 1;
        Item coal = ItemMananger.coalOre;
        int coalConsumption = 5;
        Item itemToCraft = ItemMananger.copperPlate;

        //This should be done for every Item the recipe needs
        if (!(inputInventory.containsKey(itemNeeded1) && inputInventory.containsKey(coal))) {
            return;
        }
        //same here
        if (!canCraft(itemNeeded1, neededCopperOre, coal, 1)){
            return;
        }

        if (now - lastTimeCrafted >= craftingSpeed) {
            craftedItems++;
            inputInventory.put(itemNeeded1, inputInventory.get(itemNeeded1) - neededCopperOre);

            if (craftedItems != 0 && (craftedItems % coalConsumption == 0)) {
                inputInventory.put(coal, inputInventory.get(coal) - 1);
            }

            if(!outputInventory.containsKey(itemToCraft)){
                outputInventory.put(itemToCraft, 0);
            }

            outputInventory.put(itemToCraft, outputInventory.get(itemToCraft) + 1);
            lastTimeCrafted = now;
        }
    }

    /**
     * checks if items are in inventory
     * @param input1
     * @param amount1
     * @param input2
     * @param amount2
     * @return boolean
     */
    private boolean canCraft(Item input1, int amount1, Item input2, int amount2) {
        return inputInventory.getOrDefault(input1, 0) >= amount1 &&
                inputInventory.getOrDefault(input2, 0) >= amount2;
    }


    /**
     * adds item to input inventory
     * @param item item to add
     * @param amount int amount
     */
    @Override
    public void addItemToInventory(Item item, int amount) {

        if(!inputInventory.containsKey(item)){
            inputInventory.put(item, amount);
            return;
        }

        inputInventory.put(item, inputInventory.get(item) + amount);
    }
}
