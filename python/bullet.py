from abc import ABC, abstractmethod
from point2d import Point2D
import imgloader
from setting import AircraftWar

class Bullet(ABC):
    def __init__(self, path, x, y, damage, speed):
        """Initialize the bullet.

        Keyword arguments:
        path -- the image path to be specified into image loader.
        x -- the x coordinate of location.
        y -- the y coordinate of location.
        damage -- the initial damage power of the bullet.
        speed -- the initial speed of the bullet.
        """
        # Read image and check its size.
        self.image = imgloader.load(path)
        w, h = self.image.get_rect().size
        if w >= AircraftWar.width or h >= AircraftWar.height:
            raise ValueError("The size of image is invalid")
        
        self.location = Point2D(x, y)
        self.damage = damage # The initial damage power
        self.speed = speed # The initial speed

    def display(self, graphics):
        """Display image on the screen.
        """
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
        """Initialize the bullet.

        Keyword arguments:
        x -- the x coordinate of location.
        y -- the y coordinate of location.
        """
        damage = AircraftWar.damage["HeroBullet"]
        speed = AircraftWar.speed["HeroBullet"]
        super().__init__("hero_bullet.png", x, y, damage, speed)
    
    def move(self):
        """Move the bullet to the next location at the next frame, according to
        the current speed and direction.
        """
        self.location.y -= self.speed

    def effect(self, plane):
        """Reduce the health point of the plane.
        """
        plane.health -= self.damage

class EnemyBullet(Bullet):
    def __init__(self, path, x, y, damage, speed):
        """Initialize the bullet.

        Keyword arguments:
        path -- the image path to be specified into image loader.
        x -- the x coordinate of location.
        y -- the y coordinate of location.
        damage -- the initial damage power of the bullet.
        speed -- the initial speed of the bullet.
        """
        # Exactly follow the super class constructor.
        super().__init__(path, x, y, damage, speed)

class EnemyNormalBullet(EnemyBullet):
    def __init__(self, x, y):
        """Initialize the bullet.

        Keyword arguments:
        x -- the x coordinate of location.
        y -- the y coordinate of location.
        """
        damage = AircraftWar.damage["EnemyNormalBullet"]
        speed = AircraftWar.speed["EnemyNormalBullet"]
        super().__init__("enemy_normal_bullet.png", x, y, damage, speed)

    def move(self):
        """Move the bullet to the next location at the next frame, according to
        the current speed and direction.
        """
        self.location.y += self.speed

    def effect(self, plane):
        """Reduce the health point of the plane.
        """
        plane.health -= self.damage
        # Prevent the health point display of hero plane from being
        # a negative number.
        if plane.health < 0: plane.health = 0

class EnemyCannon(EnemyBullet):
    def __init__(self, x, y, alpha = 0):
        """Initialize the bullet.

        Keyword arguments:
        x -- the x coordinate of location.
        y -- the y coordinate of location.
        """
        damage = AircraftWar.damage["EnemyCannon"]
        speed = AircraftWar.speed["EnemyCannon"]
        super().__init__("enemy_cannon.png", x, y, damage, speed)
        self.__alpha = alpha

    def move(self):
        """Move the bullet to the next location at the next frame, according to
        the current speed and direction.
        """
        self.location.x += self.speed * self.__alpha
        self.location.y += self.speed

    def effect(self, plane):
        """Reduce the health point of the plane.
        """
        plane.health -= self.damage
        # Prevent the health point display of hero plane from being
        # a negative number.
        if plane.health < 0: plane.health = 0