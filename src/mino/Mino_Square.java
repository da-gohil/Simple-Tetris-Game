package mino;

import java.awt.Color;

public class Mino_Square extends Mino {

    public Mino_Square() {
        create(Color.YELLOW); // Square piece is usually yellow
    }

    @Override
    public void setXY(int x, int y) {
        // 0 1
        // 2 3

        b[0].x = x;
        b[0].y = y;

        b[1].x = x + Block.SIZE;
        b[1].y = y;

        b[2].x = x;
        b[2].y = y + Block.SIZE;

        b[3].x = x + Block.SIZE;
        b[3].y = y + Block.SIZE;
    }

    // -------------------------------
    // DIRECTION 1
    // -------------------------------
    @Override
    public void getDirection1() {
        // Square does not rotate
        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;

        tempB[1].x = b[1].x;
        tempB[1].y = b[1].y;

        tempB[2].x = b[2].x;
        tempB[2].y = b[2].y;

        tempB[3].x = b[3].x;
        tempB[3].y = b[3].y;

        updateXY(1);
    }

    @Override
    public void getDirection2() {
        getDirection1();
    }

    @Override
    public void getDirection3() {
        getDirection1();
    }

    @Override
    public void getDirection4() {
        getDirection1();
    }
}
