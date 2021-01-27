package aircraft.game.plane;

import aircraft.game.AircraftWar;
import aircraft.game.bullet.EnemyNormalBullet;

public class EnemyLightPlane extends Plane {
  // Constructor.
  public EnemyLightPlane(double x, double y, int health, double speed) {
    // Load the plane from the image directory.
    super("aircraft/images/enemy_light_plane.png", x, y);
    
    this.health = health; // The initial health point.
    this.speed = speed; // The initial speed.

    // Moving direction.
    this.direction.x = (int)(Math.random() + 0.5) * 2 - 1;
    this.direction.y = 1;
  }

  @Override
  public void move() {
    location.x += speed * direction.x;
    location.y += speed * direction.y;

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
    
    int indicator = boundCheck();
    if (indicator != 0) reactOnceInvalid(indicator);
  }

  @Override
  protected void reactOnceInvalid(int indicator) {
    AircraftWar.trash.add(this);
  }

  @Override
  public void fire() {
    double p = 0.5;
    if (AircraftWar.bernoulli(p)) {
      EnemyNormalBullet enemyBullet = new EnemyNormalBullet(0, 0, 5, 3.0);
      double offset = (image.getWidth() - enemyBullet.image.getWidth()) / 2;
      enemyBullet.location.x = location.x + offset;
      enemyBullet.location.y = location.y + image.getHeight();
      AircraftWar.objects.add(enemyBullet);
    }
  }
}
