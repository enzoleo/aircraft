package aircraft.game;
import java.awt.image.BufferedImage;

// The abstract base class for all flying objects: bullets, planes, bombs,
// supplies... In object-oriented programming, polymorphism is implemented
// via inheritance. Derived classes inherit from this base classes and
// implement abstract methods in different ways.
public abstract class Flying {
  protected BufferedImage image;
  protected int x, y; // The current position.

  // Each object should have a specific move policy.
  public abstract void move();
}
