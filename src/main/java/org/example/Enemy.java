package org.example;

import processing.core.PApplet;
import processing.core.PVector;

public class Enemy extends Sprite implements Drawable, CollisionDetection{
    public Enemy(PApplet p, PVector position, PVector velocity, int size) {
        super(p, position, velocity, size);
    }

    @Override
    public void checkEdges(int screenWidth, int screenHeight) {

    }

    @Override
    public boolean collidesWith(Sprite other) {
        return false;
    }

    @Override
    public void draw() {

    }
}
