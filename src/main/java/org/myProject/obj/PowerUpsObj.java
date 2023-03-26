package org.myProject.obj;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import org.myProject.GameWin;
import org.myProject.utils.GameUtils;

public class PowerUpsObj extends GameObj {

  private Image powerUpImg;
  private int powerUpWidth;
  private int powerUpHeight;

  public PowerUpsObj(Image powerUpImg, int x, int y, int powerUpWidth, int powerUpHeight, int speed, GameWin gameWin) {
    super(powerUpImg, x, y, powerUpWidth, powerUpHeight, speed, gameWin);
    this.powerUpImg = powerUpImg;
    this.powerUpWidth = powerUpWidth;
    this.powerUpHeight = powerUpHeight;
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


  public void collide(GameObj obj) {
    if (obj instanceof PlaneObj) {
      // add power-up effect here
      GameWin.gameObjects.remove(this);
    }
  }


  public static void spawnPowerUp(GameWin gameWin) {
    if (Math.random() < 0.5) { // 50% chance to spawn a power-up
      int x = (int) (Math.random() * (gameWin.getWidth() - GameUtils.powerups.getWidth(null)));
      PowerUpsObj powerUpObj = new PowerUpsObj(GameUtils.powerups, x, -GameUtils.powerups.getHeight(null),
              GameUtils.powerups.getWidth(null), GameUtils.powerups.getHeight(null),
              2, gameWin);
      gameWin.addGameObject(powerUpObj);
    }
  }

}
