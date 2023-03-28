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

//Player-BasicFunctions-JT
//Player-Shoot-And-PowerUps

/**
 This class represents a plane object in a game.
 */
public class PlaneObj extends GameObj {
  
  private long lastShotTime = 0;
  private final long SHOT_DELAY = 250000000; // 0.25 seconds in nanoseconds
  
  private int health = 100;
  private int lives = 3;
  private int maxLives = 3;
  private static int invincibleTimer = 0;
  private boolean invincible = false;
  private int fireType = 1;
  private int score = 0;
  
  /**
   The game win object associated with the plane.
   */
  public GameWin gameWin;
  
  public static final int STARTX = 290;
  public static final int STARTY = 550;



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
   Gets the invincible timer value.
   @return The invincible timer value.
   */
  public static int getInvincibleTimer() {
    return invincibleTimer;
  }
  
  /**
   Sets the invincible timer value.
   @param invincibleTimer The invincible timer value.
   */
  public static void setInvincibleTimer(int invincibleTimer) {
    PlaneObj.invincibleTimer = invincibleTimer;
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
    
    // Add a Timer to shoot bullets every 10 milliseconds
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
      public void run() {
        shoot(fireType);
      }
    };
    timer.schedule(task, 0, 10);
    
    //The plane moves with the mouse
    this.frame.addMouseMotionListener(new MouseAdapter() {
      @Override
      public void mouseMoved(MouseEvent e) {
        PlaneObj.super.x = e.getX() - 11;
        PlaneObj.super.y = e.getY() - 16;
      }
    });
  }
  
  /**
   * Used to constantly update player sprite position, health, and check collision
   * @param gImage
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
        /**
         takeDamage(obj.getDamage(obj.getDamage()));
         */
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
      gameWin.state = 3;
    }
  }
  
  /**
   Triggers an explosion animation at the current PlaneObj's location when
   player health equals 0. This function is called in takeDamage()
   */
  public void explode() {
    try {
      // Load all explosion gifs
      Image[] explosionGifs = new Image[16];
      for (int i = 1; i <= 16; i++) {
        explosionGifs[i - 1] = ImageIO.read(new File("explode/e" + i + ".gif"));
      }
      // Draw current explosion gif at PlaneObj's location
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
    health = 1;
    lives--;
    if (lives >= 0) {
      this.x = STARTX;
      this.y = STARTY;
      this.health = 1;
      this.invincible = true; // set invincibility to true
      invincibleTimer = 0;
  
      /**
       * Reset mouse course to start position of player dies
       */
      try {
        Robot robot = new Robot();
        robot.mouseMove(this.STARTX, this.STARTY);
      } catch (AWTException e) {
        System.err.println("Mouse reset error");
      }
      /**
       * Sets a Timer for 3 seconds to turn off the invicibility flag
       */
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
          spreadFire();
          break;
        case 5:
          beam();
          break;
        default:
          break;
      }
    }
  }
  
  public void straightShot() {
    long currentTime = System.nanoTime();
    
    if (currentTime - lastShotTime >= SHOT_DELAY) {
      BulletObj bullet = new BulletObj(bulletimg, this.x, this.y, 5, 10, 10, this.frame, false);
      bullet.setX(this.getX() + 4);
      bullet.setY(this.getY() - 20);
      GameUtils.bulletObjList.add(new BulletObj(GameUtils.shellimg,this.getX()+4,this.getY()-16,14,29,12,frame, false));
      GameUtils.gameObjList.add(GameUtils.bulletObjList.get(GameUtils.bulletObjList.size()-1));
      
      lastShotTime = currentTime;
    }
  }
  
  public void doubleFire(){
  
  }
  
  public void tripleFire(){
  
  }
  
  public void spreadFire(){
  
  }
  
  public void beam(){
  
  }

  public void paintPower() {
    PowerUpsObj powerUpsObj = new PowerUpsObj(GameUtils.powerups, x, -GameUtils.powerups.getHeight(null),
            GameUtils.powerups.getWidth(null), GameUtils.powerups.getHeight(null),
            2, gameWin);
    while (true) {
      powerUpsObj.spawnPowerUp(gameWin);
    }
  }


  
  
}