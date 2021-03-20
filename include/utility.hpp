#ifndef AIRCRAFT_UTILITY_HPP_
#define AIRCRAFT_UTILITY_HPP_

#include <string>
#include <SDL2/SDL.h>
#include <SDL2/SDL_image.h>

namespace aw {

namespace util {

SDL_Surface* loadSurface(
  std::string filename, SDL_PixelFormat *fmt,
  std::string base = "./images");

} // namespace util

} // namespace aw

#endif // AIRCRAFT_UTILITY_HPP_
