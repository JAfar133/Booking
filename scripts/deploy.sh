#!/usr/bin/env bash

chmod +x mvnw
./mvnw clean package

echo 'Copy files...'
scp -i