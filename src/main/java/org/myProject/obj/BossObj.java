package org.myProject.obj;

import org.myProject.GameWin;
import org.myProject.utils.GameUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Timer;

import static org.myProject.utils.GameUtils.bulletimg;

public class BossObj extends GameObj{
    private Timer timer;
    private boolean isActive;
    private int distance;
    private int damage;
    private long lastShotTime = 0;
    public GameWin window;
    public BossObj(Image img, int x, int y, int width, int height, double speed, GameWin frame) {
        super(img, x, y, width, height, speed, frame);
        this.window = frame;
        this.isActive = true;
    }

    public void paintself(Graphics gImage) {
        super.paintself(gImage);
        checkCollision(window.getPlaneobj());
        removeEnemy();
        fire();
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
    private void checkCollision(PlaneObj planeobj){
        List<GameObj> gameObjList = GameUtils.gameObjList;
        for (GameObj obj : gameObjList) {
            if (obj instanceof BulletObj && !((BulletObj) obj).isEnemyBullet && this.collidesWith(obj)) {
                explode();
                if (this.isActive) {
                    this.isActive = false;
                    planeobj.setScore(planeobj.getScore() + 25);
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
    private void fire(){
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
    private void removeBoss(){
        if(!this.isActive) {
            GameUtils.enemyObjList.remove(this);
            this.window.setEnemyCount(1);
        }
    }
}
