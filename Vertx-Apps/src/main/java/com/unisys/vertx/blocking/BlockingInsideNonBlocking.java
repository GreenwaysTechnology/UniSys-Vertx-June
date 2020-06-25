package com.unisys.vertx.blocking;

import io.vertx.core.AbstractVerticle;
import io.vertx.example.util.Runner;


public class BlockingInsideNonBlocking extends AbstractVerticle {

  public static void main(String[] args) {
    Runner.runExample(BlockingInsideNonBlocking.class);
  }

  @Override
  public void start() throws Exception {
    super.start();

    vertx.<String>executeBlocking(blockingHandler -> {

      try {
        System.out.println("Waiting in Blocking Mode!!!");
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        System.out.println(e.getMessage());
      }
      //after 5000ms, send some data to result Handler
      String message = "Sleep is done,hand over me to Some one to handle";
      blockingHandler.complete(message);

    }, resultHandler -> {
      //you can perform non blocking operations here
      System.out.println("Blocking operation is completed!!!");
      if (resultHandler.succeeded()) {
        System.out.println(resultHandler.result());
      }
    });

  }
}
