package org.myProject.obj;

import org.myProject.GameWin;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Player-BasicFunctions-JT
 */

public class PlaneObj extends GameObj {
  
  private int health = 1;
  
  private int lives = 3;
  
  private int maxLives = 3;
  
  //Getters for health and lives
  public int getHealth(){
    return health;
  }
  
  public int getLive(){
    return lives;
  }
  
  public int getMaxLives(){
    return maxLives;
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
    health -= dmg;
    if (health <= 0){
      lives--;
    }
    if (lives <= 0){
      gameWin.state = 2;
    }
  }
}