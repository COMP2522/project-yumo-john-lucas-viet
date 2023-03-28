package org.myProject.obj;

import java.awt.*;

import org.myProject.GameWin;
import org.myProject.utils.GameUtils;

public class PowerUpsObj extends GameObj {

  private Image powerUpImg;
  private int powerUpWidth;
  private int powerUpHeight;

  public PowerUpsObj(Image powerUpImg, int x, int y, int powerUpWidth, int powerUpHeight, int speed, GameWin gameWin) {
    super(powerUpImg, x, y, powerUpWidth, powerUpHeight, speed, gameWin);
    this.powerUpImg = Toolkit.getDefaultToolkit().getImage("image/plane.png");;
    this.powerUpWidth = powerUpWidth;
    this.powerUpHeight = powerUpHeight;
  }

  public PowerUpsObj() {

  }

  @Override
  public void paintself(Graphics g) {
    g.drawImage(powerUpImg, getX(), getY(), null);
  }


  public void move() {
    setY((int) (getY() + getSpeed()));
  }


  public Rectangle getRectangle() {
    return new Rectangle(getX(), getY(), powerUpWidth, powerUpHeight);
  }


//  public void collide(GameObj obj) {
//    if (obj instanceof PlaneObj) {
//      // add power-up effect here
//      GameWin.gameObjects.remove(this);
//    }
//  }


  public static void spawnPowerUp(GameWin gameWin) {
    if (true) { // 50% chance to spawn a power-up
//      int x = (int) (Math.random() * (gameWin.getWidth() - GameUtils.powerups.getWidth(null)));
      int x = (gameWin.getWidth() - GameUtils.powerups.getWidth(null)) / 2;

      PowerUpsObj powerUpObj = new PowerUpsObj(GameUtils.powerups, x, GameUtils.powerups.getHeight(null),
              GameUtils.powerups.getWidth(null), GameUtils.powerups.getHeight(null),
              0, gameWin);
      gameWin.addGameObject(powerUpObj);
      GameUtils.gameObjList.add(powerUpObj); // add power-up object to the game object list


    }
  }







}
