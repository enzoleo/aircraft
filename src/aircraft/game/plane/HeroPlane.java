package aircraft.game.plane;

import java.awt.Point;

import aircraft.game.AircraftWar;
import aircraft.game.ImageLoader;

public class HeroPlane extends Plane {
  // The moving direction of the hero plane. It contains two components which
  // should only take three possible values: 1, 0, -1, as vertical and 
  // horizontal direction flags.
  public Point direction = new Point();

  // Constructor.
  public HeroPlane() {
    // Load the plane from the image directory.
    image = ImageLoader.readImg("aircraft/images/hero_plane.png");
    if (image.getWidth()  >= AircraftWar.WIDTH ||
        image.getHeight() >= AircraftWar.HEIGHT)
      throw new RuntimeException("The size of hero plane image is invalid");

    // The initial position of the hero plane.
    location.x = 150.; location.y = 400.;
    if (boundCheck() != 0) // It must be inside the window.
      throw new RuntimeException(
          "The hero plane is not at a valid location.");
    
    health = 20; // The initial health point.
    speed = 2.0; // The initial speed.
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
