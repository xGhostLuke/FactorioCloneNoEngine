package map.items;

import java.awt.image.BufferedImage;

public class Ore extends Item{



    public Ore(String name, OreType type, boolean isFuel, BufferedImage image) {
        super(name, image);
        this.type = type;
        this.isFuel = isFuel;
    }
}
