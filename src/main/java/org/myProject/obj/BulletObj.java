package org.myProject.obj;

import org.myProject.GameWin;

import java.awt.*;

public class BulletObj extends GameObj {
  private final int DAMAGE = 1;

  public BulletObj(Image img, int x, int y, int width, int height, double speed, GameWin frame) {
    super(img, x, y, width, height, speed, frame);
  }

  @Override
  public void paintself(Graphics gImage) {
    super.paintself(gImage);
    move();
  }

  public void move() {
    // Move the bullet up the screen at the given speed
    int newY = (int) (getY() - getSpeed());
    setY(newY);
  }

  public boolean isOffScreen() {
    // Check if the bullet has gone off the top of the screen
    return getY() + getHeight() < 0;
  }

  public int getDamage() {
    return DAMAGE;
  }
}
