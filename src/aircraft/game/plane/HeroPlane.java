package aircraft.game.plane;

import aircraft.game.AircraftWar;
import aircraft.game.bullet.*;
import aircraft.game.bomb.*;
import aircraft.game.supply.*;

public class HeroPlane extends Plane {
  // A boolean determining whether to fire at the current status.
  public boolean fireCommand = false;

  // Constructor.
  public HeroPlane(double x, double y, int health, double speed) {
    // Load the plane from the image directory.
    super("aircraft/images/hero_plane.png", x, y);
    
    this.health = health; // The initial health point.
    this.speed = speed; // The initial speed.
  }

  // Implement the abstract method of the base class. The move method is
  // called everytime, but the plane can be stationary as long as the
  // direction is equal to a zero point, e.g. when the user inputs nothing.
  @Override
  public void move() {
    location.x += speed * direction.x;
    location.y += speed * direction.y;
    
    int indicator = boundaryCheck();
    if (indicator != 0) reactOnceInvalid(indicator);
  }

  @Override
  protected void reactOnceInvalid(int indicator) {
    switch (indicator) {
      case  1:
        // Reset the location to the right boundary. 
        location.x = AircraftWar.WIDTH - image.getWidth(); break;
      case -2:
        // Reset the location to the top right corner.
        location.x = AircraftWar.WIDTH - image.getWidth();
        location.y = 0; break;
      case  4:
        // Reset the location to the bottom right corner.
        location.x = AircraftWar.WIDTH - image.getWidth();
        location.y = AircraftWar.HEIGHT - image.getHeight(); break;
      case -1:
        // Reset the location to the left boundary.
        location.x = 0; break;
      case -4:
        // Reset the location to the top left corner.
        location.x = 0; location.y = 0; break;
      case  2:
        // Reset the location to the bottom left corner.
        location.x = 0;
        location.y = AircraftWar.HEIGHT - image.getHeight(); break;
      case -3:
        // Reset the location to the top boundary.
        location.y = 0; break;
      case  3:
        // Reset the location to the bottom boundary.
        location.y = AircraftWar.HEIGHT - image.getHeight(); break;
      default:
        throw new RuntimeException("Invalid indicator is specified.");
    }
  }

  @Override
  public void fire() {
    HeroBullet heroBullet = new HeroBullet(0, 0, 10, 3.0);
    double offset = (image.getWidth() - heroBullet.image.getWidth()) / 2;
    heroBullet.location.x = location.x + offset;
    heroBullet.location.y = location.y - heroBullet.image.getHeight();
    AircraftWar.newcome.add(heroBullet);
  }

  @Override
  public void action() {
    if (fireCommand) this.fire();
  }

  @Override
  protected boolean isHit(Object object) {
    double x, y, w, h;
    if (object instanceof EnemyNormalBullet) {
      EnemyNormalBullet bullet = (EnemyNormalBullet)object;
      x = bullet.location.x; y = bullet.location.y;
      w = bullet.image.getWidth(); h = bullet.image.getHeight();
    } else if (object instanceof Bomb) {
      Bomb bullet = (Bomb)object;
      x = bullet.location.x; y = bullet.location.y;
      w = bullet.image.getWidth(); h = bullet.image.getHeight();
    } else if (object instanceof Supply) {
      Supply bullet = (Supply)object;
      x = bullet.location.x; y = bullet.location.y;
      w = bullet.image.getWidth(); h = bullet.image.getHeight();
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
    if (!isHit(object)) return;
    if (object instanceof EnemyNormalBullet) {
      ((EnemyNormalBullet)object).effect(this);
      AircraftWar.trash.add(object);
    } else if (object instanceof Bomb) {
      ((Bomb)object).effect(this);
      AircraftWar.trash.add(object);
    } else if (object instanceof Supply) {
      ((Supply)object).effect(this);
      AircraftWar.trash.add(object);
    }
  }
}
