package map;

import controller.OreController;
import controller.PlayerController;

import java.awt.*;

public abstract class Building {
    protected OreController oreController;
    protected PlayerController playerController;
    protected String name;

    public Building(PlayerController playerController, OreController oreController, String name) {
        this.playerController = playerController;
        this.oreController = oreController;
        this.name = name;
    }

    public abstract void update();
    public abstract void draw(Graphics2D g);
}
