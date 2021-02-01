package aircraft.game.bullet;

import aircraft.game.plane.Plane;

public class EnemyNormalBullet extends Bullet {
  // Constructor.
  public EnemyNormalBullet(double x, double y, int damage, double speed) {
    super("aircraft/images/enemy_normal_bullet.png", x, y);
    this.damage = damage;
    this.speed = speed;
  }

  @Override
  public void move() {
    location.y += speed;
  }

  @Override
  public void effect(Plane plane) {
    plane.health -= this.damage;
  }
}
