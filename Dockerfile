FROM debian:buster-slim
MAINTAINER Tech Team <tech-team@wethinkcode.co.za> 

RUN apt-get update && apt-get install -y make

COPY . /app
WORKDIR /app


EXPOSE 5000

ENTRYPOINT ["java", "-jar", "java-turtle-1.0-SNAPSHOT-jar-with-dependencies.jar"]
