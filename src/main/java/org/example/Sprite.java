package org.example;

import processing.core.*;

public abstract class Sprite implements Drawable, CollisionDetection {
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
}