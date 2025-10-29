package main;

import controller.*;
import entity.*;
import map.Building;
import map.Item;
import map.Miner;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class GamePanel extends JPanel implements Runnable {

    //SCREEN SETTINGS

    final int originalTileSize = 32;
    final int scale = 1;

    public final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 32;
    final int maxScreenRow = 32;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    int fps = 60;

    private final MouseHandler mouseHandler = new MouseHandler();
    private final KeyHandler keyHandler = new KeyHandler(mouseHandler);

    public final OreController oreController = new OreController();

    private final MapController mapGen = new MapController(this, oreController, 32);

    Thread gameThread;

    Player player = new Player(this, keyHandler);
    PlayerController playerController = new PlayerController(this, keyHandler, mouseHandler, mapGen);
    InventoryController inventoryController = new InventoryController(playerController);

    public ArrayList<Building> buildingList = new ArrayList<Building>();

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);
    }

    public void StartGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / fps;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null){

            update();

            repaint();

            try{
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0){
                    remainingTime = 0;
                }

                Thread.sleep( (long) remainingTime);

                nextDrawTime += drawInterval;
            }catch (InterruptedException e){
                System.out.println("Error sleeping Thread");
            }

        }
    }

    public void update(){
        playerController.update();
        player.update();

        for (Building building : buildingList){
            building.update();
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        mapGen.renderMap(g2);
        inventoryController.paintInventory(g2);

        for (Building building : buildingList){
            building.draw(g2);
        }

        player.draw(g2);

        if(playerController.activeMinerInventory != null){
            UIController.drawMinerInventory(g2, playerController.activeMinerInventory);
        }

        g2.dispose();
    }
}
