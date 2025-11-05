package map.buildings;

import controller.PlayerController;
import main.GamePanel;
import main.ImageLoader;
import map.items.Item;
import map.items.Ore;
import map.items.OreType;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Miner extends Building {

    Ore placedOnOre;
    int miningAmount;
    int miningSpeed;
    private long lastMineTime = 0;
    int oresMined = 0;
    int fuelConsumption = 3;


    //Mining Animation
    private boolean isMining = false;
    private int miningFrame = 0;
    private long lastFrameTime = 0;
    private long frameDelay = 100; // Zeit zwischen Frames in ms
    private int totalFrames = 10;

    public Miner(PlayerController playerController, String name, Ore placedOnOre, int mingingSpeed, int xPos, int yPos, GamePanel gamePanel, Direction direction) {
        super(playerController, name, gamePanel, xPos, yPos, direction);
        miningAmount = 1;
        this.miningSpeed = mingingSpeed;
        this.placedOnOre = placedOnOre;

        setCraftingCost(itemMananger.stoneOre, 20, itemMananger.copperOre, 10);

        inputInventory.put(itemMananger.coalOre, 0);
    }

    public void update(){
        mineOre();
    }

    private void mineOre() {
        long now = System.currentTimeMillis();

        if (inputInventory.getOrDefault(itemMananger.coalOre, 0) <= 0) {
            return;
        }
        isMining = true;
        if (now - lastMineTime >= miningSpeed) {
            OreType type = placedOnOre.getType();
            Ore outputOre = switch (type) {
                case COPPER -> itemMananger.copperOre;
                case STONE -> itemMananger.stoneOre;
                case COAL -> itemMananger.coalOre;
                case IRON -> itemMananger.ironOre;
                default -> null;
            };

            if (outputOre != null) {

                System.out.println("Miner mined Ore");
                outputInventory.put(outputOre, outputInventory.getOrDefault(outputOre, 0) + miningAmount);
                oresMined++;

                if (oresMined % fuelConsumption == 0) {
                    System.out.println("Miner used coal");
                    int coalLeft = inputInventory.getOrDefault(itemMananger.coalOre, 0) - 1;
                    inputInventory.put(itemMananger.coalOre, Math.max(coalLeft, 0));
                }
            }
            isMining = false;
            lastMineTime = now;
        }
    }

    //if the miner isnt placed on any ore you cant take the output if it got some through transportation
    @Override
    public Item getOneItem() {
        if (placedOnOre.getType() != OreType.NONE){
            return placedOnOre;
        }

        if (outputInventory.isEmpty()){
            return null;
        }

        return outputInventory.entrySet().iterator().next().getKey();
    }


    @Override
    public void draw(Graphics2D g, int cameraX, int cameraY){
        if(!isMining){
            g.drawImage(image, xPos - cameraX, yPos - cameraY, null);
            return;
        }
        playAnimationMining(g, cameraX, cameraY);
    }

    private void playAnimationMining(Graphics2D g, int cameraX, int cameraY) {
        long now = System.currentTimeMillis();

        if (now - lastFrameTime >= frameDelay) {
            miningFrame++;
            if (miningFrame >= totalFrames) {
                miningFrame = 1;
            }
            lastFrameTime = now;
        }

        BufferedImage frame = ImageLoader.getImage("minerMining" + miningFrame);
        g.drawImage(frame, xPos - cameraX, yPos - cameraY, null);
    }
}
