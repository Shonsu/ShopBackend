#!/bin/zsh
#mvn clean install -P docker
mvn compile package -P docker
docker rm shop-docker-v2
docker image rm shop-docker:v2
docker build -t shop-docker:v2 .
docker run -d --name shop-docker-v2 -p 8080:8080 -v /Users/shonsu/Documents/IdeaProjects/_nullpointerexception.pl/Kurs-WebApp/backend/data:/app/data shop-docker:v2

