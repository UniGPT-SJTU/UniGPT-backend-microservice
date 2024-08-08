FROM maven:3.9.5 AS build
WORKDIR /app
COPY pom.xml .
COPY ./.mvn/wrapper/settings.xml /root/.m2/settings.xml
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app

# 设置代理环境变量
ENV http_proxy="http://192.168.0.9:7890"
ENV https_proxy="http://192.168.0.9:7890"

RUN apt-get update && apt-get install -y curl && apt-get clean && rm -rf /var/lib/apt/lists/*
RUN curl -sSL https://cn-north-4-hdn-koocli.obs.cn-north-4.myhuaweicloud.com/cli/latest/hcloud_install.sh -o ./hcloud_install.sh && bash ./hcloud_install.sh -y
RUN hcloud configure set --cli-access-key="3FY9DIXH8CPZHLFHL9RF"  --cli-secret-key="7YDUBsXjojRjD9NUoTYU7BBkLkf5DjlaLesABjDq"

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]