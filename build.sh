#!/usr/bin/env bash

./clean.sh

echo "Starting build for exercise 1, this can take a while..."
mvn clean >/dev/null
mvn clean compile assembly:single -Dexercise=Ex1 >/dev/null
mv target/Ex1-jar-with-dependencies.jar . >/dev/null

echo "Starting build for exercise 2, this can take a while..."
mvn clean >/dev/null
mvn clean compile assembly:single -Dexercise=Ex2 >/dev/null
mv target/Ex2-jar-with-dependencies.jar . >/dev/null

echo "Cleaning build folder"
mvn clean >/dev/null

echo "Finishing build..."