package org.xllyll.vertx.server.restapi;

import org.xllyll.vertx.boot.annotation.RequestBody;
import org.xllyll.vertx.boot.annotation.RequestMapping;
import org.xllyll.vertx.boot.annotation.RequestMethod;
import org.xllyll.vertx.boot.annotation.RestApi;
import org.xllyll.vertx.server.dto.BaseResponse;
import org.xllyll.vertx.server.dto.UserDto;

import java.math.BigDecimal;

@RestApi
public class TestApi {

    @RequestMapping(path={"/test","/test1"},method = RequestMethod.GET)
    public BaseResponse test(String name) {
        BaseResponse baseResponse = new BaseResponse();
        if (name==null){
           baseResponse.setCode(0);
            baseResponse.setMessage("没有参数");
        }
        baseResponse.setCode(200);
        return baseResponse;
    }

    @RequestMapping(path={"/test/post1"},method = RequestMethod.POST)
    public BaseResponse testPost1(String name, int age, BigDecimal value) {
        BaseResponse baseResponse = new BaseResponse();
        if (name==null){
            baseResponse.setCode(0);
            baseResponse.setMessage("没有参数");
        }
        baseResponse.setData(value);
        baseResponse.setCode(200);
        return baseResponse;
    }

    @RequestMapping(path={"/test/post2"},method = RequestMethod.POST)
    public BaseResponse testPost2(@RequestBody UserDto userDto) {
        BaseResponse baseResponse = new BaseResponse();
        if (userDto==null){
            baseResponse.setCode(0);
            baseResponse.setMessage("没有参数");
        }
        baseResponse.setData(userDto);
        baseResponse.setCode(200);
        return baseResponse;
    }
}
