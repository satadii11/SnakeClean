package com.snake;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Fruit extends GameObject {

    public Fruit(int xPosition, int yPosition) {
        super(xPosition, yPosition);

        int size = Config.GRID_SIZE - (2 * Config.SPACING);
        setSize(size);
    }

    @Override
    public void draw(Graphics graphicDrawer) {
        int xCoordinate = getTopLeftCoordinate(getXPosition());
        int yCoordinate = getTopLeftCoordinate(getYPosition());

        graphicDrawer.setColor(new Color(0, 156, 0));
        graphicDrawer.fillRect(xCoordinate, yCoordinate, getSize(), getSize());
    }

    private int getTopLeftCoordinate(int currentPosition) {
        return (currentPosition * Config.GRID_SIZE) + Config.SPACING;
    }

    @Override
    public void move(int maxRow, int maxColumn) {
    }

    @Override
    public void checkForEvent(KeyEvent event) {
    }
}
