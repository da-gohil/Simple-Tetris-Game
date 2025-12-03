package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame window = new JFrame("Simple Tetris Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        //Add the created GamePanel to the main window
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();

        window.setLocationRelativeTo(null); //We do not set specific location on the screen
        window.setVisible(true);
        //Launching the GamePanel Frame in main class
        gamePanel.launchGame();
    }
}
