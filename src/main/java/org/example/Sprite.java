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
    checkEdges();
  }
  
  public void checkEdges() {
    // wrap around the screen if the sprite goes off the edge
    if (position.x > p.width + size/2) {
      position.x = -size/2;
    } else if (position.x < -size/2) {
      position.x = p.width + size/2;
    }
    if (position.y > p.height + size/2) {
      position.y = -size/2;
    } else if (position.y < -size/2) {
      position.y = p.height + size/2;
    }
  }
  
  public boolean collidesWith(Sprite other) {
    float d = PVector.dist(position, other.position);
    return d < (size + other.size) / 2;
  }
  
  public abstract void draw();
}