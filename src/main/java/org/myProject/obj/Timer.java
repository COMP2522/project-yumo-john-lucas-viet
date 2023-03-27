package org.myProject.obj;

import java.util.TimerTask;

public class Timer {
  
  private Timer timer;
  
  public Timer(){
    timer = new Timer();
  }
  
  public void scheduleTask(TimerTask task, long delay, long period){
  
  }
  
  public void cancel(){
    timer.cancel();
  }
}
