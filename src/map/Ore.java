package map;

public class Ore extends Item{



    public Ore(String name, OreType type, boolean isFuel){
        super(name);
        this.type = type;
        this.isFuel = isFuel;
    }
}
