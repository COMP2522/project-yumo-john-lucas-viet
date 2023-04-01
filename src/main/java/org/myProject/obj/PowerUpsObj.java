package org.myProject.obj;

import java.awt.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;


import org.myProject.GameWin;
import org.myProject.utils.GameUtils;

public class PowerUpsObj extends GameObj {

  private int powerUpWidth;
  private int powerUpHeight;


  GameWin gameWin;





  public PowerUpsObj(Image powerUpImg, int x, int y, int powerUpWidth, int powerUpHeight, int speed, GameWin gameWin) {
    super(powerUpImg, x, y, powerUpWidth, powerUpHeight, speed, gameWin);
    this.powerUpWidth = powerUpWidth;
    this.powerUpHeight = powerUpHeight;
  }

  public PowerUpsObj() {

  }


  public void paintself(Graphics gImage) {
    //g.drawImage(powerUpImg, getX(), getY(), null);
    try {
      super.paintself(gImage);
      checkCollision();
    } catch(ConcurrentModificationException e){}
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
    } catch(ConcurrentModificationException e){}

}



  public void spawnPowerUp(int x, int y) {
    double probability = Math.random();
    int number = (int) (Math.random() * 2) + 1;




    if (probability <= 0.8) {
      if (number == 1) {
        GameUtils.gameObjList.remove(GameUtils.powerUpsObjList);
        GameUtils.powerUpsObjList.add(new PowerUpsObj(GameUtils.powerups, x, y, 20, 30, 0, gameWin));
      } else {
        GameUtils.gameObjList.remove(GameUtils.powerUpsObjList2);
        GameUtils.powerUpsObjList2.add(new HealPowerUpsObj(GameUtils.powerups2, x, y, 20, 30, 0, gameWin));
      }
      GameUtils.gameObjList.addAll(GameUtils.powerUpsObjList);
      GameUtils.gameObjList.addAll(GameUtils.powerUpsObjList2);



    }
  }




}

