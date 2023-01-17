FROM arm64v8/eclipse-temurin:17 as build
LABEL maintainer="Shonsu"
RUN apt-get update -y

ARG JAR_FILE=target/shop*.jar
COPY ${JAR_FILE} /app/shop.jar
WORKDIR /app

ENTRYPOINT ["java", "-jar", "shop.jar"]
