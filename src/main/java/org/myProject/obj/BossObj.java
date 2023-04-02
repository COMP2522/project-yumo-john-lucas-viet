package org.myProject.obj;

import org.myProject.GameWin;
import org.myProject.utils.GameUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Timer;

import static org.myProject.utils.GameUtils.*;

/**
 BossObj class - this class represent the Boss object in the game.
 This class contain boss's basic functions.
 @author : Viet Nguyen
 @version : 1.0
 */

public class BossObj extends GameObj implements ActionListener{
    private Timer timer;
    private boolean isActive;
    private int distance;
    private int damage = 100;
    private long lastShotTime = 0;
    public GameWin window;
    private double hitpoints = 90;
    private int direction = 1;

    /**
     * Creates a new boss object with the given image, position, size, speed, and game window.
     *
     * @param img the image of the boss object
     * @param x the x-coordinate of the boss object
     * @param y the y-coordinate of the boss object
     * @param width the width of the boss object
     * @param height the height of the boss object
     * @param speed the speed of the boss object
     * @param frame the game window that contains the boss object
     */
    public BossObj(Image img, int x, int y, int width, int height, double speed, GameWin frame) {
        super(img, x, y, width, height, speed, frame);
        this.timer = new Timer(10, this);
        timer.start();
        this.window = frame;
        this.isActive = true;
        this.window.setBossAppear(false);
    }

    /**
     * Draws the boss object on the given graphics context, and updates its position and state.
     *
     * @param gImage the graphics context on which to draw the boss object
     */
    public void paintself(Graphics gImage) {
        gImage.drawImage(bossimg, this.x-75, this.y-50, window);
        moveDown(100);
        checkCollision(window.getPlaneobj(), gImage);
        removeBoss();
    }

    /**
     * Getter for Boss collision damage
     */
    public int getDamage(){
        return this.damage;
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
    private void checkCollision(PlaneObj planeobj, Graphics gImage){
        List<GameObj> gameObjList = GameUtils.gameObjList;
        for (GameObj obj : gameObjList) {
            //Decrement boss's hit-points by one everytime it get hit by player's bullet
            if (obj instanceof BulletObj && !((BulletObj) obj).isEnemyBullet && this.collidesWith(obj)) {
                this.hitpoints -= ((BulletObj) obj).getDamage();
                if(this.hitpoints == 0){
                    planeobj.setScore(planeobj.getScore() + 30);
                    this.isActive = false;
                    gImage.drawImage(GameUtils.explodeimg, this.x, this.y, null);
                    GameUtils.removeobjList.add(this);
                    break;
                }
            }

            //If boss plane collide directly with player's plane it will be deleted.
            if (obj instanceof PlaneObj && this.collidesWith(obj)){
                this.isActive = false;
                gImage.drawImage(GameUtils.explodeimg, this.x, this.y, null);
                GameUtils.removeobjList.add(this);
                break;
            }
        }
    }

    /**
     * Moves the enemy object vertically down.
     *
     * @param distance The distance the enemy object should move down.
     */
    private void moveDown(int distance) {
        this.distance = distance;
        if (this.y < distance) { // move down until the enemy reaches y-coordinate 100
            this.y += this.speed;
        }
        if(this.y >= distance && this.isActive){
            fire();
            moveSideWay();
        }
    }

    /**
     * Move the boss horizontally right then left.
     * Direction will change if the boss hit the border of the window.
     */
    private void moveSideWay(){
        if(this.x >= window.getWidth() - this.getWidth()){
            direction *= -1;
        }
        if(this.x <= 0){
            direction *=-1;
        }
        this.x += direction;
    }

    /**
     * Perform the method every 1 second.
     * Make the movement look smoother.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            moveDown(this.distance);
            moveSideWay();
        }
    }

    /**
     * Fires a bullet from the boss object.
     * Firing two beams, more beam than the enemy
     */
    private void fire() {
        long currentTime = System.nanoTime();
        long timeSinceLastShot = currentTime - lastShotTime;

        if (timeSinceLastShot >= 900000000) {
            for(int i = 0; i < 2; i++) {
                BulletObj bullet = new BulletObj(reverseShell, this.x, this.y, 5, 10, 14, this.frame, true);
                bullet.setY(this.y);
                bullet.setX(this.x + i * 40);
                GameUtils.bulletObjList.add(bullet);
                GameUtils.gameObjList.add(GameUtils.bulletObjList.get(GameUtils.bulletObjList.size() - 1));
            }
            lastShotTime = currentTime;
        }
    }

    /**
     * Remove an enemy plane when it's no longer active
     */
    private void removeBoss(){
        if(!this.isActive) {
            GameUtils.gameObjList.remove(this);
            this.window.resetWaveCount();
        }
    }
}
