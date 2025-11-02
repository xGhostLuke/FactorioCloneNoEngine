package controller;

import map.items.Ore;
import map.items.OreType;

public class OreController {

    public Ore copperOre = new Ore("Copper", OreType.COPPER, false);
    public Ore ironOre = new Ore("Iron",OreType.IRON, false);
    public Ore coalOre = new Ore("Coal",OreType.COAL, true);
    public Ore stoneOre = new Ore("Stone",OreType.STONE, false);
    public Ore noOre = new Ore("None",OreType.NONE, false);
}
