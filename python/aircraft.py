#!/usr/bin/python3
import pygame
import scipy.stats

from plane import *
from bomb import *
from supply import *
from setting import AircraftWar

def generate_character():
    if scipy.stats.bernoulli.rvs(0.01): # Generate enemies.
        x = scipy.stats.uniform.rvs(0.2, 0.6) * AircraftWar.width
        if scipy.stats.bernoulli.rvs(0.2) and AircraftWar.boss_num < 1:
            # Generate a boss ship. Note that at most one boss ship can
            # be displayed at a time.
            AircraftWar.newcome.add(EnemyBoss(x, 0))
            AircraftWar.boss_num += 1
        else:
            AircraftWar.newcome.add(EnemyLightPlane(x, 0))
    if scipy.stats.bernoulli.rvs(0.001): # Generate bombs.
        x = scipy.stats.uniform.rvs(0.2, 0.6) * AircraftWar.width
        AircraftWar.newcome.add(Bomb(x, 0))
    if scipy.stats.bernoulli.rvs(0.004): # Generate supplies.
        x = scipy.stats.uniform.rvs(0.2, 0.6) * AircraftWar.width
        AircraftWar.newcome.add(Supply(x, 0))

if __name__ == '__main__':    
    pygame.init()
    pygame.display.set_caption("AircraftWar")

    hero = HeroPlane(150, 400)
    AircraftWar.objects.add(hero)
    
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

        AircraftWar.graphics.blit(AircraftWar.background, (0, 0))        
        if AircraftWar.status:
            generate_character()
            for object in AircraftWar.objects:
                object.move()
                object.boundary_check()
                if isinstance(object, Plane):
                    object.fire()

            for object in AircraftWar.objects:
                if isinstance(object, Plane):
                    # Check whether a plane (hero/enemy) is hit by some other
                    # objects like bullets, supplies, barriers, etc.
                    for npc in AircraftWar.objects:
                        # If a plane is hit by an npc (bullets/bombs/...), take
                        # specific actions. Different planes may react differently.
                        if object.is_hit(npc): object.hit_by(npc)
                    # The plane is shot down!
                    if object.health <= 0: object.explode()

            for object in AircraftWar.newcome:
                AircraftWar.objects.add(object)
            AircraftWar.newcome = set() # Reset the newcome list.

            for object in AircraftWar.trash:
                AircraftWar.objects.discard(object)
            AircraftWar.trash = set() # Reset the newcome list.
        else:
            AircraftWar.graphics.blit(AircraftWar.gameover, (0, 0))

        # Draw health point and game score on the screen.
        font = pygame.font.SysFont(pygame.font.get_default_font(), 36)
        text_sc = font.render("SCORE:" + str(AircraftWar.score), 1, (0, 0, 0))
        text_hp = font.render("HP:" + str(hero.health), 1, (0, 0, 0))
        AircraftWar.graphics.blit(text_sc, (5, 25))
        AircraftWar.graphics.blit(text_hp, (5, 55))
        
        # Draw everything including bullets, planes, supplies, etc.
        for object in AircraftWar.objects:
            object.display(AircraftWar.graphics)
        pygame.display.update()
    
    # Safely quit the game.
    pygame.quit()

