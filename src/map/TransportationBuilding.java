package map;

import controller.MapController;
import main.GamePanel;

import java.util.HashMap;
import java.util.Map;

public abstract class TransportationBuilding extends Placeable {

    Map<Item, Integer> inventory = new HashMap<Item, Integer>();
    protected Placeable buildingAddingTo;
    protected Placeable buildingRemovingFrom;
    protected MapController mapController;


    public TransportationBuilding(int x, int y, GamePanel gamePanel, MapController mapController, String name) {
        super(x, y, gamePanel, name);
        this.mapController = mapController;
    }
}
