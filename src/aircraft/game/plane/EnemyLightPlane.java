package aircraft.game.plane;

import aircraft.game.AircraftWar;
import aircraft.game.bullet.EnemyNormalBullet;

public class EnemyLightPlane extends EnemyPlane {
  // Constructor.
  public EnemyLightPlane(double x, double y) {
    // Load the plane from the image directory.
    super("aircraft/images/enemy_light_plane.png", x, y, 20, 1.5);
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
  }

  @Override
  public void fire() {
    if (AircraftWar.bernoulli(0.01)) {
      EnemyNormalBullet enemyBullet = new EnemyNormalBullet(0, 0);
      double offset = (image.getWidth() - enemyBullet.image.getWidth()) / 2;
      enemyBullet.location.x = location.x + offset;
      enemyBullet.location.y = location.y + image.getHeight();
      AircraftWar.newcome.add(enemyBullet);
    }
  }

  @Override
  public void explode() {
    super.explode();
    AircraftWar.score += 10;
  }
}
