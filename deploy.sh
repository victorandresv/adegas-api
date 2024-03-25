#!/bin/bash

OUTPUT=$(git pull origin main)
if grep -q "Already up to date" OUTPUT then
  echo "OK"
fi
#./gradlew build
#docker build -t adegasapi .
#docker-compose up -d