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
    key_listerner = canvas.key_event()
    while True:
        rendering = key_listerner.response()
        canvas.render()
        if not rendering: break # Stop rendering and close the window.
    
    # Safely quit the game.
    pygame.quit()

