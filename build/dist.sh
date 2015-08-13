#!/bin/bash
PROJECT_HOME="$(cd "`dirname "$0"`"/..; pwd)"
cd ${PROJECT_HOME}

pwd

VERSION=` grep "version" version.sbt |awk -F":=" '{ print $2 }' |sed -e 's/^[[:space:]]*//'|tr -d '"' `

# docker build
ASSEMBLY="${PROJECT_HOME}/dist/web-sample.tar.gz"
if [ -f "${ASSEMBLY}" ]
then
	echo "build docker images..."
	cp build/Dockerfile dist/
	docker build -t mylesjao/web-sample:${VERSION} dist/
else
	echo "${ASSEMBLY} not found. Please run build.sh first"
fi