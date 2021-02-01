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
    location.x += speed * direction.x;
  }

  @Override
  public void boundaryCheck() {
    super.boundaryCheck();
    AircraftWar.bossNum--;
  }

  @Override
  public void fire() {
    if (AircraftWar.bernoulli(0.015)) {
      EnemyNormalBullet enemyBullet = new EnemyNormalBullet(0, 0, 5, 4.0);
      double offset = (image.getWidth() - enemyBullet.image.getWidth()) / 2;
      enemyBullet.location.x = location.x + offset;
      enemyBullet.location.y = location.y + image.getHeight();
      AircraftWar.newcome.add(enemyBullet);
    }
  }

  @Override
  public void explode() {
    super.explode();
    AircraftWar.score += 80;
    AircraftWar.bossNum--;
  }
}
