package io.github.xllyll.vertx.boot;

import io.github.xllyll.vertx.boot.utils.FileUtils;
import io.vertx.core.Vertx;
import io.github.xllyll.vertx.boot.utils.PackageScannerCore;
import io.github.xllyll.vertx.boot.verticle.MainVerticle;

public class VertxApplication {

  public static void run(Class aClass){

    FileUtils.readBanner();

    PackageScannerCore.getInstance().init(aClass);

    Vertx vertx = Vertx.vertx();

    vertx.deployVerticle(MainVerticle.class.getName());

  }

}
