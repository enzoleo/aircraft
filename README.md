# Aircraft War
![Build Status](https://img.shields.io/travis/enzoleo/aircraft.svg?style=flat-square)
![C++ Version](https://img.shields.io/badge/C++-17-pink.svg?style=flat-square&logo=c%2B%2B)
![Java](https://img.shields.io/badge/Java-OpenJDK-orange.svg?style=flat-square&logo=java)
![Python](https://img.shields.io/badge/Python-3-blue.svg?style=flat-square&logo=python)
![License](https://img.shields.io/github/license/enzoleo/aircraft.svg?color=black&style=flat-square)
![Repo Size](https://img.shields.io/github/repo-size/enzoleo/aircraft.svg?style=flat-square)

Quick implementation of a simplified aircraft war game with a graphics user interface. This simple game is implemented in `C++`, `Java` and `python`.

## Quick Setup

Before running the game from the provided source files, you should quickly setup the environment, especially for the `java` implementation.

### C++

The `SDL2` package is used to build the graphical user interface. On any `Ubuntu` machine, install the development package of `SDL2` and `SDL2_image` using the command

```shell
sudo apt-get install libsdl2-dev
sudo apt-get install libsdl2-image-dev
```

On your `MacOS` machine, you can use `homebrew` to install these dependencies by

```shell
brew install sdl2
brew install sdl2_image
```

Now make a `build` directory and follow the `CMake` routine, then the binaries will be generated in the `build` directory. Or, you can directly execute `make` command under the project root directory, and then binaries will be generated in the `.build` directory (so that we can distinguish it from the one you make when working with `CMake`) automatically if everything goes fine.

### Java

The source files can be compiled on `Ubuntu 20.04` system. Some other versions may also suitable. For other operating systems, we do not have a check in detail but it should still work fine in a correct environment.

```shell
sudo apt-get update
sudo apt-get install openjdk-14-jdk
```

After executing this command in terminal, you should have already installed `OpenJDK` correctly. Check the `java` version by `java -version` and you will get the following output if everything goes fine:

```reStructuredText
openjdk version "14.0.2" 2020-07-14
OpenJDK Runtime Environment (build 14.0.2+12-Ubuntu-120.04)
OpenJDK 64-Bit Server VM (build 14.0.2+12-Ubuntu-120.04, mixed mode, sharing)
```

On your `MacOS` machine, refer to [AdoptOpenJDK](https://adoptopenjdk.net) for pre-built binaries.

Once you have successfully installed the development kits, simply execute `make java` in your terminal and everything will be built automatically. To start the game, execute `make run-java` command in your terminal. Quite easy!

### Python

The `python` packages you require

- [`scipy.stats`](https://pypi.org/project/scipy/). This library is an open-source software for science. We only require its `stat` module for generation of random boolean values according to a Bernoulli distribution. You can install this module via `pip` by executing command `pip3 install scipy`.
- [`pygame`](https://pypi.org/project/pygame/). This module is an open-source library to develop multimedia applications. We import this module to render the graphic user interface of the game. You can install this module via `pip` by executing command `pip3 install pygame`.
- [`point2d`](https://pypi.org/project/point2d/). The module mainly contains a class named `Point2d` that describes Cartesian coordinates of two-dimensional points. Please refer to [PyPI](https://pypi.org/project/point2d/) for more details. You can install this module via `pip` by executing command `pip3 install point2d`.

Once you have installed all the required packages, simply go into `python` directory and execute `python aircraft.py` to start the game.

#### A common Issue

Note that if you are using `MacOS` with Apple Silicon, you should use `homebrew` to install `scipy` package by `brew install scipy`. Besides of that, `pygame>=2.0.0` use `pkg-config` for dependency scan, so execute `brew install pkg-config` first. There may be an issue that you are not allowed to load `.png` images as `pygame.image.get_extended()` returns `False`. This is because the current version of `pygame` you are using tries to search a wrong path for `SDL2_image` and unfortunately it fails to find your installation. To resolve this issue, you may have to download the source files and add `/opt/homebrew/include` to the search path in its configuration script, then execute the `setup.py` script for a manual installation without `pip3`.
