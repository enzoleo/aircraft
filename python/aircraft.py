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
    graphics = pygame.display.set_mode((setting.WIDTH, setting.HEIGHT), 0, 32)

    # The default canvas.
    canvas = Canvas(graphics)
    key_listener = canvas.key_event()
    fps_clock = pygame.time.Clock()
    while True:
        rendering = key_listener.response()
        canvas.render()
        if not rendering: break # Stop rendering and close the window.
        fps_clock.tick(setting.FPS)
    
    # Safely quit the game.
    pygame.quit()

