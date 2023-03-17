package org.example;

public interface CollisionDetection {
  public void checkEdges(int screenWidth, int screenHeight);
  public boolean collidesWith(Sprite other);
}
