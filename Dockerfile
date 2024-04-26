FROM openjdk:17
ARG JAR_FILE=built/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]