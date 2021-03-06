#!/usr/bin/env bash

./clean.sh

echo "Starting build for exercise 1, this can take a while (especially if this is the first time you run it!)..."
mvn clean >/dev/null
mvn clean compile assembly:single -Dexercise=Ex1 >/dev/null
mv target/Ex1-jar-with-dependencies.jar . >/dev/null

echo "Starting build for exercise 2, this can take a while..."
mvn clean >/dev/null
mvn clean compile assembly:single -Dexercise=Ex2 >/dev/null
mv target/Ex2-jar-with-dependencies.jar . >/dev/null

echo "Starting build for exercise 2 (local), this can take a while..."
mvn clean >/dev/null
mvn clean compile assembly:single -Dexercise=Ex2Local >/dev/null
mv target/Ex2Local-jar-with-dependencies.jar . >/dev/null

echo "Starting build for exercise 3, this can take a while..."
mvn clean >/dev/null
mvn clean compile assembly:single -Dexercise=Ex3 >/dev/null
mv target/Ex3-jar-with-dependencies.jar . >/dev/null

echo "Starting build for exercise 4, this can take a while..."
mvn clean >/dev/null
mvn clean compile assembly:single -Dexercise=Ex4 >/dev/null
mv target/Ex4-jar-with-dependencies.jar . >/dev/null

echo "Cleaning build folder"
mvn clean >/dev/null

echo "Finishing build..."