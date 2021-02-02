package aircraft.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.nio.file.*;
import javax.imageio.ImageIO;

public class ImageLoader {
  // Private constructor.
  private ImageLoader() { }
  public static BufferedImage readImg(String path) {
    BufferedImage img = null;
    String dir = AircraftWar.class
        .getProtectionDomain().getCodeSource()
        .getLocation().getPath();
    String base = Paths.get(dir).getParent() + "/images/";
    
    // Read images and catch exceptions.
    try {
      img = ImageIO.read(new File(base + path));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return img;
  }
}
