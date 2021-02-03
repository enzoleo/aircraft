from abc import ABC, abstractmethod
from point2d import Point2D
import scipy.stats
import imgloader
from setting import AircraftWar
from bullet import *

class Plane(ABC):
    def __init__(self, path, x, y, health, speed):
        """Initialize the plane.

        Keyword arguments:
        path -- the image path to be specified into image loader.
        x -- the x coordinate of location.
        y -- the y coordinate of location.
        health -- the initial health point of the plane.
        speed -- the initial speed of the plane.
        """
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
        """Display image on the screen.
        """
        # Show image on the screen with respect to the specified location
        # and the image sources. Note that this method is not abstract.
        graphics.blit(self.image, self.location.cartesian())
        
    @abstractmethod
    def boundary_check(self): pass

    @abstractmethod
    def move(self): pass
    
    @abstractmethod
    def fire(self): pass

    @abstractmethod
    def is_hit(self, obj): pass

    @abstractmethod
    def hit_by(self, obj): pass

    @abstractmethod
    def explode(self): pass

class HeroPlane(Plane):
    def __init__(self, x, y):
        """Initialize the hero plane.

        Keyword arguments:
        x -- the x coordinate of location.
        y -- the y coordinate of location.
        """
        health = AircraftWar.health["HeroPlane"]
        speed = AircraftWar.speed["HeroPlane"]
        super().__init__("hero_plane.png", x, y, health, speed)
        self.__cool_down = 0
        self.fire_command = False
    
    def display(self, graphics):
        """Display image on the screen.
        """
        super().display(graphics)
        if self.__cool_down > 0: # Update cool down time.
            self.__cool_down = (self.__cool_down + 1) % \
                AircraftWar.cool_down_time["HeroPlane"]
    
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
        """Move the plane to the next location at the next frame, according to
        the current speed and direction.
        """
        self.location.x += self.speed * self.direction.x
        self.location.y += self.speed * self.direction.y

    def fire(self):
        """Fire some bullets to your enemies.
        """
        if self.fire_command and self.__cool_down == 0:
            # Initialize the bullet first to obtain its image size.
            hero_bullet = HeroBullet(0, 0)
            w, h = hero_bullet.image.get_rect().size # The image size of bullet.
            width, _ = self.image.get_rect().size

            # Update the real location of the bullet.
            hero_bullet.location.x = self.location.x + (width - w) * 0.5
            hero_bullet.location.y = self.location.y - h
            AircraftWar.newcome.add(hero_bullet)

            # Once the plane fires, it should be pushed into cool down stage,
            # which means that only a specific number of frames have been drawn
            # can this plane fire again.
            self.__cool_down += 1

    def is_hit(self, obj):
        """Check whether the current plane is hit by other objects. Specifically,
        the hero plane can be hit by any other characters except planes.
        """
        # The hero plane will be effected by any kinds of objects excepts planes.
        if isinstance(obj, Plane): return False
        x, y = obj.location.cartesian()
        w, h = obj.image.get_rect().size
        width, height = self.image.get_rect().size

        flag1 = ( # Object (x,y) locates inside the hero plane.
            x > self.location.x and x < self.location.x + width and
            y > self.location.y and y < self.location.y + height)
        flag2 = ( # Plane (x,y) locates inside the object.
            self.location.x > x and self.location.x < x + w and
            self.location.y > y and self.location.y < y + h)
        return (flag1 or flag2)

    def hit_by(self, obj):
        """Once the hero plane is hit by some objects, take specific actions,
        increasing or decreasing health point, etc.
        """
        # The hero plane will be effected by any kinds of objects excepts planes.
        if isinstance(obj, Plane): return False
        obj.effect(self)
        AircraftWar.trash.add(obj)

    def explode(self):
        """The plane explode when health point attains zero.
        """
        AircraftWar.status = False

class EnemyPlane(Plane):
    def __init__(self, path, x, y, health, speed):
        """Initialize the enemy plane.

        Keyword arguments:
        x -- the x coordinate of location.
        y -- the y coordinate of location.
        """
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
            AircraftWar.trash.add(self)
    
    @abstractmethod
    def move(self): pass

    @abstractmethod
    def fire(self): pass

    def is_hit(self, obj):
        """Check whether the current plane is hit by other objects. Specifically,
        enemy planes will be only effected by hero bullets. All other characters
        like supplies, bombs, etc. have no effect on enemy planes.
        """
        if not isinstance(obj, HeroBullet): return False
        x, y = obj.location.cartesian()
        w, h = obj.image.get_rect().size
        width, height = self.image.get_rect().size

        flag1 = ( # Object (x,y) locates inside the hero plane.
            x > self.location.x and x < self.location.x + width and
            y > self.location.y and y < self.location.y + height)
        flag2 = ( # Plane (x,y) locates inside the object.
            self.location.x > x and self.location.x < x + w and
            self.location.y > y and self.location.y < y + h)
        return (flag1 or flag2)

    def hit_by(self, obj):
        """Once the hero plane is hit by some objects, take specific actions,
        increasing or decreasing health point, etc.
        """
        # Enemy planes will be only effected by hero bullets.
        if not isinstance(obj, HeroBullet): return False
        obj.effect(self)
        AircraftWar.trash.add(obj)

    def explode(self):
        """The plane explode when health point attains zero.
        """
        AircraftWar.trash.add(self)

class EnemyLightPlane(EnemyPlane):
    def __init__(self, x, y):
        """Initialize the enemy light plane.

        Keyword arguments:
        x -- the x coordinate of location.
        y -- the y coordinate of location.
        """
        health = AircraftWar.health["EnemyLightPlane"]
        speed = AircraftWar.speed["EnemyLightPlane"]
        super().__init__("enemy_light_plane.png", x, y, health, speed)
        self.direction.y = 1

    def move(self):
        """Move the plane to the next location at the next frame, according to
        the current speed and direction.
        """
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
        """Fire some bullets to your enemies.
        """
        if scipy.stats.bernoulli.rvs(0.01):
            # Initialize the bullet first to obtain its image size.
            bullet = EnemyNormalBullet(0, 0)
            w, _ = bullet.image.get_rect().size
            width, height = self.image.get_rect().size

             # Update the real location of the bullet.
            bullet.location.x = self.location.x + (width - w) * 0.5
            bullet.location.y = self.location.y + height
            AircraftWar.newcome.add(bullet)

    def explode(self):
        """The plane explode when health point attains zero.
        """
        super().explode()
        AircraftWar.score += AircraftWar.bonus["EnemyLightPlane"]

class EnemyBoss(EnemyPlane):
    def __init__(self, x, y):
        """Initialize the enemy boss plane.

        Keyword arguments:
        x -- the x coordinate of location.
        y -- the y coordinate of location.
        """
        health = AircraftWar.health["EnemyBoss"]
        speed = AircraftWar.speed["EnemyBoss"]
        super().__init__("enemy_boss.png", x, y, health, speed)
        self.__cool_down = 0
        self.direction.y = 0

    def display(self, graphics):
        """Display image on the screen.
        """
        super().display(graphics)
        if self.__cool_down > 0: # Update cool down time.
            self.__cool_down = (self.__cool_down + 1) % \
                AircraftWar.cool_down_time["EnemyBoss"]
    
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
            AircraftWar.trash.add(self)
            AircraftWar.boss_num -= 1
    
    def move(self):
        """Move the plane to the next location at the next frame, according to
        the current speed and direction.
        """
        self.location.x += self.speed * self.direction.x

    def fire(self):
        """Fire some bullets to your enemies.
        """
        if self.__cool_down == 0:
            # Initialize the cannon first to obtain its image size.
            cannon = EnemyCannon(0, 0)
            w, _ = cannon.image.get_rect().size # The image size of cannon.
            width, height = self.image.get_rect().size

            # Update the real location of the cannon.
            cannon.location.x = self.location.x + (width - w) * 0.5
            cannon.location.y = self.location.y + height

            # Shoot three bullets at a time.
            AircraftWar.newcome.add(cannon)
            AircraftWar.newcome.add( # Right direction.
                EnemyCannon(cannon.location.x, cannon.location.y, 0.2))
            AircraftWar.newcome.add( # Left direction.
                EnemyCannon(cannon.location.x, cannon.location.y, -0.2))

            # Once the boss fires, it should be pushed into cool down stage,
            # which means that only a specific number of frames have been drawn
            # can this boss fire again.
            self.__cool_down += 1

    def explode(self):
        """The plane explode when health point attains zero.
        """
        super().explode()
        AircraftWar.score += AircraftWar.bonus["EnemyBoss"]
        AircraftWar.boss_num -= 1
