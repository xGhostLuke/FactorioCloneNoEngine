package map;

public class Ore extends Item{

    public OreType type;

    public Ore(String name, OreType type, boolean isFuel){
        super(name);
        this.type = type;
        this.isFuel = isFuel;
    }
}
