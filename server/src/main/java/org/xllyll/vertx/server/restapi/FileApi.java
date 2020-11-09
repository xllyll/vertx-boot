package org.xllyll.vertx.server.restapi;

import io.vertx.ext.web.FileUpload;
import org.xllyll.vertx.boot.annotation.RequestMapping;
import org.xllyll.vertx.boot.annotation.RequestMethod;
import org.xllyll.vertx.boot.annotation.RestApi;
import org.xllyll.vertx.server.dto.BaseResponse;

@RestApi
public class FileApi {

    @RequestMapping(path = "/file/upload",method = RequestMethod.POST)
    public BaseResponse uploadFile(FileUpload file,FileUpload image){
        BaseResponse baseResponse = new BaseResponse();
        return baseResponse;
    }

}
