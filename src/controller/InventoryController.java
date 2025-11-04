package controller;

import map.items.Item;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferDouble;
import java.util.Map;

public class InventoryController {

    private PlayerController playerController;
    private int xPos = 50;
    private int yPos = 50;
    private int startYPos = yPos;
    private int yMargin = 25;
    private int xMargin = 30;
    private final int size = 20;

    public BufferedImage[] imageArray;

    public InventoryController(PlayerController playerController) {
        this.playerController = playerController;

        imageArray = new BufferedImage[playerController.inventory.size()];
    }

    public void paintInventory(Graphics2D g2){
        yPos = startYPos;
        String invetoryString;

        g2.setColor(Color.white);

        g2.setFont(new Font("Arial", Font.BOLD, size));

        for (Map.Entry<Item, Integer> entry : playerController.inventory.entrySet()) {
            g2.drawImage(entry.getKey().getImage(), xPos, yPos, null);
            invetoryString = entry.getValue() + " \n";
            yPos += yMargin;
            g2.drawString(invetoryString, xPos+xMargin, yPos);
        }
    }



}
