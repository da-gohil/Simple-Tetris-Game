package mino;

import java.awt.*;

public class Block extends Rectangle {
    public int x, y;
    public static final int SIZE = 30;
    public Color color;

    public Block(Color c){
        this.color = c;
    }

    public void draw(Graphics2D g2d){
        g2d.setColor(color);
        g2d.fillRect(x, y, SIZE, SIZE);
    }
}
