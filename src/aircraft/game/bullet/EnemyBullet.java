package aircraft.game.bullet;

import aircraft.game.Canvas;
import aircraft.game.plane.Plane;

public abstract class EnemyBullet extends Bullet {
  // Constructor.
  public EnemyBullet(String img, Canvas canvas,
                     double x, double y, int damage, double speed) {
    super(img, canvas, x, y, damage, speed);
  }

  @Override
  public void effect(Plane plane) {
    // Prevent the health point display of hero plane from being
    // a negative number.
    plane.reduceHP(this.damage);
    if (plane.getHP() < 0) plane.clearHP();
  }
}
