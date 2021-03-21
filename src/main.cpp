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
  aw::Canvas canvas(aw::setting::WIDTH, aw::setting::HEIGHT);
  auto status = canvas.init(); // Initialize the canvas.
  assert(((void)"Failed to initialize canvas!", status));
  
  bool quit = false;
  SDL_Event e;
  while (!quit) {
    while (SDL_PollEvent(&e) != 0) {
      // Check the event type. In this project, we need to implement
      // keyboard response.
      switch (e.type) {
      case SDL_QUIT:
        quit = true; break;
      case SDL_KEYDOWN:
        switch (e.key.keysym.sym) {
        case SDLK_UP:
          /* code */
          break;
        case SDLK_LEFT:
          /* code */
          break;
        case SDLK_DOWN:
          /* code */
          break;
        case SDLK_RIGHT:
          /* code */
          break;
        default:
          break;
        }
        break;
      case SDL_KEYUP:
        break;
      default:
        break;
      }
    }
    canvas.paint();
    canvas.update();
  }
  // Free resources and close SDL. This operation must be done.
  canvas.destroy();
  return 0;
}


