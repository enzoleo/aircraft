import pygame

from plane import *
from setting import AircraftWar

if __name__ == '__main__':    
    pygame.init()
    pygame.display.set_caption("AircraftWar")

    hero = HeroPlane(150, 400)
    AircraftWar.objects.append(hero)
    
    while AircraftWar.status:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                AircraftWar.status = False
            if event.type == pygame.KEYDOWN:
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
        for object in AircraftWar.objects:
            object.display(AircraftWar.graphics)

        for object in AircraftWar.objects:
            object.move()
            object.boundary_check()
        hero.fire()

        for object in AircraftWar.newcome:
            AircraftWar.objects.append(object)
        AircraftWar.newcome = [] # Reset the newcome list.

        for object in AircraftWar.trash:
            AircraftWar.objects.remove(object)
        AircraftWar.trash = [] # Reset the newcome list.

        pygame.display.update()
    
    print("quit normally?")
    pygame.quit()

