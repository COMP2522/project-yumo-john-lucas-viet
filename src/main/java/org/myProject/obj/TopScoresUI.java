package org.myProject.obj;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.bson.Document;
import org.myProject.utils.DB;

/**
 * This class represents the user interface responsible for
 * displaying the topScores
 *
 * @author John2T
 */
public class TopScoresUI extends GameObj {
  
  private static final int TOP_SCORES_FONT_SIZE = 10;
  private static final int TOP_SCORES_MARGIN_LEFT = 10;
  private static final int TOP_SCORES_MARGIN_TOP = 75;
  private static final int TOP_SCORES_VERTICAL_GAP = 20;
  private static final int TOP_FIVE_SCORES = 5;
  private List<Document> topScores;
  
  /**
   * Constructs a new instance of the TopScoresUI class.
   * @param db The database instance used to retrieve the top 5 scores.
   */
  public TopScoresUI(DB db) {
    try {
      // Retrieve the top 5 scores from the database and store them locally
      this.topScores = db.getTop5Scores();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }

/**
 * Overrides the paintself method in GameObj.
 * Draws the top scores on the screen.
 * @param gImage The Graphics object to draw on.
 */
  @Override
  public void paintself(Graphics gImage) {
    super.paintself(gImage);
    drawTopScores(gImage);
  }
  
  /**
   * Draws the top five scores on the screen.
   * @param gImage The Graphics object to draw on.
   */
  public void drawTopScores(Graphics gImage) {
    int x = TOP_SCORES_MARGIN_LEFT;
    int y = TOP_SCORES_MARGIN_TOP;
    gImage.setColor(Color.WHITE);
    gImage.setFont(new Font("Arial", Font.BOLD, TOP_SCORES_FONT_SIZE));
    gImage.drawString("Top Scores:", x, y);
    
    int i = 1;
    for (Document score : topScores) {
      String user = score.getString("User");
      int userScore = score.getInteger("Score");
      gImage.drawString(i + ". " + user + ": " + userScore, x, y + (i * TOP_SCORES_VERTICAL_GAP));
      i++;
    }
  }
  
  /**
   * Adds the local score to the top scores list.
   * @param plane The Player object that contains the score to add.
   */
  /**
   * Adds the local score to the top scores list.
   * @param plane The Player object that contains the score to add.
   */
  public void addLocalScore(Player plane) {
    String playerName = plane.getName();
    int playerScore = plane.getScore();
    boolean playerExists = false;
    
    // check if player already exists in topScores list
    for (Document score : topScores) {
      if (score.getString("User").equals(playerName)) {
        // player exists, update their score if current score is higher
        int existingScore = score.getInteger("Score");
        if (playerScore > existingScore) {
          score.put("Score", playerScore);
        }
        playerExists = true;
        break;
      }
    }
    
    // player does not exist, add new score to list
    if (!playerExists) {
      Document newScore = new Document("User", playerName).append("Score", playerScore);
      topScores.add(newScore);
    }
    
    // sort the list and remove any excess scores
    topScores.sort((a, b) -> b.getInteger("Score") - a.getInteger("Score"));
    if (topScores.size() > TOP_FIVE_SCORES) {
      topScores.remove(TOP_FIVE_SCORES);
    }
  }
  
}

