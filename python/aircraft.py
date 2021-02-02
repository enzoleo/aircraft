import pygame
import imgloader

from plane import *

class AircraftWar:
    graphics = pygame.display.set_mode((400, 654), 0, 32)
    background = imgloader.load("background.png")
    gameover = imgloader.load("gameover.png")

    hero = HeroPlane(150, 400)
    
    objects = []
    objects.append(hero)    

if __name__ == '__main__':    
    pygame.init()
    pygame.display.set_caption("AircraftWar")
    
    while True:
        AircraftWar.graphics.blit(AircraftWar.background, (0, 0))
        for object in AircraftWar.objects:
            object.display(AircraftWar.graphics)

        pygame.display.update()

