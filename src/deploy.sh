#!/bin/bash

git pull origin main
./gradlew build
docker build -t adegasapi .
docker-compose up -d