package aircraft.game.plane;

import aircraft.game.Flying;

public abstract class Plane extends Flying {
  // The health point. The plane will vanish once its health attains zero.
  protected int health = 100;
}
