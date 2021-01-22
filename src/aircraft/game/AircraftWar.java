package aircraft.game;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;

public class AircraftWar extends JPanel {
  // Define serialVersionUID which is used during deserialization to verify
  // that the sender and receiver of a serialized object have loaded classes
  // for that object that are compatible with respect to serialization. 
  private static final long serialVersionUID = 1L;

  // The fundamental components of graphics window.
  public static final int WIDTH = 400;
  public static final int HEIGHT = 654;

  static BufferedImage background = ImageLoader.readImg("aircraft/images/background.png");

  public void paint(Graphics graphics) {
    graphics.drawImage(background, 0, 0, null);
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame("AircraftWar");
    AircraftWar game = new AircraftWar();
    frame.add(game);
  
    frame.setSize(WIDTH, HEIGHT);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}

