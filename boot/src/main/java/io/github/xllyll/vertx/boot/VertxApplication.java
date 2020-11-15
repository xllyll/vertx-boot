package io.github.xllyll.vertx.boot;

import io.vertx.core.Vertx;
import io.github.xllyll.vertx.boot.utils.PackageScannerCore;
import io.github.xllyll.vertx.boot.verticle.MainVerticle;

public class VertxApplication {

  public static void run(Class aClass){

    PackageScannerCore.getInstance().init(aClass);

    Vertx vertx = Vertx.vertx();

    vertx.deployVerticle(MainVerticle.class.getName());

  }

}
