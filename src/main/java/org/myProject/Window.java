package org.myProject;

import org.myProject.obj.*;
import org.myProject.utils.DB;
import org.myProject.utils.GameUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;


/**
 * The Window class extends JFrame and is responsible for painting the game
 * window with its graphical components, including the player, the enemies,
 * bullets, and game messages. It implements the KeyListener interface
 * to receive keyboard input events from the user.
 *
 * @author YumoZhou
 */
public class Window extends JFrame {

    /**
     * The current state of the game.
     * 0 - not started
     * 1 - in progress
     * 2 - paused
     * 3 - game failed
     */
    public static int state = 0; // The default state of the game

    /**
     * The off-screen image used for double buffering.
     */
    Image offSreenimage = null;
    int width = 600;
    int height = 600;

    /**
     * The number of redraws of the game.
     */
    int count = 1;

    /**
     * The number of enemy aircraft present.
     */
    int enemyCount = 0;

    /**
     * The number of waves completed.
     */
    int waveCount = 0;

    /**
     * Whether the boss enemy has appeared.
     */
    boolean bossAppear = false;

    /**
     * Whether a power-up is available.
     */
    boolean hasPowerup = true;

    /**
     * The database instance used to store player scores and passwords.
     */
    DB db = new DB();

    /**
     * The list of power-up objects in the game.
     */
    private ArrayList<PowerUps> powerUps = new ArrayList<>();

    /**
     * The top scores UI object that displays the highest scores.
     */
    public TopScoresUI topScores = new TopScoresUI(db);

    /**
     * The player's plane object.
     */
    public Player planeobj = new Player(GameUtils.planeimg, 290, 550, 20, 30, 0, this, topScores);

    /**
     * Decrements the number of enemy aircraft present by x.
     *
     * @param x The number of enemy aircraft to decrement by.
     */
    public void setEnemyCount(int x) {
        this.enemyCount -= x;
    }

    /**
     * Returns the player's plane object.
     *
     * @return The player's plane object.
     */
    public Player getPlaneobj() {
        return this.planeobj;
    }

    /**
     * Resets the wave count to 0.
     */
    public void resetWaveCount() {
        this.waveCount = 0;
    }

    /**
     * Sets the appearance status of the boss enemy.
     *
     * @param b True if the boss enemy should appear, false otherwise.
     */
    public void setBossAppear(boolean b) {
        this.bossAppear = b;
    }

    // Movement of the background image
    BackGround bgobj = new BackGround(GameUtils.bgimg, 0, -400, 2);

    /**
     * Validates a player's name and password and sets them to a Player object.
     * The validation is done by checking if the name exists in the database, and if
     * it does, the entered password is compared with the one stored in the database.
     * If the name is not in the database, a new record with the name, password, and
     * score is created.
     *
     * @param planeobj The Player object to set the player name and password to.
     * @param db       The database instance to use for validation and record creation.
     * @return The validated player's name.
     */
    public String validatePlayer(Player planeobj, DB db) {
        String playerName;
        while (true) {
            playerName = JOptionPane.showInputDialog(this, "Enter your name:");
            if (playerName == null || playerName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name cannot be empty. Please try again.");
            } else if (db.validateName(playerName)) {
                planeobj.setName(playerName);
                String password;
                do {
                    password = JOptionPane.showInputDialog(this, "Create a password:");
                } while (password == null || password.isEmpty()); // keep prompting for a non-null and non-empty
                                                                  // password
                planeobj.setPassword(password);
                db.put(planeobj.getName(), planeobj.getScore(), planeobj.getPassword());
                break; // exit the loop once a valid name and password are entered
            } else {
                String password = JOptionPane.showInputDialog(this, "Enter your password:");
                if (db.validatePassword(playerName, password)) {
                    planeobj.setPassword(password);
                    break; // exit the loop if the password is correct
                } else {
                    JOptionPane.showMessageDialog(this, "Incorrect password. Please try again.");
                }
            }
        }
        return playerName;
    }

    /**
     * Launches the game and handles game loop.
     */
    public void launch() {
        planeobj.setName(validatePlayer(planeobj, db));
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                db.put(planeobj.getName(), planeobj.getScore(), planeobj.getPassword());
            }
        }, 0, 5000); // execute the task every 5 seconds

        // Set whether the window is visible
        this.setVisible(true);
        // set window size
        this.setSize(width, height);
        // set window position
        this.setLocationRelativeTo(null);

        // set window title
        this.setTitle("comp2522-Project");

        GameUtils.gameObjList.add(bgobj);

        GameUtils.gameObjList.add(planeobj);

        GameUtils.gameObjList.add(topScores);

        // Mouse click
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Before starting and clicking the left mouse button
                if (e.getButton() == 1 && state == 0) {
                    state = 1;
                    repaint();
                }
            }
        });

        // game pause function
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Press the space bar, the code of the space bar is 32
                if (e.getKeyCode() == 32) {
                    switch (state) {
                        case 1:
                            state = 2;
                            break;
                        case 2:
                            state = 1;
                            break;
                        default:
                    }
                }
            }
        });
        while (true) {
            if (state == 1) {
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

    /**
     *This method overrides the paint() method of the Graphics class.
     *It paints the game elements onto the screen. It creates an off-screen image
     *to avoid flicker, fills it with a solid color, and then draws the game elements onto it.
     *The image is then drawn onto the main window at once.
     *@param g the Graphics object to draw on
     */
    @Override
    public void paint(Graphics g) {
        if (offSreenimage == null) {
            offSreenimage = createImage(width, height);
        }
        // Get the brush object of offScreen image
        Graphics gimage = offSreenimage.getGraphics();
        // Fill a region with a width of 600 and a height of 600
        gimage.fillRect(0, 0, width, height);
        // game not started
        if (state == 0) {
            gimage.drawImage(GameUtils.bgimg, 0, 0, null);
            gimage.drawImage(GameUtils.explodeimg, 270, 370, null);
            GameUtils.drawWord(gimage, "Click to start", Color.yellow, 40, 170, 300);

        }
        // Games start
        if (state == 1) {
            for (int i = 0; i < GameUtils.gameObjList.size(); i++) {
                GameUtils.gameObjList.get(i).paintself(gimage);
            }

            GameUtils.gameObjList.removeAll(GameUtils.removeobjList);
        }
        // game over
        if (state == 3) {
            // Final player data written when the game is over
            db.put(planeobj.getName(), planeobj.getScore(), planeobj.getPassword());
            gimage.drawImage(GameUtils.explodeimg, planeobj.getX() - 35, planeobj.getY() - 50, null);
            GameUtils.drawWord(gimage, "GAME OVER", Color.red, 40, 180, 300);
        }
        // Draw the new picture to the main window at once
        g.drawImage(offSreenimage, 0, 0, null);
        count++;
    }

    /**
     *This method is used to generate bullets and enemy planes in batches.
     *It creates power-ups, enemy planes, and bosses depending on the state of the game.
     */
    void create() {

        if (hasPowerup) {
            hasPowerup = false;
            GameUtils.powerUpsListStart2.add(new PowerUps(GameUtils.powerups, 100, 400, 20, 30,
                    2, this));
            GameUtils.powerUpsObjListStart.add(new HealPowerUps(GameUtils.powerups2, 400, 400, 20, 30,
                    2, this));
            GameUtils.gameObjList.addAll(GameUtils.powerUpsListStart2);
            GameUtils.gameObjList.addAll(GameUtils.powerUpsObjListStart);
        }

        if (enemyCount == 0) {
            int x = 32;
            for (int i = 0; i < 12; i++) {
                GameUtils.enemyList.add(new Enemy(GameUtils.enemyimg, x, 0, 20, 30, 1, this, x));
                x += 45;
                enemyCount++;
                if (enemyCount == 12) {
                    waveCount++;
                    break;
                }
            }
            // Keep track of number of enemy waves
            if (waveCount == 4) {
                setBossAppear(true);
            }
            GameUtils.gameObjList.addAll(GameUtils.enemyList);
        }

        int y = 0;
        for (Enemy enemy : GameUtils.enemyList) {
            if (y < 2 || y >= 10) {
                enemy.moveDown(200);
            } else if (y < 4 || y >= 8) {
                enemy.moveDown(150);
            } else if (y < 5 || y == 7) {
                enemy.moveDown(100);
            } else {
                enemy.moveDown(50);
            }
            y++;
        }

        if (this.bossAppear) {
            GameUtils.gameObjList.add(new Boss(GameUtils.bossimg, 280, -120, 50, 50, 1, this));
        }
    }

    /**
     * This method is the entry point for the Java application.
     * It creates a new instance of the Window class and calls its launch() method, which starts the game.
     * The args parameter is not used in this method.
     */
    public static void main(String[] args) {
        Window gamewin = new Window();
        gamewin.launch();
    }
}