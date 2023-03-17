package org.example;

import processing.core.*;

public class Player extends Sprite {
  private int lives; // number of lives left
  private int cooldown; // frames left until the next shot can be fired
  private int score; // current score
  private boolean moving = false; // Is player moving?
  
  public void setMoving(boolean moving) {
    this.moving = moving;
  }
  
  public Player(PApplet p, PVector position, PVector velocity, int size, int lives) {
    super(p, position, velocity, size);
    this.lives = lives;
    this.cooldown = 0;
    this.score = 0;
    //this.playerImage = playerImage;
  }
  
  @Override
  public void draw() {
    // Set the fill color to green
    p.fill(0, 255, 0);
    
    // Save the current transform matrix
    p.pushMatrix();
    
    // Translate to the player's position
    p.translate(position.x, position.y);
    
    // Draw the inverted triangle
    p.triangle(0, -size/2, size/2, size/2, -size/2, size/2);
    
    // Restore the previous transform matrix
    p.popMatrix();
  }
  
  public void display() {
    p.imageMode(PConstants.CENTER);
  }
  
  public void update() {
    if (moving) {
      // move the player if it is currently moving
      position.add(velocity);
      checkEdges(800, 600);
    }
    if (cooldown > 0) {
      cooldown--;
    }
  }
  
  public void move(char direction, boolean move) {
    float speed = 5.0f; // adjust as needed
    
    if (move) {
      switch (direction) {
        case 'w':
          velocity.y = -speed;
          break;
        case 'a':
          velocity.x = -speed;
          break;
        case 's':
          velocity.y = speed;
          break;
        case 'd':
          velocity.x = speed;
          break;
        default:
          // do nothing for other characters
          break;
      }
    } else {
      // stop the player's movement in the corresponding direction
      switch (direction) {
        case 'w':
        case 's':
          velocity.y = 0;
          break;
        case 'a':
        case 'd':
          velocity.x = 0;
          break;
        default:
          // do nothing for other characters
          break;
      }
    }
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
