FROM openjdk:11-jdk-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar app.jar" ]
EXPOSE 8080