package io.github.xllyll.vertx.boot.utils;

import cn.hutool.core.io.resource.ResourceUtil;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

public class BootYmlUtils {

  private static Map<String,Object> object;

  private static Map<String,Object> getBoot(){
    if (object==null){
      String input = ResourceUtil.readUtf8Str("boot.yml");
      Yaml yaml = new Yaml();
      object = (Map<String, Object>) yaml.load(input);
    }
    return object;
  }

  public static Integer serverPort(){
    Integer port = 8080;
    if (getBoot()!=null){
      LinkedHashMap server = (LinkedHashMap) object.get("server");
      if (server!=null){
        Integer server_port = (Integer) server.get("port");
        if (server_port!=null){
          port = server_port;
        }
      }
    }
    return port;
  }
}
