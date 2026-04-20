FROM amazoncorretto:21-alpine-jdk
COPY build/libs/api-gateway-*.jar api-gateway-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/api-gateway-0.0.1-SNAPSHOT.jar"]