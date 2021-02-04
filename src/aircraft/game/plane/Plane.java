package aircraft.game.plane;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import aircraft.game.Canvas;
import aircraft.game.ImageLoader;

public abstract class Plane {
  // The moving direction of the hero plane. It contains two components which
  // should only take three possible values: 1, 0, -1, as vertical and 
  // horizontal direction flags.
  public Point direction = new Point();
  public BufferedImage image;
  protected final Canvas canvas;

  // The current position.
  public Point2D.Double location = new Point2D.Double();
  protected double speed; // The speed of the flying object.

  // The health point. The plane will vanish once its health attains zero.
  // Note that only the generic class Plane has attribute health.
  public int health;

  // Constructor that inherits from base class.
  protected Plane(String img, Canvas canvas, double x, double y, int health, double speed) {
    // Load the plane from the image directory.
    this.canvas = canvas;
    image = ImageLoader.readImg(img);
    if (image.getWidth()  >= Canvas.WIDTH ||
        image.getHeight() >= Canvas.HEIGHT)
      throw new RuntimeException("The size of image is invalid");
    
    location.x = x; location.y = y;
    this.health = health; // The initial health point.
    this.speed = speed; // The initial speed.
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
  public abstract void boundaryCheck();

  // Each object should have a specific move policy.
  public abstract void move();

  // Any plane can fire, e.g. shoot bullets.
  public abstract void fire();

  // Check whether a plane is hit by an object. Different types of planes
  // react to different types of objects.
  public abstract boolean isHit(Object object);

  // Take specific actions once hit by an object.
  public abstract void hitBy(Object object);

  // When the health point of the plane attains zero, it automatically
  // explodes. It is only called when health point attains zero.
  public abstract void explode();
}
