#ifndef AIRCRAFT_UTILITY_HPP_
#define AIRCRAFT_UTILITY_HPP_

#include <string>
#include <SDL2/SDL.h>
#include <SDL2/SDL_image.h>

namespace aw {

namespace util {

// The camp constants.
enum class CAMP {
  NEUTRAL, HERO, ENEMY
};

// Moving directions. For both horizontal and verical directions,
// an object have three different actions: moving forward, staying
// stationary, and moving backward.
enum class DIRECTION {
  BACKWARD   = -1,
  STATIONARY =  0,
  FORWARD    =  1
};

/**
 * @brief Two-dimensional point struct.
 * Note that this struct is hightly public, roughly a wrapper of a pair.
 * You are allowed to modify coordinates freely outside the struct.
 * Structured binding are automatically enabled.
 * 
 * This struct is defined for convenience only, so it should not be
 * used in any arithmetic operations / linear algebra opeations.
 */
template<class DataType>
struct Point {  
  using type = DataType;
  // The dimension of planar point defaults to be 2.
  static constexpr std::size_t dim() { return 2; }

  // Convert to a standard pair/tuple struct.
  constexpr auto pair() const { return std::make_pair(x, y); }
  constexpr auto& operator[](std::size_t index) {
    // Probably this overloading requires assert to check the input
    // index is only 0 or 1. Here we still allow any non-negative inputs.
    // assert(index == 0 || index == 1);
    return index? y : x;
  }
  constexpr const auto& operator[](std::size_t index) const {
    // Probably this overloading requires assert to check the input
    // index is only 0 or 1. Here we still allow any non-negative inputs.
    // assert(index == 0 || index == 1);
    return index? y : x;
  }

  // Two-dimensional coordinates.
  DataType x, y;
};

// A direction pair (alias) to store the moving status of an object.
using Direction = Point<DIRECTION>;
// Template type aliases.
using Point2i = Point<int>;
using Point2u = Point<unsigned>;
using Point2l = Point<size_t>;
using Point2f = Point<float>;
using Point2d = Point<double>;

/**
 * @brief load surface from a given filename.
 * 
 * @param filename the image filename allowed to be a complicated path.
 * @param fmt the format pointer.
 * @param base the image path where we will look for the specified file.
 * @return SDL_Surface* the optimized surface.
 */
SDL_Surface* loadSurface(
  std::string filename,
  std::string base = "../images");

} // namespace util

} // namespace aw

#endif // AIRCRAFT_UTILITY_HPP_
