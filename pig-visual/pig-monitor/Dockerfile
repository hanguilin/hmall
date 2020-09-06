FROM openjdk:8-jdk-alpine

MAINTAINER 1451633962@qq.com

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /pig-monitor

WORKDIR /pig-monitor

EXPOSE 5001

ADD ./target/pig-monitor.jar ./

CMD sleep 60;java -Djava.security.egd=file:/dev/./urandom -jar pig-monitor.jar
