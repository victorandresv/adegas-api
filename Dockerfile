FROM eclipse-temurin:21 AS build
COPY . .
RUN ./gradlew build
COPY build/libs/*.jar app.jar

FROM eclipse-temurin:21-jre
COPY --from=build /app.jar /app.jar
EXPOSE 8020
ENTRYPOINT ["java","-jar","/app.jar"]