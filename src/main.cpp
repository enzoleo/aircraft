#include <iostream>
#include <cassert>
#include "canvas.hpp"

auto main(int argc, char* args[]) -> int {
  // Initialize SDL first before calling any SDL functions.
  if (SDL_Init(SDL_INIT_TIMER | SDL_INIT_VIDEO) < 0) {
    SDL_LogError(SDL_LOG_CATEGORY_APPLICATION,
                 "Couldn't initialize SDL: %s", SDL_GetError());
    return EXIT_FAILURE; // Return directly.
  }
  // Initialize the PNG image loading.
  if (!(IMG_Init(IMG_INIT_PNG) & IMG_INIT_PNG)) {
    SDL_LogError(SDL_LOG_CATEGORY_APPLICATION,
                 "SDL_image could not initialize: %s", SDL_GetError());
    return EXIT_FAILURE; // Return directly.
  }

  // Start up SDL and create window. Check the status of canvas.
  aw::Canvas canvas(400, 654); // Declare a canvas object.
  auto status = canvas.init(); // Initialize the canvas.
  assert(((void)"Failed to initialize canvas!", status));
  
  bool quit = false;
  SDL_Event e;
  // While application is running
  while (!quit) {
    // Handle events on queue
    while (SDL_PollEvent(&e) != 0) {
      // User requests quit
      if (e.type == SDL_QUIT)
        quit = true;
    }
    canvas.paint();
    canvas.update();
  }
  // Free resources and close SDL. This operation must be done.
  canvas.destroy();
  return 0;
}


