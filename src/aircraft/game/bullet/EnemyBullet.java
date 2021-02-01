package aircraft.game.bullet;

public abstract class EnemyBullet extends Bullet {
  // Constructor.
  public EnemyBullet(String img, double x, double y, int damage, double speed) {
    super(img, x, y);
    this.damage = damage;
    this.speed = speed;
  }
}
