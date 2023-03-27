package org.myProject.Interfaces;

import org.myProject.obj.GameObj;

public interface CollisionDetection {
  public void checkEdges(int screenWidth, int screenHeight);
  public boolean collidesWith(GameObj other);
}
