package entity;

import main.GameObject;

import java.awt.*;

public class Entity implements GameObject {

    public int x, y;
    public int speed;

    @Override
    public void draw(Graphics g) {

    }

    @Override
    public void update() {

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
