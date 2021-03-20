#ifndef AIRCRAFT_PLANE_HPP_
#define AIRCRAFT_PLANE_HPP_

#include "utility.hpp"

namespace aw {

class HeroPlane {
public:
  // The default constructor of hero plane.
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
  void display() const {
    SDL_BlitSurface(img_, nullptr, surface_, nullptr);
  }

protected:
  // The canvas surface pointer.
  SDL_Surface* surface_ = nullptr;

  // The background image surface pointer.
  SDL_Surface* img_ = nullptr;
  std::size_t x_, y_;
};

} // namespace aw

#endif // AIRCRAFT_PLANE_HPP_ 