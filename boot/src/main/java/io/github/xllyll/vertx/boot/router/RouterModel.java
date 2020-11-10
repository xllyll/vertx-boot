package io.github.xllyll.vertx.boot.router;

import io.github.xllyll.vertx.boot.annotation.RequestMethod;

import java.lang.reflect.Method;

public class RouterModel {

  private Class<?> mClass;

  private String path;

  private RequestMethod requestMethod;

  private Method handle;

  private ParameterModel[] parameterModels;

  public RouterModel(){

  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public RequestMethod getRequestMethod() {
    return requestMethod;
  }

  public void setRequestMethod(RequestMethod requestMethod) {
    this.requestMethod = requestMethod;
  }

  public Method getHandle() {
    return handle;
  }

  public void setHandle(Method handle) {
    this.handle = handle;
  }

  public Class<?> getmClass() {
    return mClass;
  }

  public void setmClass(Class<?> aClass) {
    this.mClass = aClass;
  }

  public ParameterModel[] getParameterModels() {
    return parameterModels;
  }

  public void setParameterModels(ParameterModel[] parameterModels) {
    this.parameterModels = parameterModels;
  }
}
