package org.myProject.obj;


import org.myProject.GameWin;
import org.myProject.utils.GameUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ConcurrentModificationException;
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
    int damage;
    private long lastShotTime = 0;

    public GameWin window;

    /**
     * Constructor for EnemyObj class.
     *
     * @param img    The image of the enemy object.
     * @param x      The x-coordinate of the enemy object.
     * @param y      The y-coordinate of the enemy object.
     * @param width  The width of the enemy object.
     * @param height The height of the enemy object.
     * @param speed  The speed of the enemy object.
     * @param frame  The game window frame.
     */
    public EnemyObj(Image img, int x, int y, int width, int height, double speed, GameWin frame) {
        super(img, x, y, width, height, speed, frame);
        this.timer = new Timer(20, this);
        this.timer.start();
        this.isActive = true;
        this.damage = 1;
        this.window = frame;
    }

    /**
     * Checks if the enemy object collides with another game object.
     *
     * @param otherObj The game object to check collision with.
     * @return True if the enemy object collides with the other game object, false otherwise.
     */
    private boolean collidesWith(GameObj otherObj){
        Rectangle rect1 = this.getrect();
        Rectangle rect2 = otherObj.getrect();
        return rect1.intersects(rect2);
    }

    /**
     * Checks collision between the enemy object and other game objects.
     */
    public void checkCollision(PlaneObj planeobj){
        List<GameObj> gameObjList = GameUtils.gameObjList;
        for (GameObj obj : gameObjList) {
            if (obj instanceof BulletObj && !((BulletObj) obj).isEnemyBullet && this.collidesWith(obj)) {
                //this.window.setEnemyCount(1);
                explode();
                if (this.isActive) {
                    this.isActive = false;
                    planeobj.setScore(planeobj.getScore() + 1);
                }
                GameUtils.removeobjList.add(this);
                break;
            }
        }
    }


    private void explode() {
        ExplodeObj explodeobj = new ExplodeObj(this.x,this.y);
        GameUtils.explodeObjList.add(explodeobj);
        GameUtils.removeobjList.add(explodeobj);
    }

    /**
     * Moves the enemy object down.
     *
     * @param distance The distance the enemy object should move down.
     */
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

    /**
     * Fires a bullet from the enemy object.
     */
    public void fire(){
        long currentTime = System.nanoTime();
        long timeSinceLastShot = currentTime - lastShotTime;

        if (timeSinceLastShot >= 900000000) {
            BulletObj bullet = new BulletObj(bulletimg, this.x, this.y, 5, 10, 10, this.frame, true);
            bullet.setY(this.getY() + 30);
            bullet.setX(this.getX() + 20);
            GameUtils.bulletObjList.add(new BulletObj(GameUtils.shellimg,this.getX()+4,this.getY()-16,14,29,12,frame, true));
            GameUtils.gameObjList.add(GameUtils.bulletObjList.get(GameUtils.bulletObjList.size()-1));
            bullet.removeBullet();
            lastShotTime = currentTime;
        }
    }

    /**
     * Remove an enemy plane when it's no longer active
     */
    public void removeEnemy(){
        if(!this.isActive) {
            GameUtils.enemyObjList.remove(this);
            this.window.setEnemyCount(1);
        }
    }
}

