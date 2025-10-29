package controller;

import map.Item;
import map.Miner;
import map.Ore;

import java.awt.*;
import java.util.Map;

public class InventoryController {

    private PlayerController playerController;
    private int xPos = 50;
    private int yPos = 50;
    private int startYPos = yPos;
    private int yMargin = 20;
    private final int size = 20;


    public InventoryController(PlayerController playerController) {
        this.playerController = playerController;
    }

    public void paintInventory(Graphics2D g2){
        yPos = startYPos;
        String invetoryString;

        g2.setColor(Color.white);

        g2.setFont(new Font("Arial", Font.BOLD, size));

        for (Map.Entry<Item, Integer> entry : playerController.inventory.entrySet()) {
            invetoryString = entry.getKey().getName() + ": " + entry.getValue() + " \n";
            yPos += yMargin;
            g2.drawString(invetoryString, xPos, yPos);
        }
    }



}
