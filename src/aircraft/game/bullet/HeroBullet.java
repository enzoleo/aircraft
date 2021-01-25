package aircraft.game.bullet;

public class HeroBullet extends Bullet {
  // Constructor.
  public HeroBullet(double x, double y, int damage, double speed) {
    super("aircraft/images/hero_bullet.png", x, y);
    this.direction.x = 0; this.direction.y = -1; // Moving direction.

    this.damage = damage;
    this.speed = speed;
  }

  @Override
  public void move() {
    location.x += speed * direction.x;
    location.y += speed * direction.y;
  }

  @Override
  protected void reactOnceInvalid(int indicator) {

  }
  
}
