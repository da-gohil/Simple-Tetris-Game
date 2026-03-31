package mino;

import java.awt.Color;

// Z-piece (was incorrectly identical to Z1/S-piece — fixed here)
public class Mino_Z2 extends Mino {

    public Mino_Z2() {
        create(Color.GREEN);
    }

    @Override
    public void setXY(int x, int y) {
        // Direction 1:
        // 1 0
        //   2 3
        b[0].x = x;              b[0].y = y;
        b[1].x = x - Block.SIZE; b[1].y = y;
        b[2].x = x;              b[2].y = y + Block.SIZE;
        b[3].x = x + Block.SIZE; b[3].y = y + Block.SIZE;
    }

    @Override
    public void getDirection1() {
        // 1 0
        //   2 3
        tempB[0].x = b[0].x;              tempB[0].y = b[0].y;
        tempB[1].x = b[0].x - Block.SIZE; tempB[1].y = b[0].y;
        tempB[2].x = b[0].x;              tempB[2].y = b[0].y + Block.SIZE;
        tempB[3].x = b[0].x + Block.SIZE; tempB[3].y = b[0].y + Block.SIZE;
    }

    @Override
    public void getDirection2() {
        //   1
        // 2 0
        // 3
        tempB[0].x = b[0].x;              tempB[0].y = b[0].y;
        tempB[1].x = b[0].x;              tempB[1].y = b[0].y - Block.SIZE;
        tempB[2].x = b[0].x - Block.SIZE; tempB[2].y = b[0].y;
        tempB[3].x = b[0].x - Block.SIZE; tempB[3].y = b[0].y + Block.SIZE;
    }

    @Override
    public void getDirection3() {
        getDirection1(); // Z-piece has 2 unique rotations
    }

    @Override
    public void getDirection4() {
        getDirection2();
    }
}
