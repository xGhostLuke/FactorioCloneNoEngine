package controller;

import main.GamePanel;
import main.KeyHandler;
import main.MouseHandler;
import map.*;

import java.util.HashMap;
import java.util.Map;

public class PlayerController {

    private final GamePanel gamePanel;
    private final OreController oreController;
    private final MapController mapGen;
    private final MouseHandler mouseHandler;
    private final KeyHandler keyHandler;

    private long mousePressedStartTime = 0;
    private final long playerMiningDuration = 1000;

    public Map<Item, Integer> inventory = new HashMap<>();

    public Miner activeMinerInventory = null;

    public PlayerController(GamePanel gamePanel, KeyHandler keyHandler, MouseHandler mouseHandler, MapController mapGen) {
        this.gamePanel = gamePanel;
        this.oreController = gamePanel.oreController;
        this.keyHandler = keyHandler;
        this.mouseHandler = mouseHandler;
        this.mapGen = mapGen;

        inventory.put(oreController.coalOre, 100);
        inventory.put(oreController.stoneOre, 100);
        inventory.put(oreController.copperOre, 100);
        inventory.put(oreController.ironOre, 100);
    }

    public void update(){
        playerMining();
        playerBuilding();
    }

    public void addItemToInventory(Ore ore, int amount){
        if(inventory.containsKey(ore)){
            inventory.put(ore, inventory.get(ore) + amount);
            System.out.println(ore.type.toString() + " increased by " + amount);
            return;
        }
        inventory.put(ore, amount);

        System.out.println(ore.type.toString() + " added to the inventory");
        System.out.println(inventory.get(ore) + " now in inventory");
    }

    private void playerBuilding(){
        int tileX = mouseHandler.mouseX / gamePanel.TILESIZE;
        int tileY = mouseHandler.mouseY / gamePanel.TILESIZE;

        Tile clickedTile = mapGen.getTile(tileX, tileY);

        //This is just for testing the belts and should be removes
        if(keyHandler.placingBelt){
            if(mouseHandler.leftClicked){
                mouseHandler.leftClicked = false;

                if(clickedTile.getBuildingOnTile() != null && keyHandler.placingBelt){
                    System.out.println("There is already a Building on this Tile!");
                    return;
                }

                Placeable newBuilding = new Belt(tileX, tileY, gamePanel, mapGen, "belt");

                boolean canAfford = true;

                for (Map.Entry<Item, Integer> cost : newBuilding.getCraftingCost().entrySet()) {
                    Item item = cost.getKey();
                    int needed = cost.getValue();

                    if (!inventory.containsKey(item) || inventory.get(item) < needed) {
                        canAfford = false;
                        break;
                    }
                }

                if (canAfford) {
                    for (Map.Entry<Item, Integer> cost : newBuilding.getCraftingCost().entrySet()) {
                        Item item = cost.getKey();
                        int needed = cost.getValue();
                        inventory.put(item, inventory.get(item) - needed);
                    }

                    clickedTile.setBuildingOnTile(newBuilding);
                    gamePanel.buildingList.add(newBuilding);
                    System.out.println("Belt placed on " + clickedTile.getOreOnTile());
                } else {
                    System.out.println("Not enough resources!");
                }
            }
        }


        if (keyHandler.inBuildMode){

            if(mouseHandler.leftClicked){
                mouseHandler.leftClicked = false;

                if(clickedTile.getBuildingOnTile() != null && keyHandler.inBuildMode){
                    System.out.println("There is already a Building on this Tile!");
                    return;
                }

                Building newBuilding = new Miner(this,oreController ,"miner", clickedTile.getOreOnTile(),
                                    2000, tileX, tileY, gamePanel);

                boolean canAfford = true;

                for (Map.Entry<Item, Integer> cost : newBuilding.getCraftingCost().entrySet()) {
                    Item item = cost.getKey();
                    int needed = cost.getValue();

                    if (!inventory.containsKey(item) || inventory.get(item) < needed) {
                        canAfford = false;
                        break;
                    }
                }

                if (canAfford) {
                    for (Map.Entry<Item, Integer> cost : newBuilding.getCraftingCost().entrySet()) {
                        Item item = cost.getKey();
                        int needed = cost.getValue();
                        inventory.put(item, inventory.get(item) - needed);
                    }

                    clickedTile.setBuildingOnTile(newBuilding);
                    gamePanel.buildingList.add(newBuilding);
                    System.out.println("Miner placed on " + clickedTile.getOreOnTile());
                } else {
                    System.out.println("Not enough resources!");
                }
            }
        }

        if(mouseHandler.rightClicked){
            if(clickedTile.getBuildingOnTile() instanceof Miner miner){
                if(activeMinerInventory == miner){
                    miner.takeOutput();
                }
                System.out.println("Opened Miner Inventory");
            }
        }

        if(mouseHandler.rightClicked){
            if(clickedTile.getBuildingOnTile() instanceof Miner miner){
                activeMinerInventory = miner;
                System.out.println("Opened Miner Inventory");
            }
            if(clickedTile.getBuildingOnTile() == null){
                activeMinerInventory = null;
            }

            mouseHandler.rightClicked = false;
            return;
        }

        if(mouseHandler.leftClicked && clickedTile.getBuildingOnTile() instanceof Miner miner){
            if (inventory.get(oreController.coalOre) > 0){
                miner.addFuel(oreController.coalOre, 1);
                inventory.put(oreController.coalOre, inventory.get(oreController.coalOre) - 1);
                mouseHandler.leftClicked = false;
                return;
            }
        }
    }

    private void playerMining(){
        int tileX = mouseHandler.mouseX / gamePanel.TILESIZE;
        int tileY = mouseHandler.mouseY / gamePanel.TILESIZE;

        Tile clickedTile = mapGen.getTile(tileX, tileY);

        if(mouseHandler.leftPressed){
            mouseHandler.leftClicked = false;
            if(mousePressedStartTime == 0){
                mousePressedStartTime = System.currentTimeMillis();
            } else {
                long heldTime = System.currentTimeMillis() - mousePressedStartTime;

                if(heldTime >= playerMiningDuration){
                    addItemToInventory(clickedTile.getOreOnTile(), 1);
                    mousePressedStartTime = 0;
                }
            }
        } else {
            mousePressedStartTime = 0;
        }
    }


}
