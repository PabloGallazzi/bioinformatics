#!/usr/bin/env bash

./clean.sh
echo "Starting build..."
mvn clean >/dev/null
mvn clean compile assembly:single -Dexercise=Ex1 >/dev/null
mv target/Ex1-jar-with-dependencies.jar . >/dev/null
mvn clean >/dev/null
echo "Finishing build..."