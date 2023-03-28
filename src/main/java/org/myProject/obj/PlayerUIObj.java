package org.myProject.obj;

import org.myProject.utils.GameUtils;

import java.awt.*;

/**
 * PlayerUIObj is used with the PlaneObj class to track player,health,lives, and
 * score and displays it on screen as the UI
 *
 * @author John Tu
 */
public class PlayerUIObj extends GameObj {
  
  private final PlaneObj player;
  
  private static final int LIVES_VERTICAL_GAP = 20;
  private static final int MAX_HEALTH = 100;
  private static final int HEALTH_BAR_WIDTH = 150;
  private static final int HEALTH_BAR_HEIGHT = 15;
  private static final int HEALTH_BAR_MARGIN_LEFT = 10;
  private static final int HEALTH_BAR_MARGIN_BOTTOM = 10;
  private static final int LIVES_MARGIN_TOP = LIVES_VERTICAL_GAP;
  private static final int PLAYER_SPRITE_GAP = 5;
  private static final int SCORE_FONT_SIZE = 30;
  private static final int SCORE_MARGIN_LEFT = 15;
  private static final int SCORE_MARGIN_TOP = 60;
  
  public PlayerUIObj(PlaneObj player) {
    this.player = player;
  }
  
  @Override
  public void paintself(Graphics gImage) {
    super.paintself(gImage);
    drawHealthUI(gImage);
  }
  
  public void drawHealthUI(Graphics gImage) {
    int barX = HEALTH_BAR_MARGIN_LEFT;
    int barY = player.frame.getHeight() - HEALTH_BAR_HEIGHT - HEALTH_BAR_MARGIN_BOTTOM;
    gImage.setColor(Color.WHITE);
    gImage.drawRect(barX, barY, HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT);
    
    double healthPercent = (double) player.getHealth() / MAX_HEALTH;
    if (healthPercent >= 0.7) {
      gImage.setColor(Color.GREEN);
    } else if (healthPercent >= 0.3) {
      gImage.setColor(Color.YELLOW);
    } else {
      gImage.setColor(Color.RED);
    }
    
    int healthBarWidth = (int) (HEALTH_BAR_WIDTH * healthPercent);
    gImage.fillRect(barX, barY, healthBarWidth, HEALTH_BAR_HEIGHT);
    
    drawLives((Graphics2D) gImage, barX, barY);
    drawScore(gImage);
  }
  
  public void drawLives(Graphics2D gImage, int barX, int barY) {
    Image playerImage = player.img;
    int playerWidth = player.width;
    int playerHeight = player.height;
    int livesY = barY - LIVES_MARGIN_TOP;
    
    for (int i = 0; i < player.getLives(); i++) {
      gImage.drawImage(playerImage, barX + i * (playerWidth / 2 + PLAYER_SPRITE_GAP), livesY, playerWidth / 2, playerHeight / 2, null);
    }
  }
  
  public void drawScore(Graphics gImage) {
    int score = player.getScore();
    GameUtils.drawWord(gImage, "Score: " + score, Color.green, SCORE_FONT_SIZE, SCORE_MARGIN_LEFT, SCORE_MARGIN_TOP);
  }
}
