#!/bin/sh
gradle clean makeJar
if [ $? -eq 0 ]; then
  echo "\nJar file created in distribution directory\n"
fi