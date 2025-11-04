package map.buildings;

import controller.PlayerController;
import main.GamePanel;
import map.items.Item;
import map.items.Ore;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public abstract class Building extends Placeable {

    protected Map<Item, Integer> inputInventory = new HashMap<Item, Integer>();
    protected Map<Item, Integer> outputInventory = new HashMap<Item, Integer>();

    public Building(PlayerController playerController, String name, GamePanel gamePanel, int x, int y, Direction direction) {
        super(x, y, gamePanel, name, direction);
        this.playerController = playerController;
    }

    /**
     * Method to add a random item stack from output inventory and add it to player inv
     */
    public void takeOutput(){
        Item item = getOneItem();

        if (outputInventory.isEmpty()){
            return;
        }

        playerController.addItemToInventory(item, outputInventory.get(item));
        System.out.println("Taking output: " + item  + " " + outputInventory.get(item));
        outputInventory.put(item, 0);
    }

    public void takeItem(Item item, int amount){
        if (!outputInventory.containsKey(item)){
            return;
        }

        if (outputInventory.get(item) < amount){
            return;
        }

        outputInventory.put(item, outputInventory.get(item) - amount);
    }

    public Map<Item, Integer> getInventory() {
        Map<Item, Integer> out = new HashMap<Item, Integer>();
        out.putAll(inputInventory);
        out.putAll(outputInventory);
        return out;
    }

    @Override
    public void draw(Graphics2D g, int cameraX, int cameraY){
        g.drawImage(image, xPos - cameraX, yPos - cameraY, null);
    }

    @Override
    public void setCraftingCost(Item item, int cost, Item item2, int cost2) {
        craftingCost.put(item, cost);
        craftingCost.put(item2, cost2);
    }

    @Override
    public Map<Item, Integer> getCraftingCost() {
        return craftingCost;
    }

    public Map<Item, Integer> getOutputInventory() {
        return outputInventory;
    }

    public Map<Item, Integer> getInputInventory() {
        return inputInventory;
    }

    @Override
    public void addItemToInventory(Item item, int amount) {

        if(item.getIsFuel()){
            if(!inputInventory.containsKey(item)){
                inputInventory.put(item, amount);
                return;
            }

            inputInventory.put(item, inputInventory.get(item) + amount);
            return;
        }

        if(!outputInventory.containsKey(item)){
            outputInventory.put(item, amount);
            return;
        }

        outputInventory.put(item, outputInventory.get(item) + amount);


    }

    @Override
    public Item getOneItem() {
        if (outputInventory.isEmpty()){
            return null;
        }

        return outputInventory.entrySet().iterator().next().getKey();
    }

    public void addFuel(Ore ore, int amount){
        if(!ore.getIsFuel()){
            System.out.println("Ore wasnt fueling Ore");
            return;
        }
        System.out.println("Ore was fueling Ore");

        if(!inputInventory.containsKey(ore)){
            inputInventory.put(ore, amount);
        }

        inputInventory.put(ore, inputInventory.get(ore) + amount);
        System.out.println("coal in Miner: " + inputInventory.get(itemMananger.coalOre));
    }
}
