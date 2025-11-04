package controller;

import main.GamePanel;
import main.ImageLoader;
import main.ItemMananger;
import map.items.Ore;
import map.items.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MapController {

    private final GamePanel gamePanel;
    public final int size;
    int tilesPerRow;

    private BufferedImage grasTileImage = ImageLoader.getImage("dirt_tile");
    private BufferedImage copperTileImage = ImageLoader.getImage("copper_tile");
    private BufferedImage stoneTileImage = ImageLoader.getImage("stone_tile");
    private BufferedImage coalTileImage = ImageLoader.getImage("coal_tile");
    private BufferedImage ironTileImage = ImageLoader.getImage("iron_tile");

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


        for (int i = 0; i < (Math.random() * 10000) + 1000; i++){
            if (i%4==0){
                generateOreCluster(ItemMananger.copperOre, 2);
            }
            if (i%6==0){
                generateOreCluster(ItemMananger.coalOre,5);
            }
            if (i%6==0){
                generateOreCluster(ItemMananger.stoneOre,2);
            }
            if (i%8==0){
                generateOreCluster(ItemMananger.ironOre,2);
            }
        }
    }

    public void renderMap(Graphics2D g2, int xCam, int yCam){
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);


        int startCol = xCam / gamePanel.TILESIZE;
        int startRow = yCam / gamePanel.TILESIZE;
        int endCol = (xCam + gamePanel.screenWidth) / gamePanel.TILESIZE;
        int endRow = (yCam + gamePanel.screenHeight) / gamePanel.TILESIZE;

        //This should only render visible tiles now
        for (int row = startRow; row <= endRow; row++) {
            for (int col = startCol; col <= endCol; col++) {

                Tile tile = getTileDraw(col, row);
                if (tile == null) continue;
                int x = tile.getX() - xCam;
                int y = tile.getY() - yCam;
                Ore ore = tile.getOreOnTile();

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
                    case IRON:
                        g2.drawImage(ironTileImage, x, y, null);
                        break;
                    default:
                        break;
                }
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

                Tile tile = getTileDraw(tileX, tileY);
                tile.setOreOnTile(ore);
            }
        }
        System.out.println("Generated Cluster of " + ore.getName());
    }

    public Tile getTileCoords(int x, int y){
        //System.out.println("x " + x + "     " +y );
        int dx = x / gamePanel.TILESIZE;
        int dy = y / gamePanel.TILESIZE;
        try{
            return tileArrayList.get(dx * tilesPerRow + dy);
        }catch (IndexOutOfBoundsException e){
            System.out.println("Tile not in Arraybounds");
        }
        return null;
    }

    public Tile getTileDraw(int x, int y){
        //System.out.println("x " + x + "     " +y );
        try{
            return tileArrayList.get(x * tilesPerRow + y);
        }catch (IndexOutOfBoundsException e){
            System.out.println("Tile not in Arraybounds");
        }
        return null;
    }
}
