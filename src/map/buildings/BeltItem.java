package map.buildings;

import main.ImageLoader;
import map.items.Item;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BeltItem {

    private Item item;
    private BufferedImage image;
    private double progress;

    public BeltItem(Item item, String itemName) {
        this.item = item;
        this.progress = 0;
        image = ImageLoader.getImage(itemName);
        System.out.println(itemName);
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
