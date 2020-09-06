FROM openjdk:8-jdk-alpine

MAINTAINER 1451633962@qq.com

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /pig-register

WORKDIR /pig-register

EXPOSE 8848

ADD ./target/pig-register.jar ./

CMD sleep 30;java -Djava.security.egd=file:/dev/./urandom -jar pig-register.jar


