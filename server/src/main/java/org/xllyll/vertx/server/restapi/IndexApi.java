package org.xllyll.vertx.server.restapi;

import com.alibaba.fastjson.JSON;
import org.xllyll.vertx.boot.annotation.RequestMapping;
import org.xllyll.vertx.boot.annotation.RequestMethod;
import org.xllyll.vertx.boot.annotation.RestApi;
import org.xllyll.vertx.server.dto.BaseResponse;
import org.xllyll.vertx.server.dto.UserDto;

import java.util.List;

@RestApi
public class IndexApi {

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
