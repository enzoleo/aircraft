package aircraft.game.bullet;

import aircraft.game.plane.Plane;
import aircraft.game.Canvas;
import aircraft.game.Setting;

public class HeroBullet extends Bullet {
  // Constructor.
  public HeroBullet(Canvas canvas, double x, double y) {
    super("hero_bullet.png", canvas, x, y,
          Setting.damage.get("HeroBullet"),
          Setting.speed.get("HeroBullet"));
  }

  @Override
  public void move() {
    location.y -= speed;
  }

  @Override
  public void effect(Plane plane) {
    plane.reduceHP(this.damage);
  }

  // Return the camp of this object.
  @Override
  public int camp() {
    return Setting.HERO;
  }
}
