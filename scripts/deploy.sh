#!/usr/bin/env bash

chmod +x mvnw
./mvnw clean package
java -jar ../target/bookingSite-0.0.1-SNAPSHOT.jar