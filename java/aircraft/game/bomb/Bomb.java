package aircraft.game.bomb;

import java.awt.geom.Point2D;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import aircraft.game.Canvas;
import aircraft.game.ImageLoader;
import aircraft.game.Setting;
import aircraft.game.plane.Plane;

public class Bomb {
  protected final Canvas canvas;
  public BufferedImage image;

  // The current position.
  public Point2D.Double location = new Point2D.Double();
  protected double speed = 1; // The speed of the flying object.

  public Bomb(Canvas canvas, double x, double y) {
    // Load the bomb from the image directory.
    this.canvas = canvas;
    image = ImageLoader.readImg(Setting.images.get("Bomb"));
    if (image.getWidth()  >= canvas.getWidth() ||
        image.getHeight() >= canvas.getHeight())
      throw new RuntimeException("The size of image is invalid");
    
    location.x = x; location.y = y;
  }

  // The display method: as the name suggests, this method draw the image
  // onto the graphics user interface, so the argument is necessary.
  public void display(Graphics graphics) {
    // Default implementation: show image on the gui.
    graphics.drawImage(image, (int)location.x, (int)location.y, null);
  }

  // Check whether the bomb is displayed inside the window.
  public void boundaryCheck() {
    double xmin = location.x; // Left boundary.
    double xmax = location.x + image.getWidth(); // Right boundary.
    double ymin = location.y; // Bottom boundary.
    double ymax = location.y + image.getHeight(); // Top boundary.

    if (xmin < 0 || xmax > canvas.getWidth() ||
        ymin < 0 || ymax > canvas.getHeight())
      canvas.objects.get("trash").add(this);
  }

  // Each object should have a specific move policy.
  public void move() {
    location.y += speed;
  }

  public void effect(Plane plane) {
    if (plane.getHP() > 0) plane.clearHP();
  }

  // Return the camp of this object.
  public int camp() {
    return Setting.NEUTRAL;
  }
}
