package aircraft.game.plane;

import aircraft.game.AircraftWar;

public class HeroPlane extends Plane {
  // Constructor.
  public HeroPlane(double x, double y, int health, double speed) {
    // Load the plane from the image directory.
    super("aircraft/images/hero_plane.png", x, y);
    
    this.health = health; // The initial health point.
    this.speed = speed; // The initial speed.
  }

  // Implement the abstract method of the base class. The move method is
  // called everytime, but the plane can be stationary as long as the
  // direction is equal to a zero point, e.g. when the user inputs nothing.
  @Override
  public void move() {
    location.x += speed * direction.x;
    location.y += speed * direction.y;
    
    int indicator = boundCheck();
    if (indicator != 0) reactOnceInvalid(indicator);
  }

  @Override
  protected void reactOnceInvalid(int indicator) {
    switch (indicator) {
      case  1:
        // Reset the location to the right boundary. 
        location.x = AircraftWar.WIDTH - image.getWidth(); break;
      case -2:
        // Reset the location to the top right corner.
        location.x = AircraftWar.WIDTH - image.getWidth();
        location.y = 0; break;
      case  4:
        // Reset the location to the bottom right corner.
        location.x = AircraftWar.WIDTH - image.getWidth();
        location.y = AircraftWar.HEIGHT - image.getHeight(); break;
      case -1:
        // Reset the location to the left boundary.
        location.x = 0; break;
      case -4:
        // Reset the location to the top left corner.
        location.x = 0; location.y = 0; break;
      case  2:
        // Reset the location to the bottom left corner.
        location.x = 0;
        location.y = AircraftWar.HEIGHT - image.getHeight(); break;
      case -3:
        // Reset the location to the top boundary.
        location.y = 0; break;
      case  3:
        // Reset the location to the bottom boundary.
        location.y = AircraftWar.HEIGHT - image.getHeight(); break;
      default:
        throw new RuntimeException("Invalid indicator is specified.");
    }
  }
}
