package aircraft.game.plane;

import java.awt.Point;

import aircraft.game.ImageLoader;

public class HeroPlane extends Plane {
  // The moving direction of the hero plane. It contains two components which
  // should only take three possible values: 1, 0, -1, as vertical and 
  // horizontal direction flags.
  public Point direction = new Point();

  // Constructor.
  public HeroPlane() {
    image = ImageLoader.readImg("aircraft/images/hero_plane.png");
    location.x = 150.; location.y = 400.; // The initial position of the hero plane.
    health = 20;
    speed = 2.0; // The initial speed.
  }

  // Implement the abstract method of the base class. The move method is called
  // everytime, but the plane can be stationary as long as the direction point is
  // equal to a zero point, e.g. when the user inputs nothing.
  @Override
  public void move() {
    location.x += speed * direction.x;
    location.y += speed * direction.y;
  }
}
