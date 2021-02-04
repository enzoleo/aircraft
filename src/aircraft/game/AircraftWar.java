package aircraft.game;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Timer;
import java.util.TimerTask;

public class AircraftWar extends JPanel {
  // Define serialVersionUID which is used during deserialization to verify
  // that the sender and receiver of a serialized object have loaded classes
  // for that object that are compatible with respect to serialization. 
  private static final long serialVersionUID = 1L;

  public static void main(String[] args) {
    JFrame frame = new JFrame("AircraftWar");
    Canvas canvas = new Canvas();
    frame.add(canvas);
    frame.setSize(Canvas.WIDTH, Canvas.HEIGHT);
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

