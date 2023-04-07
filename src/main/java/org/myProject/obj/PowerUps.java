package org.myProject.obj;

import java.awt.*;
import java.util.ConcurrentModificationException;
import java.util.List;


import org.myProject.Window;
import org.myProject.utils.GameUtils;


/**
 * Represents a power-up game object that can be spawned in the game world. It extends the GameObj class and adds
 * functionality for collision detection with the player's plane object and the ability to spawn a power-up object
 * based on a random probability.
 *
 * @author Lucas Ying
 */
public class PowerUps extends GameObj {

  /**
   * The width of the power-up object.
   */
  private int powerUpWidth;

  /**
   * The height of the power-up object.
   */
  private int powerUpHeight;


  /**
   * Constructs a new PowerUps with the specified image, position, size, speed, and Window instance.
   *
   * @param powerUpImg The image to use for the power-up object.
   * @param x The x-coordinate of the power-up object's position.
   * @param y The y-coordinate of the power-up object's position.
   * @param powerUpWidth The width of the power-up object.
   * @param powerUpHeight The height of the power-up object.
   * @param speed The speed of the power-up object.
   * @param frame The Window instance that the power-up object is associated with.
   */
  public PowerUps(Image powerUpImg, int x, int y, int powerUpWidth, int powerUpHeight, int speed, Window frame) {
    super(powerUpImg, x, y, powerUpWidth, powerUpHeight, speed, frame);
    this.powerUpWidth = powerUpWidth;
    this.powerUpHeight = powerUpHeight;
  }



  /**
   * Draws the power-up object on the specified graphics context and checks for collision with the player's plane object.
   *
   * @param gImage The graphics context on which to draw the power-up object.
   */
  public void paintself(Graphics gImage) {
    try {
      super.paintself(gImage);
      checkCollision();
    } catch(ConcurrentModificationException e){}
  }



  /**
   * Checks if the power-up object collides with another game object.
   *
   * @param otherObj The other game object to check for collision.
   * @return true if the power-up object collides with the other game object, false otherwise.
   */
  private boolean collidesWith(GameObj otherObj) {
    Rectangle rect1 = this.getrect();
    Rectangle rect2 = otherObj.getrect();
    return rect1.intersects(rect2);
  }

  /**
   * Checks for collision with the player's plane object and removes the power-up object from the game object list if collided.
   */
  public void checkCollision(){
    List<GameObj> gameObjList = GameUtils.gameObjList;
    try {
      for (GameObj obj : gameObjList) {
        if (obj instanceof Player && this.collidesWith(obj)) {
          GameUtils.gameObjList.remove(this);
          GameUtils.gameObjList.remove(GameUtils.powerUpsListBulletUpgrade);



        }
      }
    } catch(ConcurrentModificationException e){}

}


  /**
   * Spawns a power-up object at the specified position based on a random probability.
   *
   * @param x The x-coordinate of the power-up object's position.
   * @param y The y-coordinate of the power-up object's position.
   */
  public void spawnPowerUp(int x, int y) {
    double probability = Math.random();
    int number = (int) (Math.random() * 3) + 1;


    if (probability <= 0.2) {
      GameUtils.powerUpsListBulletUpgrade.clear();
      GameUtils.powerUpsObjListHeal.clear();
      if (number == 1) {
        GameUtils.powerUpsListBulletUpgrade.add(new PowerUps(GameUtils.powerups, x, y, 20, 30, 0, frame));
      } else {
        GameUtils.powerUpsObjListHeal.add(new HealPowerUps(GameUtils.powerups2, x, y, 20, 30, 0, frame));
      }


      GameUtils.gameObjList.addAll(GameUtils.powerUpsListBulletUpgrade);
      GameUtils.gameObjList.addAll(GameUtils.powerUpsObjListHeal);

    }
  }

  /**
   *Adds a new PowerUps object to the powerUpsListBulletUpgrade list and to the gameObjList.
   * 100% add two bullet upgrades.
   *@param x the x coordinate of the HealPowerUps object
   *@param y the y coordinate of the HealPowerUps object
   */
  public void bossPowerUp(int x, int y) {
    GameUtils.powerUpsListBulletUpgrade.clear();
    GameUtils.powerUpsListBulletUpgrade.add(new PowerUps(GameUtils.powerups, x, y, 20, 30, 0, frame));
    GameUtils.gameObjList.addAll(GameUtils.powerUpsListBulletUpgrade);

  }





}

