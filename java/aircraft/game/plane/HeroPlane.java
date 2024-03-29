package aircraft.game.plane;

import java.awt.Graphics;

import aircraft.game.Canvas;
import aircraft.game.Setting;
import aircraft.game.bullet.*;
import aircraft.game.bomb.*;
import aircraft.game.supply.*;

public class HeroPlane extends Plane {
  // A boolean determining whether to fire at the current status.
  public boolean fireCommand = false;
  // Cool down time to fire. Only when the coolDown variable is zero can
  // the hero plane shoot cannons.
  private int coolDown = 0;
  private int coolDownTime = Setting.COOL_DOWN_TIME.get("HeroPlane");

  // The total score of the current plane.
  private int score = 0;

  // Constructor.
  public HeroPlane(Canvas canvas, double x, double y) {
    // Load the plane from the image directory.
    super(Setting.IMAGES.get("HeroPlane"), canvas, x, y,
          Setting.HEALTH.get("HeroPlane"), Setting.SPEED.get("HeroPlane"));
  }

  // Getter and setter of the score.
  public int getScore() { return score; }
  public void addScore(int bonus) { score += bonus; }

  @Override
  public void display(Graphics graphics) {
    super.display(graphics);
    if (coolDown > 0)
      coolDown = (coolDown + 1) % coolDownTime; // Update cool down time.
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
    if (xmax > canvas.getWidth())
      location.x = canvas.getWidth() - image.getWidth();
    if (ymax > canvas.getHeight())
      location.y = canvas.getHeight() - image.getHeight();
  }

  @Override
  public void fire() {
    if (fireCommand && coolDown == 0) {
      HeroBullet heroBullet = new HeroBullet(canvas, 0, 0);
      double offset = (image.getWidth() - heroBullet.getImage().getWidth()) / 2;
      heroBullet.setLocation( // Set the coordicates of the bullet.
        location.x + offset,
        location.y - heroBullet.getImage().getHeight());
      canvas.objects.get("newcome").add(heroBullet);
      coolDown++;
    }
  }

  @Override
  public boolean isHit(Object object) {
    double x, y, w, h;
    int camp = Setting.NEUTRAL;
    if (object instanceof Bullet) {
      Bullet bullet = (Bullet)object;
      x = bullet.getLocation().x; y = bullet.getLocation().y;
      w = bullet.getImage().getWidth(); h = bullet.getImage().getHeight();
      camp = bullet.camp();
    } else if (object instanceof Bomb) {
      Bomb bomb = (Bomb)object;
      x = bomb.getLocation().x; y = bomb.getLocation().y;
      w = bomb.getImage().getWidth(); h = bomb.getImage().getHeight();
      camp = bomb.camp();
    } else if (object instanceof Supply) {
      Supply supply = (Supply)object;
      x = supply.getLocation().x; y = supply.getLocation().y;
      w = supply.getImage().getWidth(); h = supply.getImage().getHeight();
      camp = supply.camp();
    } else return false;
    // Only be effected by hero camps (i.e. hero bullets).
    // In fact, we do not need to check all types of bullets, Please
    // compare this method with duck typing and find the conveniences
    // of it.
    if (camp == Setting.HERO) return false;

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
    canvas.status = Setting.GAMEOVER;
  }

  @Override
  public int camp() {
    return Setting.HERO;
  }
}
