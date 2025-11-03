package main;

import map.items.Item;
import map.items.Ore;
import map.items.OreType;

/**
 * static class to load all Items before hand
 */
public class ItemMananger {

    public static Ore copperOre = new Ore("copper_ore", OreType.COPPER, false);
    public static Ore ironOre = new Ore("Iron",OreType.IRON, false);
    public static Ore coalOre = new Ore("coal_ore",OreType.COAL, true);
    public static Ore stoneOre = new Ore("stone_ore",OreType.STONE, false);
    public static Ore noOre = new Ore("None",OreType.NONE, false);

    public static Item copperPlate = new Item("copper_plate");
}
