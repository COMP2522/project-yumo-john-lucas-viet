package org.myProject.obj;

import java.awt.*;
import java.util.List;

import org.myProject.GameWin;
import org.myProject.utils.GameUtils;

public class PowerUpsObj extends GameObj {

  private Image powerUpImg;
  private int powerUpWidth;
  private int powerUpHeight;

  private boolean isActive;

  GameWin gameWin;

  public PowerUpsObj(Image powerUpImg, int x, int y, int powerUpWidth, int powerUpHeight, int speed, GameWin gameWin) {
    super(powerUpImg, x, y, powerUpWidth, powerUpHeight, speed, gameWin);
    this.powerUpImg = Toolkit.getDefaultToolkit().getImage("image/powerup.jpg");;
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

      PowerUpsObj powerUpObj = new PowerUpsObj(GameUtils.powerups, 100, 400,
              GameUtils.powerups.getWidth(null), GameUtils.powerups.getHeight(null),
              0, gameWin);
      gameWin.addGameObject(powerUpObj);
      GameUtils.gameObjList.add(powerUpObj); // add power-up object to the game object list


    }
  }

  private boolean collidesWith(GameObj otherObj){
    Rectangle rect1 = this.getrect();
    Rectangle rect2 = otherObj.getrect();
    return rect1.intersects(rect2);
  }

  public void checkCollision(GameWin gameWin){
    List<GameObj> gameObjList = GameUtils.gameObjList;
    PowerUpsObj powerUpObj = new PowerUpsObj(GameUtils.powerups, 100, 400,
            GameUtils.powerups.getWidth(null), GameUtils.powerups.getHeight(null),
            0, gameWin);
    for (GameObj obj : gameObjList) {
      if (this.collidesWith(obj)) {
        GameUtils.removeobjList.add(powerUpObj);
      }
    }
  }
}
