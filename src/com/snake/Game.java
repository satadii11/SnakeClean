package com.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class Game extends JPanel implements GameObject.CollisionListener {
    public static boolean aBoolean = true;

    private final Snake snake;
    private final ArrayList<GameObject> list;
    private final Random random;

    private final int i;
    private final int j;

    public Game(Snake snake, int x, int y) {
        this.snake = snake;
        this.j = (x / Config.GRID_SIZE) - 1;
        this.i = (y / Config.GRID_SIZE) - 1;

        this.random = new Random();
        this.list = new ArrayList<>(this.j);
        this.list.add(snake);
        this.snake.setCollisionListener(this);

        setPreferredSize(new Dimension(x, y));

        // Generate Food
        int xPosition;
        int yPosition;
        do {
            xPosition = random.nextInt(j);
            yPosition = random.nextInt(i);
        } while (snake.isColliding(xPosition, yPosition));

        Fruit newFruit = new Fruit(xPosition, yPosition);
        this.list.add(newFruit);
    }

    public void paintComponent(Graphics graphicDrawer) {
        super.paintComponent(graphicDrawer);

        long startTime = System.nanoTime();

        //Check for coilisoon
        int numOfChild = list.size();
        for (int position = 0; position < numOfChild; position++) {
            GameObject currentChild = list.get(position);
            int nextChildPosition = position + 1;
            if (numOfChild != nextChildPosition) {
                for (int nextPosition = nextChildPosition; nextChildPosition < numOfChild; nextChildPosition++) {
                    GameObject nextChild = list.get(nextPosition);
                    if (currentChild.isColliding(nextChild))
                        currentChild.onCollided(currentChild, nextChild);
                }
            }
        }

        snake.move(i, j);
        for (GameObject child : list) {
            child.draw(graphicDrawer);
        }

        long deltaTime = (System.nanoTime() - startTime) / 1000000L;
        if (deltaTime < Config.FRAME_TIME) {
            try {
                Thread.sleep(Config.FRAME_TIME - deltaTime);
            } catch (Exception ignored) {
            }
        }

        if (aBoolean) {
            repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Game over!");
            System.exit(0);
        }
    }


    public void checkForEvent(KeyEvent event) {
        for (GameObject child : list) {
            child.checkForEvent(event);
        }
    }

    @Override
    public void onCollided(GameObject currentChild, GameObject nextChild) {
        if (currentChild instanceof Snake && nextChild instanceof Fruit) {
            int xPosition;
            int yPosition;
            do {
                xPosition = random.nextInt(j);
                yPosition = random.nextInt(i);
            } while (snake.isColliding(xPosition, yPosition));

            Fruit newFruit = new Fruit(xPosition, yPosition);
            this.list.add(newFruit);
            list.remove(nextChild);
        }
    }
}
