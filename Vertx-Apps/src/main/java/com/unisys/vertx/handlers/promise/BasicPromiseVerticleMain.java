package com.unisys.vertx.handlers.promise;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.example.util.Runner;

class PromiseVerticle extends AbstractVerticle {

  // //way 1 : sending empty  success response
//  public Promise<Void> getEmptyPromise() {
//    //Create Promise Object
//    Promise promise = Promise.promise();
//    //send empty response
//    promise.complete();
//    //future
//    return promise;
//  }

  public Future<Void> getEmptyPromise() {
    //Create Promise Object
    Promise promise = Promise.promise();
    //send empty response
    promise.complete();
    //future
    return promise.future();
  }

  //Sending Response , Response type could be any thing from primitive to Objects,lists,arrays,json
  public Future<String> getDataPromise() {
    //Create Promise Object
    Promise promise = Promise.promise();
    //declare fake response
    String message = "Hello,How are you!,I am coming from Future";
    promise.complete(message);
    //future
    return promise.future();
  }

  public Future<String> auth(String userName, String password) {
    //Create Promise Object
    Promise promise = Promise.promise();
    //biz logic
    if (userName.equals("admin") && password.equals("admin")) {
      promise.complete("Login Success!!!");
    } else {
      promise.fail("Login failed!!!!");
    }

    //future
    return promise.future();
  }

  @Override
  public void start() throws Exception {
    super.start();
    //handle empty promise
//    boolean result = getEmptyPromise().future().succeeded();
//    if (result) {
//      System.out.println("Promise success!!!");
//    }
    boolean result = getEmptyPromise().succeeded();
    if (result) {
      System.out.println("Promise success!!!");
    }
    getDataPromise().onComplete(ar -> {
      System.out.println(ar.result());
    });
    getDataPromise().onComplete(ar -> {
      System.out.println(ar.result());
    });
    getDataPromise().onSuccess(System.out::println);

    auth("admin", "admin").onComplete(ar -> {
      if (ar.succeeded()) {
        System.out.println(ar.result());
      } else {
        System.out.println(ar.cause().getMessage());
      }
    });
    auth("foo", "admin").onComplete(ar -> {
      if (ar.succeeded()) {
        System.out.println(ar.result());
      } else {
        System.out.println(ar.cause().getMessage());
      }
    });
  }
}


public class BasicPromiseVerticleMain extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(BasicPromiseVerticleMain.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new PromiseVerticle());
  }
}
