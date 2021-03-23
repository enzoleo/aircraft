#include <iostream>
#include <utility.hpp>

namespace aw {

namespace util {

SDL_Surface* loadSurface(std::string filename, std::string base) {
  // Parse the directory and path first.
  if (base.empty()) base = ".";
  std::string path(base);
  if (char c = path.back(); c != '/') path += '/';
  
  // Append the filename into the base path. The first char should not be '/'.
  std::size_t i { 0 };
  for (; i < filename.size(); ++i)
    if (filename[i] != '/') break;
  path += filename.substr(i);

  // The final optimized image
  SDL_Surface* optimizedSurface = nullptr;

  // Load image at specified path
  SDL_Surface* loadedSurface = IMG_Load(path.c_str());
  if (loadedSurface == nullptr) {
    printf("Unable to load image %s! SDL_image Error: %s\n", path.c_str(), IMG_GetError());
  }
  else {
    // Convert surface to screen format
    optimizedSurface = SDL_ConvertSurfaceFormat(loadedSurface, SDL_PIXELFORMAT_RGBA8888, 0);
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
