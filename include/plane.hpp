#ifndef AIRCRAFT_PLANE_HPP_
#define AIRCRAFT_PLANE_HPP_

#include "utility.hpp"

namespace aw {

class HeroPlane {
public:
  // The default constructor of hero plane.
  HeroPlane() = default;
  HeroPlane(SDL_Surface* surface, std::size_t x, std::size_t y)
      : surface_(surface), x_(x), y_(y) {
    this->img_ = // Load the surface from a backgroung image.
      aw::util::loadSurface("images/hero_plane.png", surface->format);
  }

  // The default destructor.
  ~HeroPlane() {
    SDL_FreeSurface(this->img_);
  }

  // The display method to render all components in the canvas surface.
  // Specifically, we display the background image of hero plane.
  int display() const {
    SDL_Rect rect { static_cast<int>(x_), static_cast<int>(this->y_) };
    return SDL_BlitSurface(img_, nullptr, surface_, &rect);
  }

protected:
  // The canvas surface pointer.
  SDL_Surface* surface_ = nullptr;

  // The background image surface pointer.
  SDL_Surface* img_ = nullptr;
  std::size_t x_ { 0 }, y_ { 0 };
};

} // namespace aw

#endif // AIRCRAFT_PLANE_HPP_
