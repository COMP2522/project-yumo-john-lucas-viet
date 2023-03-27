package org.myProject;

import org.myProject.obj.*;
import org.myProject.utils.GameUtils;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameWin extends JFrame {
    //Game status 0 not started 1 in progress 2 paused 3 game passed 4 game failed
    public static int state=0;//The default state of the game
    //COUNTING SCORE
    public static  int score=0;
    Image offSreenimage=null;
    int width=600;
    int height=600;
    //The number of redraws of the game
    int count=1;
    //The number of enemy aircraft present
    int enemyCount=0;
    
    //PlaneObj (Player)
    public PlaneObj planeobj = new PlaneObj(GameUtils.planeimg,290,550,20,30,0,this);

    //Movement of the background image

    BgObj bgobj=new BgObj(GameUtils.bgimg,0,-400,2);

    //boss
    /**
    public BossObj bossobj =null;
     */
     
    public void launch(){
        //Set whether the window is visible
        this.setVisible(true);
        //set window size
        this.setSize(width,height);
        //set window position
        this.setLocationRelativeTo(null);
        
        //set window title
        this.setTitle("comp2522-Project");

        GameUtils.gameObjList.add(bgobj);

        GameUtils.gameObjList.add(planeobj);

        //Mouse click
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                //Before starting and clicking the left mouse button
                if(e.getButton()==1&&state==0){
                    state=1;
                    repaint();
                }
            }
        });

        //game pause function
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                //Press the space bar, the code of the space bar is 32
                if(e.getKeyCode() == 32){
                    switch (state){
                        case 1:
                            state=2;
                            break;
                        case 2:
                            state=1;
                            break;
                        default:
                    }
                }
            }
        });
        while(true){
            if(state==1){
                create();
                repaint();
            }

            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        if(offSreenimage==null){
            offSreenimage=createImage(width,height);
        }
        //Get the brush object of offScrenimage
        Graphics gimage=offSreenimage.getGraphics();
        //Fill a region with a width of 600 and a height of 600
        gimage.fillRect(0,0,width,height);
        //game not started
        if(state==0){
            gimage.drawImage(GameUtils.bgimg,0,0,null);
            gimage.drawImage(GameUtils.bossimg,200,100,null);
            gimage.drawImage(GameUtils.explodeimg,270,370,null);
            GameUtils.drawWord(gimage,"press to start",Color.yellow,40,180,300);

        }
        //Games start
        if(state==1){
            /**
            GameUtils.gameObjList.addAll(GameUtils.explodeObjList);
             */

            for(int i = 0; i< GameUtils.gameObjList.size(); i++){
                GameUtils.gameObjList.get(i).paintself(gimage);
            }
            GameUtils.gameObjList.removeAll(GameUtils.removeobjList);
        }
        //game over
        if(state ==3) {
            gimage.drawImage(GameUtils.explodeimg, planeobj.getX() - 35, planeobj.getY() - 50, null);
            GameUtils.drawWord(gimage,"GAME OVER",Color.red,40,180,300);

        }
        //Game Win
        if(state==4) {
            /**
            gimage.drawImage(GameUtils.explodeimg, bossobj.getX() + 35, bossobj.getY() + 50, null);
            GameUtils.drawWord(gimage, " Game Win", Color.red, 40, 180, 300);
             */
        }
        GameUtils.drawWord(gimage,score+"SCORE",Color.green,40,30,100);
        //Draw the new picture to the main window at once
        g.drawImage(offSreenimage,0,0,null);
        count++;
    }
    //The creation method is used to generate bullets and enemy planes in batches
     void create(){
        //Our bullets are divided by 10 to control the velocity of the bullets
        
         /**
        if(count%10==0){
            GameUtils.shellObjList.add(new ShellObj(GameUtils.shellimg,planeobj.getX()+4,planeobj.getY()-16,14,29,5,this));
            GameUtils.gameObjList.add(GameUtils.shellObjList.get(GameUtils.shellObjList.size()-1));
        }
        //enemy fighter
        if(count%15==0){
            GameUtils.enemyObjList.add(new EnemyObj(GameUtils.enemyimg,(int)(Math.random()*12)*50,0,50,50,5,this));
            GameUtils.gameObjList.add(GameUtils.enemyObjList.get(GameUtils.enemyObjList.size()-1));
            enemyCount++;
        }
        //enemy boss bullet
         //Bullets are not spawned until the boss appears
         if(count%15==0 && bossobj !=null){
             GameUtils.bulletObjList.add(new BulletObj(GameUtils.bulletimg,bossobj.getX()+75,bossobj.getY()+80,15,25,5,this));
             GameUtils.gameObjList.add(GameUtils.bulletObjList.get(GameUtils.bulletObjList.size()-1));
         }
         if( enemyCount>30 && bossobj == null ){
             bossobj=new BossObj(GameUtils.bossimg,250,20,155,100,5,this);
             GameUtils.gameObjList.add(bossobj);
         }
          */
          
    }

    public static void main(String[] args) {
        GameWin Gamewin=new GameWin();
        Gamewin.launch();
    }

}
