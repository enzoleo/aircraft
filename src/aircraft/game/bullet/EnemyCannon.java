package aircraft.game.bullet;

import aircraft.game.plane.Plane;

public class EnemyCannon extends EnemyBullet {
  // This member might be a little bit hard to understand.
  // The boss ship can shoot cannon moving horizontally and vertically
  // at the same time. Variable @alpha is used to control the bullet
  // angle! The shoot direction will be along vector (@alpha, 1);
  private double alpha = 0;

  // Constructor.
  public EnemyCannon(double x, double y, double alpha) {
    super("aircraft/images/enemy_cannon.png", x, y, 10, 3.0);
    this.alpha = alpha;
  }

  @Override
  public void move() {
    location.y += speed;
    location.x += speed * alpha;
  }

  @Override
  public void effect(Plane plane) {
    plane.health -= this.damage;
  }
}
