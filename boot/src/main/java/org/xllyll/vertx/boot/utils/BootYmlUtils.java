package org.xllyll.vertx.boot.utils;

import org.yaml.snakeyaml.Yaml;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class BootYmlUtils {

  private static Map<String,Object> object;

  private static Map<String,Object> getBoot(){
    if (object==null){
      File file = new File("src/main/resources/boot.yml");
      InputStream input = null;
      try {
        input = new FileInputStream(file);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
        return null;
      }
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
