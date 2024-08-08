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

# 设置代理环境变量
ENV HUAWEICLOUD_SDK_AK=C0HLZ2JBNJNX156VWRLU
ENV HUAWEICLOUD_SDK_SK=rVUbpBGPlVYdhti0H6BtwdTdKNTxxYNnoKTEeK5R

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]