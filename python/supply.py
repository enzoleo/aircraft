from point2d import Point2D
import imgloader
import setting
from setting import AircraftWar

class Supply:
    def __init__(self, x, y):
        """Initialize the supply class.

        Keyword arguments:
        x -- the x coordinate of location.
        y -- the y coordinate of location.
        """
        # Read image and check its size.
        self.image = imgloader.load("supply.gif")
        w, h = self.image.get_rect().size
        if w >= setting.width or h >= setting.height:
            raise ValueError("The size of image is invalid")

        self.location = Point2D(x, y)
        self.speed = setting.speed["Supply"] # The speed of the flying object.
        self.__recovery = setting.recovery["Supply"]

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
        if self.location.x < 0 or self.location.x + w > setting.width or \
           self.location.y < 0 or self.location.y + h > setting.height:
            AircraftWar.trash.add(self)

    def move(self):
        """Move the supply to the next location at the next frame, according to
        the current speed and direction.
        """
        self.location.y += self.speed

    def effect(self, plane):
        plane.health += self.__recovery
