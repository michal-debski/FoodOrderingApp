FROM gradle:8.10-jdk21 AS build
WORKDIR /app

COPY . .
RUN ./gradlew bootJar
FROM amazoncorretto:21-alpine-jdk
WORKDIR /app

COPY --from=build /app/build/libs/*.jar /app/discovery-service.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/discovery-service.jar"]