import pygame
from imgloader import ImgLoader

if __name__ == '__main__':
    # DO NOT change the default path! All image sources locate at this
    # directory, so do not touch that directory and do not change the
    # default path specified here.
    img_loader = ImgLoader("../src/aircraft/images");
    screen = pygame.display.set_mode((400, 654), 0, 32)

    background = img_loader.load("background.png")
    gameover = img_loader.load("gameover.png")

