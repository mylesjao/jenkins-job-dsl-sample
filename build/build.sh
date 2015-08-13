#!/bin/bash

PROJECT_HOME="$(cd "`dirname "$0"`"/..; pwd)"
cd ${PROJECT_HOME}

VERSION=` grep "version" version.sbt |awk -F":=" '{ print $2 }' |sed -e 's/^[[:space:]]*//'|tr -d '"' `

# build and generate assembly jar with sbt
#sbt clean assembly

#clean dist dir
echo "remove dist directory..."
rm -rf dist/
mkdir dist

# create distributed tar
echo "build distributed tarball..."
tar zcvf dist/web-sample.tar.gz -C web-sample/target/scala-2.11/ web-sample-assembly-${VERSION}.jar