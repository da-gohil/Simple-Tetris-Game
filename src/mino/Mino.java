package mino;

import main.KeyHandler;
import main.PlayManager;

import java.awt.Color;
import java.awt.Graphics2D;

public class Mino {

    public Block[] b = new Block[4];
    public Block[] tempB = new Block[4];
    int autoDropCounter = 0;

    public int direction = 1; // There are 4 direction(1/2/3/4)

    public void create(Color color) {
        b[0] = new Block(color);
        b[1] = new Block(color);
        b[2] = new Block(color);
        b[3] = new Block(color);

        tempB[0] = new Block(color);
        tempB[1] = new Block(color);
        tempB[2] = new Block(color);
        tempB[3] = new Block(color);
    }

    public void setXY(int x, int y) {

    }


    public void updateXY(int direction) {
        // Apply rotated tempB[] to b[]
        for (int i = 0; i < 4; i++) {
            b[i].x = tempB[i].x;
            b[i].y = tempB[i].y;
        }

        // Update current direction
        this.direction = direction + 1;

        // Wrap back to 1-4
        if (this.direction > 4) {
            this.direction = 1;
        }
    }


    public void getDirection1(){}
    public void getDirection2(){}
    public void getDirection3(){}
    public void getDirection4(){}


    public void update() {

        // ROTATE The Mino
        if (KeyHandler.upPressed) {
            switch (direction) {
                case 1: getDirection1(); break;
                case 2: getDirection2(); break;
                case 3: getDirection3(); break;
                case 4: getDirection4(); break;
            }
            KeyHandler.upPressed = false;
        }

        // SOFT DROP
        if (KeyHandler.downPressed) {
            b[0].y += Block.SIZE;
            b[1].y += Block.SIZE;
            b[2].y += Block.SIZE;
            b[3].y += Block.SIZE;
            KeyHandler.downPressed = false;
        }

        // MOVE LEFT
        if (KeyHandler.leftPressed) {
            b[0].x -= Block.SIZE;
            b[1].x -= Block.SIZE;
            b[2].x -= Block.SIZE;
            b[3].x -= Block.SIZE;
            KeyHandler.leftPressed = false;   // one move per press
        }

        // MOVE RIGHT
        if (KeyHandler.rightPressed) {
            b[0].x += Block.SIZE;
            b[1].x += Block.SIZE;
            b[2].x += Block.SIZE;
            b[3].x += Block.SIZE;
            KeyHandler.rightPressed = false;  // one move per press
        }

        // AUTO DROP
        autoDropCounter++;
        if (autoDropCounter == PlayManager.dropInterval) {
            b[0].y += Block.SIZE;
            b[1].y += Block.SIZE;
            b[2].y += Block.SIZE;
            b[3].y += Block.SIZE;
            autoDropCounter = 0;
        }
    }

    public void draw(Graphics2D g2d) {

        int margin = 2;
        g2d.setColor(b[0].color);

        g2d.fillRect(b[0].x + margin, b[0].y + margin, Block.SIZE - margin * 2, Block.SIZE - margin * 2);
        g2d.fillRect(b[1].x + margin, b[1].y + margin, Block.SIZE - margin * 2, Block.SIZE - margin * 2);
        g2d.fillRect(b[2].x + margin, b[2].y + margin, Block.SIZE - margin * 2, Block.SIZE - margin * 2);
        g2d.fillRect(b[3].x + margin, b[3].y + margin, Block.SIZE - margin * 2, Block.SIZE - margin * 2);
    }
}
