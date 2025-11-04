package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;

public class Player extends Entity {

    GamePanel gamePanel;
    KeyHandler keyHandler;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        setDefaultValues();
    }

    public void setDefaultValues(){

        x = gamePanel.mapGen.size / 2;
        y = gamePanel.mapGen.size / 2;
        speed = 4;
    }

    public void update(){
        if(keyHandler.upPressed){
            y -= speed;
        }
        else if(keyHandler.downPressed){
            y += speed;
        }
        else if(keyHandler.leftPressed){
            x -= speed;
        }
        else if(keyHandler.rightPressed){
            x += speed;
        }

        //System.out.println("x " + x + "     " +y );
    }

    public void draw(Graphics2D g2){
        int screenX = x - gamePanel.cameraX;
        int screenY = y - gamePanel.cameraY;

        g2.setColor(Color.white);
        g2.fillRect(screenX, screenY, gamePanel.TILESIZE, gamePanel.TILESIZE);
    }


}
