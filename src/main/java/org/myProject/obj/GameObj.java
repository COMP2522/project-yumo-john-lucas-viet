package org.myProject.obj;

import org.myProject.GameWin;

import java.awt.*;
/**
 *A class that represents a game object with a position, size, image, and moving speed.
 *
 * @author: YumoZhou
 */
public class GameObj {
    Image img;
    int x;
    int y;
    int width;
    int height;
    double speed;//Moving speed
    GameWin frame;//window reference

    /**
     * Get the image of the object
     * @return the image of the object
     */
    public Image getImg() {
        return img;
    }

    /**
     * Set the image of the object
     * @param img the new image of the object
     */
    public void setImg(Image img) {
        this.img = img;
    }

    /**
     * Get the x-coordinate of the object
     * @return the x-coordinate of the object
     */
    public int getX() {
        return x;
    }

    /**
     * Set the x-coordinate of the object
     * @param x the new x-coordinate of the object
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Create a new GameObj with the given x and y coordinates
     * @param x the x-coordinate of the new GameObj
     * @param y the y-coordinate of the new GameObj
     */
    public GameObj(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the y-coordinate of the object
     * @return the y-coordinate of the object
     */
    public int getY() {
        return y;
    }

    /**
     * Set the y-coordinate of the object
     * @param y the new y-coordinate of the object
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Get the width of the object
     * @return the width of the object
     */
    public int getWidth() {
        return width;
    }

    /**
     * Set the width of the object
     * @param width the new width of the object
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Get the height of the object
     * @return the height of the object
     */
    public int getHeight() {
        return height;
    }

    /**
     * Set the height of the object
     * @param height the new height of the object
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Get the moving speed of the object
     * @return the moving speed of the object
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Set the moving speed of the object
     * @param speed the new moving speed of the object
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Get the window reference of the object
     * @return the window reference of the object
     */
    public GameWin getFrame() {
        return frame;
    }

    /**
     * Set the window reference of the object
     * @param frame the new window reference of the object
     */
    public void setFrame(GameWin frame) {
        this.frame = frame;
    }

    /**
     * Create a new GameObj with no arguments
     */
    public GameObj() {
    }

    /**
     * Create a new GameObj with 4 arguments
     */
    public GameObj(Image img, int x, int y, double speed) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    /**
     * Create a new GameObj with 7 arguments
     */
    public GameObj(Image img, int x, int y, int width, int height, double speed, GameWin frame) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.frame = frame;
    }
    /**
     *This method is used to draw the image of the game object on the screen.
     *It takes a Graphics object as parameter and uses it to draw the image
     *of the object at its current position (x,y) on the screen.
     *@param gImage The Graphics object used to draw the image of the game object.
     */
    public void paintself(Graphics gImage) {
        gImage.drawImage(img, x, y, null);
    }


    /**
     *Returns a rectangle object that represents the bounding box of the game object.
     *This method is typically used for collision detection.
     *@return a rectangle object representing the bounding box of the game object.
     */
    public Rectangle getrect() {
        return new Rectangle(x, y, width, height);
    }

}



