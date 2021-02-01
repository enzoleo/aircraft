package aircraft.game.bullet;

import aircraft.game.plane.Plane;

public class HeroBullet extends Bullet {
  // Constructor.
  public HeroBullet(double x, double y, int damage, double speed) {
    super("aircraft/images/hero_bullet.png", x, y);
    this.damage = damage;
    this.speed = speed;
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
