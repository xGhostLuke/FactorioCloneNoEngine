package map;

import controller.MapController;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Map;

public class Belt extends TransportationBuilding{

    private long lastTransferTime = 0;
    private final long transferDelay = 500;

    public Belt(int x, int y, GamePanel gamePanel, MapController mapController, String name, Direction direction) {
        super(x, y, gamePanel, mapController, name, direction);

        try{
            image = ImageIO.read(getClass().getResourceAsStream("/res/belt_up.png"));
        }catch(IOException e){
            System.out.println("Image image not found");
        }
    }

    @Override
    public void update() {
        setBuildingAddingTo();
        setBuildingTakingFrom();

        long currentTime = System.currentTimeMillis();

        if (currentTime - lastTransferTime >= transferDelay) {
            takeItemFromBuilding();
            giveItemToBuilding();
            lastTransferTime = currentTime;
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(image, xPos, yPos, null);
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
    public void takeItem(Item item) {
        if (!inventory.containsKey(item)){
            return;
        }

        if (inventory.get(item) < 1){
            return;
        }

        inventory.put(item, inventory.get(item) - 1);
    }

    /**
     * Checks if there is a building on top. For now
     */
    private void setBuildingAddingTo(){
        int tileX = xPos / gamePanel.TILESIZE;
        int tileY = yPos / gamePanel.TILESIZE + 1;

        Tile tile = mapController.getTile(tileX, tileY);
        if (tile == null) return;

        buildingAddingTo = tile.getBuildingOnTile();
    }

    /**
     * Checks if a Building is Placed under the claw for now
     */
    private void setBuildingTakingFrom(){
        int tileX = xPos / gamePanel.TILESIZE;
        int tileY = yPos / gamePanel.TILESIZE - 1; // eine Tile oberhalb

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

        Item itemToAddToOwnInv = buildingRemovingFrom.getOneItem();
        if(itemToAddToOwnInv == null){
            return;
        }

        Map<Item, Integer> sourceInv = buildingRemovingFrom.getInventory();
        if (sourceInv == null) {
            return;
        }

        Integer amount = sourceInv.get(itemToAddToOwnInv);
        if(amount == null || buildingRemovingFrom.getInventory().get(itemToAddToOwnInv) < 2){
            return;
        }

        addItemToInventory(itemToAddToOwnInv, 1);
        buildingRemovingFrom.takeItem(itemToAddToOwnInv);
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

        inventory.put(buildingRemovingFrom.getOneItem(), inventory.get(buildingRemovingFrom.getOneItem()) + amount);
    }

    public void giveItemToBuilding(){
        if(buildingAddingTo == null){
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
}
