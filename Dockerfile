FROM openjdk:11-jre-slim

COPY build/libs/TENNIS_MAUEL_USER_API-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "app.jar" ]
