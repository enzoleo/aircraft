from point2d import Point2D
import imgloader
from setting import AircraftWar

class Supply:
    def __init__(self, x, y):
        # Read image and check its size.
        self.image = imgloader.load("supply.gif")
        w, h = self.image.get_rect().size
        if w >= AircraftWar.width or h >= AircraftWar.height:
            raise ValueError("The size of image is invalid")

        self.location = Point2D(x, y)
        self.speed = 1.5 # The speed of the flying object.
        self.__recovery = 20

    def display(self, graphics):
        # Show image on the screen with respect to the specified location
        # and the image sources. Note that this method is not abstract.
        graphics.blit(self.image, self.location.cartesian())

    def boundary_check(self):
        """Check whether the supply moves out of window.

        One supply will be directly moved to trash and deleted in the next frame
        once it moves out of the current game window.
        """
        w, h = self.image.get_rect().size
        if self.location.x < 0 or self.location.x + w > AircraftWar.width or \
           self.location.y < 0 or self.location.y + h > AircraftWar.height:
            AircraftWar.trash.add(self)

    def move(self):
        self.location.y += self.speed

    def effect(self, plane):
        plane.health += self.__recovery
