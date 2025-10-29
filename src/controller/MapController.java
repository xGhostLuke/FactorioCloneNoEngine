package controller;

import main.GamePanel;
import map.Ore;
import map.OreType;
import map.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class MapController {

    private final GamePanel gamePanel;
    private final int size;
    int tilesPerRow;

    private BufferedImage grasTileImage;
    private BufferedImage copperTileImage;
    private BufferedImage stoneTileImage;

    private ArrayList<Tile> tileArrayList = new ArrayList<>();

    public MapController(GamePanel gamePanel, int size) {
        this.size = size*gamePanel.tileSize;
        this.gamePanel = gamePanel;
        this.tilesPerRow = size;

        generateMap(this.size);
        loadTileImages();
    }

    private void loadTileImages(){
        try {
            grasTileImage = ImageIO.read(getClass().getResourceAsStream("/res/GrasTile.png"));
            copperTileImage = ImageIO.read(getClass().getResourceAsStream("/res/CopperTile.png"));
            stoneTileImage = ImageIO.read(getClass().getResourceAsStream("/res/StoneTile.png"));
        }catch (IOException e){
            System.out.println("Error loading Images");
        }
    }

    private void generateMap(int size){
        for(int x = 0; x < size; x += gamePanel.tileSize){
            for(int y = 0; y < size; y += gamePanel.tileSize){
                Tile tile = new Tile (x,y);
                tileArrayList.add(tile);
                tile.setOreOnTile(new Ore(OreType.NONE));
            }
        }

        generateOreCluster(new Ore(OreType.COPPER), 2);
        generateOreCluster(new Ore(OreType.COPPER), 3);
        generateOreCluster(new Ore(OreType.COPPER),1);
        generateOreCluster(new Ore(OreType.STONE),2);
        generateOreCluster(new Ore(OreType.STONE),2);
    }

    public void renderMap(Graphics2D g2){
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);


        for (Tile tile : tileArrayList){

            Ore ore = tile.getOreOnTile();
            int x = tile.getX();
            int y = tile.getY();

            switch (ore.type){
                case NONE:
                    g2.drawImage(grasTileImage, x, y, null);
                    break;
                case COPPER:
                    g2.drawImage(copperTileImage, x, y, null);
                    break;
                    case STONE:
                        g2.drawImage(stoneTileImage, x, y, null);
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

    public Tile getTile(int x, int y){
        try{
            return tileArrayList.get(x * tilesPerRow + y);
        }catch (IndexOutOfBoundsException e){
            System.out.println("Tile not in Arraybounds");
        }
        return null;
    }
}
