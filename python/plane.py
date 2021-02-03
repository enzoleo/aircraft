from abc import ABC, abstractmethod
from point2d import Point2D
import scipy.stats
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
        # Show image on the screen with respect to the specified location
        # and the image sources. Note that this method is not abstract.
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
        if self.location.x < 0: self.location.x = 0
        if self.location.y < 0: self.location.y = 0
        if self.location.x + w > AircraftWar.width:
            self.location.x = AircraftWar.width - w
        if self.location.y + h > AircraftWar.height:
            self.location.y = AircraftWar.height - h
    
    def move(self):
        self.location.x += self.speed * self.direction.x
        self.location.y += self.speed * self.direction.y

    def fire(self):
        if self.fire_command and self.__cool_down == 0:
            # Initialize the bullet first to obtain its image size.
            hero_bullet = HeroBullet(0, 0)
            w, h = hero_bullet.image.get_rect().size # The image size of bullet.
            width, _ = self.image.get_rect().size

            # Update the real location of the bullet.
            hero_bullet.location.x = self.location.x + (width - w ) * 0.5
            hero_bullet.location.y = self.location.y - h
            AircraftWar.newcome.append(hero_bullet)

            # Once the plane fires, it should be pushed into cool down stage,
            # which means that only a specific number of frames have been drawn
            # can this plane fire again.
            self.__cool_down += 1

class EnemyPlane(Plane):
    def __init__(self, path, x, y, health = 20, speed = 1.5):
        # Call the super class constructor.
        super().__init__(path, x, y, health, speed)
        # A random integer in {-1, 1}. Only two choices are valid!
        self.direction.x = scipy.stats.randint.rvs(0, 2) * 2 - 1

    def boundary_check(self):
        """Check whether the enemy plane moves out of window.

        The enemy plane acts differently from our hero plane. If an enemy plane
        reaches the left or right boundary, make it rebound. If it reaches the
        top (of course it's impossible...) or bottom boundary, delete it.
        """
        w, h = self.image.get_rect().size
        if self.location.x < 0:
            self.location.x = 0
            self.direction.x = -self.direction.x
        elif self.location.x + w > AircraftWar.width:
            self.location.x = AircraftWar.width - w
            self.direction.x = -self.direction.x
        if self.location.y < 0 or self.location.y > AircraftWar.height - h:
            AircraftWar.trash.append(self)
    
    @abstractmethod
    def move(self): pass

    @abstractmethod
    def fire(self): pass

class EnemyLightPlane(EnemyPlane):
    def __init__(self, x, y):
        super().__init__("enemy_light_plane.png", x, y, 20, 1.5)
        self.direction.y = 1

    def move(self):
        self.location.x += self.speed * self.direction.x
        self.location.y += self.speed * self.direction.y
        bound = AircraftWar.width
        ratio = 0.2 # Must be inside the interval [0, 0.5].

        # Rebound when hitting the left/right boundary.
        if self.location.x < ratio * bound and self.direction.x == -1:
            # Calculate adaptive propobility.
            p = 1 - self.location.x / (ratio * bound)
            if scipy.stats.bernoulli.rvs(p):
                self.direction.x = 1
        elif self.location.x > (1 - ratio) * bound and self.direction.x == 1:
            # Calculate adaptive propobility.
            p = 1 + self.location.x / (ratio * bound) - 1 / ratio
            if scipy.stats.bernoulli.rvs(p):
                self.direction.x = -1
    
    def fire(self):
        if scipy.stats.bernoulli.rvs(0.01):
            # Initialize the bullet first to obtain its image size.
            bullet = EnemyNormalBullet(0, 0)
            w, _ = bullet.image.get_rect().size
            width, height = self.image.get_rect().size

             # Update the real location of the bullet.
            bullet.location.x = self.location.x + (width - w) * 0.5
            bullet.location.y = self.location.y + height
            AircraftWar.newcome.append(bullet)

class EnemyBoss(EnemyPlane):
    def __init__(self, x, y):
        super().__init__("enemy_boss.png", x, y, 800, 1.5)
        self.__cool_down = 0
        self.direction.y = 0

    def display(self, graphics):
        super().display(graphics)
        if self.__cool_down > 0: # Update cool down time.
            self.__cool_down = (self.__cool_down + 1) % 60
    
    def boundary_check(self):
        """Check whether the enemy boss moves out of window.

        This method is very SIMILAR to the super class method. However, pay
        attention to the last line. When the boss plane moves out of the
        bottom boundary (well, you know it's impossible because it only moves
        horizontally), the boss count should be decreased.
        """
        w, h = self.image.get_rect().size
        if self.location.x < 0:
            self.location.x = 0
            self.direction.x = -self.direction.x
        elif self.location.x + w > AircraftWar.width:
            self.location.x = AircraftWar.width - w
            self.direction.x = -self.direction.x
        if self.location.y < 0 or self.location.y + h > AircraftWar.height:
            AircraftWar.trash.append(self)
            AircraftWar.boss_num -= 1
    
    def move(self):
        self.location.x += self.speed * self.direction.x

    def fire(self):
        if self.__cool_down == 0:
            # Initialize the cannon first to obtain its image size.
            cannon = EnemyCannon(0, 0)
            w, _ = cannon.image.get_rect().size # The image size of cannon.
            width, height = self.image.get_rect().size

            # Update the real location of the cannon.
            cannon.location.x = self.location.x + (width - w) * 0.5
            cannon.location.y = self.location.y + height

            # Shoot three bullets at a time.
            AircraftWar.newcome.append(cannon)
            AircraftWar.newcome.append( # Right direction.
                EnemyCannon(cannon.location.x, cannon.location.y, 0.2))
            AircraftWar.newcome.append( # Left direction.
                EnemyCannon(cannon.location.x, cannon.location.y, -0.2))

            # Once the boss fires, it should be pushed into cool down stage,
            # which means that only a specific number of frames have been drawn
            # can this boss fire again.
            self.__cool_down += 1
