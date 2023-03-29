package org.myProject.obj;

import org.myProject.GameWin;

import java.awt.*;
//import processing.core.PApplet;
//import processing.core.PImage;
//Writing the parent class of the game class

public class GameObj {
    Image img;
    int x;
    int y;
    int width;
    int height;
    double speed;//Moving speed
    GameWin frame;//window reference

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public GameObj(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public GameWin getFrame() {
        return frame;
    }

    public void setFrame(GameWin frame) {
        this.frame = frame;
    }

    //Parameterized and no-argument constructors
    public GameObj() {
    }

    public GameObj(Image img, int x, int y, double speed) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public GameObj(Image img, int x, int y, int width, int height, double speed, GameWin frame) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.frame = frame;
    }

    //draw itself
    public void paintself(Graphics gImage) {
        gImage.drawImage(img, x, y, null);
    }



    //The method of drawing a rectangle is used for collision detection
    public Rectangle getrect() {
        return new Rectangle(x, y, width, height);
    }
}



