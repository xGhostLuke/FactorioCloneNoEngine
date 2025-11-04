package map.items;

import java.awt.image.BufferedImage;

public class Item {

    protected boolean isFuel = false;
    protected String name;
    protected OreType type;
    protected BufferedImage image;

    public Item(String name, BufferedImage image) {
        this.name = name;
        this.type = null;
        this.image = image;
    }

    public String getName(){
        return name;
    }
    public OreType getType(){return type;}
    public boolean getIsFuel(){
        return isFuel;
    }

    public BufferedImage getImage(){
        return image;
    }
}
