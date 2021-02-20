package aircraft.game;

import java.awt.geom.Point2D;
import java.util.HashMap;

public class Setting {
  // Default window size.
  public static final int WIDTH = 400;
  public static final int HEIGHT = 654;
  
  // Default game status.
  public static final boolean RUNNING = true;
  public static final boolean GAMEOVER = false;

  // Camp constants.
  public static final int NEUTRAL = 0;
  public static final int HERO = 1;
  public static final int ENEMY = 2;

  // The initial position of the hero plane. Feel free to edit it.
  public static final Point2D.Double HERO_INIT_POS = new Point2D.Double(150, 500);

  // The image filenames.
  public static final HashMap<String, String> IMAGES = new HashMap<>();
  static {
    IMAGES.put("HeroPlane", "hero_plane.png");
    IMAGES.put("EnemyLightPlane", "enemy_light_plane.png");
    IMAGES.put("EnemyBoss", "enemy_boss.png");
    IMAGES.put("HeroBullet", "hero_bullet.png");
    IMAGES.put("EnemyNormalBullet", "enemy_normal_bullet.png");
    IMAGES.put("EnemyCannon", "enemy_cannon.png");
    IMAGES.put("Bomb", "bomb.png");
    IMAGES.put("Supply", "supply.png"); 
 }

  // Probabilities when generating characters at the beginning of every
  // frame. Feel free to modify them.
  public static final HashMap<String, Double> PROB = new HashMap<>();
  static {
    PROB.put("EnemyPlane", 0.01);
    PROB.put("Bomb", 0.001);
    PROB.put("Supply", 0.005);
  }

  // The sub probability. When an enemy plane is going to be generated,
  // it will 'mutate' to a boss with respect to a specific probability.
  public static final HashMap<String, HashMap<String, Double> > SUB_PROB =
    new HashMap<>();
  static {
    HashMap<String, Double> item = new HashMap<>();
    item.put("EnemyBoss", 0.1);
    SUB_PROB.put("EnemyPlane", item);
  }
  
  // Initial health point of each kind of planes.
  public static final HashMap<String, Integer> HEALTH = new HashMap<>();
  static {
    HEALTH.put("HeroPlane", 100);
    HEALTH.put("EnemyLightPlane", 20);
    HEALTH.put("EnemyBoss", 800);
  }

  // Initial damage power of each kind of bullets.
  public static final HashMap<String, Integer> DAMAGE = new HashMap<>();
  static {
    DAMAGE.put("HeroBullet", 20);
    DAMAGE.put("EnemyNormalBullet", 5);
    DAMAGE.put("EnemyCannon", 8);
  }

  // The speed of each kind of characters in this game. 
  public static final HashMap<String, Double> SPEED = new HashMap<>();
  static {
    SPEED.put("HeroPlane", 2.0);
    SPEED.put("EnemyLightPlane", 1.5);
    SPEED.put("EnemyBoss", 1.5);
    SPEED.put("HeroBullet", 3.0);
    SPEED.put("EnemyNormalBullet", 4.0);
    SPEED.put("EnemyCannon", 3.0);
    SPEED.put("Bomb", 1.0);
    SPEED.put("Supply", 1.5); 
  }
  
    // The recovery power of the supply. Without supply, you cannot live long.
  // Try to catch supplies as many as possible.
  public static final HashMap<String, Integer> RECOVERY = new HashMap<>();
  static {
    RECOVERY.put("Supply", 20);
  }

  // When you take down an enemy, you will get bonus.
  public static final HashMap<String, Integer> BONUS = new HashMap<>();
  static {
    BONUS.put("EnemyLightPlane", 10);
    BONUS.put("EnemyBoss", 80);
  }
  
  // Planes can fire. We assume that every plane has unlimited fire resources,
  // but hero plane and boss plane has cool down time so that they cannot fire
  // at every time. An enemy light plane fire with respect to a relatively
  // small probability.
  public static final HashMap<String, Integer> COOL_DOWN_TIME = new HashMap<>();
  static {
    COOL_DOWN_TIME.put("HeroPlane", 15);
    COOL_DOWN_TIME.put("EnemyBoss", 60);
  }

  // Enemy light planes do not have cool down time. They fire with respect to
  // a specific probability.
  public static final HashMap<String, Double> FIRE_PROB = new HashMap<>();
  static {
    FIRE_PROB.put("EnemyLightPlane", 0.01);
  }

  // A very simple implementation to generate a random number given a
  // bernoulli distribution. Not perfect but enough for us.
  public static boolean bernoulli(double p) {
    if (Math.random() < p) return true;
    return false;
  }
}
