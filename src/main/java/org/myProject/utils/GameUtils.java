package org.myProject.utils;

import org.myProject.obj.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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

    public static Image enemyshellimg = Toolkit.getDefaultToolkit().getImage("image/enemyshell.png");

    // boss bullets
    public static Image bulletimg = Toolkit.getDefaultToolkit().getImage("image/bossbullet.png");
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

    public static List<ExplodeObj> explodeObjList = new ArrayList<>();

    public static List<PowerUpsObj> powerUpsObjList = new ArrayList<>();
    public static List<HealPowerUpsObj> powerUpsObjList2 = new ArrayList<>();

    public static List<HealPowerUpsObj> powerUpsObjList3 = new ArrayList<>();
    public static List<PowerUpsObj> powerUpsObjList4 = new ArrayList<>();

    public static Image powerups = Toolkit.getDefaultToolkit().getImage("image/powerup.jpg");
    public static Image powerups2 = Toolkit.getDefaultToolkit().getImage("image/shell.png");

    // Tool class for drawing strings
    public static void drawWord(Graphics gImage, String str, Color color, int size, int x, int y) {
        gImage.setColor(color);
        gImage.setFont(new Font("Arial", Font.BOLD, size));
        gImage.drawString(str, x, y);
    }

}
