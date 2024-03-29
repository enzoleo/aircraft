package aircraft.game.plane;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import aircraft.game.Canvas;
import aircraft.game.ImageLoader;
import aircraft.game.bullet.*;
import aircraft.game.bomb.*;
import aircraft.game.supply.*;

public abstract class Plane {
  // The moving direction of the hero plane. It contains two components which
  // should only take three possible values: 1, 0, -1, as vertical and 
  // horizontal direction flags.
  public Point direction = new Point();
  protected BufferedImage image;
  protected final Canvas canvas;

  // The current position.
  protected Point2D.Double location = new Point2D.Double();
  protected double speed; // The speed of the flying object.

  // The health point. The plane will vanish once its health attains zero.
  // Note that only the generic class Plane has attribute health.
  protected int health;

  // Getters/setters of location and image.
  public BufferedImage getImage() { return image; }
  public Point2D.Double getLocation() { return location; }
  public void setLocation(double x, double y) {
    location.x = x; location.y = y;
  }

  // Constructor that inherits from base class.
  protected Plane(String img, Canvas canvas, double x, double y,
                  int health, double speed) {
    // Load the plane from the image directory.
    this.canvas = canvas;
    image = ImageLoader.readImg(img);
    if (image.getWidth()  >= canvas.getWidth() ||
        image.getHeight() >= canvas.getHeight())
      throw new RuntimeException("The size of image is invalid");
    
    location.x = x; location.y = y;
    this.health = health; // The initial health point.
    this.speed = speed; // The initial speed.
  }

  // Getter and setter of health point.
  public int getHP() { return health; }
  public void clearHP() { this.health = 0; } // Directly set to zero.
  public void reduceHP(int damage) { this.health -= damage; }
  public void recover(int recovery) { this.health += recovery; }

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
  // Note that this method is not abstract!
  public void hitBy(Object object) {
    if (object instanceof Bullet) {
      ((Bullet)object).effect(this);
      canvas.objects.get("trash").add(object);
    } else if (object instanceof Bomb) {
      ((Bomb)object).effect(this);
      canvas.objects.get("trash").add(object);
    } else if (object instanceof Supply) {
      ((Supply)object).effect(this);
      canvas.objects.get("trash").add(object);
    }
  }

  // When the health point of the plane attains zero, it automatically
  // explodes. It is only called when health point attains zero.
  public abstract void explode();

  // Return the camp of this plane.
  public abstract int camp();
}
