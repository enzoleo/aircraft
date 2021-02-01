package aircraft.game.bullet;

import aircraft.game.plane.Plane;

public class HeroBullet extends Bullet {
  // Constructor.
  public HeroBullet(double x, double y) {
    super("aircraft/images/hero_bullet.png", x, y);
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
