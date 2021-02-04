package aircraft.game.plane;

import aircraft.game.Canvas;
import aircraft.game.Setting;
import aircraft.game.bullet.EnemyNormalBullet;

public class EnemyLightPlane extends EnemyPlane {
  // Constructor.
  public EnemyLightPlane(Canvas canvas, double x, double y) {
    // Load the plane from the image directory.
    super("enemy_light_plane.png", canvas, x, y,
          Setting.health.get("EnemyLightPlane"),
          Setting.speed.get("EnemyLightPlane"),
          Setting.bonus.get("EnemyLightPlane"));
    this.direction.y = 1;
  }

  @Override
  public void move() {
    location.x += speed * direction.x;
    location.y += speed * direction.y;

    double bound = canvas.getWidth();
    double ratio = 0.2; // Must be inside the interval [0, 0.5].

    // Rebound when hitting the left/right boundary.
    if (location.x < ratio * bound && direction.x == -1) {
      // Calculate adaptive propobility.
      double p = 1 - location.x / (ratio * bound);
      if (Setting.bernoulli(p)) direction.x = 1;
    } else if (location.x > (1 - ratio) * bound && direction.x == 1) {
      // Calculate adaptive propobility.
      double p = 1 + location.x / (ratio * bound) - 1 / ratio;
      if (Setting.bernoulli(p)) this.direction.x = -1;
    }
  }

  @Override
  public void fire() {
    if (Setting.bernoulli(Setting.fireProb.get("EnemyLightPlane"))) {
      EnemyNormalBullet enemyBullet = new EnemyNormalBullet(canvas, 0, 0);
      double offset = (image.getWidth() - enemyBullet.image.getWidth()) / 2;
      enemyBullet.location.x = location.x + offset;
      enemyBullet.location.y = location.y + image.getHeight();
      canvas.newcome.add(enemyBullet);
    }
  }

  @Override
  public void explode() {
    super.explode();
    canvas.getHeroPlane().addScore(10);
  }
}
