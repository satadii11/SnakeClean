package com.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class Game extends JPanel implements GameObject.CollisionListener {
    public static boolean isPlaying = true;

    private final Snake snake;
    private final ArrayList<GameObject> childs;
    private final Random randomGenerator;

    private final int maxRow;
    private final int maxColumn;

    public Game(Snake snake, int width, int height) {
        this.snake = snake;
        this.maxColumn = (width / Config.GRID_SIZE) - 1;
        this.maxRow = (height / Config.GRID_SIZE) - 1;
        this.randomGenerator = new Random();
        this.childs = new ArrayList<>(this.maxColumn);
        this.childs.add(snake);
        this.snake.setCollisionListener(this);

        setPreferredSize(new Dimension(width, height));
        generateFood();
    }

    private void generateFood() {
        int xPosition;
        int yPosition;
        do {
            xPosition = randomGenerator.nextInt(maxColumn);
            yPosition = randomGenerator.nextInt(maxRow);
        } while (snake.isColliding(xPosition, yPosition));

        Fruit newFruit = new Fruit(xPosition, yPosition);
        this.childs.add(newFruit);
    }

    public void paintComponent(Graphics graphicDrawer) {
        super.paintComponent(graphicDrawer);

        long startTime = System.nanoTime();

        checkForCollision();
        snake.move(maxRow, maxColumn);
        for (GameObject child : childs) {
            child.draw(graphicDrawer);
        }

        long deltaTime = (System.nanoTime() - startTime) / 1000000L;
        if (deltaTime < Config.FRAME_TIME) {
            try {
                Thread.sleep(Config.FRAME_TIME - deltaTime);
            } catch (Exception ignored) {
            }
        }

        if (isPlaying) {
            repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Game over!");
            System.exit(0);
        }
    }

    public void checkForCollision() {
        int numOfChild = childs.size();
        for (int position = 0; position < numOfChild; position++) {
            GameObject currentChild = childs.get(position);
            int nextChildPosition = position + 1;
            if (numOfChild != nextChildPosition) {
                for (int nextPosition = nextChildPosition; nextChildPosition < numOfChild; nextChildPosition++) {
                    GameObject nextChild = childs.get(nextPosition);
                    if (currentChild.isColliding(nextChild))
                        currentChild.onCollided(currentChild, nextChild);
                }
            }
        }
    }

    public void checkForEvent(KeyEvent event) {
        for (GameObject child : childs) {
            child.checkForEvent(event);
        }
    }

    @Override
    public void onCollided(GameObject currentChild, GameObject nextChild) {
        if (currentChild instanceof Snake && nextChild instanceof Fruit) {
            generateFood();
            childs.remove(nextChild);
        }
    }
}
