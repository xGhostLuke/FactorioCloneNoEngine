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


    public TransportationBuilding(int x, int y, GamePanel gamePanel, MapController mapController, String name, Direction direction) {
        super(x, y, gamePanel, name, direction);
        this.mapController = mapController;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
