FROM bde2020/spark-master:2.4.4-hadoop2.7
MAINTAINER Maotion FinTech Ltd.

RUN mkdir /code
WORKDIR /code

COPY . /code/