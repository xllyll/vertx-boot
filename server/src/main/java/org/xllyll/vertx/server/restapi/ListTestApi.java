package org.xllyll.vertx.server.restapi;

import org.xllyll.vertx.boot.annotation.RequestBody;
import org.xllyll.vertx.boot.annotation.RequestMapping;
import org.xllyll.vertx.boot.annotation.RequestMethod;
import org.xllyll.vertx.boot.annotation.RestApi;
import org.xllyll.vertx.server.dto.BaseResponse;
import org.xllyll.vertx.server.dto.UserDto;

import java.util.Date;
import java.util.List;

@RestApi
public class ListTestApi {

    @RequestMapping(path={"/list/int"},method = RequestMethod.GET)
    public BaseResponse listPost2(List<Integer> ids) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(new Date());
        baseResponse.setCode(200);
        return baseResponse;
    }

}
