package aircraft.game.plane;

import java.awt.Graphics;

import aircraft.game.AircraftWar;
import aircraft.game.bullet.HeroBullet;

public abstract class EnemyPlane extends Plane {
  // Constructor.
  public EnemyPlane(String img, double x, double y, int health, double speed) {
    // Load the plane from the image directory.
    super(img, x, y);
    
    this.health = health; // The initial health point.
    this.speed = speed; // The initial speed.

    // Moving direction.
    this.direction.x = (int)(Math.random() + 0.5) * 2 - 1;
  }

  @Override
  public void display(Graphics graphics) {
    if (this.health > 0)
      super.display(graphics);
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
    } else if (xmax > AircraftWar.WIDTH) {
      location.x = AircraftWar.WIDTH - image.getWidth();
      direction.x = -direction.x;
    }
    if (ymin < 0 || ymax > AircraftWar.HEIGHT)
      AircraftWar.trash.add(this);
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
      AircraftWar.trash.add(object);
    }
  }

  @Override
  public void explode() {
    AircraftWar.trash.add(this);
  }
}
