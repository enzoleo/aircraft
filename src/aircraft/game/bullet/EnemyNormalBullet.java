package aircraft.game.bullet;

import aircraft.game.AircraftWar;

public class EnemyNormalBullet extends Bullet {
  // Constructor.
  public EnemyNormalBullet(double x, double y, int damage, double speed) {
    super("aircraft/images/enemy_normal_bullet.png", x, y);
    this.damage = damage;
    this.speed = speed;
  }

  @Override
  public void move() {
    location.y += speed;

    int indicator = boundCheck();
    if (indicator != 0) reactOnceInvalid(indicator);
  }

  // When the bullet moves out of the boundary, it "vanishes". In other
  // words, it will be removed from the object set and the game will not
  // displayed it in the window.
  @Override
  protected void reactOnceInvalid(int indicator) {
    AircraftWar.trash.add(this);
  }

  @Override
  public void action() {
  }
}
