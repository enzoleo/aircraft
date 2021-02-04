#!/usr/bin/python3
import pygame

from plane import *
from bomb import *
from supply import *
import setting
from canvas import *

if __name__ == '__main__':    
    pygame.init()
    pygame.display.set_caption("canvas")
    graphics = pygame.display.set_mode((setting.width, setting.height), 0, 32)

    # The default canvas.
    canvas = Canvas(graphics)
    while True:
        if not canvas.render():
            break
    
    # Safely quit the game.
    pygame.quit()

