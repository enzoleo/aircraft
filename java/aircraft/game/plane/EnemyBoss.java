package aircraft.game.plane;

import java.awt.Graphics;

import aircraft.game.Canvas;
import aircraft.game.Setting;
import aircraft.game.bullet.EnemyCannon;

public class EnemyBoss extends EnemyPlane {
  // Cool down time to fire. Only when the coolDown variable is zero can
  // the boss ship shoot cannons.
  private int coolDown = 0;
  private int coolDownTime = Setting.COOL_DOWN_TIME.get("EnemyBoss");

  // Constructor.
  public EnemyBoss(Canvas canvas, double x, double y) {
    // Load the plane from the image directory.
    super(Setting.IMAGES.get("EnemyBoss"), canvas, x, y,
          Setting.HEALTH.get("EnemyBoss"), Setting.SPEED.get("EnemyBoss"),
          Setting.BONUS.get("EnemyBoss"));
    this.direction.y = 0; // The boss only moves horizontally from our view.
  }

  @Override
  public void move() {
    location.x += speed * direction.x;
  }

  @Override
  public void display(Graphics graphics) {
    super.display(graphics);
    if (coolDown > 0)
      coolDown = (coolDown + 1) % coolDownTime; // Update cool down time.
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
    if (ymin < 0 || ymax > canvas.getHeight()) {
      canvas.planes.get("trash").add(this);
      canvas.bossNum--;  
    }
  }

  @Override
  public void fire() {
    if (coolDown == 0) {
      EnemyCannon enemyCannon = new EnemyCannon(canvas, 0, 0, 0);
      double offset = (image.getWidth() - enemyCannon.getImage().getWidth()) / 2;
      enemyCannon.setLocation(location.x + offset, location.y + image.getHeight());

      // Shoot three bullets at a time.
      canvas.objects.get("newcome").add(enemyCannon);
      canvas.objects.get("newcome").add(new EnemyCannon( // Right direction.
          canvas, enemyCannon.getLocation().x, enemyCannon.getLocation().y, 0.2));
      canvas.objects.get("newcome").add(new EnemyCannon( // Left direction.
          canvas, enemyCannon.getLocation().x, enemyCannon.getLocation().y, -0.2));
      coolDown++;
    }
  }

  @Override
  public void explode() {
    super.explode();
    canvas.getHeroPlane().addScore(80);
    canvas.bossNum--;
  }
}
