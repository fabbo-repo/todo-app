FROM docker.io/eclipse-temurin:21-jdk-alpine

ENV TZ=UTC
ENV SPRING_PROFILES_ACTIVE=prod

VOLUME /tmp

WORKDIR /app

COPY ./target/todo-app-api.jar /app

ENTRYPOINT ["java", "-jar", "todo-app-api.jar"]
