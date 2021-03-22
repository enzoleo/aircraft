cmake_minimum_required(VERSION 3.12) 
project(aircraft LANGUAGES CXX)

# We use C++17 by defaults.
set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_FLAGS "-Wall -O2")
set(AIRCRAFT_INCLUDE_DIR ${PROJECT_SOURCE_DIR}/include)
set(AIRCRAFT_SOURCE_DIR ${PROJECT_SOURCE_DIR}/src)

# Find the required SDL2 and related thirdparties.
find_package(SDL2 REQUIRED)

# Include required headers.
include_directories(${AIRCRAFT_INCLUDE_DIR})

# Specify all cpp files in the source directory as sources for convenience.
file(GLOB_RECURSE AIRCRAFT_SOURCES ${AIRCRAFT_SOURCE_DIR}/*.cpp)
list(REMOVE_ITEM AIRCRAFT_SOURCES ${AIRCRAFT_SOURCE_DIR}/main.cpp)
add_library(aw SHARED ${AIRCRAFT_SOURCES})

# Add the main executable of the aircraft war game.
add_executable(aircraft ${AIRCRAFT_SOURCE_DIR}/main.cpp)
target_link_libraries(aircraft aw)

# Install settings.
set(CMAKE_INSTALL_PREFIX ${PROJECT_SOURCE_DIR}/install)
install(DIRECTORY include/ DESTINATION include)
install(TARGETS aw DESTINATION lib)

