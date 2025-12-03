package mino;

import java.awt.Color;

public class Mino_T extends Mino {

    public Mino_T() {
        create(Color.MAGENTA); // T piece is often magenta
    }

    @Override
    public void setXY(int x, int y) {
        // DIRECTION 1 (default)
        //   1 0 2
        //       3

        b[0].x = x;
        b[0].y = y;

        b[1].x = x - Block.SIZE;
        b[1].y = y;

        b[2].x = x + Block.SIZE;
        b[2].y = y;

        b[3].x = x;
        b[3].y = y + Block.SIZE;
    }

    // -------------------------------
    // DIRECTION 1
    // -------------------------------
    @Override
    public void getDirection1() {
        //   1 0 2
        //       3

        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;

        tempB[1].x = b[0].x - Block.SIZE;
        tempB[1].y = b[0].y;

        tempB[2].x = b[0].x + Block.SIZE;
        tempB[2].y = b[0].y;

        tempB[3].x = b[0].x;
        tempB[3].y = b[0].y + Block.SIZE;

        updateXY(1);
    }

    // -------------------------------
    // DIRECTION 2
    // -------------------------------
    @Override
    public void getDirection2() {
        //     1
        //   3 0
        //     2

        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;

        tempB[1].x = b[0].x;
        tempB[1].y = b[0].y - Block.SIZE;

        tempB[2].x = b[0].x;
        tempB[2].y = b[0].y + Block.SIZE;

        tempB[3].x = b[0].x - Block.SIZE;
        tempB[3].y = b[0].y;

        updateXY(2);
    }

    // -------------------------------
    // DIRECTION 3
    // -------------------------------
    @Override
    public void getDirection3() {
        //       3
        //   1 0 2

        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;

        tempB[1].x = b[0].x - Block.SIZE;
        tempB[1].y = b[0].y;

        tempB[2].x = b[0].x + Block.SIZE;
        tempB[2].y = b[0].y;

        tempB[3].x = b[0].x;
        tempB[3].y = b[0].y - Block.SIZE;

        updateXY(3);
    }

    // -------------------------------
    // DIRECTION 4
    // -------------------------------
    @Override
    public void getDirection4() {
        //     2
        //   0 3
        //     1

        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;

        tempB[1].x = b[0].x;
        tempB[1].y = b[0].y + Block.SIZE;

        tempB[2].x = b[0].x;
        tempB[2].y = b[0].y - Block.SIZE;

        tempB[3].x = b[0].x + Block.SIZE;
        tempB[3].y = b[0].y;

        updateXY(4);
    }
}
