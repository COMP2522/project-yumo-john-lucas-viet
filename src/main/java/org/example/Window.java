package org.example;

import processing.core.*;
import processing.event.KeyEvent;

public class Window extends PApplet {
  
  private Player player;
  
  public void settings() {
    size(800, 600); // set the size of the window
  }
  
  public void setup() {
    int gapSize = 20;
    int playerSize = 25;
    PVector position = new PVector(width / 2, height - gapSize - playerSize / 2);
    PVector velocity = new PVector(0, 0);
    player = new Player(this, position, velocity, playerSize, 3);
  }
  
  public void draw() {
    background(0); // set the background color of the window
    
    // update and draw game objects here
    
    player.update(); // update player position
    player.draw(); // draw player object
  }
  
  public void keyPressed(KeyEvent event) {
    switch (event.getKey()) {
      case 'w':
      case 'a':
      case 's':
      case 'd':
        player.setMoving(true);
        player.move(event.getKey(), true);
        break;
      default:
        // do nothing for other keys
        break;
    }
  }
  
  public void keyReleased(KeyEvent event) {
    switch (event.getKey()) {
      case 'w':
      case 'a':
      case 's':
      case 'd':
        player.setMoving(false);
        player.move(event.getKey(), false);
        break;
      default:
        // do nothing for other keys
        break;
    }
  }
  
  public void save(){
    player.savePlayerInfo();
  }
  
  public static void main(String[] args) {
    String[] appletArgs = new String[]{"Window"};
    Window window = new Window();
    PApplet.runSketch(appletArgs, window);
  }
  
}
