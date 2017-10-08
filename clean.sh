#!/usr/bin/env bash

echo "Starting clean..."
if [ -f *.jar ]; then
       rm *.jar
   fi
files=(data/output/*)
if [ -e "${files[0]}" ];
then
    rm data/output/*
fi
echo "Finishing clean..."
