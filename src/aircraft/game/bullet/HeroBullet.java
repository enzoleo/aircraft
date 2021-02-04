package aircraft.game.bullet;

import aircraft.game.plane.Plane;
import aircraft.game.Canvas;

public class HeroBullet extends Bullet {
  // Constructor.
  public HeroBullet(Canvas canvas, double x, double y) {
    super("hero_bullet.png", canvas, x, y);
    this.damage = 20;
    this.speed = 3.0;
  }

  @Override
  public void move() {
    location.y -= speed;
  }

  @Override
  public void effect(Plane plane) {
    plane.health -= this.damage;
  }
}
