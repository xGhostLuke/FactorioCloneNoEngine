package map.buildings;

import main.ImageLoader;
import main.ItemMananger;
import controller.PlayerController;
import main.GamePanel;
import map.items.Item;

import java.awt.*;

public class Crafter extends Building {

    private long lastTimeCrafted = 0;
    private int craftingSpeed;
    private int craftedItems;

    private Item currentCraftingItem;


    public Crafter(PlayerController playerController, String name, GamePanel gamePanel, int x, int y, Direction direction, int craftingSpeed) {
        super(playerController, name, gamePanel, x, y, direction);
        this.craftingSpeed = craftingSpeed;

        setCraftingCost(ItemMananger.copperPlate, 5, ItemMananger.stoneOre, 5);
    }

    @Override
    public void update() {
        craft();
    }

    /**
     * produces an Item based on factors
     * is enough coal in inventory
     * is enough item1 in inventory
     */
    public void craft(){
        long now = System.currentTimeMillis();

        checkWhatItemToCraft();

        if(currentCraftingItem == null){
            return;
        }

        Item itemNeeded1 = null;
        Item itemNeeded2 = null;
        int amountItem1 = 0, amountItem2 = 0;
        if(currentCraftingItem.equals(ItemMananger.copperWire)){
            itemNeeded1 = ItemMananger.copperPlate;
            amountItem1 = 2;
            itemNeeded2 = null;
        }
        if(currentCraftingItem.equals(ItemMananger.smallChip)){
            itemNeeded1 = ItemMananger.copperWire;
            amountItem1 = 3;
            itemNeeded2 = ItemMananger.ironPlate;
            amountItem2 = 3;
        }

        System.out.println(currentCraftingItem.getName());

        if(itemNeeded1 == null){
            return;
        }

        Item itemToCraft = currentCraftingItem;

        if (!(inputInventory.containsKey(itemNeeded1))) {
            return;
        }

        if(itemNeeded2 != null && !(inputInventory.containsKey(itemNeeded2))){
            System.out.println("WEIRD");
            return;
        }

        if (!canCraft(itemNeeded1, amountItem1, itemNeeded2, amountItem2)){
            System.out.println("NOT ENOUGH ReS");
            return;
        }

        if (now - lastTimeCrafted >= craftingSpeed) {
            craftedItems++;
            inputInventory.put(itemNeeded1, inputInventory.get(itemNeeded1) - amountItem1);
            if(itemNeeded2 != null){
                inputInventory.put(itemNeeded2, inputInventory.get(itemNeeded2) - amountItem2);
            }


            if(!outputInventory.containsKey(itemToCraft)){
                outputInventory.put(itemToCraft, 0);
            }

            outputInventory.put(itemToCraft, outputInventory.get(itemToCraft) + 1);
            System.out.println("crafted: " + itemToCraft.getName());
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
        if(input2 != null) {
            return inputInventory.getOrDefault(input1, 0) >= amount1 &&
                    inputInventory.getOrDefault(input2, 0) >= amount2;
        }

        return inputInventory.getOrDefault(input1, 0) >= amount1;
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

    /**
     * this method should check what Item got started crafting first
     * ex. when furnace is supplied with different ores it should craft
     * copper plates until no copper ore is in the furnace
     */
    public void checkWhatItemToCraft(){
        currentCraftingItem = null;

        if(inputInventory.containsKey(ItemMananger.copperWire) && inputInventory.containsKey(ItemMananger.ironPlate)){
            if(inputInventory.get(ItemMananger.copperWire) < 1){
                return;
            }
            if(inputInventory.get(ItemMananger.ironPlate) < 1){
                return;
            }
            currentCraftingItem = ItemMananger.smallChip;
        }

        if(inputInventory.containsKey(ItemMananger.copperPlate)){
            if(inputInventory.get(ItemMananger.copperPlate) < 1){
                return;
            }
            currentCraftingItem = ItemMananger.copperWire;
        }
    }
}
