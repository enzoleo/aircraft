package aircraft.game.plane;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import aircraft.game.AircraftWar;
import aircraft.game.ImageLoader;

public abstract class Plane {
  // The moving direction of the hero plane. It contains two components which
  // should only take three possible values: 1, 0, -1, as vertical and 
  // horizontal direction flags.
  public Point direction = new Point();
  public BufferedImage image;

  // The current position.
  public Point2D.Double location = new Point2D.Double();
  protected double speed = 0; // The speed of the flying object.

  // The health point. The plane will vanish once its health attains zero.
  // Note that only the generic class Plane has attribute health.
  public int health = 100;

  // Constructor that inherits from base class.
  protected Plane(String img, double x, double y) {
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

  // Check whether the current flying object is displayed inside the window.
  // Usually an object should be restricted so that it will not move outside
  // the boundary, or it should be directly deleted once it moves out of the
  // horizon.
  protected int boundaryCheck() {
    double xmin = location.x; // Left boundary.
    double xmax = location.x + image.getWidth(); // Right boundary.
    double ymin = location.y; // Bottom boundary.W
    double ymax = location.y + image.getHeight(); // Top boundary.

    // NOTE the meaning of argument: only 9 values are valid.
    // -------------------------------------
    // | -4 |           -3            | -2 |
    // -------------------------------------
    // | -1 |     Game Window (0)     |  1 |
    // -------------------------------------
    // |  2 |           -3            |  4 |
    // -------------------------------------
    int xind = 0, yind = 0;
    if (xmin < 0) xind = -1; else if (xmax > AircraftWar.WIDTH)  xind = 1;
    if (ymin < 0) yind = -3; else if (ymax > AircraftWar.HEIGHT) yind = 3;

    return xind + yind;
  }

  // Each object should have a specific move policy.
  public abstract void move();

  // An object should take specific actions once it moves out of bound.
  // Bullets should be deleted, hero plane should be reverted to the valid
  // position, supplies should rebound when hit the left/right border, etc.
  protected abstract void reactOnceInvalid(int indicator);

  public abstract void action();

  protected abstract void fire();

  public abstract boolean isHit(Object object);

  public abstract void hitBy(Object object);
}
