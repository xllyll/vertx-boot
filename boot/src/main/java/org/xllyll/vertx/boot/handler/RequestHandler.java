package org.xllyll.vertx.boot.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.xllyll.vertx.boot.annotation.RequestBody;
import org.xllyll.vertx.boot.router.ParameterModel;
import org.xllyll.vertx.boot.router.RouterModel;
import org.xllyll.vertx.boot.utils.PackageScannerCore;

import java.util.Map.Entry;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class RequestHandler implements Handler<RoutingContext> {

  private Logger logger = Logger.getLogger(RequestHandler.class.getName());

  private HashMap<RoutingContext,Integer> contextIntegerHashMap = new HashMap<>();

  @Override
  public void handle(RoutingContext context) {
    String urlPath = context.normalisedPath();
    logger.info("URL:"+urlPath);
    RouterModel routerModel = PackageScannerCore.routerModels.get(urlPath);

    Class<?> clazz = routerModel.getmClass();
    Method method = routerModel.getHandle();
    Class<?>[] parameterTypes = method.getParameterTypes();
    ParameterModel[] parameterModels = routerModel.getParameterModels();
    Object[] parameters = null;
    if (parameterModels!=null){
      parameters = new Object[parameterModels.length];
    }

    Object responseObjc = null;
    Object objc1 = null;
    try {
      objc1 = clazz.newInstance();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }

    if (parameterModels!=null && parameterModels.length>0){
      for (int i = 0; i < parameterModels.length;i++){
        ParameterModel parameterModel = parameterModels[i];
        Class<?> aClass = parameterModel.getType();
        String param = context.request().getParam(parameterModels[i].getName());
        if(aClass.isPrimitive()){
          //基础数据类型：int ,float,double,long,boolean
          buildPrimitiveValue(context,method,objc1,param,parameters,i,aClass);
        }else if (aClass.isAssignableFrom(String.class)){
          //字符串类型
          String value = param;
          buildParameters(context,method,objc1,parameters,i,value);
        }else if (aClass.isAssignableFrom(Double.class)){
          Double value = Double.valueOf(param);
          buildParameters(context,method,objc1,parameters,i,value);
        }else if (aClass.isAssignableFrom(Float.class)){
          Float value = Float.valueOf(param);
          buildParameters(context,method,objc1,parameters,i,value);
        }else if (aClass.isAssignableFrom(Integer.class)){
          Integer value = Integer.valueOf(param);
          buildParameters(context,method,objc1,parameters,i,value);
        }else if (aClass.isAssignableFrom(Long.class)){
          Long value = Long.valueOf(param);
          buildParameters(context,method,objc1,parameters,i,value);
        }else if (aClass.isAssignableFrom(Boolean.class)){
          Boolean value = Boolean.valueOf(param);
          buildParameters(context,method,objc1,parameters,i,value);
        }else if (aClass.isAssignableFrom(BigDecimal.class)){
          BigDecimal value = new BigDecimal(param);
          buildParameters(context,method,objc1,parameters,i,value);
        }else if (aClass.isAssignableFrom(List.class)){
          //TODO: MARK: 数组处理
//          parameters[i] = new List<>(param) {};
//          buildParameters(context,method,objc1,parameters,i,value);
        }else {
          //其他数据类型
          RequestBody requestBody = parameterModel.getParameter().getAnnotation(RequestBody.class);
          if (requestBody!=null){
            //JSON
            buildBodyValue(context,method,objc1,parameters,i,aClass);
          }else{
            //FROM
            HashMap o = new HashMap();
            if (!context.request().params().isEmpty()){
              for (Entry<String, String> empty :context.request().params().entries()){
                o.put(empty.getKey(),empty.getValue());
              }
            }
            String jsonStr = JSONObject.toJSONString(o);
            Object value = JSON.parseObject(jsonStr,aClass);
            buildParameters(context,method,objc1,parameters,i,value);
          }
        }

      }

    }else{
      buildParameters(context,method,objc1,null,0,null);
    }

  }

  /**
   * 处理基础数据
   * @param context
   * @param method
   * @param objc1
   * @param param
   * @param parameters
   * @param i
   * @param aClass
   */
  private void buildPrimitiveValue(RoutingContext context,Method method,Object objc1,String param,Object[] parameters,int i,Class<?> aClass){

    if (aClass.getName().equals("int")){
      int value = Integer.valueOf(param).intValue();
      buildParameters(context,method,objc1,parameters,i,value);
    }else if (aClass.getName().equals("long")){
      long value = Long.valueOf(param).longValue();
      buildParameters(context,method,objc1,parameters,i,value);
    }else if (aClass.getName().equals("float")){
      float value = Float.valueOf(param).floatValue();
      buildParameters(context,method,objc1,parameters,i,value);
    }else if (aClass.getName().equals("double")){
      double value = Double.valueOf(param).doubleValue();
      buildParameters(context,method,objc1,parameters,i,value);
    }else if (aClass.getName().equals("boolean")){
      boolean value = Boolean.valueOf(param).booleanValue();
      buildParameters(context,method,objc1,parameters,i,value);
    }
  }
  //处理JSON请求数据
  private void buildBodyValue(RoutingContext context,Method method,Object objc,Object[] parameters,int index,Class<?> aClass){

    context.request().bodyHandler(body->{

      String jsonStr = body.toJsonObject().toString();
      Object value = JSON.parseObject(jsonStr,aClass);
      buildParameters(context,method,objc,parameters,index,value);

    });

  }

  private void buildParameters(RoutingContext context,Method method,Object objc,Object[] parameters,int index,Object value){
    parameters[index] = value;
    Integer doIndex = contextIntegerHashMap.get(context);
    if (doIndex==null){
      doIndex=0;
    }
    doIndex++;
    contextIntegerHashMap.put(context,doIndex);

    if (parameters.length==doIndex){
      Object responseObjc = null;
      try {
        if (parameters!=null && parameters.length>0){
          responseObjc = method.invoke(objc,parameters);
        }else {
          responseObjc = method.invoke(objc);
        }
      }catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      }
      invoke(context,responseObjc);
    }
  }

  /**
   * 返回数据
   * @param context
   * @param responseObjc
   */
  private void invoke(RoutingContext context,Object responseObjc){
    String responseString = "";
    if (responseObjc!=null) {

      if (responseObjc instanceof String){
        responseString = (String) responseObjc;
      }else{
        responseString = JSONObject.toJSONString(responseObjc);
      }
    }
    contextIntegerHashMap.remove(context);
    // 申明response类型为json格式，结束response并且输出json字符串
    context.response().putHeader("content-type", "application/json")
      .end(responseString);
  }

}
