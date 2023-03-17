package org.example;

import processing.core.*;

public abstract class Sprite implements Drawable {
  protected PApplet p; // reference to Processing applet
  protected PVector position; // current position of the sprite
  protected PVector velocity; // current velocity of the sprite
  //protected PImage image; // sprite image
  
  protected int size; // sprite size
  
  public Sprite(PApplet p, PVector position, PVector velocity, int size) {
    this.p = p;
    this.position = position;
    this.velocity = velocity;
    this.size = size;
  }
  
  
  public void update() {
    position.add(velocity);
    /**
     * Change checkEdges args if screen size is changed
     */
    checkEdges(800, 600);
  }
  
  public void checkEdges(int screenWidth, int screenHeight) {
    // calculate the half-width and half-height of the triangle
    float halfWidth = size / 2.0f;
    float halfHeight = (float) (size * Math.sqrt(3) / 2.0) / 2.0f;
    
    // restrict movement if the sprite goes off the edge
    if (position.x - halfWidth < 0) {
      position.x = halfWidth;
    } else if (position.x + halfWidth > screenWidth) {
      position.x = screenWidth - halfWidth;
    }
    if (position.y - halfHeight < 0) {
      position.y = halfHeight;
    } else if (position.y + halfHeight > screenHeight) {
      position.y = screenHeight - halfHeight;
    }
  }
  
  
  public boolean collidesWith(Sprite other) {
    float d = PVector.dist(position, other.position);
    return d < (size + other.size) / 2;
  }
  
  public abstract void draw();
}