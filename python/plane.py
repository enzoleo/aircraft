from abc import ABC, abstractmethod
from point2d import Point2D
import imgloader
from setting import AircraftWar
from bullet import *

class Plane(ABC):
    def __init__(self, path, x, y, health = 100, speed = 2.0):
        # Read image and check its size.
        self.image = imgloader.load(path)
        w, h = self.image.get_rect().size
        if w >= AircraftWar.width or h >= AircraftWar.height:
            raise ValueError("The size of image is invalid")

        self.location = Point2D(x, y)
        self.health = health # The initial health point
        self.speed = speed # The initial speed
        self.direction = Point2D(0, 0)

    def display(self, graphics):
        graphics.blit(self.image, self.location.cartesian())
        
    @abstractmethod
    def boundary_check(self): pass

    @abstractmethod
    def move(self): pass
    
    @abstractmethod
    def fire(self): pass

class HeroPlane(Plane):
    def __init__(self, x, y):
        super().__init__("hero_plane.png", x, y, 100, 2.0)
        self.__cool_down = 0
        self.fire_command = False
    
    def display(self, graphics):
        super().display(graphics)
        if self.__cool_down > 0: # Update cool down time.
            self.__cool_down = (self.__cool_down + 1) % 15
    
    def boundary_check(self):
        """Check whether the hero plane moves out of window.
        """
        w, h = self.image.get_rect().size
        if self.location.x < 0: self.location.x = 0;
        if self.location.y < 0: self.location.y = 0;
        if self.location.x + w > AircraftWar.width:
            self.location.x = AircraftWar.width - w;
        if self.location.y + h > AircraftWar.height:
            self.location.y = AircraftWar.height - h;
    
    def move(self):
        self.location.x += self.speed * self.direction.x
        self.location.y += self.speed * self.direction.y

    def fire(self):
        if self.fire_command and self.__cool_down == 0:
            hero_bullet = HeroBullet(0, 0)
            w, h = hero_bullet.image.get_rect().size # The image size of bullet.
            width, _ = self.image.get_rect().size
            hero_bullet.location.x = self.location.x + (width - w ) * 0.5
            hero_bullet.location.y = self.location.y - h
            AircraftWar.newcome.append(hero_bullet)
            self.__cool_down += 1
