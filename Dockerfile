FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
EXPOSE 8020
CMD ./gradlew build
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]