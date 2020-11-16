[![Maven central](https://maven-badges.herokuapp.com/maven-central/io.github.xllyll/vertx-boot/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.xllyll/vertx-boot)
[![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

# vertx-boot

vert.x http 服务器框架

引用

maven

```xml
<dependency>
  <groupId>io.github.xllyll</groupId>
  <artifactId>vertx-boot</artifactId>
  <version>0.0.2</version>
</dependency>
```

使用：

```java

import org.xllyll.vertx.boot.VertxApplication;

public class ServerApplication {

    public static void main(String[] args) {
        VertxApplication.run(ServerApplication.class);
    }

}


```
api

```java
@RestApi
public class IndexApi {

    @RequestMapping(path={"/index"},method = RequestMethod.GET)
    public BaseResponse index() {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData("Hello Vertx Boot");
        baseResponse.setMessage("操作成功");
        return baseResponse;
    }

    @RequestMapping(path={"/index/name"},method = RequestMethod.GET)
    public BaseResponse indexName(List<Integer> age) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage("操作成功");
        return baseResponse;
    }

    @RequestMapping(path="/index",method = RequestMethod.GET)
    public BaseResponse index(UserDto userDto) {

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage("操作成功");
        baseResponse.setData(userDto);

        return baseResponse;
    }

}
```
