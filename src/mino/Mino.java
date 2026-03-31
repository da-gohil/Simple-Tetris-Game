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
    public boolean active = true;

    public void create(Color color) {
        for (int i = 0; i < 4; i++) {
            b[i] = new Block(color);
            tempB[i] = new Block(color);
        }
    }

    public void setXY(int x, int y) {
        // Implemented in subclasses
    }

    // Apply tempB positions to b and update direction — no collision check here.
    // Call checkRotationCollision() before calling this.
    public void updateXY(int newDirection) {
        for (int i = 0; i < 4; i++) {
            b[i].x = tempB[i].x;
            b[i].y = tempB[i].y;
        }
        this.direction = newDirection;
        if (this.direction > 4) this.direction = 1;
        else if (this.direction < 1) this.direction = 4;
    }

    // Subclasses set tempB[] to the rotated positions for each direction.
    // Do NOT call updateXY() inside these — rotation application is handled in update().
    public void getDirection1() {}
    public void getDirection2() {}
    public void getDirection3() {}
    public void getDirection4() {}

    // -----------------------------
    // COLLISION CHECKS
    // -----------------------------

    // Checks whether the current b[] positions collide with walls or static blocks
    // for movement purposes (one step ahead: left, right, down).
    public void checkMovementCollision() {
        leftCollision = rightCollision = downCollision = false;

        // Wall / boundary collision
        for (int i = 0; i < b.length; i++) {
            if (b[i].x == PlayManager.left_x)               leftCollision  = true;
            if (b[i].x + Block.SIZE == PlayManager.right_x)  rightCollision = true;
            if (b[i].y + Block.SIZE == PlayManager.bottom_y)  downCollision  = true;
        }

        // Static block collision — check one step in each direction
        for (int i = 0; i < PlayManager.staticBlocks.size(); i++) {
            int targetX = PlayManager.staticBlocks.get(i).x;
            int targetY = PlayManager.staticBlocks.get(i).y;

            for (int i1 = 0; i1 < b.length; i1++) {
                // Moving down: bottom of b[i1] would land on top of target
                if (b[i1].x == targetX && b[i1].y + Block.SIZE == targetY)
                    downCollision = true;
                // Moving left: left edge of b[i1] would enter target column
                if (b[i1].x - Block.SIZE == targetX && b[i1].y == targetY)
                    leftCollision = true;
                // Moving right: right edge of b[i1] would enter target column
                if (b[i1].x + Block.SIZE == targetX && b[i1].y == targetY)
                    rightCollision = true;
            }
        }
    }

    // Checks whether the rotated tempB[] positions collide with walls or static blocks.
    // Uses overlap detection (exact position match) for static blocks.
    public void checkRotationCollision() {
        leftCollision = rightCollision = downCollision = false;

        // Boundary collision on rotated positions
        for (int i = 0; i < tempB.length; i++) {
            if (tempB[i].x < PlayManager.left_x)              leftCollision  = true;
            if (tempB[i].x + Block.SIZE > PlayManager.right_x) rightCollision = true;
            if (tempB[i].y + Block.SIZE > PlayManager.bottom_y) downCollision = true;
            if (tempB[i].y < PlayManager.top_y)               downCollision  = true; // above top
        }

        // Static block overlap — rotation would place a block inside an existing block
        for (int i = 0; i < PlayManager.staticBlocks.size(); i++) {
            int targetX = PlayManager.staticBlocks.get(i).x;
            int targetY = PlayManager.staticBlocks.get(i).y;
            for (int i1 = 0; i1 < tempB.length; i1++) {
                if (tempB[i1].x == targetX && tempB[i1].y == targetY) {
                    downCollision = true; // reuse flag to block the rotation
                }
            }
        }
    }

    // -----------------------------
    // UPDATE MINO (MOVEMENT & ROTATION)
    // -----------------------------
    public void update() {

        // ROTATE — set tempB via getDirectionN(), validate, then apply
        if (KeyHandler.upPressed) {
            int nextDir = direction + 1;
            if (nextDir > 4) nextDir = 1;

            switch (nextDir) {
                case 1: getDirection1(); break;
                case 2: getDirection2(); break;
                case 3: getDirection3(); break;
                case 4: getDirection4(); break;
            }

            checkRotationCollision();

            if (!leftCollision && !rightCollision && !downCollision) {
                updateXY(nextDir);
            }

            KeyHandler.upPressed = false;
        }

        // Refresh movement collision state before applying inputs
        checkMovementCollision();

        // HARD DROP — slam piece to the bottom instantly
        if (KeyHandler.enterPressed) {
            while (!downCollision) {
                for (Block block : b) block.y += Block.SIZE;
                checkMovementCollision();
            }
            KeyHandler.enterPressed = false;
        }

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

        if (downCollision) {
            active = false;
        } else {
            // AUTO DROP
            autoDropCounter++;
            if (autoDropCounter >= PlayManager.dropInterval) {
                for (Block block : b) block.y += Block.SIZE;
                autoDropCounter = 0;
            }
        }
    }

    // -----------------------------
    // DRAW MINO
    // -----------------------------
    public void draw(Graphics2D g2d) {
        int margin = 2;
        for (Block block : b) {
            g2d.setColor(block.color);
            g2d.fillRect(block.x + margin, block.y + margin,
                         Block.SIZE - margin * 2, Block.SIZE - margin * 2);
        }
    }
}
