package aircraft.game;

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
import java.util.HashMap;
import java.util.Iterator;

import aircraft.game.plane.*;
import aircraft.game.bullet.*;
import aircraft.game.bomb.*;
import aircraft.game.supply.*;

public class Canvas extends JPanel {
  // Define serialVersionUID which is used during deserialization to verify
  // that the sender and receiver of a serialized object have loaded classes
  // for that object that are compatible with respect to serialization. 
  private static final long serialVersionUID = 1L;

  // The game status.

  private static final BufferedImage background = ImageLoader.readImg("background.png");
  private static final BufferedImage gameover = ImageLoader.readImg("gameover.png");

  // The fundamental components of graphics window.
  private final int width = Setting.width;
  private final int height = Setting.height;
  
  private final HeroPlane hero = // Initialize the hero plane (player).
    new HeroPlane(this, Setting.hero_init_pos.x, Setting.hero_init_pos.y);
  public boolean status = Setting.RUNNING;

  // The hash set to store all objects. Each time an object is constructed,
  // it should be added into this set, e.g. the push operation should be
  // called in the base constructor.
  public HashSet<Object> objects = new HashSet<>();
  public HashSet<Object> trash = new HashSet<>();
  public HashSet<Object> newcome = new HashSet<>();
  
  public int bossNum = 0;

  // Add keylistener to the game in order to obtain the user manipulation, and
  // then take specific actions accordingly.
  private final KeyListener keyListener = new KeyAdapter() {
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
  
  public Canvas() {
    this.objects.add(hero); 
  }

  // Getter of the key listener.
  public KeyListener getKeyListener() {
    return this.keyListener;
  }
  // Getters of canvas width and height.
  public int getWidth() { return this.width; }
  public int getHeight() { return this.height; }

  // Getter of the hero plane.
  public HeroPlane getHeroPlane() { return hero; }

  // A very simple implementation to generate a random number given a
  // bernoulli distribution. Not perfect but enough for us.
  public static boolean bernoulli(double p) {
    if (Math.random() < p) return true;
    return false;
  }

  private void generateCharacter() {
    HashMap<String, Double> p = Setting.prob;
    if (bernoulli(p.get("EnemyPlane"))) { // Generate enemies.
      double x = (Math.random() * 0.6 + 0.2) * width;
      HashMap<String, Double> prob = Setting.subProb.get("EnemyPlane");
      if (bernoulli(prob.get("EnemyBoss")) && bossNum < 1) {
        // Generate a boss ship. Note that at most one boss ship can
        // be displayed at a time.
        newcome.add(new EnemyBoss(this, x, 0));
        bossNum++;
      } else
        newcome.add(new EnemyLightPlane(this, x, 0));
    }
    if (bernoulli(p.get("Bomb"))) { // Generate bombs.
      double x = (Math.random() * 0.6 + 0.2) * width;
      newcome.add(new Bomb(this, x, 0));
    }
    if (bernoulli(p.get("Supply"))) { // Generate supplies.
      double x = (Math.random() * 0.6 + 0.2) * width;
      newcome.add(new Supply(this, x, 0));
    }
  }

  // Draw everything including bullets, planes, supplies, etc.
  @Override
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
    graphics.drawString("SCORE: " + hero.getScore(), 5, 25);
    graphics.drawString("HP: " + hero.health, 5, 55);

    if (status == Setting.GAMEOVER)
      graphics.drawImage(gameover, 0, 0, null);
  }

  public void render() {
    if (status == Setting.RUNNING) {
      generateCharacter();
      for (Object object : objects) {
        if (object instanceof Bullet) {
          ((Bullet)object).move();
          ((Bullet)object).boundaryCheck();
        } else if (object instanceof Plane) {
          ((Plane)object).move();
          ((Plane)object).boundaryCheck();
          ((Plane)object).fire();
        } else if (object instanceof Bomb) {
          ((Bomb)object).move();
          ((Bomb)object).boundaryCheck();
        } else if (object instanceof Supply) {
          ((Supply)object).move();
          ((Supply)object).boundaryCheck();
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
          if (plane.health <= 0) plane.explode();
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
    repaint();
  }

}

