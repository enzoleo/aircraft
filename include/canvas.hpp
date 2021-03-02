#ifndef AIRCRAFT_CANVAS_HPP_
#define AIRCRAFT_CANVAS_HPP_

#include <iostream>
#include "utility.hpp"

namespace aw {

class Canvas {
public:
  // The default constructor/destructor.
  Canvas();
  ~Canvas();

  // Accessors to invisible member variables.
  auto format() const noexcept { return this->surface_->format; }
  auto good()   const noexcept { return this->status_; }

  void paint();

  // Apply the surface and render on the window.
  void apply(SDL_Surface*    surface,
             const SDL_Rect* srcrect = nullptr,
             SDL_Rect*       dstrect = nullptr);

  // Update the surface in the main gui loop.
  void update();

protected:
  // The window we'll be rendering to.
  SDL_Window* window_ = nullptr;
	
  // The surface contained by the window.
  SDL_Surface* surface_ = nullptr;

  SDL_Surface* background_ = nullptr;

  // The boolean variable indicating whether the image loadings are
  // all correct or not.
  bool status_ = false;
};

} // namespace aw

#endif // AIRCRAFT_CANVAS_HPP_

