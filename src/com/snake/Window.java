package com.snake;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Window extends JFrame implements KeyListener {
    private Game game;

    public Window() {
        super(Config.GAME_TITLE);

        Snake snake = new Snake(0, 0);
        this.game = new Game(snake, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setContentPane(game);
        setVisible(true);
        addKeyListener(this);
        pack();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent event) {
        game.checkForEvent(event);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Window::new);
    }
}
