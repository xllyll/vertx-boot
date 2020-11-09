package org.xllyll.vertx.server.utils;

import java.lang.reflect.Method;

public class TestModel {

    public String getMoney(String name,Integer age,String phone){
        System.out.println("=======");
        return "Test";
    }
    public static void main(String[] args) throws Exception {
        Class<?> userClass = Class.forName("org.xllyll.vertx.server.restapi.TestApi");
        Object userEntity = userClass.newInstance();

        Object[] ps = new Object[3];
        ps[0] = "show";
        ps[1] = 12;
        ps[2] = null;

        //第二种方法,（无参的示例：借钱）
        System.out.println("第二次借钱：");
        Method method = userClass.getDeclaredMethods()[0];
        Object money2 = method.invoke(userEntity,ps);//调用借钱方法，得到返回值
        System.out.println("实际拿到钱为：" + money2);
    }
}
