import pygame
import imgloader

if __name__ == '__main__':    
    pygame.init()
    screen = pygame.display.set_mode((400, 654), 0, 32)
    pygame.display.set_caption("AircraftWar")

    background = imgloader.load("background.png")
    gameover = imgloader.load("gameover.png")
    
    while True:
        screen.blit(background, (0, 0))
        pygame.display.update()

