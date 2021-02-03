import pygame
import imgloader

class AircraftWar:
    # Default window size.
    width, height = 400, 654
    graphics = pygame.display.set_mode((width, height), 0, 32)
    background = imgloader.load("background.png")
    gameover = imgloader.load("gameover.png")

    newcome = set()
    trash = set()
    objects = set()
    status = True

    score = 0
    boss_num = 0