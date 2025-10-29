package map;

public class Item {

    protected boolean isFuel = false;
    protected String name;

    public Item(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
