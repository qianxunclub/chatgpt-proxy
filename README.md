# 配置
在 `application.yml` 文件添加`openaiKey`，  
或者在请求header中添加`Authorization:Bearer xxxxx `


# 部署
docker-compose部署：

```shell
cd chatgpt-proxy
# 构建
./build

# 启动
docker-compose up -d

# 查看日志
docker-compose logs --tail=800 -f chatgpt-proxy
```

swagger 访问：http://localhost:8080/swagger-ui/index.html

