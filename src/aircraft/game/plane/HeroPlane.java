package aircraft.game.plane;

import java.awt.Graphics;

import aircraft.game.Canvas;
import aircraft.game.bullet.*;
import aircraft.game.bomb.*;
import aircraft.game.supply.*;

public class HeroPlane extends Plane {
  // A boolean determining whether to fire at the current status.
  public boolean fireCommand = false;
  // Cool down time to fire. Only when the coolDown variable is zero can
  // the hero plane shoot cannons.
  private int coolDown = 0;

  // Constructor.
  public HeroPlane(Canvas canvas, double x, double y) {
    // Load the plane from the image directory.
    super("hero_plane.png", canvas, x, y, 100, 2.0);
  }

  @Override
  public void display(Graphics graphics) {
    super.display(graphics);
    if (coolDown > 0)
      coolDown = (coolDown + 1) % 15; // Update cool down time.
  }

  // Implement the abstract method of the base class. The move method is
  // called everytime, but the plane can be stationary as long as the
  // direction is equal to a zero point, e.g. when the user inputs nothing.
  @Override
  public void move() {
    location.x += speed * direction.x;
    location.y += speed * direction.y;
  }

  @Override
  public void boundaryCheck() {
    double xmin = location.x; // Left boundary.
    double xmax = location.x + image.getWidth(); // Right boundary.
    double ymin = location.y; // Bottom boundary.
    double ymax = location.y + image.getHeight(); // Top boundary.

    if (xmin < 0) location.x = 0;
    if (ymin < 0) location.y = 0;
    if (xmax > Canvas.WIDTH)
      location.x = Canvas.WIDTH - image.getWidth();
    if (ymax > Canvas.HEIGHT)
      location.y = Canvas.HEIGHT - image.getHeight();
  }

  @Override
  public void fire() {
    if (fireCommand && coolDown == 0) {
      HeroBullet heroBullet = new HeroBullet(canvas, 0, 0);
      double offset = (image.getWidth() - heroBullet.image.getWidth()) / 2;
      heroBullet.location.x = location.x + offset;
      heroBullet.location.y = location.y - heroBullet.image.getHeight();
      canvas.newcome.add(heroBullet);
      coolDown++;
    }
  }

  @Override
  public boolean isHit(Object object) {
    double x, y, w, h;
    if (object instanceof EnemyNormalBullet) {
      EnemyNormalBullet bullet = (EnemyNormalBullet)object;
      x = bullet.location.x; y = bullet.location.y;
      w = bullet.image.getWidth(); h = bullet.image.getHeight();
    } else if (object instanceof EnemyCannon) {
      EnemyCannon cannon = (EnemyCannon)object;
      x = cannon.location.x; y = cannon.location.y;
      w = cannon.image.getWidth(); h = cannon.image.getHeight();
    } else if (object instanceof Bomb) {
      Bomb bomb = (Bomb)object;
      x = bomb.location.x; y = bomb.location.y;
      w = bomb.image.getWidth(); h = bomb.image.getHeight();
    } else if (object instanceof Supply) {
      Supply supply = (Supply)object;
      x = supply.location.x; y = supply.location.y;
      w = supply.image.getWidth(); h = supply.image.getHeight();
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
    if (object instanceof EnemyNormalBullet) {
      ((EnemyNormalBullet)object).effect(this);
      canvas.trash.add(object);
    } else if (object instanceof EnemyCannon) {
      ((EnemyCannon)object).effect(this);
      canvas.trash.add(object);
    } else if (object instanceof Bomb) {
      ((Bomb)object).effect(this);
      canvas.trash.add(object);
    } else if (object instanceof Supply) {
      ((Supply)object).effect(this);
      canvas.trash.add(object);
    }
  }

  @Override
  public void explode() {
    canvas.status = Canvas.GAMEOVER;
  }
}
