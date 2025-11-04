package main;

import map.items.Item;
import map.items.Ore;
import map.items.OreType;

/**
 * static class to load all Items before hand
 */
public class ItemMananger {

    public static Ore copperOre = new Ore("copper_ore", OreType.COPPER, false, ImageLoader.getImage("copper_ore"));
    public static Ore ironOre = new Ore("iron_ore",OreType.IRON, false, ImageLoader.getImage("iron_ore"));
    public static Ore coalOre = new Ore("coal_ore",OreType.COAL, true, ImageLoader.getImage("coal_ore"));
    public static Ore stoneOre = new Ore("stone_ore",OreType.STONE, false, ImageLoader.getImage("stone_ore"));
    public static Ore noOre = new Ore("None",OreType.NONE, false, ImageLoader.getImage("copper_ore"));

    public static Item copperPlate = new Item("copper_plate", ImageLoader.getImage("copper_plate"));
    public static Item ironPlate = new Item("iron_plate", ImageLoader.getImage("iron_plate"));

    public static Item copperWire = new Item("copper_wire", ImageLoader.getImage("copper_wire")); //this needs rework ASAP

    public static Item smallChip = new Item("small_chip", ImageLoader.getImage("small_chip"));
}
