package map;

import controller.OreController;
import controller.PlayerController;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Miner extends Building {


    Ore placedOnOre;



    int miningAmount;
    int miningSpeed;
    private long lastMineTime = 0;
    int oresMined = 0;
    int fuelConsumption = 3;

    private Map<Item, Integer> inputInventory = new HashMap<Item, Integer>();
    private Map<Item, Integer> outputInventory = new HashMap<Item, Integer>();

    public Miner(PlayerController playerController, OreController oreController, String name, Ore placedOnOre, int mingingSpeed, int xPos, int yPos, GamePanel gamePanel, Direction direction) {
        super(playerController, oreController, name, gamePanel, xPos, yPos, direction);
        miningAmount = 1;
        this.miningSpeed = mingingSpeed;
        this.placedOnOre = placedOnOre;

        setCraftingCost(oreController.stoneOre, 20, oreController.copperOre, 10);

        inputInventory.put(oreController.coalOre, 0);
    }

    public void update(){
        mineOre();
    }

    private void mineOre() {
        long now = System.currentTimeMillis();

        if (inputInventory.getOrDefault(oreController.coalOre, 0) <= 0) {
            return;
        }

        if (now - lastMineTime >= miningSpeed) {
            OreType type = placedOnOre.type;
            Ore outputOre = switch (type) {
                case COPPER -> oreController.copperOre;
                case STONE -> oreController.stoneOre;
                case COAL -> oreController.coalOre;
                case IRON -> oreController.ironOre;
                default -> null;
            };

            if (outputOre != null) {
                System.out.println("Miner mined Ore");
                outputInventory.put(outputOre, outputInventory.getOrDefault(outputOre, 0) + miningAmount);
                oresMined++;

                if (oresMined % fuelConsumption == 0) {
                    System.out.println("Miner used coal");
                    int coalLeft = inputInventory.getOrDefault(oreController.coalOre, 0) - 1;
                    inputInventory.put(oreController.coalOre, Math.max(coalLeft, 0));
                }
            }
            lastMineTime = now;
        }
    }


    public void addFuel(Ore ore, int amount){
        if(!ore.isFuel){
            System.out.println("Ore wasnt fueling Ore");
            return;
        }
        System.out.println("Ore was fueling Ore");
        inputInventory.put(ore, inputInventory.get(ore) + amount);
        System.out.println("coal in Miner: " + inputInventory.get(oreController.coalOre));
    }

    public void takeOutput(){
        Item item = getOneItem();

        if (outputInventory.isEmpty()){
            return;
        }

        playerController.addItemToInventory(item, outputInventory.get(item));
        System.out.println("Taking output: " + item  + " " + outputInventory.get(item));
        outputInventory.put(item, 0);
    }

    public void takeItem(Item item){
        if (!outputInventory.containsKey(item)){
            return;
        }

        if (outputInventory.get(item) < 1){
            return;
        }

        outputInventory.put(item, outputInventory.get(item) - 1);
    }

    public Map<Item, Integer> getInventory() {
        Map<Item, Integer> out = new HashMap<Item, Integer>();
        out.putAll(inputInventory);
        out.putAll(outputInventory);
        return out;
    }

    @Override
    public void draw(Graphics2D g){
        g.drawImage(image, xPos, yPos, null);
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

        if(item.isFuel){
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


    //if the miner isnt placed on any ore you cant take the output if it got some through transportation
    @Override
    public Item getOneItem() {
        if (placedOnOre.type != OreType.NONE){
            return placedOnOre;
        }

        if (outputInventory.isEmpty()){
            return null;
        }
            return outputInventory.entrySet().iterator().next().getKey();
    }
}
