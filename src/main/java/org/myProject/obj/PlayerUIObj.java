package org.myProject.obj;

import org.myProject.GameWin;
import org.myProject.utils.GameUtils;

import java.awt.*;

public class PlayerUIObj extends GameObj {
  
  private final PlaneObj player;
  
  public PlayerUIObj(PlaneObj player) {
    this.player = player;
  }
  
  @Override
  public void paintself(Graphics gImage) {
    super.paintself(gImage);
    drawHealthUI(gImage);
    drawScore(gImage);
  }
  
  /**
   * Draws the player's health bar and remaining lives on the specified Graphics object.
   * The health bar is a rectangular shape filled with a color that indicates the player's current
   * health percentage. The remaining lives are drawn as small versions of the player sprite.
   *
   * @param gImage the Graphics object to draw the health UI on
   */
  public void drawHealthUI(Graphics gImage) {
    int barWidth = 2;
    int barHeight = 15;
    int barX = 10; // Left margin
    int barY = player.frame.getHeight() - barHeight - 10; // Bottom margin
    
    gImage.setColor(Color.WHITE);
    gImage.drawRect(barX, barY, barWidth, barHeight);
    
    double healthPercent = (double) player.getHealth() / 1.0;
    
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
    
    drawScore(gImage);
  }
  
  /**
   * Draws the player's remaining lives as small versions of the player sprite. The images are
   * drawn starting from the top-left corner of the health bar, spaced evenly apart with a fixed
   * gap between them.
   *
   * @param gImage the Graphics2D object to draw the images on
   * @param barX   the x-coordinate of the top-left corner of the health bar
   */
  public void drawLives(Graphics2D gImage, int barX, int barY) {
    Image playerImage = player.img; // Gets the image of the PlaneObj Sprite
    int playerWidth = playerImage.getWidth(null);
    int playerHeight = playerImage.getHeight(null);
    int livesX = barX; // distance between player sprites and health bar
    int livesY = barY - 20;
    
    for (int i = 0; i < player.getLives(); i++) {
      gImage.drawImage(playerImage, livesX + i * (playerWidth / 2 + 5), livesY, playerWidth / 2, playerHeight / 2, null);
    }
  }
  
  public void drawScore(Graphics gImage) {
    int score = player.getScore();
    GameUtils.drawWord(gImage,"Score: " + score, Color.green, 30,15,60);
  }

}