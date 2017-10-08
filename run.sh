#!/usr/bin/env bash

./build.sh

echo "Starting run..."

java -jar Ex1-jar-with-dependencies.jar
java -jar Ex2-jar-with-dependencies.jar

echo "Finishing run..."

echo "You can verify results at data/output"