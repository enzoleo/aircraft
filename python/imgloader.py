import pygame

class ImgLoader:
    def __init__(self, path = "."):
        """Initialize image loader with a specific path.
        """
        self.dir = path

    def load(self, path):
        img = pygame.image.load(self.dir + "/" + path)
        return pygame.transform.scale(img, img.get_rect().size)

# DO NOT change the default path!
# The variable is global in the current python file. All image sources
# locate at this directory, so do not touch that directory and do not change
# the default path specified here.
loader = ImgLoader("../images")

def load(path):
    return loader.load(path)
