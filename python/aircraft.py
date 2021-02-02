import pygame
import imgloader

from plane import *

if __name__ == '__main__':    
    pygame.init()
    graphics = pygame.display.set_mode((400, 654), 0, 32)
    pygame.display.set_caption("AircraftWar")

    background = imgloader.load("background.png")
    gameover = imgloader.load("gameover.png")

    hero = HeroPlane(150, 400)
    
    objects = []
    objects.append(hero)
    
    while True:
        graphics.blit(background, (0, 0))
        for object in objects:
            object.display(graphics)

        pygame.display.update()

