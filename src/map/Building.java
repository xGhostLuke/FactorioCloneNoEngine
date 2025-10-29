package map;

import controller.PlayerController;

import java.awt.*;

public abstract class Building {
    protected PlayerController playerController;
    protected String name;

    public Building(PlayerController playerController, String name) {
        this.playerController = playerController;
        this.name = name;
    }

    public abstract void update();
    public abstract void draw(Graphics2D g);
}
