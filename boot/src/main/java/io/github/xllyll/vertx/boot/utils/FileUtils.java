package io.github.xllyll.vertx.boot.utils;

import io.netty.util.internal.ResourcesUtil;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class FileUtils {

    public static void readBanner(){

        String filePath = FileUtils.class.getClassLoader().getResource("root_banner.txt").getFile();
        File file = new File(filePath);

        String string = fileToString(file);

        if (string!=null){
            System.out.println(string);
        }

    }

    public static String readConfigFile(String cfgFile) {

        try {
            InputStream in = FileUtils.class.getClassLoader().getResourceAsStream(cfgFile);
            BufferedReader br=new BufferedReader(new InputStreamReader(in));
            StringBuilder sb=new StringBuilder();
            String line="";
            while((line=br.readLine())!=null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String fileToString(File file){
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(System.lineSeparator()+s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }


}
