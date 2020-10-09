package com.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public abstract class GameObject {
    private Graphics graphicDrawer;
    private int xPosition;
    private int yPosition;
    private int size;
    private CollisionListener collisionListener;

    public GameObject(int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;

        int size = Config.GRID_SIZE - (2 * Config.SPACING);
        setSize(size);
    }

    public abstract void draw(Graphics graphicDrawer);

    public abstract void move(int maxRow, int maxColumn);

    public abstract void checkForEvent(KeyEvent event);

    public boolean isColliding(GameObject otherObject) {
        return otherObject.getXPosition() == xPosition && otherObject.getYPosition() == yPosition;
    }

    public void onCollided(GameObject currentChild, GameObject nextChild) {
        if (collisionListener != null) collisionListener.onCollided(currentChild, nextChild);
    }

    public void setCollisionListener(CollisionListener collisionListener) {
        this.collisionListener = collisionListener;
    }

    public int getXPosition() {
        return xPosition;
    }

    public void setXPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public void setYPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    protected Graphics getGraphicDrawer() {
        return graphicDrawer;
    }

    public interface CollisionListener {
        void onCollided(GameObject currentChild, GameObject nextChild);
    }
}
