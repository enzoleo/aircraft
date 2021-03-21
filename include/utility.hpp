#ifndef AIRCRAFT_UTILITY_HPP_
#define AIRCRAFT_UTILITY_HPP_

#include <string>
#include <SDL2/SDL.h>
#include <SDL2/SDL_image.h>

namespace aw {

namespace util {

// The camp constants.
enum class CAMP {
  NEUTRAL, HERO, ENEMY
};

// Moving directions. For both horizontal and verical directions,
// an object have three different actions: moving forward, staying
// stationary, and moving backward.
enum class DIRECTION {
  BACKWARD   = -1,
  STATIONARY =  0,
  FORWARD    =  1
};
// A direction pair to store the moving status of an object.
using Direction = std::pair<DIRECTION, DIRECTION>;

SDL_Surface* loadSurface(
  std::string filename, SDL_PixelFormat *fmt,
  std::string base = "./images");

} // namespace util

} // namespace aw

#endif // AIRCRAFT_UTILITY_HPP_
