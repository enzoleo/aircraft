#include <cassert>
#include "canvas.hpp"
#include "setting.hpp"

namespace aw {

// The default constructor.
bool Canvas::init() {
  // When construct a canvas object, make sure the sdl and sdl image are
  // already been successfully initialized!
  // Create an application window with the following settings.
  this->window_ = SDL_CreateWindow(
      "AircraftWar",           // The title of the window.
      SDL_WINDOWPOS_UNDEFINED, // Initial x coordinate of window position.
      SDL_WINDOWPOS_UNDEFINED, // Initial y coordinate of window position.
      width_, height_,         // Width and height in pixels.
      SDL_WINDOW_SHOWN);
  if (this->window_ == nullptr) {
    SDL_LogError(SDL_LOG_CATEGORY_APPLICATION,
                 "Window could not be created: %s", SDL_GetError());
    return this->status_; // Return directly.
  }
  // Get the window surface.
  this->surface_ = SDL_GetWindowSurface(this->window_);
  this->background_ = aw::util::loadSurface(
    setting::IMAGES.at("Background"), this->surface_->format);
  if (this->background_ == nullptr) {
    SDL_LogError(SDL_LOG_CATEGORY_APPLICATION,
                 "Failed to load image: %s", SDL_GetError());
    return this->status_; // Return directly.
  }

  // Initialize the hero plane pointer.
  this->hero_ = new HeroPlane(this->surface_, 150, 500);

  // Successfully initialize the canvas.
  this->status_ = true;
  return this->status_;
}

// The default destructor.
void Canvas::destroy() {
  // Ignore this if the initialization is not successful.
  if (!this->status_) return;

  // Free the background surface.
  SDL_FreeSurface(this->background_);
  this->background_ = nullptr;

  // Free the hero plane pointer.
  delete this->hero_;
  this->hero_ = nullptr;

  // Destroy the rendering window and quit the system.
  SDL_DestroyWindow(this->window_);
  this->window_ = nullptr;
  IMG_Quit(); SDL_Quit();
}

// Paint all components of this canvas.
void Canvas::paint() const {
  assert(!this->apply(this->background_));
  assert(!this->hero_->display());
}

// Apply the surface and render on the window.
int Canvas::apply(SDL_Surface*    surface,
                  const SDL_Rect* srcrect,
                  SDL_Rect*       dstrect) const {
  return SDL_BlitSurface(surface, srcrect, this->surface_, dstrect);
}

// Update the surface in the main gui loop.
void Canvas::update() {
  SDL_UpdateWindowSurface(this->window_);
}


} // namespace aw

