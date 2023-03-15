package org.example;

import processing.core.*;

public class Sprite {
  protected PApplet p; // reference to Processing applet
  protected PVector position; // current position of the sprite
  protected PVector velocity; // current velocity of the sprite
  protected PImage image; // sprite image
  
  protected int size; // sprite size
  
  public Sprite(PApplet p, PVector position, PVector pVector, PImage image, int size) {
    this.p = this.p;
    this.position = this.position;
    this.velocity = velocity;
    this.image = this.image;
    this.size = this.size;
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
  
  public void display() {
    p.imageMode(PConstants.CENTER);
    p.image(image, position.x, position.y, size, size);
  }
  
  public boolean collidesWith(Sprite other) {
    float d = PVector.dist(position, other.position);
    return d < (size + other.size) / 2;
  }
}