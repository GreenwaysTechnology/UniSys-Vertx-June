package com.unisys.vertx.microservice.webclient;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.example.util.Runner;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;

class UserRestApi extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();
    //Create WebClient instance
    WebClient client = WebClient.create(vertx);
    client.get("jsonplaceholder.typicode.com", "/users").send(ar -> {
      if (ar.succeeded()) {
        HttpResponse<Buffer> response = ar.result();
        //process result
        System.out.println(response.bodyAsJsonArray().encodePrettily());
      } else {
        System.out.println(ar.cause());
      }
    });

  }
}


public class ThirdPartyRESTCallVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(ThirdPartyRESTCallVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new UserRestApi());
  }
}
