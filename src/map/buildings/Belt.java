package map.buildings;

import controller.MapController;
import main.GamePanel;
import map.items.Item;
import map.items.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class Belt extends TransportationBuilding{

    private long lastTransferTime = 0;
    private final long transferDelay = 500;

    private final ArrayList<BeltItem> movingItems = new ArrayList<BeltItem>();
    private final double speed = 0.05;

    public Belt(int x, int y, GamePanel gamePanel, MapController mapController, String name, Direction direction) {
        super(x, y, gamePanel, mapController, name, direction);
    }

    @Override
    public void update() {
        setBuildingAddingTo();
        setBuildingTakingFrom();

        long currentTime = System.currentTimeMillis();

        moveBeltItems();

        if (currentTime - lastTransferTime >= transferDelay) {
            takeItemFromBuilding();
            giveItemToBuilding();
            lastTransferTime = currentTime;
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(image, xPos, yPos, null);
        drawItems(g);
    }

    @Override
    public void setCraftingCost(Item item, int cost, Item item2, int cost2) {
        craftingCost.put(item, cost);
        craftingCost.put(item2, cost2);
    }

    @Override
    public Map<Item, Integer> getCraftingCost() {
        return craftingCost;
    }

    @Override
    public Map<Item, Integer> getOutputInventory() {
        return Map.of();
    }

    @Override
    public Item getOneItem() {
        if (inventory.isEmpty()) {
            return null;
        }

        for (Item item : inventory.keySet()) {
            if (inventory.get(item) > 0) {
                return item;
            }
        }

        return null;
    }

    @Override
    public void takeItem(Item item, int amount) {
        if(!inventory.containsKey(item)) {
            return;
        }

        inventory.put(item, inventory.get(item) - amount);
    }

    /**
     * Checks if there is a building on top. For now
     */
    protected void setBuildingAddingTo(){
        int tileX;
        int tileY;

        switch (direction){
            case Direction.TOP:
                tileX = xPos / gamePanel.TILESIZE;
                tileY = yPos / gamePanel.TILESIZE - 1;
                break;
            case Direction.RIGHT:
                tileX = xPos / gamePanel.TILESIZE + 1;
                tileY = yPos / gamePanel.TILESIZE;
                break;
            case Direction.LEFT:
                tileX = xPos / gamePanel.TILESIZE - 1;
                tileY = yPos / gamePanel.TILESIZE;
                break;
            default:
                tileX = xPos / gamePanel.TILESIZE;
                tileY = yPos / gamePanel.TILESIZE + 1;
                break;
        }

        Tile tile = mapController.getTile(tileX, tileY);
        if (tile == null) return;

        buildingAddingTo = tile.getBuildingOnTile();
    }

    /**
     * Checks if a Building is Placed under the claw for now
     */
    protected void setBuildingTakingFrom(){
        int tileX;
        int tileY;

        switch (direction){
            case Direction.TOP:
                tileX = xPos / gamePanel.TILESIZE;
                tileY = yPos / gamePanel.TILESIZE + 1;
                break;
            case Direction.RIGHT:
                tileX = xPos / gamePanel.TILESIZE - 1;
                tileY = yPos / gamePanel.TILESIZE;
                break;
            case Direction.LEFT:
                tileX = xPos / gamePanel.TILESIZE + 1;
                tileY = yPos / gamePanel.TILESIZE;
                break;
            default:
                tileX = xPos / gamePanel.TILESIZE;
                tileY = yPos / gamePanel.TILESIZE - 1;
                break;
        }

        Tile tile = mapController.getTile(tileX, tileY);
        if (tile == null) return;

        buildingRemovingFrom = tile.getBuildingOnTile();
    }

    /**
     * if there is a building to take from
     * Takes one Item per Tick and adds one to the other
     */
    public void takeItemFromBuilding(){

        if(buildingRemovingFrom == null){
            return;
        }

        Map<Item, Integer> sourceInv = buildingRemovingFrom.getInventory();
        if (sourceInv == null) {
            return;
        }

        Item itemToAddToOwnInv = buildingRemovingFrom.getOneItem();
        System.out.println(itemToAddToOwnInv);
        if(itemToAddToOwnInv == null){
            return;
        }

        int amount = 1;

        Integer currenAmount = sourceInv.get(itemToAddToOwnInv);
        if(currenAmount == null || buildingRemovingFrom.getInventory().get(itemToAddToOwnInv) < amount){
            return;
        }

        movingItems.add(new BeltItem(itemToAddToOwnInv, itemToAddToOwnInv.getName()));
        addItemToInventory(itemToAddToOwnInv, amount);
        buildingRemovingFrom.takeItem(itemToAddToOwnInv, amount);
    }

    @Override
    public Map<Item, Integer> getInventory() {
        return inventory;
    }

    @Override
    public void addItemToInventory(Item item, int amount) {
        if(!inventory.containsKey(item)){
            inventory.put(item, amount);
            return;
        }

        if(buildingRemovingFrom == null){
            return;
        }

        inventory.put(buildingRemovingFrom.getOneItem(), inventory.get(buildingRemovingFrom.getOneItem()) + amount);
    }

    public void giveItemToBuilding(){
        if(buildingAddingTo == null){
            return;
        }

        if(buildingAddingTo instanceof TransportationBuilding){
            return;
        }

        if(inventory.isEmpty()){
            return;
        }

        Item item = getOneItem();

        if (inventory.get(item) == null) {
            return;
        }

        buildingAddingTo.addItemToInventory(item, 1);
        inventory.put(item, inventory.get(item) - 1);
    }

    private void moveBeltItems(){
        for (int i = 0; i < movingItems.size(); i++) {
            BeltItem beltItem = movingItems.get(i);
            double currenProgress = beltItem.getProgress();
            beltItem.setProgress(currenProgress + speed);

            if (beltItem.getProgress() >= 1.0) {
                // Item am Ende angekommen -> weitergeben
                //giveItemToBuilding(beltItem.getItem());
                movingItems.remove(i);
                i--;
            }
        }
    }

    private void drawItems(Graphics2D g){
        for (BeltItem beltItem : movingItems) {
            double px = xPos;
            double py = yPos;

            switch (direction) {
                case TOP -> py -= beltItem.getProgress() * gamePanel.TILESIZE;
                case DOWN -> py += beltItem.getProgress() * gamePanel.TILESIZE;
                case LEFT -> px -= beltItem.getProgress() * gamePanel.TILESIZE;
                case RIGHT -> px += beltItem.getProgress() * gamePanel.TILESIZE;
            }

            g.drawImage(beltItem.getImage(), (int) px , (int) py , 16, 16, null);
        }
    }
}
