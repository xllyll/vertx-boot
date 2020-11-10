package io.github.xllyll.vertx.boot.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.github.xllyll.vertx.boot.handler.RequestHandler;
import io.github.xllyll.vertx.boot.router.RouterModel;
import io.github.xllyll.vertx.boot.utils.BootYmlUtils;
import io.github.xllyll.vertx.boot.utils.PackageScannerCore;

public class MainVerticle extends AbstractVerticle {

  private Logger logger = LoggerFactory.getLogger(MainVerticle.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    Integer port = BootYmlUtils.serverPort();


    // 实例化一个路由器出来，用来路由不同的rest接口
    Router router = Router.router(vertx);
    // 增加一个处理器，将请求的上下文信息，放到RoutingContext中
    router.route().handler(BodyHandler.create());

    if (PackageScannerCore.routerModels.values()!=null){
      for (RouterModel routerModel:PackageScannerCore.routerModels.values()){
        switch (routerModel.getRequestMethod()){
          case GET:
            router.get( routerModel.getPath()).blockingHandler(new RequestHandler());
            break;
          case POST:
            router.post(routerModel.getPath()).blockingHandler(new RequestHandler());
            break;
          case PUT:
            router.put( routerModel.getPath()).blockingHandler(new RequestHandler());
            break;
          case DELETE:
            router.delete(routerModel.getPath()).blockingHandler(new RequestHandler());
            break;
          default:
            break;
        }
      }
    }

    vertx.createHttpServer().requestHandler(router).listen(port, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        logger.info("HTTP server started on port " + port);
      } else {
        startPromise.fail(http.cause());
        logger.error(http.cause());
      }
    });

  }

//  // 处理请求的handler
//  private void handleRequest(RoutingContext context) {
//    String urlPath = context.normalisedPath();
//    logger.info("URL:"+urlPath);
//    RouterModel routerModel = PackageScannerCore.routerModels.get(urlPath);
//
//    Class<?> clazz = routerModel.getmClass();
//    Method method = routerModel.getHandle();
//
//    String[] parameterNames = routerModel.getParameterNames();
//    Object[] parameters = new Object[parameterNames.length];
//
//    // 从上下文获取请求参数，类似于从httprequest中获取parameter一样
////    String param1 = context.request().getParam("param1");
////    String param2 = context.request().getParam("param2");
////
////    if (isBlank(param1) || isBlank(param2)) {
////      // 如果参数空，交由httpserver提供默认的400错误界面
////      context.response().setStatusCode(400).end();
////    }
//
//
//    Object responseObjc = null;
//    Object objc1 = null;
//    try {
//      objc1 = clazz.newInstance();
//    } catch (InstantiationException e) {
//      e.printStackTrace();
//    } catch (IllegalAccessException e) {
//      e.printStackTrace();
//    }
//
//    try {
//      if (parameterNames.length>0){
//
//        for (int i = 0; i < parameterNames.length;i++){
//          String param = context.request().getParam(parameterNames[i]);
//          parameters[i] = param;
//        }
//        System.out.println("BEGIN:"+new Date().getTime()+"");
//        responseObjc = method.invoke(objc1,parameterNames);
//        System.out.println(" END :"+new Date().getTime()+"");
//      }else{
//        responseObjc = method.invoke(objc1);
//      }
//    } catch (IllegalAccessException e) {
//      e.printStackTrace();
//    } catch (InvocationTargetException e) {
//      e.printStackTrace();
//    }
//
//    JsonObject obj = new JsonObject();
//    obj.put("method", "post").put("param1", 2).put("param2", 2);
//
//    // 申明response类型为json格式，结束response并且输出json字符串
//    context.response().putHeader("content-type", "application/json")
//      .end(obj.encodePrettily());
//
//  }

}
