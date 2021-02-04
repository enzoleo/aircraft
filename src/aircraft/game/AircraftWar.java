package aircraft.game;

import javax.swing.JFrame;
import java.util.Timer;
import java.util.TimerTask;

public class AircraftWar {
  public static void main(String[] args) {
    JFrame frame = new JFrame("AircraftWar");
    Canvas canvas = new Canvas(Setting.width, Setting.height);
    frame.add(canvas);
    frame.setSize(canvas.getWidth(), canvas.getHeight());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);

    // The paint method is called here.
    frame.setVisible(true);
    frame.addKeyListener(canvas.getKeyListener());
    
    Timer timer = new Timer();
    timer.schedule(new TimerTask() {
      public void run() { canvas.render(); }
    }, 8, 8);
  }
}

