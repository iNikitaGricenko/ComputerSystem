FROM openjdk:17-alpine
ARG JAR_FILE=oauth2-server/target/*.jar
COPY ${JAR_FILE} oauth2-server.jar
ENTRYPOINT ["java", "-jar", "oauth2-server.jar"]