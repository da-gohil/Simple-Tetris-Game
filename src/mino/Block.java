package mino;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Block extends Rectangle {
    public int x, y;
    public static final int SIZE = 30;
    public Color color;

    public Block(Color c){
        this.color = c;
    }

    public void draw(Graphics2D g2d){
        int margins = 2;
        g2d.setColor(color);
        g2d.fillRect(x+margins, y+margins, SIZE-(margins * 2), SIZE -  (margins * 2));
    }
}
