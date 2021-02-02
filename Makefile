# Default java compiler.
JC = javac
JAVA = java
JFLAGS = -g

.SUFFIXES: .java .class

# Code structure. This project is relatively small, so actually we don't care
# too much about this retrieving.
AIRCRAFT_CLASS_PATH = src
AIRCRAFT_SRC_PACKAGE = $(AIRCRAFT_CLASS_PATH)/aircraft
AIRCRAFT_SRC_DIR = $(AIRCRAFT_SRC_PACKAGE)/game
AIRCRAFT_BINARY_DIR = class

# The source we are going to build. Note that there are a lot of java files
# besides of the main one. However, there exists an inner connection, so
# we don't need to build them separately.
CLASSES = $(AIRCRAFT_SRC_DIR)/AircraftWar.java

# Default make target entry.
default: classes

# Suffix replacement within a macro.
classes: $(CLASSES:.java=.class)

# Target entry for creating .class files from .java files.
# The classes are required by classes target, which will be built according
# to the dependency of default target.
.java.class:
	@mkdir -p $(AIRCRAFT_BINARY_DIR);
	$(JC) $(JFLAGS) -cp $(AIRCRAFT_CLASS_PATH) $(CLASSES) -d $(AIRCRAFT_BINARY_DIR)

run:
	$(JAVA) -cp $(AIRCRAFT_BINARY_DIR) aircraft.game.AircraftWar

clean:
	@$(RM) -r $(AIRCRAFT_BINARY_DIR);

