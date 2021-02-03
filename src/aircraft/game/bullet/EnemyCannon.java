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
    super("enemy_cannon.png", x, y, 8, 3.0);
    this.alpha = alpha;
  }

  @Override
  public void move() {
    location.y += speed;
    location.x += speed * alpha;
  }

  @Override
  public void effect(Plane plane) {
    // Prevent the health point display of hero plane from being
    // a negative number.
    plane.health -= this.damage;
    if (plane.health < 0) plane.health = 0;
  }
}
