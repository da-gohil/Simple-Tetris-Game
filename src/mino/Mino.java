package mino;

import main.KeyHandler;
import main.PlayManager;

import java.awt.Color;
import java.awt.Graphics2D;

public class Mino {

    public Block[] b = new Block[4];
    public Block[] tempB = new Block[4];
    int autoDropCounter = 0;
    public int direction = 1; // 1 to 4
    boolean leftCollision, rightCollision, downCollision;

    public void create(Color color) {
        for (int i = 0; i < 4; i++) {
            b[i] = new Block(color);
            tempB[i] = new Block(color);
        }
    }

    public void setXY(int x, int y) {
        // Implemented in subclasses
    }

    public void updateXY(int direction) {
        // Apply rotated tempB[] to b[]
        for (int i = 0; i < 4; i++) {
            b[i].x = tempB[i].x;
            b[i].y = tempB[i].y;
        }
        this.direction = direction;
        if (this.direction > 4) this.direction = 1;
        else if (this.direction < 1) this.direction = 4;
    }

    public void getDirection1(){}
    public void getDirection2(){}
    public void getDirection3(){}
    public void getDirection4(){}

    // -----------------------------
    // COLLISION CHECKS
    // -----------------------------
    public void checkMovementCollision() {
        leftCollision = rightCollision = downCollision = false;

        for (Block block : b) {
            if (block.x <= PlayManager.left_x) leftCollision = true;
            if (block.x >= PlayManager.right_x) rightCollision = true;
            if (block.y >= PlayManager.bottom_y) downCollision = true;
        }
    }

    public boolean canRotate() {
        for (Block block : tempB) {
            if (block.x < PlayManager.left_x ||
                    block.x > PlayManager.right_x ||
                    block.y > PlayManager.bottom_y) {
                return false;
            }
            // TODO: add collision with placed blocks here if needed
        }
        return true;
    }

    // -----------------------------
    // UPDATE MINO (MOVEMENT & ROTATION)
    // -----------------------------
    public void update() {

        // ROTATE
        if (KeyHandler.upPressed) {
            switch (direction) {
                case 1: getDirection1(); break;
                case 2: getDirection2(); break;
                case 3: getDirection3(); break;
                case 4: getDirection4(); break;
            }
            if (canRotate()) updateXY(direction);
            KeyHandler.upPressed = false;
        }

        // CHECK COLLISION BEFORE MOVEMENT
        checkMovementCollision();

        // SOFT DROP
        if (KeyHandler.downPressed && !downCollision) {
            for (Block block : b) block.y += Block.SIZE;
            autoDropCounter = 0;
            KeyHandler.downPressed = false;
        }

        // MOVE LEFT
        if (KeyHandler.leftPressed && !leftCollision) {
            for (Block block : b) block.x -= Block.SIZE;
            KeyHandler.leftPressed = false;
        }

        // MOVE RIGHT
        if (KeyHandler.rightPressed && !rightCollision) {
            for (Block block : b) block.x += Block.SIZE;
            KeyHandler.rightPressed = false;
        }

        // AUTO DROP
        autoDropCounter++;
        if (autoDropCounter >= PlayManager.dropInterval && !downCollision) {
            for (Block block : b) block.y += Block.SIZE;
            autoDropCounter = 0;
        }
    }

    // -----------------------------
    // DRAW MINO
    // -----------------------------
    public void draw(Graphics2D g2d) {
        int margin = 2;
        for (Block block : b) {
            g2d.setColor(block.color);
            g2d.fillRect(block.x + margin, block.y + margin, Block.SIZE - margin * 2, Block.SIZE - margin * 2);
        }
    }
}
