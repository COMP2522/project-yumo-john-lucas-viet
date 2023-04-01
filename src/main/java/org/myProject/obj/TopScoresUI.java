package org.myProject.obj;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.bson.Document;
import org.myProject.utils.DB;

public class TopScoresUI extends GameObj {
  
  private static final int TOP_SCORES_FONT_SIZE = 10;
  private static final int TOP_SCORES_MARGIN_LEFT = 10;
  private static final int TOP_SCORES_MARGIN_TOP = 75;
  private static final int TOP_SCORES_VERTICAL_GAP = 20;
  private static final int TOP_FIVE_SCORES = 5;
  private DB db;
  private List<Document> topScores;
  
  
  public TopScoresUI(DB db) {
    this.db = db;
    try {
      // Retrieve the top 5 scores from the database and store them locally
      this.topScores = db.getTop5Scores();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }
  
  @Override
  public void paintself(Graphics gImage) {
    super.paintself(gImage);
    drawTopScores(gImage);
    
  }
  
  
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
  
  public void addLocalScore(PlaneObj plane) {
    String playerName = plane.getName();
    int playerScore = plane.getScore();
    boolean playerExists = false;
    
    // check if player already exists in topScores list
    for (Document score : topScores) {
      if (score.getString("User").equals(playerName)) {
        // player exists, update their score
        score.put("Score", playerScore);
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
    Collections.sort(topScores, (a, b) -> b.getInteger("Score") - a.getInteger("Score"));
    if (topScores.size() > TOP_FIVE_SCORES) {
      topScores.remove(TOP_FIVE_SCORES);
    }
  }

  
  
  
}

