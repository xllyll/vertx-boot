package io.github.xllyll.vertx.boot.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {

  String[] path() default {};

  RequestMethod[] method() default {};
}
