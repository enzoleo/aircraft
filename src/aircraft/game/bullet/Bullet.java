package aircraft.game.bullet;

import java.awt.geom.Point2D;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import aircraft.game.AircraftWar;
import aircraft.game.ImageLoader;
import aircraft.game.plane.Plane;

public abstract class Bullet {
  public BufferedImage image;

  // The current position.
  public Point2D.Double location = new Point2D.Double();
  protected double speed = 0; // The speed of the flying object.

  // Each kind of bullet must have a damage power.
  // Of course different kind of bullets have different damage powers.
  protected int damage = 1;
  protected Bullet(String img, double x, double y) {
    // Load the plane from the image directory.
    image = ImageLoader.readImg(img);
    if (image.getWidth()  >= AircraftWar.WIDTH ||
        image.getHeight() >= AircraftWar.HEIGHT)
      throw new RuntimeException("The size of image is invalid");
    
    location.x = x; location.y = y;
  }

  // The display method: as the name suggests, this method draw the image
  // onto the graphics user interface, so the argument is necessary.
  public void display(Graphics graphics) {
    // Default implementation: show image on the gui.
    graphics.drawImage(image, (int)location.x, (int)location.y, null);
  }

  // Check whether the current bullet is displayed inside the window.
  public void boundaryCheck() {
    double xmin = location.x; // Left boundary.
    double xmax = location.x + image.getWidth(); // Right boundary.
    double ymin = location.y; // Bottom boundary.W
    double ymax = location.y + image.getHeight(); // Top boundary.

    if (xmin < 0 || xmax > AircraftWar.WIDTH ||
        ymin < 0 || ymax > AircraftWar.HEIGHT)
      AircraftWar.trash.add(this);
  }

  // Each object should have a specific move policy.
  public abstract void move();

  public abstract void effect(Plane plane);
}
