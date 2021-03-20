#ifndef AIRCRAFT_SETTING_HPP_
#define AIRCRAFT_SETTING_HPP_

#include <string>
#include <map>

namespace aw {

// This file only contains global settings. Everything defined here is
// actually associated to the system settings of the game. To better
// control the hyperparameters you may encounter in designing this game,
// just feel free to edit them as you like.
namespace setting {

// The default window size.
constexpr const std::size_t WIDTH = 400, HEIGHT = 654;

// Default game status.
constexpr const bool RUNNING = true, GAMEOVER = false;

// The camp constants.
enum class CAMP {
  NEUTRAL, HERO, ENEMY
};

// Image filenames. Not necessary to implement literal-type map.
const std::map<std::string, std::string> IMAGES = {
  { "Background",        "background.png" },
  { "HeroPlane",         "hero_plane.png" },
  { "EnemyLightPlane",   "enemy_light_plane.png" },
  { "EnemyBoss",         "enemy_boss.png" },
  { "HeroBullet",        "hero_bullet.png" },
  { "EnemyNormalBullet", "enemy_normal_bullet.png" },
  { "EnemyCannon",       "enemy_cannon.png" },
  { "Bomb",              "bomb.png" },
  { "Supply",            "supply.png" }
};

} // namespace setting

} // namespace aw

#endif // AIRCRAFT_SETTING_HPP_
