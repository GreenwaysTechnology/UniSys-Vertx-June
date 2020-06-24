package com.unisys.vertx.handlers.future;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.example.util.Runner;

//Future Demo :
class BasicFutureVerticle extends AbstractVerticle {

  //various way of sending success and failure response

  //way 1 : sending empty  success response
  public Future<Void> getEmptyFuture() {
    //Create Future Object
    Future future = Future.future();
    //send empty response
    future.complete();
    //future
    return future;
  }

  //Sending Response , Response type could be any thing from primitive to Objects,lists,arrays,json
  public Future<String> getDataFuture() {
    //Create Future Object
    Future future = Future.future();
    //declare fake response
    String message = "Hello,How are you!,I am coming from Future";
    future.complete(message);
    //future
    return future;
  }

  //Sending failure Response :  String message /Throwable Instance
  public Future<String> getErrorMessage() {
    //Create Future Object
    Future future = Future.future();
    //declare fake response
    String errorMessage = "Sorry,Something went Wrong!!!";
    future.fail(errorMessage);
    //future
    return future;
  }

  //Success and failure based biz logic

  public Future<String> auth(String userName, String password) {
    //Create Future Object
    Future future = Future.future();
    //biz logic
    if (userName.equals("admin") && password.equals("admin")) {
      future.complete("Login Success!!!");
    } else {
      future.fail("Login failed!!!!");
    }

    //future
    return future;
  }


  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("Future Verticle is ready!!");
    Future future = null;

    //grab empty response :Handlers api
    future = getEmptyFuture();
    if (future.succeeded()) {
      System.out.println("Future is success");
    } else {
      System.out.println("Future is failed!");
    }
    //Grab data response : Handlers api
    future = getDataFuture();
    //old style
    future.setHandler(new Handler<AsyncResult>() {
      @Override
      public void handle(AsyncResult asyncResult) {
        //test success or failure
        if (asyncResult.succeeded()) {
          //grab result
          System.out.println(asyncResult.result());
        } else {
          System.out.println(asyncResult.cause());
        }

      }
    });
    ////lambda + setHandler / onComplete + fluent pattern
    getDataFuture().setHandler(asyncResult -> {
      if (asyncResult.succeeded()) {
        //grab result
        System.out.println(asyncResult.result());
      } else {
        System.out.println(asyncResult.cause());
      }
    });
    getDataFuture().onComplete(asyncResult -> {
      if (asyncResult.succeeded()) {
        //grab result
        System.out.println(asyncResult.result());
      } else {
        System.out.println(asyncResult.cause());
      }
    });
    //Failure Resonse
    getErrorMessage().onComplete(asyncResult -> {
      if (asyncResult.failed()) {
        System.out.println(asyncResult.cause().getMessage());
      } else {

      }
    });

    //biz logic
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
    //only success
    //getDataFuture().onSuccess(result-> System.out.println(result));
    getDataFuture().onSuccess(System.out::println);
    getErrorMessage().onFailure(System.out::println);

  }
}


public class BasicHanldersUsingFuture extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(BasicHanldersUsingFuture.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new BasicFutureVerticle());
  }
}
