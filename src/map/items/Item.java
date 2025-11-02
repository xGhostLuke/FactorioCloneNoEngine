package map.items;

public class Item {

    protected boolean isFuel = false;
    protected String name;
    protected OreType type;

    public Item(String name) {
        this.name = name;
        this.type = null;
    }

    public String getName(){
        return name;
    }
    public OreType getType(){return type;}
    public boolean getIsFuel(){
        return isFuel;
    }
}
