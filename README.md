# Aircraft War 
Quick implementation of a simplified aircraft war game with a graphics user interface. This simple game is implemented in `Java` and `python`.

## Quick Setup

Before running the game from the provided source files, you should quickly setup the environment, especially for the `java` implementation.

### Java

The source files can be compiled on `Ubuntu 20.04` system. For other operating systems, we do not have a check in detail but it should still work fine in a correct environment.

```shell
sudo apt update
sudo apt install openjdk-14-jdk
sudo apt install openjdk-14-jre
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



