#ifndef AIRCRAFT_PLANE_HPP_
#define AIRCRAFT_PLANE_HPP_

#include "utility.hpp"

namespace aw {

class HeroPlane {
public:
  HeroPlane(SDL_Surface* surface, std::size_t x, std::size_t y)
      : surface_(surface), x_(x), y_(y) {
    this->img_ = aw::util::loadSurface(
      "images/hero_plane.png", surface_->format);
  }

  void display() {
    SDL_BlitSurface(img_, nullptr, surface_, nullptr);
  }

protected:
  // The canvas surface pointer.
  SDL_Surface* surface_;

  // The background image surface pointer.
  SDL_Surface* img_;
  std::size_t x_, y_;
};

} // namespace aw

#endif // AIRCRAFT_PLANE_HPP_ 