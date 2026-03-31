package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    public static final int WIDTH  = 1280;
    public static final int HEIGHT = 720;
    final int FPS = 60;

    Thread gameThread;
    PlayManager playManager;

    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        this.addKeyListener(new KeyHandler());
        this.setFocusable(true);

        playManager = new PlayManager();
    }

    public void launchGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1_000_000_000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();

        while (gameThread != null) {
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    private void update() {
        // Restart takes priority — works even during pause or game over
        if (KeyHandler.restartPressed) {
            playManager = new PlayManager();
            KeyHandler.pausePressed  = false;
            KeyHandler.restartPressed = false;
            return;
        }

        if (!KeyHandler.pausePressed) {
            playManager.update();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        playManager.draw(g);
    }
}
