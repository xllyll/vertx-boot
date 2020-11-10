package io.github.xllyll.vertx.boot.utils;

import org.objectweb.asm.*;

import io.github.xllyll.vertx.boot.annotation.RequestMapping;
import io.github.xllyll.vertx.boot.annotation.RequestMethod;
import io.github.xllyll.vertx.boot.router.ParameterModel;
import io.github.xllyll.vertx.boot.router.RouterModel;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;

public class PackageScannerCore {

  public static HashMap<String,RouterModel> routerModels = new HashMap<>();

  private static PackageScannerCore instance = null;

  private PackageScannerCore() {

  }

  private Class mainClass;

  public static PackageScannerCore getInstance() {
    if (instance == null) {
      instance = new PackageScannerCore();
    }
    return instance;
  }

  public void init(Class aClass){
    mainClass = aClass;
    buildScan();
  }

  private void buildScan(){
    String mainPackStr = mainClass.getPackage().getName();
    System.out.println("begin scan package:"+mainPackStr);
    routerModels.clear();
    new PackageScanner(){

      @Override
      public void dealClass(Class<?> klass) {
        System.out.println("====>"+klass.getName());
        Method methods[] = klass.getDeclaredMethods();
        if (methods!=null && methods.length>0){
          for (int i = 0 ; i < methods.length;i++){
            Method method = methods[i];
            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
            if (requestMapping!=null){
              String paths[] = requestMapping.path();
              RequestMethod requestMethods[] = requestMapping.method();
              for (int a = 0; a < paths.length ; a++){
                String path  = paths[a];
                RequestMethod requestMethod = requestMethods[0];
                if (requestMethods.length>1){
                  requestMethod = requestMethods[a];
                }
                System.out.println("method:"+requestMethod.name());
                System.out.println("path:"+path);
                RouterModel routerModel = new RouterModel();
                routerModel.setPath(path);
                routerModel.setRequestMethod(requestMethod);
                routerModel.setHandle(method);
                routerModel.setmClass(klass);

                ParameterModel[] parameterModels = PackageScannerCore.getParameterNamesByAsm5(klass,method);
                routerModel.setParameterModels(parameterModels);

                routerModels.put(path,routerModel);
              }

            }
          }
        }
      }
    }.packageScan(mainPackStr);
  }

  public static ParameterModel[] getParameterNamesByAsm5(Class<?> clazz,
                                                 final Method method) {

    final Class<?>[] parameterTypes = method.getParameterTypes();
    if (parameterTypes == null || parameterTypes.length == 0) {
      return null;
    }
    final Type[] types = new Type[parameterTypes.length];
    for (int i = 0; i < parameterTypes.length; i++) {
      types[i] = Type.getType(parameterTypes[i]);
    }
    final String[] parameterNames = new String[parameterTypes.length];

    final ParameterModel[] parameterModels = new ParameterModel[parameterTypes.length];

    try {

      // 读取HelloTest的字节码信息到ClassReader中
      ClassReader reader = new ClassReader(clazz.getName());
      ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
      //accept接收了一个ClassAdapter的子类，想要操作什么，就在子类实现什么
      reader.accept(new ClassAdapter(cw) {

        /**
         * 会遍历该类的所有方法，你可以对不需要操作的方法直接返回
         */
        @Override
        public MethodVisitor visitMethod(final int access, final String name, final String desc,
                                         final String signature, final String[] exceptions) {
          //不需要操作的方法，直接返回，注意不要返回null,会把该方法删掉
          if (!name.equals(method.getName())) {
            return (MethodVisitor) super.visitMethod(access, name, desc, signature, exceptions);
          }
          MethodVisitor v = (MethodVisitor) super.visitMethod(access, name, desc,
            signature, exceptions);
          /**
           *  遍历该方法信息，比如参数、注解等，这里我们要操作参数，所以实现了参数方法
           */
          return new MethodAdapter(v) {
            public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
              //如果是静态方法，第一个参数就是方法参数，非静态方法，则第一个参数是 this ,然后才是方法的参数
              // 非静态成员方法的第一个参数是this
              if (Modifier.isStatic(method.getModifiers())) {
                parameterNames[index] = name;
              } else if (index > 0) {
                if (index <= parameterNames.length){
                  parameterNames[index - 1] = name;
                }
              }
            }
          };
        }
      }, 0);

    } catch (IOException e) {
      e.printStackTrace();
    }
    if (parameterNames!=null && parameterNames.length>0){
      for (int i = 0 ; i < parameterNames.length;i++){
        ParameterModel parameterModel = new ParameterModel();
        parameterModel.setParameter(method.getParameters()[i]);
        parameterModel.setName(parameterNames[i]);
        parameterModel.setType(parameterTypes[i]);
        parameterModels[i] = parameterModel;
      }
    }
    return parameterModels;
  }

}
