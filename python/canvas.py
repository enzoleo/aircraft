import setting

class Canvas:
    """This class contains the global variables associated to the game.
    These variables are going to be changed at every frame.
    """
    def __init__(self):
      self.status = setting.RUNNING

      # We use this variable to control the number of boss ships. Clearly,
      # you don't want too many boss to appear at the same time... Only one
      # will be displayed at a time. That's enough.
      self.boss_num = 0

      # ALSO DO NOT touch these variables. They are used to control what 
      # the pygame will display on the screen.
      self.newcome, self.trash, self.objects = set(), set(), set()