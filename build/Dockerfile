FROM mylesjao/ubuntu1404-java8:v0.1

MAINTAINER Myles Jao <mrlau1@gmail.com>

RUN mkdir -p /opt/app

# add assembly jar
ADD ./web-sample.tar.gz /opt/app/

# set working dir
WORKDIR /opt/app/