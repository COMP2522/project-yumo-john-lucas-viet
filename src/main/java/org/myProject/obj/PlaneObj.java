package org.myProject.obj;

import org.myProject.GameWin;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Player-BasicFunctions-JT
 *
 */

public class PlaneObj extends GameObj {
  
  private int health = 1;
  
  private int lives = 3;
  
  private int maxLives = 3;
  
  private static int invincibleTimer = 0;
  
  private boolean invincible = false;
  
  public static final int STARTX = 290;
  
  public static final int STARTY = 550;
  
  //Getters and Setters
  
  
  public int getHealth() {
    return health;
  }
  
  public void setHealth(int health) {
    this.health = health;
  }
  
  public int getLives() {
    return lives;
  }
  
  public void setLives(int lives) {
    this.lives = lives;
  }
  
  public int getMaxLives() {
    return maxLives;
  }
  
  public void setMaxLives(int maxLives) {
    this.maxLives = maxLives;
  }
  
  public static int getInvincibleTimer() {
    return invincibleTimer;
  }
  
  public static void setInvincibleTimer(int invincibleTimer) {
    PlaneObj.invincibleTimer = invincibleTimer;
  }
  
  @Override
  public Image getImg() {
    return super.getImg();
  }
  public GameWin gameWin;
  
  public PlaneObj() {
    super();
  }
  
  public PlaneObj(Image img, int x, int y, int width, int height, double speed, GameWin frame) {
    super(img, x, y, width, height, speed, frame);
    //The plane moves with the mouse
    this.frame.addMouseMotionListener(new MouseAdapter() {
      @Override
      public void mouseMoved(MouseEvent e) {
        PlaneObj.super.x=e.getX()-11;
        PlaneObj.super.y=e.getY()-16;
      }
    });
  }
  
  public void paintself(Graphics gImage) {
    super.paintself(gImage);
  
    // Draw health bar
    int barWidth = 150;
    int barHeight = 15;
    int barX = 10; // Left margin
    int barY = this.frame.getHeight() - barHeight - 10; // Bottom margin
  
    gImage.setColor(Color.WHITE);
    gImage.drawRect(barX, barY, barWidth, barHeight);

// Calculate health percent and fill health bar
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

// Draw player sprites for number of lives
    Image playerImage = this.img; // Gets the image of the PlaneObj Sprite
    int playerWidth = playerImage.getWidth(null);
    int playerHeight = playerImage.getHeight(null);
    int livesX = barX; // distance between player sprites and health bar
    int livesY = barY - 20;
  
    for (int i = 0; i < this.lives; i++) {
      gImage.drawImage(playerImage, livesX + i * (playerWidth / 2 + 5), livesY, playerWidth / 2, playerHeight / 2, null);
    }
  
  
  
    // Check for collision with boss
    /** No boss yet
    if (this.frame.bossobj != null && this.getrect().intersects(this.frame.bossobj.getrect())) {
      // Take damage
      this.lives--;
      
      if (this.lives <= 0) {
        this.gameWin.state = 4;
      }
    }
     */
  }
  
  
  @Override
  public Rectangle getrect() {
    return super.getrect();
  }
  
  public void takeDamage(int dmg){
    if (invincible) {
      return; // if invincible, do not take damage
    }
    health -= dmg;
    if (health <= 0){
      explode();
      respawn();
    }
    if (lives <= 0){
      gameWin.state = 3;
    }
  }
  
  public void explode(){
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
       * Sets a Timer for 3 seconds to turn off the invicibility flag
       */
      Timer timer = new Timer();
      TimerTask task = new TimerTask() {
        @Override
        public void run() {
          invincible = false;
        }
      }
      timer.schedule(task, 3000);
    }
      
      // Reset mouse cursor to player position
      try {
        Robot robot = new Robot();
        robot.mouseMove(this.STARTX, this.STARTY);
      } catch (AWTException e) {
        e.printStackTrace();
      }
    }
  }
}