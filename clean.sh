#!/usr/bin/env bash

echo "Starting clean..."

jars=(*.jar)
if [ -e "${jars[0]}" ];
then
    rm *.jar
fi

files=(data/output/*)
if [ -e "${files[0]}" ];
then
    rm data/output/*
fi

echo "Finishing clean..."
