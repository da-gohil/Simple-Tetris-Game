package mino;

import main.PlayManager;

import java.awt.*;

//this is the super class for all the tetrominos
public class Mino {

    public Block b[] = new Block[4];
    public Block tempB[] = new Block[4];
    int autoDropCounter = 0;

    public void create(Color color){
        b[0] = new Block(color);
        b[1] = new Block(color);
        b[2] = new Block(color);
        b[3] = new Block(color);
        tempB[0] = new Block(color);
        tempB[1] = new Block(color);
        tempB[2] = new Block(color);
        tempB[3] = new Block(color);
    }

    public void setXY(int x, int y){}
    public void updateXY(int direction){}
    public void update(){
        autoDropCounter++;
        if(autoDropCounter == PlayManager.dropInterval){
            //mino to go down
            b[0].y += Block.SIZE;
            b[1].y += Block.SIZE;
            b[2].y += Block.SIZE;
            b[3].y += Block.SIZE;
            autoDropCounter = 0;
        }
    }

    public void draw(Graphics2D g2d){

        int margin = 2;

        g2d.setColor(b[0].color);
        g2d.fillRect(b[0].x + margin, b[0].y + margin, Block.SIZE - (margin*2), Block.SIZE - (margin*2));
        g2d.fillRect(b[1].x + margin, b[1].y + margin, Block.SIZE - (margin*2), Block.SIZE - (margin*2));
        g2d.fillRect(b[2].x + margin, b[2].y + margin, Block.SIZE - (margin*2), Block.SIZE - (margin*2));
        g2d.fillRect(b[3].x + margin, b[3].y + margin, Block.SIZE - (margin*2), Block.SIZE - (margin*2));

    }
}
