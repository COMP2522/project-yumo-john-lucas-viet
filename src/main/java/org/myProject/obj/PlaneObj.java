package org.myProject.obj;

import org.myProject.GameWin;
import org.myProject.utils.GameUtils;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

import static org.myProject.utils.GameUtils.*;

/**
 * PlaneObj is class representing the player. This class is responsible
 * for handling player functions such as shooting, checking collision,
 * updating player info (Health, score).
 *
 * @author John Tu
 */
public class PlaneObj extends GameObj {
  
  public long lastShotTime = 0;
  public static final long SHOT_DELAY = 250000000; // 0.25 seconds in nanoseconds
  
  public static final int MOUSE_OFFSET_X = 11;
  public static final int MOUSE_OFFSET_Y = 16;
  public static final int SHOOT_DELAY_MS = 10;
  public static final int BULLET_SPACING = 20;
  
  private int health = 100;
  private boolean invincible = false;
  
  private int fireType = 1;
  private int score = 0;

  boolean pickUpPowerupBullet;
  boolean pickUpPowerupHealth;

  
  /**
   The game win object associated with the plane.
   */
  public GameWin gameWin;
  
  public static final int START_X = 290;
  public static final int START_Y = 550;



  /**
   Gets the current health of the plane.
   @return The current health of the plane.
   */
  public int getHealth() {
    return health;
  }
  
  /**
   Sets the current health of the plane.
   @param health The current health of the plane.
   */
  public void setHealth(int health) {
    this.health = health;
  }
  
  /**
   * Gets the current FireType the plane current has.
   * @return The current fire type of the plane
   */
  public int getFireType() {
    return fireType;
  }
  
  /**
   * Sets the fire type the plane can have
   * @param fireType The mode/style of firing type
   */
  public void setFireType(int fireType) {
    this.fireType = fireType;
  }
  
  /**
   Returns the current score.
   @return an integer representing the current score
   */
  public int getScore() {
    return score;
  }
  
  /**
   Sets the score to the size of the given ArrayList.
   @param score value that will be used to set score
   */
  public void setScore(int score) {
    this.score = score; // set the score to the size of the ArrayList
  }
  
  
  
  
  /**
   Gets the image of the plane object.
   @return The image of the plane object.
   */
  @Override
  public Image getImg() {
    return super.getImg();
  }
  
  /**
   Creates a PlaneObj object with the given parameters.
   @param img The image of the plane object.
   @param x The x-coordinate of the plane object.
   @param y The y-coordinate of the plane object.
   @param width The width of the plane object.
   @param height The height of the plane object.
   @param speed The speed of the plane object.
   @param frame The GameWin object associated with the plane.
   */
  public PlaneObj(Image img, int x, int y, int width, int height, double speed, GameWin frame) {
    super(img, x, y, width, height, speed, frame);
    
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
      public void run() {
        shoot(fireType);
      }
    };
    timer.schedule(task, 0, SHOOT_DELAY_MS);
    
    this.frame.addMouseMotionListener(new MouseAdapter() {
      @Override
      public void mouseMoved(MouseEvent e) {
        PlaneObj.super.x = e.getX() - MOUSE_OFFSET_X;
        PlaneObj.super.y = e.getY() - MOUSE_OFFSET_Y;
      }
    });
  }
  
  /**
   * Used to constantly update player sprite position, health, and check collision
   * @param gImage representing the image of the plan
   */
  public void paintself(Graphics gImage) {
    super.paintself(gImage);
    checkCollision();
    PlayerUIObj UI = new PlayerUIObj(this);
    UI.drawHealthUI(gImage);
  }
  
  
  /**
   Checks for collisions between the current GameObj and all GameObjs in the gameObjList.
   If a collision is detected, the current GameObj takes damage based on the damage inflicted by the other GameObj.
   */
  public void checkCollision(){
    List<GameObj> gameObjList = GameUtils.gameObjList;
    try{
    for (GameObj obj : gameObjList) {
      if (obj instanceof BulletObj && ((BulletObj) obj).isEnemyBullet && this.collidesWith(obj)){
        takeDamage(((BulletObj) obj).getDamage());
        GameUtils.removeobjList.add(obj);
      }
      if (obj instanceof EnemyObj && this.collidesWith(obj)){
        takeDamage(((EnemyObj) obj).getDamage());

      }

      if (obj instanceof PowerUpsObj && this.collidesWith(obj)) {
        GameUtils.gameObjList.remove(obj);
        pickUpPowerupBullet = true;

      }

      if (obj instanceof HealPowerUpsObj && this.collidesWith(obj)) {
        GameUtils.gameObjList.remove(obj);
        pickUpPowerupHealth = true;

      }

      if (pickUpPowerupHealth) {
        if (health <= 95) {
          health += 5;
        }
        pickUpPowerupHealth = false;
      }

      }
    }catch(ConcurrentModificationException e){}
  }


  
  /**
   Returns the rectangular bounds of this GameObj.
   @return a Rectangle object representing the bounds of this GameObj
   */
  @Override
  public Rectangle getrect() {
    return super.getrect();
  }
  
  /**
   Checks if the current GameObj collides with another GameObj by comparing their rectangles.
   @param otherObj the GameObj to check collision against
   @return true or false if rectangles of GameObj are intersecting
   */
  public boolean collidesWith(GameObj otherObj){
    Rectangle rect1 = this.getrect();
    Rectangle rect2 = otherObj.getrect();
    return rect1.intersects(rect2);
  }
  
  /**
   Reduces the health of the player by the specified amount of damage (dmg).
   If the player is invincible, no damage is taken.
   If the player's health drops to 0 or below, the player explodes and respawns.
   If the player has no remaining lives, the game enters the "game over" state.
   @param dmg the amount of damage to subtract from the player's health
   */
  public void takeDamage(int dmg) {
    if (invincible) {
      return; // if invincible, do not take damage
    }
    health -= dmg;
    if (health <= 0) {
      explode();
      gameWin.state = 3;
    }
    invincible = true;
    //Player will become invincible for 0.5s after being hit
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
      @Override
      public void run() {
        invincible = false;
      }
    };
    timer.schedule(task, 500);
    
  }
  
  /**
   Triggers an explosion animation at the current Plane's location when
   player health equals 0. This function is called in takeDamage()
   */
  public void explode() {
    ExplodeObj explodeobj=new ExplodeObj(this.x,this.y);
    GameUtils.explodeObjList.add(explodeobj);
    GameUtils.removeobjList.add(explodeobj);
  }
  
  public void shoot(int fireType) {
    while (GameWin.state == 1) {
      if (pickUpPowerupBullet) {
        fireType += 1;
        pickUpPowerupBullet = false;
        if (fireType > 4) {
          fireType = 4;
        }
      }
      switch (fireType) {
        case 1 -> straightShot();
        case 2 -> doubleFire();
        case 3 -> tripleFire();
        case 4 -> pentaFire();
        default -> {
        }
      }
    }
  }

  
  /**
   * Fires bullets in a straight line from the player's current position. Bullets originate from
   * the center of the player.
   *
   * @param bulletCount The number of bullets to fire.
   * @param bulletSpacing The spacing between the bullets.
   */
  private void fireBullets(int bulletCount, int bulletSpacing) {
    final int BULLET_WIDTH = 14;
    final int BULLET_HEIGHT = 29;
    final int BULLET_SPEED = 12;
    final int BULLET_X_OFFSET = 4;
    final int BULLET_Y_OFFSET = -20;

    long currentTime = System.nanoTime();

    if (currentTime - lastShotTime >= SHOT_DELAY) {
      int startX = this.getX() + BULLET_X_OFFSET - ((bulletCount - 1) * bulletSpacing) / 2;

      for (int i = 0; i < bulletCount; i++) {
        BulletObj bullet = new BulletObj(shellimg, this.x, this.y, BULLET_WIDTH, BULLET_HEIGHT, BULLET_SPEED, this.frame, false);
        bullet.setX(startX + i * bulletSpacing);
        bullet.setY(this.getY() + BULLET_Y_OFFSET);
        GameUtils.bulletObjList.add(bullet);
        GameUtils.gameObjList.add(GameUtils.bulletObjList.get(GameUtils.bulletObjList.size() - 1));
      }

      lastShotTime = currentTime;
    }
  }
  
  /**
   * Fires a bullet in a straight line from the player's current position. Bullets originate from
   * the center of the player.
   */
  public void straightShot() {
    fireBullets(1, 0);
  }
  
  /**
   * Fires bullets in 2 straight lines from the player's current position. Bullets are on even offset from the center
   * of the player.
   */
  public void doubleFire() {
    fireBullets(2, BULLET_SPACING);
  }
  
  /**
   * Fires bullets in 3 straight lines from the player's current position. Bullets are on even offset from the center
   * of the player.
   */
  public void tripleFire() {
    fireBullets(3, BULLET_SPACING);
  }
  
  /**
   * Fires bullets in 5 straight lines from the player's current position. Bullets are on even offset from the center
   * of the player.
   */
  public void pentaFire() {
    fireBullets(5, BULLET_SPACING);
  }
  
}