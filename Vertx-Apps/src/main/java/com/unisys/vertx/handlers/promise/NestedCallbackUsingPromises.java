package com.unisys.vertx.handlers.promise;

import io.vertx.core.*;
import io.vertx.example.util.Runner;

class CallbackNestingVerticle extends AbstractVerticle {

  //getUser
  public Future<String> getUser() {
    Promise promise = Promise.promise();
    //biz logic
    String fakeUser = "Subramanian";
    if (fakeUser != null) {
      promise.complete(fakeUser);
    } else {
      promise.fail("User Not Found!!");
    }

    return promise.future();
  }

  //login
  public Future<String> login(String userName) {
    Promise promise = Promise.promise();
    //biz logic
    if (userName.equals("Subramanian")) {
      promise.complete("Login success ");
    } else {
      promise.fail("Login failed!!");
    }

    return promise.future();
  }

  @Override
  public void start() throws Exception {
    super.start();
    //Call getUser
    getUser().onComplete(ar -> {
      if (ar.succeeded()) {
        System.out.println("getUser Method : " + ar.result());
        //call login methods
        login(ar.result()).onComplete(lar -> {
          //test login success
          if (lar.succeeded()) {
            System.out.println("Login Method :" + lar.result());
          } else {
            System.out.println(lar.cause().getMessage());
          }
        });
      } else {
        System.out.println(ar.cause());
      }
    });


  }
}

class NestedCallbackWithouOnComplete extends AbstractVerticle {

  //passing handler function as parameter : no need to promise/future
  public void getUser(Handler<AsyncResult<String>> aHandler) {
    String fakeUser = "Subramanian";
    //biz logic
    if (fakeUser != null) {
      //handle success
      aHandler.handle(Future.succeededFuture(fakeUser));
    } else {
      aHandler.handle(Future.failedFuture("No User Found"));
    }
  }

  public void login(String userName, Handler<AsyncResult<String>> aHandler) {
    //biz logic
    if (userName.equals("Subramanian")) {
      //handle success
      aHandler.handle(Future.succeededFuture("Login Success"));
    } else {
      aHandler.handle(Future.failedFuture("Login failed"));
    }
  }


  @Override
  public void start() throws Exception {
    super.start();
    //function as parameter : callback function
    getUser(ar -> {
      if (ar.succeeded()) {
        System.out.println("Get User Method");
        login(ar.result(), lar -> {
          if (lar.succeeded()) {
            System.out.println(lar.result());
          } else {
            System.out.println(lar.cause());
          }
        });

      } else {
        System.out.println(ar.cause().getMessage());
      }
    });
  }
}


public class NestedCallbackUsingPromises extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(NestedCallbackUsingPromises.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new CallbackNestingVerticle());
   // vertx.deployVerticle(new NestedCallbackWithouOnComplete());
  }
}
