package org.xllyll.vertx.server.restapi;

import io.vertx.ext.web.FileUpload;
import io.github.xllyll.vertx.boot.annotation.RequestMapping;
import io.github.xllyll.vertx.boot.annotation.RequestMethod;
import io.github.xllyll.vertx.boot.annotation.RestApi;
import org.xllyll.vertx.server.dto.BaseResponse;

import java.util.List;

@RestApi
public class FileApi {

    @RequestMapping(path = "/file/upload",method = RequestMethod.POST)
    public BaseResponse uploadFile(FileUpload file,FileUpload image){
        BaseResponse baseResponse = new BaseResponse();
        return baseResponse;
    }

    @RequestMapping(path = "/file/uploads",method = RequestMethod.POST)
    public BaseResponse uploadFiles(List<FileUpload> file){
        BaseResponse baseResponse = new BaseResponse();
        return baseResponse;
    }

}
