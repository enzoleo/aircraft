#!/usr/bin/python3
import pygame
import scipy.stats

from plane import *
from bomb import *
from supply import *
import setting

def generate_character():
    """Randomly generate characters from the top boundary of the window.
    In general, three kinds of characters will be generated with respect
    to specific probability provided in settings.py automatically.

    Note that the probability might look very small, because it is used
    by this generator at every frame.
    """
    p = setting.prob
    if scipy.stats.bernoulli.rvs(p["EnemyPlane"]): # Generate enemies.
        x = scipy.stats.uniform.rvs(0.2, 0.6) * setting.width
        prob = setting.sub_prob["EnemyPlane"]["EnemyBoss"]
        if scipy.stats.bernoulli.rvs(prob) and setting.boss_num < 1:
            # Generate a boss ship. Note that at most one boss ship can
            # be displayed at a time.
            setting.newcome.add(EnemyBoss(x, 0))
            setting.boss_num += 1
        else:
            setting.newcome.add(EnemyLightPlane(x, 0))
    if scipy.stats.bernoulli.rvs(p["Bomb"]): # Generate bombs.
        x = scipy.stats.uniform.rvs(0.2, 0.6) * setting.width
        setting.newcome.add(Bomb(x, 0))
    if scipy.stats.bernoulli.rvs(p["Supply"]): # Generate supplies.
        x = scipy.stats.uniform.rvs(0.2, 0.6) * setting.width
        setting.newcome.add(Supply(x, 0))

if __name__ == '__main__':    
    pygame.init()
    pygame.display.set_caption("AircraftWar")

    hero = HeroPlane(*setting.hero_init_pos)
    setting.objects.add(hero)
    
    quit_game = False
    while not quit_game:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                quit_game = True
            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_ESCAPE:
                    quit_game = True
                if event.key == pygame.K_UP:
                    hero.direction.y = -1
                elif event.key == pygame.K_LEFT:
                    hero.direction.x = -1
                elif event.key == pygame.K_DOWN:
                    hero.direction.y = 1
                elif event.key == pygame.K_RIGHT:
                    hero.direction.x = 1
                elif event.key == pygame.K_SPACE:
                    hero.fire_command = True
            if event.type == pygame.KEYUP:
                if event.key == pygame.K_UP:
                    if hero.direction.y == -1: hero.direction.y = 0
                if event.key == pygame.K_LEFT:
                    if hero.direction.x == -1: hero.direction.x = 0
                if event.key == pygame.K_DOWN:
                    if hero.direction.y == 1: hero.direction.y = 0
                if event.key == pygame.K_RIGHT:
                    if hero.direction.x == 1: hero.direction.x = 0
                if event.key == pygame.K_SPACE:
                    hero.fire_command = False

        setting.graphics.blit(setting.background, (0, 0))        
        if setting.status:
            generate_character()
            for object in setting.objects:
                object.move()
                object.boundary_check()
                if isinstance(object, Plane):
                    object.fire()

            for object in setting.objects:
                if isinstance(object, Plane):
                    # Check whether a plane (hero/enemy) is hit by some other
                    # objects like bullets, supplies, barriers, etc.
                    for npc in setting.objects:
                        # If a plane is hit by an npc (bullets/bombs/...), take
                        # specific actions. Different planes may react differently.
                        if object.is_hit(npc): object.hit_by(npc)
                    # The plane is shot down!
                    if object.health <= 0: object.explode()

            for object in setting.newcome:
                setting.objects.add(object)
            setting.newcome = set() # Reset the newcome list.

            for object in setting.trash:
                setting.objects.discard(object)
            setting.trash = set() # Reset the newcome list.
        else:
            setting.graphics.blit(setting.gameover, (0, 0))

        # Draw everything including bullets, planes, supplies, etc.
        for object in setting.objects:
            object.display(setting.graphics)

        # Draw health point and game score on the screen.
        font = pygame.font.SysFont(pygame.font.get_default_font(), 36)
        text_sc = font.render("SCORE:" + str(setting.score), 1, (0, 0, 0))
        text_hp = font.render("HP:" + str(hero.health), 1, (0, 0, 0))
        setting.graphics.blit(text_sc, (5, 25))
        setting.graphics.blit(text_hp, (5, 55))
        pygame.display.update()
    
    # Safely quit the game.
    pygame.quit()

