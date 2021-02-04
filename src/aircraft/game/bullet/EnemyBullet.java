package aircraft.game.bullet;

import aircraft.game.Canvas;

public abstract class EnemyBullet extends Bullet {
  // Constructor.
  public EnemyBullet(String img, Canvas canvas, double x, double y, int damage, double speed) {
    super(img, canvas, x, y);
    this.damage = damage;
    this.speed = speed;
  }
}
