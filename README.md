# Aircraft War 
Quick implementation of a simplified aircraft war game with a graphics user interface. This simple game is implemented in `Java` and `python`.

## Quick Setup

Before running the game from the provided source files, you should quickly setup the environment, especially for the `java` implementation.

### Java

The source files can be compiled on `Ubuntu 20.04` system. For other operating systems, we do not have a check in detail but it should still work fine in a correct environment.

```shell
sudo apt-get update
sudo apt-get install openjdk-14-jdk
sudo apt-get install openjdk-14-jre
```

After executing this command in terminal, you should have already installed `OpenJDK` and `OpenJRE` correctly. Check the `java` version by `java -version` and you will get the following output if everything goes fine:

```reStructuredText
openjdk version "14.0.2" 2020-07-14
OpenJDK Runtime Environment (build 14.0.2+12-Ubuntu-120.04)
OpenJDK 64-Bit Server VM (build 14.0.2+12-Ubuntu-120.04, mixed mode, sharing)
```

Now you can use `javac` to compile the project. On `Ubuntu`, go to the root directory of this project and execute

```shell
javac -cp ./src src/aircraft/game/AircraftWar.java -d ./class
```

If everything goes fine, you can find a new directory `class` preserving the same structure as  `src`, but containing `.class` files after compilation. Execute the following command to run the game!

```shell
java -cp ./class aircraft.game.AircraftWar
```

If you are not satisfied with the complex and boring command line, an `Makefile` is provided to you. On `Ubuntu`, you need to install some useful tools first, by

```shell
sudo apt-get install build-essential
```

Then simply execute `make` in your terminal and everything will be built automatically. To start the game, execute `make run` command in your terminal. Quite easy!

### Python

The `python` packages you require

- [`scipy.stats`](https://pypi.org/project/scipy/). This library is an open-source software for science. We only require its `stat` module for generation of random boolean values according to a Bernoulli distribution. You can install this module via `pip` by executing command `pip3 install scipy`.
- [`pygame`](https://pypi.org/project/pygame/). This module is an open-source library to develop multimedia applications. We import this module to render the graphic user interface of the game. You can install this module via `pip` by executing command `pip3 install pygame`.
- [`point2d`](https://pypi.org/project/point2d/). The module mainly contains a class named `Point2d` that describes Cartesian coordinates of two-dimensional points. Please refer to [PyPI](https://pypi.org/project/point2d/) for more details. You can install this module via `pip` by executing command `pip3 install point2d`.

Once you have installed all the required packages, simply go into `python` directory and execute `python aircraft.py` to start the game.