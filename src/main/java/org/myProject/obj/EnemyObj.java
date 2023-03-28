package org.myProject.obj;
/*
EnemyObj class - Contain enemy basic functions.
Member: Viet Nguyen
TODO 1: Complete the checkCollision method.
TODO 2: Make fire function to attack.
 */
import org.myProject.GameWin;
import org.myProject.utils.GameUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Timer;

public class EnemyObj extends GameObj implements ActionListener {

    private Timer timer;
    int distance;

    public EnemyObj(Image img, int x, int y, int width, int height, double speed, GameWin frame) {
        super(img, x, y, width, height, speed, frame);
        this.timer = new Timer(20, this);
        this.timer.start();
    }

    public boolean collidesWith(GameObj otherObj){
        Rectangle rect1 = this.getrect();
        Rectangle rect2 = otherObj.getrect();
        return rect1.intersects(rect2);
    }

    public void checkCollision(){
        List<GameObj> gameObjList = GameUtils.gameObjList;
        for (GameObj obj : gameObjList) {
            if (this.collidesWith(obj)) {
                /**
                 takeDamage(obj.getDamage());
                 */
                break;
            }
        }
    }

    public void moveDown(int distance) {
        this.distance = distance;
        if (this.y < distance) { // move down until the enemy reaches y-coordinate 100
            this.y += this.speed;
        } else if (this.y == distance) { // stop moving when the enemy reaches y-coordinate 100
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            moveDown(this.distance);
        }
    }
}

