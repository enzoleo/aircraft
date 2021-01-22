package aircraft.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageLoader {
  // Private constructor.
  private ImageLoader() { }
  public static BufferedImage readImg(String path) {
    BufferedImage img = null;
    try {
      img = ImageIO.read(
        ImageLoader.class.getClassLoader().getResource(path));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return img;
  }
}
