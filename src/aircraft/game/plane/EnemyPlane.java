package aircraft.game.plane;

import aircraft.game.Canvas;
import aircraft.game.bullet.HeroBullet;

public abstract class EnemyPlane extends Plane {
  // Constructor.
  public EnemyPlane(String img, Canvas canvas, double x, double y, int health, double speed) {
    // Call the super class constructor.
    super(img, canvas, x, y, health, speed);

    // Moving direction.
    this.direction.x = (int)(Math.random() + 0.5) * 2 - 1;
  }

  @Override
  public void boundaryCheck() {
    double xmin = location.x; // Left boundary.
    double xmax = location.x + image.getWidth(); // Right boundary.
    double ymin = location.y; // Bottom boundary.
    double ymax = location.y + image.getHeight(); // Top boundary.

    if (xmin < 0) {
      location.x = 0;
      direction.x = -direction.x;
    } else if (xmax > Canvas.WIDTH) {
      location.x = Canvas.WIDTH - image.getWidth();
      direction.x = -direction.x;
    }
    if (ymin < 0 || ymax > Canvas.HEIGHT)
      canvas.trash.add(this);
  }

  @Override
  public boolean isHit(Object object) {
    double x, y, w, h;
    if (object instanceof HeroBullet) {
      HeroBullet bullet = (HeroBullet)object;
      x = bullet.location.x; y = bullet.location.y;
      w = bullet.image.getWidth();
      h = bullet.image.getHeight();
    } else {
      return false;
    }

    boolean flag1 = // Object (x,y) locates inside the hero plane.
      x > location.x && x < location.x + image.getWidth() &&
      y > location.y && y < location.y + image.getHeight();
    boolean flag2 = // Plane (x,y) locates inside the object.
      location.x > x && location.x < x + w &&
      location.y > y && location.y < y + h;
    return (flag1 || flag2);
  }

  @Override
  public void hitBy(Object object) {
    if (object instanceof HeroBullet) {
      HeroBullet bullet = (HeroBullet)object;
      bullet.effect(this);
      canvas.trash.add(object);
    }
  }

  @Override
  public void explode() {
    canvas.trash.add(this);
  }
}
