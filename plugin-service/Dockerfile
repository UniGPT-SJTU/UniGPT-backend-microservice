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


# 设置代理环境变量
ENV http_proxy="http://192.168.0.9:7890"
ENV https_proxy="http://192.168.0.9:7890"

RUN apt-get update && apt-get install -y docker docker.io && apt-get clean && rm -rf /var/lib/apt/lists/*

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]