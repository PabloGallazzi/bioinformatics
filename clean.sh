#!/usr/bin/env bash

echo "Starting clean..."

jars=(*.jar)
if [ -e "${jars[0]}" ];
then
    rm *.jar
fi

tars=(*.tar.gz)
if [ -e "${tars[0]}" ];
then
    rm *.tar.gz
fi

files=(data/output/*)
if [ -e "${files[0]}" ];
then
    rm data/output/*
fi

rm -rf bioinformatics

echo "Finishing clean..."
