package aircraft.game;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

import aircraft.game.plane.*;

public class AircraftWar extends JPanel {
  // Define serialVersionUID which is used during deserialization to verify
  // that the sender and receiver of a serialized object have loaded classes
  // for that object that are compatible with respect to serialization. 
  private static final long serialVersionUID = 1L;

  // The fundamental components of graphics window.
  public static final int WIDTH = 400;
  public static final int HEIGHT = 654;

  static BufferedImage background = ImageLoader.readImg("aircraft/images/background.png");
  HeroPlane hero = new HeroPlane();

  public void paint(Graphics graphics) {
    graphics.drawImage(background, 0, 0, null);
    hero.display(graphics);
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame("AircraftWar");
    AircraftWar aircraftWar = new AircraftWar();
    frame.add(aircraftWar);
    frame.setSize(WIDTH, HEIGHT);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);

    // The paint method is called here.
    frame.setVisible(true);
    
    // Add keylistener to the game in order to obtain the user manipulation, and
    // then take specific actions accordingly.
    KeyListener keyListener = new KeyListener() {
      // We use a hashset to store all currently pressed keys.
      // This is necessary to enable multi-key listening event.
      private final Set<Integer> pressed = new HashSet<>();

      @Override
      public synchronized void keyPressed(KeyEvent e) {
        pressed.add(e.getKeyCode());
        if (!pressed.isEmpty()) {
          for (Iterator<Integer> it = pressed.iterator(); it.hasNext();) {
            switch (it.next()) {
              // Press 'W' or 'UP' buttons, the vertical direction will be -1,
              // which means that your hero plane is moving up.
              case KeyEvent.VK_W:
              case KeyEvent.VK_UP:
                aircraftWar.hero.direction.y = -1; break;
              // Press 'A' or 'LEFT' buttons, the horizontal direction will be -1,
              // which means that your hero plane is moving to the left.
              case KeyEvent.VK_A:
              case KeyEvent.VK_LEFT:
                aircraftWar.hero.direction.x = -1; break;
              // Press 'S' or 'DOWN' buttons, the vertical direction will be 1,
              // which means that your hero plane is moving down.
              case KeyEvent.VK_S:
              case KeyEvent.VK_DOWN:
                aircraftWar.hero.direction.y = 1; break;
              // Press 'D' or 'RIGHT' buttons, the horizontal direction will be 1,
              // which means that your hero plane is moving to the right.
              case KeyEvent.VK_D:
              case KeyEvent.VK_RIGHT:
                aircraftWar.hero.direction.x = 1; break;
            }
          }
        }
      }

      @Override
      public synchronized void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        pressed.remove(code);
        switch (code) {
          // Release key, then reset all direction value to zero, so that we
          // can prevent the hero from unpredictable moving.
          case KeyEvent.VK_W:
          case KeyEvent.VK_S:
          case KeyEvent.VK_UP:
          case KeyEvent.VK_DOWN:
            aircraftWar.hero.direction.y = 0; break;
          case KeyEvent.VK_A:
          case KeyEvent.VK_D:
          case KeyEvent.VK_LEFT:
          case KeyEvent.VK_RIGHT:
            aircraftWar.hero.direction.x = 0; break;
        }
      }

      @Override
      public void keyTyped(KeyEvent e) { /* This method is never used. */ }
    };
    frame.addKeyListener(keyListener);
    
    Timer timer = new Timer();
    int interval = 10;
    timer.schedule(new TimerTask() {
      public void run() {
        aircraftWar.hero.move();
        aircraftWar.repaint();
      }
    }, interval, interval);
  }
}

