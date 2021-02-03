package aircraft.game.bullet;

import aircraft.game.plane.Plane;

public class EnemyNormalBullet extends EnemyBullet {
  // Constructor.
  public EnemyNormalBullet(double x, double y) {
    super("enemy_normal_bullet.png", x, y, 5, 4.0);
  }

  @Override
  public void move() {
    location.y += speed;
  }

  @Override
  public void effect(Plane plane) {
    // Prevent the health point display of hero plane from being
    // a negative number.
    plane.health -= this.damage;
    if (plane.health < 0) plane.health = 0;
  }
}
