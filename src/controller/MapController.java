package controller;

import main.GamePanel;
import main.ImageLoader;
import main.ItemMananger;
import map.items.Ore;
import map.items.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class MapController {

    private final GamePanel gamePanel;
    private final int size;
    int tilesPerRow;

    private BufferedImage grasTileImage = ImageLoader.getImage("dirt");
    private BufferedImage copperTileImage = ImageLoader.getImage("copperOre");
    private BufferedImage stoneTileImage = ImageLoader.getImage("stoneOre");
    private BufferedImage coalTileImage = ImageLoader.getImage("coalOre");


    private ArrayList<Tile> tileArrayList = new ArrayList<>();

    private ItemMananger itemMananger;

    public MapController(GamePanel gamePanel, int size) {
        this.size = size*gamePanel.TILESIZE;
        this.gamePanel = gamePanel;
        this.tilesPerRow = size;

        this.itemMananger = itemMananger;

        generateMap(this.size);
    }

    private void generateMap(int size){
        for(int x = 0; x < size; x += gamePanel.TILESIZE){
            for(int y = 0; y < size; y += gamePanel.TILESIZE){
                Tile tile = new Tile (x,y);
                tileArrayList.add(tile);
                tile.setOreOnTile(itemMananger.noOre);
            }
        }

        generateOreCluster(itemMananger.copperOre, 2);
        generateOreCluster(itemMananger.copperOre, 3);
        generateOreCluster(itemMananger.coalOre,5);
        generateOreCluster(itemMananger.stoneOre,2);
        generateOreCluster(itemMananger.stoneOre,2);
    }

    public void renderMap(Graphics2D g2){
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);


        for (Tile tile : tileArrayList){

            Ore ore = tile.getOreOnTile();
            int x = tile.getX();
            int y = tile.getY();

            switch (ore.getType()){
                case NONE:
                    g2.drawImage(grasTileImage, x, y, null);
                    break;
                case COPPER:
                    g2.drawImage(copperTileImage, x, y, null);
                    break;
                    case STONE:
                        g2.drawImage(stoneTileImage, x, y, null);
                    break;
                case COAL:
                    g2.drawImage(coalTileImage, x, y, null);
                    break;
                default:
                    break;
            }
        }
    }

    private void generateOreCluster(Ore ore, int maxClusterSize) {

        int startX = (int) (Math.random() *tilesPerRow);
        int startY = (int) (Math.random() * tilesPerRow);

        int clusterRadius = (int) (Math.random() * maxClusterSize) + 1;

        for (int dx = -clusterRadius; dx <= clusterRadius; dx++) {
            for (int dy = -clusterRadius; dy <= clusterRadius; dy++) {
                int tileX = startX + dx;
                int tileY = startY + dy;

                if (tileX < 0 || tileY < 0 || tileX >= tilesPerRow || tileY >= tilesPerRow){
                    continue;
                }

                Tile tile = getTile(tileX, tileY);
                tile.setOreOnTile(ore);
            }
        }
    }

    public Tile  getTile(int x, int y){
        try{
            return tileArrayList.get(x * tilesPerRow + y);
        }catch (IndexOutOfBoundsException e){
            System.out.println("Tile not in Arraybounds");
        }
        return null;
    }
}
