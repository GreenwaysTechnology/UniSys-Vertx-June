package com.unisys.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.example.util.Runner;

public class MainVerticleRunnerDemo extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(MainVerticleRunnerDemo.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("Hello Vertx!!!");
  }
}
