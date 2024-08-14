# UniGPT微服务架构后端
## 简介
本项目将原有的UniGPT后端拆分成了多个微服务(bot-service, chat-service, plugin-service, user-service)，不同微服务之间通过RESTful api通信（HTTP协议）。
各微服务的主要功能如下：
- bot-service: 机器人信息的增删改查
- chat-service: 对话信息的增删改查和聊天
- user-service: 用户信息的增删改查
- plugin-service: 插件信息的增删改查


## 如何运行
运行其他人开发的项目通常都不是一件容易的事，本文档尝试以最简洁易懂的方式描述如何运行本项目。
#### 操作系统
适用于Windows, MacOS, Linux。
#### 前置条件
按照[官方文档](https://docs.docker.com/engine/install/)安装最新版本的Docker。
#### 运行数据库容器
在仓库根目录使用docker compose命令启动所有数据库容器
```sh
docker compose up user_db bot_db chat_db plugin_db postgresql_db -d
```
在保证所有数据库容器启动成功后，再进行下一步。

#### 运行clash服务
clash代理的作用是使得chat-service可以访问到ChatGPT api，不影响其他微服务。
```sh
docker compose up clash -d 
```

#### 运行Redis服务
如果不启动redis，bot-service的部分功能无法使用，不影响其他微服务。
```sh
docker compose up redis -d
```

#### 运行微服务后端
在仓库根目录运行docker compose 命令启动所有微服务。
```sh
docker compose up user_service bot_service chat_service plugin_service -d
```
`-d`选项表示容器在后台运行，如果去掉`-d`选项，容器将在终端前台运行（阻塞终端）。
比如我只运行chat-service微服务，并在前台运行：
```sh
docker compose up chat_service
```
如果某个微服务的代码或依赖发生改变，需要重新构建Docker镜像，可以加上`--build`选项。
比如我修改了chat-service的代码，需要重新构建镜像并运行chat-service服务：
```sh
docker compose up chat_service --build
```

#### 停止运行的微服务后端
对于在**后台**运行的微服务后端，可以使用docker compose down停止容器运行。
停止该目录下`docker-compose.yml`指定的所有容器（后端+数据库）：
```sh
docker compose down
```
