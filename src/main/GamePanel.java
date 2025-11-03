package main;

import controller.*;
import entity.*;
import map.buildings.BuildingPreview;
import map.buildings.Placeable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {

    //SCREEN SETTINGS

    final int ORIGINALESIZE = 32;
    final int SCLAE = 1;
    public final int TILESIZE = ORIGINALESIZE * SCLAE;

    final int maxScreenCol = 32;
    final int maxScreenRow = 32;
    final int screenWidth = TILESIZE * maxScreenCol;
    final int screenHeight = TILESIZE * maxScreenRow;

    int fps = 60;

    private final MouseHandler mouseHandler;
    private final KeyHandler keyHandler;


    private final MapController mapGen;

    Thread gameThread;

    Player player;
    PlayerController playerController;
    InventoryController inventoryController;
    BuildingPreview buildingPreview;

    public ArrayList<Placeable> buildingList = new ArrayList<Placeable>();

    public GamePanel() {
        mouseHandler = new MouseHandler();
        keyHandler = new KeyHandler(mouseHandler);

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);

        ImageLoader.loadAll();
        UIController.initMenu();

        mapGen = new MapController(this, 32);

        player = new Player(this, keyHandler);
        playerController = new PlayerController(this, keyHandler, mouseHandler, mapGen);
        inventoryController = new InventoryController(playerController);
        buildingPreview = new BuildingPreview(this, keyHandler, mouseHandler);

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
        UIController.update(mouseHandler);

        playerController.update();
        player.update();



        for (Placeable building : buildingList){
            building.update();
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        mapGen.renderMap(g2);
        inventoryController.paintInventory(g2);

        for (Placeable building : buildingList){
            building.draw(g2);
        }

        player.draw(g2);

        if(playerController.activeInventory != null){
            UIController.drawMinerInventory(g2, playerController.activeInventory);
        }

        if(keyHandler.inBuildMode){
            UIController.drawBuildingMenu(g2);

            try {
                buildingPreview.draw(g2);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        g2.dispose();
    }
}
