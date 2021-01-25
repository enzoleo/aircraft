package aircraft.game.plane;

import aircraft.game.Flying;

public abstract class Plane extends Flying {
  // The health point. The plane will vanish once its health attains zero.
  // Note that only the generic class Plane has attribute health.
  protected int health = 100;

  // Constructor that inherits from base class.
  protected Plane(String img, double x, double y) {
    super(img, x, y);
  }
}
