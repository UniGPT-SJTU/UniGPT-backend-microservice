# UniGPT微服务架构后端
## 简介
本项目将原有的UniGPT后端拆分成了多个微服务(bot-service, chat-service, plugin-service, user-service)，不同微服务之间通过RESTful api通信（HTTP协议）。
## 如何运行
运行其他人开发的项目通常都不是一件容易的事，本文档尝试以最简洁易懂的方式描述如何运行本项目。
本文档适用的环境适用于Linux和MacOS， 对于Windows用户，仅配置环境变量步骤有所不同，可自行google。
### 数据库监听端口
本项目的所有数据库都运行在Docker容器中，在任何情况下，每个数据库容器都各自监听在唯一的端口：
| 数据库容器名称 | 数据库类型   | 端口  | 相关微服务 |
| ------------ | ---------- | ----- | ----- |
| user_db      | MySQL      | 3306  | user-service |
| bot_db       | MySQL      | 3307  | bot-service |
| chat_db      | MySQL      | 3308  | chat-service |
| postgresql_db| PostgreSQL | 5432  | chat-service |
### 本地运行单个微服务
在开发过程中，需要本地运行单个微服务后端，此时后端直接运行在操作系统上，与Docker无关，因此后端监听的端口为8080。
#### 运行数据库容器
按照上述的表格，首先启动微服务依赖的数据库容器。
以chat-service为例，依赖的数据库为chat_db和postgresql_db，在仓库根目录下，运行docker compose命令启动数据库容器：
```sh
docker compose up chat_db postgresql_db -d
```
注意，只需要启动微服务依赖的数据库即可，比如运行chat-service时，不需要运行bot_db。

#### 配置微服务所需的环境变量
根据每个微服务子目录下的`README.md`文件配置对应的环境变量，以chat-service为例。
在chat-service子目录下创建一个`.env`文件：
```sh
touch .env
```
按照README在里面写上环境变量：
```sh
export DB_URL="jdbc:mysql://localhost:3308/unigpt_chat"
export DB_USERNAME="unigpt"
export DB_PASSWORD="unigpt"

export OPENAI_API_BASE_URL="https://api.openai.com"
export OPENAI_API_KEY="sk-j101-s9eW29r08H38nPRyMia7T3BlbkFJom4ndaHHCjhWkRRp3lsG"
export CLAUDE_API_BASE_URL="https://api.claude-Plus.top"
export CLAUDE_API_KEY="sk-JAuS27IqZB15lJST6a76A0111e2d4eEb9d7aD0Bd34F20271"
export LLAMA_API_BASE_URL="https://xqtd520qidong.com"
export LLAMA_API_KEY="sk-hMdKUabqMiRM247Y2b23B02e8f484a9198D27cA2D66eAe4d"
export KIMI_API_BASE_URL="https://api.moonshot.cn"
export KIMI_API_KEY="sk-xn7ruJ2a0MYLAcheud6qVC87I6mx7b0wpFODccHCDW8oUWMg"

export POSTGRES_HOST="localhost"
export POSTGRES_PORT="5432"
export POSTGRES_DB="mydatabase"
export POSTGRES_USER="bleaves" 
export POSTGRES_PASSWORD="bleaves" 

export HTTP_PROXY_HOST="127.0.0.1" 
export HTTP_PROXY_PORT="7890" 

export USER_SERVICE_URL="http://localhost:8082"
export BOT_SERVICE_URL="http://localhost:8083"
```

注意两点即可：
- `DB_URL`中对应的3308端口即为上一步启动的chat_db监听端口。
- 由于只运行单个chat-service微服务，因此最后两个环境变量的值没有实际意义，但是一定要写，否则后端未找到环境变量`USER_SERVICE_URL`和`BOT_SERVICE_URL`无法启动。

将`.env`文件保存，使用`source`命令使环境变量在此终端中生效。
```sh
source .env
```
#### 启动后端
在启动了数据库并配置了环境变量后，在当前终端中运行后端：
```sh
./mvnw spring-boot:run 
```
此时，微服务后端应该能够**正常启动**，并监听在8080端口，使用apifox测试时请将环境切换为`本地后端`。

### 部署环境下运行多个微服务
在部署环境下（即服务器上），数据库和微服务后端都运行在Docker容器中，此时每个后端不再运行在8080端口，而是有对应的监听端口，具体如下：
| 微服务名称 | 端口 |
| ------------ | --- |
| user-service | 8082 |
| bot-service | 8083 |
| chat-service | 8084 |

#### 运行数据库容器
在仓库根目录使用docker compose命令启动所有数据库容器
```sh
docker compose up user_db bot_db chat_db postgresql_db -d
```
在保证所有数据库容器启动成功后，再进行下一步。

#### 运行微服务后端
使用Docker部署后端使得运行微服务变得非常地简单，既不需要安装复杂的依赖，也不需要配置环境变量（已经在docker-compose.yml配置好了），只需要在仓库根目录运行docker compose 命令启动所有微服务。
```sh
docker compose up user_service bot_service chat_service -d
```
其中，`-d`选项表示容器在后台运行，如果去掉`-d`选项，容器将在终端前台运行（阻塞终端）。
比如我只运行chat-service微服务，并在前台运行：
```sh
docker compose up chat_service
```


#### 停止运行的微服务后端
对于在**后台**运行的微服务后端，可以使用docker compose down停止容器运行。
停止该目录下`docker-compose.yml`指定的所有容器（后端+数据库）：
```sh
docker compose down
```
停止指定微服务
```sh
docker compose down chat_service
```
