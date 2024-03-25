  GNU nano 5.4                                                                                   deploy.sh                                                                                             
#!/bin/bash

OUTPUT=$(git pull origin main)
SEARCH='Already up to date'
if [[ "$OUTPUT" != *"$SEARCH"* ]]; then
  ./gradlew build
  docker build -t adegasapi .
  docker-compose up -d
  echo "Deploy Done"
fi