package aircraft.game.bullet;

import aircraft.game.AircraftWar;
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
  public void effect(Plane plane) {
    plane.health -= this.damage;
  }
}
