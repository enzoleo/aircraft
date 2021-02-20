package aircraft.game.bullet;

import aircraft.game.plane.Plane;
import aircraft.game.Canvas;
import aircraft.game.Setting;

public class EnemyNormalBullet extends EnemyBullet {
  // Constructor.
  public EnemyNormalBullet(Canvas canvas, double x, double y) {
    super(Setting.IMAGES.get("EnemyNormalBullet"), canvas, x, y,
          Setting.DAMAGE.get("EnemyNormalBullet"),
          Setting.SPEED.get("EnemyNormalBullet"));
  }

  @Override
  public void move() {
    location.y += speed;
  }

  @Override
  public void effect(Plane plane) {
    // Prevent the health point display of hero plane from being
    // a negative number.
    plane.reduceHP(this.damage);
    if (plane.getHP() < 0) plane.clearHP();
  }
}
