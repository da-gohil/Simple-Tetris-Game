package main;

import mino.*;

import java.awt.*;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

public class PlayManager {

    // Play area dimensions
    final int WIDTH  = 360;
    final int HEIGHT = 600;

    public static int left_x, right_x, top_y, bottom_y;

    // Active and next piece
    Mino currentMino;
    final int MINO_START_X;
    final int MINO_START_Y;

    Mino nextMino;
    final int NEXT_MINO_X;
    final int NEXT_MINO_Y;

    public static ArrayList<Block> staticBlocks = new ArrayList<>();

    // Auto-drop speed (frames between drops). Decreases as level increases.
    public static int dropInterval = 48;

    // Game state
    public boolean gameOver = false;

    // Score tracking
    int score  = 0;
    int level  = 1;
    int lines  = 0;

    // Right-panel layout constants (computed in constructor)
    private int panelX;   // left edge of right info panel

    public PlayManager() {
        left_x  = GamePanel.WIDTH / 2 - WIDTH / 2;
        right_x = left_x + WIDTH;
        top_y   = 50;
        bottom_y = top_y + HEIGHT;

        MINO_START_X = left_x + WIDTH / 2 - Block.SIZE;
        MINO_START_Y = top_y + Block.SIZE;

        // Position the NEXT preview box to the right of the play area
        panelX = right_x + 60;
        NEXT_MINO_X = panelX + 75;
        NEXT_MINO_Y = top_y + 160;

        currentMino = pickMino();
        currentMino.setXY(MINO_START_X, MINO_START_Y);

        nextMino = pickMino();
        nextMino.setXY(NEXT_MINO_X, NEXT_MINO_Y);
    }

    private Mino pickMino() {
        Mino mino;
        switch (new Random().nextInt(7)) {
            case 0: mino = new Mino_L1();     break;
            case 1: mino = new Mino_L2();     break;
            case 2: mino = new Mino_Square(); break;
            case 3: mino = new Mino_T();      break;
            case 4: mino = new Mino_Z1();     break;
            case 5: mino = new Mino_Z2();     break;
            default: mino = new Mino_Bar();   break;
        }
        return mino;
    }

    public void update() {
        if (gameOver) return;

        if (!currentMino.active) {
            // Lock the piece into the static block list
            for (int i = 0; i < 4; i++) {
                staticBlocks.add(currentMino.b[i]);
            }

            // Check for game over: any locked block is at or above the spawn row
            for (int i = 0; i < 4; i++) {
                if (currentMino.b[i].y <= top_y) {
                    gameOver = true;
                    return;
                }
            }

            // Clear completed lines and update score/level
            checkLines();

            // Spawn next piece
            currentMino = nextMino;
            currentMino.setXY(MINO_START_X, MINO_START_Y);
            nextMino = pickMino();
            nextMino.setXY(NEXT_MINO_X, NEXT_MINO_Y);

        } else {
            currentMino.update();
        }
    }

    private void checkLines() {
        int linesCleared = 0;

        // Scan rows from bottom to top
        int row = bottom_y - Block.SIZE;
        while (row >= top_y) {
            int count = 0;
            for (Block b : staticBlocks) {
                if (b.y == row) count++;
            }

            int cols = WIDTH / Block.SIZE; // 12 columns
            if (count == cols) {
                // Remove all blocks in this completed row
                linesCleared++;
                final int clearedRow = row;
                staticBlocks.removeIf(b -> b.y == clearedRow);

                // Shift everything above this row down by one block
                for (Block b : staticBlocks) {
                    if (b.y < clearedRow) b.y += Block.SIZE;
                }
                // Stay at same row index — the blocks above shifted down into it
            } else {
                row -= Block.SIZE;
            }
        }

        if (linesCleared > 0) {
            // Classic Tetris scoring
            switch (linesCleared) {
                case 1: score +=  100 * level; break;
                case 2: score +=  300 * level; break;
                case 3: score +=  500 * level; break;
                default: score += 800 * level; break; // Tetris!
            }
            lines += linesCleared;
            level  = lines / 10 + 1;
            // Speed up: subtract 5 frames per level, floor at 5
            dropInterval = Math.max(5, 48 - (level - 1) * 5);
        }
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // ── Play area background ──────────────────────────────
        g2.setColor(new Color(20, 20, 30));
        g2.fillRect(left_x, top_y, WIDTH, HEIGHT);

        // Grid lines (subtle)
        g2.setColor(new Color(40, 40, 55));
        for (int x = left_x; x < right_x; x += Block.SIZE) {
            g2.drawLine(x, top_y, x, bottom_y);
        }
        for (int y = top_y; y <= bottom_y; y += Block.SIZE) {
            g2.drawLine(left_x, y, right_x, y);
        }

        // ── Ghost piece ────────────────────────────────────────
        if (currentMino != null && !gameOver) {
            int[] gx = new int[4];
            int[] gy = new int[4];
            for (int i = 0; i < 4; i++) {
                gx[i] = currentMino.b[i].x;
                gy[i] = currentMino.b[i].y;
            }

            boolean hit = false;
            while (!hit) {
                for (int i = 0; i < 4; i++) gy[i] += Block.SIZE;
                // Check boundaries
                for (int i = 0; i < 4 && !hit; i++) {
                    if (gy[i] + Block.SIZE > bottom_y) hit = true;
                }
                // Check static blocks
                for (Block sb : staticBlocks) {
                    for (int i = 0; i < 4 && !hit; i++) {
                        if (gx[i] == sb.x && gy[i] + Block.SIZE == sb.y) hit = true;
                    }
                }
            }
            // Step back: the ghost is one step before the collision
            for (int i = 0; i < 4; i++) gy[i] -= Block.SIZE;

            // Only draw ghost if it differs from the live piece
            boolean different = false;
            for (int i = 0; i < 4; i++) {
                if (gy[i] != currentMino.b[i].y) { different = true; break; }
            }
            if (different) {
                g2.setColor(new Color(80, 80, 80));
                for (int i = 0; i < 4; i++) {
                    g2.drawRect(gx[i] + 2, gy[i] + 2, Block.SIZE - 4, Block.SIZE - 4);
                }
            }
        }

        // ── Static blocks ──────────────────────────────────────
        for (Block b : staticBlocks) b.draw(g2);

        // ── Current piece ──────────────────────────────────────
        if (currentMino != null) currentMino.draw(g2);

        // ── Play area border ───────────────────────────────────
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3f));
        g2.drawRect(left_x - 3, top_y - 3, WIDTH + 6, HEIGHT + 6);
        g2.setStroke(new BasicStroke(1f));

        // ── Right panel — NEXT preview ─────────────────────────
        int nextBoxX = panelX;
        int nextBoxY = top_y + 30;
        int nextBoxW = 200;
        int nextBoxH = 200;

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 22));
        g2.drawString("NEXT", nextBoxX + 65, nextBoxY - 8);
        g2.setColor(new Color(50, 50, 70));
        g2.fillRect(nextBoxX, nextBoxY, nextBoxW, nextBoxH);
        g2.setColor(Color.WHITE);
        g2.drawRect(nextBoxX, nextBoxY, nextBoxW, nextBoxH);

        if (nextMino != null) nextMino.draw(g2);

        // ── Right panel — score / level / lines ────────────────
        int statY = nextBoxY + nextBoxH + 40;
        int statX = panelX + 20;

        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.setColor(Color.LIGHT_GRAY);
        g2.drawString("SCORE",  statX, statY);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 26));
        g2.drawString(String.format("%,d", score), statX, statY + 32);

        statY += 80;
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.setColor(Color.LIGHT_GRAY);
        g2.drawString("LEVEL", statX, statY);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 26));
        g2.drawString(String.valueOf(level), statX, statY + 32);

        statY += 80;
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.setColor(Color.LIGHT_GRAY);
        g2.drawString("LINES", statX, statY);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 26));
        g2.drawString(String.valueOf(lines), statX, statY + 32);

        // ── Controls hint ──────────────────────────────────────
        statY += 90;
        g2.setFont(new Font("Arial", Font.PLAIN, 13));
        g2.setColor(new Color(140, 140, 140));
        g2.drawString("WASD / Arrows: move", statX, statY);
        g2.drawString("W / Up: rotate",      statX, statY + 18);
        g2.drawString("Enter: hard drop",    statX, statY + 36);
        g2.drawString("Space: pause",        statX, statY + 54);
        g2.drawString("R: restart",          statX, statY + 72);

        // ── Pause overlay ──────────────────────────────────────
        if (KeyHandler.pausePressed && !gameOver) {
            g2.setColor(new Color(0, 0, 0, 160));
            g2.fillRect(left_x, top_y, WIDTH, HEIGHT);
            g2.setColor(Color.YELLOW);
            g2.setFont(new Font("Arial", Font.BOLD, 54));
            int pw = g2.getFontMetrics().stringWidth("PAUSED");
            g2.drawString("PAUSED", left_x + (WIDTH - pw) / 2, top_y + HEIGHT / 2);
        }

        // ── Game over overlay ──────────────────────────────────
        if (gameOver) {
            g2.setColor(new Color(0, 0, 0, 180));
            g2.fillRect(left_x, top_y, WIDTH, HEIGHT);

            g2.setColor(Color.RED);
            g2.setFont(new Font("Arial", Font.BOLD, 50));
            String go = "GAME OVER";
            int gw = g2.getFontMetrics().stringWidth(go);
            g2.drawString(go, left_x + (WIDTH - gw) / 2, top_y + HEIGHT / 2 - 20);

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.PLAIN, 24));
            String restart = "Press R to restart";
            int rw = g2.getFontMetrics().stringWidth(restart);
            g2.drawString(restart, left_x + (WIDTH - rw) / 2, top_y + HEIGHT / 2 + 30);

            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            String finalScore = "Score: " + String.format("%,d", score);
            int sw = g2.getFontMetrics().stringWidth(finalScore);
            g2.drawString(finalScore, left_x + (WIDTH - sw) / 2, top_y + HEIGHT / 2 + 65);
        }
    }
}
