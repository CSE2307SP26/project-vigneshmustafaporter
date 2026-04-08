#!/bin/bash

set -e

mkdir -p bin
javac -d bin src/main/*.java
java -cp bin main.MainMenu
