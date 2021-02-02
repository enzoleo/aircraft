import pygame
import imgloader

from plane import *

class AircraftWar:
    # Default window size.
    width, height = 400, 654
    graphics = pygame.display.set_mode((width, height), 0, 32)
    background = imgloader.load("background.png")
    gameover = imgloader.load("gameover.png")

    newcome = []
    trash = []    
    objects = []

if __name__ == '__main__':    
    pygame.init()
    pygame.display.set_caption("AircraftWar")

    hero = HeroPlane(150, 400)
    AircraftWar.objects.append(hero) 
    
    while True:
        AircraftWar.graphics.blit(AircraftWar.background, (0, 0))
        for object in AircraftWar.objects:
            object.display(AircraftWar.graphics)

        pygame.display.update()

