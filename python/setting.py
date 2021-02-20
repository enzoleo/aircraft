# This file only contains global settings. Everything defined here is
# actually associated to the system settings of the game. To better
# control the hyperparameters you may encounter in designing this game,
# just feel free to edit them as you like.

# Default window size.
WIDTH, HEIGHT = 400, 654

# Max frame per second.
FPS = 120

# Default game status.
RUNNING, GAMEOVER = True, False

# The camp constants.
NEUTRAL, HERO, ENEMY = 0, 1, 2

# The initial position of the hero plane. Feel free to edit it.
HERO_INIT_POS = (150, 500)

# Image filenames
IMAGES = {
    "HeroPlane": "hero_plane.png",
    "EnemyLightPlane": "enemy_light_plane.png",
    "EnemyBoss": "enemy_boss.png",
    "HeroBullet": "hero_bullet.png",
    "EnemyNormalBullet": "enemy_normal_bullet.png",
    "EnemyCannon": "enemy_cannon.png",
    "Bomb": "bomb.png",
    "Supply": "supply.png" 
}

# Probabilities when generating characters at the beginning of every
# frame. Feel free to modify them.
PROB = {
    "EnemyPlane": 0.01,
    "Bomb": 0.001,
    "Supply": 0.005,
}
# The sub probability. When an enemy plane is going to be generated,
# it will 'mutate' to a boss with respect to a specific probability.
SUB_PROB = {
    "EnemyPlane": {
        "EnemyBoss": 0.1
    }
}
# Initial health point of each kind of planes.
HEALTH = {
    "HeroPlane": 100,
    "EnemyLightPlane": 20,
    "EnemyBoss": 800
}
# Initial damage power of each kind of bullets.
DAMAGE = {
    "HeroBullet": 20,
    "EnemyNormalBullet": 5,
    "EnemyCannon": 8
}
# The speed of each kind of characters in this game. 
SPEED = {
    "HeroPlane": 2.0,
    "EnemyLightPlane": 1.5,
    "EnemyBoss": 1.5,
    "HeroBullet": 3.0,
    "EnemyNormalBullet": 4.0,
    "EnemyCannon": 3.0,
    "Bomb": 1,
    "Supply": 1.5 
}
# The recovery power of the supply. Without supply, you cannot live long.
# Try to catch supplies as many as possible.
RECOVERY = {
    "Supply": 20
}

# When you take down an enemy, you will get bonus.
BONUS = {
    "EnemyLightPlane": 10,
    "EnemyBoss": 80
}
# Planes can fire. We assume that every plane has unlimited fire resources,
# but hero plane and boss plane has cool down time so that they cannot fire
# at every time. An enemy light plane fire with respect to a relatively
# small probability.
COOL_DOWN_TIME = {
    "HeroPlane": 15,
    "EnemyBoss": 60
}

# Enemy light planes do not have cool down time. They fire with respect to
# a specific probability
FIRE_PROB = {
    "EnemyLightPlane": 0.01
}