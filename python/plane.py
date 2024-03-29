from abc import ABC, abstractmethod
from point2d import Point2D
import scipy.stats
import imgloader
import setting
from bullet import *

class Plane(ABC):
    def __init__(self, path, canvas, x, y, health, speed):
        """Initialize the plane.

        Keyword arguments:
        path -- the image path to be specified into image loader.
        canvas -- the canvas for gui.
        x -- the x coordinate of location.
        y -- the y coordinate of location.
        health -- the initial health point of the plane.
        speed -- the initial speed of the plane.
        """
        # Read image and check its size.
        self.canvas = canvas
        self.image = imgloader.load(path)
        w, h = self.image.get_rect().size
        if w >= self.canvas.width or h >= self.canvas.height:
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

    def hit_by(self, obj):
        """Once the hero plane is hit by some objects, take specific actions,
        increasing or decreasing health point, etc.
        """
        # The hero plane will be effected by any kinds of objects excepts planes.
        obj.effect(self)
        self.canvas.objects["trash"].add(obj)

    @abstractmethod
    def explode(self): pass

    @abstractmethod
    def camp(self): pass

class HeroPlane(Plane):
    def __init__(self, canvas, x, y):
        """Initialize the hero plane.

        Keyword arguments:
        canvas -- the canvas for gui.
        x -- the x coordinate of location.
        y -- the y coordinate of location.
        """
        health = setting.HEALTH["HeroPlane"]
        speed = setting.SPEED["HeroPlane"]
        super().__init__(setting.IMAGES["HeroPlane"], canvas, x, y, health, speed)
        self._cool_down = 0
        self._fire_command = False
        self._score = 0
        self._cool_down_time = setting.COOL_DOWN_TIME["HeroPlane"]
    
    @property
    def score(self): return self._score

    @property
    def fire_command(self): return self._fire_command
    @fire_command.setter
    def fire_command(self, value): self._fire_command = value
    
    def add_score(self, bonus):
        """Add bonus to the total score of the hero.
        """
        self._score += bonus
    
    def display(self, graphics):
        """Display image on the screen.
        """
        super().display(graphics)
        if self._cool_down > 0: # Update cool down time.
            self._cool_down = (self._cool_down + 1) % self._cool_down_time
    
    def boundary_check(self):
        """Check whether the hero plane moves out of window.
        """
        w, h = self.image.get_rect().size
        if self.location.x < 0: self.location.x = 0
        if self.location.y < 0: self.location.y = 0
        if self.location.x + w > self.canvas.width:
            self.location.x = self.canvas.width - w
        if self.location.y + h > self.canvas.height:
            self.location.y = self.canvas.height - h
    
    def move(self):
        """Move the plane to the next location at the next frame, according to
        the current speed and direction.
        """
        self.location.x += self.speed * self.direction.x
        self.location.y += self.speed * self.direction.y

    def fire(self):
        """Fire some bullets to your enemies.
        """
        if self._fire_command and self._cool_down == 0:
            # Initialize the bullet first to obtain its image size.
            hero_bullet = HeroBullet(self.canvas, 0, 0)
            w, h = hero_bullet.image.get_rect().size # The image size of bullet.
            width, _ = self.image.get_rect().size

            # Update the real location of the bullet.
            hero_bullet.location.x = self.location.x + (width - w) * 0.5
            hero_bullet.location.y = self.location.y - h
            self.canvas.objects["newcome"].add(hero_bullet)

            # Once the plane fires, it should be pushed into cool down stage,
            # which means that only a specific number of frames have been drawn
            # can this plane fire again.
            self._cool_down += 1

    def is_hit(self, obj):
        """Check whether the current plane is hit by other objects. Specifically,
        the hero plane can be hit by any other characters except planes.
        """
        # The hero plane will be effected by any kinds of objects excepts planes.
        if obj.camp() == setting.HERO: return False
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

    def explode(self):
        """The plane explode when health point attains zero.
        """
        self.canvas.status = False

    def camp(self):
        """Return the camp of this plane.
        """
        return setting.HERO

class EnemyPlane(Plane):
    def __init__(self, path, canvas, x, y, health, speed, bonus):
        """Initialize the enemy plane.

        Keyword arguments:
        path -- the image path to be specified into image loader.
        canvas -- the canvas for gui.
        x -- the x coordinate of location.
        y -- the y coordinate of location.
        health -- the initial health point of the plane.
        speed -- the initial speed of the plane.
        bonus -- the bonus player can get once take down this plane.
        """
        # Call the super class constructor.
        super().__init__(path, canvas, x, y, health, speed)
        # A random integer in {-1, 1}. Only two choices are valid!
        self.direction.x = scipy.stats.randint.rvs(0, 2) * 2 - 1
        self.bonus = bonus

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
        elif self.location.x + w > self.canvas.width:
            self.location.x = self.canvas.width - w
            self.direction.x = -self.direction.x
        if self.location.y < 0 or self.location.y > self.canvas.height - h:
            self.canvas.planes["trash"].add(self)
    
    @abstractmethod
    def move(self): pass

    @abstractmethod
    def fire(self): pass

    def is_hit(self, obj):
        """Check whether the current plane is hit by other objects. Specifically,
        enemy planes will be only effected by hero bullets. All other characters
        like supplies, bombs, etc. have no effect on enemy planes.
        """
        if obj.camp() != setting.HERO: return False
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

    def explode(self):
        """The plane explode when health point attains zero.
        """
        self.canvas.planes["trash"].add(self)

    def camp(self):
        """Return the camp of this plane.
        """
        return setting.ENEMY

class EnemyLightPlane(EnemyPlane):
    def __init__(self, canvas, x, y):
        """Initialize the enemy light plane.

        Keyword arguments:
        x -- the x coordinate of location.
        y -- the y coordinate of location.
        """
        health = setting.HEALTH["EnemyLightPlane"]
        speed = setting.SPEED["EnemyLightPlane"]
        super().__init__(setting.IMAGES["EnemyLightPlane"], canvas, x, y,
                         health, speed, setting.BONUS["EnemyLightPlane"])
        self.direction.y = 1

    def move(self):
        """Move the plane to the next location at the next frame, according to
        the current speed and direction.
        """
        self.location.x += self.speed * self.direction.x
        self.location.y += self.speed * self.direction.y
        bound = self.canvas.width
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
        if scipy.stats.bernoulli.rvs(setting.FIRE_PROB["EnemyLightPlane"]):
            # Initialize the bullet first to obtain its image size.
            bullet = EnemyNormalBullet(self.canvas, 0, 0)
            w, _ = bullet.image.get_rect().size
            width, height = self.image.get_rect().size

             # Update the real location of the bullet.
            bullet.location.x = self.location.x + (width - w) * 0.5
            bullet.location.y = self.location.y + height
            self.canvas.objects["newcome"].add(bullet)

    def explode(self):
        """The plane explode when health point attains zero.
        """
        super().explode()
        self.canvas.hero.add_score(self.bonus)

class EnemyBoss(EnemyPlane):
    def __init__(self, canvas, x, y):
        """Initialize the enemy boss plane.

        Keyword arguments:
        canvas -- the canvas for gui.
        x -- the x coordinate of location.
        y -- the y coordinate of location.
        """
        health = setting.HEALTH["EnemyBoss"]
        speed = setting.SPEED["EnemyBoss"]
        super().__init__(setting.IMAGES["EnemyBoss"], canvas, x, y, health, speed,
                         setting.BONUS["EnemyBoss"])
        self._cool_down = 0
        self._cool_down_time = setting.COOL_DOWN_TIME["EnemyBoss"]
        self.direction.y = 0

    def display(self, graphics):
        """Display image on the screen.
        """
        super().display(graphics)
        if self._cool_down > 0: # Update cool down time.
            self._cool_down = (self._cool_down + 1) % self._cool_down_time
    
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
        elif self.location.x + w > self.canvas.width:
            self.location.x = self.canvas.width - w
            self.direction.x = -self.direction.x
        if self.location.y < 0 or self.location.y + h > self.canvas.height:
            self.canvas.planes["trash"].add(self)
            self.canvas.boss_num -= 1
    
    def move(self):
        """Move the plane to the next location at the next frame, according to
        the current speed and direction.
        """
        self.location.x += self.speed * self.direction.x

    def fire(self):
        """Fire some bullets to your enemies.
        """
        if self._cool_down == 0:
            # Initialize the cannon first to obtain its image size.
            cannon = EnemyCannon(self.canvas, 0, 0)
            w, _ = cannon.image.get_rect().size # The image size of cannon.
            width, height = self.image.get_rect().size

            # Update the real location of the cannon.
            cannon.location.x = self.location.x + (width - w) * 0.5
            cannon.location.y = self.location.y + height

            # Shoot three bullets at a time.
            self.canvas.objects["newcome"].add(cannon)
            self.canvas.objects["newcome"].add( # Right direction.
                EnemyCannon(self.canvas, cannon.location.x, cannon.location.y, 0.2))
            self.canvas.objects["newcome"].add( # Left direction.
                EnemyCannon(self.canvas, cannon.location.x, cannon.location.y, -0.2))

            # Once the boss fires, it should be pushed into cool down stage,
            # which means that only a specific number of frames have been drawn
            # can this boss fire again.
            self._cool_down += 1

    def explode(self):
        """The plane explode when health point attains zero.
        """
        super().explode()
        self.canvas.hero.add_score(self.bonus)
        self.canvas.boss_num -= 1
