package org.myProject;

import org.myProject.obj.*;
import org.myProject.utils.GameUtils;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameWin extends JFrame {
    //Game status 0 not started 1 in progress 2 paused 3 game passed 4 game failed
    public static int state=0;//The default state of the game
    Image offSreenimage=null;
    int width=600;
    int height=600;
    //The number of redraws of the game
    int count=1;
    //The number of enemy aircraft present
    int enemyCount=0;

    //ly
    private final int BULLET_SPEED = 10;
    private final int BULLET_DELAY = 100; // Time between bullet shots, in milliseconds
    private Timer bulletTimer;

    //ly
    // Define constants for power-ups
    private static final int POWERUP_WIDTH = 30;
    private static final int POWERUP_HEIGHT = 30;
    private static final int POWERUP_SPEED = 5;
    private static final int POWERUP_DELAY = 5000; // milliseconds

    private static final int GAME_DELAY = 10; // milliseconds
    private long lastPowerUpSpawnTime;



    // Define a timer for spawning power-ups
    private Timer powerUpTimer;

    // Define a list to store power-up objects
    private ArrayList<PowerUpsObj> powerUps = new ArrayList<>();

    public GameWin() {
        // ...
        lastPowerUpSpawnTime = System.currentTimeMillis();
    }




    //PlaneObj (Player)
    public PlaneObj planeobj = new PlaneObj(GameUtils.planeimg,290,550,20,30,0,this);

    public static ArrayList<GameObj> gameObjects = new ArrayList<>();

    public void addGameObject(GameObj obj) {
        gameObjects.add(obj);
//        if (obj instanceof BulletObj) {
//            bulletObjs.add((BulletObj) obj);
//        } else if (obj instanceof EnemyObj) {
//            enemyObjs.add((EnemyObj) obj);
//        } else
        if (obj instanceof PowerUpsObj) {
            powerUps.add((PowerUpsObj) obj);
        }
    }


    public void removeGameObject(GameObj gameObject) {
        gameObjects.remove(gameObject);
    }


    public Image getPowerUpImage() {
        return Toolkit.getDefaultToolkit().getImage("image/powerup.png");
    }



    //ly
    // Define a method for starting the power-up timer
    private void startPowerUpTimer() {
        TimerTask powerUpTask = new TimerTask() {
            @Override
            public void run() {
                // Generate a random x-coordinate for the power-up
                int x = new Random().nextInt(getWidth() - POWERUP_WIDTH);

                // Create a new power-up object and add it to the game's list of objects
                Image powerUpImg = getPowerUpImage();
                PowerUpsObj powerUpObj = new PowerUpsObj(powerUpImg, x, 0, POWERUP_WIDTH, POWERUP_HEIGHT, POWERUP_SPEED, GameWin.this);
                addGameObject(powerUpObj);
                powerUps.add(powerUpObj);
            }
        };
        powerUpTimer = new Timer();
        powerUpTimer.schedule(powerUpTask, 0, POWERUP_DELAY);
    }



    //Movement of the background image
    /**
    BgObj bgobj=new BgObj(GameUtils.bgimg,0,-400,2);
     */
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
        /**
        GameUtils.gameObjList.add(bgobj);
         */
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
            PowerUpsObj.spawnPowerUp(this);


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
        //Draw the new picture to the main window at once
        g.drawImage(offSreenimage,0,0,null);
        count++;





    }
    //The creation method is used to generate bullets and enemy planes in batches
     void create(){
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
//         //temp
//         PowerUpsObj.spawnPowerUp(this);
//         startPowerUpTimer();
//         // spawn power-ups every 5 seconds
//         if (System.currentTimeMillis() - lastPowerUpSpawnTime > 5000) {
//             PowerUpsObj.spawnPowerUp(this);
//             lastPowerUpSpawnTime = System.currentTimeMillis();
//         }



     }

    public static void main(String[] args) {
        GameWin Gamewin=new GameWin();
        Gamewin.launch();
    }

}
