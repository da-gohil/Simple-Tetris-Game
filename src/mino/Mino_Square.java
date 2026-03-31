package mino;

import java.awt.Color;

public class Mino_Square extends Mino {

    public Mino_Square() {
        create(Color.YELLOW);
    }

    @Override
    public void setXY(int x, int y) {
        // 0 1
        // 2 3
        b[0].x = x;              b[0].y = y;
        b[1].x = x + Block.SIZE; b[1].y = y;
        b[2].x = x;              b[2].y = y + Block.SIZE;
        b[3].x = x + Block.SIZE; b[3].y = y + Block.SIZE;
    }

    @Override
    public void getDirection1() {
        // Square does not rotate — copy current positions to tempB
        for (int i = 0; i < 4; i++) {
            tempB[i].x = b[i].x;
            tempB[i].y = b[i].y;
        }
    }

    @Override public void getDirection2() { getDirection1(); }
    @Override public void getDirection3() { getDirection1(); }
    @Override public void getDirection4() { getDirection1(); }
}
