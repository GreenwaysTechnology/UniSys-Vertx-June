package com.unisys.advanced.serviceproxy;

//service interface

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.*;
import io.vertx.example.util.Runner;
import io.vertx.serviceproxy.ServiceBinder;

@ProxyGen
@VertxGen
interface GreeterService {
  public static String ADDRESS = GreeterService.class.getName();

  //biz apis
  void sayHello(String name, Handler<AsyncResult<String>> handler);

  //api to create Service Proxy Object
  static GreeterService createProxy(Vertx vertx, String address) {
    return new GreeterServiceVertxEBProxy(vertx, address);
  }
}

class GreeterServiceImpl implements GreeterService {
  @Override
  public void sayHello(String name, Handler<AsyncResult<String>> handler) {
    handler.handle(Future.succeededFuture("Hello" + name));
  }
}

class HaiVerticle extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();
    //Call service
    GreeterService service = GreeterService.createProxy(vertx, GreeterService.ADDRESS);
    service.sayHello("Subramanian", ar -> {
        if(ar.succeeded()){
          System.out.println(ar.result());
        }
    });
  }
}
public class ServiceProxyMain extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(ServiceProxyMain.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    //Service Registeration
    GreeterService greeterService = new GreeterServiceImpl();
    new ServiceBinder(vertx).setAddress(GreeterService.ADDRESS).register(GreeterService.class, greeterService);

    //deploy
    vertx.deployVerticle(new HaiVerticle());

  }
}
