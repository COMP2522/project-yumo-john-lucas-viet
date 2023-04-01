package org.myProject.obj;

import java.awt.*;
import java.util.ConcurrentModificationException;
import java.util.List;

import org.myProject.GameWin;
import org.myProject.utils.GameUtils;


/**
 * Represents a power-up object that heals the plane when collected.
 * Extends the GameObj class and adds functionality for collision detection with planes.
 *
 * @author Lucas Ying
 */
public class HealPowerUpsObj extends GameObj {

  /**
   * The width of the power-up object.
   */
  private int powerUpWidth;
  /**
   * The height of the power-up object.
   */
  private int powerUpHeight;


  /**
   * Constructs a new HealPowerUpsObj with the given parameters.
   * @param powerUpImg The image of the power-up object
   * @param x The x coordinate of the power-up object
   * @param y The y coordinate of the power-up object
   * @param powerUpWidth The width of the power-up object
   * @param powerUpHeight The height of the power-up object
   * @param speed The speed of the power-up object
   * @param gameWin The GameWin object that the power-up object belongs to
   */
  public HealPowerUpsObj(Image powerUpImg, int x, int y, int powerUpWidth, int powerUpHeight, int speed, GameWin gameWin) {
    super(powerUpImg, x, y, powerUpWidth, powerUpHeight, speed, gameWin);
    this.powerUpWidth = powerUpWidth;
    this.powerUpHeight = powerUpHeight;
  }



  /**
   * Paints the power-up object and checks for collisions with planes.
   * @param gImage The Graphics object to use for painting
   */
  public void paintself(Graphics gImage) {
    //g.drawImage(powerUpImg, getX(), getY(), null);
    super.paintself(gImage);
    checkCollision();
  }



  /**
   * Checks if this power-up object collides with another GameObj.
   * @param otherObj The other GameObj to check for collision
   * @return True if the power-up object collides with the other object, false otherwise
   */
  private boolean collidesWith(GameObj otherObj) {
    Rectangle rect1 = this.getrect();
    Rectangle rect2 = otherObj.getrect();
    return rect1.intersects(rect2);
  }

  /**
   * Checks for collisions between this power-up object and planes, and handles the collisions by removing the power-up object from the game.
   */
  public void checkCollision(){
    List<GameObj> gameObjList = GameUtils.gameObjList;
    try {
      for (GameObj obj : gameObjList) {
        if (obj instanceof PlaneObj && this.collidesWith(obj)) {
          GameUtils.gameObjList.remove(this);
          GameUtils.gameObjList.remove(GameUtils.powerUpsObjList2);

        }
      }
    } catch (ConcurrentModificationException e){}

}
}
