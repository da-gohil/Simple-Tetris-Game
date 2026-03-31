package mino;

import java.awt.Color;

// S-piece (green in classic Tetris, but kept red here)
public class Mino_Z1 extends Mino {

    public Mino_Z1() {
        create(Color.RED);
    }

    @Override
    public void setXY(int x, int y) {
        // Direction 1:
        //   0 1
        // 2 3
        b[0].x = x;              b[0].y = y;
        b[1].x = x + Block.SIZE; b[1].y = y;
        b[2].x = x - Block.SIZE; b[2].y = y + Block.SIZE;
        b[3].x = x;              b[3].y = y + Block.SIZE;
    }

    @Override
    public void getDirection1() {
        //   0 1
        // 2 3
        tempB[0].x = b[0].x;              tempB[0].y = b[0].y;
        tempB[1].x = b[0].x + Block.SIZE; tempB[1].y = b[0].y;
        tempB[2].x = b[0].x - Block.SIZE; tempB[2].y = b[0].y + Block.SIZE;
        tempB[3].x = b[0].x;              tempB[3].y = b[0].y + Block.SIZE;
    }

    @Override
    public void getDirection2() {
        // 2
        // 0 1
        //   3
        tempB[0].x = b[0].x;              tempB[0].y = b[0].y;
        tempB[1].x = b[0].x + Block.SIZE; tempB[1].y = b[0].y;
        tempB[2].x = b[0].x;              tempB[2].y = b[0].y - Block.SIZE;
        tempB[3].x = b[0].x + Block.SIZE; tempB[3].y = b[0].y + Block.SIZE;
    }

    @Override
    public void getDirection3() {
        getDirection1(); // S-piece has 2 unique rotations
    }

    @Override
    public void getDirection4() {
        getDirection2();
    }
}
