package map;

import controller.OreController;
import controller.PlayerController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Miner extends Building {

    int xPos, yPos;
    Ore placedOnOre;

    private BufferedImage minerImage;

    int miningAmount;
    int miningSpeed;
    private long lastMineTime = 0;
    int oresMined = 0;
    int fuelConsumption = 3;

    private Map<Item, Integer> inventory = new HashMap<Item, Integer>();

    public Miner(PlayerController playerController, OreController oreController, String name, Ore placedOnOre, int mingingSpeed, int xPos, int yPos) {
        super(playerController, oreController, name);
        miningAmount = 1;
        this.miningSpeed = mingingSpeed;
        this.placedOnOre = placedOnOre;
        this.xPos = xPos;
        this.yPos = yPos;

        try{
             minerImage = ImageIO.read(getClass().getResourceAsStream("/res/Drill.png"));
        }catch(IOException e){
            System.out.println("Miner image not found");
        }

        inventory.put(oreController.coalOre, 0);
    }

    public void update(){
        mineOre();
    }

    private void mineOre() {
        long now = System.currentTimeMillis();

        if (inventory.getOrDefault(oreController.coalOre, 0) <= 0) {
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
                inventory.put(outputOre, inventory.getOrDefault(outputOre, 0) + miningAmount);
                oresMined++;

                if (oresMined % fuelConsumption == 0) {
                    System.out.println("Miner used coal");
                    int coalLeft = inventory.getOrDefault(oreController.coalOre, 0) - 1;
                    inventory.put(oreController.coalOre, Math.max(coalLeft, 0));
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
        inventory.put(ore, inventory.get(ore) + amount);
        System.out.println("coal in Miner: " + inventory.get(oreController.coalOre));
    }

    public void takeOutput(){
        if(oresMined == 0){
            return;
        }

        System.out.println("Taking output: " + placedOnOre + " " + inventory.get(placedOnOre));
        playerController.addItemToInventory(placedOnOre, inventory.get(placedOnOre));
        inventory.put(placedOnOre, 0);
    }

    public Map<Item, Integer> getInventory() {
        return inventory;
    }

    public void draw(Graphics2D g){
        g.drawImage(minerImage, xPos, yPos, null);
    }
}
