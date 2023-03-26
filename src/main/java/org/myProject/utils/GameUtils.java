package org.myProject.utils;

import org.myProject.obj.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameUtils {
    //Background picture
    public static Image bgimg =Toolkit.getDefaultToolkit().getImage("image/bg.jpeg");
    //boss
    public static Image bossimg =Toolkit.getDefaultToolkit().getImage("image/boss.png");
    //explode
    public static Image explodeimg =Toolkit.getDefaultToolkit().getImage("image/explode/e11.gif");
    //heroplane
    public static Image planeimg =Toolkit.getDefaultToolkit().getImage("image/plane.png");
    //our bullets
    public static Image shellimg =Toolkit.getDefaultToolkit().getImage("image/shell.png");
    //boss bullets
    public static Image bulletimg =Toolkit.getDefaultToolkit().getImage("image/bossbullet.png");
    //enemy
    public static Image enemyimg =Toolkit.getDefaultToolkit().getImage("image/enemy.png");
    //needs to be removed
    public static List<GameObj> removeobjList =new ArrayList<>();

    //everything
    public static List<GameObj> gameObjList =new ArrayList<>();
    //our bullets
    /**

    public static List<ShellObj> shellObjList =new ArrayList<>();
    //enemies
    public static List<EnemyObj> enemyObjList =new ArrayList<>();
    //boss bullets
    public static List<BulletObj> bulletObjList =new ArrayList<>();
    //explode
    public static List<ExplodeObj> explodeObjList =new ArrayList<>();
     */

    //Tool class for drawing strings
    public static  void drawWord(Graphics gImage,String str,Color color,int size,int x,int y){
        gImage.setColor(color);
        gImage.setFont(new Font("仿宋",Font.BOLD,size));
        gImage.drawString(str,x,y);
    }

}
