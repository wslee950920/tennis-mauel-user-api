FROM openjdk:11-jre-slim

COPY build/libs/user-api.jar app.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "app.jar" ]
