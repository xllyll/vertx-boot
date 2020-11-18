package io.github.xllyll.vertx.boot.core;

import io.github.xllyll.vertx.boot.listener.ApplicationRunner;

public class VertxBootCore {

    private static VertxBootCore instance = null;

    private VertxBootCore() {

    }

    private Class mainClass;
    private Object mainApplication;

    public static VertxBootCore getInstance() {
        if (instance == null) {
            instance = new VertxBootCore();
        }
        return instance;
    }

    public void init(Class aClass){
        mainClass = aClass;
        try {
            mainApplication = mainClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 项目启动完成
     */
    public void applicationFinish(){
        Class<?>[] interfaceClassList = mainClass.getInterfaces();
        if (interfaceClassList!=null && interfaceClassList.length>0){
            for (int i = 0;i< interfaceClassList.length;i++){
                Class<?> clazz = interfaceClassList[i];
                if (clazz.isAssignableFrom(ApplicationRunner.class)){
                    ApplicationRunner applicationRunner = (ApplicationRunner)mainApplication;
                    try {
                        applicationRunner.run();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                }
            }
        }
    }
}
