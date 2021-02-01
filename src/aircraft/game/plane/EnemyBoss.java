package aircraft.game.plane;

import aircraft.game.AircraftWar;
import aircraft.game.bullet.EnemyNormalBullet;

public class EnemyBoss extends EnemyPlane {
  // Constructor.
  public EnemyBoss(double x, double y, int health, double speed) {
    // Load the plane from the image directory.
    super("aircraft/images/enemy_boss.png", x, y, health, speed);
    this.direction.y = 0; // The boss only moves horizontally from our view.
  }

  @Override
  public void move() {
    //location.x += speed * direction.x;
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
    AircraftWar.bossNum--;
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
  public void explode() {
    super.explode();
    AircraftWar.score += 80;
    AircraftWar.bossNum--;
  }
}
