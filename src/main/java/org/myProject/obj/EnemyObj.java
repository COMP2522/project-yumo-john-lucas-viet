package org.myProject.obj;


import org.myProject.GameWin;
import org.myProject.utils.GameUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Timer;

import static org.myProject.utils.GameUtils.bulletimg;
/**
 EnemyObj class - this class represent the enemy object in the game.
 This class contain enemy basic functions.
 @author : Viet Nguyen
 @version : 1.0
 */
public class EnemyObj extends GameObj implements ActionListener {

    private Timer timer;
    private boolean isActive;
    int distance;
    private long lastShotTime = 0;

    public EnemyObj(Image img, int x, int y, int width, int height, double speed, GameWin frame) {
        super(img, x, y, width, height, speed, frame);
        this.timer = new Timer(20, this);
        this.timer.start();
        this.isActive = true;
    }

    private boolean collidesWith(GameObj otherObj){
        Rectangle rect1 = this.getrect();
        Rectangle rect2 = otherObj.getrect();
        return rect1.intersects(rect2);
    }

    //TODO 1: Complete the checkCollision method
    public void checkCollision(){
        List<GameObj> gameObjList = GameUtils.gameObjList;
        for (GameObj obj : gameObjList) {
            if (obj instanceof BulletObj && !((BulletObj) obj).isEnemyBullet && this.collidesWith(obj)) {
                this.isActive = false;
                GameUtils.removeobjList.add(this);
//                ((BulletObj) obj).setActive(false);
                break;
            }
        }
    }

    public void moveDown(int distance) {
        this.distance = distance;
        if (this.y < distance) { // move down until the enemy reaches y-coordinate 100
            this.y += this.speed;
        }
        if(this.y == distance && this.isActive){
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
            bullet.setY(this.getY() + 30);
            bullet.setX(this.getX() + 20);
            GameUtils.bulletObjList.add(new BulletObj(GameUtils.shellimg,this.getX()+4,this.getY()-16,14,29,12,frame, true));
            GameUtils.gameObjList.add(GameUtils.bulletObjList.get(GameUtils.bulletObjList.size()-1));

            lastShotTime = currentTime;
        }
    }
}

