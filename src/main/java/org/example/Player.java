package org.example;


import processing.core.*;

public class Player extends Sprite {
  private int lives; // number of lives left
  private int cooldown; // frames left until the next shot can be fired
  private int score; // current score
  
  public Player(PApplet p, PVector position, PImage image, int size, int lives) {
    super(p, position, new PVector(0, 0), image, size);
    this.lives = lives;
    this.cooldown = 0;
    this.score = 0;
  }
  
  public void move(float dx, float dy) {
    // move the player by a given amount
    position.add(dx, dy);
    checkEdges();
  }
  
  public void draw() {
    display();
  }
  
  public void powerup() {
    // do nothing for now
  }
  
  public void explode() {
    // do nothing for now
  }
  
  /**
   public void shoot() {
   if (cooldown <= 0) {
   // create a new bullet at the player's position
   Bullet bullet = new Bullet(p, new PVector(position.x, position.y - size/2), new PVector(0, -10), 10);
   Game.addBullet(bullet);
   cooldown = 10; // set a cooldown of 10 frames
   }
   }
   */
  
  public void collisions() {
    // do nothing for now
  }
}