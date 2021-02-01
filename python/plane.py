import imgloader

def HeroPlane:
    def __init__(self, x, y):
        self.image = imgloader.load("hero_plane.png")
        self.health = 100
        self.speed = 2.0
        self.cool_down = 0
        self.fire_command = False
        # self.direction
        # self.location
