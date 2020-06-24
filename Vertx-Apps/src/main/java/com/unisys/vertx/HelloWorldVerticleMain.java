package com.unisys.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.example.util.Runner;

//separate Verticle
class HelloWorldVerticle extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("HelloWorld Verticle is ready");
    System.out.println(vertx.toString());
  }

  @Override
  public void stop() throws Exception {
    super.stop();
    System.out.println("Hello World is stoped");
  }
}

class GreeterVerticle extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("Greeter Verticle is ready");
    System.out.println(vertx.toString());
  }

  @Override
  public void stop() throws Exception {
    super.stop();
    System.out.println("Hello World is stoped");
  }
}

public class HelloWorldVerticleMain extends AbstractVerticle {
  public static void main(String[] args) {
    //Vertx vertx = Vertx.vertx();
    //vertx.deployVerticle(new HelloWorldVerticle());
    Runner.runExample(HelloWorldVerticleMain.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new HelloWorldVerticle());
    vertx.deployVerticle(new GreeterVerticle(),ar -> {
      if(ar.succeeded()){
        vertx.undeploy(ar.result(), res -> {
          if (res.succeeded()) {
            System.out.println("Undeployed ok");
          } else {
            System.out.println("Undeploy failed!");
          }
        });
      }
    });
  }
}


