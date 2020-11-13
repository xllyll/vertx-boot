package io.github.xllyll.vertx.boot.router;

import java.lang.reflect.Parameter;

public class ParameterModel {

  private Parameter parameter;

  private String name;

  private Class<?> type;

  public Parameter getParameter() {
    return parameter;
  }

  public void setParameter(Parameter parameter) {
    this.parameter = parameter;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Class<?> getType() {
    return type;
  }

  public void setType(Class<?> type) {
    this.type = type;
  }
}
