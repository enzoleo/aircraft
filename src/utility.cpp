#include <iostream>
#include <utility.hpp>

namespace aw {

namespace util {

SDL_Surface* loadSurface(std::string path, SDL_PixelFormat *fmt) {
  // The final optimized image
  SDL_Surface* optimizedSurface = nullptr;

  // Load image at specified path
  SDL_Surface* loadedSurface = IMG_Load(path.c_str());
  if (loadedSurface == nullptr) {
    printf("Unable to load image %s! SDL_image Error: %s\n", path.c_str(), IMG_GetError());
  }
  else {
    // Convert surface to screen format
    optimizedSurface = SDL_ConvertSurface(loadedSurface, fmt, 0);
    if (optimizedSurface == nullptr) {
      printf("Unable to optimize image %s! SDL Error: %s\n", path.c_str(), SDL_GetError());
    }
    // Get rid of old loaded surface
    SDL_FreeSurface(loadedSurface);
  }
  return optimizedSurface;
}

} // namespace util

} // namespace aw
