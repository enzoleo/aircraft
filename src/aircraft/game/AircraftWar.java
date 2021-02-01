package aircraft.game;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyAdapter;
import java.awt.Color;
import java.awt.Font;

import java.util.Set;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import aircraft.game.plane.*;
import aircraft.game.bullet.*;
import aircraft.game.bomb.*;
import aircraft.game.supply.*;

public class AircraftWar extends JPanel {
  // Define serialVersionUID which is used during deserialization to verify
  // that the sender and receiver of a serialized object have loaded classes
  // for that object that are compatible with respect to serialization. 
  private static final long serialVersionUID = 1L;

  // The fundamental components of graphics window.
  public static final int WIDTH = 400;
  public static final int HEIGHT = 654;

  // The game status.
  public static final int RUNNING = 0;
  public static final int GAMEOVER = 1;
  public static int status = RUNNING;

  // The hash set to store all objects. Each time an object is constructed,
  // it should be added into this set, e.g. the push operation should be
  // called in the base constructor.
  public static final HashSet<Object> objects = new HashSet<>();
  public static final LinkedList<Object> trash = new LinkedList<>();
  public static final LinkedList<Object> newcome = new LinkedList<>();
  
  static BufferedImage background = ImageLoader.readImg("aircraft/images/background.png");
  static BufferedImage gameover = ImageLoader.readImg("aircraft/images/gameover.png");
  static HeroPlane hero = new HeroPlane(150., 400., 100, 2.0);
  public static int score = 0;
  
  public static int bossNum = 0;
  
  static {
    objects.add(hero);
  }

  // A very simple implementation to generate a random number given a
  // bernoulli distribution. Not perfect but enough for us.
  public static boolean bernoulli(double p) {
    if (Math.random() < p) return true;
    return false;
  }

  public static void generateCharacter() {
    if (bernoulli(0.01)) { // Generate enemies.
      double x = (Math.random() * 0.6 + 0.2) * WIDTH;
      if (bernoulli(0.2) && bossNum < 1) {
        newcome.add(new EnemyBoss(x, 0, 1000, 1.5));
        bossNum++;
      } else
        newcome.add(new EnemyLightPlane(x, 0, 20, 1.5));
    }
    if (bernoulli(0.002)) { // Generate bombs.
      double x = (Math.random() * 0.6 + 0.2) * WIDTH;
      newcome.add(new Bomb(x, 0));
    }
    if (bernoulli(0.006)) { // Generate supplies.
      double x = (Math.random() * 0.6 + 0.2) * WIDTH;
      newcome.add(new Supply(x, 0));
    }
  }

  // Draw everything including bullets, planes, supplies, etc.
  public void paint(Graphics graphics) {
    graphics.drawImage(background, 0, 0, null);
    // The toArray method is applied, instead of iterating the set
    // itself. This is an immediate solution to fix CME.
    for (Object object : objects.toArray(new Object[0])) {
      if (object instanceof Bullet) {
        ((Bullet)object).display(graphics);
      } else if (object instanceof Plane) {
        ((Plane)object).display(graphics);
      } else if (object instanceof Bomb) {
        ((Bomb)object).display(graphics);
      } else if (object instanceof Supply) {
        ((Supply)object).display(graphics);
      }
    }

    // Draw health point and game score on the screen.
    graphics.setColor(new Color(0x000000));
    graphics.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
    graphics.drawString("SCORE: " + score, 10, 25);
    graphics.drawString("HP: " + hero.health, 10, 45);

    if (status == GAMEOVER)
      graphics.drawImage(gameover, 0, 0, null);
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
    KeyListener keyListener = new KeyAdapter() {
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
                hero.direction.y = -1; break;
              // Press 'A' or 'LEFT' buttons, the horizontal direction will be -1,
              // which means that your hero plane is moving to the left.
              case KeyEvent.VK_A:
              case KeyEvent.VK_LEFT:
                hero.direction.x = -1; break;
              // Press 'S' or 'DOWN' buttons, the vertical direction will be 1,
              // which means that your hero plane is moving down.
              case KeyEvent.VK_S:
              case KeyEvent.VK_DOWN:
                hero.direction.y = 1; break;
              // Press 'D' or 'RIGHT' buttons, the horizontal direction will be 1,
              // which means that your hero plane is moving to the right.
              case KeyEvent.VK_D:
              case KeyEvent.VK_RIGHT:
                hero.direction.x = 1; break;
              case KeyEvent.VK_SPACE:
                hero.fireCommand = true;
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
            hero.direction.y = 0; break;
          case KeyEvent.VK_A:
          case KeyEvent.VK_D:
          case KeyEvent.VK_LEFT:
          case KeyEvent.VK_RIGHT:
            hero.direction.x = 0; break;
          case KeyEvent.VK_SPACE:
            hero.fireCommand = false;
        }
      }
    };
    frame.addKeyListener(keyListener);
    
    Timer timer = new Timer();
    int interval = 10;
    timer.schedule(new TimerTask() {
      public void run() {
        if (status == RUNNING) {
          generateCharacter();
          for (Object object : objects) {
            if (object instanceof Bullet) {
              ((Bullet)object).move();
            } else if (object instanceof Plane) {
              ((Plane)object).move();
              ((Plane)object).fire();
            } else if (object instanceof Bomb) {
              ((Bomb)object).move();
            } else if (object instanceof Supply) {
              ((Supply)object).move();
            }
          }

          for (Object object : objects) {
            if (object instanceof Plane) {
              // Check whether a plane (hero/enemy) is hit by some other
              // objects like bullets, supplies, barriers, etc.
              Plane plane = (Plane)object;
              for (Object npc : objects) {
                // If a plane is hit by an npc (bullets/bombs/...), take
                // specific actions. Different planes may react differently.
                if (plane.isHit(npc)) plane.hitBy(npc);
              }
              // The plane is shot down!
              if (plane.health < 0) plane.explode();
            }
          }
          // Push all objects that are goind to be displayed in
          // the next frame to the objects set.
          for (Object object : newcome) objects.add(object);
          newcome.clear();

          // Remove all objects that are going to be deleted.
          // Then the trash bin will be cleared and reused.
          for (Object object : trash) objects.remove(object);
          trash.clear();
        }
        aircraftWar.repaint();
      }
    }, interval, interval);
    
  }
}

