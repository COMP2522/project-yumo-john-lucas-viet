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
 * EnemyObj class - this class represent the enemy object in the game.
 * This class contain enemy basic functions.
 * 
 * @author : Viet Nguyen
 * @version : 1.0
 */
public class EnemyObj extends GameObj implements ActionListener {

    private Timer timer;
    private boolean isActive;
    int distance;
    private long lastShotTime = 0;
    public GameWin window;

    public boolean powerupSpawned = false;

    GameWin gameWin;
    Image powerUpImage;

    private int hitpoints = 10;

    /**
     * If player collides with an enemy ship, player will take this amount of damage
     */
    private int Damage = 50;

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
    public EnemyObj(Image img, int x, int y, int width, int height, double speed, GameWin frame, int distance) {
        super(img, x, y, width, height, speed, frame);
        this.timer = new Timer(20, this);
        this.timer.start();
        this.isActive = true;
        window = frame;
    }

    /**
     * Enemy damage when collide with player's plane
     * 
     * @return amount of damage dealt by enemy when collided.
     */
    public int getDamage() {
        return Damage;
    }

    /**
     * Used to constantly update player sprite position, health, and check collision
     * 
     * @param gImage representing the image of the plan
     */
    public void paintself(Graphics gImage) {
        gImage.drawImage(enemyimg, this.x - 15, this.y, window);
        checkCollision(window.getPlaneobj(), gImage);
        removeEnemy();
    }

    /**
     * Checks if the enemy object collides with another game object.
     *
     * @param otherObj The game object to check collision with.
     * @return True if the enemy object collides with the other game object, false
     *         otherwise.
     */
    private boolean collidesWith(GameObj otherObj) {
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
            if (obj instanceof BulletObj && !((BulletObj) obj).isEnemyBullet && this.collidesWith(obj)) {
                if (this.isActive) {
                    this.isActive = false;
                    planeobj.setScore(planeobj.getScore() + 1);
                    PowerUpsObj power = new PowerUpsObj(powerUpImage, x, y, 20, 30, 2, gameWin);
                    power.spawnPowerUp(x, y+50);


            //Decrement enemy's hit-points by one everytime it get hit by player's bullet
            if (obj instanceof BulletObj && !((BulletObj) obj).isEnemyBullet && this.collidesWith(obj)) {
                this.hitpoints --;
                if(this.hitpoints == 0){
                    planeobj.setScore(planeobj.getScore() + 1);
                    this.isActive = false;
                    gImage.drawImage(GameUtils.explodeimg, this.getX() - 35, this.getY() - 50, null);
                    GameUtils.removeobjList.add(this);
                    break;
                }
            }

            //If enemy collide directly with player plane, it will be deleted.
            if (obj instanceof PlaneObj && this.collidesWith(obj)){
                this.isActive = false;
                gImage.drawImage(GameUtils.explodeimg, this.x, this.y, null);
                GameUtils.removeobjList.add(this);
                powerupSpawned = true;

                break;
            }
        }
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
        if (this.y >= distance && this.isActive) {
            fire();
        }
    }

    /**
     * Perform the method every 1 second.
     * Make the movement look smoother.
     * 
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            moveDown(this.distance);
        }
    }

    /**
     * Fires a bullet from the enemy object.
     */
    public void fire() {
        long currentTime = System.nanoTime();
        long timeSinceLastShot = currentTime - lastShotTime;

        if (timeSinceLastShot >= 900000000) {
            BulletObj bullet = new BulletObj(reverseShell, this.x, this.y, 5, 10, 12, this.frame, true);
            bullet.setY(this.y);
            bullet.setX(this.x);
            GameUtils.bulletObjList.add(bullet);
            GameUtils.gameObjList.add(GameUtils.bulletObjList.get(GameUtils.bulletObjList.size() - 1));
            lastShotTime = currentTime;
        }
    }

    /**
     * Remove an enemy plane when it's no longer active
     */
    private void removeEnemy() {
        if (!this.isActive) {
            GameUtils.enemyObjList.remove(this);
            this.window.setEnemyCount(1);
        }
    }

}