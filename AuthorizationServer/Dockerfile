FROM openjdk:17-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} authorization-server.jar
ENTRYPOINT ["java", "-jar", "authorization-server.jar"]