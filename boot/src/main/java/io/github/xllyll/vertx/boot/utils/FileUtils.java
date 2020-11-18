package io.github.xllyll.vertx.boot.utils;

import cn.hutool.core.io.resource.ResourceUtil;

public class FileUtils {

    public static void readBanner(){

        String string = ResourceUtil.readUtf8Str("root_banner.txt");

        if (string!=null){
            System.out.println(string);
        }

    }



}
