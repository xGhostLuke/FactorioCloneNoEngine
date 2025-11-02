package map.items;

import map.buildings.Placeable;

public class Tile {

    private int x, y;
    private Ore oreOnTile;
    private Placeable buildingOnTile;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Ore getOreOnTile() {
        return oreOnTile;
    }

    public void setOreOnTile(Ore oreOnTile) {
        this.oreOnTile = oreOnTile;
    }

    public Placeable getBuildingOnTile() {
        return buildingOnTile;
    }

    public void setBuildingOnTile(Placeable buildingOnTile) {
        this.buildingOnTile = buildingOnTile;
    }

}
