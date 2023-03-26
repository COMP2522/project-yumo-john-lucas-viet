package org.myProject.obj;

import org.myProject.GameWin;
import org.myProject.utils.GameUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

 //Player-BasicFunctions-JT
 //Player-Shoot-And-PowerUps

/**
 This class represents a plane object in a game.
 */
public class PlaneObj extends GameObj {
  private int health = 100;
  private int lives = 3;
  private int maxLives = 3;
  private static int invincibleTimer = 0;
  private boolean invincible = false;
  
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
   
   Gets the image of the plane object.
   @return The image of the plane object.
   */
  @Override
  public Image getImg() {
    return super.getImg();
  }
  /**
   
   The game win object associated with the plane.
   */
  public GameWin gameWin;
  /**
   
   Default constructor for the PlaneObj class.
   */
  public PlaneObj() {
    super();
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
    //The plane moves with the mouse
    this.frame.addMouseMotionListener(new MouseAdapter() {
      @Override
      public void mouseMoved(MouseEvent e) {
        PlaneObj.super.x = e.getX() - 11;
        PlaneObj.super.y = e.getY() - 16;
      }
    });
  }
  
  public void paintself(Graphics gImage) {
    super.paintself(gImage);
    drawHealthUI(gImage);
    checkCollision();
  }
  
  /**
   Draws the player's health bar and remaining lives on the specified Graphics object.
   The health bar is a rectangular shape filled with a color that indicates the player's current
   health percentage. The remaining lives are drawn as small versions of the player sprite.
   @param gImage the Graphics object to draw the health UI on
*/
   public void drawHealthUI(Graphics gImage) {
    int barWidth = 150;
    int barHeight = 15;
    int barX = 10; // Left margin
    int barY = this.frame.getHeight() - barHeight - 10; // Bottom margin
    
    gImage.setColor(Color.WHITE);
    gImage.drawRect(barX, barY, barWidth, barHeight);
    
    double healthPercent = (double) this.health / 1.0;
    
    if (healthPercent >= 0.7) {
      gImage.setColor(Color.GREEN);
    } else if (healthPercent >= 0.3) {
      gImage.setColor(Color.YELLOW);
    } else {
      gImage.setColor(Color.RED);
    }
    
    int healthBarWidth = (int) (barWidth * healthPercent);
    
    gImage.fillRect(barX, barY, healthBarWidth, barHeight);
  
    drawLives((Graphics2D) gImage, barX, barY);
  }
  
  /**
   Draws the player's remaining lives as small versions of the player sprite. The images are
   drawn starting from the top-left corner of the health bar, spaced evenly apart with a fixed
   gap between them.
   @param gImage the Graphics2D object to draw the images on
   @param barX the x-coordinate of the top-left corner of the health bar
  */
   public void drawLives(Graphics2D gImage, int barX, int barY) {
    Image playerImage = this.img; // Gets the image of the PlaneObj Sprite
    int playerWidth = playerImage.getWidth(null);
    int playerHeight = playerImage.getHeight(null);
    int livesX = barX; // distance between player sprites and health bar
    int livesY = barY - 20;
    
    for (int i = 0; i < this.lives; i++) {
      gImage.drawImage(playerImage, livesX + i * (playerWidth / 2 + 5), livesY, playerWidth / 2, playerHeight / 2, null);
    }
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
  
      // Reset mouse cursor to player position
      try {
        Robot robot = new Robot();
        robot.mouseMove(this.STARTX, this.STARTY);
      } catch (AWTException e) {
        e.printStackTrace();
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
  
  public void shoot(){
  
  }
}