package controller;

import main.ImageLoader;
import map.buildings.*;
import map.items.Item;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Map;
import main.MouseHandler;

public class UIController {

    public static Class<? extends Placeable> selectedBuildingClass;
    private static BufferedImage selectedBuildingImage;

    public static void drawMinerInventory(Graphics2D g2, Building building) {
        int boxX = 32*32 - 250;
        int boxY = 50;
        int boxWidth = 250;
        int boxHeight = 200;

        //Background
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 15, 15);

        g2.setColor(Color.WHITE);
        g2.drawRoundRect(boxX, boxY, boxWidth, boxHeight, 15, 15);

        g2.setFont(new Font("Arial", Font.BOLD, 16));
        g2.drawString(building.getName(), boxX + 20, boxY + 25);

        int lineY = boxY + 50;
        for (Map.Entry<Item, Integer> entry : building.getInputInventory().entrySet()) {
            g2.drawString("Input: " + entry.getKey().getName() + ": " + entry.getValue(), boxX + 20, lineY);
            lineY += 20;
        }

        for (Map.Entry<Item, Integer> entry : building.getOutputInventory().entrySet()) {
            if (entry.getValue() > 0){
                g2.drawString("Output: " + entry.getKey().getName() + ": " + entry.getValue(), boxX + 20, lineY);
                lineY += 20;
            }
        }

        // CloseText
        g2.setFont(new Font("Arial", Font.PLAIN, 12));
        g2.drawString("(Right click elsewhere to close)", boxX + 20, boxY + boxHeight - 10);
    }


    private static class BuildingButton {
        Rectangle bounds;
        String name;
        BufferedImage image;
        Class<? extends Placeable> buildingClass;

        BuildingButton(String name, int x, int y, int size, BufferedImage image, Class<? extends Placeable> cls) {
            this.bounds = new Rectangle(x, y, size, size);
            this.name = name;
            this.image = image;
            this.buildingClass = cls;
        }
    }

    private static ArrayList<BuildingButton> buildingButtons = new ArrayList<>();
    private static BuildingButton selectedButton = null;

    public static void initMenu() {

        try {

            BufferedImage minerImg = ImageLoader.getImage("miner");
            BufferedImage beltImg = ImageLoader.getImage("belt_top");
            BufferedImage furnanceImage = ImageLoader.getImage("furnace");
            BufferedImage crafterImage = ImageLoader.getImage("crafter");

            buildingButtons.add(new BuildingButton("miner", 50, 850, 64, minerImg, Miner.class));
            buildingButtons.add(new BuildingButton("belt", 130, 850, 64, beltImg, Belt.class));
            buildingButtons.add(new BuildingButton("furnace", 210, 850, 64, furnanceImage, Furnace.class));
            buildingButtons.add(new BuildingButton("crafter", 290, 850, 64, crafterImage, Crafter.class));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(MouseHandler mouse) {
        if (!(mouse.mouseX > 30 && mouse.mouseX < 380 &&
                mouse.mouseY > 830 && mouse.mouseY < 930)) {
            return;
        }

        if (mouse.leftClicked) {
            for (BuildingButton btn : buildingButtons) {
                if (btn.bounds.contains(mouse.mouseX, mouse.mouseY)) {
                    selectedButton = btn;
                    System.out.println("Selected building: " + btn.name);
                    setSelectedBuildingImage((BufferedImage) btn.image);
                    setSelectedBuilding(btn.buildingClass);
                }
            }
            mouse.leftClicked = false;
        }
    }

    public static void drawBuildingMenu(Graphics2D g2) {
        g2.setFont(new Font("Arial", Font.BOLD, 14));
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRoundRect(30, 830, 400, 100, 15, 15);

        for (BuildingButton btn : buildingButtons) {
            // Rahmen
            if (btn == selectedButton) {
                g2.setColor(Color.YELLOW);
            } else {
                g2.setColor(Color.WHITE);
            }
            g2.drawRect(btn.bounds.x, btn.bounds.y, btn.bounds.width, btn.bounds.height);

            // Bild
            g2.drawImage(btn.image, btn.bounds.x, btn.bounds.y, btn.bounds.width, btn.bounds.height, null);

            // Text
            g2.setColor(Color.WHITE);
            g2.drawString(btn.name, btn.bounds.x, btn.bounds.y + btn.bounds.height + 15);
        }
    }

    public static void setSelectedBuilding(Class<? extends Placeable> buildingClass) {
        selectedBuildingClass = buildingClass;
    }

    public static Class<? extends Placeable> getSelectedBuilding() {
        return selectedBuildingClass;
    }

    public static BufferedImage getSelectedBuildingImage(Direction dir) {
        //System.out.println(selectedButton.name + "_"+ dir.name().toLowerCase());
        return ImageLoader.getImage(selectedButton.name + "_"+ dir.name().toLowerCase());
    }

    public static void setSelectedBuildingImage(BufferedImage image) {
        selectedBuildingImage = image;
    }
}
