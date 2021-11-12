FROM openjdk:11

COPY build/libs/user-0.0.1-SNAPSHOT.jar app.jar

ARG profile=dev
ENV PROFILE=$profile

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "app.jar", "--spring.config.location=/config/application.yml" ]
