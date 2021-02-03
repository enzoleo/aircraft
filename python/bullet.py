from abc import ABC, abstractmethod
from point2d import Point2D
import imgloader
from setting import AircraftWar

class Bullet(ABC):
    def __init__(self, path, x, y, damage = 1, speed = 2.0):
        # Read image and check its size.
        self.image = imgloader.load(path)
        w, h = self.image.get_rect().size
        if w >= AircraftWar.width or h >= AircraftWar.height:
            raise ValueError("The size of image is invalid")
        
        self.location = Point2D(x, y)
        self.damage = damage # The initial damage power
        self.speed = speed # The initial speed

    def display(self, graphics):
        graphics.blit(self.image, self.location.cartesian())

    def boundary_check(self):
        """Check whether the bullet moves out of window.

        For all kinds of bullets, one bullet will be directly moved to
        trash and deleted in the next frame once it moves out of the
        current game window.
        """
        w, h = self.image.get_rect().size
        if self.location.x < 0 or self.location.x + w > AircraftWar.width or \
           self.location.y < 0 or self.location.y + h > AircraftWar.height:
            AircraftWar.trash.add(self)

    @abstractmethod
    def move(self): pass

    @abstractmethod
    def effect(self, plane): pass

class HeroBullet(Bullet):
    def __init__(self, x, y):
        super().__init__("hero_bullet.png", x, y, 20, 3.0)
    
    def move(self):
        self.location.y -= self.speed

    def effect(self, plane):
        plane.health -= self.damage

class EnemyBullet(Bullet):
    def __init__(self, path, x, y, damage, speed):
        # Exactly follow the super class constructor.
        super().__init__(path, x, y, damage, speed)

class EnemyNormalBullet(EnemyBullet):
    def __init__(self, x, y):
        super().__init__("enemy_normal_bullet.png", x, y, 5, 4.0)

    def move(self):
        self.location.y += self.speed

    def effect(self, plane):
        plane.health -= self.damage
        # Prevent the health point display of hero plane from being
        # a negative number.
        if plane.health < 0: plane.health = 0

class EnemyCannon(EnemyBullet):
    def __init__(self, x, y, alpha = 0):
        super().__init__("enemy_cannon.png", x, y, 8, 3.0)
        self.__alpha = alpha

    def move(self):
        self.location.x += self.speed * self.__alpha
        self.location.y += self.speed

    def effect(self, plane):
        plane.health -= self.damage
        # Prevent the health point display of hero plane from being
        # a negative number.
        if plane.health < 0: plane.health = 0