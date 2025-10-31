package map;

import controller.UIController;
import main.GameObject;
import main.GamePanel;
import main.KeyHandler;
import main.MouseHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BuildingPreview {

    GamePanel gamePanel;
    KeyHandler keyHandler;
    MouseHandler mouseHandler;

    public BuildingPreview(GamePanel gamePanel, KeyHandler keyHandler, MouseHandler mouseHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        this.mouseHandler = mouseHandler;
    }

    public void draw(Graphics2D g2) throws InstantiationException, IllegalAccessException {
        if (keyHandler.inBuildMode) {
            if(UIController.getSelectedBuilding() == null) {
                return;
            }

            int tileX = (mouseHandler.mouseX / gamePanel.TILESIZE) * gamePanel.TILESIZE;
            int tileY = (mouseHandler.mouseY / gamePanel.TILESIZE) * gamePanel.TILESIZE;

            BufferedImage previewImage = UIController.getSelectedBuildingImage();
            if (previewImage != null) {
                // Halbe Transparenz
                AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
                g2.setComposite(ac);

                g2.drawImage(previewImage, tileX, tileY, gamePanel.TILESIZE, gamePanel.TILESIZE, null);

                // Transparenz zurücksetzen
                g2.setComposite(AlphaComposite.SrcOver);
            }
        }
    }
}
