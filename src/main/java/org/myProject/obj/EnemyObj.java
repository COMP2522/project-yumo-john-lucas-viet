package org.myProject.obj;
/*
EnemyObj class - Contain enemy basic functions.
Member: Viet Nguyen
TODO 1: Complete the checkCollision method.
 */
import org.myProject.GameWin;
import org.myProject.utils.GameUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Timer;

import static org.myProject.utils.GameUtils.bulletimg;

public class EnemyObj extends GameObj implements ActionListener {

    private Timer timer;
    int distance;
    private long lastShotTime = 0;

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
        }
        if(this.y == distance){
            fire();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            moveDown(this.distance);
        }
    }

    public void fire(){
        long currentTime = System.nanoTime();
        long timeSinceLastShot = currentTime - lastShotTime;

        if (timeSinceLastShot >= 900000000) {
            BulletObj bullet = new BulletObj(bulletimg, this.x, this.y, 5, 10, 10, this.frame, true);
            bullet.setY(this.getY() + 20);
            bullet.setX(this.getX() + 20);
            GameUtils.bulletObjList.add(new BulletObj(GameUtils.shellimg,this.getX()+4,this.getY()-16,14,29,12,frame, true));
            GameUtils.gameObjList.add(GameUtils.bulletObjList.get(GameUtils.bulletObjList.size()-1));

            lastShotTime = currentTime;
        }
    }
}

