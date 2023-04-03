package org.myProject.obj;

import org.myProject.GameWin;
import org.myProject.utils.GameUtils;

import java.awt.*;

/**
 * Represents a bullet in the game. Bullets are fired by either the player or enemy and have a direction and damage value.
 *
 * @author Lucas Ying
 */
public class BulletObj extends GameObj {
  private final int DAMAGE = 1;
  public boolean isEnemyBullet;

  /**
   * Constructs a new bullet object with the given image, position, size, speed, and owner.
   *
   * @param img the image to use for the bullet
   * @param x the starting x-coordinate of the bullet
   * @param y the starting y-coordinate of the bullet
   * @param width the width of the bullet
   * @param height the height of the bullet
   * @param speed the speed at which the bullet moves
   * @param frame the game window that the bullet is in
   * @param isEnemyBullet true if the bullet was fired by an enemy, false if it was fired by the player
   */
  public BulletObj(Image img, int x, int y, int width, int height, double speed, GameWin frame, boolean isEnemyBullet) {
    super(img, x, y, width, height, speed, frame);
    this.isEnemyBullet = isEnemyBullet;
  }

  /**
   *Overrides the paintself method in GameObj class to paint the BulletObj on the screen,
   *moves the bullet, and removes it if it goes off-screen.
   *@param gImage the Graphics object used to draw the bullet on the screen
   */
  @Override
  public void paintself(Graphics gImage) {
    super.paintself(gImage);
    move();
    removeBullet();
  }

  /**
   * Moves the bullet in its current direction.
   */
  public void move() {
    int newY;
    if (isEnemyBullet) {
      // Move the bullet down the screen at the given speed for enemy bullets
      newY = (int) (getY() + getSpeed());
    } else {
      // Move the bullet up the screen at the given speed for player bullets
      newY = (int) (getY() - getSpeed());
    }
    setY(newY);
  }

  /**
   * Checks if the bullet has gone off the top of the screen.
   *
   * @return true if the bullet is off the screen, false otherwise
   */
  public boolean isOffScreen() {
    // Check if the bullet has gone off the top of the screen
    return getY() + getHeight() < 0;
  }

  /**
   * Removes the bullet from the game if it has gone off the top of the screen.
   */
  public void removeBullet(){
    if (isOffScreen()){
      GameUtils.bulletObjList.remove(this);
      GameUtils.gameObjList.remove(this);
    }
  }

  /**
   * Gets the amount of damage that the bullet inflicts.
   *
   * @return the damage value of the bullet
   */
  public int getDamage() {
    return DAMAGE;
  }
}
