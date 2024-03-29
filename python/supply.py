from point2d import Point2D
import imgloader
import setting

class Supply:
    def __init__(self, canvas, x, y):
        """Initialize the supply class.

        Keyword arguments:
        canvas -- the canvas for gui.
        x -- the x coordinate of location.
        y -- the y coordinate of location.
        """
        # Read image and check its size.
        self.canvas = canvas
        self.image = imgloader.load(setting.IMAGES["Supply"])
        w, h = self.image.get_rect().size
        if w >= self.canvas.width or h >= self.canvas.height:
            raise ValueError("The size of image is invalid")

        self.location = Point2D(x, y)
        self.speed = setting.SPEED["Supply"] # The speed of the flying object.
        self._recovery = setting.RECOVERY["Supply"]

    def display(self, graphics):
        """Display image on the screen.
        """
        # Show image on the screen with respect to the specified location
        # and the image sources. Note that this method is not abstract.
        graphics.blit(self.image, self.location.cartesian())

    def boundary_check(self):
        """Check whether the supply moves out of window.

        One supply will be directly moved to trash and deleted in the next frame
        once it moves out of the current game window.
        """
        w, h = self.image.get_rect().size
        if self.location.x < 0 or self.location.x + w > self.canvas.width or \
           self.location.y < 0 or self.location.y + h > self.canvas.height:
            self.canvas.objects["trash"].add(self)

    def move(self):
        """Move the supply to the next location at the next frame, according to
        the current speed and direction.
        """
        self.location.y += self.speed

    def effect(self, plane):
        plane.health += self._recovery

    def camp(self):
        """Return the camp of this object.
        """
        return setting.NEUTRAL
