package org.myProject.obj;

import java.awt.*;
import java.util.ConcurrentModificationException;
import java.util.List;

import org.myProject.GameWin;
import org.myProject.utils.GameUtils;

public class HealPowerUpsObj extends GameObj {

  private int powerUpWidth;
  private int powerUpHeight;



  public HealPowerUpsObj(Image powerUpImg, int x, int y, int powerUpWidth, int powerUpHeight, int speed, GameWin gameWin) {
    super(powerUpImg, x, y, powerUpWidth, powerUpHeight, speed, gameWin);
    this.powerUpWidth = powerUpWidth;
    this.powerUpHeight = powerUpHeight;
  }




  public void paintself(Graphics gImage) {
    //g.drawImage(powerUpImg, getX(), getY(), null);
    super.paintself(gImage);
    checkCollision();
  }


  public void move() {
    setY((int) (getY() + getSpeed()));
  }


  public Rectangle getRectangle() {
    return new Rectangle(getX(), getY(), powerUpWidth, powerUpHeight);
  }


  private boolean collidesWith(GameObj otherObj) {
    Rectangle rect1 = this.getrect();
    Rectangle rect2 = otherObj.getrect();
    return rect1.intersects(rect2);
  }


  public void checkCollision(){
    List<GameObj> gameObjList = GameUtils.gameObjList;
    try {
      for (GameObj obj : gameObjList) {
        if (obj instanceof PlaneObj && this.collidesWith(obj)) {
          GameUtils.gameObjList.remove(this);
        }
      }
    }catch (ConcurrentModificationException ignored){}
  }
}
