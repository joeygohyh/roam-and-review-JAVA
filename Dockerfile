##build angular
FROM node:22 AS ng-builder

##install angular
RUN npm i -g @angular/cli@17.3.8

WORKDIR /frontend

COPY frontend/package*.json .
COPY frontend/angular.json .
COPY frontend/tsconfig*.json .
COPY frontend/ngsw-config.json .
COPY frontend/src src

##install modules
##can run npm i or ci
# RUN npm ci && ng build
RUN npm ci --force && ng build

## IF FIRST ONE SUCCESS, RUN SECOND, IF FIRST FAILS DONT RUN SECOND
#or
# RUN npm ci 
# RUN ng build


# /ngapp/dist/frontend/browser/*

# Starting with this Linux server
# FROM maven:3-eclipse-temurin-21 AS sb-builder
FROM openjdk:21 AS sb-builder

## Build the application
# Create a directory call /sbapp
# go into the directory cd /app
WORKDIR /backend

# everything after this is in /sbapp


# COPY --from=ng-builder /frontend/dist/frontend/browser/ src/main/resources/static

COPY backend/mvnw .
# COPY backend/mvnw.cmd .
COPY backend/pom.xml .
COPY backend/.mvn .mvn
COPY backend/src src
#copy angular files to sb
COPY --from=ng-builder /frontend/dist/frontend/browser/ src/main/resources/static

# Build the application
##produces file in target
RUN ./mvnw package -Dmaven.test.skip=true

#run container

FROM openjdk:21

WORKDIR /app 

COPY --from=sb-builder /backend/target/backend-0.0.1-SNAPSHOT.jar app.jar

## Run the application
# Define environment variable 
ENV PORT=8080 S3_KEY_SECRET=abc S3_KEY_ACCESS=abc 

# Expose the port
EXPOSE ${PORT}

# Run the program
ENTRYPOINT SERVER_PORT=${PORT} java -jar app.jar


# docker run -d -e GIPHY+KEY=$

# echo $GIPHY_KEY