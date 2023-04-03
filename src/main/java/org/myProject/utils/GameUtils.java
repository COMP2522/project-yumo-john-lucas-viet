package org.myProject.utils;

import org.myProject.obj.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 *A utility class containing various game-related images and lists of game objects.
 *
 * @author YumoZhou
 */
public class GameUtils {
    // Background picture
    public static Image bgimg = Toolkit.getDefaultToolkit().getImage("image/bg.jpeg");
    // boss
    public static Image bossimg = Toolkit.getDefaultToolkit().getImage("image/boss.png");
    // explode
    public static Image explodeimg = Toolkit.getDefaultToolkit().getImage("image/explode/e11.gif");
    // heroplane
    public static Image planeimg = Toolkit.getDefaultToolkit().getImage("image/plane.png");
    // player bullets
    public static Image shellimg = Toolkit.getDefaultToolkit().getImage("image/shell.png");

    // Enemy and boss bullet
    public static Image reverseShell = Toolkit.getDefaultToolkit().getImage("image/shellReverse.png");
    // enemy
    public static Image enemyimg = Toolkit.getDefaultToolkit().getImage("image/enemy.png");
    // needs to be removed
    public static List<GameObj> removeobjList = new ArrayList<>();

    // everything
    public static List<GameObj> gameObjList = new ArrayList<>();

    // our bullets
    public static List<BulletObj> bulletObjList = new ArrayList<>();
    // enemies

    public static List<EnemyObj> enemyObjList = new ArrayList<>();

    public static List<PowerUpsObj> powerUpsObjListBulletUpgrade = new ArrayList<>();
    public static List<HealPowerUpsObj> powerUpsObjListHeal = new ArrayList<>();

    public static List<HealPowerUpsObj> powerUpsObjListStart = new ArrayList<>();
    public static List<PowerUpsObj> powerUpsObjListStart2 = new ArrayList<>();

    public static Image powerups = Toolkit.getDefaultToolkit().getImage("image/powerup.jpg");
    public static Image powerups2 = Toolkit.getDefaultToolkit().getImage("image/powerup2.png");


    /**
     * Draws a string on the specified graphics object using the specified color, size, and position.
     *
     * @param gImage the graphics object to draw on
     * @param str the string to draw
     * @param color the color to draw the string in
     * @param size the size of the font to use
     * @param x the x-coordinate of the string's position
     * @param y the y-coordinate of the string's position
     */
    public static void drawWord(Graphics gImage, String str, Color color, int size, int x, int y) {
        gImage.setColor(color);
        gImage.setFont(new Font("Arial", Font.BOLD, size));
        gImage.drawString(str, x, y);
    }

}
