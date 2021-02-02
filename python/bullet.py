from abc import ABC, abstractmethod
from point2d import Point2D
import imgloader
from aircraft import AircraftWar

class Bullet(ABC):
    def __init__(self, path, x, y, damage = 1, speed = 2.0):
        # Read image and check its size.
        self.image = imgloader.load(path)
        h, w = self.image.get_rect().size
        if w >= AircraftWar.width or h >= AircraftWar.height:
            raise ValueError("The size of image is invalid")
        
        self.location = Point2D(x, y)
        self.damage = damage # The initial damage power
        self.speed = speed # The initial speed

    def display(self, graphics):
        graphics.blit(self.image, self.location.cartesian())

    def boundary_check(self):
        pass

    @abstractmethod
    def move(self): pass

class HeroBullet(Bullet):
    def __init__(self, x, y):
        super().__init__("hero_plane.png", x, y, 20, 3.0)
    
    def move(self):
        self.location.y -= self.speed * self._direction.y
