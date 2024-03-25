FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
EXPOSE 8020
ARG JAR_FILE
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]