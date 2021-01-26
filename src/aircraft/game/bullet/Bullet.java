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

  // When the bullet moves out of the boundary, it "vanishes". In other
  // words, it will be removed from the object set and the game will not
  // displayed it in the window.
  protected void vanish() {
    AircraftWar.trash.add(this);
  }
}
