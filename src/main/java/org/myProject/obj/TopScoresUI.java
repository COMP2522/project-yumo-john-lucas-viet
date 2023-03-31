package org.myProject.obj;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.bson.Document;
import org.myProject.utils.DB;

public class TopScoresUI extends GameObj {
  
  private static final int TOP_SCORES_FONT_SIZE = 10;
  private static final int TOP_SCORES_MARGIN_LEFT = 10;
  private static final int TOP_SCORES_MARGIN_TOP = 75;
  private static final int TOP_SCORES_VERTICAL_GAP = 20;
  private DB db;
  private List<Document> topScores;
  
  public TopScoresUI(DB db){
    this.db = db;
  }
  
  @Override
  public void paintself(Graphics gImage) {
    super.paintself(gImage);
    db.getTop5ScoresAsync().thenAccept(scores -> {
      this.topScores = scores;
      // trigger a repaint of the component to show the updated scores
    });
    drawTopScores(gImage);
  }
  
  
  public void drawTopScores(Graphics gImage) {
    int x = TOP_SCORES_MARGIN_LEFT;
    int y = TOP_SCORES_MARGIN_TOP;
    gImage.setColor(Color.WHITE);
    gImage.setFont(new Font("Arial", Font.BOLD, TOP_SCORES_FONT_SIZE));
    gImage.drawString("Top Scores:", x, y);
    
    for (int i = 0; i < topScores.size(); i++) {
      Document score = topScores.get(i);
      String user = score.getString("User");
      int userScore = score.getInteger("Score");
      gImage.drawString(user + ": " + userScore, x, y + ((i + 1) * TOP_SCORES_VERTICAL_GAP));
    }
  }
}

