cmake_minimum_required(VERSION 3.12) 
project(aircraft LANGUAGES CXX)

# We use C++17 by defaults.
set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_FLAGS "-Wall -O2")
set(AIRCRAFT_INCLUDE_DIR ${PROJECT_SOURCE_DIR}/include)
set(AIRCRAFT_SOURCE_DIR ${PROJECT_SOURCE_DIR}/src)

# Add additional cmake module paths so that cmake can find some libraries
# that are not supported natively but added as extensions in cmake/
# directory in terms of the custom FindXXX.cmake file.
set(CMAKE_MODULE_PATH ${CMAKE_MODULE_PATH} ${CMAKE_SOURCE_DIR}/cmake/)

# Find the required SDL2 and related thirdparties.
INCLUDE(FindPkgConfig)
find_package(SDL2 REQUIRED)
find_package(SDL2_image REQUIRED)
if(SDL2_FOUND AND SDL2_IMAGE_FOUND)
  message(STATUS "SDL2 include directories: " ${SDL2_INCLUDE_DIRS})
  message(STATUS "SDL2_image include directories: " ${SDL2_IMAGE_INCLUDE_DIRS})
  message(STATUS "SDL2 libraries: " ${SDL2_LIBRARIES})
  message(STATUS "SDL2_image libraries: " ${SDL2_IMAGE_LIBRARIES})
endif()

# Include required headers.
include_directories(${SDL2_INCLUDE_DIRS})
include_directories(${SDL2_IMAGE_INCLUDE_DIRS})
include_directories(${AIRCRAFT_INCLUDE_DIR})

# Specify all cpp files in the source directory as sources for convenience.
file(GLOB_RECURSE AIRCRAFT_SOURCES ${AIRCRAFT_SOURCE_DIR}/*.cpp)
list(REMOVE_ITEM AIRCRAFT_SOURCES ${AIRCRAFT_SOURCE_DIR}/main.cpp)
add_library(aw SHARED ${AIRCRAFT_SOURCES})
target_link_libraries(aw ${SDL2_LIBRARIES} ${SDL2_IMAGE_LIBRARIES})

# Add the main executable of the aircraft war game.
add_executable(aircraft ${AIRCRAFT_SOURCE_DIR}/main.cpp)
target_link_libraries(aircraft aw)

# Install settings.
set(CMAKE_INSTALL_PREFIX ${PROJECT_SOURCE_DIR}/install)
install(DIRECTORY include/ DESTINATION include)
install(TARGETS aw DESTINATION lib)

