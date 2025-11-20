package main;

import main.GamePanel;
import mino.Block;
import mino.Mino;
import mino.Mino_L1;

import java.awt.*;
import java.awt.Graphics2D;

//Handling the basic game play controls
public class PlayManager {
    //Main Play Area
    final int WIDTH = 360;
    final int HEIGHT = 600;
    public static int left_x = 0;
    public static int right_x = 0;

    public static int top_y = 0;
    public static int bottom_y = 0;

    //Mino
    Mino currentMino;
    final int MINO_START_X;
    final int MINO_START_Y;

    //Auto drop variables
    public static int dropInterval = 60; //drops in every 60 frames or 1 second

    public PlayManager(){

        // Main Play area frame
        // This assumes GamePanel.WIDTH is defined and accessible
        left_x = GamePanel.WIDTH / 2 - WIDTH / 2; // 1280/2 - 360/2 = 460
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;

        // Block.SIZE needs to be defined in your Block class
        MINO_START_X = left_x + (WIDTH / 2) - Block.SIZE;
        MINO_START_Y = top_y + Block.SIZE;

        //Set the Starting mino
        currentMino = new Mino_L1();
        currentMino.setXY(MINO_START_X, MINO_START_Y);

    }

    public void update(){
        currentMino.update();
    }

    // Changed parameter type from Graphics g2d back to Graphics g for robustness
    public void draw(Graphics g){

        // CRITICAL FIX: Cast Graphics object 'g' to Graphics2D 'g2d'
        Graphics2D g2d = (Graphics2D) g;

        //Draw play area frame
        g2d.setColor(Color.WHITE);
        // g2d.setStroke(new BasicStroke(4f)); // Kept commented out as requested

        //Modify the values to make sure the collision happens
        g2d.drawRect(left_x - 4, top_y - 4, WIDTH + 8, HEIGHT + 8);

        //Draw next frame to see what frame will be coming next
        int x = right_x + 100;
        int y = bottom_y - 200;
        g2d.drawRect(x, y, 200, 200);

        g2d.setFont(new Font("ARIAL", Font.PLAIN, 30));

        // g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); // Kept commented out

        g2d.drawString("NEXT", x + 60, y + 60);

        //Draw the currentMino
        if(currentMino != null){
            // Pass the Graphics2D object to the Mino's draw method
            currentMino.draw(g2d);
        }
    }
}