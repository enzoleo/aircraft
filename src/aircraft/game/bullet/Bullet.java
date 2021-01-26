package aircraft.game.bullet;

import aircraft.game.Flying;
import aircraft.game.AircraftWar;

public abstract class Bullet extends Flying {
  // Each kind of bullet must have a damage power.
  // Of course different kind of bullets have different damage powers.
  protected int damage = 1;
  protected Bullet(String img, double x, double y) {
    super(img, x, y);
  }

  protected void vanish() {
    AircraftWar.objects.remove(this);
  }
}
