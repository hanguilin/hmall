FROM openjdk:8-jdk-alpine

MAINTAINER 1451633962@qq.com

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /pig-upms

WORKDIR /pig-upms

EXPOSE 4000

ADD ./target/pig-upms-biz.jar ./

CMD sleep 60;java -Djava.security.egd=file:/dev/./urandom -jar pig-upms-biz.jar
