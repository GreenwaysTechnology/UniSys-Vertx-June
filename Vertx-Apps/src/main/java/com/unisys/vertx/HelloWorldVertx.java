package com.unisys.vertx;

import io.vertx.core.Vertx;

public class HelloWorldVertx {
  public static void main(String[] args) {
    //Create Vertx Instance
    Vertx vertx = Vertx.vertx();
    System.out.println(vertx.toString());
  }
}
