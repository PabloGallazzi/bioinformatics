#!/usr/bin/env bash

echo "Checking MAVEN"
mvn -version | head -1 | tr " " "\n" | head -3 | tail -1

if [ $? -ne 0 ]; then
    echo "You don't have maven installed"
    exit 100
fi

mvnVersion=$(mvn -version | head -1 | tr " " "\n" | head -3 | tail -1)
if [[ "$mvnVersion" < "3" ]]; then
    echo "Your maven version is lower than 3"
    exit 100
fi

echo "Checking JAVA"
java -version 2>&1 | awk -F '"' '/version/ {print $2}'

if [ $? -ne 0 ]; then
    echo "You don't have java installed"
    exit 100
fi

javaVersion=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}')
if [[ "$javaVersion" < "1.8" ]]; then
    echo "Your java version is lower than 8"
    exit 100
fi

echo "Checking GZIP"
gunzip --version | head -1

if [ $? -ne 0 ]; then
    echo "You don't have gunzip installed"
    exit 100
fi

echo "Checking WGET"
wget --version | head -1

if [ $? -ne 0 ]; then
    echo "You don't have wget installed"
    exit 100
fi