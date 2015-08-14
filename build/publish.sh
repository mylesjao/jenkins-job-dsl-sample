#!/bin/bash

PROJECT_HOME="$(cd "`dirname "$0"`"/..; pwd)"
cd ${PROJECT_HOME}

VERSION=` grep "version" version.sbt |awk -F":=" '{ print $2 }' |sed -e 's/^[[:space:]]*//'|tr -d '"' `

# change this to private registry host if need
REGISTRY_HOST=localhost:5000

# push to docker registry
docker tag web-sample:${VERSION} ${REGISTRY_HOST}/web-sample:${VERSION}
docker push ${REGISTRY_HOST}/web-sample:${VERSION}