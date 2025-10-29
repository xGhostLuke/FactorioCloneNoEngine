package map;

import controller.PlayerController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Miner extends Building {


    int miningSpeed;
    Ore placedOnOre;
    private long lastMineTime = 0;
    int xPos, yPos;

    private BufferedImage minerImage;

    public Miner(PlayerController playerController, String name, Ore placedOnOre, int mingingSpeed, int xPos, int yPos) {
        super(playerController, name);
        this.miningSpeed = mingingSpeed;
        this.placedOnOre = placedOnOre;
        this.xPos = xPos;
        this.yPos = yPos;

        try{
             minerImage = ImageIO.read(getClass().getResourceAsStream("/res/Drill.png"));
        }catch(IOException e){
            System.out.println("Miner image not found");
        }
    }

    public void update(){
        mineOre();
    }

    private void mineOre(){
        long now = System.currentTimeMillis();

        if (now - lastMineTime >= miningSpeed) {
            OreType type = placedOnOre.type;
            switch (type) {
                case COPPER:
                    playerController.addItemToInventory(new Ore(OreType.COPPER), 1);
                    break;
                case STONE:
                    playerController.addItemToInventory(new Ore(OreType.STONE), 1);
                    break;
                default:
                    break;
            }
            lastMineTime = now;
        }
    }

    public void draw(Graphics2D g){
        g.drawImage(minerImage, xPos, yPos, null);
    }
}
