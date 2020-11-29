#!/bin/bash

cd anaplan-springboot
./mvnw package
cd ..
docker-compose build