import pygame

class ImgLoader:
    def __init__(self, path = "."):
        """Initialize image loader with a specific path.
        """
        self.dir = path

    def load(self, path):
        return pygame.image.load(self.dir + "/" + path)