FROM eclipse-temurin:25-jdk AS build
WORKDIR /app
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
RUN chmod +x ./gradlew
COPY src src
RUN ./gradlew bootJar -x test --no-daemon

FROM eclipse-temurin:25-jre
WORKDIR /app
ENV SERVER_PORT=8082
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]