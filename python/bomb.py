from point2d import Point2D
import imgloader
import setting

class Bomb:
    def __init__(self, x, y):
        """Initialize the bomb class.

        Keyword arguments:
        x -- the x coordinate of location.
        y -- the y coordinate of location.
        """
        # Read image and check its size.
        self.image = imgloader.load("bomb.png")
        w, h = self.image.get_rect().size
        if w >= setting.width or h >= setting.height:
            raise ValueError("The size of image is invalid")

        self.location = Point2D(x, y)
        self.speed = setting.speed["Bomb"] # The speed of the flying object.

    def display(self, graphics):
        """Display image on the screen.
        """
        # Show image on the screen with respect to the specified location
        # and the image sources. Note that this method is not abstract.
        graphics.blit(self.image, self.location.cartesian())

    def boundary_check(self):
        """Check whether the bomb moves out of window.

        One bomb will be directly moved to trash and deleted in the next frame
        once it moves out of the current game window.
        """
        w, h = self.image.get_rect().size
        if self.location.x < 0 or self.location.x + w > setting.width or \
           self.location.y < 0 or self.location.y + h > setting.height:
            setting.trash.add(self)

    def move(self):
        """Move the bomb to the next location at the next frame, according to
        the current speed and direction.
        """
        self.location.y += self.speed

    def effect(self, plane):
        """A bomb make the plane explode directly.
        """
        if plane.health > 0: plane.health = 0
