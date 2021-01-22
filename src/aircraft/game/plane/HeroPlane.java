package aircraft.game.plane;

import java.awt.Graphics;
import aircraft.game.ImageLoader;

public class HeroPlane extends Plane {
  // Constructor.
  public HeroPlane() {
    image = ImageLoader.readImg("aircraft/images/hero_plane.png");
    x = 150; y = 400; // The initial position of the hero plane.
    health = 20;
  }

  // The display method: as the name suggests, this method draw the image onto
  // the graphics user interface, so the argument is necessary.
  public void display(Graphics graphics) {
    graphics.drawImage(image, x, y, null);
  }

  // Implement the abstract method of the base class.
  public void move() {

  }
}
