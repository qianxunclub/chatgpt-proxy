# 该镜像需要依赖的基础镜像
FROM openjdk:18.0.2
# 将当前目录下的jar包复制到docker容器的/目录下
ADD target/chatgpt-proxy-0.0.1-SNAPSHOT.jar /app/app.jar
# 指定docker容器启动时运行jar包
ENTRYPOINT ["/bin/bash","-cx","java -server -Djava.awt.headless=true -XX:-OmitStackTraceInFastThrow -Djava.security.egd=file:/dev/./urandom ${ENABLE_DEBUG:+ -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5006} $JAVA_OPTS -jar /app/app.jar"]