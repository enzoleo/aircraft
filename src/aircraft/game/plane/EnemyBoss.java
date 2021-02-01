package aircraft.game.plane;

import aircraft.game.AircraftWar;
import aircraft.game.bullet.EnemyNormalBullet;
import aircraft.game.bullet.HeroBullet;

public class EnemyBoss extends EnemyPlane {
  // Constructor.
  public EnemyBoss(double x, double y, int health, double speed) {
    // Load the plane from the image directory.
    super("aircraft/images/enemy_boss.png", x, y, health, speed);
    this.direction.y = 0; // The boss only moves horizontally from our view.
  }

  @Override
  public void move() {
    location.x += speed * direction.x;
    double bound = AircraftWar.WIDTH;
    double ratio = 0.2; // Must be inside the interval [0, 0.5].

    // Rebound when hitting the left/right boundary.
    if (location.x < ratio * bound && direction.x == -1) {
      // Calculate adaptive propobility.
      double p = 1 - location.x / (ratio * bound);
      if (AircraftWar.bernoulli(p)) direction.x = 1;
    } else if (location.x > (1 - ratio) * bound && direction.x == 1) {
      // Calculate adaptive propobility.
      double p = 1 + location.x / (ratio * bound) - 1 / ratio;
      if (AircraftWar.bernoulli(p)) this.direction.x = -1;
    }
    
    int indicator = boundaryCheck();
    if (indicator != 0) reactOnceInvalid(indicator);
  }

  @Override
  protected void reactOnceInvalid(int indicator) {
    AircraftWar.trash.add(this);
    AircraftWar.score += 80;
  }

  @Override
  public void fire() {
    EnemyNormalBullet enemyBullet = new EnemyNormalBullet(0, 0, 5, 4.0);
    double offset = (image.getWidth() - enemyBullet.image.getWidth()) / 2;
    enemyBullet.location.x = location.x + offset;
    enemyBullet.location.y = location.y + image.getHeight();
    AircraftWar.newcome.add(enemyBullet);
  }

  @Override
  public void action() {
    double p = 0.015;
    if (AircraftWar.bernoulli(p))
      this.fire();
  }

  @Override
  public boolean isHit(Object object) {
    double x, y, w, h;
    if (object instanceof HeroBullet) {
      HeroBullet bullet = (HeroBullet)object;
      x = bullet.location.x; y = bullet.location.y;
      w = bullet.image.getWidth();
      h = bullet.image.getHeight();
    } else {
      return false;
    }

    boolean flag1 = // Object (x,y) locates inside the hero plane.
      x > location.x && x < location.x + image.getWidth() &&
      y > location.y && y < location.y + image.getHeight();
    boolean flag2 = // Plane (x,y) locates inside the object.
      location.x > x && location.x < x + w &&
      location.y > y && location.y < y + h;
    return (flag1 || flag2);
  }

  @Override
  public void hitBy(Object object) {
    if (object instanceof HeroBullet) {
      HeroBullet bullet = (HeroBullet)object;
      bullet.effect(this);
      AircraftWar.trash.add(object);
    }
  }
}
