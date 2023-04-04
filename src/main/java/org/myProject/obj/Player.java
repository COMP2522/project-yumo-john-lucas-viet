package org.myProject.obj;

import org.myProject.Window;
import org.myProject.utils.GameUtils;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static org.myProject.utils.GameUtils.*;

/**
 * Player is class representing the player. This class is responsible
 * for handling player functions such as shooting, checking collision,
 * updating player info (Health, score).
 *
 * @author John Tu
 */
public class Player extends GameObj {

  public long lastShotTime = 0;
  public static final long SHOT_DELAY = 250000000; // 0.25 seconds in nanoseconds

  public static final int MOUSE_OFFSET_X = 11;
  public static final int MOUSE_OFFSET_Y = 16;
  public static final int SHOOT_DELAY_MS = 10;
  public static final int BULLET_SPACING = 20;
  public static final int MAX_HEALTH = 4;
  public static final int MAX_BULLETS = 4;

  private int health = 4;
  private boolean invincible = false;

  private int fireType = 1;
  private int score = 0;

  private String name;
  private String password;

  public TopScoresUI topScore;
  
  /**
   * Gets current name of plane.
   *
   * @return The name of the plane.
   */
  public String getName() {
    return name;
  }
  
  /**
   * Sets the name of the plane.
   *
   * @param name The name that will be set to the plane.
   */
  public void setName(String name) {
    this.name = name;
  }
  
  /**
   * Gets the password for the plane instance
   *
   * @return The password of the plane.
   */
  public String getPassword() {
    return password;
  }
  
  /**
   * Sets the password of the plane.
   *
   * @param password The password that will be set to the plane.
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Gets the current health of the plane.
   * 
   * @return The current health of the plane.
   */
  public int getHealth() {
    return health;
  }

  /**
   * Sets the health of the plane.
   * 
   * @param health The health that will be set to the plane.
   */
  public void setHealth(int health) {
    this.health = health;
  }

  /**
   * Gets the current FireType the plane current has.
   * 
   * @return The current fire type of the plane
   */
  public int getFireType() {
    return fireType;
  }

  /**
   * Sets the fire type the plane can have
   * 
   * @param fireType The mode/style of firing type
   */
  public void setFireType(int fireType) {
    this.fireType = fireType;
  }

  /**
   * Returns the current score.
   * 
   * @return an integer representing the current score
   */
  public int getScore() {
    return score;
  }

  /**
   * Sets the score to the size of the given ArrayList.
   * 
   * @param score value that will be used to set score
   */
  public void setScore(int score) {
    this.score = score; // set the score to the size of the ArrayList
    topScore.addLocalScore(this);
  }

  /**
   * Gets the image of the plane object.
   * 
   * @return The image of the plane object.
   */
  @Override
  public Image getImg() {
    return super.getImg();
  }

  /**
   * Creates a Player object with the given parameters.
   * 
   * @param img    The image of the plane object.
   * @param x      The x-coordinate of the plane object.
   * @param y      The y-coordinate of the plane object.
   * @param width  The width of the plane object.
   * @param height The height of the plane object.
   * @param speed  The speed of the plane object.
   * @param frame  The Window object associated with the plane.
   */
  public Player(Image img, int x, int y, int width, int height, double speed, Window frame,
                TopScoresUI topScore) {
    super(img, x, y, width, height, speed, frame);
    this.topScore = topScore;

    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
      public void run() {
        shoot();
      }
    };
    timer.schedule(task, 0, SHOOT_DELAY_MS);

    this.frame.addMouseMotionListener(new MouseAdapter() {
      @Override
      public void mouseMoved(MouseEvent e) {
        Player.super.x = e.getX() - MOUSE_OFFSET_X;
        Player.super.y = e.getY() - MOUSE_OFFSET_Y;
      }
    });

  }

  /**
   * Used to constantly update player sprite position, health, and check collision
   * 
   * @param gImage representing the image of the plan
   */
  public void paintself(Graphics gImage) {
    super.paintself(gImage);
    checkCollision();
    PlayerUI UI = new PlayerUI(this);
    UI.drawHealthUI(gImage);
  }

  /**
   * Checks for collisions between the current GameObj and all GameObjs in the
   * gameObjList.
   * If a collision is detected, the current GameObj takes damage based on the
   * damage inflicted by the other GameObj.
   */
  public void checkCollision() {
    List<GameObj> gameObjList = GameUtils.gameObjList;
    try {
      for (GameObj obj : gameObjList) {
        // Check collision for enemy bullets
        if (obj instanceof Bullet && ((Bullet) obj).isEnemyBullet && this.collidesWith(obj)) {
          takeDamage(((Bullet) obj).getDamage());
          GameUtils.removeobjList.add(obj);
          deUpgradeBullets(this.getFireType());
        }
        // Check collision for crashing into enemies
        if (obj instanceof Enemy && this.collidesWith(obj)) {
          takeDamage(((Enemy) obj).getDamage());
          GameUtils.removeobjList.add(obj);
        }
        // Check collision for crashing into boss
        if (obj instanceof Boss && this.collidesWith(obj)) {
            takeDamage(((Boss) obj).getDamage());
            GameUtils.removeobjList.add(obj);
            deUpgradeBullets(this.getFireType());
          }
          // Check collision for bullet upgrade power up
        if (obj instanceof PowerUps && this.collidesWith(obj)) {
            upgradeBullets(this.getFireType());

          }
          // Check collision for health power up
        if (obj instanceof HealPowerUps && this.collidesWith(obj)) {
            this.healthPickUp(this.getHealth());
          }
      }
    } catch (ConcurrentModificationException ignored) {
    }
  }

  /**
   * Returns the rectangular bounds of this GameObj.
   * 
   * @return a Rectangle object representing the bounds of this GameObj
   */
  @Override
  public Rectangle getrect() {
    return super.getrect();
  }

  /**
   * Checks if the current GameObj collides with another GameObj by comparing
   * their rectangles.
   * 
   * @param otherObj the GameObj to check collision against
   * @return true or false if rectangles of GameObj are intersecting
   */
  public boolean collidesWith(GameObj otherObj) {
    Rectangle rect1 = this.getrect();
    Rectangle rect2 = otherObj.getrect();
    return rect1.intersects(rect2);
  }

  /**
   * Reduces the health of the player by the specified amount of damage (dmg).
   * If the player is invincible, no damage is taken.
   * If the player's health drops to 0 or below, the player explodes and respawns.
   * If the player has no remaining lives, the game enters the "game over" state.
   * 
   * @param dmg the amount of damage to subtract from the player's health
   */
  public void takeDamage(int dmg) {
    final int GAME_OVER = 3;
    if (invincible) {
      return; // if invincible, do not take damage
    }
    health -= dmg;
    if (health <= 0) {
      Window.state = GAME_OVER;
    }
    invincible = true;
    // Player will become invincible for 0.5s after being hit
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
   * Increases health.
   *
   * @param health The current player health.
   */
  public void healthPickUp(int health) {
    final int HEALTH_INCREASE = 1;
    this.setHealth(health + HEALTH_INCREASE);
    if (this.getHealth() >= MAX_HEALTH) {
      this.setHealth(MAX_HEALTH);
    }
  }
  
  /**
   * Increases bullet level.
   *
   * @param fireType The current player bullet level.
   */
  public void upgradeBullets(int fireType) {
    final int INCREMENT_BULLET = 1;
    
    this.setFireType(fireType + INCREMENT_BULLET);
    if (this.getFireType() >= 4) {
      this.setFireType(MAX_BULLETS);
    }
  }
  
  /**
   * Decreases bullet level.
   *
   * @param fireType The current player bullet level
   */
  public void deUpgradeBullets(int fireType) {
    final int INCREMENT_BULLET = 1;

    if (this.getFireType() > 1 ) {
      this.setFireType(fireType - INCREMENT_BULLET);
    }
  }



  /**
   * This method allows the player to shoot their weapon, depending on the type of
   * power-up they have collected.
   * 1 = straight shot, 2 = double shot, 3 = triple shot, 4 = penta shot
   */
  public void shoot() {
    while (Window.state == 1) {
      switch (this.fireType) {
        case 1:
          straightShot();
          break;
        case 2:
          doubleFire();
          break;
        case 3:
          tripleFire();
          break;
        case 4:
          pentaFire();
          break;
        default:
          break;
      }
    }
  }

  /**
   * Fires bullets in a straight line from the player's current position. Bullets
   * originate from
   * the center of the player.
   *
   * @param bulletCount   The number of bullets to fire.
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
        Bullet bullet = new Bullet(shellimg, this.x, this.y, BULLET_WIDTH, BULLET_HEIGHT, BULLET_SPEED,
            this.frame, false);
        bullet.setX(startX + i * bulletSpacing);
        bullet.setY(this.getY() + BULLET_Y_OFFSET);
        GameUtils.bulletList.add(bullet);
        GameUtils.gameObjList.add(GameUtils.bulletList.get(GameUtils.bulletList.size() - 1));
      }

      lastShotTime = currentTime;
    }
  }

  /**
   * Fires a bullet in a straight line from the player's current position. Bullets
   * originate from
   * the center of the player.
   */
  public void straightShot() {
    fireBullets(1, 0);
  }

  /**
   * Fires bullets in 2 straight lines from the player's current position. Bullets
   * are on even offset from the center
   * of the player.
   */
  public void doubleFire() {
    fireBullets(2, BULLET_SPACING);
  }

  /**
   * Fires bullets in 3 straight lines from the player's current position. Bullets
   * are on even offset from the center
   * of the player.
   */
  public void tripleFire() {
    fireBullets(3, BULLET_SPACING);
  }

  /**
   * Fires bullets in 5 straight lines from the player's current position. Bullets
   * are on even offset from the center
   * of the player.
   */
  public void pentaFire() {
    fireBullets(5, BULLET_SPACING);
  }

}