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

        inventory.put(oreController.coalOre, 1000);
        inventory.put(oreController.stoneOre, 1000);
        inventory.put(oreController.copperOre, 1000);
        inventory.put(oreController.ironOre, 1000);
    }

    public void update(){
        playerMining();
        playerBuilding();
    }

    public void addItemToInventory(Item item, int amount){
        if(inventory.containsKey(item)){
            inventory.put(item, inventory.get(item) + amount);
            System.out.println(item.getType().toString() + " increased by " + amount);
            return;
        }
        inventory.put(item, amount);

        System.out.println(item.getType().toString() + " added to the inventory");
        System.out.println(inventory.get(item) + " now in inventory");
    }

    private void playerBuilding(){
        int tileX = mouseHandler.mouseX / gamePanel.TILESIZE;
        int tileY = mouseHandler.mouseY / gamePanel.TILESIZE;

        Tile clickedTile = mapGen.getTile(tileX, tileY);

        if (keyHandler.inBuildMode) {
            if (mouseHandler.leftClicked) {
                mouseHandler.leftClicked = false;

                Class<? extends Placeable> buildingClass = UIController.getSelectedBuilding();
                if (buildingClass == null) {
                    System.out.println("No Building selected!");
                    return;
                }

                clickedTile = mapGen.getTile(tileX, tileY);
                if (clickedTile == null) return;

                if (clickedTile.getBuildingOnTile() != null) {
                    System.out.println("Already Building on Tile!");
                    return;
                }

                Placeable newBuilding = null;
                System.out.println(clickedTile.getOreOnTile().getType().name());
                try {
                    if (buildingClass == Miner.class) {
                        newBuilding = new Miner(this, oreController, "miner", clickedTile.getOreOnTile(),
                                2000, tileX, tileY, gamePanel, keyHandler.getDirection());
                    } else if (buildingClass == Belt.class) {
                        newBuilding = new Belt(tileX, tileY, gamePanel, mapGen, "belt", keyHandler.getDirection());
                    } else {
                        System.out.println("Unknown building type!");
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }

                boolean canAfford = true;
                for (Map.Entry<Item, Integer> cost : newBuilding.getCraftingCost().entrySet()) {
                    Item item = cost.getKey();
                    int needed = cost.getValue();
                    if (!inventory.containsKey(item) || inventory.get(item) < needed) {
                        canAfford = false;
                        break;
                    }
                }

                if (!canAfford) {
                    System.out.println("Not enough resources!");
                    return;
                }

                for (Map.Entry<Item, Integer> cost : newBuilding.getCraftingCost().entrySet()) {
                    Item item = cost.getKey();
                    int needed = cost.getValue();
                    inventory.put(item, inventory.get(item) - needed);
                }

                clickedTile.setBuildingOnTile(newBuilding);
                gamePanel.buildingList.add(newBuilding);
                System.out.println("Building placed " + newBuilding.getClass().getSimpleName());
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
