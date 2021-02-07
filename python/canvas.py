import pygame
import scipy.stats

import setting
import imgloader
from plane import *
from bomb import *
from supply import *

class Canvas:
    """This class contains the global variables associated to the game.
    These variables are going to be changed at every frame.
    """
    # The background image and gameover image.
    background = imgloader.load("background.png")
    gameover = imgloader.load("gameover.png")

    def __init__(self, graphics):
        """Create a self for graphical user interface painting.
        """
        self.graphics = graphics
        self.width, self.height = graphics.get_rect().size

        # Initialize the self.hero plane (player).
        self.hero = HeroPlane(self, *setting.hero_init_pos)
        self.status = setting.RUNNING

        # ALSO DO NOT touch these variables. They are used to control what 
        # the pygame will display on the screen.
        self.planes  = { "newcome": set(), "trash": set(), "current": set() }
        self.objects = { "newcome": set(), "trash": set(), "current": set() }
        self.planes["current"].add(self.hero)

        # We use this variable to control the number of boss ships. Clearly,
        # you don't want too many boss to appear at the same time... Only one
        # will be displayed at a time. That's enough.
        self.boss_num = 0

    def generate_character(self):
        """Randomly generate characters from the top boundary of the window.
        In general, three kinds of characters will be generated with respect
        to specific probability provided in settings.py automatically.

        Note that the probability might look very small, because it is used
        by this generator at every frame.
        """
        p = setting.prob
        if scipy.stats.bernoulli.rvs(p["EnemyPlane"]): # Generate enemies.
            x = scipy.stats.uniform.rvs(0.2, 0.6) * self.width
            prob = setting.sub_prob["EnemyPlane"]["EnemyBoss"]
            if scipy.stats.bernoulli.rvs(prob) and self.boss_num < 1:
                # Generate a boss ship. Note that at most one boss ship can
                # be displayed at a time.
                self.planes["newcome"].add(EnemyBoss(self, x, 0))
                self.boss_num += 1
            else:
                self.planes["newcome"].add(EnemyLightPlane(self, x, 0))
        if scipy.stats.bernoulli.rvs(p["Bomb"]): # Generate bombs.
            x = scipy.stats.uniform.rvs(0.2, 0.6) * self.width
            self.objects["newcome"].add(Bomb(self, x, 0))
        if scipy.stats.bernoulli.rvs(p["Supply"]): # Generate supplies.
            x = scipy.stats.uniform.rvs(0.2, 0.6) * self.width
            self.objects["newcome"].add(Supply(self, x, 0))

    def render(self):
        rendering = True
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                rendering = False
            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_ESCAPE:
                    rendering = False
                if event.key == pygame.K_UP:
                    self.hero.direction.y = -1
                elif event.key == pygame.K_LEFT:
                    self.hero.direction.x = -1
                elif event.key == pygame.K_DOWN:
                    self.hero.direction.y = 1
                elif event.key == pygame.K_RIGHT:
                    self.hero.direction.x = 1
                elif event.key == pygame.K_SPACE:
                    self.hero.fire_command = True
            if event.type == pygame.KEYUP:
                if event.key == pygame.K_UP:
                    if self.hero.direction.y == -1: self.hero.direction.y = 0
                if event.key == pygame.K_LEFT:
                    if self.hero.direction.x == -1: self.hero.direction.x = 0
                if event.key == pygame.K_DOWN:
                    if self.hero.direction.y == 1: self.hero.direction.y = 0
                if event.key == pygame.K_RIGHT:
                    if self.hero.direction.x == 1: self.hero.direction.x = 0
                if event.key == pygame.K_SPACE:
                    self.hero.fire_command = False

        self.graphics.blit(self.background, (0, 0))        
        if self.status:
            self.generate_character()
            for object in self.objects["current"]:
                object.move()
                object.boundary_check()
            for object in self.planes["current"]:
                object.move()
                object.boundary_check()
                object.fire()

            for plane in self.planes["current"]:
                # Check whether a plane (self.hero/enemy) is hit by some other
                # objects like bullets, supplies, barriers, etc.
                for obj in self.objects["current"]:
                    # If a plane is hit by an npc (bullets/bombs/...), take
                    # specific actions. Different planes may react differently.
                    if plane.is_hit(obj): plane.hit_by(obj)
                # The plane is shot down!
                if plane.health <= 0: plane.explode()

            for s in [self.planes, self.objects]:
                for obj in s["newcome"]: s["current"].add(obj)
                for obj in s["trash"]: s["current"].discard(obj)
                # Reset the newcome and trash set.
                s["newcome"], s["trash"] = set(), set()
        else:
            self.graphics.blit(self.gameover, (0, 0))

        # Draw everything including bullets, planes, supplies, etc.
        for object in self.planes["current"] | self.objects["current"]:
            object.display(self.graphics)

        # Draw health point and game score on the screen.
        font = pygame.font.SysFont(pygame.font.get_default_font(), 36)
        text_sc = font.render("SCORE:" + str(self.hero.score), 1, (0, 0, 0))
        text_hp = font.render("HP:" + str(self.hero.health), 1, (0, 0, 0))
        self.graphics.blit(text_sc, (5, 25))
        self.graphics.blit(text_hp, (5, 55))
        pygame.display.update()
        return rendering
