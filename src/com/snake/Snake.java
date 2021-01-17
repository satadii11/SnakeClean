package com.snake;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Snake extends GameObject {

    private ArrayList<int[]> LList;

    private int direct = -1;

    private int tailTotal;

    private int LEFTX = 1201;
    private int RIGHTX = 1202;
    private int UPX = 1203;
    private int DOWNX = 1204;

    public Snake(int xPosition, int yPosition) {
        super(xPosition, yPosition);
        this.LList = new ArrayList<>();
        this.tailTotal = 0;
        direct = RIGHTX;
    }

    @Override
    public void draw(Graphics graphicDrawer) {
        snakes(graphicDrawer);

        System.out.println("asd");

        int xCoordinate = getTopLeftCoordinate(getXPosition());
        int yCoordinate = getTopLeftCoordinate(getYPosition());
        graphicDrawer.setColor(new Color(156, 0, 0));
        graphicDrawer.fillOval(xCoordinate, yCoordinate, getSize(), getSize());
    }

    private int getTopLeftCoordinate(int currentPosition) {
        return (currentPosition * Config.GRID_SIZE) + Config.SPACING;
    }

    private void snakes(Graphics graphicDrawer) {
        for (int i = 1;i<LList.size();i++){
            int xCoordinate = (LList.get(i)[0] * Config.GRID_SIZE) + Config.SPACING;
            int yCoordinate = (LList.get(i)[1] * Config.GRID_SIZE) + Config.SPACING;
            graphicDrawer.setColor(new Color(0, 0, 156));
            graphicDrawer.fillOval(xCoordinate, yCoordinate, getSize(), getSize());
        }
    }

    @Override
    public void move(int maxRow, int maxColumn) {
        moveTail();
        moveHead(maxRow, maxColumn);
        if (crushSnake(getXPosition(), getYPosition())) Game.aBoolean = false;
    }

    private void moveHead(int maxRow, int maxColumn) {
        int newXPosition = getXPosition();
        int newYPosition = getYPosition();

        if (direct == LEFTX) {
            newXPosition = getXPosition() == 0 ? maxColumn : getXPosition() - 1;
        } else if (direct == RIGHTX) {
            newXPosition = getXPosition() == maxColumn ? 0 : getXPosition() + 1;
        } else if (direct == UPX) {
            newYPosition = getYPosition() == 0 ? maxRow : getYPosition() - 1;
        } else if (direct == DOWNX) {
            newYPosition = getYPosition() == maxRow ? 0 : getYPosition() + 1;
        }

        setXPosition(newXPosition);
        setYPosition(newYPosition);
    }

    private void moveTail() {
        if (LList.size() != tailTotal) {
            LList.add(new int[]{getXPosition(), getYPosition()});
        } else if (tailTotal != 0) {
            ArrayList<int[]> newLList = new ArrayList<>(tailTotal);
            int lastTailPosition = LList.size() - 1;
            for (int i = 0; i < lastTailPosition; i++) {
                newLList.add(LList.get(i + 1));
            }
            newLList.add(lastTailPosition, new int[]{getXPosition(), getYPosition()});
            LList = newLList;
        }
    }


    @Override
    public void checkForEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.VK_UP) {
            if (direct != UPX) direct = UPX;
        } else if (event.getKeyCode() == KeyEvent.VK_DOWN) {
            if (direct != DOWNX) direct = DOWNX;
        } else if (event.getKeyCode() == KeyEvent.VK_LEFT) {
            if (direct != LEFTX) direct = LEFTX;
        } else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (direct != RIGHTX) direct = RIGHTX;
        }
    }

    @Override
    public void onCollided(GameObject currentChild, GameObject nextChild) {
        if (nextChild instanceof Fruit) tailTotal++;
        super.onCollided(currentChild, nextChild);
    }

    public boolean isColliding(int xPosition, int yPosition) {
        boolean headIsColliding = getXPosition() == xPosition && getYPosition() == yPosition;
        if (headIsColliding) return true;
        return crushSnake(xPosition, yPosition);
    }

    private boolean crushSnake(int x, int y) {
        for (int[] tail : LList) {
            if (tail[0] == x && tail[1] == y) return true;
        }

        return false;
    }

}
