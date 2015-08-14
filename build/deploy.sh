#!/bin/bash

PROJECT_HOME="$(cd "`dirname "$0"`"/..; pwd)"
cd ${PROJECT_HOME}

VERSION=` grep "version" version.sbt |awk -F":=" '{ print $2 }' |sed -e 's/^[[:space:]]*//'|tr -d '"' `
CONTAINER_NAME=web-sample-${VERSION}

# change this to private registry host if need
REGISTRY_HOST=localhost:5000

# remove previous container
echo "stop and remove container: ${CONTAINER_NAME} ..."
docker rm -f ${CONTAINER_NAME}

# pull 
echo "pull image: ${REGISTRY_HOST}/web-sample:${VERSION}..."
docker pull ${REGISTRY_HOST}/web-sample:${VERSION}

# run
echo "run..." 
docker run -d --name ${CONTAINER_NAME} --net=host ${REGISTRY_HOST}/web-sample:${VERSION} \
java -jar web-sample-assembly-${VERSION}.jar