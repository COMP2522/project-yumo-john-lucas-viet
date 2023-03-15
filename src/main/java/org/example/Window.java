package org.example;

import processing.core.PApplet;

public class Window extends PApplet {
  
  public void settings() {
    size(800, 600); // set the size of the window
  }
  
  public void setup() {
    // initialize game objects here
  }
  
  public void draw() {
    background(0); // set the background color of the window
    
    // update and draw game objects here
  }
  
  public static void main(String[] args) {
    String[] appletArgs = new String[]{"Window"};
    Window eatBubbles = new Window();
    PApplet.runSketch(appletArgs, eatBubbles);
  }
}