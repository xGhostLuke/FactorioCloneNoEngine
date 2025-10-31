package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    MouseHandler mouseHandler;

    public boolean upPressed, downPressed, leftPressed, rightPressed, inBuildMode, rotationPressed;

    public KeyHandler(MouseHandler mouseHandler) {
        this.mouseHandler = mouseHandler;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_E) {
            if(inBuildMode){
                inBuildMode = false;
            }else{
                inBuildMode = true;
                mouseHandler.leftClicked = false;
                mouseHandler.leftPressed = false;
                mouseHandler.rightClicked = false;
            }
            System.out.println(inBuildMode);
        }
        if (code == KeyEvent.VK_R) {
            rightPressed = true;
            System.out.println(rotationPressed);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }
}
