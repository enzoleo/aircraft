#ifndef AIRCRAFT_PLANE_HPP_
#define AIRCRAFT_PLANE_HPP_

#include "setting.hpp"

namespace aw {

class Plane {
public:
  // The display method to render all components in the canvas surface.
  // Specifically, we display the background image of hero plane.
  int display() const {
    SDL_Rect rect { static_cast<int>(loc_.x), static_cast<int>(loc_.y) };
    return SDL_BlitSurface(img_, nullptr, surface_, &rect);
  }

  // Attribute accessors.
  auto health() const noexcept { return health_; }
  auto speed()  const noexcept { return speed_; }
  const auto& direction() const { return direction_; }
  const auto& location()  const { return loc_; }
  auto x() const noexcept { return loc_.x; }
  auto y() const noexcept { return loc_.y; }

  // Attribute mutators for directions.
  void direction(util::DIRECTION hori, util::DIRECTION vert) {
    direction_.x = hori; direction_.y = vert;
  }
  void direction(util::Direction d) { direction_ = d; }

  // Stop the plane horizontally/vertically/totally.
  void hstop() { direction_.x = util::DIRECTION::STATIONARY; }
  void vstop() { direction_.y = util::DIRECTION::STATIONARY; }
  void stop()  { hstop(); vstop(); }

protected:
  // The default constructor of hero plane.
  Plane() = default;
  Plane(SDL_Surface* surface, std::size_t x, std::size_t y)
      : surface_ { surface }, loc_ { x, y } { }
  Plane(SDL_Surface* surface, const util::Point2l& p)
      : surface_ { surface }, loc_ { p } { }

  // The canvas surface pointer.
  SDL_Surface* surface_ = nullptr;

  // The background image surface pointer.
  SDL_Surface* img_ = nullptr;
  util::Point2l loc_ { 0, 0 };

  // The health point and speed attribute.
  int health_ { 0 }; // Note the health point requires subtraction.
  std::size_t speed_ { 0 };

  // The moving direction of the plane.
  util::Direction direction_ {
    util::DIRECTION::STATIONARY, util::DIRECTION::STATIONARY
  };
};

class HeroPlane : public Plane {
public:
  // The default constructor of hero plane.
  HeroPlane() = default;
  HeroPlane(SDL_Surface* surface, std::size_t x, std::size_t y)
      : Plane(surface, x, y) {
    this->img_ = // Load the surface from a backgroung image.
      aw::util::loadSurface(setting::IMAGES.at("HeroPlane"));
  }
  HeroPlane(SDL_Surface* surface, const util::Point2l& p)
      : Plane(surface, p) {
    this->img_ = // Load the surface from a backgroung image.
      aw::util::loadSurface(setting::IMAGES.at("HeroPlane"));
  }

  // The default destructor.
  ~HeroPlane() {
    SDL_FreeSurface(this->img_);
  }
};

} // namespace aw

#endif // AIRCRAFT_PLANE_HPP_
