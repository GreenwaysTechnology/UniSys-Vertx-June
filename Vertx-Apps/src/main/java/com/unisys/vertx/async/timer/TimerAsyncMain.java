package com.unisys.vertx.async.timer;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.example.util.Runner;

import java.util.Date;

class Timer extends AbstractVerticle {

  //delay
  private Future<String> delay(long timer, String message) {
    Promise promise = Promise.promise();
    //return result after some time
    //Async code : once timeout , completes
    vertx.setTimer(timer, handler -> {
      //you wrap async result : send result after delay
      promise.tryComplete(message);
    });

    return promise.future();
  }

  //call for every one sec
  private Future<String> heartBeat() {
    Promise promise = Promise.promise();
    //return result after some time
    vertx.setPeriodic(1000, ar -> {
//       promise.c(new Date().toString());
      System.out.println(new Date().toString());
    });

    return promise.future();
  }

  ;


  @Override
  public void start() throws Exception {
    super.start();

    System.out.println("start");
    delay(1000, "Hey, I am delayed ").onComplete(ar -> {
      if (ar.succeeded()) {
        System.out.println(ar.result());
      } else {
        System.out.println(ar.cause().getMessage());
      }
    });
    delay(2000, "Hey, I am delayed ").onComplete(ar -> {
      if (ar.succeeded()) {
        System.out.println(ar.result());
      } else {
        System.out.println(ar.cause().getMessage());
      }
    });
    delay(5000, "Hey, I am delayed ").onComplete(ar -> {
      if (ar.succeeded()) {
        System.out.println(ar.result());
      } else {
        System.out.println(ar.cause().getMessage());
      }
    });

    heartBeat().onComplete(ar -> {
      if (ar.succeeded()) {
        System.out.println("Result" + ar.result());
      } else {
        System.out.println(ar.cause().getMessage());
      }
    });
    System.out.println("end");

  }
}


public class TimerAsyncMain extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(TimerAsyncMain.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new Timer());
  }
}
