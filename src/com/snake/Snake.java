package com.snake;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Snake extends GameObject {
    private static final int X_POSITION = 0;
    private static final int Y_POSITION = 1;

    private ArrayList<int[]> tails;

    private Direction direction;

    private int tailTotal;

    public Snake(int xPosition, int yPosition) {
        super(xPosition, yPosition);
        this.tails = new ArrayList<>();
        this.tailTotal = 0;
        this.direction = Direction.RIGHT;
    }

    @Override
    public void draw(Graphics graphicDrawer) {
        drawTail(graphicDrawer);

        int xCoordinate = getTopLeftCoordinate(getXPosition());
        int yCoordinate = getTopLeftCoordinate(getYPosition());
        graphicDrawer.setColor(new Color(156, 0, 0));
        graphicDrawer.fillOval(xCoordinate, yCoordinate, getSize(), getSize());
    }

    private int getTopLeftCoordinate(int currentPosition) {
        return (currentPosition * Config.GRID_SIZE) + Config.SPACING;
    }

    private void drawTail(Graphics graphicDrawer) {
        for (int[] tail : tails) {
            int xCoordinate = getTopLeftCoordinate(tail[X_POSITION]);
            int yCoordinate = getTopLeftCoordinate(tail[Y_POSITION]);
            graphicDrawer.setColor(new Color(0, 0, 156));
            graphicDrawer.fillOval(xCoordinate, yCoordinate, getSize(), getSize());
        }
    }

    @Override
    public void move(int maxRow, int maxColumn) {
        moveTail();
        moveHead(maxRow, maxColumn);
    }

    private void moveHead(int maxRow, int maxColumn) {
        int newXPosition = getXPosition();
        int newYPosition = getYPosition();
        switch (direction) {
            case LEFT:
                newXPosition = getXPosition() == 0 ? maxColumn : getXPosition() - 1;
                break;
            case RIGHT:
                newXPosition = getXPosition() == maxColumn ? 0 : getXPosition() + 1;
                break;
            case UP:
                newYPosition = getYPosition() == 0 ? maxRow : getYPosition() - 1;
                break;
            case DOWN:
                newYPosition = getYPosition() == maxRow ? 0 : getYPosition() + 1;
        }
        setXPosition(newXPosition);
        setYPosition(newYPosition);
    }

    private void moveTail() {
        if (shouldAddTail()) {
            tails.add(new int[]{getXPosition(), getYPosition()});
        } else if (shouldShiftTails()) {
            shiftTails();
        }
    }

    private void shiftTails() {
        ArrayList<int[]> newTails = new ArrayList<>(tailTotal);
        int lastTailPosition = tails.size() - 1;
        for (int i = 0; i < lastTailPosition; i++) {
            newTails.add(tails.get(i + 1));
        }
        newTails.add(lastTailPosition, new int[]{getXPosition(), getYPosition()});
        tails = newTails;
    }

    private boolean shouldShiftTails() {
        return tailTotal != 0;
    }

    private boolean shouldAddTail() {
        return tails.size() != tailTotal;
    }

    @Override
    public void checkForEvent(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.VK_UP:
                if (direction != Direction.DOWN) direction = Direction.UP;
                break;
            case KeyEvent.VK_DOWN:
                if (direction != Direction.UP) direction = Direction.DOWN;
                break;
            case KeyEvent.VK_LEFT:
                if (direction != Direction.RIGHT) direction = Direction.LEFT;
                break;
            case KeyEvent.VK_RIGHT:
                if (direction != Direction.LEFT) direction = Direction.RIGHT;
                break;
        }
    }

    @Override
    public void onCollided(GameObject currentChild, GameObject nextChild) {
        if (nextChild instanceof Fruit) tailTotal++;
        super.onCollided(currentChild, nextChild);
    }

    public boolean isColliding(int xPosition, int yPosition) {
        boolean headIsColliding = getXPosition() == xPosition && getYPosition() == yPosition;
        if (!headIsColliding) return false;
        for (int[] tail : tails) {
            if (tail[X_POSITION] == xPosition && tail[Y_POSITION] == yPosition) return true;
        }

        return false;
    }
}
