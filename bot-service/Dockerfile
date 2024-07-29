FROM maven:3.9.5 AS build
WORKDIR /app
COPY pom.xml .
COPY ./.mvn/wrapper/settings.xml /root/.m2/settings.xml
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]