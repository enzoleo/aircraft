import pygame
import imgloader

class AircraftWar:
    # Default window size.
    width, height = 400, 654
    graphics = pygame.display.set_mode((width, height), 0, 32)
    background = imgloader.load("background.png")
    gameover = imgloader.load("gameover.png")

    newcome = []
    trash = []
    objects = []
    status = True

    boss_num = 0