#ifndef AIRCRAFT_CANVAS_HPP_
#define AIRCRAFT_CANVAS_HPP_

#include <iostream>
#include "plane.hpp"

namespace aw {

class Canvas {
public:
  // The default constructor/destructor.
  Canvas() = default;
  Canvas(std::size_t width, std::size_t height)
      : width_(width), height_(height) { }

  // Initialize/Destroy canvas.
  bool init();
  void destroy();

  // Accessors to invisible member variables.
  auto width()  const noexcept { return this->width_; }
  auto height() const noexcept { return this->height_; }
  auto format() const noexcept { return this->surface_->format; }
  auto good()   const noexcept { return this->status_; }

  // Paint objects on the canvas.
  void paint() const;

  // Update the surface in the main gui loop.
  void update();

protected:
  // Apply the surface and render on the window.
  int apply(SDL_Surface*    surface,
            const SDL_Rect* srcrect = nullptr,
            SDL_Rect*       dstrect = nullptr) const;

  // The window we'll be rendering to.
  SDL_Window* window_ = nullptr;
	
  // The surface contained by the window.
  SDL_Surface* surface_ = nullptr;

  // Background surface.
  SDL_Surface* background_ = nullptr;

  // The boolean variable indicating whether the image loadings are
  // all correct or not.
  bool status_ = false;

  // The gemetrical shape of the canvas.
  std::size_t width_, height_;

  // The hero character we are operating on.
  HeroPlane* hero_ = nullptr;
};

} // namespace aw

#endif // AIRCRAFT_CANVAS_HPP_

