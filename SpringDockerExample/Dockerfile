FROM java:8-jre
MAINTAINER LiLei<lilei931031@gmail.com>

VOLUME /tmp
ADD ./target/spring-docker-example-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT [ "java", "-jar", "/app.jar" ]

EXPOSE 8080