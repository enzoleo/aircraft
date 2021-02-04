package aircraft.game;

import java.awt.geom.Point2D;
import java.util.HashMap;

public class Setting {
  // Default window size.
  public static final int width = 400;
  public static final int height = 654;
  
  // Default game status.
  public static final boolean RUNNING = true;
  public static final boolean GAMEOVER = false;

  // The initial position of the hero plane. Feel free to edit it.
  public static final Point2D.Double heroInitPos = new Point2D.Double(150, 500);

  // Probabilities when generating characters at the beginning of every
  // frame. Feel free to modify them.
  public static final HashMap<String, Double> prob = new HashMap<>();
  static {
    prob.put("EnemyPlane", 0.01);
    prob.put("Bomb", 0.001);
    prob.put("Supply", 0.005);
  }

  // The sub probability. When an enemy plane is going to be generated,
  // it will 'mutate' to a boss with respect to a specific probability.
  public static final HashMap<String, HashMap<String, Double> > subProb =
    new HashMap<>();
  static {
    HashMap<String, Double> item = new HashMap<>();
    item.put("EnemyBoss", 0.1);
    subProb.put("EnemyPlane", item);
  }
  
  // Initial health point of each kind of planes.
  public static final HashMap<String, Integer> health = new HashMap<>();
  static {
    health.put("HeroPlane", 100);
    health.put("EnemyLightPlane", 20);
    health.put("EnemyBoss", 800);
  }

  // Initial damage power of each kind of bullets.
  public static final HashMap<String, Integer> damage = new HashMap<>();
  static {
    damage.put("HeroBullet", 20);
    damage.put("EnemyNormalBullet", 5);
    damage.put("EnemyCannon", 8);
  }

  // The speed of each kind of characters in this game. 
  public static final HashMap<String, Double> speed = new HashMap<>();
  static {
    speed.put("HeroPlane", 2.0);
    speed.put("EnemyLightPlane", 1.5);
    speed.put("EnemyBoss", 1.5);
    speed.put("HeroBullet", 3.0);
    speed.put("EnemyNormalBullet", 4.0);
    speed.put("EnemyCannon", 3.0);
    speed.put("Bomb", 1.0);
    speed.put("Supply", 1.5); 
  }
  
    // The recovery power of the supply. Without supply, you cannot live long.
  // Try to catch supplies as many as possible.
  public static final HashMap<String, Integer> recovery = new HashMap<>();
  static {
    recovery.put("Supply", 20);
  }

  // When you take down an enemy, you will get bonus.
  public static final HashMap<String, Integer> bonus = new HashMap<>();
  static {
    bonus.put("EnemyLightPlane", 10);
    bonus.put("EnemyBoss", 80);
  }
  
  // Planes can fire. We assume that every plane has unlimited fire resources,
  // but hero plane and boss plane has cool down time so that they cannot fire
  // at every time. An enemy light plane fire with respect to a relatively
  // small probability.
  public static final HashMap<String, Integer> coolDownTime = new HashMap<>();
  static {
    coolDownTime.put("HeroPlane", 15);
    coolDownTime.put("EnemyBoss", 60);
  }

  // Enemy light planes do not have cool down time. They fire with respect to
  // a specific probability
  public static final HashMap<String, Double> fireProb = new HashMap<>();
  static {
    fireProb.put("EnemyLightPlane", 0.01);
  }

  // A very simple implementation to generate a random number given a
  // bernoulli distribution. Not perfect but enough for us.
  public static boolean bernoulli(double p) {
    if (Math.random() < p) return true;
    return false;
  }
}
