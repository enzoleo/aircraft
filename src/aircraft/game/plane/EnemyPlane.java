package aircraft.game.plane;

import aircraft.game.Canvas;
import aircraft.game.Setting;
import aircraft.game.bullet.*;
import aircraft.game.bomb.*;
import aircraft.game.supply.*;

public abstract class EnemyPlane extends Plane {
  // The bonus player can get once take down this plane.
  protected int bonus;
  public EnemyPlane(String img, Canvas canvas, double x, double y,
                    int health, double speed, int bonus) {
    // Call the super class constructor.
    super(img, canvas, x, y, health, speed);

    // Moving direction.
    this.direction.x = (int)(Math.random() + 0.5) * 2 - 1;
    this.bonus = bonus;
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
    } else if (xmax > canvas.getWidth()) {
      location.x = canvas.getWidth() - image.getWidth();
      direction.x = -direction.x;
    }
    if (ymin < 0 || ymax > canvas.getHeight())
      canvas.planes.get("trash").add(this);
  }

  @Override
  public boolean isHit(Object object) {
    double x, y, w, h;
    int camp = Setting.NEUTRAL;
    if (object instanceof HeroBullet) {
      HeroBullet bullet = (HeroBullet)object;
      x = bullet.location.x; y = bullet.location.y;
      w = bullet.image.getWidth(); h = bullet.image.getHeight();
      camp = bullet.camp();
    } else if (object instanceof EnemyNormalBullet) {
      EnemyNormalBullet bullet = (EnemyNormalBullet)object;
      x = bullet.location.x; y = bullet.location.y;
      w = bullet.image.getWidth(); h = bullet.image.getHeight();
      camp = bullet.camp();
    } else if (object instanceof EnemyCannon) {
      EnemyCannon cannon = (EnemyCannon)object;
      x = cannon.location.x; y = cannon.location.y;
      w = cannon.image.getWidth(); h = cannon.image.getHeight();
      camp = cannon.camp();
    } else if (object instanceof Bomb) {
      Bomb bomb = (Bomb)object;
      x = bomb.location.x; y = bomb.location.y;
      w = bomb.image.getWidth(); h = bomb.image.getHeight();
      camp = bomb.camp();
    } else if (object instanceof Supply) {
      Supply supply = (Supply)object;
      x = supply.location.x; y = supply.location.y;
      w = supply.image.getWidth(); h = supply.image.getHeight();
      camp = supply.camp();
    } else return false;
    // Only be effected by hero camps (i.e. hero bullets).
    // In fact, we do not need to check all types of bullets, as only
    // HeroBullet is enough. Please compare this method with duck typing
    // and find the conveniences of it.
    if (camp != Setting.HERO) return false;

    boolean flag1 = // Object (x,y) locates inside the hero plane.
      x > location.x && x < location.x + image.getWidth() &&
      y > location.y && y < location.y + image.getHeight();
    boolean flag2 = // Plane (x,y) locates inside the object.
      location.x > x && location.x < x + w &&
      location.y > y && location.y < y + h;
    return (flag1 || flag2);
  }

  @Override
  public void explode() {
    canvas.planes.get("trash").add(this);
  }

  @Override
  public int camp() {
    return Setting.ENEMY;
  }
}
