package org.myProject;

import com.jogamp.opengl.math.geom.Frustum;
import org.bson.Document;
import org.myProject.obj.*;
import org.myProject.utils.DB;
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

    boolean hasPowerup = true;
    
    DB db = new DB();

    // Define a list to store power-up objects
    private ArrayList<PowerUpsObj> powerUps = new ArrayList<>();




    public PowerUpsObj powerobj = new PowerUpsObj(GameUtils.powerups, 100, 400, 0, 0, 0, this);

    //PlaneObj (Player)
    //String playerName = JOptionPane.showInputDialog(this, "Enter your name:");
    public PlaneObj planeobj = new PlaneObj(GameUtils.planeimg,290,550,20,30,0,this, "");
    
    public TopScoresUI topScores = new TopScoresUI(db);

    public static ArrayList<GameObj> gameObjects = new ArrayList<>();

    public void addGameObject(GameObj obj) {
        gameObjects.add(obj);
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
    






    //Movement of the background image

    BgObj bgobj=new BgObj(GameUtils.bgimg,0,-400,2);

    //boss
    /**
     public BossObj bossobj =null;
     */

    public String getValidPlayerName(PlaneObj planeobj, DB db) {
        String playerName;
        while (true) {
            playerName = JOptionPane.showInputDialog(this, "Enter your name:");
            if (db.validateName(playerName)) {
                planeobj.setName(playerName);
                // insert the new user into the database
                db.put(planeobj.getName(), planeobj.getScore());
                break; // exit the loop once a valid name is entered
            } else {
                JOptionPane.showMessageDialog(this, "Name already taken. Please choose a different name.");
            }
        }
        return playerName;
    }
    
    
    public void launch(){
    
        planeobj.setName(getValidPlayerName(planeobj, db));
    
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
    
        GameUtils.gameObjList.add(topScores);

        //GameUtils.gameObjList.add(powerobj);




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
            //PowerUpsObj.spawnPowerUp(this);
            db.put(planeobj.getName(), planeobj.getScore());



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
          /*enemy fighter
          The first if statement set each enemy to be 35 away from each other horizontally.
          And add them into enemyObj list and then add them into gameObj list.
          The total number of enemy on screen is 12. This could be change base
          on the game progress.
          The for loop, loop through the enemy in the enemyObj list and determine how far they move down the window.
          Intend to make a formation. There will be different formation in the future update.
          */


        if (hasPowerup) {
            hasPowerup = false;
            GameUtils.powerUpsObjList.add(new PowerUpsObj(GameUtils.powerups, 100, 400, 20, 30,
                    2, this));
            GameUtils.powerUpsObjList.add(new PowerUpsObj(GameUtils.powerups, 400, 400, 20, 30,
                    2, this));
            GameUtils.gameObjList.addAll(GameUtils.powerUpsObjList);
        }

//        for(PowerUpsObj power : GameUtils.powerUpsObjList){
//            power.checkCollision();
//        }


        if (enemyCount < 12) {
            int x = 32;
            for (int i = 0; i < 12; i++) {
                GameUtils.enemyObjList.add(new EnemyObj(GameUtils.enemyimg, x, 0, 20, 30, 1, this));
                x += 45;
                enemyCount++;
                if (enemyCount == 12) {
                    break;
                }
            }
            GameUtils.gameObjList.addAll(GameUtils.enemyObjList);
        }

        for(EnemyObj enemy : GameUtils.enemyObjList){
            enemy.checkCollision(planeobj);
        }

        /**
         * Make the enemy plane fly in reversed V formation
         */
        int y = 0;
        for (EnemyObj enemy: GameUtils.enemyObjList) {
            if (y < 2 || y >= 10) {
                enemy.moveDown(200);
            } else if(y < 4 || y >= 8) {
                enemy.moveDown(150);
            }else if(y < 5 || y == 7){
                enemy.moveDown(100);
            }else{
                enemy.moveDown(50);
            }
            y++;
        }
        //enemy boss bullet
        //Bullets are not spawned until the boss appears
         /*
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
