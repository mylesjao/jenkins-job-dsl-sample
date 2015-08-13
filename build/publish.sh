#!/bin/bash

PROJECT_HOME="$(cd "`dirname "$0"`"/..; pwd)"
cd ${PROJECT_HOME}

VERSION=` grep "version" version.sbt |awk -F":=" '{ print $2 }' |sed -e 's/^[[:space:]]*//'|tr -d '"' `

# change this to private registry host if need
REGISTRY_HOST=mylesjao

# push to docker hub
docker push ${REGISTRY_HOST}/web-sample:${VERSION}