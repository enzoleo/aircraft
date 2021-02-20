package aircraft.game.bullet;

import aircraft.game.Canvas;
import aircraft.game.Setting;

public class EnemyCannon extends EnemyBullet {
  // This member might be a little bit hard to understand.
  // The boss ship can shoot cannon moving horizontally and vertically
  // at the same time. Variable @alpha is used to control the bullet
  // angle! The shoot direction will be along vector (@alpha, 1);
  private double alpha = 0;

  // Constructor.
  public EnemyCannon(Canvas canvas, double x, double y, double alpha) {
    super(Setting.IMAGES.get("EnemyCannon"), canvas, x, y,
          Setting.DAMAGE.get("EnemyCannon"),
          Setting.SPEED.get("EnemyCannon"));
    this.alpha = alpha;
  }

  @Override
  public void move() {
    location.y += speed;
    location.x += speed * alpha;
  }
}
