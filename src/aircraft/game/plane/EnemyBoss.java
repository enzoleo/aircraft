package aircraft.game.plane;

import java.awt.Graphics;

import aircraft.game.AircraftWar;
import aircraft.game.bullet.EnemyCannon;

public class EnemyBoss extends EnemyPlane {
  // Cool down time to fire. Only when the coolDown variable is zero can
  // the boss ship shoot cannons.
  private int coolDown = 0;

  // Constructor.
  public EnemyBoss(double x, double y) {
    // Load the plane from the image directory.
    super("enemy_boss.png", x, y, 800, 1.5);
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
      coolDown = (coolDown + 1) % 60; // Update cool down time.
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
    if (ymin < 0 || ymax > AircraftWar.HEIGHT) {
      AircraftWar.trash.add(this);
      AircraftWar.bossNum--;  
    }
  }

  @Override
  public void fire() {
    if (coolDown == 0) {
      EnemyCannon enemyCannon = new EnemyCannon(0, 0, 0);
      double offset = (image.getWidth() - enemyCannon.image.getWidth()) / 2;
      enemyCannon.location.x = location.x + offset;
      enemyCannon.location.y = location.y + image.getHeight();

      // Shoot three bullets at a time.
      AircraftWar.newcome.add(enemyCannon);
      AircraftWar.newcome.add(new EnemyCannon(offset, image.getHeight(), 0.2));
      AircraftWar.newcome.add(new EnemyCannon(offset, image.getHeight(), -0.2));
      coolDown++;
    }
  }

  @Override
  public void explode() {
    super.explode();
    AircraftWar.score += 80;
    AircraftWar.bossNum--;
  }
}
