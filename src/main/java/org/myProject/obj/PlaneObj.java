package org.myProject.obj;

import org.myProject.GameWin;
import org.myProject.utils.GameUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static org.myProject.utils.GameUtils.bulletimg;

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
  
  private int health = 100;
  private int lives = 3;
  private int maxLives = 3;
  private boolean invincible = false;
  
  private int fireType = 1;
  private int score = 0;
  
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
   Gets the current number of lives the plane has.
   @return The current number of lives the plane has.
   */
  public int getLives() {
    return lives;
  }
  
  /**
   Sets the current number of lives the plane has.
   @param lives The current number of lives the plane has.
   */
  public void setLives(int lives) {
    this.lives = lives;
  }
  
  /**
   Gets the maximum number of lives the plane can have.
   @return The maximum number of lives the plane can have.
   */
  public int getMaxLives() {
    return maxLives;
  }
  
  /**
   Sets the maximum number of lives the plane can have.
   @param maxLives The maximum number of lives the plane can have.
   */
  public void setMaxLives(int maxLives) {
    this.maxLives = maxLives;
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
   Sets the score to the specified value.
   @param score the integer value to set the score to
   */
  public void setScore(int score) {
    this.score = score;
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
    for (GameObj obj : gameObjList) {
      if (this.collidesWith(obj)) {
        //Waiting on bullet getDamage() and enemy getDamage()
         //takeDamage(obj.getDamage(obj.getDamage()));
         
        break;
      }
    }
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
      respawn();
    }
    if (lives <= 0) {
      GameWin.state = 3;
    }
  }
  
  /**
   Triggers an explosion animation at the current Plane's location when
   player health equals 0. This function is called in takeDamage()
   */
  public void explode() {
    try {
      // Load all explosion gifs
      Image[] explosionGifs = new Image[16];
      for (int i = 1; i <= 16; i++) {
        explosionGifs[i - 1] = ImageIO.read(new File("explode/e" + i + ".gif"));
      }
      // Draw current explosion gif at Plane location
      int currentExplosionIndex = 0; // Update this variable to change which explosion frame is shown
      Graphics gImage = this.frame.getGraphics(); // Get the graphics object of the frame
      gImage.drawImage(explosionGifs[currentExplosionIndex], this.x, this.y, null); // Draw the explosion gif
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  /**
   Resets player's health and position upon death, sets invincibility to true for 3 seconds,
   and sets a Timer to turn off invincibility after 3 seconds.
   */
  public void respawn() {
    lives--;
    if (lives >= 0) {
      this.x = START_X;
      this.y = START_Y;
      this.health = 100;
      this.invincible = true; // set invincibility to true
  
      //Resets the mouse course to the starting position of the player
      try {
        Robot robot = new Robot();
        robot.mouseMove(START_X, START_Y);
      } catch (AWTException e) {
        System.err.println("Mouse reset error");
      }
      
      //Sets up a timer to turn off the player invincibility after 3secs
      Timer timer = new Timer();
      TimerTask task = new TimerTask() {
        @Override
        public void run() {
          invincible = false;
        }
      };
      timer.schedule(task, 3000);
    }
  }
  
  public void shoot(int fireType) {
    while (GameWin.state == 1) {
      switch (fireType) {
        case 1 -> straightShot();
        case 2 -> doubleFire();
        case 3 -> tripleFire();
        case 4 -> spreadFire();
        default -> {
        }
      }
    }
  }
  
  /**
   * Fires a bullet in a straight line from the player's current position. Bullets originate from
   * the center of the player
   */
  public void straightShot() {
    final int BULLET_WIDTH = 14;
    final int BULLET_HEIGHT = 29;
    final int BULLET_SPEED = 12;
    final int BULLET_X_OFFSET = 4;
    final int BULLET_Y_OFFSET = -20;
    
    long currentTime = System.nanoTime();
  
    if (currentTime - lastShotTime >= SHOT_DELAY) {
      BulletObj bullet = new BulletObj(bulletimg, this.x, this.y, BULLET_WIDTH, BULLET_HEIGHT, BULLET_SPEED, this.frame);
      bullet.setX(this.getX() + BULLET_X_OFFSET);
      bullet.setY(this.getY() + BULLET_Y_OFFSET);
      GameUtils.bulletObjList.add(new BulletObj(GameUtils.shellimg,this.getX()+BULLET_X_OFFSET,this.getY()-16,BULLET_WIDTH,BULLET_HEIGHT,BULLET_SPEED,frame));
      GameUtils.gameObjList.add(GameUtils.bulletObjList.get(GameUtils.bulletObjList.size()-1));
    
      lastShotTime = currentTime;
    }
  }
  
  /**
   * Fires bullets in 2 straight lines from the player's current position. Bullets are on even offset from the center
   * of the player.
   */
  public void doubleFire(){
  
  }
  /**
   * Fires bullets in 3 straight lines from the player's current position. Bullets are on even offset from the center
   * of the player.
   */
  public void tripleFire(){
  
  }
  
  /**
   * Fires bullets in 5 straight lines from the player's current position. This will allow the player to fire in an arc
   */
  public void spreadFire(){
  
  }
  
  /**
   * Move this to your own class Lucas, and remove this comment after
   */
  public void paintPower() {
    PowerUpsObj powerUpsObj = new PowerUpsObj(GameUtils.powerups, x, -GameUtils.powerups.getHeight(null),
            GameUtils.powerups.getWidth(null), GameUtils.powerups.getHeight(null),
            2, gameWin);
    while (true) {
      PowerUpsObj.spawnPowerUp(gameWin);
    }
  }
}