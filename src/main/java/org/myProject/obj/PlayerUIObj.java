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
  
  
  /**
   * The PlaneObj object representing the player associated with this UI object.
   */
  private final PlaneObj player;
  private static final int MAX_HEALTH = 4;
  private static final int HEALTH_BAR_WIDTH = 150;
  private static final int HEALTH_BAR_HEIGHT = 15;
  private static final int HEALTH_BAR_MARGIN_LEFT = 10;
  private static final int HEALTH_BAR_MARGIN_BOTTOM = 10;
  private static final int PLAYER_SPRITE_GAP = 5;
  private static final int SCORE_FONT_SIZE = 30;
  private static final int SCORE_MARGIN_LEFT = 15;
  private static final int SCORE_MARGIN_TOP = 60;
  
  /**
   * Creates a new PlayerUIObj object associated with the specified PlaneObj player.
   *
   * @param player the PlaneObj object representing the player to track
   */
  public PlayerUIObj(PlaneObj player) {
    this.player = player;
  }
  
  /**
   * Paints the UI components for the player.
   *
   * @param gImage the graphics context on which to paint the UI components
   */
  @Override
  public void paintself(Graphics gImage) {
    super.paintself(gImage);
    drawHealthUI(gImage);
  }
  
  /**
   * Draws the health UI component for the player.
   *
   * @param gImage the graphics context on which to draw the health UI component
   */
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
    drawScore(gImage);
    drawName(gImage);
  }
  
  /**
   * Draws the score UI component for the player
   *
   * @param gImage the graphics context on which to draw the score UI component
   */
  public void drawScore(Graphics gImage) {
    int score = player.getScore();
    GameUtils.drawWord(gImage, "Score: " + score, Color.green, SCORE_FONT_SIZE, SCORE_MARGIN_LEFT, SCORE_MARGIN_TOP);
  }
  
  /**
   * Draws the name UI component for the player.
   *
   * @param gImage the graphics context on which to draw the name UI component
   */
  public void drawName(Graphics gImage) {
    gImage.setColor(Color.WHITE);
    gImage.setFont(new Font("Arial", Font.BOLD, 12));
    String playerName = player.getName(); // assuming there's a getName() method in PlaneObj
    int playerNameX = player.getX() + player.getWidth() / 2 - gImage.getFontMetrics().stringWidth(playerName) / 2;
    int playerNameY = player.getY() + player.getHeight() + PLAYER_SPRITE_GAP + 12; // 12 is the font size
    gImage.drawString(playerName, playerNameX, playerNameY);
  }
}
