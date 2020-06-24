package com.unisys.vertx.handlers.promise;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.example.util.Runner;


class HelloMessageCompose extends AbstractVerticle {


  public Future<String> sayWorld() {
    System.out.println("Sayworld is called");
    Promise promise = Promise.promise();
    promise.complete("World");
    return promise.future();
  }

  public Future<String> sayHello(String world) {
    System.out.println("SayHello is called");
    Promise promise = Promise.promise();
    promise.complete("Hello" + world);
    return promise.future();
  }

  public Future<String> sayError() {
    System.out.println("say Error is called");
    Promise promise = Promise.promise();
    promise.fail("failure message");
    return promise.future();
  }

  @Override
  public void start() throws Exception {
    super.start();
//    sayWorld().onComplete(ar -> {
//      if (ar.succeeded()) {
//        sayHello(ar.result()).onComplete(h -> {
//          System.out.println(h.result());
//        });
//      }
//    });
//    sayError().compose(s -> sayWorld()).compose(m -> sayHello(m)).onComplete(ar -> {
//      if (ar.succeeded()) {
//        System.out.println(ar.result());
//      } else {
//        System.out.println(ar.cause().getMessage());
//      }
//    });

    sayWorld().compose(m -> sayHello(m)).onComplete(ar -> {
      if (ar.succeeded()) {
        System.out.println(ar.result());
      } else {
        System.out.println(ar.cause().getMessage());
      }
    });

    sayWorld().compose(s -> sayError()).compose(m -> sayHello(m)).onComplete(ar -> {
      if (ar.succeeded()) {
        System.out.println(ar.result());
      } else {
        System.out.println(ar.cause().getMessage());
      }
    });
  }
}


class ComplexCallbackVerticle extends AbstractVerticle {

  //prepareDatabase
  public Future<Void> prepareDatabase() {
    System.out.println("PrepareDatabase is called");
    Promise promise = Promise.promise();
   // promise.fail("prepareDatabase is broken");
    promise.complete();
    return promise.future();
  }

  //startHttpDatabase
  public Future<Void> startHttpServer() {
    System.out.println("startHttpServer is called");
    Promise promise = Promise.promise();
    // promise.fail("Http Server is broken");
    promise.complete();
    return promise.future();
  }

  public Future<Void> startWebContainer() {
    System.out.println("startWebContainer is called");
    Promise promise = Promise.promise();
    promise.complete();
    return promise.future();
  }

  @Override
  public void start() throws Exception {
    super.start();
    //callback based : callback hell
//    prepareDatabase().onComplete(ar -> {
//      if (ar.succeeded()) {
//        startHttpServer().onComplete(httpar -> {
//          if (httpar.succeeded()) {
//            startWebContainer().onComplete(web -> {
//              if (web.succeeded()) {
//                System.out.println("All Server is Ready!");
//              }
//            });
//          }
//
//        });
//      }
//    });
    //compose : avoiding callback hell
//    prepareDatabase().compose(handler -> startHttpServer()).compose(web -> startWebContainer()).onComplete(status -> {
//      if (status.succeeded()) {
//        System.out.println("All Server : Compose is Ready!");
//      }
//    });
    prepareDatabase().compose(handler -> startHttpServer()).compose(web -> startWebContainer()).onComplete(status -> {
      if (status.succeeded()) {
        System.out.println("All Server : Compose is Ready!");
      } else {
        System.out.println(status.cause().getMessage());

      }
    });

  }
}


public class CallbackHelloSolution extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(CallbackHelloSolution.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new ComplexCallbackVerticle());
    // vertx.deployVerticle(new HelloMessageCompose());
  }
}
