#include <iostream>
#include <cassert>
#include <SDL2/SDL.h>

auto main(int argc, char* argv[]) -> int {
  // Declare a window we will be rendering to that contains a surface.
  // An SDL surface is roughly an image.
  SDL_Window* window = nullptr;
  SDL_Surface* surface = nullptr;

  // Initialize SDL first before calling any SDL functions.
  assert(((void)"Could not initialize SDL!", SDL_Init(SDL_INIT_VIDEO) >= 0));
  window = SDL_CreateWindow( // Now create window and check the pointer.
      "AircraftWar", SDL_WINDOWPOS_UNDEFINED, SDL_WINDOWPOS_UNDEFINED,
      400, 654, SDL_WINDOW_SHOWN);
  assert(((void)"SDL window could not be created!", window != nullptr));

  // Get window surface to assign to the pointer.
  surface = SDL_GetWindowSurface(window);
  SDL_FillRect( // Color the SDL surface.
      surface, nullptr, SDL_MapRGB(surface->format, 0xFF, 0xFF, 0xFF));
            
  // Update the surface
  SDL_UpdateWindowSurface(window);
  SDL_Delay(5000);

  // Destroy the rendering window and quit the system.
  SDL_DestroyWindow(window);
  SDL_Quit();

  return 0;
}


