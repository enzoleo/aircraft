from abc import ABC, abstractmethod
from point2d import Point2D
import imgloader
from aircraft import AircraftWar

class Plane(ABC):
    def __init__(self, path, x, y, health = 100, speed = 2.0):
        self.image = imgloader.load(path)
        self.location = Point2D(x, y)
        self.health = health # The initial health point
        self.speed = speed # The initial speed
        self._direction = Point2D(1, -1)

    def display(self, graphics):
        graphics.blit(self.image, self.location.cartesian())

    @abstractmethod
    def move(): pass
    
    @abstractmethod
    def fire(): pass

class HeroPlane(Plane):
    def __init__(self, x, y):
        super().__init__("hero_plane.png", x, y)
        self._cool_down = 0
        self.fire_command = False
    
    def display(self, graphics):
        super().display(graphics)
        if self._cool_down > 0: # Update cool down time.
            self._cool_down = (self._cool_down + 1) % 15
    
    def move(self):
        self.location.x += self.speed * self._direction.x
        self.location.y += self.speed * self._direction.y

    def fire():
        pass
