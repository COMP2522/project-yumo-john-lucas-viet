package org.myProject.obj;

import java.awt.*;

/**
 *The BackGround class represents a background object in the game.
 * It inherits from the GameObj class.
 *
 * @author YumoZhou
 */
public class BackGround extends GameObj {
    public BackGround() {
        super();
    }
    // the height of the background image is 1024,the height of the window is 600,use -400 to keep resetting the position of the image.
    private static final int a = -400;

    /**
     *Constructs a new BackGround instance with the specified image, x coordinate, y coordinate, and speed.
     *@param img the image of the background object
     *@param x the x coordinate of the background object
     *@param y the y coordinate of the background object
     *@param speed the speed at which the background object moves
     */
    public BackGround(Image img, int x, int y, double speed) {
        super(img, x, y, speed);
    }

    /**
     *Paints the background object on the specified graphics context.
     *@param gImage the graphics context to paint on
     */
    @Override
    public void paintself(Graphics gImage) {
        super.paintself(gImage);
        y+=speed;
        if(y>=0){
            y = a;
        }
    }
}
