package main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseHandler extends MouseAdapter {

    public int mouseX, mouseY;
    public boolean leftPressed, rightPressed;
    public boolean leftClicked, rightClicked;

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftPressed = true;
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            rightPressed = true;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            leftClicked = true;
        }
        if(e.getButton() == MouseEvent.BUTTON3) {
            rightClicked = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftPressed = false;
            leftClicked = false;
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            rightPressed = false;
            rightClicked = false;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
}

