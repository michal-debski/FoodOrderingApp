FROM gradle:8.10-jdk21 AS build
WORKDIR /app
COPY . .
RUN ./gradlew bootJar --no-daemon
RUN ./gradlew bootJar
FROM amazoncorretto:21-alpine-jdk
WORKDIR /app

COPY --from=build /app/build/libs/*.jar /app/config-service.jar

EXPOSE 8888
ENTRYPOINT ["java", "-jar", "/app/config-service.jar"]