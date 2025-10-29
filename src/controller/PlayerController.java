package controller;

import main.GamePanel;
import main.KeyHandler;
import main.MouseHandler;
import map.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PlayerController {

    private final GamePanel gamePanel;
    private final MapController mapGen;
    private final MouseHandler mouseHandler;
    private final KeyHandler keyHandler;

    private long mousePressedStartTime = 0;
    private final long playerMiningDuration = 1000;

    public Map<String, Integer> inventory = new HashMap<>();

    public PlayerController(GamePanel gamePanel, KeyHandler keyHandler, MouseHandler mouseHandler, MapController mapGen) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        this.mouseHandler = mouseHandler;
        this.mapGen = mapGen;
    }

    public void update(){
        playerMining();
        playerBuilding();
    }

    public void addItemToInventory(Ore ore, int amount){
        if(inventory.containsKey(ore.type.toString())){
            inventory.put(ore.type.toString(), inventory.get(ore.type.toString()) + amount);
            System.out.println(ore.type.toString() + " increased by " + amount);
            return;
        }
        inventory.put(ore.type.toString(), amount);

        System.out.println(ore.type.toString() + " added to the inventory");
        System.out.println(inventory.get(ore.type.toString()) + " now in inventory");
    }

    private void playerBuilding(){
        if (keyHandler.inBuildMode){
            int tileX = mouseHandler.mouseX / gamePanel.tileSize;
            int tileY = mouseHandler.mouseY / gamePanel.tileSize;

            Tile clickedTile = mapGen.getTile(tileX, tileY);

            if(mouseHandler.leftClicked){

                if(clickedTile.getBuildingOnTile() != null){
                    System.out.println("There is already a Building on this Tile!");
                    mouseHandler.leftClicked = false;
                    return;
                }

                Building newBuilding = new Miner(this, "miner", clickedTile.getOreOnTile(),
                                    2000, tileX* gamePanel.tileSize, tileY* gamePanel.tileSize);
                clickedTile.setBuildingOnTile(newBuilding);
                gamePanel.buildingList.add(newBuilding);
                System.out.println("Miner Placed on " + clickedTile.getOreOnTile());
                mouseHandler.leftClicked = false;
            }
        }
    }

    private void playerMining(){
        int tileX = mouseHandler.mouseX / gamePanel.tileSize;
        int tileY = mouseHandler.mouseY / gamePanel.tileSize;

        Tile clickedTile = mapGen.getTile(tileX, tileY);

        if(mouseHandler.leftPressed){
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
