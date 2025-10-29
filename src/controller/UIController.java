package controller;

import map.Item;
import map.Miner;

import java.awt.*;
import java.util.Map;

public class UIController {

    public static void drawMinerInventory(Graphics2D g2, Miner miner) {
        int boxX = 32*32 - 250;
        int boxY = 50;
        int boxWidth = 200;
        int boxHeight = 100;

        //Background
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 15, 15);

        g2.setColor(Color.WHITE);
        g2.drawRoundRect(boxX, boxY, boxWidth, boxHeight, 15, 15);

        g2.setFont(new Font("Arial", Font.BOLD, 16));
        g2.drawString("Miner Inventory", boxX + 20, boxY + 25);

        int lineY = boxY + 50;
        for (Map.Entry<Item, Integer> entry : miner.getInventory().entrySet()) {
            g2.drawString(entry.getKey().getName() + ": " + entry.getValue(), boxX + 20, lineY);
            lineY += 20;
        }

        // CloseText
        g2.setFont(new Font("Arial", Font.PLAIN, 12));
        g2.drawString("(Right click elsewhere to close)", boxX + 20, boxY + boxHeight - 10);
    }
}
