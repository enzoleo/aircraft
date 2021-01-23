package aircraft.game;

import java.awt.geom.Point2D;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

// The abstract base class for all flying objects: bullets, planes, bombs,
// supplies... In object-oriented programming, polymorphism is implemented
// via inheritance. Derived classes inherit from this base classes and
// implement abstract methods in different ways.
public abstract class Flying {
  protected BufferedImage image;
  protected Point2D.Double location = new Point2D.Double(); // The current position.
  protected double speed = 0; // The speed of the flying object.

  // Each object should have a specific move policy.
  public abstract void move();

  // The display method: as the name suggests, this method draw the image onto
  // the graphics user interface, so the argument is necessary.
  public void display(Graphics graphics) {
    // Default implementation: show image on the gui.
    graphics.drawImage(image, (int)location.x, (int)location.y, null);
  }
}
